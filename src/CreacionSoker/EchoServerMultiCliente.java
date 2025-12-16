package CreacionSoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// Servidor multicliente
public class EchoServerMultiCliente {
    public static void main(String[] args) {
        int puerto;
        try {
            puerto = Validacion.validarPuerto(args);
        } catch (Exception e) {
            System.err.println("Error en el puerto: " + e.getMessage());
            return;
        }
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor MULTICLIENTE escuchando en el puerto: " + puerto);
            // Bucle infinito para aceptar clientes
            while (true) {
                Socket socket = servidor.accept();
                // Crear un hilo para manejar al cliente
                new ManejadorCliente(socket).start();

            }

        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
// Hilo que atiende a cada cliente por separado
class ManejadorCliente extends Thread {
    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getName() + "]"+" Cliente conectado: "  + socket.getInetAddress());

        try {
            //recibe inputs
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            //imprime imputs
            PrintWriter pw = new PrintWriter(
                    socket.getOutputStream(), true
            );

            String mensaje;
            //lee linea a linea hasta que ya no haya que leer
            while ((mensaje = bf.readLine()) != null) {
                System.out.println("[" + Thread.currentThread().getName() + "] Recibido de cliente: " + mensaje);
                pw.println(mensaje.toLowerCase());
            }
            this.socket.close();
        } catch (IOException e) {
            System.err.println("[" + Thread.currentThread().getName() + "] Error con cliente: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("[" + Thread.currentThread().getName() + "] Cliente desconectado");
        }
    }
}
