package HechoServer;

import java.io.*;
// Importa todas las clases del paquete java.io necesarias para manejar
// entrada y salida de datos (InputStream, OutputStream, BufferedReader, PrintWriter, etc.)

import java.net.Socket;
// Importa la clase Socket, que permite crear una conexión TCP
// entre el cliente y el servidor

import java.util.Scanner;
// Importa Scanner, que se utiliza para leer datos introducidos por el usuario
// desde el teclado (entrada estándar)

public class HechoCliente {
// Define la clase HechoCliente
// Este programa actúa como un cliente TCP que se conecta a un servidor

    public static Scanner sc = new Scanner(System.in);
    // Declara un Scanner estático asociado a System.in
    // Se usa para leer texto que el usuario escribe en la consola

    public static void main(String[] args) {
        // Método principal, punto de entrada del programa

        try (Socket socket = new Socket("localhost", 1025)) {
            // Crea un socket cliente y se conecta al servidor
            // "localhost" indica que el servidor está en la misma máquina
            // 1025 es el puerto donde el servidor está escuchando
            // try-with-resources asegura que el socket se cierre automáticamente

            OutputStream os = socket.getOutputStream();
            // Obtiene el flujo de salida del socket
            // Se usa para enviar datos al servidor

            PrintWriter pw = new PrintWriter(os, true);
            // Crea un PrintWriter para enviar texto al servidor
            // El parámetro true activa el auto-flush, enviando los datos inmediatamente

            InputStream is = socket.getInputStream();
            // Obtiene el flujo de entrada del socket
            // Se usa para recibir datos enviados por el servidor

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // Envuelve el InputStream en un InputStreamReader y luego en un BufferedReader
            // Esto permite leer texto línea por línea de forma eficiente

            String entrada;
            // Declara una variable para almacenar cada línea escrita por el usuario

            do {
                // Inicia un bucle que se ejecuta al menos una vez

                entrada = sc.nextLine();
                // Lee una línea de texto introducida por el usuario desde el teclado

                pw.println(entrada);
                // Envía la línea escrita al servidor a través del socket

                System.out.println("Recibido del servidor: " + br.readLine());
                // Lee la respuesta del servidor (una línea de texto)
                // y la muestra por pantalla

            } while (!entrada.equals("0"));
            // El bucle se repite mientras el usuario no escriba "0"
            // Cuando se escribe "0", se envía al servidor y el programa termina

        } catch (IOException e) {
            // Captura excepciones relacionadas con la entrada/salida o la red

            throw new RuntimeException(e);
            // Convierte la excepción en una RuntimeException y detiene el programa
        }
    }
}


