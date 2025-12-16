package ejercicioVocales;

import com.sun.tools.javac.Main;
import java.io.*;
// Importa todas las clases necesarias para trabajar con archivos y procesos
// (File, IOException, ProcessBuilder, etc.)

public class principal {
// Define la clase principal (ojo: por convención debería llamarse Principal)

    private static final String JAVA = "java";
    // Comando del sistema operativo para ejecutar la máquina virtual de Java

    private static final String CP = "-cp";
    // Parámetro que indica el classpath (ruta donde Java busca las clases)

    private static final String[] VOCALES = {"a","e","i","o","u"};
    // Array con las vocales que se van a procesar

    private static final String CLASE = "ejercicioVocales.ContadorVocales";
    // Nombre COMPLETO de la clase que se ejecutará
    // Incluye el paquete: ejercicioVocales

    private static final String EXTENSION = ".txt";
    // Extensión de los archivos de salida

    public static void main(String[] args) {
        // Método principal, punto de entrada del programa

        // Uso System.getProperty("user.dir")
        // Obtiene la ruta del directorio de trabajo actual del programa Java
        File file = new File(System.getProperty("user.dir"));
        // Crea un objeto File que representa el directorio del proyecto

        CrearCarpetaArchivosYtxt(file);
        // Llama al método que crea las carpetas y el archivo de texto si no existen

        String CLASSPATH = file.getAbsolutePath() + "\\out\\production\\ProgramacionProcesos";
        // Construye la ruta del classpath donde están los .class compilados
        // Necesario para que ProcessBuilder pueda ejecutar la clase ContadorVocales

        String ARCHIVO = file.getAbsolutePath()+"\\src\\archivos\\vocales.txt";
        // Ruta completa del archivo de texto que contiene el contenido a analizar

        String SALIDA = file.getAbsolutePath()+"\\src\\archivos\\salida\\";
        // Ruta de la carpeta donde se guardarán los resultados

        System.out.println("Salida: " + SALIDA);
        // Muestra la ruta de salida por consola

        System.out.println("Archivo: " + ARCHIVO);
        // Muestra la ruta del archivo de entrada

        System.out.println("Classpath: " + CLASSPATH);
        // Muestra el classpath que se usará para ejecutar los procesos

        // Bucle que recorre todas las vocales (a, e, i, o, u)
        for (int i = 0; i < VOCALES.length; i++) {

            ProcessBuilder pb = new ProcessBuilder(
                    JAVA,                 // Comando: java
                    CP,                   // Parámetro: -cp
                    CLASSPATH,            // Ruta del classpath
                    CLASE,                // Clase a ejecutar
                    VOCALES[i],           // Vocal a contar (argumento 1)
                    ARCHIVO               // Archivo a analizar (argumento 2)
            );
            // Crea un proceso del sistema operativo que ejecuta:
            // java -cp CLASSPATH ejercicioVocales.ContadorVocales vocal archivo

            pb.redirectOutput(
                    new File(SALIDA + VOCALES[i] + EXTENSION)
            );
            // Redirige la salida estándar del proceso (System.out)
            // a un archivo, por ejemplo:
            // a.txt, e.txt, i.txt, etc.

            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            // Redirige los errores del proceso hijo
            // a la consola del proceso principal

            try {
                pb.start();
                // Inicia la ejecución del proceso
                // Cada iteración crea un proceso independiente
            } catch (IOException e) {
                throw new RuntimeException(e);
                // Si falla la creación del proceso, lanza una excepción
            }
        }

        System.out.println("Finalizado el conteo de vocales.");
        // Mensaje final indicando que todos los procesos fueron lanzados
    }

    // Método que crea la carpeta "archivos", la carpeta "salida"
    // y el archivo "vocales.txt" si no existen
    public static void CrearCarpetaArchivosYtxt(File newFile){

        // Obtiene el directorio "src/archivos" dentro del proyecto
        File Carpeta = new File(newFile,"src/archivos");

        // Comprueba si la carpeta no existe
        if(!Carpeta.exists()){

            if (Carpeta.mkdir()) {
                // Crea la carpeta "archivos"

                System.out.println(
                        "Carpeta creada en: " + Carpeta.getAbsolutePath()
                );

                // Crea la carpeta "salida" dentro de "archivos"
                File CarpetaSalida = new File(Carpeta,"salida");

                if(!CarpetaSalida.exists()){
                    CarpetaSalida.mkdir();
                    // Crea la carpeta de salida si no existe
                }
            }

            try{
                // Crea el archivo "vocales.txt"
                // Solo se crea si la carpeta es nueva
                File archivotxt = new File(Carpeta,"vocales.txt");

                if(archivotxt.createNewFile()){
                    System.out.println(
                            "archivo txt creado exitosamente"
                    );
                }
            }catch (Exception e){
                // Captura cualquier excepción (no recomendado dejarlo vacío)
            }

        } else {
            // Si la carpeta ya existe
            System.out.println("la carpeta ya existe");
        }
    }
}
