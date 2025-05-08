import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManejodeJsonTest {
    @Test
    public void test_CrearManejo() {
        ManejodeJson mj = new ManejodeJson();
        assertNotNull(mj.getListaClientes());
    }
}
