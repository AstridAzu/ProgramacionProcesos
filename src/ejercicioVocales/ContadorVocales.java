package ejercicioVocales;

import java.io.*;

public class ContadorVocales {

    //cuantas veces aparece la vocal
    public static int contar(String texto, char vocal) {
        int contador = 0;
        for (char c : texto.toCharArray()) {
            if (Character.toLowerCase(c) == Character.toLowerCase(vocal)) {
                contador++;
            }
        }
        return contador;
    }
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java ContadorVocales <vocal> <archivoEntrada> <archivoSalida>");
            return;
        }

        char vocal = args[0].charAt(0);
        String archivoEntrada = args[1];

        try {
            // Detectar carpeta base del archivo de entrada
            File entrada = new File(archivoEntrada);
            File carpetaBase = entrada.getParentFile(); // carpeta donde está vocales.txt

            // Crear carpeta "salidas" dentro de esa carpeta base
            File carpetaSalidas = new File(carpetaBase, "salidas");
            if (!carpetaSalidas.exists()) {
                carpetaSalidas.mkdirs();
            }

            // Crear archivo de salida dentro de esa carpeta
            File archivoSalida = new File(carpetaSalidas, "salida_" + vocal + ".txt");

            // Leer texto
            String texto = new String(java.nio.file.Files.readAllBytes(entrada.toPath()));
            int cantidad = contar(texto, vocal);

            // Escribir resultado
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoSalida))) {
                pw.println(cantidad);
            }

            System.out.println("[" + vocal + "] -> " + cantidad + " guardado en " + archivoSalida.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
