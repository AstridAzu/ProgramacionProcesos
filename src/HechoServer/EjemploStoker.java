package HechoServer;

import java.io.*;
import java.net.Socket;

public class EjemploStoker {
// Define la clase EjemploStoker
// Este programa actúa como un cliente TCP que se conecta a un servidor WHOIS

    public static void main(String[] args) {
        // Método principal, punto de entrada del programa

        try (Socket socket = new Socket("whois.internic.net", 43)) {
            // Crea un socket cliente y se conecta al servidor WHOIS
            // "whois.internic.net" es el servidor WHOIS de InterNIC
            // 43 es el puerto estándar del servicio WHOIS
            // try-with-resources garantiza que el socket se cierre automáticamente

            OutputStream os = socket.getOutputStream();
            // Obtiene el flujo de salida del socket
            // Se utiliza para enviar datos al servidor WHOIS

            PrintWriter pw = new PrintWriter(os, true);
            // Crea un PrintWriter para escribir texto al servidor
            // El parámetro true activa el auto-flush, enviando los datos inmediatamente

            InputStream is = socket.getInputStream();
            // Obtiene el flujo de entrada del socket
            // Se usa para recibir la información enviada por el servidor WHOIS

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // Envuelve el InputStream en un InputStreamReader y luego en un BufferedReader
            // Permite leer la respuesta del servidor línea por línea

            pw.println("miservidorprivado.com");
            // Envía al servidor WHOIS el nombre del dominio
            // El servidor responderá con información sobre el propietario del dominio

            String line = null;
            // Variable que almacenará cada línea de texto recibida del servidor

            while ((line = br.readLine()) != null) {
                // Lee todas las líneas que envía el servidor
                // El bucle continúa mientras haya información disponible

                System.out.println(line);
                // Muestra cada línea recibida por pantalla
            }

        } catch (IOException e) {
            // Captura errores relacionados con la conexión o la entrada/salida

            System.err.println("Error al acceder al servidor");
            // Muestra un mensaje de error si no se puede acceder al servidor WHOIS
        }
    }
}


