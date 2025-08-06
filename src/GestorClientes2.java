import java.util.*;


public class GestorClientes2 {
    GestorClientes g;
     AVL<Cliente> clientesPorScoring = new AVL<>();
     Arbol<Cliente> jerarquiaClientes = new Arbol<>();
     ABB<Cliente> abbPorScoring = new ABB<Cliente>();


    public GestorClientes2() {
        this.g = new GestorClientes();
        this.clientesPorScoring = new AVL<>();
        this.jerarquiaClientes = new Arbol<>();
        this.g.grafo = new GestorConexiones();
        this.g.contadorId = 0;
        this.abbPorScoring = new ABB<Cliente>();
    }

    // Imprimir arbol de jerarquia
    public void imprimirJerarquiaCompleta() {
        jerarquiaClientes.imprimirEstructura();
    }

    //Imprimir arbol de scoring
    public void imprimirAVLCompleto() {
        clientesPorScoring.imprimirAVL();
    }

    // Función auxiliar para ABB
    private static List<Cliente> buscarPorScoringABB(Nodo<Cliente> nodo, int scoring) {
        List<Cliente> resultado = new ArrayList<>();
        buscarABBRec(nodo, scoring, resultado);
        return resultado;
    }

    private static void buscarABBRec(Nodo<Cliente> nodo, int scoring, List<Cliente> lista) {
        if (nodo == null) return;
        int actual = nodo.getDato().getScoring();
        if (scoring == actual) lista.add(nodo.getDato());
        if (scoring <= actual) buscarABBRec(nodo.getIzquierdo(), scoring, lista);
        if (scoring >= actual) buscarABBRec(nodo.getDerecho(), scoring, lista);
    }



    //Agregar cliente

    public void agregar(Cliente cliente) {
        if (cliente == null || cliente.getNombre() == null || cliente.getScoring() < 0) {
            System.out.println("Cliente inválido.");
            return;
        }
        if (g.clientesMap.containsKey(cliente.getNombre())) {
            System.out.println("El cliente ya existe.");
            return;
        }

        g.agregar(cliente);
        cliente.setIdGrafo(g.contadorId++);
        g.clientesMap.put(cliente.getNombre(), cliente);
        clientesPorScoring.agregar(cliente);
        jerarquiaClientes.insertar(cliente);
        g.grafo.adicionarVertice(cliente.getIdGrafo());
        abbPorScoring.insertar(cliente);
    }
 //ABB
    public List<Cliente> buscarPorScoringABB(int scoring) {
        List<Cliente> resultado = new ArrayList<>();
        buscarPorScoringRec(abbPorScoring.getRaiz(), scoring, resultado);
        return resultado;
    }

    private void buscarPorScoringRec(Nodo<Cliente> nodo, int s, List<Cliente> lista) {
        if (nodo == null) return;

        int actual = nodo.getDato().getScoring();
        if (s == actual) lista.add(nodo.getDato());

        buscarPorScoringRec(nodo.getIzquierdo(), s, lista);
        buscarPorScoringRec(nodo.getDerecho(), s, lista);
    }
    public Cliente obtenerClienteConMayorScoringABB() {
        Nodo<Cliente> nodo = abbPorScoring.getRaiz();
        if (nodo == null) return null;

        while (nodo.getDerecho() != null) {
            nodo = nodo.getDerecho(); // el mayor scoring está al fondo del nodo derecho
        }

        return nodo.getDato();
    }
    public List<Cliente> buscarClientesEnRangoABB(int min, int max) {
        List<Cliente> resultado = new ArrayList<>();
        buscarEnRangoRec(abbPorScoring.getRaiz(), min, max, resultado);
        return resultado;
    }

    private void buscarEnRangoRec(Nodo<Cliente> nodo, int min, int max, List<Cliente> resultado) {
        if (nodo == null) return;

        int scoring = nodo.getDato().getScoring();

        if (scoring > min)
            buscarEnRangoRec(nodo.getIzquierdo(), min, max, resultado);

        if (scoring >= min && scoring <= max)
            resultado.add(nodo.getDato());

        if (scoring < max)
            buscarEnRangoRec(nodo.getDerecho(), min, max, resultado);
    }


