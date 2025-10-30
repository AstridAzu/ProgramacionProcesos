package RepasoExamen;
import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
public class AplicacionGeneralThreads {
    public static void main(String[] args) {
        // Se crea un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Menú de opciones para seleccionar el ejercicio a ejecutar
            System.out.println("\n--- Ejercicios de Programación Concurrente Multihilo ---");
            System.out.println("1. U2P00ThreadTesting");
            System.out.println("2. U2P01CreacionThreads");
            System.out.println("3. U2P02ContadorVocalThread");
            System.out.println("4. U2P02ContarVocalesThread");
            System.out.println("5. U2P02ContarVocalThread");
            System.out.println("6. U2P03SleepingThreads");
            System.out.println("7. U2P04CondicionDeCarrera");
            System.out.println("8. U2P04CondicionDeCarreraAtomicVars");
            System.out.println("9. U2P04CondicionDeCarreraLock");
            System.out.println("10. U2P04CondicionDeCarreraMonitorSynchronized");
            System.out.println("11. U2P04CondicionDeCarreraSemaforo");
            System.out.println("12. U2P05Deadlock");
            System.out.println("13. U2P05DeadlockCorregido");
            System.out.println("14. U2P05DeadLockCuentaCorriente");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            // Se lee la opción seleccionada por el usuario
            int opcion = scanner.nextInt();
            // Se ejecuta el ejercicio correspondiente según la opción elegida
            switch (opcion) {
                case 1:
                    // Ejecuta U2P00ThreadTesting
                    ejecutarU2P00ThreadTesting();
                    break;
                case 2:
                    // Ejecuta U2P01CreacionThreads
                    ejecutarU2P01CreacionThreads();
                    break;
                case 3:
                    // Ejecuta U2P02ContadorVocalThread
                    ejecutarU2P02ContadorVocalThread();
                    break;
                case 4:
                    // Ejecuta U2P02ContarVocalesThread
                    ejecutarU2P02ContarVocalesThread();
                    break;
                case 5:
                    // Ejecuta U2P02ContarVocalThread
                    ejecutarU2P02ContarVocalThread();
                    break;
                case 6:
                    // Ejecuta U2P03SleepingThreads
                    ejecutarU2P03SleepingThreads();
                    break;
                case 7:
                    // Ejecuta U2P04CondicionDeCarrera
                    ejecutarU2P04CondicionDeCarrera();
                    break;
                case 8:
                    // Ejecuta U2P04CondicionDeCarreraAtomicVars
                    ejecutarU2P04CondicionDeCarreraAtomicVars();
                    break;
                case 9:
                    // Ejecuta U2P04CondicionDeCarreraLock
                    ejecutarU2P04CondicionDeCarreraLock();
                    break;
                case 10:
                    // Ejecuta U2P04CondicionDeCarreraMonitorSynchronized
                    ejecutarU2P04CondicionDeCarreraMonitorSynchronized();
                    break;
                case 11:
                    // Ejecuta U2P04CondicionDeCarreraSemaforo
                    ejecutarU2P04CondicionDeCarreraSemaforo();
                    break;
                case 12:
                    // Ejecuta U2P05Deadlock
                    ejecutarU2P05Deadlock();
                    break;
                case 13:
                    // Ejecuta U2P05DeadlockCorregido
                    ejecutarU2P05DeadlockCorregido();
                    break;
                case 14:
                    // Ejecuta U2P05DeadLockCuentaCorriente
                    ejecutarU2P05DeadLockCuentaCorriente();
                    break;
                case 0:
                    // Sale del programa
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;
                default:
                    // Opción inválida
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    // --- EJERCICIO 1: U2P00ThreadTesting ---
    // Programación multihilo es más óptima que multiproceso
    // Ojo condición de carrera. Varios hilos pueden ir a un mismo objetivo
    // Deadlock / Abrazo mortal
    public static void ejecutarU2P00ThreadTesting() {
        System.out.println("El nombre del thread es: " + Thread.currentThread().getName());
        System.out.println("El ID del thread es: " + Thread.currentThread().threadId());
        System.out.println("La prioridad del thread es: " + Thread.currentThread().getPriority());
        System.out.println("El estado del thread es: " + Thread.currentThread().getState());
        System.out.println("El grupo del thread es: " + Thread.currentThread().getThreadGroup());
    }

    // --- EJERCICIO 2: U2P01CreacionThreads ---
    // Clase auxiliar para implementación de thread implementando interfaz Runnable
    static class ThreadImplements implements Runnable{
        // Este es el código que se ejecuta cuando lanzo el thread
        @Override
        public void run(){
            System.out.println("El nombre del thread es: " + Thread.currentThread().getName());
            System.out.println("El ID del thread es: " + Thread.currentThread().threadId());
        }
    }

    // Clase auxiliar para declaración de clase thread extendiendo/heredando de la clase padre Thread
    static class ThreadExtends extends Thread{
        // Este es el código que se ejecuta cuando lanzo el thread
        @Override
        public void run(){
            System.out.println("El nombre del thread es: " + Thread.currentThread().getName());
            System.out.println("El ID del thread es: " + Thread.currentThread().threadId());
        }
    }

    public static void ejecutarU2P01CreacionThreads() {
        // 3. Instanciación de thread mediante expresión lambda
        Thread t1 = new Thread(() -> {
            System.out.println("El nombre del thread es: " + Thread.currentThread().getName());
            System.out.println("El ID del thread es: " + Thread.currentThread().threadId());
        }, "Thread Lambda");

        Thread t2 = new Thread(new ThreadExtends(), "Thread Herencia");

        Thread t3 = new Thread(new ThreadImplements(), "Thread Implements");

        t1.start();
        t2.start();
        t3.start();
    }

    // --- EJERCICIO 3: U2P02ContadorVocalThread ---
    // Clase auxiliar para contar vocales en archivos
    static class ContadorVocalThread implements Runnable {
        private String archivo;
        private String salida;
        private char vocal;

        private static final Map<Character, Character> VOCALES;

        static {
            VOCALES = new HashMap();
            VOCALES.put('a', 'á');
            VOCALES.put('e', 'é');
            VOCALES.put('i', 'í');
            VOCALES.put('o', 'ó');
            VOCALES.put('u', 'ú');
        }

        public ContadorVocalThread(char vocal, String archivo, String salida) {
            this.vocal = vocal;
            this.archivo = archivo;
            this.salida = salida;
        }

        @Override
        public void run(){
            int contador = 0;
            System.out.println("[" + Thread.currentThread().getName() + "] iniciando cuenta vocal " + vocal);

            try (BufferedReader in = new BufferedReader(new FileReader(archivo))){
                String line = "";
                // Mientras dentro de la linea hay algo distinto a null entra en el bucle
                while ((line = in.readLine()) != null){
                    line = line.toLowerCase();
                    for(int i = 0; i < line.length(); i++){
                        // Si la vocal se encuentra entre uno de los
                        // carácteres de la linea sumo uno al contador
                        if(line.charAt(i) == vocal || line.charAt(i) == VOCALES.get(vocal)){
                            contador++;
                        }
                    }
                }
            }catch (FileNotFoundException e){
                System.err.println("Archivo "+ archivo+ " no encontrado");
                throw new RuntimeException();
            }catch (IOException e){
                System.err.println("Error en lectura de archivo " + archivo);
                throw new RuntimeException();
            }
            System.out.println("[" + Thread.currentThread().getName() + "] finalizada cuenta vocal " + vocal);

            try (BufferedWriter out = new BufferedWriter(new FileWriter(salida + vocal + ".txt"))) {
                out.write(String.valueOf(contador));
            } catch (IOException e) {
                System.err.println("Error al escribir el archivo " + vocal + ".txt");
            }
        }
    }

    public static void ejecutarU2P02ContadorVocalThread() {
        System.out.println("Este ejercicio requiere un archivo './resources/vocales.txt' para funcionar correctamente.");
        System.out.println("Ejemplo simplificado de contador de vocales en thread:");

        String textoEjemplo = "Esta es una prueba de texto con vocales aeiou";
        Map<Character, Integer> contadores = new HashMap<>();

        for (char c : textoEjemplo.toLowerCase().toCharArray()) {
            if ("aeiouáéíóú".indexOf(c) != -1) {
                contadores.put(c, contadores.getOrDefault(c, 0) + 1);
            }
        }

        System.out.println("Texto: " + textoEjemplo);
        System.out.println("Conteo de vocales: " + contadores);
    }

    // --- EJERCICIO 4: U2P02ContarVocalesThread ---
    static class ContarVocalesThread implements Runnable {
        private static final Map<Character, Character> VOCALES_MAP;
        static{
            VOCALES_MAP = new HashMap();
            VOCALES_MAP.put('a', 'á');
            VOCALES_MAP.put('e', 'é');
            VOCALES_MAP.put('i', 'í');
            VOCALES_MAP.put('o', 'ó');
            VOCALES_MAP.put('u', 'ú');
        }

        private char vocal;
        private String archivo;
        private String salida;

        public ContarVocalesThread(char vocal, String archivo, String salida) {
            this.vocal = vocal;
            this.archivo = archivo;
            this.salida = salida;
        }

        @Override
        public void run() {
            int contador = 0;

            System.out.println("[" + Thread.currentThread().getName() + "] Iniciado cuenta vocal " + vocal);

            try (BufferedReader in = new BufferedReader(new FileReader(archivo))) {
                String line;
                while ((line = in.readLine()) != null) {
                    line = line.toLowerCase();
                    // Leer la línea por caracteres
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == vocal || line.charAt(i) == VOCALES_MAP.get(vocal)) {
                            contador++;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("No existe el archivo: " + archivo);
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + archivo);
                throw new RuntimeException(e);
            }
            System.out.println("[" + Thread.currentThread().getName() + "] Finalizada cuenta vocal " + vocal + ": " + contador + " resultados.");
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(salida + vocal + ".txt"))){
                bw.write(String.valueOf(contador));
            } catch (IOException e) {
                System.err.println("Error al escribir el archivo " + vocal + ".txt");
            }
        }
    }

