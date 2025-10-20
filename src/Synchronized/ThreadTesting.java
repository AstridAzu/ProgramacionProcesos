package Synchronized;


/*
 Solo usa Thread.currentThread() para obtener el hilo actual,
  que en este caso es el hilo principal (main)
   que la JVM crea automáticamente al iniciar el programa.
*/
public class ThreadTesting {

    public static void main(String[] args){
        System.out.println("El nombre del thread es: " + Thread.currentThread().getName());
        System.out.println("El ID del thread es: " + Thread.currentThread().threadId());
        System.out.println("La prioridad del thread es: " + Thread.currentThread().getPriority());
        System.out.println("El estado del thread es: " + Thread.currentThread().getState());
        System.out.println("El grupo del thread es: " + Thread.currentThread().getThreadGroup());
    }
}
