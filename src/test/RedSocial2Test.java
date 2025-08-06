
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class RedSocial2Test {

    private RedSocial2 red;

    private Cliente crearCliente(String nombre, int scoring, int id) {
        return new Cliente(nombre, scoring, id);
    }

    @BeforeEach
    public void setup() {
        red = new RedSocial2();
    }

    @Test
    public void testAgregarCliente() {
        red.agregarCliente("Lucia", 8);
        Cliente c = red.getCliente("Lucia");
        assertNotNull(c);
        assertEquals("Lucia", c.getNombre());
    }

    @Test
    public void testBuscarClientesMayoresA() {
        red.agregarCliente("Carlos", 5);
        red.agregarCliente("Mica", 9);
        List<Cliente> resultado = red.buscarClientesMayoresA(6);
        assertTrue(resultado.stream().anyMatch(c -> c.getNombre().equals("Mica")));
        assertTrue(resultado.stream().noneMatch(c -> c.getNombre().equals("Carlos")));
    }

    @Test
    public void testBuscarPorScoring() {
        red.agregarCliente("Nacho", 7);
        List<Cliente> resultado = red.buscarPorScoring(7);
        assertEquals(1, resultado.size());
        assertEquals("Nacho", resultado.get(0).getNombre());
    }

    @Test
    public void testBuscarEnRango() {
        red.agregarCliente("Ari", 1);
        red.agregarCliente("Luna", 3);
        red.agregarCliente("Nico", 9);
        List<Cliente> resultado = red.buscarEnRango(3, 4);
        assertTrue(resultado.stream().anyMatch(c -> c.getNombre().equals("Luna")));
        assertEquals(1, resultado.size());
    }

    @Test
    public void testEnviarYProcesarSolicitud() {
        red.agregarCliente("Ana", 7);
        red.agregarCliente("Nati", 6);

        red.enviarSolicitud("Ana", "Nati");
        assertEquals(1, red.colaDeSeguimientos.getSolicitudes().size());

        red.procesarSolicitud();

        assertTrue(red.getGestorClientes2().g.estaSiguiendo("Ana", "Nati"));
    }



}

