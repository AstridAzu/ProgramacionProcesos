package CreacionSoker;

import java.io.*;
import java.net.*;

public class Cliente3 {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 5000;

        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor. Comandos disponibles: inc, desc, get, bye");

            String userInput;
            while ((userInput = console.readLine()) != null) {
                out.println(userInput);
                String respuesta = in.readLine();
                System.out.println("Servidor: " + respuesta);

                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}