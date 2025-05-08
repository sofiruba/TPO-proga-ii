import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SeguimientoTest {
    @Test
    public void test_CrearSeguimiento() {
        String origen = "test";
        String destino = "test";
        Seguimiento seg = new Seguimiento(origen, destino);
        assertNotNull(seg);
        assertEquals(origen, seg.getOrigen());
        assertEquals(destino, seg.getDestino());
        
    }
}
