package CreacionSoker;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

class Validacion{
    public static int validarPuerto(String[] args){
        // Verificamos que nos llega un solo argumento
        if(args.length!=1){
            System.err.println("Debe ingresar un único argumento");
            throw new IllegalArgumentException();
        }
        // Conversión del argumento de String a Integer
        int puerto = Integer.parseInt(args[0]);
        // Validamos que el puerto es válido (no es un puerto reservado)
        if (puerto<1024 || puerto>65535){
            throw new IllegalArgumentException("Debe ingresar un puerto entre 1024 y 65535");
        }
        // Si todo ha ido bien, devolvemos el puerto
        return puerto;
    }
}

public class EchoServer {
    public static void main(String[] args) {
        int puerto = 0;
        try{
            puerto = Validacion.validarPuerto(args);
        }catch (Exception e){
            System.err.println("Error en el puerto: " + e.getMessage());
            System.exit(1);
        }

        // Aranque del servidor
        try (ServerSocket servidor = new ServerSocket(puerto)){
            System.out.println("Servidor iniciado: esperando conexiones en el puerto: " + puerto);

            Socket socket = servidor.accept();

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            PrintWriter pw = new PrintWriter(out, true);

            String entrada;
            while ((entrada = bf.readLine()) != null) {
                System.out.println("Recibido de cliente: " + entrada);
                pw.println(entrada.toLowerCase());
            }

        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
