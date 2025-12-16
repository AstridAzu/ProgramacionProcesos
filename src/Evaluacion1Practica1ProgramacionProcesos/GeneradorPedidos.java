package Evaluacion1Practica1ProgramacionProcesos;

import java.io.*;
// Importa clases para trabajar con archivos y procesos
// (File, IOException, ProcessBuilder, etc.)

import java.util.HashMap;
// Importa HashMap (aunque en este código NO se usa)

import java.util.Map;
// Importa Map (tampoco se usa en este código)

public class GeneradorPedidos {
// Define la clase principal GeneradorPedidos
// La idea aparente es generar pedidos usando procesos externos

    private static final String JAVA = "java";
    // Comando del sistema operativo para ejecutar la JVM

    private static final String CP = "-cp";
    // Parámetro que indica el classpath

    private static final String NOMBRES = "";
    // ❌ Cadena vacía
    // Parece que debería contener nombres de pedidos o clientes

    private static final String Precio = "";
    // ❌ Cadena vacía
    // Parece que debería contener el precio del pedido

    private static final String FECHA = "";
    // ❌ Cadena vacía
    // Parece que debería contener la fecha del pedido

    private static final String CLASE =
            "Evaluacion1Practica1ProgramacionProcesos.GeneradorPedidos";
    // Nombre COMPLETO de la clase a ejecutar (con paquete)

    private static final String EXTENSION = "GeneradorPedidos.txt";
    // Nombre del archivo de salida

    public static void main(String[] args) {
        // Método principal del programa

        // Obtiene la ruta del directorio de trabajo actual
        File file = new File(System.getProperty("user.dir"));
        // Representa la raíz del proyecto

        CrearCarpetaArchivosYtxt(file);
        // Crea carpetas y archivos necesarios si no existen

        String CLASSPATH =
                file.getAbsolutePath() + "\\out\\production\\ProgramacionProcesos";
        // Ruta donde están los archivos .class compilados

        String ARCHIVO =
                file.getAbsolutePath() + "\\src\\archivos\\GeneradorPedidos.txt";
        // Archivo principal donde se escribirán o leerán datos

        String SALIDA =
                file.getAbsolutePath() + "\\src\\archivos\\salida\\";
        // Carpeta donde se redirigirá la salida de los procesos

        System.out.println("Salida: " + SALIDA);
        // Muestra la ruta de salida

        System.out.println("Archivo: " + ARCHIVO);
        // Muestra la ruta del archivo principal

        System.out.println("Classpath: " + CLASSPATH);
        // Muestra el classpath utilizado

        // ❌ PROBLEMA IMPORTANTE:
        // NOMBRES es una cadena vacía, por lo que:
        // NOMBRES.length() == 0
        // Este bucle NO SE EJECUTA

        for (int i = 0; i < NOMBRES.length(); i++) {

            ProcessBuilder pb = new ProcessBuilder(
                    JAVA,          // Comando: java
                    CP,            // Parámetro: -cp
                    CLASSPATH,     // Ruta del classpath
                    NOMBRES,       // ❌ Argumento vacío
                    Precio,        // ❌ Argumento vacío
                    FECHA,         // ❌ Argumento vacío
                    CLASE,         // Clase a ejecutar
                    ARCHIVO        // Archivo de trabajo
            );
            // Crea un proceso del sistema operativo
            // java -cp CLASSPATH CLASE NOMBRES Precio FECHA ARCHIVO

            pb.redirectOutput(
                    new File(SALIDA + NOMBRES + EXTENSION)
            );
            // Redirige la salida estándar del proceso a un archivo

            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            // Redirige los errores a la consola principal

            try {
                pb.start();
                // Inicia el proceso externo
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Finalizado el conteo de pedidos.");
        // Mensaje final del programa
    }

    // Método que crea carpetas y archivo de texto si no existen
    public static void CrearCarpetaArchivosYtxt(File newFile) {

        // Crea la ruta src/archivos dentro del proyecto
        File Carpeta = new File(newFile, "src/archivos");

        // Si la carpeta NO existe
        if (!Carpeta.exists()) {

            if (Carpeta.mkdir()) {
                // Crea la carpeta "archivos"

                System.out.println(
                        "Carpeta creada en: " + Carpeta.getAbsolutePath()
                );

                // Crea la carpeta "salida"
                File CarpetaSalida = new File(Carpeta, "salida");

                if (!CarpetaSalida.exists()) {
                    CarpetaSalida.mkdir();
                }
            }

            try {
                // Crea el archivo GeneradorPedidos.txt
                File archivotxt =
                        new File(Carpeta, "GeneradorPedidos.txt");

                if (archivotxt.createNewFile()) {
                    System.out.println(
                            "archivo txt creado exitosamente"
                    );
                }
            } catch (Exception e) {
                // ❌ No se maneja la excepción
            }

        } else {
            System.out.println("la carpeta ya existe");
        }
    }
}

// ------------------------------------------------------------------

class pedido {
// Define la clase pedido (por convención debería llamarse Pedido)

    private int id;
    // Identificador del pedido

    private String nombre;
    // Nombre del cliente o del pedido

    private String fecha;
    // Fecha del pedido

    public pedido(int id, String nombre, String fecha) {
        // Constructor de la clase pedido

        this.id = id;
        // Asigna el id

        this.nombre = nombre;
        // Asigna el nombre

        this.fecha = fecha;
        // Asigna la fecha
    }

    public int getId() {
        // Devuelve el id del pedido
        return id;
    }

    public void setId(int id) {
        // Modifica el id del pedido
        this.id = id;
    }

    public String getNombre() {
        // Devuelve el nombre del pedido
        return nombre;
    }

    public void setNombre(String nombre) {
        // Modifica el nombre del pedido
        this.nombre = nombre;
    }

    public String getFecha() {
        // Devuelve la fecha del pedido
        return fecha;
    }

    public void setFecha(String fecha) {
        // Modifica la fecha del pedido
        this.fecha = fecha;
    }
}
