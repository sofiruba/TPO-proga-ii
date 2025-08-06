import java.io.FileReader;
import com.google.gson.Gson;

public class ManejodeJson {
    private GestorClientes listaClientes;
    private final String rutaArchivo = "C:\\Users\\Natalia\\Downloads\\final falta test\\progra ii reco\\src\\Listadeclientes.json";
    public ManejodeJson() {
        this.listaClientes = new GestorClientes();
        cargarDesdeJson();  // Llamar a cargarDesdeJson sin el parámetro
    }
    private void cargarDesdeJson() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(rutaArchivo); 
            GestorClientes clientesDesdeJson = gson.fromJson(reader, GestorClientes.class);

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

    public GestorClientes getListaClientes() {
        return listaClientes;
    }
}
