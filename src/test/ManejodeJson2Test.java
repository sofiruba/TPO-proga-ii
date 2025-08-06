import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ManejodeJson2Test {

    private GestorClientes2 gestor;
    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        gestor = new GestorClientes2();

        // Crear archivo JSON temporal
        String json = "[{\"nombre\":\"Ana\",\"scoring\":8,\"id\":1}]";
        tempFile = Files.createTempFile("clientes_test", ".json");
        Files.write(tempFile, json.getBytes());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void testCargarDesdeJson_ClienteValido() {
        ManejodeJson2 manejador = new ManejodeJson2(gestor, tempFile.toString());

        manejador.cargarDesdeJson();

        // Verifica que se haya agregado el cliente
        Cliente cliente = gestor.g.obtenerClientePorNombre("Ana");
        assertNotNull(cliente);
        assertEquals("Ana", cliente.getNombre());
        assertEquals(8, cliente.getScoring());
    }
}

