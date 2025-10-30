package Evaluacion1Practica1ProgramacionProcesos;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RegistroPedidos {
    private int nextId = 1;
    // Lista sincronizada para el historial de pedidos
    private final List<Pedido> historico = Collections.synchronizedList(new ArrayList<>());
    // Contador por cliente (AtomicInteger por cliente)
    private final ConcurrentHashMap<String, AtomicInteger> contadorPorCliente = new ConcurrentHashMap<>();
    public static void main(String[] args) throws InterruptedException {
        final RegistroPedidos registrarpedidos = new RegistroPedidos();
        final int numHilos = 10;
        final int pedidosPorHilo = 10;

        Thread[] hilos = new Thread[numHilos];

        for (int i = 0; i < numHilos; i++) {
            hilos[i] = new Thread(() -> {
                Random rnd = new Random();
                for (int j = 0; j < pedidosPorHilo; j++) {
                    String cliente = "Cliente-" + rnd.nextInt(10); // 0..9
                    registrarpedidos.crearPedido(cliente);
                    // pequeña pausa para aumentar mezcla entre hilos
                    try {
                        Thread.sleep(rnd.nextInt(20));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            hilos[i].setName("Hilo-" + i);
        }

        // Iniciar hilos
        for (Thread t : hilos) t.start();
        // Esperar a que terminen
        for (Thread t : hilos) t.join();

        // Mostrar resultados
        List<Pedido> historico = registrarpedidos.getHistoricoSnapshot();
        historico.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

        System.out.println("\n--- Listado de todos los pedidos ---");
        for (Pedido p : historico) {
            System.out.println(p.toString());
        }

        int total = historico.size();
        System.out.println("\nNúmero total de pedidos: " + total);

        Map<String, Integer> conteos = registrarpedidos.getConteosPorClienteSnapshot();
        System.out.println("\n--- Pedidos por cliente ---");
        int suma = 0;
        for (Map.Entry<String, Integer> e : conteos.entrySet()) {
            System.out.printf("%s -> %d%n", e.getKey(), e.getValue());
            suma += e.getValue();
        }

        System.out.println("\nSuma de pedidos por cliente: " + suma);
        System.out.println("\nConsistencia: " + (suma == total ? "OK" : "ERROR"));
    }


    public Pedido crearPedido(String cliente) {
        int id = nextId;
        nextId++;
        long fecha = System.currentTimeMillis();
        Pedido p = new Pedido(id, cliente, fecha);
        historico.add(p); // lista sincronizada
        contadorPorCliente.computeIfAbsent(cliente, k -> new AtomicInteger(0)).incrementAndGet();

        return p;
    }

    /**
     * Devuelve una copia del histórico de pedidos en el momento de la llamada.
     */
    public List<Pedido> getHistoricoSnapshot() {
        synchronized (historico) {
            return new ArrayList<>(historico);
        }
    }

    /**
     * Devuelve un mapa (ordenado por clave) con el conteo de pedidos por cliente.
     */
    public Map<String, Integer> getConteosPorClienteSnapshot() {
        Map<String, Integer> snapshot = new TreeMap<>();
        for (Map.Entry<String, AtomicInteger> e : contadorPorCliente.entrySet()) {
            snapshot.put(e.getKey(), e.getValue().get());
        }
        return snapshot;
    }

    /**
     * Número total de pedidos generados hasta ahora.
     */
    public int getTotalPedidos() {
        return nextId;
    }

}

class Pedido {
    private final int id;
    private final String cliente;
    private final long fechaMillis;

    private static final SimpleDateFormat FORMATO = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    public Pedido(int id, String cliente, long fechaMillis) {
        this.id = id;
        this.cliente = cliente;
        this.fechaMillis = fechaMillis;
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public long getFechaMillis() {
        return fechaMillis;
    }

    public String getFechaFormateada() {
        return FORMATO.format(new Date(fechaMillis));
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Cliente: %s, Fecha: %s", id, cliente, getFechaFormateada());
    }
}

