import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class AccionTest {
    @Test
    public void test_getTipo() {
        Accion accion = new Accion("LOGIN");
        assertEquals("LOGIN", accion.getTipo());
    }
    @Test
    public void test_getDetalles() {
        Accion accion = new Accion("LOGIN", "detalle");
        assertEquals("detalle", accion.getDetalles());
    }
    @Test
    public void test_getFechayhora() {
        LocalDateTime ahora = LocalDateTime.now();
        Accion accion = new Accion("LOGIN");
        assertEquals(ahora, accion.getFechayhora());
    }
}
