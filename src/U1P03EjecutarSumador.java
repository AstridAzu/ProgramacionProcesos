import java.io.IOException;

public class U1P03EjecutarSumador {
    private static final String JAVA = "java";
    private static  final  String CP ="-cp";
    private static final String  CLASSPATH="C:\\Users\\AlumnoAfternoon\\IdeaProjects\\PSP\\out\\production\\PSP";
    private static final String CLASE = "edu.thepower.u1.programacionmultiproceso.U1P03Sumador";
    //ruta en mi compu C:\Users\astrid\IdeaProjects\ProgramacionProcesos\out\production
    private static final String MYCLASSPAH="C:\\Users\\astrid\\IdeaProjects\\ProgramacionProcesos\\out\\production\\ProgramacionProcesos";

    // solo sirve para procesos esta ruta C:\Users\astrid\IdeaProjects\ProgramacionProcesos\out\production\ProgramacionProcesos
    private static final String sumador = "U1P03Sumador";

    public static void main(String[] args) {

        ProcessBuilder pb = new ProcessBuilder(JAVA,CP,MYCLASSPAH,sumador,"10","20");
        try {
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT); //Tenemos la salida Standar
            pb.redirectError(ProcessBuilder.Redirect.INHERIT); //Tenemos la salida Error
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
