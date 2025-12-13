package HechoServer;
import java.io.*;
// Importa todas las clases del paquete java.io necesarias para manejar
// entrada y salida de datos (InputStream, OutputStream, BufferedReader, PrintWriter, etc.)

import java.net.ServerSocket;
// Importa ServerSocket, que permite crear un servidor TCP
// Este objeto se encarga de escuchar conexiones entrantes en un puerto

import java.net.Socket;
// Importa Socket, que representa la conexión concreta entre el servidor y un cliente

import java.text.SimpleDateFormat;
// Importa SimpleDateFormat para dar formato legible a fechas y horas

public class HechoServer {
// Define la clase HechoServer, que implementa un servidor TCP sencillo

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    // Crea un formateador de fecha y hora con el patrón:
    // día/mes/año horas:minutos:segundos

    public static String getFecha() {
        // Método auxiliar que devuelve la fecha y hora actual como String
        return sdf.format(System.currentTimeMillis());
        // Obtiene el tiempo actual del sistema en milisegundos
        // y lo convierte a texto usando el formato definido
    }

    public static void main(String[] args) {
        // Método principal, punto de entrada del programa

        Thread demonio = new Thread(() -> {
            // Crea un nuevo hilo usando una expresión lambda
            // Este hilo se ejecutará en segundo plano

            while (true) {
                // Bucle infinito para que el hilo esté siempre activo

                System.out.println(getFecha() + " Servidor activo");
                // Muestra periódicamente que el servidor sigue funcionando

                try {
                    Thread.sleep(5000);
                    // Detiene el hilo durante 5 segundos (5000 ms)
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                    // Lanza una excepción si el hilo es interrumpido
                }
            }
        });

        demonio.setDaemon(true);
        // Marca el hilo como demonio
        // Un hilo demonio no impide que el programa termine

        try (ServerSocket server = new ServerSocket(1025)) {
            // Crea un ServerSocket que escucha conexiones TCP en el puerto 1025
            // try-with-resources asegura que el servidor se cierre correctamente

            demonio.start();
            // Inicia el hilo demonio

            System.out.println(getFecha() + " Servidor escuchando en puerto 1025");
            // Indica que el servidor ya está listo para aceptar conexiones

            Socket socket = server.accept();
            // Espera (de forma bloqueante) a que un cliente se conecte
            // Cuando ocurre, se crea un Socket para esa conexión

            System.out.println(getFecha() + " Recibida solicitud de comunicación");
            // Confirma que un cliente se ha conectado al servidor

            OutputStream os = socket.getOutputStream();
            // Obtiene el flujo de salida del socket
            // Se usa para enviar datos al cliente

            PrintWriter pw = new PrintWriter(os, true);
            // Crea un PrintWriter para enviar texto al cliente
            // El parámetro true activa el auto-flush (envío inmediato)

            InputStream is = socket.getInputStream();
            // Obtiene el flujo de entrada del socket
            // Se usa para recibir datos del cliente

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // Permite leer texto del cliente línea por línea de forma eficiente

            String line = null;
            // Variable donde se almacenarán los mensajes recibidos del cliente

            while ((line = br.readLine()) != null) {
                // Lee líneas enviadas por el cliente
                // El bucle termina cuando el cliente cierra la conexión

                System.out.println("Recibido del cliente: " + line);
                // Muestra en pantalla el mensaje recibido

                pw.println(line.toLowerCase());
                // Envía al cliente el mismo mensaje convertido a minúsculas
            }

        } catch (IOException e) {
            // Captura errores relacionados con la red o entrada/salida

            System.err.println("Error al arrancar el servidor " + e.getMessage());
            // Muestra el mensaje de error por la consola
        }
    }
}
