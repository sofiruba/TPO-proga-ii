import java.util.LinkedList;
import java.util.List;

public class HistorialDeAcciones {
    private static class Nodo {
        Accion dato;
        Nodo siguiente;

        Nodo(Accion dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }
    private Nodo cima;
    private List<Accion> acciones;
    public HistorialDeAcciones() {
        this.cima = null;
        this.acciones = new LinkedList<>();
    }

    // metodos
    public void agregarAccion(Accion accion) {
        Nodo nuevaAccion = new Nodo(accion);
        if (this.cima != null) {
            nuevaAccion.siguiente = cima;
        }
        this.cima = nuevaAccion;
        acciones.add(0,accion);
    }

    public Accion eliminarAccion() throws Exception {
        if (this.cima == null) {
            throw new Exception("Pila vacia");
        }
        Accion eliminada = cima.dato;
        cima = cima.siguiente;
        if (!acciones.isEmpty()) {
            acciones.remove(0);
        }
        return eliminada;
    }

    public Accion getCima() throws Exception {
        if (cima == null) {
            throw new Exception("Pila vacia");
        }
        return cima.dato;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public List <Accion> getHistorialDeAcciones() {
        return acciones;
    }

    public void imprimirHistorial() {
        if (acciones.isEmpty()) {
            System.out.println("No hay acciones registradas.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                               HISTORIAL DE ACCIONES                               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        for (int i = 0; i < acciones.size(); i++) {
            Accion accion = acciones.get(i);
            System.out.printf("%n - Acción #%d:%n", i + 1);
            accion.imprimirDetallado();
        }
    }

    public void imprimirUltimasAcciones(int cantidad) {
        if (acciones.isEmpty()) {
            System.out.println("No hay acciones registradas.");
            return;
        }

        int limite = Math.min(cantidad, acciones.size());
        System.out.printf("%n ÚLTIMAS %d ACCIONES:%n", limite);
        System.out.println("═══════════════════════════");

        for (int i = 0; i < limite; i++) {
            System.out.printf("%d. %s%n", i + 1, acciones.get(i).toString());
        }
    }
}
