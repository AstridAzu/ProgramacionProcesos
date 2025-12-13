package LatenciPingPong;
import java.io.IOException;
// Importa la clase IOException, que se usa para manejar errores de entrada/salida
// (por ejemplo, problemas de red al enviar o recibir datos)

import java.net.DatagramPacket;
// Importa DatagramPacket, que representa un paquete de datos UDP
// Contiene la información que se envía o se recibe: datos, tamaño, IP y puerto

import java.net.DatagramSocket;
// Importa DatagramSocket, que es el “socket” para comunicación UDP
// Permite enviar y recibir DatagramPacket

import java.net.InetAddress;
// Importa InetAddress, que representa una dirección IP
// Se usa para obtener la IP del servidor (localhost en este caso)

import java.net.SocketTimeoutException;
// Importa SocketTimeoutException, que se lanza cuando se supera el tiempo de espera
// al recibir un paquete UDP

public class ClienteLatenciPingPong {
// Define la clase ClienteLatenciPingPong, que implementa un cliente UDP
// para medir la latencia usando mensajes tipo “ping”

    public static void main(String[] args) {
        // Método principal, punto de entrada del programa

        try (DatagramSocket dgs = new DatagramSocket()) {
            // Crea un DatagramSocket UDP
            // No se especifica puerto, así que el sistema asigna uno automáticamente
            // try-with-resources asegura que el socket se cierre al finalizar

            String ping = "ping";
            // Define el mensaje que se enviará al servidor

            byte[] data = ping.getBytes();
            // Convierte el texto "ping" en un arreglo de bytes
            // UDP solo envía datos en forma de bytes

            InetAddress ip = InetAddress.getByName("localhost");
            // Obtiene la dirección IP asociada a "localhost"
            // Normalmente es 127.0.0.1 (la misma máquina)

            int port = 2300;
            // Define el puerto del servidor UDP al que se enviará el ping

            DatagramPacket dgp = new DatagramPacket(data, data.length, ip, port);
            // Crea el paquete UDP que se enviará
            // Incluye los datos, su tamaño, la IP de destino y el puerto

            for (int i = 0; i < 10; i++) {
                // Bucle que envía 10 mensajes ping al servidor

                long tiempoInicio = System.nanoTime();
                // Guarda el instante exacto (en nanosegundos) antes de enviar el ping
                // Se usa para calcular la latencia

                try {
                    dgs.send(dgp);
                    // Envía el paquete ping al servidor mediante UDP

                    byte[] dataACK = new byte[1024];
                    // Crea un buffer de 1024 bytes para recibir la respuesta del servidor

                    DatagramPacket dgpACK = new DatagramPacket(dataACK, dataACK.length);
                    // Crea un paquete UDP vacío que se usará para recibir el ACK

                    dgs.receive(dgpACK);
                    // Espera a recibir la respuesta del servidor
                    // El programa se bloquea aquí hasta que llega un paquete

                    long tiempoFinal = System.nanoTime();
                    // Guarda el instante exacto cuando se recibe la respuesta

                    double latenciaMs = (tiempoFinal - tiempoInicio) / 1_000_000.0;
                    // Calcula la latencia
                    // Resta el tiempo inicial al final (nanosegundos)
                    // Divide entre 1.000.000 para convertir a milisegundos

                    System.out.println(
                            "Número de ping: " + (i + 1) +
                                    " latencia: " + String.format("%.2f", latenciaMs) + "ms"
                    );
                    // Muestra el número de ping enviado
                    // String.format("%.2f", latenciaMs) formatea la latencia con 2 decimales
                    // "ms" indica que la unidad es milisegundos

                } catch (SocketTimeoutException e) {
                    // Captura el error si el servidor no responde dentro del tiempo esperado
                    // Esto indica una posible pérdida de paquetes UDP

                    System.err.println("Posible pérdida de paquetes: " + e.getMessage());
                    // Muestra un mensaje de error en la consola
                }
            }
        } catch (IOException e) {
            // Captura errores generales de red o entrada/salida

            System.err.println("Error: " + e.getMessage());
            // Muestra el mensaje de error
        }
    }
}
