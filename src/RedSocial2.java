import java.util.List;

public class RedSocial2 {
     GestorClientes2 gestorClientes;
     GestorClientes gestorCliente;
    ColaDeSeguimientos colaDeSeguimientos;
    HistorialDeAcciones historialDeAcciones;

    // Ruta fija al archivo JSON
    private static final String RUTA_JSON = "C:\\Users\\USUARIO\\Downloads\\final final falta test\\final falta test\\progra ii reco\\src\\Listadeclientes.json";

    // Constructor: carga automática de clientes desde el archivo JSON
    public RedSocial2() {
        this.gestorClientes = new GestorClientes2();
        ManejodeJson2 manejador = new ManejodeJson2(this.gestorClientes, RUTA_JSON);
        manejador.cargarDesdeJson();
        this.colaDeSeguimientos = new ColaDeSeguimientos();
        this.historialDeAcciones = new HistorialDeAcciones();
        gestorClientes.g.reconstruirGrafoDesdeRelaciones(); // ¡antes de llamar a obtenerClientesConMasConexiones()!


    }

    public void mostrarJerarquia() {
        gestorClientes.imprimirJerarquiaCompleta();
    }
    public void mostrarAVL() {
        gestorClientes.imprimirAVLCompleto();
    }


    // Getter del gestor
    public GestorClientes2 getGestorClientes2() {
        return gestorClientes;
    }

    // Agregar un cliente manualmente
    public void agregarCliente(String nombre, int scoring) {
        Cliente nuevo = new Cliente(nombre, scoring, -1); // el ID se asigna internamente
        gestorClientes.agregar(nuevo);
        gestorClientes.g.reconstruirGrafoDesdeRelaciones();
    }


    // Buscar clientes con scoring mayor a
    public List<Cliente> buscarClientesMayoresA(int valor) {
        return gestorClientes.buscarClientesConScoringMayorA(valor);
    }


    // Buscar clientes por scoring exacto
    public List<Cliente> buscarPorScoring(int scoring) {
        return gestorClientes.obtenerClientesPorScoringArbol(scoring);
    }

    // Buscar clientes dentro de un rango de scoring
    public List<Cliente> buscarEnRango(int min, int max) {
        return gestorClientes.obtenerClientesEnRango(min, max);
    }

    // Obtener el cliente con más conexiones
    public List<Cliente> obtenerClienteConMasConexiones() {
        return gestorClientes.obtenerClientesConMasConexiones();
    }

    // Mostrar clientes por nivel jerárquico
    public void mostrarClientesPorNivel(int nivel) {
        gestorClientes.obtenerClientesPorNivel(nivel);
    }



    // Obtener cliente por nombre
    public Cliente getCliente(String nombre) {
        return gestorClientes.g.obtenerClientePorNombre(nombre);
    }

    public void cargarClientesDesdeArchivo(String path) {
        ManejodeJson2 manejador = new ManejodeJson2(gestorClientes, path);
        manejador.cargarDesdeJson();
    }

    // Mandar solicitud de seguimiento
    public void enviarSolicitud(String origen, String destino) {
        Cliente clienteOrigen = gestorClientes.g.obtenerClientePorNombre(origen);
        Cliente clienteDestino = gestorClientes.g.obtenerClientePorNombre(destino);
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

        if (gestorClientes.g.estaSiguiendo(origen, destino)){
            System.out.println("Error: Ya estas siguiendo a ese cliente");
            return;
        }
        if (gestorClientes.g.clienteTieneAmistadCon(origen, destino)){
            System.out.println("Error: Ya tiene conexion con ese cliente");
            return;
        }
        Seguimiento solicitud = new Seguimiento(origen, destino);

        colaDeSeguimientos.agregarSolicitud(solicitud);
        Accion solicitudAccion = new Accion("SOLICITUD_ENVIADA");
        historialDeAcciones.agregarAccion(solicitudAccion);
        System.out.println("Solicitud enviada");
    }

    // Procesar solicitud -> crear amistad o seguimiento
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

        Cliente clienteOrigen = gestorClientes.g.obtenerClientePorNombre(origen);
        Cliente clienteDestino = gestorClientes.g.obtenerClientePorNombre(destino);


        if (clienteOrigen == null || clienteDestino == null) {
            System.out.println("Error: Cliente origen o destino no encontrado.");
            return;
        }

        boolean destinoEsPrivado = clienteDestino.isPrivado();
        boolean destinoYaSigueAOrigen = gestorClientes.g.estaSiguiendo(destino, origen);
        boolean origenYaSigueADestino = gestorClientes.g.estaSiguiendo(origen, destino);
        boolean yaSonAmigos = destinoYaSigueAOrigen && origenYaSigueADestino;

        if (!gestorClientes.g.puedeAgregarSeguimiento(origen) && !destinoYaSigueAOrigen){
            System.out.println("Error: No puedes seguir a mas de dos clientes");
            return;
        }
        if (yaSonAmigos){
            System.out.println("Error: Ya son amigos");
            return;
        }
        Accion accion;


        if (destinoYaSigueAOrigen) {
            gestorClientes.g.agregaramistadMutua(origen, destino);
            accion = new Accion("AMISTAD_CREADA", origen + " " + destino);
            System.out.println("🤝 Amistad creada entre " + origen + " y " + destino);
        } else {
            gestorClientes.g.agregarSeguimiento(origen, destino);
            accion = new Accion("AGREGAR_SEGUIDOR", origen + " " + destino);
            System.out.println("✅ " + origen + " ahora sigue a " + destino);
        }

        historialDeAcciones.agregarAccion(accion);
        gestorClientes.g.reconstruirGrafoDesdeRelaciones();
    }
    public void compararVelocidadBusqueda(int scoring) {
        System.out.println("⏱️ Comparando búsqueda por scoring (" + scoring + ") entre AVL y ABB...");

        // Tiempo ABB
        long inicioABB = System.nanoTime();
        List<Cliente> abbResultado = gestorClientes.buscarPorScoringABB(scoring);
        long finABB = System.nanoTime();
        long tiempoABB = finABB - inicioABB;

        // Tiempo AVL
        long inicioAVL = System.nanoTime();
        List<Cliente> avlResultado = gestorClientes.obtenerClientesPorScoringArbol(scoring);
        long finAVL = System.nanoTime();
        long tiempoAVL = finAVL - inicioAVL;

        // Resultados
        System.out.println("\n ABB → " + abbResultado.size() + " cliente(s) encontrados en " + tiempoABB + " nanosegundos.");
        System.out.println(" AVL → " + avlResultado.size() + " cliente(s) encontrados en " + tiempoAVL + " nanosegundos.");

        if (tiempoABB < tiempoAVL) {
            System.out.println(" ABB fue más rápido en este caso.");
        } else if (tiempoAVL < tiempoABB) {
            System.out.println(" AVL fue más rápido en este caso.");
        } else {
            System.out.println("⚖ Ambos tardaron lo mismo (o diferencia insignificante).");
        }
    }

}

