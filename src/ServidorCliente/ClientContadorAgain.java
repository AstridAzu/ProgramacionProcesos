package ServidorCliente;
import java.io.BufferedReader;
// Permite leer texto línea por línea desde un flujo de entrada

import java.io.IOException;
// Maneja excepciones de entrada/salida

import java.io.InputStreamReader;
// Convierte un InputStream en un Reader de caracteres

import java.io.PrintWriter;
// Permite escribir texto en un flujo de salida con facilidad

import java.net.Socket;
// Permite crear un socket para conectarse a un servidor

import java.util.Scanner;
// Permite leer datos desde el teclado

public class ClientContadorAgain {
// Define la clase principal del cliente

    private static final String HOST = "localhost";
    // Dirección del servidor al que se va a conectar

    private static final int PORT = 4000;
    // Puerto en el que escucha el servidor

    public static void main(String[] args) {
        // Método principal

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {
            // Crea un socket y se conecta al servidor
            // BufferedReader permite leer mensajes del servidor línea por línea
            // PrintWriter permite enviar mensajes al servidor con auto-flush activado

            System.out.println("Conectado con el servidor");
            // Mensaje informativo de que la conexión fue exitosa

            Scanner sc = new Scanner(System.in);
            // Scanner para leer comandos desde el teclado

            String comando;
            // Variable para almacenar el comando que el usuario introduce

            do {
                System.out.print("Introduzca un comando (inc/dec/get/bye): ");
                // Mensaje que indica al usuario qué comandos puede escribir

                comando = sc.nextLine();
                // Lee la línea introducida por el usuario

                pw.println(comando);
                // Envía el comando al servidor

                System.out.println(br.readLine());
                // Lee la respuesta del servidor y la muestra por consola

            } while (!comando.trim().equalsIgnoreCase("bye"));
            // Repite hasta que el usuario escriba "bye"
            // trim() elimina espacios al inicio/final y equalsIgnoreCase ignora mayúsculas/minúsculas

        } catch (IOException e) {
            // Captura errores de conexión o de entrada/salida

            System.err.println("Error en la conexión: " + e.getMessage());
            // Muestra mensaje de error con detalles
        }

    }
}
