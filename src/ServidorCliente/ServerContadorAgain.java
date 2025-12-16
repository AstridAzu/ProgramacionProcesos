package ServidorCliente;

import java.io.BufferedReader;
// Permite leer texto línea por línea desde un flujo de entrada

import java.io.IOException;
// Maneja excepciones de entrada/salida

import java.io.InputStreamReader;
// Convierte un InputStream en Reader de caracteres

import java.io.PrintWriter;
// Permite enviar texto a un flujo de salida de forma sencilla

import java.net.ServerSocket;
// Permite crear un servidor que escucha conexiones de clientes

import java.net.Socket;
// Permite representar la conexión con un cliente

import java.util.concurrent.atomic.AtomicInteger;
// Permite manejar un contador seguro en entornos concurrentes

public class ServerContadorAgain {
// Clase principal que implementa un servidor concurrente de contador

    private static AtomicInteger contador = new AtomicInteger(0);
    // Contador global seguro para acceso concurrente
    // AtomicInteger permite incrementar/decrementar de forma atómica

    // Clase interna que gestiona la comunicación con cada cliente
    static class GestorClientesContador implements Runnable {

        private Socket socket;
        // Socket asociado a este cliente

        private String cliente;
        // Identificación del cliente (IP:puerto)

        private static final String CONTADOR_ACTUALIZADO = "Contador actualizado. ";
        private static final String VALOR_CONTADOR = "Valor: ";
        // Constantes para las respuestas al cliente

        public GestorClientesContador(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            cliente = socket.getInetAddress() + ":" + socket.getPort();
            // Identifica al cliente con IP y puerto
            System.out.println("[" + Thread.currentThread().getName() + "] IP: " + cliente);
            // Muestra qué hilo atiende a qué cliente

            try (
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {
                // BufferedReader para leer mensajes del cliente
                // PrintWriter para enviar respuestas

                String comando;
                String respuesta;
                boolean continuar = true;
                // Variable para controlar el bucle mientras el cliente no diga "bye"

                while (continuar && (comando = br.readLine()) != null) {
                    // Lee comandos mientras el cliente siga conectado

                    respuesta = switch (comando.trim().toLowerCase()) {
                        case "inc" -> CONTADOR_ACTUALIZADO + VALOR_CONTADOR + incrementarContador();
                        // Incrementa el contador y devuelve la respuesta
                        case "dec" -> CONTADOR_ACTUALIZADO + VALOR_CONTADOR + decrementarContador();
                        // Decrementa el contador y devuelve la respuesta
                        case "get" -> VALOR_CONTADOR + getContador();
                        // Devuelve el valor actual del contador
                        case "bye" -> {
                            continuar = false;
                            yield "Bye";
                            // Finaliza la comunicación con este cliente
                        }
                        default -> "Comando no válido";
                        // Respuesta ante un comando desconocido
                    };

                    pw.println(respuesta);
                    // Envía la respuesta al cliente
                }

            } catch (IOException e) {
                System.err.println("Error en la conexión: " + e.getMessage());
                // Captura errores de lectura/escritura
            }

            System.out.println("Conexión finalizada de " + cliente);
            // Indica que se cerró la conexión con este cliente
        }
    }

    // Métodos para manipular el contador atómicamente
    public static int getContador() {
        return contador.get();
    }

    public static int incrementarContador() {
        return contador.incrementAndGet();
    }

    public static int decrementarContador() {
        return contador.decrementAndGet();
    }

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(4000)) {
            System.out.println("Servidor escuchando peticiones en el puerto: 4000");
            // Informa que el servidor está listo

            // Bucle infinito para aceptar múltiples clientes
            while (true) {
                Socket socket = serverSocket.accept();
                // Espera hasta que un cliente se conecte

                // Crea un thread para manejar a este cliente
                Thread thread = new Thread(new GestorClientesContador(socket));
                thread.start();
                // Inicia el thread y continúa escuchando nuevos clientes
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            // Captura errores al crear o usar el ServerSocket
        }
    }
}
