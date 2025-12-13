package LatenciPingPong;

import java.io.IOException;
// Importa IOException para manejar errores de entrada/salida, típicos en operaciones de red

import java.net.DatagramPacket;
// Importa DatagramPacket, que representa un paquete de datos UDP
// Se usa tanto para recibir como para enviar información

import java.net.DatagramSocket;
// Importa DatagramSocket, que permite la comunicación usando el protocolo UDP

import java.net.InetAddress;
// Importa InetAddress, que representa una dirección IP
// Se utiliza para identificar la IP del cliente

public class ServidorLatenciaPingPong {
// Define la clase ServidorLatenciaPingPong
// Este programa actúa como un servidor UDP que responde a mensajes "ping"

    public static void main(String[] args) {
        // Método principal, punto de entrada del programa

        try (DatagramSocket dgs = new DatagramSocket(2300)) {
            // Crea un socket UDP escuchando en el puerto 2300
            // try-with-resources asegura que el socket se cierre correctamente

            byte[] data = new byte[1024];
            // Crea un buffer de 1024 bytes para almacenar los datos recibidos

            DatagramPacket dgp;
            // Declara una variable DatagramPacket para recibir los paquetes entrantes

            while (true) {
                // Bucle infinito para que el servidor esté siempre activo

                dgp = new DatagramPacket(data, data.length);
                // Crea un paquete UDP vacío que se usará para recibir datos

                dgs.receive(dgp);
                // Espera a que llegue un paquete del cliente
                // El programa se queda bloqueado aquí hasta recibir datos

                String mensaje = new String(dgp.getData(), 0, dgp.getLength());
                // Convierte los bytes recibidos en un String
                // dgp.getData(): obtiene el array de bytes
                // 0: posición inicial
                // dgp.getLength(): número real de bytes recibidos

                System.out.println("Mensaje recibido: " + mensaje);
                // Muestra en pantalla el mensaje enviado por el cliente

                String respuesta = "Pong";
                // Define el mensaje de respuesta que enviará el servidor

                byte[] dataRespuesta = respuesta.getBytes();
                // Convierte la respuesta "Pong" en bytes para enviarla por UDP

                InetAddress ip = dgp.getAddress();
                // Obtiene la dirección IP del cliente que envió el paquete

                int puerto = dgp.getPort();
                // Obtiene el puerto del cliente que envió el paquete

                DatagramPacket dgpRespuesta =
                        new DatagramPacket(dataRespuesta, dataRespuesta.length, ip, puerto);
                // Crea el paquete UDP de respuesta
                // Incluye los datos, su tamaño, la IP y el puerto del cliente

                dgs.send(dgpRespuesta);
                // Envía la respuesta "Pong" al cliente
            }
        } catch (IOException e) {
            // Captura errores relacionados con la red o entrada/salida

            System.err.println("Error: " + e.getMessage());
            // Muestra el mensaje de error por la consola
        }
    }
}
