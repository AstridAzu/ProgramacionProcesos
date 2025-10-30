package RepasoExamen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcesosEjemplos1 {
    public static void main(String[] args){
        EjecutarProcesoConBufferedReader();
        ejecutarProceso();

    }
    static void ejecutarProceso(){
        //crear un proceso
        //declarar proceso
        String java= "Java";//DECLARO EL PROGRAMA QUE QUIERO EJECUTAR
        String version = "-version";//DECLARO EL COMANDO DE JAVA QUE QUIERO EJECUTAR
        ProcessBuilder PS= new ProcessBuilder(java,version);//EL PROCESSBUILDER ME RECIBE EL PROGRAMA Y LA COMANDO
        try {
            PS.inheritIO();//HACE QUE ME MUESTRE LOS DATOS EN LA TERMINAL
            PS.start();//INICIO EL PROCESO
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    static void EjecutarProcesoConBufferedReader(){
        String java= "Java";
        String version = "-version";
        ProcessBuilder PB= new ProcessBuilder(java,version);
        try {
            PB.redirectErrorStream(true);//CONVINO LAS SALIDAS DE ERRORES Y MENSAJES EN UN SOLO CANAL
            Process p = PB.start();//guardamos el proceso en Process
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            //uso el while para leer las lineas del proceso
            while ((line = br.readLine()) != null) {
                //imprimo la linea leida
                System.out.println(line);
            }


        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }
    //inicializar el proceso

}
