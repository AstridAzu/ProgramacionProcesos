package Synchronized;

public class ThreadDemonio {
// Clase principal que demuestra el uso de threads normales y threads demonio

    public static void main(String[] args) {
        long tiempo = System.currentTimeMillis() + 10000;
        // Calcula el tiempo actual + 10 segundos (en milisegundos)
        // Los hilos t1 y t2 se ejecutarán mientras el tiempo actual sea menor a este valor

        // Thread t1 imprime "t1: saludo" cada 500ms durante 10 segundos
        Thread t1 = new Thread(() -> {
            while (tiempo > System.currentTimeMillis()) {
                System.out.println("t1: saludo");
                try {
                    Thread.sleep(500);
                    // Pausa el hilo 500 milisegundos para no saturar la consola
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                    // Lanza excepción si el hilo es interrumpido
                }
            }
        });

        // Thread t2 imprime "t2: saludo" cada 1000ms durante 10 segundos
        Thread t2 = new Thread(() -> {
            while (tiempo > System.currentTimeMillis()) {
                System.out.println("t2: saludo");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Thread latido imprime "Boom Boom" cada 100ms infinitamente
        Thread latido = new Thread(() -> {
            while (true) {
                System.out.println("Boom Boom");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Este Thread seguirá vivo solo mientras haya hilos de usuario activos
        latido.setDaemon(true);
        // ❗ Importante: los hilos demonio terminan automáticamente cuando todos los hilos de usuario finalizan

        System.out.println("Inicio ejecución Threads");

        t1.start();
        // Inicia el hilo t1
        // t1.setPriority(Thread.MAX_PRIORITY) se podría usar para darle máxima prioridad

        t2.start();
        // Inicia el hilo t2

        latido.start();
        // Inicia el hilo demonio latido

        System.out.println("Threads ejecutándose");
        // Mensaje informativo de que los hilos fueron iniciados
    }
}
