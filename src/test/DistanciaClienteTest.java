import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

public class DistanciaClienteTest {
    private GestorClientes lista;
    
    @BeforeEach
    public void setUp() {
        lista = new GestorClientes();


        Cliente clienteA = new Cliente("A", 5,1);
        clienteA.getSiguiendo().add("B");
        clienteA.getSiguiendo().add("E");

        Cliente clienteB = new Cliente("B", 6,1);
        clienteB.getSiguiendo().add("C");

        Cliente clienteC = new Cliente("C", 7, 2);
        clienteC.getSiguiendo().add("D");
        
        Cliente clienteD = new Cliente("D", 8,2);
        Cliente clienteE = new Cliente("E", 4, 3);
        clienteE.getSiguiendo().add("F");
        Cliente clienteF = new Cliente("F", 3, 2);
        
        // Cliente aislado
        Cliente clienteG = new Cliente("G", 2, 3);
        
        // Agregar clientes a la lista
        lista.agregar(clienteA);
        lista.agregar(clienteB);
        lista.agregar(clienteC);
        lista.agregar(clienteD);
        lista.agregar(clienteE);
        lista.agregar(clienteF);
        lista.agregar(clienteG);
        
        // Simular algunas conexiones bidireccionales (amistades)
        lista.agregaramistadMutua("B", "C");
    }
    
    @Test
    public void testDistanciaClienteASiMismo() {
        int distancia = lista.calcularDistancia("A", "A");
        assertEquals(0, distancia, "La distancia de un cliente a sí mismo debe ser 0");
    }
    
    @Test
    public void testDistanciaDirecta() {
        int distancia = lista.calcularDistancia("A", "B");
        assertEquals(1, distancia, "La distancia entre A y B debe ser 1 (conexión directa)");
    }
    
    @Test
    public void testDistanciaDosSaltos() {
        int distancia = lista.calcularDistancia("A", "C");
        assertEquals(2, distancia, "La distancia entre A y C debe ser 2 saltos (A->B->C)");
    }
    
    @Test
    public void testDistanciaTresSaltos() {
        int distancia = lista.calcularDistancia("A", "D");
        assertEquals(3, distancia, "La distancia entre A y D debe ser 3 saltos (A->B->C->D)");
    }
    
    @Test
    public void testDistanciaOtraRama() {
        int distancia = lista.calcularDistancia("A", "F");
        assertEquals(2, distancia, "La distancia entre A y F debe ser 2 saltos (A->E->F)");
    }
    
    @Test
    public void testSinConexion() {
        int distancia = lista.calcularDistancia("A", "G");
        assertEquals(-1, distancia, "No debe haber conexión entre A y G (cliente aislado)");
    }
    
    @Test
    public void testClienteInexistente() {
        int distancia = lista.calcularDistancia("A", "X");
        assertEquals(-2, distancia, "Debe retornar -2 para cliente inexistente");
    }
    
    @Test
    public void testParametrosNulos() {
        int distancia = lista.calcularDistancia(null, "A");
        assertEquals(-2, distancia, "Debe retornar -2 para parámetros nulos");
        
        distancia = lista.calcularDistancia("A", null);
        assertEquals(-2, distancia, "Debe retornar -2 para parámetros nulos");
    }
    
    @Test
    public void testConexionBidireccional() {
        // B y C tienen amistad mutua, debería ser distancia 1 en ambas direcciones
        int distanciaBC = lista.calcularDistancia("B", "C");
        int distanciaCB = lista.calcularDistancia("C", "B");
        
        assertEquals(1, distanciaBC, "La distancia entre B y C debe ser 1 (amistad mutua)");
        assertEquals(1, distanciaCB, "La distancia entre C y B debe ser 1 (amistad mutua)");
    }
}
