package Synchronized;


public class CondicionDeCarreraMonitorSynchronized {

    private static int contador = 0;

    //SYNCRONIZED -> BLOQUEA LA CLASE(EN ESTE CASO) / OBJETO PARA QUE LOS MÉTODOS NO SE EJECUTEN AL MISMO TIEMPO
    public static synchronized void incrementarContador(int num){

        System.out.println("Entrando en incrementarContador");
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        contador += num;
        System.out.println("Saliendo de incrementarContador");
    }

    public static synchronized int getContador(){

        System.out.println("Entrando en getContador");
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Saliendo de getContador");
        return contador;
    }

    public static void main(String[] args) {

        //LOS GUIONES BAJOS SON COMO PUNTOS DE LOS MILES IMAGINARIOS (NO AFECTA EN NADA)
        final int ITERACIONES = 1_000_000;
        final int VALOR = 10;

        Thread accesor1 = new Thread(() -> {
            getContador();
        });

        Thread accesor2 = new Thread(() -> {
            incrementarContador(VALOR);
        });

        accesor1.start();
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        accesor2.start();

    }
}
