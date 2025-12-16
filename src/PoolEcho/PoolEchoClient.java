package PoolEcho;


import java.io.*;
// Importa clases para entrada y salida de datos
// (InputStream, OutputStream, PrintWriter, BufferedReader, IOException)

import java.net.Socket;
// Importa la clase Socket para crear la conexión con el servidor

import java.util.Scanner;
// Importa Scanner para leer datos desde el teclado

public class PoolEchoClient {
// Define la clase PoolEchoClient
// Actúa como cliente que envía mensajes al servidor y recibe respuestas

    static Scanner sc = new Scanner(System.in);
    // Scanner estático para leer datos desde la consola (teclado)

    public static void main(String[] args) {
        // Método principal, punto de inicio del programa

        // Creamos el socket para establecer la comunicación
        try (Socket socket = new Socket("localhost", 2777)) {
            // Crea un socket y se conecta al servidor
            // "localhost" → servidor en la misma máquina
            // 2777        → puerto donde escucha el servidor

            OutputStream os = socket.getOutputStream();
            // Obtiene el flujo de salida del socket
            // Se usará para enviar datos al servidor

            PrintWriter pw = new PrintWriter(os, true);
            // Crea un PrintWriter para enviar texto al servidor
            // true → activa el envío automático (auto-flush)

            InputStream is = socket.getInputStream();
            // Obtiene el flujo de entrada del socket
            // Se usará para recibir datos del servidor

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is)
            );
            // Permite leer texto línea por línea enviado por el servidor

            String entrada;
            // Variable que almacenará el texto introducido por el usuario

            do {
                entrada = sc.nextLine();
                // Lee una línea escrita por el usuario desde el teclado

                pw.println(entrada);
                // Envía el texto escrito al servidor

                String line = null;
                // Variable declarada pero NO utilizada (innecesaria)

                System.out.println(
                        "Recibido del servidor: " + br.readLine()
                );
                // Lee la respuesta enviada por el servidor
                // y la muestra en pantalla

            } while (!entrada.equals("0"));
            // El bucle se repite hasta que el usuario escriba "0"

        } catch (IOException e) {
            // Captura errores de conexión o de entrada/salida

            throw new RuntimeException(e);
            // Lanza una excepción en tiempo de ejecución
        }
    }
}
