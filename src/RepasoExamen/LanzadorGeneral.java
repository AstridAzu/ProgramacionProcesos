package RepasoExamen;


import java.io.*;
import java.util.*;

// Clase principal que permite ejecutar diferentes programas y scripts desde un menú interactivo
public class LanzadorGeneral {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            // Menú de opciones para el usuario
            System.out.println("Seleccione una opción:");
            System.out.println("1. Ejecutar programa externo");
            System.out.println("2. Ejecutar programa Java");
            System.out.println("3. Ejecutar sumador con argumentos");
            System.out.println("4. Ejecutar contador de vocales con archivo");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    // Opción para ejecutar un programa externo (ejecutable)
                    System.out.print("Ruta del ejecutable: ");
                    String ruta = sc.nextLine();
                    ejecutarProceso(new String[]{ruta});
                    break;
                case "2":
                    // Opción para ejecutar una clase Java
                    System.out.print("Nombre de la clase Java (sin .java): ");
                    String clase = sc.nextLine();
                    ejecutarJava(clase, sc);
                    break;
                case "3":
                    // Opción para ejecutar el sumador con argumentos
                    System.out.print("Argumentos para el sumador (ej: 5 7): ");
                    String argsSumador = sc.nextLine();
                    ejecutarJava("U1P03Sumador", argsSumador.split(" "));
                    break;
                case "4":
                    // Opción para ejecutar el contador de vocales con un archivo
                    System.out.print("Nombre del archivo de texto: ");
                    String archivo = sc.nextLine();
                    ejecutarJava("U1P04ContadorVocal", new String[]{archivo});
                    break;
                case "5":
                    // Salir del programa
                    System.out.println("Saliendo...");
                    return;
                default:
                    // Opción inválida
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    // Método para ejecutar un proceso externo usando ProcessBuilder
    private static void ejecutarProceso(String[] comando) {
        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            Process p = pb.start();
            mostrarSalida(p);
        } catch (IOException e) {
            System.err.println("Error al ejecutar el proceso: " + e.getMessage());
        }
    }

    // Método para ejecutar una clase Java, pidiendo argumentos al usuario
    private static void ejecutarJava(String clase, Scanner sc) {
        System.out.print("Argumentos (opcional, separados por espacio): ");
        String argumentos = sc.nextLine();
        String[] args = argumentos.isEmpty() ? new String[]{} : argumentos.split(" ");
        ejecutarJava(clase, args);
    }

    // Método para ejecutar una clase Java con argumentos
    private static void ejecutarJava(String clase, String[] args) {
        List<String> comando = new ArrayList<>();
        comando.add("java"); // Comando para ejecutar la JVM
        comando.add(clase);  // Nombre de la clase principal
        comando.addAll(Arrays.asList(args)); // Agrega los argumentos
        ejecutarProceso(comando.toArray(new String[0]));
    }

    // Método para mostrar la salida estándar y de error del proceso ejecutado
    private static void mostrarSalida(Process p) throws IOException {
        BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String linea;
        System.out.println("Salida del proceso:");
        while ((linea = stdOut.readLine()) != null) {
            System.out.println(linea);
        }
        System.out.println("Errores del proceso (si hay):");
        while ((linea = stdErr.readLine()) != null) {
            System.out.println(linea);
        }
    }
}
