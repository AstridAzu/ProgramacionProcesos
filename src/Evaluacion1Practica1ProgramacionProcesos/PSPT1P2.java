package Evaluacion1Practica1ProgramacionProcesos;
public class PSPT1P2 {
    static class Impresora {
        /**
         * Método sincronizado para simular la impresión.
         */
        public synchronized void imprimir(String doc) {
            System.out.println(Thread.currentThread().getName() + " imprime: " + doc);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
        }
    }

    static class Scanner {
        /**
         * Método sincronizado para simular el escaneo.
         */
        public synchronized void scan(String doc) {
            System.out.println(Thread.currentThread().getName() + " escanea: " + doc);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        Impresora impresora = new Impresora();
        Scanner scanner = new Scanner();

        Thread tA = new Thread(() -> {
            // SOLUCIÓN: Ambos hilos adquieren locks en el MISMO ORDEN
            // Primero impresora, luego scanner
            synchronized (impresora) {
                System.out.println(Thread.currentThread().getName() + " acceso a impresora...");
                impresora.imprimir("Documento A");
                synchronized (scanner) {
                    System.out.println(Thread.currentThread().getName() + " acceso a escáner...");
                    scanner.scan("Documento A");
                }
            }
            System.out.println(Thread.currentThread().getName() + " completada");
        }, "Tarea-A");

        Thread tB = new Thread(() -> {
            // CAMBIO CLAVE: Ahora también adquiere primero impresora, luego scanner
            // Mismo orden que Tarea-A -> NO HAY DEADLOCK
            synchronized (impresora) {
                System.out.println(Thread.currentThread().getName() + " acceso a impresora...");
                impresora.imprimir("Documento B");
                synchronized (scanner) {
                    System.out.println(Thread.currentThread().getName() + " acceso a escáner...");
                    scanner.scan("Documento B");
                }
            }
            System.out.println(Thread.currentThread().getName() + " completada");
        }, "Tarea-B");

        tA.start();
        tB.start();

        // Opcional: Esperar a que terminen los hilos
        try {
            tA.join();
            tB.join();
            System.out.println("\n=== Todas las tareas completadas ===");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}