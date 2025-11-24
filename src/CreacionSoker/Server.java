package CreacionSoker;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.*;

public class Server {
    private static int contador = 0;
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        final int PUERTO = 5000;

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en puerto " + PUERTO);

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String comando;
                while ((comando = in.readLine()) != null) {
                    String respuesta = procesarComando(comando.trim().toLowerCase());
                    out.println(respuesta);

                    if (comando.equalsIgnoreCase("bye")) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String procesarComando(String comando) {
            lock.lock();
            try {
                switch (comando) {
                    case "inc":
                        contador++;
                        return "OK - Contador: " + contador;
                    case "desc":
                        contador--;
                        return "OK - Contador: " + contador;
                    case "get":
                        return "Contador actual: " + contador;
                    case "bye":
                        return "Adiós";
                    default:
                        return "Comando no válido";
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
