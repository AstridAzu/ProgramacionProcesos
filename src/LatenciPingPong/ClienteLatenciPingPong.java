package LatenciPingPong;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class ClienteLatenciPingPong {
    public static void main(String[] args) {
        try (DatagramSocket dgs = new DatagramSocket()) {
            String ping = "ping";
            byte[] data = ping.getBytes();
            InetAddress ip = InetAddress.getByName("localhost");
            int port = 2300;
            DatagramPacket dgp = new DatagramPacket(data, data.length, ip, port);

            for (int i = 0; i < 10; i++) {
                long tiempoInicio = System.nanoTime();
                try {
                    dgs.send(dgp);
                    byte[] dataACK = new byte[1024];
                    DatagramPacket dgpACK = new DatagramPacket(dataACK, dataACK.length);
                    dgs.receive(dgpACK);
                    long tiempoFinal = System.nanoTime();
                    System.out.println("Número de ping: " + (i + 1) + " latencia: " + String.format("%.2f", (tiempoFinal - tiempoInicio) / 1000000.0) + "ms");
                } catch (SocketTimeoutException e) {
                    System.err.println("Posible pérdida de paquetes: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}