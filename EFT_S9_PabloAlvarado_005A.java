package eft_s9_pabloalvarado_005a;

import java.util.*;

/**
 * Sistema Teatro Moro para Gestion y Venta de Entradas
 * Evaluacion Final Transversal - Fundamentos de Programacion
 * Autor: Pablo Alvarado Hernandez - Seccion 005A
 */
public class EFT_S9_PabloAlvarado_005A {

    // Scanner para entradas por teclado
    static Scanner sc = new Scanner(System.in);

    // Lista para almacenar ventas realizadas (estructura de datos temporal)
    static List<Entrada> ventas = new ArrayList<>();

    // CLASE CLIENTE 
    static class Cliente {
        String nombre, tipo;
        int edad;

        public Cliente(String nombre, int edad, String tipo) {
            this.nombre = nombre;
            this.edad = edad;
            this.tipo = tipo;
        }

        public double obtenerDescuento() {
            if (edad <= 12) return 0.10; // Ninos
            if (edad >= 60) return 0.25; // Tercera edad
            if (tipo.equalsIgnoreCase("estudiante")) return 0.15; // Descuento por estudiante
            return 0.0; // Sin descuento
        }
    }

    // CLASE ENTRADA
    static class Entrada {
        Cliente cliente;
        String seccion;
        int asiento;
        double precioBase;

        public Entrada(Cliente cliente, String seccion, int asiento, double precioBase) {
            this.cliente = cliente;
            this.seccion = seccion;
            this.asiento = asiento;
            this.precioBase = precioBase;
        }

        public double calcularPrecioFinal() {
            return precioBase * (1 - cliente.obtenerDescuento());
        }
        
        //PARA GENERAR BOLETA
        public void imprimirBoleta() {
            System.out.println("\n---------- BOLETA ----------");
            System.out.println("Cliente: " + cliente.nombre);
            System.out.println("Edad: " + cliente.edad + " | Tipo: " + cliente.tipo);
            System.out.println("Seccion: " + seccion + " | Asiento Nro: " + asiento);
            System.out.println("Precio base: $" + precioBase);
            System.out.println("Descuento aplicado: " + (cliente.obtenerDescuento() * 100) + "%");
            System.out.println("TOTAL A PAGAR: $" + calcularPrecioFinal());
            System.out.println("----------------------------\n");
        }
    }

    //  METODO PRINCIPAL
    public static void main(String[] args) {
        mostrarMenuPrincipal();
    }

    
    //  INICIO DEL PROGRAMA
    
