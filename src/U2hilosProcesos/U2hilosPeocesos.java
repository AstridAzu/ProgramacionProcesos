package U2hilosProcesos;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class U2hilosPeocesos {
    private static final String[] VOCALES = {"a","e","i","o","u"};

    public static void main(String[] args) {
        // Mostrar información del thread principal
        Thread threadActual = Thread.currentThread();
        System.out.println("=== THREAD PRINCIPAL ===");
        System.out.println("Thread: " + threadActual.getName() + " | ID: " + threadActual.getId());
        System.out.println("========================\n");

        //uso system.getProperty("user.dir") que Obtiene la ruta del directorio de trabajo actual del programa Java.
        //y lo guardo en mi nuevo file
        File file = new File(System.getProperty("user.dir"));
        CrearCarpetaArchivosYtxt(file);

        String ARCHIVO = file.getAbsolutePath()+"\\src\\archivos\\vocales.txt";
        System.out.println("Archivo: " + ARCHIVO);
        System.out.println();

        // Crear una lista para guardar los threads
        List<Thread> threads = new ArrayList<>();

        // Lanzar un thread por cada vocal
        for (int i = 0; i < VOCALES.length; i++) {
            char vocal = VOCALES[i].charAt(0);
            ContadorVocales contador = new ContadorVocales(vocal, ARCHIVO);
            Thread thread = new Thread(contador, "Thread-Vocal-" + vocal);
            threads.add(thread);
            thread.start(); // Iniciar el thread
            System.out.println("Thread lanzado para vocal '" + vocal + "'");
        }

        System.out.println("\n=== ESPERANDO A QUE LOS THREADS TERMINEN ===\n");

        // Esperar a que todos los threads terminen
        for (Thread thread : threads) {
            try {
                thread.join(); // Espera a que el thread termine
                System.out.println("Thread " + thread.getName() + " finalizado");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nFinalizado el conteo de vocales.");
    }

    //creo un metodo que me cree la carpeta archivos en el directorio de trabajo
    public static void CrearCarpetaArchivosYtxt(File newFile){
        //obtengo el directorio padre de este archivo
        File Carpeta= new File(newFile,"src/archivos");
        //creo la carpeta si no existe
        if(!Carpeta.exists()){
            if (Carpeta.mkdir()) {
                System.out.println("Carpeta creada en: " + Carpeta.getAbsolutePath());
                //creo carpeta de salida
                File CarpetaSalida= new File(Carpeta,"salida");
                if(!CarpetaSalida.exists()){
                    CarpetaSalida.mkdir();
                }
            }
            try{
                //creo el archivo solo se crea si la carpeta es nueva
                File archivotxt= new File(Carpeta,"vocales.txt");
                if(archivotxt.createNewFile()){
                    System.out.println("archivo txt creado exitosamente");
                }
            }catch (Exception e){}

        }else {
            System.out.println("la carpeta ya existe");
        }
    }
}