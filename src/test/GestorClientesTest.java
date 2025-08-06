import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

public class GestorClientesTest {
    @Test
    public void test_AgregarYObtener() {
        GestorClientes lista = new GestorClientes();
        Cliente c = new Cliente("Pedro", 6, 1);
        lista.agregar(c);
        assertEquals(c, lista.obtener(0));
    }
    @Test
    public void test_Eliminar() {
        GestorClientes lista = new GestorClientes();
        Cliente c1 = new Cliente("Pedro", 6, 2);
        Cliente c2 = new Cliente("Ana", 7, 3);
        lista.agregar(c1);
        lista.agregar(c2);
        lista.eliminarClientePorIndice(0);
        assertEquals(c2, lista.obtener(0));
        lista.eliminarClientePorNombre("Ana");
        assertEquals(0, lista.getClientes().size());
    }
    @Test
    public void obtenerPorNombre() {
        GestorClientes lista = new GestorClientes();
        Cliente c1 = new Cliente("Pedro", 6, 4);
        Cliente c2 = new Cliente("Ana", 7, 1);
        lista.agregar(c1);
        lista.agregar(c2);
        assertEquals(c1, lista.obtenerClientePorNombre("Pedro"));
    }
    @Test
    public void obtenerPorScoring() {
        GestorClientes lista = new GestorClientes();
        Cliente c1 = new Cliente("Pedro", 7, 2);
        Cliente c2 = new Cliente("Ana", 6,3);
        lista.agregar(c1);
        lista.agregar(c2);
        ArrayList<Cliente> encontrados = new ArrayList<>();
        encontrados.add(c1);
        assertEquals(encontrados, lista.obtenerClientesPorScoring(7));

    }

}