    //  MENU PRINCIPAL
    public static void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n====== TEATRO MORO - MENU PRINCIPAL ======");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver asientos disponibles");
            System.out.println("3. Modificar o cancelar reserva");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = obtenerEntradaEntera();

            switch (opcion) {
                case 1 -> comprarEntrada();
                case 2 -> verAsientosDisponibles();
                case 3 -> modificarReserva();
                case 4 -> {
                    System.out.println("Gracias por usar el sistema del Teatro Moro.");
                    return; // Salir del programa
                }
                default -> System.out.println("Opcion invalida. Intente nuevamente.");
            }
            System.out.println(); // Salto de línea después de mostrar el menú
        } while (true);
    }

    // COMPRAR ENTRADA
    public static void comprarEntrada() {
        boolean continuar = true;
        while (continuar) {
            sc.nextLine(); // Limpiar buffer
            System.out.print("Nombre del cliente: ");
            String nombre = sc.nextLine();

            System.out.print("Edad del cliente: ");
            int edad = obtenerEntradaEntera();
            sc.nextLine(); // Limpiar buffer para evitar lectura vacía

            String tipo;
            do {
                System.out.print("Tipo de cliente (normal / estudiante): ");
                tipo = sc.nextLine().trim().toLowerCase();
                if (!tipo.equals("normal") && !tipo.equals("estudiante")) {
                    System.out.println("Error: Ingrese un tipo valido ('normal' o 'estudiante').");
                }
            } while (!tipo.equals("normal") && !tipo.equals("estudiante"));

            Cliente cliente = new Cliente(nombre, edad, tipo);

            System.out.println("Secciones disponibles: VIP, Palco, Platea Baja, Platea Alta, Galeria");
            System.out.print("Seleccione la seccion: ");
            String seccion = sc.nextLine();

            mostrarAsientosDisponibles(seccion);

            System.out.print("Numero de asiento: ");
            int asiento = obtenerEntradaEntera();

            double precioBase = obtenerPrecioPorSeccion(seccion);

            Entrada entrada = new Entrada(cliente, seccion, asiento, precioBase);
            ventas.add(entrada); // Agregar a lista de ventas

            entrada.imprimirBoleta();

            // Preguntar si desea hacer otra reserva o regresar al menu
            System.out.print("\nDesea realizar otra reserva? (s/n): ");
            char respuesta = sc.next().charAt(0);
            if (respuesta == 'n' || respuesta == 'N') {
                continuar = false;
            }
            System.out.println(); // Salto de línea después de la interacción
        }
    }

    // VER ASIENTOS DISPONIBLES
    public static void verAsientosDisponibles() {
        int opcion;
        do {
            sc.nextLine(); // Limpiar buffer
            System.out.println("\nAsientos disponibles por seccion:");
            System.out.println("1. VIP");
            System.out.println("2. Palco");
            System.out.println("3. Platea Baja");
            System.out.println("4. Platea Alta");
            System.out.println("5. Galeria");
            System.out.print("Seleccione la seccion para ver los asientos disponibles: ");
            String seccion = sc.nextLine();

            mostrarAsientosDisponibles(seccion);

            System.out.print("\nDesea regresar al menu principal? (s/n): ");
            char respuesta = sc.next().charAt(0);
            if (respuesta == 'n' || respuesta == 'N') {
                System.out.println("Gracias por usar el sistema del Teatro Moro.");
                return; // Terminar el programa
            }
        } while (true);
    }

    // MOSTRAR ASIENTOS DISPONIBLES
    public static void mostrarAsientosDisponibles(String seccion) {
        System.out.println("Asientos disponibles en la seccion " + seccion + ":");

        // Listar asientos disponibles (suponiendo 10 asientos por sección)
        List<Integer> asientosOcupados = new ArrayList<>();
        for (Entrada e : ventas) {
            if (e.seccion.equalsIgnoreCase(seccion)) {
                asientosOcupados.add(e.asiento);
            }
        }

        for (int i = 1; i <= 10; i++) {
            if (!asientosOcupados.contains(i)) {
                System.out.println("Asiento " + i + " disponible.");
            }
        }
        System.out.println(); // Salto de línea después de mostrar los asientos disponibles
    }

    // MODIFICAR O CANCELAR RESERVA
    public static void modificarReserva() {
        int opcion;
        do {
            sc.nextLine(); // Limpiar buffer
            System.out.print("Ingrese el nombre del cliente para modificar su reserva: ");
            String nombre = sc.nextLine();

            Entrada reservaEncontrada = null;

            for (Entrada e : ventas) {
                if (e.cliente.nombre.equalsIgnoreCase(nombre)) {
                    reservaEncontrada = e;
                    break;
                }
            }

            if (reservaEncontrada != null) {
                System.out.println("Reserva encontrada. Desea cancelar la reserva? (s/n)");
                String respuesta = sc.nextLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    ventas.remove(reservaEncontrada);
                    System.out.println("Reserva cancelada exitosamente.");
                } else {
                    System.out.println("No se realizaron cambios en la reserva.");
                }
            } else {
                System.out.println("No se encontro ninguna reserva con ese nombre.");
            }

            System.out.print("\nDesea regresar al menu principal? (s/n): ");
            char respuesta = sc.next().charAt(0);
            if (respuesta == 'n' || respuesta == 'N') {
                System.out.println("Gracias por usar el sistema del Teatro Moro.");
                return; // Terminar el programa
            }
        } while (true);
    }

    // PRECIO POR SECCION
    public static double obtenerPrecioPorSeccion(String seccion) {
        return switch (seccion.toLowerCase()) {
            case "vip" -> 50000;
            case "palco" -> 40000;
            case "platea baja" -> 35000;
            case "platea alta" -> 30000;
            case "galeria" -> 20000;
            default -> {
                System.out.println("Seccion no valida. Se asignara precio estandar.");
                yield 25000;
            }
        };
    }

    // MANEJO DE ENTRADA DE DATOS
    public static int obtenerEntradaEntera() {
        int valor = 0;
        boolean valido = false;

        while (!valido) {
            try {
                valor = sc.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada invalida. Por favor ingrese un numero entero.");
                sc.nextLine(); // Limpiar buffer
            }
        }
        return valor;
    }
}

//FIN DEL PROGRAMA