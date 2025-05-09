import java.util.ArrayList;
import java.util.List;

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
    public void buscarClientePorNombre(String nombre) {
        Cliente clienteEncontrado= clientes.obtenerClientePorNombre(nombre);
        System.out.println(clienteEncontrado.getNombre());
    }
    public void buscarClientePorScoring(Integer scoring) {
        ArrayList<Cliente> encontrados = clientes.obtenerClientesPorScoring(scoring);
        System.out.println(encontrados);
    }
    public void registrarAccion(String tipo){
        Accion accion = new Accion(tipo);
        historialDeAcciones.agregarAccion(accion);
        System.out.println("Registro exitoso");
    }
    public void eliminarAccion() throws Exception {
        historialDeAcciones.eliminarAccion();
        System.out.println("Elimino de Accion exitoso");
    }

    public void enviarSolicitud(String origen, String destino) {
        Seguimiento solicitud = new Seguimiento(origen, destino);
        colaDeSeguimientos.agregarSolicitud(solicitud);
        System.out.println("Solicitud enviada");
    }

    public void procesarSolicitud() {
        colaDeSeguimientos.procesarProxima();
        System.out.println("Solicitud procesada");

    }

    public void deshacerUltimaAccion() throws Exception {
        Accion eliminada = historialDeAcciones.eliminarAccion();
    }
    public ListaCliente getClientes(){
        return clientes;
    }
    public ColaDeSeguimientos getSolicitudes() {
        return colaDeSeguimientos;
    }

    public HistorialDeAcciones  getHistorialDeAcciones() {
        return historialDeAcciones;
    }
    public void consultarAcciones(){
        List <Accion> acciones = historialDeAcciones.getHistorialDeAcciones();
        System.out.println(acciones);
    }
    public ArrayList<Cliente> getClientesLista(){
        return clientes.getClientes();
    }
    public List<Seguimiento> getSolicitudesLista() {
        return colaDeSeguimientos.getSolicitudes();
    }
    public List <Accion>  getHistorialDeAccionesLista() {
        return historialDeAcciones.getHistorialDeAcciones();
    }
}
