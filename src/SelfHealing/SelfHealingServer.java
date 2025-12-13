package SelfHealing;

import java.io.BufferedReader;
// Importa BufferedReader para leer texto de un flujo de entrada de forma eficiente

import java.io.IOException;
// Importa IOException para manejar errores de entrada/salida

import java.io.InputStreamReader;
// Importa InputStreamReader, que convierte un InputStream en un Reader de caracteres

import java.io.PrintWriter;
// Importa PrintWriter para escribir texto de manera sencilla en un flujo de salida

import java.net.ServerSocket;
// Importa ServerSocket, que permite crear un servidor TCP que escucha conexiones entrantes

import java.net.Socket;
// Importa Socket, que representa una conexión TCP entre cliente y servidor

public class SelfHealingServer {
// Define la clase SelfHealingServer
// Este servidor TCP tiene capacidad de “autorrepararse” cuando se cierra inesperadamente

    private static void arrancarServidor() {
        // Método que arranca el servidor y acepta conexiones de clientes

        try (ServerSocket svs = new ServerSocket(2777)) {
            // Crea un ServerSocket que escucha en el puerto 2777
            // try-with-resources asegura que el socket se cierre correctamente

            System.out.println(">>> Servidor escuchando en el puerto " + svs.getLocalPort());
            // Muestra que el servidor está activo y en qué puerto escucha

            // Thread “killer”, que cerrará el servidor tras 5 segundos
            Thread killer = new Thread(() -> {
                System.out.println("Thread killer iniciándose...");
                // Mensaje indicando que el hilo asesino comienza

                try {
                    Thread.sleep(5000);
                    // Espera 5 segundos antes de cerrar el servidor
                } catch (InterruptedException e) {
                    // Ignora interrupciones del hilo
                }

                try {
                    svs.close();
                    // Cierra el ServerSocket, forzando una excepción en accept()
                } catch (IOException e) {
                    // Ignora errores al cerrar el socket
                }
            });
            killer.start();
            // Inicia el hilo “killer”

            while (true) {
                // Bucle infinito para aceptar conexiones de clientes

                Socket sc = svs.accept();
                // Espera a que un cliente se conecte
                // Cuando un cliente se conecta, se crea un socket específico para él

                Thread t = new Thread(() -> {
                    // Crea un hilo para manejar la comunicación con este cliente

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
                         PrintWriter pw = new PrintWriter(sc.getOutputStream(), true);) {
                        // Crea flujos de entrada y salida para el cliente
                        // BufferedReader para leer líneas de texto
                        // PrintWriter con auto-flush para enviar texto inmediatamente

                        String linea;
                        // Variable para almacenar cada línea recibida del cliente

                        while ((linea = br.readLine()) != null) {
                            // Lee líneas del cliente hasta que cierre la conexión

                            System.out.println("Recibido del cliente: " + linea);
                            // Muestra el mensaje recibido por pantalla

                            pw.println(linea.toLowerCase());
                            // Envía al cliente el mismo mensaje en minúsculas
                        }

                    } catch (IOException e) {
                        System.err.println("Error en el servidor " + e.getMessage());
                        // Muestra errores de comunicación con el cliente
                    }
                });
                t.start();
                // Inicia el hilo que atiende al cliente
            }

        } catch (IOException e) {
            // Captura errores del ServerSocket (por ejemplo, cuando killer lo cierra)

            throw new RuntimeException();
            // Lanza una excepción para que el servidor pueda reiniciarse
        }
    }

    public static void main(String[] args) {
        // Método principal donde se inicia el servidor

        while (true) {
            // Bucle infinito para reiniciar el servidor en caso de fallo

            try {
                arrancarServidor();
                // Intenta arrancar el servidor
            } catch (Exception e) {
                System.err.println("Server out of service: " + e.getMessage());
                // Muestra mensaje si el servidor falla

                System.out.println("Restarting in 2 secs...");
                // Indica que el servidor se reiniciará después de 2 segundos

                try {
                    Thread.sleep(2000);
                    // Espera 2 segundos antes de reiniciar
                } catch (InterruptedException ex) {
                    // Ignora interrupciones del hilo
                }
            }
        }
    }
}
