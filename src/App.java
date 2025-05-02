import java.util.HashSet;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Iniciando sistema de gestión de clientes ===");

        // Inicializar el gestor
        GestionarClientes gestionarClientes = new GestionarClientes();
        System.out.println("✔ Instancia de GestionarClientes creada.");

        // Mostrar la lista inicial de clientes (si cargó desde JSON)
        System.out.println("📋 Lista de clientes cargada desde JSON:");

        gestionarClientes.clientes.imprimirLista();

        // Crear clientes nuevos
        Cliente cliente1 = new Cliente("Ana", 8, new HashSet<>(), new HashSet<>());
        Cliente cliente2 = new Cliente("Bruno", 9, new HashSet<>(), new HashSet<>());

        // Agregar clientes
        gestionarClientes.agregarCliente(cliente1);
        gestionarClientes.agregarCliente(cliente2);
        System.out.println("✅ Clientes agregados: Ana y Bruno.");

        // Enviar solicitud de seguimiento de Ana a Bruno
        gestionarClientes.enviarSolicitud("Ana", "Bruno");
        System.out.println("📨 Solicitud enviada de Ana a Bruno.");

        // Procesar la solicitud
        System.out.println("⚙ Procesando solicitud...");
        gestionarClientes.procesarSolicitud();

        // Deshacer última acción
        System.out.println("↩ Intentando deshacer última acción...");
        try {
            gestionarClientes.deshacerUltimaAccion();
            System.out.println("✔ Última acción deshecha correctamente.");
        } catch (Exception e) {
            System.out.println("⚠ No se pudo deshacer la acción: " + e.getMessage());
        }

        // Mostrar estado final de los clientes
        System.out.println("\n📊 Estado final de los clientes:");
        gestionarClientes.clientes.imprimirLista();


        System.out.println("\n=== Fin de la prueba ===");
    }
}
