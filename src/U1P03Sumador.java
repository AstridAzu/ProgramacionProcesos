public class U1P03Sumador {

    private void sumar(int num1, int num2){
        int resultado = 0 ;
        //3 - 2
        if (num1 > num2){
            //aux=3
            int aux = num1;
            //num1=2
            num1 = num2;
            //num2=3
            num2 = aux;
        }
        //que siempre el num1 sea menor que num2
        //i=2; 2<=3;i++
        for (int i = num1; i <= num2; i++) {
            //segunda iteracion  3
            //resultado=1+1
            //resultado 2
            resultado += i;
        }
        //resultado = 2
        //2+3= 2
        System.out.println(" La suma de los numeros entre " +num1 + " y " + num2+ " es " + resultado);

    }
   public static void main(String[] args) { // String de Arrays sirve para pasar argumentos al Main
        // instanciar la clase
        try {
            U1P03Sumador sumador = new U1P03Sumador();
            sumador.sumar(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }catch (ArrayIndexOutOfBoundsException e){}
        // ver como pasamos argumentos al programa
        // Verificar que se pasen dos argumentos
    }
}
