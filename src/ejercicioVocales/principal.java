package ejercicioVocales;

import java.io.*;

public class principal {

    private static final String ARCHIVO_ENTRADA = "C:\\Users\\astrid\\IdeaProjects\\vocales.txt";
    private static final String CLASSPATH = "C:\\Users\\astrid\\IdeaProjects\\ProgramacionProcesos\\out\\production\\ProgramacionProcesos";
    private static final String CLASE = "ejercicioVocales.ContadorVocales";
    public static void main(String[] args) {
        char[] vocales = {'a', 'e', 'i', 'o', 'u'};

        File directorioTrabajo = new File(System.getProperty("user.dir"));
        System.out.println("Directorio de trabajo (padre): " + directorioTrabajo.getAbsolutePath());

        // Carpeta donde guardaremos salidas
        File carpetaSalidas = new File(directorioTrabajo, "salidas");
        if (!carpetaSalidas.exists()) {
            if (carpetaSalidas.mkdirs()) {
                System.out.println("Creada carpeta de salidas: " + carpetaSalidas.getAbsolutePath());
            } else {
                System.err.println("No se pudo crear carpeta de salidas: " + carpetaSalidas.getAbsolutePath());
            }
        }

        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        Process[] procesos = new Process[vocales.length];

        // Lanzar procesos
        for (int i = 0; i < vocales.length; i++) {
            char v = vocales[i];
            File archivoSalida = new File(carpetaSalidas, "salida_" + v + ".txt");
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        javaBin,
                        "-cp", CLASSPATH,
                        CLASE,
                        String.valueOf(v),
                        ARCHIVO_ENTRADA,
                        archivoSalida.getAbsolutePath() // PASAMOS ruta absoluta de salida
                );
                pb.directory(new File(CLASSPATH)); // asegurar mismo working dir
                pb.redirectErrorStream(true);    // combinar stderr en stdout
                Process p = pb.start();
                procesos[i] = p;

                // Leer salida del proceso (no bloqueante)
                logStream(p.getInputStream(), "proc-" + v);

            } catch (IOException e) {
                System.err.println("Error al iniciar proceso para '" + v + "': " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Esperar a que todos terminen
        for (int i = 0; i < procesos.length; i++) {
            try {
                if (procesos[i] != null) {
                    int codigo = procesos[i].waitFor();
                    System.out.println("Proceso " + vocales[i] + " finalizó con código: " + codigo);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Leer archivos de salida (ruta absoluta)
        System.out.println("=== RESULTADOS ===");
        for (char v : vocales) {
            File f = new File("C:\\Users\\astrid\\IdeaProjects\\salidas\\", "salida_" + v + ".txt");
            if (!f.exists()) {
                System.err.println(v + ": archivo NO encontrado -> " + f.getAbsolutePath());
                continue;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String contenido = br.readLine();
                System.out.println(v + ": " + contenido + "  (archivo: " + f.getAbsolutePath() + ")");
            } catch (IOException e) {
                System.err.println("Error leyendo " + f.getAbsolutePath() + ": " + e.getMessage());
            }
        }
    }

    // Método auxiliar para imprimir la salida del proceso en consola
    private static void logStream(final InputStream is, final String prefix) {
        new Thread(() -> {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = r.readLine()) != null) {
                    System.out.println(prefix + ": " + line);
                }
            } catch (IOException e) {
                System.err.println("Error leyendo stream de " + prefix + ": " + e.getMessage());
            }
        }).start();
    }
}
