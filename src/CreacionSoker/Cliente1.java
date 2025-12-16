package CreacionSoker;

import java.io.*;
// Importa clases para entrada y salida de datos
// (BufferedReader, PrintWriter, InputStreamReader, IOException)

import java.net.*;
// Importa clases para comunicación por red
// (Socket)

public class Cliente1 {
// Define la clase Cliente1, que actuará como cliente TCP

    public static void main(String[] args) {
        // Método principal, punto de inicio del programa cliente

        final String SERVIDOR = "localhost";
        // Dirección del servidor al que se conectará el cliente
        // "localhost" significa que el servidor está en la misma máquina

        final int PUERTO = 5000;
        // Puerto del servidor al que se conectará el cliente

        try (
                Socket socket = new Socket(SERVIDOR, PUERTO);
                // Crea un socket y se conecta al servidor en la dirección y puerto indicados

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                // Flujo de entrada: permite leer los mensajes enviados por el servidor

                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true
                );
                // Flujo de salida: permite enviar mensajes al servidor
                // true activa el envío automático (auto-flush)

                BufferedReader console = new BufferedReader(
                        new InputStreamReader(System.in)
                )
                // Permite leer texto escrito por el usuario desde el teclado
        ) {

            System.out.println(
                    "Conectado al servidor. Comandos disponibles: inc, desc, get, bye"
            );
            // Muestra un mensaje indicando que la conexión fue exitosa
            // y qué comandos puede usar el usuario

            String userInput;
            // Variable para almacenar el texto escrito por el usuario

            while ((userInput = console.readLine()) != null) {
                // Lee una línea escrita en la consola
                // El bucle continúa mientras el usuario no cierre la entrada

                out.println(userInput);
                // Envía el comando escrito por el usuario al servidor

                String respuesta = in.readLine();
                // Espera y lee la respuesta enviada por el servidor

                System.out.println("Servidor: " + respuesta);
                // Muestra en pantalla la respuesta del servidor

                if (userInput.equalsIgnoreCase("bye")) {
                    // Comprueba si el usuario escribió el comando "bye"

                    break;
                    // Sale del bucle y finaliza la comunicación
                }
            }

        } catch (IOException e) {
            // Captura cualquier error de entrada/salida

            e.printStackTrace();
            // Muestra el error en la consola
        }
    }
}
