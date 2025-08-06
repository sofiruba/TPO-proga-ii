import java.util.*;

public class GestorConexiones {
    private HashMap<Integer, Set<Integer>> grafo;

    public GestorConexiones() {
        grafo = new HashMap<>();
    }

    public boolean contieneVertice(int vertice) {
        return grafo.containsKey(vertice);
    }

    public void adicionarVertice(int vertice) {
        if (!contieneVertice(vertice)) {
            grafo.putIfAbsent(vertice, new HashSet<>());
        }
    }

    public Set<Integer> getConexiones(int vertice) {
        if (!grafo.containsKey(vertice)) {
            return Collections.emptySet();
        }
        // Devolvemos copia para evitar modificaciones externas
        return new HashSet<>(grafo.get(vertice));
    }

    public int obtenerClienteConMasConexiones() {
    int maxConexiones = -1;
    int idCliente = -1;
    for (int id : grafo.keySet()) {
        int conexiones = grafo.get(id).size();
        if (conexiones > maxConexiones) {
            maxConexiones = conexiones;
            idCliente = id;
        }
    }
    return idCliente;
}


    public void adicionarArista(int origen, int destino) {
        if (grafo.containsKey(origen) && grafo.containsKey(destino)) {
            grafo.get(origen).add(destino); // HashSet evita duplicados
        }
    }

    // Método para agregar arista bidireccional (para amistades)
    public void adicionarAristaBidireccional(int origen, int destino) {
        if (grafo.containsKey(origen) && grafo.containsKey(destino)) {
            grafo.get(origen).add(destino);
            grafo.get(destino).add(origen);
        }
    }

    public void imprimirGrafo() {
        for (Integer vertice : grafo.keySet()) {
            System.out.print("Vertice " + vertice + " -> ");
            Set<Integer> destinos = grafo.get(vertice);
            for (Integer destino : destinos) {
                System.out.print(destino + " ");
            }
            System.out.println();
        }
    }

    public void bfsCamino(int origen, int destino) {
        if (!grafo.containsKey(origen) || !grafo.containsKey(destino)) {
            System.out.println("Origen o destino no existen en el grafo.");
            return;
        }
        HashMap<Integer, Integer> predecesor = new HashMap<>();
        ArrayList<Integer> cola = new ArrayList<>();
        HashMap<Integer, Boolean> visitado = new HashMap<>();
        for (Integer v : grafo.keySet()) {
            visitado.put(v, false);
        }
        cola.add(origen);
        visitado.put(origen, true);
        boolean encontrado = false;
        while (!cola.isEmpty()) {
            int actual = cola.remove(0);
            if (actual == destino) {
                encontrado = true;
                break;
            }
            for (Integer vecino : grafo.get(actual)) {
                if (!visitado.get(vecino)) {
                    visitado.put(vecino, true);
                    predecesor.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }
        if (!encontrado) {
            System.out.println("No existe camino entre " + origen + " y " + destino);
            return;
        }
        ArrayList<Integer> camino = new ArrayList<>();
        int paso = destino;
        while (paso != origen) {
            camino.add(0, paso);
            paso = predecesor.get(paso);
        }
        camino.add(0, origen);
        System.out.print("Camino BFS: ");
        for (int v : camino) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

    public int cantidadVecinos(int vertice) {
        if (!grafo.containsKey(vertice))
            return 0;
        return grafo.get(vertice).size();
    }

    public Set<Integer> getVecinos(int vertice) {
        if (!grafo.containsKey(vertice))
            return new HashSet<>();
        return new HashSet<>(grafo.get(vertice));
    }

    public void eliminarArista(int origen, int destino) {
        if (grafo.containsKey(origen)) {
            grafo.get(origen).remove(destino);
        }
    }

    // Método para debug - ver el estado actual del grafo
    public void mostrarEstadoGrafo() {
        System.out.println("\n=== ESTADO DEL GRAFO ===");
        if (grafo.isEmpty()) {
            System.out.println("El grafo está vacío.");
        } else {
            imprimirGrafo();
        }
        System.out.println("========================\n");
    }



}
