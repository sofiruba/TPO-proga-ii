import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RedSocialTest {

    // Helper para crear clientes
    private Cliente crearCliente(String nombre, int scoring, int id) {
        return new Cliente(nombre, scoring, id);
    }

    @Test
    public void test_AddClientes() {
        RedSocial gc = new RedSocial();
        gc.agregarCliente(crearCliente("Ana", 7, 1));
        assertTrue(gc.getClientes().clienteExiste("Ana"));
    }

    @Test
    public void test_enviarSolicitud() {
        RedSocial gc = new RedSocial();
        gc.agregarCliente(crearCliente("Ana", 7, 2));
        gc.agregarCliente(crearCliente("Nati", 6, 3));

        gc.enviarSolicitud("Ana", "Nati");
        assertEquals(1, gc.getSolicitudesLista().size());
    }

    @Test
    public void test_DeshacerAgregarCliente() throws Exception {
        RedSocial gc = new RedSocial();
        gc.agregarCliente(crearCliente("Ana", 7, 3));
        assertTrue(gc.getClientes().clienteExiste("Ana"));

        gc.eliminarAccion();
        assertFalse(gc.getClientes().clienteExiste("Ana"));
    }

    @Test
    public void test_DeshacerAgregarSeguidor() throws Exception {
        RedSocial gc = new RedSocial();
        gc.agregarCliente(crearCliente("Ana", 7, 4));
        gc.agregarCliente(crearCliente("Nati", 5, 5));

        gc.enviarSolicitud("Ana", "Nati");
        gc.procesarSolicitud();

        assertTrue(gc.getClientes().estaSiguiendo("Ana", "Nati"));

        gc.eliminarAccion();
        assertFalse(gc.getClientes().estaSiguiendo("Ana", "Nati"));
    }

    @Test
    public void test_DeshacerEliminarSeguidor() throws Exception {
        RedSocial gc = new RedSocial();
        gc.agregarCliente(crearCliente("Ana", 7, 6));
        gc.agregarCliente(crearCliente("Mateo", 5, 7));

        gc.enviarSolicitud("Ana", "Mateo");
        gc.procesarSolicitud();
        gc.eliminarSeguidor("Ana", "Mateo");

        assertFalse(gc.getClientes().estaSiguiendo("Ana", "Mateo"));

        gc.eliminarAccion();
        assertTrue(gc.getClientes().estaSiguiendo("Ana", "Mateo"));
    }

    @Test
    public void test_procesarSolicitud() {
        RedSocial gc = new RedSocial();
        gc.agregarCliente(crearCliente("Ana", 7, 8));
        gc.agregarCliente(crearCliente("Nati", 5, 9));

        gc.enviarSolicitud("Ana", "Nati");
        gc.procesarSolicitud();

        assertEquals(0, gc.getSolicitudesLista().size());
        assertTrue(gc.getClientes().estaSiguiendo("Ana", "Nati"));
    }

    @Test
    public void test_deshacerUltimaAccion() throws Exception {
        RedSocial gc = new RedSocial();
        gc.agregarCliente(crearCliente("Ana", 7, 9));
        gc.eliminarAccion();
        assertEquals(0, gc.getHistorialDeAccionesLista().size());
    }


    @Test
    public void test_Solicitud_Pendiente_SiPrivado() {
        RedSocial gc = new RedSocial();
        gc.agregarCliente(crearCliente("Ana", 8, 12));
        gc.agregarCliente(crearCliente("Nati", 7, 13));

        gc.cambiarPrivacidad("Nati", true);

        gc.enviarSolicitud("Ana", "Nati");
        gc.procesarSolicitud();

        assertFalse(gc.getClientes().estaSiguiendo("Ana", "Nati"));
        assertEquals(0, gc.getSolicitudesLista().size()); // se va a solicitudesPendientes internas
    }

}
