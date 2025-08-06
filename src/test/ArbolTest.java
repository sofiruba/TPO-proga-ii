import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ArbolTest {

    @Test
    public void testInsertarYBuscar() {
        Arbol<Cliente> arbol = new Arbol<>();
        Cliente c1 = new Cliente("Valen", 100, 1);
        Cliente c2 = new Cliente("Luz", 80, 2);

        arbol.insertar(c1);
        arbol.insertar(c2);

        assertTrue(arbol.buscar(c1));
        assertTrue(arbol.buscar(c2));
        assertFalse(arbol.buscar(new Cliente("NoExiste", 50, 999)));
    }

    @Test
    public void testEstaVacio() {
        Arbol<Cliente> arbol = new Arbol<>();
        assertTrue(arbol.estaVacio());

        arbol.insertar(new Cliente("Valen", 100, 1));
        assertFalse(arbol.estaVacio());
    }


    @Test
    public void testImprimirEstructura() {
        Arbol<Cliente> arbol = new Arbol<>();
        Cliente c1 = new Cliente("Valen", 100, 1);
        Cliente c2 = new Cliente("Luz", 80, 2);
        Cliente c3 = new Cliente("Mati", 120, 3);

        arbol.insertar(c1);
        arbol.insertar(c2);
        arbol.insertar(c3);

        // Capturar salida
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        arbol.imprimirEstructura();

        String output = out.toString();
        assertTrue(output.contains("Valen"));
        assertTrue(output.contains("Luz"));
        assertTrue(output.contains("Mati"));

        // Restaurar salida estándar
        System.setOut(System.out);
    }
}
