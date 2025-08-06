import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ManejodeJson2 {
    private GestorClientes2 gestorClientes;
    private String rutaArchivo;

    public ManejodeJson2(GestorClientes2 gestorClientes, String rutaArchivo) {
        this.gestorClientes = gestorClientes;
        this.rutaArchivo = rutaArchivo;
    }

    private static class GestorClientesJson {
        private List<Cliente> clientes;
        public List<Cliente> getClientes() {
            return clientes;
        }
    }

    public void cargarDesdeJson() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(rutaArchivo);

            Type tipoListaClientes = new TypeToken<List<Cliente>>(){}.getType();
            List<Cliente> clientesDesdeJson = null;

            try {
                clientesDesdeJson = gson.fromJson(reader, tipoListaClientes);
            } catch (Exception e) {
                reader.close();
                reader = new FileReader(rutaArchivo);
                GestorClientesJson gestorJson = gson.fromJson(reader, GestorClientesJson.class);
                if (gestorJson != null) {
                    clientesDesdeJson = gestorJson.getClientes();
                }
            }

            if (clientesDesdeJson != null) {
                for (Cliente cliente : clientesDesdeJson) {
                    if (cliente != null) {
                        gestorClientes.agregar(cliente);
                    }
                }
                System.out.println(" Se cargaron " + clientesDesdeJson.size() + " clientes desde el archivo JSON.");
            } else {
                System.out.println("️ No se encontraron clientes en el archivo JSON.");
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al procesar el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
