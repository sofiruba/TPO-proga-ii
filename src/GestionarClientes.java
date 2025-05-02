import java.util.ArrayList;

public class GestionarClientes {
    ListaCliente clientes;
    HistorialDeAcciones historialDeAcciones;
    ColaDeSeguimientos colaDeSeguimientos;
    ManejodeJson manejodeJson;

    public GestionarClientes() {
        this.clientes = new ListaCliente();
        this.historialDeAcciones = new HistorialDeAcciones();
        this.colaDeSeguimientos = new ColaDeSeguimientos();
        this.manejodeJson = new ManejodeJson();
        this.clientes = manejodeJson.getListaClientes();
    }

    public void agregarCliente(Cliente c) {
        clientes.agregar(c);
        Accion nueva = new Accion("AGREGAR_CLIENTE");
        historialDeAcciones.agregarAccion(nueva);
    }

    public void enviarSolicitud(String origen, String destino) {
        Seguimiento solicitud = new Seguimiento(origen, destino);
        colaDeSeguimientos.agregarSolicitud(solicitud);
    }

    public void procesarSolicitud() {
        colaDeSeguimientos.procesarProxima();

    }

    public void deshacerUltimaAccion() throws Exception {
        Accion eliminada = historialDeAcciones.eliminarAccion();
    }
}
