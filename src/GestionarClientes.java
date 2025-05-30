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
        Accion nueva = new Accion("CLIENTE REGISTRADO");
        historialDeAcciones.agregarAccion(nueva);
    }

    public void buscarClientePorNombre(String nombre) {
        Cliente clienteEncontrado = clientes.obtenerClientePorNombre(nombre);
        Accion solicitudAccion = new Accion("BUSCAR CLIENTE");
        historialDeAcciones.agregarAccion(solicitudAccion);
        System.out.println(clienteEncontrado.getNombre());
    }
    
    public void buscarClientePorScoring(Integer scoring) {
        ArrayList<Cliente> encontrados = clientes.obtenerClientesPorScoring(scoring);
        Accion solicitudAccion = new Accion("BUSCAR CLIENTE");
        historialDeAcciones.agregarAccion(solicitudAccion);
        System.out.println(encontrados);
    }
    public void registrarAccion(String tipo){
        Accion accion = new Accion(tipo);
        historialDeAcciones.agregarAccion(accion);
        System.out.println("ACCION REGISTRADA");
    }
    public void eliminarAccion() throws Exception {
        Accion eliminada =historialDeAcciones.eliminarAccion();
        if (eliminada.getTipo() == "SOLICITUD PROCESADA"){
            String[] partes = eliminada.getDetalles().split(" ");
            String origen = partes[0];
            String destino = partes[1];
           //clientes.eliminarseguidor(origen, destino);
        }
        System.out.println("ACCION ELIMINADA");
    }

    public void enviarSolicitud(String origen, String destino) {
        Cliente clienteOrigen = clientes.obtenerClientePorNombre(origen);
        Cliente clienteDestino = clientes.obtenerClientePorNombre(destino);
        if (clienteOrigen == null) {
            System.out.println("Error: Cliente origen '" + origen + "' no existe");
            return;
        }
        if (clienteDestino == null) {
            System.out.println("Error: Cliente destino '" + destino + "' no existe");
            return;
        }
        if (origen.equals(destino)) {
            System.out.println("Error: No puedes seguirte a ti mismo");
            return;
        }
        Seguimiento solicitud = new Seguimiento(origen, destino);
        // aca verificar si sigue a 2 o mas
        colaDeSeguimientos.agregarSolicitud(solicitud);
        Accion solicitudAccion = new Accion("SOLICITUD ENVIADA");
        historialDeAcciones.agregarAccion(solicitudAccion);
        System.out.println("Solicitud enviada");
    }

    public void procesarSolicitud() {
        Seguimiento solicitudProcesada = colaDeSeguimientos.procesarProxima();
        String detalles = solicitudProcesada.getOrigen() + " " + solicitudProcesada.getDestino();
        Accion solicitudAccion = new Accion("SOLICITUD PROCESADA", detalles);
        //clientes.agregarSeguidor(solicitudProcesada.origen,solicitudProcesada.destino);
        historialDeAcciones.agregarAccion(solicitudAccion);
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
