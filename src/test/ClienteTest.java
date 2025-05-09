import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;

public class ClienteTest {
    @Test
    public void test_ClienteCreation() {
        Cliente c = new Cliente("Juan", 5, new HashSet<>(), new HashSet<>());
        assertEquals("Juan", c.getNombre());
        assertEquals(5, c.getScoring());
    }
    @Test
    public void test_toStringNotNull() {
        Cliente c = new Cliente("Juan", 5, new HashSet<>(), new HashSet<>());
        assertNotNull(c.toString());
    }
}
