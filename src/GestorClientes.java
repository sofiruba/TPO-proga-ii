import java.util.*;

public class GestorClientes {
     ArrayList<Cliente> clientes;
     HashMap<String, Cliente> clientesMap;
     GestorConexiones grafo;
     int contadorId;

    public GestorClientes() {
        this.clientes = new ArrayList<>();
        this.clientesMap = new HashMap<>();
        this.grafo = new GestorConexiones();
        this.contadorId = 0; // Inicializar contador de IDs
    }

    public HashMap<String, Cliente> getClientesMap() {
        return clientesMap;
    }


    public void mostrarVecinosDesdeGrafo(String nombre) {
        reconstruirGrafoDesdeRelaciones();  // Asegura que el grafo esté actualizado

        Cliente cliente = obtenerClientePorNombre(nombre);
        if (cliente == null) {
            System.out.println("El cliente '" + nombre + "' no existe.");
            return;
        }

        int id = cliente.getIdgrafo(); // Convertir String a int
        Set<Integer> vecinos = grafo.getVecinos(id);

        System.out.println("\n=== CONEXIONES DE " + nombre.toUpperCase() + " ===");

        if (vecinos == null || vecinos.isEmpty()) {
            System.out.println("No tiene conexiones en el grafo.");
        } else {
            System.out.println("Conectado directamente con:");
            for (Integer idVecino : vecinos) {
                Cliente vecino = getClientePorId(idVecino);
                if (vecino != null) {
                    System.out.println("- " + vecino.getNombre() + " (ID: " + vecino.getIdgrafo() + ")");
                } else {
                    System.out.println("- Cliente con ID " + idVecino + " no encontrado.");
                }
            }
        }


        System.out.println("Total de conexiones directas: " + vecinos.size());
        System.out.println("=====================================\n");
    }
    public void reconstruirGrafoDesdeRelaciones() {
        grafo = new GestorConexiones();  // resetear grafo

        for (Cliente cliente : clientes) {
            int id = cliente.getIdgrafo();
            grafo.adicionarVertice(id);
        }

        for (Cliente cliente : clientes) {
            int idOrigen = cliente.getIdgrafo();

            // Opcional: Si usas conexiones aparte, agregar también
            if (cliente.getConexiones() != null) {
                for (String nombreConexion : cliente.getConexiones()) {
                    Cliente conexion = obtenerClientePorNombre(nombreConexion);
                    if (conexion != null) {
                        int idDestino = conexion.getIdgrafo();
                        grafo.adicionarArista(idOrigen, idDestino);
                    }
                }
            }
        }
    }


    public GestorConexiones getGrafo() {
        return grafo;
    }

