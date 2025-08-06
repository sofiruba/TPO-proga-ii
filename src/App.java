import java.util.Scanner;

public class App {


    private static void buscarClientesPorNombre(RedSocial gc, Scanner sc) {
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = sc.nextLine();
        Cliente cliente = gc.buscarClientePorNombre(nombre);
        if (cliente == null) {
            System.out.println("Cliente con nombre " + nombre + " no encontrado.");
        } else {
            System.out.println("Cliente encontrado: " + cliente.getNombre());
        }
    }
    private static void mostrarVecinos(Scanner sc, RedSocial gc) {
        System.out.println("==============================================");
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = GestorClientes.capitalizar(sc.nextLine().trim());
        gc.getGestorClientes().mostrarVecinosDesdeGrafo(nombre);

    }
    // VERIFICAR SI SE PUEDE HACER ESTO
    private static void ConsultarAmistad(RedSocial gc, Scanner sc) {
        System.out.println("==============================================");
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = GestorClientes.capitalizar(sc.nextLine().trim());
        gc.consultarAmistad(nombre);
    }


    private static void buscarClientesPorScoring(RedSocial gc, Scanner sc) {

        System.out.print("Ingrese el scoring del cliente: ");
        int scoring = sc.nextInt();
        sc.nextLine();
        gc.buscarClientePorScoring(scoring);
    }


    private static void registrarAccion(RedSocial gc, Scanner sc) {
        System.out.print("Ingrese el tipo de la nueva Acción: ");
        String tipoAccion = sc.nextLine();
        gc.registrarAccion(tipoAccion);
    }

    private static void eliminarAccion(RedSocial gc) throws Exception {
        gc.eliminarAccion();
    }

    private static void agregarCliente(RedSocial gc, Scanner sc) {

    }
    private static void crearSolicitud(RedSocial gc, Scanner sc) throws Exception {
        System.out.print("Ingrese el nombre del seguidor: ");
        String nombre1 = GestorClientes.capitalizar(sc.nextLine().trim());
        System.out.print("Ingrese el nombre del seguido: ");
        String nombre2 = GestorClientes.capitalizar(sc.nextLine().trim());
        gc.enviarSolicitud(nombre1, nombre2);
    }

    private static void verSolicitudes(RedSocial gc, Scanner sc){
        System.out.print("Ingrese su nombre: ");
        String nombre1 = GestorClientes.capitalizar(sc.nextLine().trim());

        if (!gc.getClientes().clienteExiste(nombre1)) {
            System.out.println("El cliente no existe.");
            return;
        }

        gc.verSolicitudesPendientes(nombre1); // Esto ahora sí se ejecuta solo si existe

        System.out.print("¿Desea 1) aceptar o 2) rechazar una solicitud? ");
        int num = sc.nextInt();
        sc.nextLine(); // consumir salto de línea

        if (num == 1){
            System.out.print("Ingrese la solicitud que desee aceptar: ");
            String nombre2 = GestorClientes.capitalizar(sc.nextLine().trim());
            gc.aceptarSolicitud(nombre2, nombre1);  // origen, destino
        } else if (num == 2) {
            System.out.print("Ingrese la solicitud que desee rechazar: ");
            String nombre2 = GestorClientes.capitalizar(sc.nextLine().trim());
            gc.rechazarSolicitud(nombre2, nombre1); // origen, destino
        } else {
            System.out.println("Opción inválida.");
        }
    }


    private static  void eliminarSeguido(RedSocial gc, Scanner sc) {
        System.out.println("Eliminar seguidor");
        System.out.print("Ingrese nombre del seguidor (origen): ");
        String origen = GestorClientes.capitalizar(sc.nextLine().trim());

        System.out.print("Ingrese nombre del seguido (destino): ");
        String destino = GestorClientes.capitalizar(sc.nextLine().trim());

        gc.eliminarSeguidor(origen, destino);

    }

    private static void cambiarPrivacidad(RedSocial gc, Scanner sc) {
        System.out.println("Hacer cliente privado");
        System.out.print("Ingrese nombre del cliente: ");
        String cliente = GestorClientes.capitalizar(sc.nextLine().trim());

        Cliente cliente2 = gc.buscarClientePorNombre(cliente);

        if (cliente2 == null) {
            System.out.println("❌ Cliente con nombre " + cliente + " no encontrado.");
        } else {
            gc.cambiarPrivacidad(cliente, true);
            System.out.println("🔒 Privacidad activada para " + cliente);
        }
    }


    private static void calcularDistancia(RedSocial gc, Scanner sc) {
        System.out.println("==============================================");
        System.out.println("CALCULAR DISTANCIA ENTRE CLIENTES");
        System.out.print("Ingrese el nombre del cliente origen: ");
        String origen = GestorClientes.capitalizar(sc.nextLine().trim());

        System.out.print("Ingrese el nombre del cliente destino: ");
        String destino = GestorClientes.capitalizar(sc.nextLine().trim());

        gc.calcularDistanciaEntreClientes(origen, destino);
    }

    private static void mostrarCaminoMasCorto(RedSocial gc, Scanner sc) {
        System.out.println("==============================================");
        System.out.println("MOSTRAR CAMINO MÁS CORTO ENTRE CLIENTES");
        System.out.print("Ingrese el nombre del cliente origen: ");
        String origen = GestorClientes.capitalizar(sc.nextLine().trim());

        System.out.print("Ingrese el nombre del cliente destino: ");
        String destino = GestorClientes.capitalizar(sc.nextLine().trim());

        gc.mostrarCaminoEntreClientes(origen, destino);
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        RedSocial gc = new RedSocial();

        boolean salir = false;

        while (!salir) {
            gc.imprimirMenuPrincipal();
            System.out.print("Seleccione una opción: ");

            if (!sc.hasNextInt()) {
                System.out.println("Debe ingresar un número.");
                sc.nextLine(); // Limpiar la entrada incorrecta
                continue;
            }
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
                case 8:
                    verSolicitudes(gc, sc);
                    break;
                case 9:
                    eliminarSeguido(gc, sc);
                    break;
                case 10:
                    gc.imprimirEstadoCompleto();
                    break;
                case 11:
                    ConsultarAmistad(gc, sc);
                    break;
                case 12:
                    cambiarPrivacidad(gc, sc);
                    break;
                case 13:
                    mostrarVecinos(sc, gc);
                    break;
                case 14:
                    calcularDistancia(gc, sc);
                    break;
                case 15:
                    mostrarCaminoMasCorto(gc, sc);
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        }

        sc.close();
        System.out.println("Programa finalizado.");
    }
}
