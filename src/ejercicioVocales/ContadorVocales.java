package ejercicioVocales;

import java.io.*;
// Importa todas las clases necesarias para trabajar con archivos
// (BufferedReader, FileReader, IOException, FileNotFoundException)

import java.util.HashMap;
// Importa la implementación HashMap para almacenar pares clave–valor

import java.util.Map;
// Importa la interfaz Map, que define el comportamiento de un mapa

public class ContadorVocales {
// Define la clase ContadorVocales
// Su objetivo es contar cuántas veces aparece una vocal en un archivo de texto

    public static final Map<Character, Character> VOCALES;
    // Declara un mapa estático y constante
    // Clave   → vocal normal (a, e, i, o, u)
    // Valor   → vocal acentuada (á, é, í, ó, ú)

    static {
        // Bloque estático
        // Se ejecuta UNA SOLA VEZ cuando la clase se carga en memoria

        VOCALES = new HashMap();
        // Crea el HashMap que almacenará las vocales

        VOCALES.put('a', 'á');
        // Relaciona la vocal 'a' con su versión acentuada 'á'

        VOCALES.put('e', 'é');
        // Relaciona la vocal 'e' con su versión acentuada 'é'

        VOCALES.put('i', 'í');
        // Relaciona la vocal 'i' con su versión acentuada 'í'

        VOCALES.put('o', 'ó');
        // Relaciona la vocal 'o' con su versión acentuada 'ó'

        VOCALES.put('u', 'ú');
        // Relaciona la vocal 'u' con su versión acentuada 'ú'
    }

    public void contarVocales(char vocal, String archivo) {
        // Método que cuenta cuántas veces aparece una vocal (normal o acentuada)
        // vocal   → vocal que se desea contar
        // archivo → ruta del archivo de texto a analizar

        int contador = 0;
        // Variable para almacenar el número total de coincidencias

        try (BufferedReader in = new BufferedReader(new FileReader(archivo))) {
            // Abre el archivo indicado para lectura
            // BufferedReader permite leer el archivo línea por línea
            // try-with-resources asegura que el archivo se cierre automáticamente

            String line;
            // Variable para almacenar cada línea leída del archivo

            while ((line = in.readLine()) != null) {
                // Lee el archivo línea por línea
                // El bucle continúa hasta llegar al final del archivo

                line = line.toLowerCase();
                // Convierte la línea a minúsculas
                // Esto evita problemas al comparar mayúsculas/minúsculas

                for (int i = 0; i < line.length(); i++) {
                    // Recorre carácter por carácter la línea

                    if (line.charAt(i) == vocal ||
                            line.charAt(i) == VOCALES.get(vocal)) {
                        // Comprueba si el carácter actual es:
                        // 1) La vocal normal (por ejemplo 'a')
                        // 2) La vocal acentuada correspondiente (por ejemplo 'á')

                        contador++;
                        // Si coincide, incrementa el contador
                    }
                }
            }

        } catch (FileNotFoundException e) {
            // Captura el error si el archivo no existe

            System.err.println("Archivo no encontrado: " + archivo);
            // Muestra un mensaje de error en la salida de error

            throw new RuntimeException(e);
            // Lanza una excepción en tiempo de ejecución

        } catch (IOException e) {
            // Captura errores durante la lectura del archivo

            System.err.println("Error en lectura de archivo: " + archivo);
            // Muestra un mensaje de error

            throw new RuntimeException(e);
            // Lanza una excepción en tiempo de ejecución
        }

        System.out.println(contador);
        // Muestra en pantalla el número total de veces
        // que aparece la vocal en el archivo
    }

    public static void main(String[] args) {
        // Método principal del programa

        ContadorVocales test = new ContadorVocales();
        // Crea un objeto de la clase ContadorVocales

        test.contarVocales(
                args[0].charAt(0),
                args[1]
        );
        // Llama al método contarVocales:
        // args[0].charAt(0) → toma la primera letra del primer argumento
        // args[1]           → toma el segundo argumento como nombre del archivo
    }
}
