package LatenciPingPong;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorLatenciaPingPong {
    public static void main(String[] args) {
        try(DatagramSocket dgs = new DatagramSocket(2300)){
            byte[] data = new byte[1024];
            DatagramPacket dgp;
            while(true){
                dgp = new DatagramPacket(data, data.length);
                dgs.receive(dgp);
                String mensaje = new String(dgp.getData(), 0, dgp.getLength());
                System.out.println("Mensaje recibido: " + mensaje);
                String respuesta = "Pong";
                byte[] dataRespuesta = respuesta.getBytes();
                InetAddress ip = dgp.getAddress();
                int puerto = dgp.getPort();
                DatagramPacket dgpRespuesta = new DatagramPacket(dataRespuesta, dataRespuesta.length, ip, puerto);
                dgs.send(dgpRespuesta);
            }
        } catch(IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}