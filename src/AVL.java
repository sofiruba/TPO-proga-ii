import java.util.ArrayList;
import java.util.List;

public class AVL<T extends Comparable<T>> {

    private Arbol<T> arbol;

    public AVL() {
        arbol = new Arbol<>();
    }

    public void agregar(T elemento) {
        arbol.setRaiz(agregarRecursivo(arbol.getRaiz(), elemento));
    }

    public boolean buscar(T elemento) {
        return arbol.buscar(elemento);

    }

    public List<T> buscarTodosConScoring(int scoring) {
        List<T> resultado = new ArrayList<>();
        buscarTodosRecursivo(arbol.getRaiz(), scoring, resultado);
        return resultado;
    }

    public List<T> buscarEnRango(int min, int max) {
        List<T> resultado = new ArrayList<>();
        buscarEnRangoRecursivo(arbol.getRaiz(), min, max, resultado);
        return resultado;
    }

    private void buscarEnRangoRecursivo(Nodo<T> nodo, int min, int max, List<T> resultado) {
        if (nodo == null)
            return;

        Cliente cliente = (Cliente) nodo.getDato(); // Cast explícito
        int scoring = cliente.getScoring();

        if (scoring > min)
            buscarEnRangoRecursivo(nodo.getIzquierdo(), min, max, resultado);
        if (scoring >= min && scoring <= max)
            resultado.add(nodo.getDato());
        if (scoring < max)
            buscarEnRangoRecursivo(nodo.getDerecho(), min, max, resultado);
    }

    
    @SuppressWarnings("unchecked")
    private void buscarTodosRecursivo(Nodo<T> nodo, int scoring, List<T> resultado) {
        if (nodo == null)
            return;

        Cliente cliente = (Cliente) nodo.getDato(); // Cast seguro si T es Cliente

        if (cliente.getScoring() == scoring) {
            resultado.add(nodo.getDato());
        }

        if (cliente.getScoring() >= scoring) {
            buscarTodosRecursivo(nodo.getIzquierdo(), scoring, resultado);
        }
        if (cliente.getScoring() <= scoring) {
            buscarTodosRecursivo(nodo.getDerecho(), scoring, resultado);
        }
    }

    private Nodo<T> agregarRecursivo(Nodo<T> nodo, T elemento) {
        if (nodo == null) {
            return new Nodo<>(elemento);
        }

        int cmp = elemento.compareTo(nodo.getDato());
        if (cmp < 0) {
            nodo.setIzquierdo(agregarRecursivo(nodo.getIzquierdo(), elemento));
        } else if (cmp > 0) {
            nodo.setDerecho(agregarRecursivo(nodo.getDerecho(), elemento));
        } else {
            // Elemento duplicado, no se agrega
            return nodo;
        }

        // Actualizar altura
        nodo.setAltura(1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho())));

        // Balancear el nodo
        int balance = getBalance(nodo);

        // Rotaciones
        // Izquierda Izquierda
        if (balance > 1 && elemento.compareTo(nodo.getIzquierdo().getDato()) < 0) {
            return rotarDerecha(nodo);
        }
        // Derecha Derecha
        if (balance < -1 && elemento.compareTo(nodo.getDerecho().getDato()) > 0) {
            return rotarIzquierda(nodo);
        }
        // Izquierda Derecha
        if (balance > 1 && elemento.compareTo(nodo.getIzquierdo().getDato()) > 0) {
            nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            return rotarDerecha(nodo);
        }
        // Derecha Izquierda
        if (balance < -1 && elemento.compareTo(nodo.getDerecho().getDato()) < 0) {
            nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    // Métodos auxiliares para AVL

    private int altura(Nodo<T> nodo) {
        return (nodo == null) ? 0 : nodo.getAltura();
    }

    private int getBalance(Nodo<T> nodo) {
        return (nodo == null) ? 0 : altura(nodo.getIzquierdo()) - altura(nodo.getDerecho());
    }
    public List<T> buscarConScoringMayorA(int valor) {
        List<T> resultado = new ArrayList<>();
        buscarMayoresRecursivo(arbol.getRaiz(), valor, resultado);
        return resultado;
    }

    @SuppressWarnings("unchecked")
    private void buscarMayoresRecursivo(Nodo<T> nodo, int valor, List<T> resultado) {
        if (nodo == null) return;

        Cliente cliente = (Cliente) nodo.getDato();
        int scoring = cliente.getScoring();

        if (scoring > valor) {
            resultado.add(nodo.getDato());
            buscarMayoresRecursivo(nodo.getIzquierdo(), valor, resultado); // por si hay más chicos pero válidos
            buscarMayoresRecursivo(nodo.getDerecho(), valor, resultado);
        } else {
            buscarMayoresRecursivo(nodo.getDerecho(), valor, resultado); // solo rama derecha puede contener mayores
        }
    }


    private Nodo<T> rotarDerecha(Nodo<T> y) {
        Nodo<T> x = y.getIzquierdo();
        Nodo<T> T2 = x.getDerecho();

        x.setDerecho(y);
        y.setIzquierdo(T2);

        y.setAltura(1 + Math.max(altura(y.getIzquierdo()), altura(y.getDerecho())));
        x.setAltura(1 + Math.max(altura(x.getIzquierdo()), altura(x.getDerecho())));

        return x;
    }

    private Nodo<T> rotarIzquierda(Nodo<T> x) {
        Nodo<T> y = x.getDerecho();
        Nodo<T> T2 = y.getIzquierdo();

        y.setIzquierdo(x);
        x.setDerecho(T2);

        x.setAltura(1 + Math.max(altura(x.getIzquierdo()), altura(x.getDerecho())));
        y.setAltura(1 + Math.max(altura(y.getIzquierdo()), altura(y.getDerecho())));

        return y;
    }

    public void imprimirPorNivel(int nivel) {
        if (arbol.estaVacio()) {
            System.out.println("El árbol está vacío.");
            return;
        }
        arbol.imprimirPorNivel(nivel);
    }

    public void imprimirMayor() {
        if (arbol.estaVacio()) {
            System.out.println("El árbol está vacío.");
            return;
        }
        System.out.println(imprimirMayorRecursivo(arbol.getRaiz()).toString());
    }

    private Nodo<T> imprimirMayorRecursivo(Nodo<T> raiz) {
        if (raiz.getDerecho() != null) {
            return imprimirMayorRecursivo(raiz.getDerecho());
        }
        return raiz; // Retorna el nodo con el valor máximo
    }
    public void imprimirAVL() {
        arbol.imprimirEstructura();
    }


}