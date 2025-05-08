import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HistorialDeAccionesTest {
    @Test
    public void test_CrearHistorial() {
        HistorialDeAcciones hist = new HistorialDeAcciones();
        assertNotNull(hist);
    }
    
    @Test
    public void test_AgregarAccion() throws Exception {
        HistorialDeAcciones hist = new HistorialDeAcciones();
        Accion accion = new Accion("LOGIN");
        hist.agregarAccion(accion);
        assertEquals(1, hist.getHistorialDeAcciones().size());
    }
    @Test
    public void test_EliminarAccion() throws Exception {
        HistorialDeAcciones hist = new HistorialDeAcciones();
        Accion accion = new Accion("LOGIN");
        hist.agregarAccion(accion);
        Accion eliminada = hist.eliminarAccion();
        assertEquals(0, hist.getHistorialDeAcciones().size());
        assertEquals(accion, eliminada);
    }

}
