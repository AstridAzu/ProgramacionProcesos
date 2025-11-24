package CreacionSoker;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoClientMultiHilo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Mensajes escritos por el usuario ANTES de crear clientes
        System.out.print("Mensaje para Cliente 1: ");
        String mensaje1 = sc.nextLine();

        System.out.print("Mensaje para Cliente 2: ");
        String mensaje2 = sc.nextLine();
        System.out.print("Mensaje para Cliente 3: ");
        String mensaje3 = sc.nextLine();
        System.out.print("Mensaje para Cliente 4: ");
        String mensaje4 = sc.nextLine();

        Runnable cliente1 = () -> ejecutarCliente(mensaje1);
        Runnable cliente2 = () -> ejecutarCliente(mensaje2);
        Runnable cliente3 = () -> ejecutarCliente(mensaje3);
        Runnable cliente4 = () -> ejecutarCliente(mensaje4);

        new Thread(cliente1).start();

        new Thread(cliente2).start();
        new Thread(cliente3).start();
        new Thread(cliente4).start();
    }

    private static void ejecutarCliente(String mensaje) {
        try {
            Socket socket = new Socket("localhost", 8000);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enviando mensaje:  " );
            pw.println(mensaje);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