    private Cliente getClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdgrafo() == id) {
                return cliente;
            }
        }
        return null;
    }

    public void registrarVertice(Cliente cliente) {
        int id = cliente.getIdgrafo();
        if (!grafo.contieneVertice(id)) {
            grafo.adicionarVertice(id);
        }
    }






    public void imprimirGrafo() {
        grafo.imprimirGrafo();
    }


    public boolean clienteTieneAmistadCon(String nombre, String conectado) {
        Cliente cliente = obtenerClientePorNombre(nombre);
        if (cliente == null) {
            System.out.println("Cliente no encontrado: " + nombre);
            return false;
        }
        return cliente.getAmistades().contains(conectado);
    }
    //TENEMOS QUE HACER SIGUIMIENTO PARA PODER HACER AMISTAD
    public boolean agregaramistadMutua(String nombre1, String nombre2) {
        Cliente cliente1 = obtenerClientePorNombre(nombre1);
        Cliente cliente2 = obtenerClientePorNombre(nombre2);

        // Validar que ambos clientes existan
        if (cliente1 == null || cliente2 == null) {
            System.out.println("Uno o ambos clientes no existen.");
            return false;
        }

        // Validar que no sea el mismo cliente
        if (nombre1.equalsIgnoreCase(nombre2)) {
            System.out.println("Un cliente no puede conectarse consigo mismo.");
            return false;
        }
        this.eliminarSeguimiento(nombre1, nombre2);
        this.eliminarSeguimiento(nombre2, nombre1);
        // Agregar conexión mutua
        cliente1.getAmistades().add(nombre2);
        cliente2.getAmistades().add(nombre1);



        System.out.println("Conexión mutua creada entre " + nombre1 + " y " + nombre2);
        return true;
    }


    public boolean tienenAmistad(String nombre1, String nombre2) {
        Cliente cliente1 = obtenerClientePorNombre(nombre1);
        Cliente cliente2 = obtenerClientePorNombre(nombre2);

        if (cliente1 == null || cliente2 == null) {
            return false;
        }

        return cliente1.getAmistades().contains(nombre2) &&
                cliente2.getAmistades().contains(nombre1);
    }

    // Método para eliminar conexión mutua entre dos clientes
    public void eliminarAmistad(String nombre1, String nombre2) {
        nombre1 = capitalizar(nombre1);
        nombre2 = capitalizar(nombre2);

        Cliente cliente1 = obtenerClientePorNombre(nombre1);
        Cliente cliente2 = obtenerClientePorNombre(nombre2);

        if (cliente1 != null && cliente2 != null) {
            cliente1.getAmistades().remove(nombre2);
            cliente2.getAmistades().remove(nombre1);
            System.out.println("Conexión mutua eliminada entre " + nombre1 + " y " + nombre2);
        } else {
            System.out.println("Uno o ambos clientes no existen.");
        }
    }


    // Método para verificar si un cliente está siguiendo a otro
    public boolean estaSiguiendo(String seguidor, String seguido) {
        Cliente cliente = obtenerClientePorNombre(seguidor);
        return cliente != null && cliente.getSiguiendo().contains(seguido);
    }

    // Método para eliminar un seguimiento
    public void eliminarSeguimiento(String seguidor, String seguido) {
        Cliente cliente = obtenerClientePorNombre(seguidor);
        Cliente clienteSeguido = obtenerClientePorNombre(seguido);

        if (cliente == null || clienteSeguido == null) {
            System.out.println("Uno o ambos clientes no existen.");
            return;
        }

        // Verificar si eran amigos y eliminar la amistad si aplica
        if (cliente.getAmistades().contains(seguido) && clienteSeguido.getAmistades().contains(seguidor)) {
            cliente.getAmistades().remove(seguido);
            clienteSeguido.getAmistades().remove(seguidor);
            grafo.eliminarArista(cliente.getIdgrafo(), clienteSeguido.getIdgrafo());
            grafo.eliminarArista(clienteSeguido.getIdgrafo(), cliente.getIdgrafo());
            return;
        }

        // Si era solo un seguimiento
        if (estaSiguiendo(seguidor, seguido)) {
            cliente.getSiguiendo().remove(seguido);                    // El seguidor deja de seguir
            clienteSeguido.getConexiones().remove(seguidor);          // El seguido pierde al seguidor
            grafo.eliminarArista(cliente.getIdgrafo(), clienteSeguido.getIdgrafo());
        }
    }


    // Método para agregar un seguimiento
    public void agregarSeguimiento(String seguidor, String seguido) {
        Cliente cliente = obtenerClientePorNombre(seguidor);
        Cliente seguidoCliente = obtenerClientePorNombre(seguido);

        // Validar existencia de ambos
        if (cliente == null || seguidoCliente == null) {
            System.out.println("Alguno de los clientes no existe.");
            return;
        }

        if (seguidor.equalsIgnoreCase(seguido)) {
            System.out.println("Un cliente no puede seguirse a sí mismo.");
            return;
        }

        if (cliente.getSiguiendo().contains(seguido)) {
            System.out.println(seguidor + " ya sigue a " + seguido);
            return;
        }

        if (!puedeAgregarSeguimiento(seguidor)) {
            System.out.println("Error: " + seguidor + " ha alcanzado el límite de 2 seguidos.");
            return;
        }

        cliente.getSiguiendo().add(seguido);
        seguidoCliente.getConexiones().add(seguidor);
        System.out.println(seguidor + " ahora sigue a " + seguido);
    }


    // Método para verificar si un cliente puede seguir a más personas
    public boolean puedeAgregarSeguimiento(String nombre) {
        Cliente cliente = obtenerClientePorNombre(nombre);
        return cliente != null && cliente.getSiguiendo().size() < 2;
    }

    // Método para verificar si ambos clientes existen
    public boolean ambosClientesExisten(String nombre1, String nombre2) {
        return obtenerClientePorNombre(nombre1) != null &&
                obtenerClientePorNombre(nombre2) != null;
    }


    public Set<String> obtenerSiguiendo(String nombre) {
        Cliente cliente = obtenerClientePorNombre(nombre);
        if (cliente == null) {
            return null; // o Collections.emptySet() si preferís evitar null
        }
        return cliente.getSiguiendo();
    }

    public static String capitalizar(String nombre) {
        if (nombre == null || nombre.isEmpty()) return nombre;
        nombre = nombre.trim().toLowerCase();
        return Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1);
    }


    public boolean clienteExiste(String nombre) {
        return clientesMap.containsKey(nombre);
    }
    public void agregar(Cliente cliente) {
        clientes.add(cliente);
        clientesMap.put(cliente.getNombre(), cliente);

    }

    public Cliente obtener(int indice) {
        return clientes.get(indice);
    }

    public int size() {
        return clientes.size();
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public Cliente obtenerClientePorNombre(String nombre) {
        if (nombre == null) return null;
        String nombreNormalizado = nombre.trim().toLowerCase();
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().trim().toLowerCase().equals(nombreNormalizado)) {
                return cliente;
            }
        }
        return null;
    }
   
    public HashMap<String, Cliente> obtenerTodosLosClientes() {
        return clientesMap;
    }
    
    public void eliminarClientePorNombre(String nombre) {
        try {
            Cliente cliente = clientesMap.remove(nombre);
    
            if (cliente == null) {
                throw new Exception("Cliente con nombre " + nombre + " no encontrado.");
            }
    
            clientes.remove(cliente);
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    

    public void eliminarClientePorIndice(int indice) {
        try {
            if (indice < 0 || indice >= clientes.size()) {
                throw new IndexOutOfBoundsException("Índice fuera de rango.");
            }
    
            Cliente cliente = clientes.remove(indice);
            clientesMap.remove(cliente.getNombre());
    
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public ArrayList<Cliente> obtenerClientesPorScoring(int scoring) {
        ArrayList<Cliente> clientesEncontrados = new ArrayList<>();
    
        try {
            for (Cliente cliente : clientes) {
                if (cliente.getScoring() == scoring) {
                    clientesEncontrados.add(cliente);  
                }
            }
    
            if (clientesEncontrados.isEmpty()) {
                System.out.println("No se encontraron clientes con Scoring " + scoring);
            }
    
        } catch (Exception e) {
            System.out.println("Se produjo un error: " + e.getMessage());
        }
    
        return clientesEncontrados; 
    }

    public void ordenarPorScoring(){

        if (clientes.isEmpty()) {
            System.out.println("No se encontraron clientes con Scoring");
            return;
        }
        ArrayList<Cliente> listaParaOrdenar = new ArrayList<>(clientes);

        // Ordenar la lista usando Collections.sort con un Comparator
        // usamos collections para utilizar la funcion de comparator y ordenar en o(n log n)
        Collections.sort(listaParaOrdenar, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                // Compara por scoring: devuelve negativo si c1 < c2, positivo si c1 > c2, 0 si iguales
                return Integer.compare(c1.getScoring(), c2.getScoring());
            }
        });

        // Actualizar la lista original con la lista ordenada
        this.clientes = listaParaOrdenar;

    }

    public boolean nombresExisten(Set<String> nombres) {
        for (String nombre : nombres) {
            if (!clientesMap.containsKey(nombre)) {
                return false;
            }
        }
        return true;
    }

    private boolean validarReferencias(Set<String> siguiendo, Set<String> conexiones) {
        if (siguiendo != null && nombresExisten(siguiendo)) {
            System.out.println("Uno o más nombres en 'siguiendo' no existen en el sistema.");
            return false;
        }
        if (conexiones != null && nombresExisten(conexiones)) {
            System.out.println("Uno o más nombres en 'conexiones' no existen en el sistema.");
            return false;
        }
        return true;
    }

    public List<String> obtenerSeguidosPorCliente(String nombre) {
        Cliente cliente = obtenerClientePorNombre(nombre);
        if (cliente == null) {
            System.out.println("Error: Cliente '" + nombre + "' no encontrado.");
            return new ArrayList<>();
        }
        return new ArrayList<>(cliente.getSiguiendo());
    }

    public void imprimirLista() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                          LISTA DE CLIENTES                                         ║");
        System.out.println("╠═════════════════┬══════════┬═══════════════════════════┬═══════════════════════════┬═══════════════════════════╣");
        System.out.printf("║ %-15s │ %-8s │ %-25s │ %-25s │ %-25s ║%n",
                "NOMBRE", "SCORING", "AMISTADES", "SIGUIENDO", "SEGUIDORES");
        System.out.println("╠═════════════════┼══════════┼═══════════════════════════┼═══════════════════════════┼═══════════════════════════╣");

        for (Cliente cliente : clientes) {
            String amistades = cliente.getAmistades().isEmpty() ? "ninguna" : String.join(", ", cliente.getAmistades());
            String siguiendo = cliente.getSiguiendo().isEmpty() ? "ninguno" : String.join(", ", cliente.getSiguiendo());
            String seguidores = cliente.getConexiones().isEmpty() ? "ninguno" : String.join(", ", cliente.getConexiones());

            amistades = amistades.length() > 25 ? amistades.substring(0, 22) + "..." : amistades;
            siguiendo = siguiendo.length() > 25 ? siguiendo.substring(0, 22) + "..." : siguiendo;
            seguidores = seguidores.length() > 25 ? seguidores.substring(0, 22) + "..." : seguidores;

            System.out.printf("║ %-15s │ %-8d │ %-25s │ %-25s │ %-25s ║%n",
                    cliente.getNombre(),
                    cliente.getScoring(),
                    amistades,
                    siguiendo,
                    seguidores);
        }
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }


    public void imprimirResumen() {
        System.out.println("\n RESUMEN DEL SISTEMA");
        System.out.println("═══════════════════════");
        System.out.printf("Total de clientes: %d%n", clientes.size());

        int totalSeguimientos = 0;
        int totalConexiones = 0;

        for (Cliente cliente : clientes) {
            totalSeguimientos += cliente.getSiguiendo().size();
            totalConexiones += cliente.getAmistades().size();
        }

        System.out.printf(" - Total seguimientos: %d%n", totalSeguimientos);
        System.out.printf(" - Total conexiones: %d%n", totalConexiones / 2); // Dividido por 2 porque son mutuas
    }


    public Set<String> obtenerAmistad(String nombre) {
        Cliente cliente = obtenerClientePorNombre(nombre);
        if (cliente == null) {
            return null;
        }
        return cliente.getAmistades();
    }
    
    /**
     * Calcula la distancia (número de saltos) entre dos clientes en la red.
     * Utiliza BFS (Breadth-First Search) para encontrar el camino más corto.
     * 
     * @param clienteOrigen Nombre del cliente origen
     * @param clienteDestino Nombre del cliente destino
     * @return La distancia en número de saltos, -1 si no hay conexión, -2 si hay error
     */
    public int calcularDistancia(String clienteOrigen, String clienteDestino) {
        if (clienteOrigen == null || clienteDestino == null) {
            System.out.println("Error: Los nombres de los clientes no pueden ser nulos.");
            return -2;
        }

        if (clienteOrigen.equals(clienteDestino)) {
            return 0;
        }

        if (!clienteExiste(clienteOrigen) || !clienteExiste(clienteDestino)) {
            System.out.println("Error: Uno o ambos clientes no existen en el sistema.");
            return -2;
        }

        Queue<String> cola = new LinkedList<>();
        Map<String, Integer> distancias = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        cola.offer(clienteOrigen);
        distancias.put(clienteOrigen, 0);
        visitados.add(clienteOrigen);

        while (!cola.isEmpty()) {
            String clienteActual = cola.poll();
            int distanciaActual = distancias.get(clienteActual);

            if (clienteActual.equals(clienteDestino)) {
                return distanciaActual;
            }

            Cliente clienteObj = obtenerClientePorNombre(clienteActual);
            if (clienteObj == null) continue;

            // Explorar vecinos: siguiendo + amistades
            Set<String> vecinos = new HashSet<>();
            vecinos.addAll(clienteObj.getSiguiendo());
            vecinos.addAll(clienteObj.getAmistades());

            for (String vecino : vecinos) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    distancias.put(vecino, distanciaActual + 1);
                    cola.offer(vecino);
                }
            }
        }

        return -1; // No hay camino
    }

    /**
     * Muestra la distancia entre dos clientes con un formato más amigable
     * 
     * @param clienteOrigen Nombre del cliente origen
     * @param clienteDestino Nombre del cliente destino
     */
    public void mostrarDistancia(String clienteOrigen, String clienteDestino) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                 CÁLCULO DE DISTANCIA                     ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        
        int distancia = calcularDistancia(clienteOrigen, clienteDestino);
        
        switch (distancia) {
            case -2:
                System.out.printf("║ ERROR: Parámetros inválidos                             ║%n");
                break;
            case -1:
                System.out.printf("║ De: %-48s ║%n", clienteOrigen);
                System.out.printf("║ A:  %-48s ║%n", clienteDestino);
                System.out.printf("║ Resultado: NO HAY CONEXIÓN                              ║%n");
                break;
            case 0:
                System.out.printf("║ Cliente: %-43s ║%n", clienteOrigen);
                System.out.printf("║ Distancia a sí mismo: 0 saltos                          ║%n");
                break;
            default:
                System.out.printf("║ De: %-48s ║%n", clienteOrigen);
                System.out.printf("║ A:  %-48s ║%n", clienteDestino);
                System.out.printf("║ Distancia: %-2d saltos                                   ║%n", distancia);
                break;
        }
        
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Encuentra y muestra el camino más corto entre dos clientes
     * 
     * @param clienteOrigen Nombre del cliente origen
     * @param clienteDestino Nombre del cliente destino
     */
    public void mostrarCaminoMasCorto(String clienteOrigen, String clienteDestino) {
        if (clienteOrigen == null || clienteDestino == null) {
            System.out.println("Error: Los nombres de los clientes no pueden ser nulos.");
            return;
        }

        if (clienteOrigen.equals(clienteDestino)) {
            System.out.println("El cliente " + clienteOrigen + " está a 0 saltos de sí mismo.");
            return;
        }

        if (!clienteExiste(clienteOrigen) || !clienteExiste(clienteDestino)) {
            System.out.println("Error: Uno o ambos clientes no existen en el sistema.");
            return;
        }

        Queue<String> cola = new LinkedList<>();
        Map<String, String> predecesores = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        cola.offer(clienteOrigen);
        visitados.add(clienteOrigen);
        predecesores.put(clienteOrigen, null);

        boolean encontrado = false;

        while (!cola.isEmpty() && !encontrado) {
            String clienteActual = cola.poll();

            if (clienteActual.equals(clienteDestino)) {
                encontrado = true;
                break;
            }

            Cliente clienteObj = obtenerClientePorNombre(clienteActual);
            if (clienteObj == null) continue;

            Set<String> vecinos = new HashSet<>();
            vecinos.addAll(clienteObj.getSiguiendo());
            vecinos.addAll(clienteObj.getAmistades());

            for (String vecino : vecinos) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    predecesores.put(vecino, clienteActual);
                    cola.offer(vecino);
                }
            }
        }

        if (!encontrado) {
            System.out.println("No hay camino entre " + clienteOrigen + " y " + clienteDestino);
            return;
        }

        // Reconstruir camino
        List<String> camino = new ArrayList<>();
        String actual = clienteDestino;
        while (actual != null) {
            camino.add(0, actual);
            actual = predecesores.get(actual);
        }

        // Mostrar camino
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    CAMINO MÁS CORTO                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.printf("║ De: %-48s ║%n", clienteOrigen);
        System.out.printf("║ A:  %-48s ║%n", clienteDestino);
        System.out.printf("║ Distancia: %-2d saltos                                   ║%n", camino.size() - 1);
        System.out.println("║                                                          ║");
        System.out.println("║ Camino:                                                  ║");

        for (int i = 0; i < camino.size(); i++) {
            if (i == camino.size() - 1) {
                System.out.printf("║ %d. %-50s ║%n", i + 1, camino.get(i));
            } else {
                System.out.printf("║ %d. %-48s → ║%n", i + 1, camino.get(i));
            }
        }

        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }


}