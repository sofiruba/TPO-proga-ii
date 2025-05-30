import java.util.Scanner;

public class App {

    private static void buscarClientesPorNombre(GestionarClientes gc, Scanner sc) {
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = sc.nextLine();
        gc.buscarClientePorNombre(nombre);
    }

    private static void buscarClientesPorScoring(GestionarClientes gc, Scanner sc) {
        System.out.print("Ingrese el scoring del cliente: ");
        int scoring = sc.nextInt();
        sc.nextLine();
        gc.buscarClientePorScoring(scoring);
    }

    private static void registrarAccion(GestionarClientes gc, Scanner sc) {
        System.out.print("Ingrese el tipo de la nueva Acción: ");
        String tipoAccion = sc.nextLine();
        gc.registrarAccion(tipoAccion);
    }

    private static void eliminarAccion(GestionarClientes gc) throws Exception {
        gc.eliminarAccion();
    }

    private static void agregarCliente(GestionarClientes gc, Scanner sc) {

    }
    private static void crearSolicitud(GestionarClientes gc, Scanner sc) throws Exception {
        System.out.print("Ingrese el nombre del seguidor: ");
        String nombre1 = sc.nextLine();
        System.out.print("Ingrese el nombre del seguido: ");
        String nombre2 = sc.nextLine();
        gc.enviarSolicitud(nombre1, nombre2);
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        GestionarClientes gc = new GestionarClientes();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Buscar cliente por nombre");
            System.out.println("2. Buscar cliente por scoring");
            System.out.println("3. Registrar acción");
            System.out.println("4. Eliminar última acción");
            System.out.println("5. Crear solicitud de seguimiento");
            System.out.println("6. Procesar solicitud de seguimiento");
            System.out.println("7. Consultar acciones");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    buscarClientesPorNombre(gc, sc);
                    break;
                case 2:
                    buscarClientesPorScoring(gc, sc);
                    break;
                case 3:
                    registrarAccion(gc, sc);
                    break;
                case 4:
                    eliminarAccion(gc);
                    break;
                case 5:
                    crearSolicitud(gc, sc);
                    break;
                case 6:
                    gc.procesarSolicitud();
                    break;
                case 7:
                    gc.consultarAcciones();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        sc.close();
        System.out.println("Programa finalizado.");
    }
}
