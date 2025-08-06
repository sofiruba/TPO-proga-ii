import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AVLTest {

    @Test
    public void testAgregarYBuscar() {
        AVL<Cliente> avl = new AVL<>();

        // Crear cliente
        Cliente cliente = new Cliente("Valen", 10, 1);

        // Agregar al árbol
        avl.agregar(cliente);

        // Buscar el MISMO objeto (esto sí va a funcionar)
        assertTrue(avl.buscar(cliente));
    }

    @Test
    public void testBuscarTodosConScoring() {
        AVL<Cliente> avl = new AVL<>();
        Cliente c1 = new Cliente("Valen", 10, 1);
        Cliente c2 = new Cliente("Luz", 10, 2);
        Cliente c3 = new Cliente("Mati", 5, 3);

        avl.agregar(c1);
        avl.agregar(c2);
        avl.agregar(c3);

        assertEquals(2, avl.buscarTodosConScoring(10).size());
    }

    @Test
    public void testBuscarEnRango() {
        AVL<Cliente> avl = new AVL<>();
        avl.agregar(new Cliente("Valen", 15, 1));
        avl.agregar(new Cliente("Luz", 10, 2));
        avl.agregar(new Cliente("Mati", 20, 3));

        assertEquals(3, avl.buscarEnRango(10, 20).size());
        assertEquals(2, avl.buscarEnRango(10, 15).size());
    }

    @Test
    public void testBuscarConScoringMayorA() {
        AVL<Cliente> avl = new AVL<>();
        avl.agregar(new Cliente("Valen", 15, 1));
        avl.agregar(new Cliente("Luz", 10, 2));
        avl.agregar(new Cliente("Mati", 20, 3));

        assertEquals(2, avl.buscarConScoringMayorA(10).size());
    }
}