    // Obtener clientes por nivel jerárquico
    public void obtenerClientesPorNivel(int nivel) {
        if (nivel < 0) {
            System.out.println("Nivel inválido. Debe ser mayor o igual a 0.");
            return;
        }
        this.jerarquiaClientes.imprimirPorNivel(nivel);
    }


    //  Obtener clientes con scoring exacto
    public List<Cliente> obtenerClientesPorScoringArbol(int scoring) {
        if (scoring < 0) {
            System.out.println("Scoring inválido.");
            return new ArrayList<>();
        }
        return this.clientesPorScoring.buscarTodosConScoring(scoring);
    }
    public List<Cliente> buscarClientesConScoringMayorA(int valor) {
        return clientesPorScoring.buscarConScoringMayorA(valor);
    }


    // Obtener clientes en un rango de scoring
    public List<Cliente> obtenerClientesEnRango(int min, int max) {
        return this.clientesPorScoring.buscarEnRango(min, max);
    }

    // Obtener clientes con scoring mayor a un valor
    public List<Cliente> obtenerClientesConScoringMayorA(int valor) {
        return this.clientesPorScoring.buscarEnRango(valor + 1, Integer.MAX_VALUE);
    }

    public List<Cliente> obtenerClientesConMasConexiones() {
        List<Cliente> resultado = new ArrayList<>();
        int maxConexiones = -1;

        for (Cliente c : g.clientesMap.values()) {
            int id = c.getIdgrafo();
            Set<Integer> vecinos = g.grafo.getConexiones(id);
            int cantidad = (vecinos != null) ? vecinos.size() : 0;

            if (cantidad > maxConexiones) {
                // Nuevo máximo → reinicio la lista
                maxConexiones = cantidad;
                resultado.clear();
                resultado.add(c);
            } else if (cantidad == maxConexiones) {
                // Iguala al máximo actual → se suma
                resultado.add(c);
            }
        }

        return resultado;
    }


    //Buscar en AVL x scoring
    public List<Cliente> buscarPorScoring(int scoring) {
        return clientesPorScoring.buscarTodosConScoring(scoring);
    }
    // buscar en AVL x rango
    public List<Cliente> buscarEnRango(int min, int max) {
        return clientesPorScoring.buscarEnRango(min, max);
    }

    public static void validarOpcion(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Opción inválida. Ingrese un número: ");
            sc.next(); // descarta entrada inválida
        }
    }
    public static int leerEntero(String mensaje, Scanner sc) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Valor inválido. Ingrese un número válido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine(); // limpiar buffer
        return valor;
    }

    public static String leerCadenaNoVacia(String mensaje, Scanner sc) {
        String input;
        do {
            System.out.print(mensaje);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("El campo no puede estar vacío.");
            }
        } while (input.isEmpty());
        return input;
    }
    public static int[] leerRangoScoring(Scanner sc) {
        int min = leerEntero("Scoring mínimo: ", sc);
        int max = leerEntero("Scoring máximo: ", sc);

        if (min > max) {
            System.out.println("Ingresaste el mínimo mayor que el máximo. Se intercambian los valores automáticamente.");
            int temp = min;
            min = max;
            max = temp;
        }

        return new int[] { min, max };
    }
    public static boolean esNombreValido(String nombre) {
        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }

    //Profundidad en arbol
    public int obtenerProfundidadDeCliente(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return -1;
        }
        return jerarquiaClientes.obtenerProfundidad(nombre);
    }



    public boolean existeCliente(String nombre) {
        for (String clave : g.clientesMap.keySet()) {
            if (clave.equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }
    public boolean existeScoring(int scoring) {
        List<Cliente> clientesConEseScoring = clientesPorScoring.buscarTodosConScoring(scoring);
        return clientesConEseScoring != null && !clientesConEseScoring.isEmpty();
    }
    public void imprimirABBCompleto() {
        System.out.println("Clientes ordenados en el ABB por scoring:");
        List<Cliente> lista = abbPorScoring.inOrden(); // método que recorre el árbol inorden
        for (Cliente c : lista) {
            System.out.println("- " + c.getNombre() + " (Scoring: " + c.getScoring() + ")");
        }
    }



}
