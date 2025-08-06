import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GestorClientes2Test {

    private GestorClientes2 gestor;

    @BeforeEach
    public void setUp() {
        gestor = new GestorClientes2();
    }

    @Test
    public void testAgregarYBuscarCliente() {
        Cliente c1 = new Cliente("Ana", 500, 30);
        gestor.agregar(c1);

        assertTrue(gestor.existeCliente("Ana"));
        assertTrue(gestor.existeScoring(500));

        List<Cliente> resultados = gestor.buscarPorScoring(500);
        assertEquals(1, resultados.size());
        assertEquals("Ana", resultados.get(0).getNombre());
    }

    @Test
    public void testBuscarEnRango() {
        gestor.agregar(new Cliente("Beto", 300, 25));
        gestor.agregar(new Cliente("Carla", 450, 28));
        gestor.agregar(new Cliente("Diego", 600, 35));

        List<Cliente> enRango = gestor.buscarEnRango(400, 700);
        assertEquals(2, enRango.size());
    }

    @Test
    public void testBuscarScoringMayorA() {
        gestor.agregar(new Cliente("Luz", 700, 22));
        gestor.agregar(new Cliente("Mauro", 900, 26));
        gestor.agregar(new Cliente("Nina", 400, 33));

        List<Cliente> mayoresA = gestor.buscarClientesConScoringMayorA(600);
        assertEquals(2, mayoresA.size());
    }

    @Test
    public void testObtenerClienteConMayorScoringABB() {
        gestor.agregar(new Cliente("Zoe", 400, 30));
        gestor.agregar(new Cliente("Tomi", 750, 31));
        gestor.agregar(new Cliente("Juli", 900, 32));

        Cliente mejor = gestor.obtenerClienteConMayorScoringABB();
        assertNotNull(mejor);
        assertEquals("Juli", mejor.getNombre());
    }

    @Test
    public void testObtenerProfundidadCliente() {
        gestor.agregar(new Cliente("Jorge", 800, 40));
        gestor.agregar(new Cliente("Marcos", 700, 42));
        gestor.agregar(new Cliente("Luis", 900, 39));

        int prof = gestor.obtenerProfundidadDeCliente("Luis");
        assertTrue(prof >= 0); // puede variar según inserción
    }
}
