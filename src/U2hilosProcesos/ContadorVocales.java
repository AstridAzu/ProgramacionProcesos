package U2hilosProcesos;



import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ContadorVocales implements Runnable {

    public static final Map<Character,Character> VOCALES;
    File file = new File(System.getProperty("user.dir"));
    String SALIDA = file.getAbsolutePath()+"\\src\\archivos\\salida\\";
    private char vocal;
    private String archivo;

    static{
        VOCALES = new HashMap();
        VOCALES.put('a','á');
        VOCALES.put('e','é');
        VOCALES.put('i','í');
        VOCALES.put('o','ó');
        VOCALES.put('u','ú');
    }

    // Constructor para recibir los parámetros
    public ContadorVocales(char vocal, String archivo) {
        this.vocal = vocal;
        this.archivo = archivo;
    }

    @Override
    public void run() {
        // Mostrar información del thread
        Thread threadActual = Thread.currentThread();
        System.out.println("Nombre Thread: " +  vocal );
        System.out.println("el ID del thread es: " + threadActual.getId());
        contarVocales();
    }

    public void contarVocales() {
        int contador = 0;
        try (BufferedReader in = new BufferedReader( new FileReader(archivo))) {
            String line;
            while ((line = in.readLine()) != null) {
                line = line.toLowerCase();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == vocal || line.charAt(i) == VOCALES.get(vocal) )
                        contador++;
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + archivo);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error en lectura de archivo: " + archivo);
            throw new RuntimeException(e);
        }
        //creo los archivos txt de cada letra

        File txt=new File(SALIDA+vocal+".txt");
        try{
            FileWriter fileWriter=new FileWriter(txt, false);
            fileWriter.write(contador);
        }catch(Exception e){}
        System.out.println("Vocal '" + vocal + "' - Total: " + contador);
    }


    public static void main(String[] args) {
        ContadorVocales test = new ContadorVocales(args[0].charAt(0), args[1]);
        test.contarVocales();
    }
}