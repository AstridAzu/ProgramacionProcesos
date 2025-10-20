package Synchronized;
// 2. Implementación de thread implementando interfaz Runnable
class ThreadImplements implements Runnable{

    // Este es el código que se ejecuta cuando lanzo el thread
    @Override
    public void run(){
        System.out.println("El nombre del thread es: " + Thread.currentThread().getName());
        System.out.println("El ID del thread es: " + Thread.currentThread().threadId());
    }
}
public class CreacionThreads extends Thread {
    // Este es el código que se ejecuta cuando lanzo el thread
    @Override
    public void run(){
        System.out.println("El nombre del thread es: " + Thread.currentThread().getName());
        System.out.println("El ID del thread es: " + Thread.currentThread().threadId());
    }

    public static void main (String[] args) {
        // 3. Instanciación de thread mediante expresión lambda
        Thread t1 = new Thread(() -> {
            System.out.println("El nombre del thread es: " + Thread.currentThread().getName());
            System.out.println("El ID del thread es: " + Thread.currentThread().threadId());
        }, "Thread Lambda");

        Thread t2 = new Thread(new CreacionThreads(), "Thread Herencia");

        Thread t3 = new Thread(new ThreadImplements(), "Thread Implements");

        t1.start();
        t2.start();
        t3.start();
    }
}
