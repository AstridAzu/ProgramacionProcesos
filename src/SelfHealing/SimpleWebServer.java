package SelfHealing;
import java.io.BufferedReader;
// Permite leer texto de un flujo de entrada de manera eficiente.
// Se usa para leer la solicitud HTTP que envía el cliente.

import java.io.IOException;
// Captura errores de entrada/salida que pueden ocurrir al leer o escribir datos del socket.

import java.io.InputStreamReader;
// Convierte un InputStream (flujo de bytes) en un flujo de caracteres, permitiendo leer texto.

import java.io.PrintWriter;
// Permite enviar texto de forma sencilla a un flujo de salida, en este caso al cliente.

import java.net.ServerSocket;
// Permite crear un servidor TCP que escucha en un puerto específico.

import java.net.Socket;
// Representa la conexión individual con cada cliente que se conecta al servidor.

import java.time.LocalDateTime;
// Permite obtener la fecha y hora actuales.

import java.time.format.DateTimeFormatter;
// Permite dar formato legible a la fecha y hora para mostrarlo en la respuesta.

import java.util.concurrent.atomic.AtomicInteger;
// AtomicInteger permite mantener un contador seguro para múltiples hilos
// evitando condiciones de carrera (por ejemplo, para contar visitas simultáneas).

public class SimpleWebServer {
// Define la clase SimpleWebServer que implementa un servidor web básico HTTP.

    private static final String HTML = """ 
            <html>
                <head>
                    <title>Servidor web simple</title>
                </head>
                <body>
                    <h1>¡Hola mundo!</h1>
                    <p>Esto es un servidor web simple.</p>
                    <p>%s</p>
                </body>
            </html>""";
// Plantilla HTML donde %s será reemplazado por contenido dinámico
// Ejemplo: número de visitas, fecha actual o nombre del servidor.

    private static final int PUERTO = 8080;
// Puerto donde el servidor escuchará solicitudes HTTP.

    private static final String NOMBRE_SERVIDOR = "Servidor Web Simple";
// Nombre del servidor que se puede mostrar en las respuestas.

    private static AtomicInteger contador = new AtomicInteger();
// Contador seguro de visitas en entornos multihilo.

    private static final String HTTP_ESTADO_OKAY = "200 OK";
    private static final String HTTP_ESTADO_NOT_FOUND = "404 Not Found";
    private static final String HTTP_ESTADO_NOT_ALLOWED = "405 Method Not Allowed";
// Códigos de estado HTTP que se usarán para responder a las solicitudes del cliente.

    private static final DateTimeFormatter FECHA_ACTUAL = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
// Formato de fecha y hora para mostrar en la respuesta HTML.

    public static void main(String[] args) {
        // Método principal, punto de entrada del programa.

        try (ServerSocket svs = new ServerSocket(PUERTO)) {
            // Crea un ServerSocket que escucha en el puerto 8080.
            // try-with-resources asegura que el socket se cierre automáticamente al finalizar o ante excepción.

            System.out.println("Servidor escuchando en el puerto " + PUERTO);
            // Mensaje en consola indicando que el servidor está activo.

            while (true) {
                // Bucle infinito para aceptar conexiones continuamente.

                Socket socket = svs.accept();
                // Bloquea hasta que un cliente se conecte.
                // Crea un Socket que representa la conexión con ese cliente.

                Thread t = new Thread(() -> atenderSolicitud(socket));
                // Cada cliente se atiende en un hilo separado para no bloquear la aceptación de nuevas conexiones.

                t.start();
                // Inicia el hilo que atenderá la solicitud del cliente.
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            // Muestra mensaje si ocurre un error al crear o usar el ServerSocket.
        }
    }

    private static void atenderSolicitud(Socket socket) {
        // Método que procesa la solicitud HTTP de un cliente.

        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pw = new PrintWriter(socket.getOutputStream())) {
            // Crea un BufferedReader para leer la solicitud del cliente y un PrintWriter para enviar la respuesta.

            String linea = br.readLine();
            // Lee la primera línea de la solicitud HTTP, por ejemplo: "GET / HTTP/1.1".

            if (linea != null && !linea.isBlank()) {
                // Verifica que la solicitud no sea nula ni vacía.

                System.out.println(linea);
                // Muestra la línea de solicitud en consola.

                String[] partes = linea.split("\\s");
                // Divide la línea por espacios para separar método, ruta y versión HTTP.

                String metodo = partes[0];
                // Primer elemento → método HTTP (GET, POST, etc.).

                String ruta = partes.length > 1 ? partes[1].trim().toLowerCase() : "/";
                // Segundo elemento → ruta solicitada, convertida a minúsculas. "/" por defecto.

                while ((linea = br.readLine()) != null && !linea.isBlank()) {
                    System.out.println(linea);
                }
                // Lee y descarta las cabeceras HTTP hasta la línea vacía.

                String estado = HTTP_ESTADO_OKAY;
                String respuesta = "";
                // Variables para el código de estado y el contenido de la respuesta.

                if (metodo.trim().equalsIgnoreCase("get")) {
                    // Procesa solo solicitudes GET.

                    if (ruta.contains("favicon")) {
                        contador.incrementAndGet();
                    }
                    // Ignora solicitudes de favicon.ico para el contador de visitas.

                    System.out.println("Devolviendo respuesta HTML: ");
                    // Mensaje indicando que se generará la respuesta HTML.

                    respuesta = switch (ruta) {
                        case "/" -> "Eres el visitante número: " + contador.incrementAndGet();
                        // Raíz: devuelve número de visitas.

                        case "/fecha", "/fecha/" -> "Fecha y hora actuales: " + FECHA_ACTUAL.format(LocalDateTime.now());
                        // Ruta /fecha: devuelve fecha y hora actuales.

                        case "/nombre", "/nombre/" -> "El nombre del servidor es: " + NOMBRE_SERVIDOR;
                        // Ruta /nombre: devuelve el nombre del servidor.

                        default -> {
                            estado = HTTP_ESTADO_NOT_FOUND;
                            yield "Error: ruta no permitida";
                        }
                        // Cualquier otra ruta → error 404.
                    };

                } else {
                    estado = HTTP_ESTADO_NOT_ALLOWED;
                    respuesta = "Error: método no permitido";
                    // Métodos distintos de GET → error 405.
                }

                // Construcción de la respuesta HTML completa:
                StringBuffer sb = new StringBuffer();
                // Crea un buffer mutable para construir la cadena de respuesta.
                sb.append(String.format(HTML, respuesta));
                // Inserta el mensaje dinámico (visitas, fecha o nombre) en la plantilla HTML usando %s.
                // Esto genera el HTML completo que se enviará al cliente.

                // Envío de la respuesta HTTP:
                pw.println("HTTP/1.1 " + estado);
                // Primera línea HTTP con el código de estado.

                pw.println("Content-Type: text/html;charset=UTF-8");
                // Cabecera indicando que el contenido es HTML en UTF-8.

                pw.println("Content-Length: " + sb.toString().getBytes().length);
                // Cabecera indicando la longitud en bytes del contenido.

                pw.println();
                // Línea en blanco que separa cabeceras del cuerpo.

                pw.println(sb.toString());
                // Envía el cuerpo HTML completo.

                pw.flush();
                // Fuerza el envío de todos los datos al cliente.
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor " + e.getMessage());
            // Captura errores de lectura/escritura del cliente.
        }
    }
}
