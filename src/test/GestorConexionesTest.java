import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GestorConexionesTest {

    private GestorConexiones grafo;

    @BeforeEach
    public void setup() {
        grafo = new GestorConexiones();

        // Crear vértices 1, 2, 3
        grafo.adicionarVertice(1);
        grafo.adicionarVertice(2);
        grafo.adicionarVertice(3);

        // Agregar amistades mutuas
        grafo.adicionarAristaBidireccional(1, 2);
        grafo.adicionarAristaBidireccional(1, 3);
    }

    @Test
    public void testVerticesExistentes() {
        assertTrue(grafo.contieneVertice(1));
        assertTrue(grafo.contieneVertice(2));
        assertTrue(grafo.contieneVertice(3));
        assertFalse(grafo.contieneVertice(4));
    }

    @Test
    public void testAristasBidireccionales() {
        Set<Integer> vecinos1 = grafo.getVecinos(1);
        assertTrue(vecinos1.contains(2));
        assertTrue(vecinos1.contains(3));

        Set<Integer> vecinos2 = grafo.getVecinos(2);
        assertTrue(vecinos2.contains(1));

        Set<Integer> vecinos3 = grafo.getVecinos(3);
        assertTrue(vecinos3.contains(1));
    }

    @Test
    public void testCantidadVecinos() {
        assertEquals(2, grafo.cantidadVecinos(1));
        assertEquals(1, grafo.cantidadVecinos(2));
        assertEquals(1, grafo.cantidadVecinos(3));
    }

    @Test
    public void testEliminarArista() {
        grafo.eliminarArista(1, 2); // solo unidireccional
        assertFalse(grafo.getVecinos(1).contains(2));
        assertTrue(grafo.getVecinos(2).contains(1)); // sigue estando

        // eliminar la otra parte
        grafo.eliminarArista(2, 1);
        assertFalse(grafo.getVecinos(2).contains(1));
    }
}
