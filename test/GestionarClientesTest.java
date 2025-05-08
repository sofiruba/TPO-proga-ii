import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GestionarClientesTest {
    @Test
    public void test_AddClientes() {
        GestionarClientes gc = new GestionarClientes();
        gc.agregarCliente(new Cliente("Ana", 7, new HashSet<>(), new HashSet<>()));
        assertEquals(1, gc.getClientes().size());
    }
    @Test
    public void test_enviarSolicitud() {
        ArrayList<Cliente> lista = new ArrayList<>();
        lista.add(new Cliente("Ana", 7, new HashSet<>(), new HashSet<>()));
        GestionarClientes gc = new GestionarClientes();
        gc.enviarSolicitud("Ana", "Pedro");
        assertEquals(1, gc.getSolicitudes().size());
    }
    @Test
    public void test_procesarSolicitud() {
        ArrayList<Cliente> lista = new ArrayList<>();
        lista.add(new Cliente("Ana", 7, new HashSet<>(), new HashSet<>()));
        GestionarClientes gc = new GestionarClientes();
        gc.enviarSolicitud("Ana", "Pedro");
        gc.procesarSolicitud();
        assertEquals(0, gc.getSolicitudes().size());
    }
    @Test
    public void test_deshacerUltimaAccion() throws Exception {
        ArrayList<Cliente> lista = new ArrayList<>();
        lista.add(new Cliente("Ana", 7, new HashSet<>(), new HashSet<>()));
        GestionarClientes gc = new GestionarClientes();
        gc.agregarCliente(new Cliente("Ana", 7, new HashSet<>(), new HashSet<>()));
        gc.deshacerUltimaAccion();
        assertEquals(0, gc.getHistorialDeAcciones().size());
    }
    
}