    public static void ejecutarU2P02ContarVocalesThread() {
        System.out.println("Este ejercicio también requiere archivos. Ejecutando ejemplo simplificado:");

        String textoEjemplo = "Hola mundo con multiples vocales aeiou";
        char[] vocales = {'a', 'e', 'i', 'o', 'u'};

        for (char vocal : vocales) {
            Thread t = new Thread(() -> {
                int contador = 0;
                for (char c : textoEjemplo.toLowerCase().toCharArray()) {
                    if (c == vocal) {
                        contador++;
                    }
                }
                System.out.println("[" + Thread.currentThread().getName() + "] Vocal '" + vocal + "': " + contador + " veces");
            }, "Thread-" + vocal);
            t.start();
        }
    }

    // --- EJERCICIO 5: U2P02ContarVocalThread ---
    public static void ejecutarU2P02ContarVocalThread() {
        System.out.println("Ejemplo de conteo de vocales con threads:");

        String texto = "Programacion concurrente multihilo con Java";
        Map<Character, Character> VOCALESTILDE = new HashMap();
        VOCALESTILDE.put('a','á');
        VOCALESTILDE.put('e','é');
        VOCALESTILDE.put('i','í');
        VOCALESTILDE.put('o','ó');
        VOCALESTILDE.put('u','ú');

        List<Thread> hilos = new ArrayList();

        for (char v : VOCALESTILDE.keySet()){
            Thread t = new Thread(() -> {
                int contador = 0;
                for (char c : texto.toLowerCase().toCharArray()) {
                    if (c == v || c == VOCALESTILDE.get(v)) {
                        contador++;
                    }
                }
                System.out.println("Vocal '" + v + "': " + contador + " veces en '" + texto + "'");
            }, "Thread-" + v);
            hilos.add(t);
            t.start();
        }

        for (Thread hilo : hilos){
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    // --- EJERCICIO 6: U2P03SleepingThreads ---
    static class SleepingThreads implements Runnable{
        @Override
        public void run(){
            // Desde la ejecucción del thread (run) vamos a dormir el thread
            String nombreThread = "[" + Thread.currentThread().getName() + "]";
            System.out.println(nombreThread + " Iniciando ejecución...");
            try {
                Thread.sleep(3000); // Dormir 3 segundos en lugar de Long.MAX_VALUE para evitar problemas
            } catch (InterruptedException e) {
                System.out.println(nombreThread + " Despertando (1)");
            }
            while(!Thread.interrupted()){}
            System.out.println(nombreThread + " Despertando (2)");
        }
    }

    public static void ejecutarU2P03SleepingThreads() {
        Thread hilo = new Thread(new SleepingThreads(), "Sleeping Thread");
        hilo.start();

        String nombreThread = "[" + Thread.currentThread().getName() + "]";
        System.out.println(nombreThread + " Iniciando ejecución");

        try {
            System.out.println(nombreThread + " Durmiendo 1 segundo");
            Thread.sleep(1000);
            System.out.println(nombreThread + " Despertando e interrumpiendo el hilo");
            hilo.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // --- EJERCICIO 7: U2P04CondicionDeCarrera ---
    // Variables de clase para condición de carrera
    private static int contador = 0;

    private static void incrementarContador(int num){
        contador += num;
    }

    private static int getContador(){
        return contador;
    }

    public static void ejecutarU2P04CondicionDeCarrera() {
        // Reiniciar contador para este ejercicio
        contador = 0;

        // Los guiones bajos ponen los puntos
        final int ITERACIONES = 100_000; // Reducido para mejor visualización
        final int VALOR = 1;

        // Hilo creado con expresión lambda
        Thread incrementador = new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + " Iniciando ejecución");
            for(int i = 0; i < ITERACIONES; i++){
                incrementarContador(VALOR);
            }
            System.out.println("[" + Thread.currentThread().getName() + "]" + " Finalizando ejecución");
        }, "Incrementador");

        Thread decrementador = new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + " Iniciando ejecución");
            for(int i = 0; i < ITERACIONES; i++){
                incrementarContador(-VALOR);
            }
            System.out.println("[" + Thread.currentThread().getName() + "]" + " Finalizando ejecución");
        }, "Decrementador");

        incrementador.start();
        decrementador.start();

        try {
            incrementador.join();
            decrementador.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("El valor final del contador es: " + getContador() + " (debería ser 0, pero puede no serlo debido a condición de carrera)");
    }

    // --- EJERCICIO 8: U2P04CondicionDeCarreraAtomicVars ---
    private static AtomicInteger contadorAtomic = new AtomicInteger(0);

    public static void incrementarContadorAtomic(int num){
        contadorAtomic.addAndGet(num);
    }

    public static int getContadorAtomic(){
        return contadorAtomic.get();
    }

    public static void ejecutarU2P04CondicionDeCarreraAtomicVars() {
        // Reiniciar contador atómico
        contadorAtomic.set(0);

        final int ITERACIONES = 100_000;
        final int VALOR = 1;

        Thread incrementador = new Thread(() -> {
            System.out.println("Iniciando ejecucion incremetador");
            for (int i = 0; i < ITERACIONES; i++) {
                incrementarContadorAtomic(VALOR);
            }
            System.out.println("Acabando ejecucion incremetador");
        });

        Thread decrementador = new Thread(() -> {
            System.out.println("Iniciando ejecucion decrementador");
            for (int i = 0; i < ITERACIONES; i++) {
                incrementarContadorAtomic(-VALOR);
            }
            System.out.println("Acabando ejecucion decrementador");
        });

        incrementador.start();
        decrementador.start();

        try {
            incrementador.join();
            decrementador.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("El valor final de contador atómico es: " + getContadorAtomic() + " (debería ser exactamente 0)");
    }

    // --- EJERCICIO 9: U2P04CondicionDeCarreraLock ---
    private static int contadorLock = 0;
    private static ReentrantLock candado = new ReentrantLock();

    public static void incrementarContadorLock(int num){
        candado.lock();
        try {
            contadorLock += num;
        }finally {
            candado.unlock();
        }
    }

    public static int getContadorLock(){
        return contadorLock;
    }

    public static void ejecutarU2P04CondicionDeCarreraLock() {
        // Reiniciar contador con lock
        contadorLock = 0;

        final int ITERACIONES = 100_000;
        final int VALOR = 1;

        Thread incrementador = new Thread(() -> {
            System.out.println("Iniciando ejecucion incremetador con Lock");
            for (int i = 0; i < ITERACIONES; i++) {
                incrementarContadorLock(VALOR);
            }
            System.out.println("Acabando ejecucion incremetador con Lock");
        });

        Thread decrementador = new Thread(() -> {
            System.out.println("Iniciando ejecucion decrementador con Lock");
            for (int i = 0; i < ITERACIONES; i++) {
                incrementarContadorLock(-VALOR);
            }
            System.out.println("Acabando ejecucion decrementador con Lock");
        });

        incrementador.start();
        decrementador.start();

        try {
            incrementador.join();
            decrementador.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("El valor final de contador con Lock es: " + getContadorLock() + " (debería ser exactamente 0)");
    }

    // --- EJERCICIO 10: U2P04CondicionDeCarreraMonitorSynchronized ---
    private static int contadorSynchronized = 0;

    public static synchronized void incrementarContadorSynchronized(int num){
        System.out.println("Entrando en incrementarContador synchronized");
        contadorSynchronized += num;
        System.out.println("Saliendo de incrementarContador synchronized");
    }

    public static synchronized int getContadorSynchronized(){
        System.out.println("Obteniendo contador synchronized: " + contadorSynchronized);
        return contadorSynchronized;
    }

    public static void ejecutarU2P04CondicionDeCarreraMonitorSynchronized() {
        // Reiniciar contador synchronized
        contadorSynchronized = 0;

        final int VALOR = 100;

        Thread accesor1 = new Thread(() -> {
            getContadorSynchronized();
        }, "Accesor");

        Thread accesor2 = new Thread(() -> {
            incrementarContadorSynchronized(VALOR);
        }, "Incrementador");

        accesor1.start();
        accesor2.start();

        try {
            accesor1.join();
            accesor2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Valor final synchronized: " + getContadorSynchronized());
    }

    // --- EJERCICIO 11: U2P04CondicionDeCarreraSemaforo ---
    static class CondicionDeCarreraSemaforo implements Runnable{
        private static Semaphore semaforo = new Semaphore(3, true); // Reducido a 3 para mejor visualización
        private static AtomicInteger contadorSem = new AtomicInteger();

        @Override
        public void run() {
            String nombre = "[" + Thread.currentThread().getName() + "]";
            try {
                System.out.println(nombre + " Intentando adquirir semáforo");
                semaforo.acquire();
                System.out.println(nombre + " Adquirido semáforo número: " + contadorSem.incrementAndGet());

                // Simular trabajo
                Thread.sleep(2000);

                contadorSem.decrementAndGet();
                System.out.println(nombre + " Liberando semáforo");
                semaforo.release();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void ejecutarU2P04CondicionDeCarreraSemaforo() {
        List<Thread> lista = new ArrayList<>();

        for (int i = 0; i < 6; i++){
            Thread t = new Thread(new CondicionDeCarreraSemaforo(), "thread_" + i);
            lista.add(t);
            t.start();
        }

        for (Thread h : lista){
            try {
                h.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("*** Ejercicio de semáforo completado.");
    }

    // --- EJERCICIO 12: U2P05Deadlock ---
    public static void ejecutarU2P05Deadlock() {
        System.out.println("¡ADVERTENCIA! Este ejercicio puede causar un deadlock. Se ejecutará con timeout.");

        Object obj1 = new Object();
        Object obj2 = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (obj1){
                System.out.println("t1: Dentro del bloque obj1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (obj2){
                    System.out.println("t1: Dentro del bloque obj2");
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (obj2){
                System.out.println("t2: Dentro del bloque obj2");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (obj1){
                    System.out.println("t2: Dentro del bloque obj1");
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        // Timeout para evitar bloqueo indefinido
        try {
            t1.join(3000); // Esperar máximo 3 segundos
            t2.join(3000);
            if (t1.isAlive() || t2.isAlive()) {
                System.out.println("¡Deadlock detectado! Interrumpiendo threads...");
                t1.interrupt();
                t2.interrupt();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // --- EJERCICIO 13: U2P05DeadlockCorregido ---
    static class DeadlockCorregido implements Runnable{
        private static Object obj1 = new Object();
        private static Object obj2 = new Object();

        @Override
        public void run() {
            // Siempre bloquear en el mismo orden para evitar deadlock
            synchronized (obj1){
                System.out.println(Thread.currentThread().getName() + ": Dentro del bloque obj1");
                synchronized (obj2){
                    System.out.println(Thread.currentThread().getName() + ": Dentro del bloque obj2");
                }
            }
        }
    }

    public static void ejecutarU2P05DeadlockCorregido() {
        Thread t1 = new Thread(new DeadlockCorregido(), "Thread-1");
        Thread t2 = new Thread(new DeadlockCorregido(), "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Deadlock corregido: ambos threads terminaron correctamente");
    }

    // --- EJERCICIO 14: U2P05DeadLockCuentaCorriente ---
    static class CuentaCorriente{
        private float saldo;

        public CuentaCorriente(float saldo) {
            this.saldo = saldo;
        }

        public void retirar (float importe){
            if (saldo >= importe){
                saldo -= importe;
            }
        }

        public void ingresar (float importe){
            saldo += importe;
        }

        public float getSaldo (){
            return saldo;
        }
    }

    public static void transferencia(CuentaCorriente origen, CuentaCorriente destino, float importe){
        // Verificamos que el HashCode para evitar deadlock
        CuentaCorriente cuenta1 = origen.hashCode() < destino.hashCode() ? origen : destino;
        CuentaCorriente cuenta2 = origen.hashCode() < destino.hashCode() ? destino : origen;

        synchronized (cuenta1){
            synchronized (cuenta2){
                origen.retirar(importe);
                destino.ingresar(importe);
            }
        }
    }

    public static void ejecutarU2P05DeadLockCuentaCorriente() {
        // Creamos las cuentas corrientes con saldo inicial
        CuentaCorriente cc1 = new CuentaCorriente(1000);
        CuentaCorriente cc2 = new CuentaCorriente(1000);

        System.out.println("Saldo inicial cc1: " + cc1.getSaldo());
        System.out.println("Saldo inicial cc2: " + cc2.getSaldo());

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++)
                transferencia(cc1, cc2, 1);
        }, "Transfer-1-to-2");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++)
                transferencia(cc2, cc1, 2);
        }, "Transfer-2-to-1");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Saldo final cc1: " + cc1.getSaldo());
        System.out.println("Saldo final cc2: " + cc2.getSaldo());
        System.out.println("Saldo total: " + (cc1.getSaldo() + cc2.getSaldo()));
    }
}
