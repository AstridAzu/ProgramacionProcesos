package Synchronized;


import java.util.concurrent.locks.ReentrantLock;

public class CondicionCarreraLogin {
        private static int contador = 0;

        //utilizaremos bloqueos o look
        private static ReentrantLock candado = new  ReentrantLock();
        //el nloque lo havccemios con look y el antibloqueo con el anlook
        //nios garantiza la esclusxion mutua unico frret a la vez
        //las variables atomicas para variables
        //mponitor del objeto de la clase nos involucramos con los objetos y la clase
        //vamos hacer los semaforos los semafgotros te permite que varios freet tantos freet cxomo tu quieras incluir
        //ese codigo utilizra ese codigo de semaforo hasta que se libere alguno del contro semaforo
        public static  void incrementarContador(int num){

            System.out.println("Entrando en incrementarContador");
            //bloqueamos el recurso

            contador += num;
            candado.unlock();
            //sino havcemos el allok explicito hacemos un  problema ya que el therrs qwueda bloqueado no puede
            //continuar para evitar que el look se ejecute siempre
            //hacemnos un try
            candado.lock();
            try {
                contador+= num;
            }finally {
                candado.unlock();;
            }

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

            //THREAD PARA INCREMENTAR EL VALOR DE LA VARIABLE CONTADOR EN "ITERACIONES" VECES
            Thread incrementador = new Thread(() -> {

                System.out.println("Iniciando ejecucion incremetador");
                for (int i = 0; i < ITERACIONES; i++) {
                    incrementarContador(VALOR);
                }
                System.out.println("Acabando ejecucion incremetador");
            });

            //THREAD PARA DECREMENTAR EL VALOR DE LA VARIABLE CONTADOR EN "ITERACIONES" VECES
            Thread decrementador = new Thread(() -> {

                System.out.println("Iniciando ejecucion decrementador");
                for (int i = 0; i < ITERACIONES; i++) {
                    incrementarContador(-VALOR);
                }
                System.out.println("Acabando ejecucion decrementador");
            });

            Thread accesor1 = new Thread(() -> {
                getContador();
            });

            Thread accesor2 = new Thread(() -> {
                incrementarContador(VALOR);
            });
        }
    }

