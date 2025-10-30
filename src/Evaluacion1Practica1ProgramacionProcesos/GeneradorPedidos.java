package Evaluacion1Practica1ProgramacionProcesos;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class GeneradorPedidos {

    private static final String JAVA = "java";
    private static final String CP = "-cp";
    private static final String NOMBRES = "";
    private static final String Precio = "";
    private static final String FECHA = "";
    private static final String CLASE = "Evaluacion1Practica1ProgramacionProcesos.GeneradorPedidos";
    private static final String EXTENSION = "GeneradorPedidos.txt";



    public static void main(String[] args) {

        //uso system.getProperty("user.dir") que Obtiene la ruta del directorio de trabajo actual del programa Java.
        //y lo guardo en mi nuevo file
        File file = new File(System.getProperty("user.dir"));
        CrearCarpetaArchivosYtxt(file);
        String CLASSPATH = file.getAbsolutePath() + "\\out\\production\\ProgramacionProcesos";
        String ARCHIVO = file.getAbsolutePath() + "\\src\\archivos\\GeneradorPedidos.txt";
        String SALIDA = file.getAbsolutePath() + "\\src\\archivos\\salida\\";

        System.out.println("Salida: " + SALIDA);
        System.out.println("Archivo: " + ARCHIVO);
        System.out.println("Classpath: " + CLASSPATH);
        //a
        //creo carpeta de salida de archivos
        for (int i = 0; i < NOMBRES.length(); i++) {
            ProcessBuilder pb = new ProcessBuilder(JAVA, CP, CLASSPATH, NOMBRES, Precio, FECHA, CLASE, ARCHIVO);
            pb.redirectOutput(new File(SALIDA + NOMBRES + EXTENSION));
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            try {
                pb.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Finalizado el conteo de pedidos.");
    }

    //creo un metodo que me cree la carpeta archivos en el directorio de trabajo
    public static void CrearCarpetaArchivosYtxt(File newFile) {
        //obtengo el directorio padre de este archivo
        File Carpeta = new File(newFile, "src/archivos");
        //creo la carpeta si no existe
        if (!Carpeta.exists()) {
            if (Carpeta.mkdir()) {
                System.out.println("Carpeta creada en: " + Carpeta.getAbsolutePath());
                //creo carpeta de salida
                File CarpetaSalida = new File(Carpeta, "salida");
                if (!CarpetaSalida.exists()) {
                    CarpetaSalida.mkdir();
                }
            }
            try {
                //creo el archivo solo se crea si la carpeta es nueva
                File archivotxt = new File(Carpeta, "GeneradorPedidos.txt");
                if (archivotxt.createNewFile()) {
                    System.out.println("archivo txt creado exitosamente");
                }
            } catch (Exception e) {
            }

        } else {
            System.out.println("la carpeta ya existe");
        }

    }
}

class pedido{
    private int id;
    private String nombre;
    private String fecha;

    public pedido(int id, String nombre, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}