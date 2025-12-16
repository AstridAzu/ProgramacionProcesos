package PoolEcho;
import java.io.*;
// Importa clases necesarias para entrada y salida
// (BufferedReader, InputStreamReader, PrintWriter, IOException)

import java.net.ServerSocket;
// Importa ServerSocket para crear un servidor que escucha conexiones

import java.net.Socket;
// Importa Socket para representar la conexión con cada cliente

import java.util.concurrent.ExecutorService;
// Importa la interfaz ExecutorService para manejar pools de hilos

import java.util.concurrent.Executors;
// Importa la clase Executors para crear un pool de hilos

public class PoolServer {
// Define la clase PoolServer, que implementa un servidor concurrente

    public static void main(String[] args) {
        // Método principal, punto de inicio del programa

        ExecutorService pool = Executors.newFixedThreadPool(5);
        // Crea un pool de hilos con tamaño fijo de 5 hilos
        // Permite atender hasta 5 clientes simultáneamente

        try (ServerSocket svs = new ServerSocket(2777)) {
            // Crea un servidor que escucha en el puerto 2777
            // try-with-resources asegura que el socket se cierre automáticamente

            System.out.println("Escuchando en el puerto " + svs.getLocalPort());
            // Muestra el puerto en el que el servidor está escuchando

            while (true) {
                // Bucle infinito para aceptar múltiples clientes

                Socket sc = svs.accept();
                // Espera hasta que un cliente se conecte
                // Crea un socket para comunicarse con el cliente

                pool.submit(() -> {
                    // Envía una tarea al pool de hilos
                    // Cada cliente será atendido por un hilo distinto

                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(sc.getInputStream()));
                         PrintWriter pw = new PrintWriter(
                                 sc.getOutputStream(), true)) {
                        // Obtiene flujos de entrada y salida del cliente
                        // BufferedReader permite leer texto línea por línea
                        // PrintWriter permite enviar texto y auto-flush

                        String linea;
                        // Variable para almacenar la línea leída del cliente

                        while ((linea = br.readLine()) != null) {
                            // Bucle para leer continuamente mientras el cliente envíe datos

                            System.out.println("Recibido del cliente: " + linea);
                            // Muestra el mensaje recibido por consola

                            pw.println(linea.toLowerCase());
                            // Envía al cliente la misma línea en minúsculas
                        }

                    } catch (IOException e) {
                        // Captura errores de lectura/escritura con este cliente

                        System.err.println("Error en el servidor " + e.getMessage());
                    }
                });
            }

        } catch (IOException e) {
            // Captura errores al crear el ServerSocket

            System.err.println("Error en el servidor " + e.getMessage());
        }
    }
}
