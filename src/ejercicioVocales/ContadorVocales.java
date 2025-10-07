package ejercicioVocales;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ContadorVocales {

    public static final Map<Character,Character> VOCALES;

    static{
        VOCALES = new HashMap();
        VOCALES.put('a','á');
        VOCALES.put('e','é');
        VOCALES.put('i','í');
        VOCALES.put('o','ó');
        VOCALES.put('u','ú');
    }

    public void contarVocales(char vocal, String archivo) {
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

        System.out.println(contador);

    }


    public static void main(String[] args) {

        ContadorVocales test = new ContadorVocales();
        test.contarVocales(args[0].charAt(0),args[1]);
    }
}
