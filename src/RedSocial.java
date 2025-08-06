import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RedSocial {
    GestorClientes clientes;
    HistorialDeAcciones historialDeAcciones;
    ColaDeSeguimientos colaDeSeguimientos;
    ManejodeJson manejodeJson;
    private List<SolicitudPendiente> solicitudesPendientes = new ArrayList<>();

    public RedSocial() {
        this.clientes = new GestorClientes();
        this.historialDeAcciones = new HistorialDeAcciones();
        this.colaDeSeguimientos = new ColaDeSeguimientos();
        this.manejodeJson = new ManejodeJson();
        this.clientes = manejodeJson.getListaClientes();
        clientes.ordenarPorScoring();
    }

    public void agregarCliente(String nombre, int scoring) {
        if (clientes.clienteExiste(nombre)) {
            System.out.println("El cliente con nombre " + nombre + " ya existe.");
            return;
        }

        int nuevoId = clientes.size();
        Cliente nuevoCliente = new Cliente(nombre, scoring, nuevoId);
        clientes.agregar(nuevoCliente);
        clientes.ordenarPorScoring();
        System.out.println("Cliente agregado: " + nombre);
    }






    public void agregarCliente(Cliente c) {
        if (clientes.clienteExiste(c.getNombre())) {
            System.out.println("Error: Ya existe un cliente con ese nombre.");
            return;
        }
        clientes.agregar(c);
        clientes.ordenarPorScoring();
        historialDeAcciones.agregarAccion(new Accion("REGISTRAR_CLIENTE", c.getNombre()));
    }


    public Cliente buscarClientePorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return null;
        String nombreNormalizado = nombre.trim();
        Cliente clienteEncontrado = clientes.obtenerClientePorNombre(nombreNormalizado);
        if (clienteEncontrado != null) {
            Accion solicitudAccion = new Accion("BUSCAR_CLIENTE");
            historialDeAcciones.agregarAccion(solicitudAccion);
        }
        return clienteEncontrado;
    }

    public void buscarClientePorScoring(Integer scoring) {
        if (scoring == null) {
            System.out.println("Error: El scoring no puede ser nulo.");
            return;
        }

        ArrayList<Cliente> encontrados = clientes.obtenerClientesPorScoring(scoring);
        Accion solicitudAccion = new Accion("BUSCAR_CLIENTE");
        historialDeAcciones.agregarAccion(solicitudAccion);
        for (Cliente cliente : encontrados) {
            System.out.println(cliente.getNombre());
        }
    }


    public void registrarAccion(String tipo){
        Accion accion = new Accion(tipo);
        historialDeAcciones.agregarAccion(accion);
        System.out.println("ACCION REGISTRADA");
    }
    public void eliminarAccion() throws Exception {
        Accion eliminada =historialDeAcciones.eliminarAccion();
        if (eliminada.getTipo() == "AGREGAR_SEGUIDOR"){
            String[] partes = eliminada.getDetalles().split(" ");
            String origen = partes[0];
            String destino = partes[1];
            clientes.eliminarSeguimiento(origen, destino);
        }
        if(eliminada.getTipo() == "REGISTRAR_CLIENTE"){
            String nombre = eliminada.getDetalles();
            clientes.eliminarClientePorNombre(nombre);
        }
        if(eliminada.getTipo() == "ELIMINAR_SEGUIDOR"){
            String[] nombre = eliminada.getDetalles().split(" ");
            String origen = nombre[0];
            String destino = nombre[1];
            clientes.agregarSeguimiento(origen, destino);
        }
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

        if (clientes.estaSiguiendo(origen, destino)){
            System.out.println("Error: Ya estas siguiendo a ese cliente");
            return;
        }
        if (clientes.clienteTieneAmistadCon(origen, destino)){
            System.out.println("Error: Ya tiene conexion con ese cliente");
            return;
        }
        Seguimiento solicitud = new Seguimiento(origen, destino);

        colaDeSeguimientos.agregarSolicitud(solicitud);
        Accion solicitudAccion = new Accion("SOLICITUD_ENVIADA");
        historialDeAcciones.agregarAccion(solicitudAccion);
        System.out.println("Solicitud enviada");
    }

    public void procesarSolicitud() {
        if (colaDeSeguimientos.estaVacia()) {
            System.out.println("No hay solicitudes para procesar.");
            return;
        }

        Seguimiento solicitud = colaDeSeguimientos.procesarProxima();
        if (solicitud == null) {
            System.out.println("No hay solicitudes para procesar.");
            return;
        }

        String origen = solicitud.getOrigen();
        String destino = solicitud.getDestino();

        Cliente clienteOrigen = clientes.obtenerClientePorNombre(origen);
        Cliente clienteDestino = clientes.obtenerClientePorNombre(destino);


        if (clienteOrigen == null || clienteDestino == null) {
            System.out.println("Error: Cliente origen o destino no encontrado.");
            return;
        }

        boolean destinoEsPrivado = clienteDestino.isPrivado();
        boolean destinoYaSigueAOrigen = clientes.estaSiguiendo(destino, origen);
        boolean origenYaSigueADestino = clientes.estaSiguiendo(origen, destino);
        boolean yaSonAmigos = destinoYaSigueAOrigen && origenYaSigueADestino;

        if (!clientes.puedeAgregarSeguimiento(origen) && !destinoYaSigueAOrigen){
            System.out.println("Error: No puedes seguir a mas de dos clientes");
            return;
        }
        if (yaSonAmigos){
            System.out.println("Error: Ya son amigos");
            return;
        }
        Accion accion;


         if (destinoYaSigueAOrigen) {
            clientes.agregaramistadMutua(origen, destino);
            accion = new Accion("AMISTAD_CREADA", origen + " " + destino);
            System.out.println("🤝 Amistad creada entre " + origen + " y " + destino);
        } else {
            clientes.agregarSeguimiento(origen, destino);
            accion = new Accion("AGREGAR_SEGUIDOR", origen + " " + destino);
            System.out.println("✅ " + origen + " ahora sigue a " + destino);
        }

        historialDeAcciones.agregarAccion(accion);
        clientes.reconstruirGrafoDesdeRelaciones();
    }


    public void eliminarSeguidor(String origen, String destino) {
        Cliente cliente1 = clientes.obtenerClientePorNombre(origen);
        Cliente cliente2 = clientes.obtenerClientePorNombre(destino);

        if (origen.equals(destino)) {
            System.out.println("Error: No puedes eliminarte de seguidor");
            return;
        }
        if (cliente1 == null) {
            System.out.println("Error: Cliente origen '" + origen + "' no existe");
            return;
        }
        if (cliente2 == null) {
            System.out.println("Error: Cliente destino '" + destino + "' no existe");
            return;
        }

        boolean esSeguidor = clientes.estaSiguiendo(origen, destino);
        boolean esAmigo = cliente1.getAmistades().contains(destino) && cliente2.getAmistades().contains(origen);

        if (!esSeguidor && !esAmigo) {
            System.out.println("Error: No tienes ni seguimiento ni amistad con ese cliente");
            return;
        }
        if (esSeguidor){
            clientes.eliminarSeguimiento(origen, destino);
        }
        else if (esAmigo){
            cliente1.getAmistades().remove(destino);
            cliente2.getAmistades().remove(origen);
            System.out.println("Amistad eliminada entre " + origen + " y " + destino);
        }


        String detalles = origen +  " " + destino;
        historialDeAcciones.agregarAccion(new Accion("ELIMINAR_SEGUIDOR", detalles));
        clientes.reconstruirGrafoDesdeRelaciones();
    }




    public void consultarAmistad(String nombre){

        if (!getClientes().clienteExiste(nombre)) {
            System.out.println("Error: El cliente \"" + nombre + "\" no existe en el sistema.");
            System.out.println("==============================================\n");
            return;
        }

        Set<String> conexiones = getClientes().obtenerAmistad(nombre);

        System.out.println("\nCliente: " + nombre);

        if (conexiones == null || conexiones.isEmpty()) {
            System.out.println("Conexiones: No tiene conexiones registradas.");
        } else {
            System.out.println("Conexiones (" + conexiones.size() + "):");
            for (String conexion : conexiones) {
                System.out.println(" - " + conexion);
            }
        }

        System.out.println("==============================================\n");
    }

    public GestorClientes getClientes(){
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


    public void cambiarPrivacidad(String nombreCliente, boolean privacidad) {
        Cliente cliente = clientes.obtenerClientePorNombre(nombreCliente);
        if (cliente != null) {
            cliente.setPrivacidad(privacidad);
            System.out.println("La privacidad del cliente " + nombreCliente + " ha sido actualizada a " + (privacidad ? "privado" : "público"));
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }
    public void verSolicitudesPendientes(String clienteId) {
        if (!clientes.clienteExiste(clienteId)) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        boolean tieneSolicitudes = false;
        for (SolicitudPendiente sp : solicitudesPendientes) {
            if (sp.getDestino().equals(clienteId)) {
                System.out.println(sp);
                tieneSolicitudes = true;
            }
        }
        if (!tieneSolicitudes) {
            System.out.println("No tiene solicitudes pendientes.");
        }
    }


    public void aceptarSolicitud(String origenId, String destinoId) {
        if (!clientes.clienteExiste(origenId) || !clientes.clienteExiste(destinoId)) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        SolicitudPendiente solicitudAceptada = null;
        for (SolicitudPendiente sp : solicitudesPendientes) {
            if (sp.getOrigen().equals(origenId) && sp.getDestino().equals(destinoId)) {
                solicitudAceptada = sp;
                break;
            }
        }

        if (solicitudAceptada != null) {
            if (solicitudAceptada.esAmistad()) {
                clientes.agregaramistadMutua(origenId, destinoId);
            } else {
                clientes.agregarSeguimiento(origenId, destinoId);
            }
            solicitudesPendientes.remove(solicitudAceptada);
            clientes.reconstruirGrafoDesdeRelaciones();
            System.out.println("Solicitud aceptada.");
        } else {
            System.out.println("No se encontró la solicitud pendiente.");
        }
    }


    public void rechazarSolicitud(String origenId, String destinoId) {
        if (!clientes.clienteExiste(origenId) || !clientes.clienteExiste(destinoId)) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        boolean removed = solicitudesPendientes.removeIf(sp ->
                sp.getOrigen().equals(origenId) && sp.getDestino().equals(destinoId)
        );

        if (removed) {
            System.out.println("Solicitud rechazada.");
        } else {
            System.out.println("No se encontró la solicitud pendiente para rechazar.");
        }
    }
    public GestorClientes getGestorClientes() {
        return clientes;
    }


    public GestorConexiones getGrafo() {
        return clientes.getGrafo();
    }



    public void imprimirEstadoCompleto() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              ESTADO COMPLETO DEL SISTEMA                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        // Imprimir clientes
        clientes.imprimirLista();

        // Imprimir resumen
        clientes.imprimirResumen();

        // Imprimir solicitudes pendientes
        //colaDeSeguimientos.imprimirCola();

        // Imprimir últimas acciones
        historialDeAcciones.imprimirUltimasAcciones(5);
    }

    public void imprimirMenuPrincipal() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                    SISTEMA DE CLIENTES                     ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║  1. 🔍 Buscar cliente por nombre                          ║");
        System.out.println("║  2. 📊 Buscar cliente por scoring                         ║");
        System.out.println("║  3. 📝 Registrar acción                                   ║");
        System.out.println("║  4. ↩️  Eliminar última acción                            ║");
        System.out.println("║  5. 📨 Crear solicitud de seguimiento                     ║");
        System.out.println("║  6. ⚙️  Procesar solicitud de seguimiento                 ║");
        System.out.println("║  7. 📋 Consultar acciones                                 ║");
        System.out.println("║  8. ❌ Ver solicitudes de seguimiento                     ║");
        System.out.println("║  9. ➕ Eliminar seguidor                                  ║");
        System.out.println("║ 10. 📊 Ver estado completo del sistema                    ║");
        System.out.println("║ 11. 📊 Consultar Amistades                                ║");
        System.out.println("║ 12. 🔒 Hacer cliente privado                              ║");
        System.out.println("║ 13. 👥 Mostrar Vecinos                                    ║");
        System.out.println("║ 14. 📏 Calcular distancia entre clientes                  ║");
        System.out.println("║ 15. 🗺️  Mostrar camino más corto entre clientes           ║");
        System.out.println("║  0. 🚪 Salir                                              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

    /**
     * Calcula y muestra la distancia entre dos clientes
     *
     * @param clienteOrigen Nombre del cliente origen
     * @param clienteDestino Nombre del cliente destino
     */
    public void calcularDistanciaEntreClientes(String clienteOrigen, String clienteDestino) {
        clientes.mostrarDistancia(clienteOrigen, clienteDestino);
        // Registrar la acción en el historial
        historialDeAcciones.agregarAccion(new Accion("CALCULAR_DISTANCIA",
                "De " + clienteOrigen + " a " + clienteDestino));
    }

    /**
     * Muestra el camino más corto entre dos clientes
     *
     * @param clienteOrigen Nombre del cliente origen
     * @param clienteDestino Nombre del cliente destino
     */
    public void mostrarCaminoEntreClientes(String clienteOrigen, String clienteDestino) {
        clientes.mostrarCaminoMasCorto(clienteOrigen, clienteDestino);
        // Registrar la acción en el historial
        historialDeAcciones.agregarAccion(new Accion("MOSTRAR_CAMINO",
                "De " + clienteOrigen + " a " + clienteDestino));
    }

    /**
     * Obtiene solo la distancia numérica entre dos clientes
     *
     * @param clienteOrigen Nombre del cliente origen
     * @param clienteDestino Nombre del cliente destino
     * @return La distancia en saltos, -1 si no hay conexión, -2 si hay error
     */
    public int obtenerDistancia(String clienteOrigen, String clienteDestino) {
        return clientes.calcularDistancia(clienteOrigen, clienteDestino);
    }
}

