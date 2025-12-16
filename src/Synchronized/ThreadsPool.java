package Synchronized;

import java.util.Map;
// Importa la interfaz Map, usada para almacenar pares clave-valor

import java.util.concurrent.ConcurrentHashMap;
// Importa ConcurrentHashMap, que permite acceso concurrente seguro desde múltiples hilos

import java.util.concurrent.ExecutorService;
// Importa ExecutorService, para manejar un pool de hilos

import java.util.concurrent.Executors;
// Importa la clase Executors, que crea instancias de ExecutorService

import java.util.concurrent.TimeUnit;
// Importa TimeUnit, usado para definir unidades de tiempo

import java.util.concurrent.atomic.AtomicInteger;
// Importa AtomicInteger, un contador seguro para uso concurrente

// Este código sirve para optimizar uso de recursos
public class ThreadsPool {

    public static void main(String[] args) {
        final int MAX_POOL_SIZE = 10;
        // Número máximo de hilos en el pool

        // Queremos saber cuántas veces se ejecuta cada Thread (con un Map)
        Map<String, AtomicInteger> map = new ConcurrentHashMap<>();
        // Mapa concurrente donde:
        // Clave   → nombre del hilo
        // Valor   → contador de veces que se ejecutó el hilo

        // ExecutorService crea un pool de threads siempre disponibles
        ExecutorService pool = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        // Crea un pool de tamaño 10 hilos fijos

        for (int i = 0; i < 50; i++) {
            // Creamos 50 tareas para ejecutar en el pool

            // Pedimos a un thread del pool que ejecute una tarea
            pool.submit(() -> {
                // Cada tarea se ejecutará en uno de los hilos disponibles

                // Añade el nombre al mapa como clave, y crea un AtomicInteger si no existe
                map.computeIfAbsent(
                        Thread.currentThread().getName(),
                        k -> new AtomicInteger()
                ).incrementAndGet();
                // Incrementa el contador de veces que se ejecutó este hilo

                System.out.println("[" + Thread.currentThread().getName() + "] Saludos");
                // Muestra qué hilo ejecutó la tarea
            });
        }

        pool.shutdown();
        // No acepta más tareas nuevas y termina de forma ordenada las pendientes

        try {
            // Espera hasta 10 segundos a que terminen todas las tareas pendientes
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                // Si no terminan en 10 segundos, cierra el pool forzadamente
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            // Si el hilo principal es interrumpido, fuerza el cierre del pool
        }

        // Imprime cuántas veces se ejecutó cada hilo
        map.forEach((k, v) -> {
            System.out.println("El Thread: " + k + " se ha ejecutado " + v.get() + " veces");
        });

        // Calcula el total de ejecuciones sumando todos los contadores
        System.out.println("Total ejecuciones Threads: " +
                map.values().stream().mapToInt(v -> v.get()).sum()
        );
    }
}
