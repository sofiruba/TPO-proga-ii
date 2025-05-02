import java.io.FileReader;
import com.google.gson.Gson;

public class ManejodeJson {
    private ListaCliente listaClientes;
    private final String rutaArchivo = "C:\\Users\\USUARIO\\Documents\\documentos\\UADE\\prog\\TPO - copia\\TPO - copia\\src\\Listadeclientes.json";

    public ManejodeJson() {
        this.listaClientes = new ListaCliente();
        cargarDesdeJson();  // Llamar a cargarDesdeJson sin el parámetro
    }
    private void cargarDesdeJson() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(rutaArchivo); 
            ListaCliente clientesDesdeJson = gson.fromJson(reader, ListaCliente.class);

            if (clientesDesdeJson != null) {
                for (Cliente cliente : clientesDesdeJson.getClientes()) {
                    listaClientes.agregar(cliente);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cliente obtenerClientePorNombre(String nombre) {
        return listaClientes.obtenerClientePorNombre(nombre);
    }

    public void imprimirClientes() {
        listaClientes.imprimirLista();
    }

    public ListaCliente getListaClientes() {
        return listaClientes;
    }
}
