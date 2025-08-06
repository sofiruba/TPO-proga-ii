import java.util.List;
import java.util.Scanner;

public class Final {
    public static void main(String[] args) {
        RedSocial2 red = new RedSocial2();
        Scanner sc = new Scanner(System.in);
        int opcion, subopcion;

        do {
            System.out.println("\n--- MENÚ RED SOCIAL ---");
            System.out.println("1. Árbol general");
            System.out.println("2. Ver ABB");
            System.out.println("3. Ver AVL");
            System.out.println("4. Busquedas y consultas avanzadas");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            GestorClientes2.validarOpcion(sc);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    do {
                        System.out.println("\n--- Árbol General ---");
                        System.out.println("1. Ver árbol general");
                        System.out.println("2. Obtener clientes por nivel jerárquico");
                        System.out.println("3. Profundidad de un cliente");
                        System.out.println("4. Volver al menú principal");
                        System.out.print("Opción: ");

                        GestorClientes2.validarOpcion(sc);
                        subopcion = sc.nextInt();
                        sc.nextLine();

                        switch (subopcion) {
                            case 1 -> red.mostrarJerarquia();
                            case 2 -> {
                                int nivel;
                                do {
                                    int nivelmax = red.gestorClientes.jerarquiaClientes.maximoNivel();
                                    nivel = GestorClientes2.leerEntero("Nivel jerárquico (mayor o igual a 0, maximo " + nivelmax+ "): ", sc);


                                    if (nivel < 0 && nivel> nivelmax)
                                        System.out.println("El nivel debe ser un número mayor o igual a 0. y mayor a : " + nivelmax);
                                } while (nivel < 0);
                                red.mostrarClientesPorNivel(nivel);
                            }
                            case 3 -> {
                                String nombreCliente = GestorClientes2.leerCadenaNoVacia("Nombre del cliente: ", sc);
                                if (!GestorClientes2.esNombreValido(nombreCliente)) {
                                    System.out.println("El nombre solo puede contener letras (sin números ni símbolos).");
                                    break;
                                }
                                if (!red.getGestorClientes2().existeCliente(nombreCliente)) {
                                    System.out.println("No existe un cliente con ese nombre.");
                                    break;
                                }
                                int profundidad = red.getGestorClientes2().obtenerProfundidadDeCliente(nombreCliente);
                                if (profundidad == -1)
                                    System.out.println("Cliente no encontrado.");
                                else
                                    System.out.println("Profundidad de " + nombreCliente + ": " + profundidad);
                            }
                            case 4 -> System.out.println("Volviendo al menú principal...");
                            default -> System.out.println("Opción inválida.");
                        }
                    } while (subopcion != 4);
                }

                case 2 -> {
                    do {
                        System.out.println("\n--- Menú ABB ---");
                        System.out.println("1. Ver árbol ABB");
                        System.out.println("2. Buscar clientes por scoring");
                        System.out.println("3. Cliente con mayor scoring");
                        System.out.println("4. Buscar clientes con rango de scoring");
                        System.out.println("5. Volver al menú principal");
                        System.out.print("Opción: ");

                        GestorClientes2.validarOpcion(sc);
                        subopcion = sc.nextInt();
                        sc.nextLine();

                        switch (subopcion) {
                            case 1 -> red.gestorClientes.imprimirABBCompleto();
                            case 2 -> {
                                int s = GestorClientes2.leerEntero("Scoring a buscar: ", sc);
                                List<Cliente> encontrados = red.gestorClientes.buscarPorScoringABB(s);
                                if (encontrados.isEmpty())
                                    System.out.println("No se encontraron clientes.");
                                else
                                    encontrados.forEach(c -> System.out.println(c.getNombre() + " - Scoring: " + c.getScoring()));
                            }
                            case 3 -> System.out.println("Cliente con mayor scoring: " + red.gestorClientes.obtenerClienteConMayorScoringABB().getNombre());
                            case 4 -> {
                                int[] rango = GestorClientes2.leerRangoScoring(sc);
                                int min = rango[0];
                                int max = rango[1];
                                List<Cliente> enRango = red.gestorClientes.buscarClientesEnRangoABB(min, max);
                                if (enRango.isEmpty())
                                    System.out.println("No hay clientes en ese rango.");
                                else
                                    enRango.forEach(c -> System.out.println(c.getNombre() + " - Scoring: " + c.getScoring()));
                            }
                            case 5 -> System.out.println("Volviendo al menú principal...");
                            default -> System.out.println("Opción inválida.");
                        }
                    } while (subopcion != 5);
                }

                case 3 -> {
                    do {
                        System.out.println("\n--- Menú AVL ---");
                        System.out.println("1. Ver AVL");
                        System.out.println("2. Listar clientes en rango de scoring");
                        System.out.println("2. Buscar clientes por scoring");
                        System.out.println("3. Volver al menú principal");
                        System.out.print("Opción: ");

                        GestorClientes2.validarOpcion(sc);
                        subopcion = sc.nextInt();
                        sc.nextLine();

                        switch (subopcion) {
                            case 1 -> red.mostrarAVL();
                            case 2 -> {
                                int[] rango = GestorClientes2.leerRangoScoring(sc);
                                int min = rango[0];
                                int max = rango[1];
                                List<Cliente> enRango = red.buscarEnRango(min, max);
                                if (enRango.isEmpty())
                                    System.out.println("No hay clientes en ese rango.");
                                else
                                    enRango.forEach(c -> System.out.println(c.getNombre() + " - Scoring: " + c.getScoring()));
                            }
                            case 3->{
                                int s = GestorClientes2.leerEntero("Scoring a buscar: ", sc);
                                List<Cliente> encontrados = red.buscarPorScoring(s);
                                if (encontrados.isEmpty()) {
                                    System.out.println("No se encontraron clientes.");
                                } else {
                                    encontrados.forEach(c -> System.out.println(c.getNombre() + " - Scoring: " + c.getScoring()));
                                }

                            }
                            case 4 -> System.out.println("Volviendo al menú principal...");
                            default -> System.out.println("Opción inválida.");
                        }
                    } while (subopcion != 4);
                }

                case 4 -> {
                    do {
                        System.out.println("\n--- Busquedas y consultas avanzadas ---");
                        System.out.println("1. Clientes con scoring mayor a:");
                        System.out.println("2. Clientes con mayor número de conexiones");
                        System.out.println("3. Agregar cliente");
                        System.out.println("4. Enviar solicitud");
                        System.out.println("5. Procesar solicitud");
                        System.out.println("6. Ver lista de estado");
                        System.out.println("7. Comparar ABB y AVL");
                        System.out.println("8. Volver al menú principal");
                        System.out.print("Opción: ");

                        GestorClientes2.validarOpcion(sc);
                        subopcion = sc.nextInt();
                        sc.nextLine();

                        switch (subopcion) {
                            case 1 -> {
                                System.out.print("Scoring mínimo: ");
                                int min = sc.nextInt();
                                List<Cliente> encontrados = red.buscarClientesMayoresA(min);
                                if (encontrados.isEmpty())
                                    System.out.println("No hay clientes con scoring mayor a " + min);
                                else
                                    encontrados.forEach(c -> System.out.println(c.getNombre() + " - Scoring: " + c.getScoring()));
                            }
                            case 2 -> {
                                List<Cliente> top = red.obtenerClienteConMasConexiones();
                                if (top != null) {
                                    System.out.println("Cliente con más conexiones: ");
                                    top.forEach(c -> System.out.println(c.getNombre() + " - Scoring: " + c.getScoring()));
                                } else {
                                    System.out.println("No hay conexiones registradas.");
                                }
                            }
                            case 3 -> {
                                String nombre;
                                do {
                                    nombre = GestorClientes2.leerCadenaNoVacia("Nombre del cliente: ", sc);
                                    nombre = red.gestorCliente.capitalizar(nombre);
                                    if (!GestorClientes2.esNombreValido(nombre)) {
                                        System.out.println("El nombre solo puede contener letras (sin números ni símbolos).");
                                    } else if (red.getGestorClientes2().existeCliente(nombre)) {
                                        System.out.println("Ya existe un cliente con ese nombre.");
                                        nombre = null;
                                    }
                                } while (nombre == null);

                                int scoring = GestorClientes2.leerEntero("Scoring del cliente: ", sc);
                                if (red.getGestorClientes2().existeScoring(scoring)) {
                                    System.out.println("Ya existe un cliente con ese scoring. No se pueden repetir.");
                                    break;
                                }
                                red.agregarCliente(nombre, scoring);
                                System.out.println("Cliente agregado con éxito.");
                            }
                            case 4 -> {
                                String n1 = GestorClientes2.leerCadenaNoVacia("Nombre del primer cliente: ", sc).trim();
                                n1 = red.gestorCliente.capitalizar(n1);
                                String n2 = GestorClientes2.leerCadenaNoVacia("Nombre del segundo cliente: ", sc).trim();
                                n2 = red.gestorCliente.capitalizar(n2);
                                if (!GestorClientes2.esNombreValido(n1) || !GestorClientes2.esNombreValido(n2)) {
                                    System.out.println("Los nombres solo pueden contener letras.");
                                    break;
                                }
                                if (!red.getGestorClientes2().existeCliente(n1) || !red.getGestorClientes2().existeCliente(n2)) {
                                    System.out.println("Uno o ambos clientes no existen.");
                                    break;
                                }
                                red.enviarSolicitud(n1, n2);
                            }
                            case 5 -> red.procesarSolicitud();
                            case 6 -> red.getGestorClientes2().g.imprimirLista();
                            case 7 -> {
                                System.out.print("Scoring a buscar para comparar: ");
                                int s = sc.nextInt();
                                red.compararVelocidadBusqueda(s);
                            }
                            case 8 -> System.out.println("Volviendo al menú principal...");
                            default -> System.out.println("Opción inválida.");
                        }
                    } while (subopcion != 8);
                }

                case 0 -> System.out.println("¡Hasta luego!");

                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        sc.close();
    }
}
