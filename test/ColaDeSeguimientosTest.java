import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColaDeSeguimientosTest {
    @Test
    public void test_CrearCola() {
        ColaDeSeguimientos cola = new ColaDeSeguimientos();
        assertNotNull(cola);
    }
    @Test
    public void test_AgregarSeguimiento() {
        ColaDeSeguimientos cola = new ColaDeSeguimientos();
        Seguimiento seguimiento = new Seguimiento("Cliente1", "Seguimiento1");
        cola.agregarSolicitud(seguimiento);
        assertEquals(1, cola.getSolicitudes().size());
    }
    @Test
    public void procesarProxima() {
        ColaDeSeguimientos cola = new ColaDeSeguimientos();
        Seguimiento seguimiento1 = new Seguimiento("Cliente1", "Seguimiento1");
        Seguimiento seguimiento2 = new Seguimiento("Cliente2", "Seguimiento2");
        cola.agregarSolicitud(seguimiento1);
        cola.agregarSolicitud(seguimiento2);
        cola.procesarProxima();
        assertEquals(1, cola.getSolicitudes().size());
    }
}
