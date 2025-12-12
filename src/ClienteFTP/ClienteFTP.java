package ClienteFTP;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;

// https://sftpcloud.io/tools/free-ftp-server

public class ClienteFTP {


    public static void main(String[] args) {
        FTPClient ftp = new FTPClient();

        try {
            // 1º Conectarse
            ftp.connect("eu-central-1.sftpcloud.io", 21);
            System.out.println("Conectado al servidor");

            // 2º Accedemos
            if (ftp.login("e341fa799bae4d1aa612f48f53109042", "xpa6mjLyBKzK2fiP6Bw9CgPiCLsuA2E2")) {
                System.out.println("Usuario autenticado. Accediendo al servidor...");
                // 3º Activar modo pasivo (cliente a servidor), los datos que se envían son en binario
                ftp.enterLocalPassiveMode();
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

                // 4º Enviar archivo
                InputStream is = new FileInputStream("resources/error.txt");
                String ficheroRemoto = "archivo_remoto.txt";
                if (ftp.storeFile(ficheroRemoto, is)){
                    System.out.println("Se ha creado el archivo en el servidor");
                }

                // 5º Listado del contenido del servidor FTP
                FTPFile[] array = ftp.listFiles();
                for(FTPFile f : array){
                    System.out.println(f.getName());
                }

                // 6º Descarga del archivo alojado en el servidor
                OutputStream os = new FileOutputStream("resources/archivo_descargado.txt");
                if(ftp.retrieveFile(ficheroRemoto, os)){
                    System.out.println("Archivo descargado correctamente del servidor");
                } else {
                    System.err.println("Error al descargar el archivo del servidor");
                }
            } else {
                System.err.println("Error al acceder al servidor");
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}