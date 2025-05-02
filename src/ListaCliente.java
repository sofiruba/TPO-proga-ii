import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ListaCliente {
    private ArrayList<Cliente> clientes;
    private HashMap<String, Cliente> clientesMap;

    public ListaCliente() {
        this.clientes = new ArrayList<>();
        this.clientesMap = new HashMap<>();
    }

    public void agregar(Cliente cliente) {
        clientes.add(cliente);
        clientesMap.put(cliente.getNombre(), cliente);
    }

    public Cliente obtener(int indice) {
        return clientes.get(indice);
    }

    public int size() {
        return clientes.size();
    }

    public void imprimirLista() {
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    

    public Cliente obtenerClientePorNombre(String nombre) {
        try {
            Cliente cliente = clientesMap.get(nombre);
    
            if (cliente == null) {
                throw new Exception("Cliente con nombre " + nombre + " no encontrado.");
            }
    
            return cliente;
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
   
    public HashMap<String, Cliente> obtenerTodosLosClientes() {
        return clientesMap;
    }
    
    public void eliminarClientePorNombre(String nombre) {
        try {
            Cliente cliente = clientesMap.remove(nombre);
    
            if (cliente == null) {
                throw new Exception("Cliente con nombre " + nombre + " no encontrado.");
            }
    
            clientes.remove(cliente);
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    

    public void eliminarClientePorIndice(int indice) {
        try {
            if (indice < 0 || indice >= clientes.size()) {
                throw new IndexOutOfBoundsException("Índice fuera de rango.");
            }
    
            Cliente cliente = clientes.remove(indice);
            clientesMap.remove(cliente.getNombre());
    
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<Cliente> obtenerClientesPorScoring(int scoring) {
        ArrayList<Cliente> clientesEncontrados = new ArrayList<>();
    
        try {
            for (Cliente cliente : clientes) {
                if (cliente.getScoring() == scoring) {
                    clientesEncontrados.add(cliente);  
                }
            }
    
            if (clientesEncontrados.isEmpty()) {
                System.out.println("No se encontraron clientes con Scoring " + scoring);
            }
    
        } catch (Exception e) {
            System.out.println("Se produjo un error: " + e.getMessage());
        }
    
        return clientesEncontrados; 
    }
    private boolean nombresExisten(Set<String> nombres) {
        for (String nombre : nombres) {
            if (!clientesMap.containsKey(nombre)) {
                return true;
            }
        }
        return false;
    }
    private boolean validarReferencias(Set<String> siguiendo, Set<String> conexiones) {
        if (siguiendo != null && nombresExisten(siguiendo)) {
            System.out.println("Uno o más nombres en 'siguiendo' no existen en el sistema.");
            return false;
        }
        if (conexiones != null && nombresExisten(conexiones)) {
            System.out.println("Uno o más nombres en 'conexiones' no existen en el sistema.");
            return false;
        }
        return true;
    }
    public boolean registrarCliente(String nombre, Integer scoring, Set<String> siguiendo, Set<String> conexiones) {
        if (clientesMap.containsKey(nombre)) {
            System.out.println("El cliente ya está registrado.");
            return false;
        }
    
        if (!validarReferencias(siguiendo, conexiones)) {
            return false;
        }
    
        try {
            Cliente nuevoCliente = new Cliente(nombre, scoring, siguiendo, conexiones);
            agregar(nuevoCliente);
            System.out.printf("Cliente '%s' registrado con éxito.\n", nuevoCliente.getNombre());
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }
    
    
    
}