import java.io.IOException;

public class U1P03EjecutarSumador {
    private static final String JAVA = "java";
    private static  final  String CP ="-cp";
    private static final String  CLASSPATH="C:\\Users\\AlumnoAfternoon\\IdeaProjects\\PSP\\out\\production\\PSP";
    private static final String CLASE = "edu.thepower.u1.programacionmultiproceso.U1P03Sumador";
    //ruta en mi compu
    private static final String MYCLASSPAH="C:\\Users\\astrid\\IdeaProjects\\PSP\\out\\production\\PSP";
    private static final String test = "U1P03Sumador";

    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder(JAVA,CP,MYCLASSPAH,test,"10","20");

        try {
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT); //Tenemos la salida Standar
            pb.redirectError(ProcessBuilder.Redirect.INHERIT); //Tenemos la salida Error
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
