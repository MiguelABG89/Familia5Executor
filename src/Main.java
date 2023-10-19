import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**********************************************************************************************************************************************
 *   APLICACIÓN: "Famila5Executor"                                                                                                          *
 **********************************************************************************************************************************************
 *   PROGRAMACIÓN DE SERVICIOS Y PROCESOS 2DAM  -  IntelliJ IDEA 2022.2.2 (Community Edition)                                                 *
 **********************************************************************************************************************************************
 *   @author Miguel A. Brand Gaviria                                                                                                         *
 *   @version 1.0 - Versión inicial del algoritmo.                                                                                            *
 *   @since 16/10/2023                                                                                                                      *
 **********************************************************************************************************************************************
 *   COMENTARIOS:                                                                                                                             *
 *        - programa en Java capaz de encontrar todos los multiplos de 5 y que sean más pequeños de 100.                                      *
 *        - En este programa se usa el metodo call                                                                                            *
 **********************************************************************************************************************************************/
public class Main {
    private static final int RANGO = 500;

    static class Multiplos implements Callable<Integer> {
        private int a_Operador1;

        public Multiplos(int p_Operador1) {
            this.a_Operador1 = p_Operador1;
        }

        //metodo que sobre cargamos
        @Override
        public Integer call() throws Exception {
            int l_Numero = 0;
            if (a_Operador1 % 5 == 0) {
                l_Numero = a_Operador1;
            }
            return l_Numero;

        }
    }

    static class Calculo implements Callable<Integer> {
        private int a_Operador1;

        public Calculo(int p_Operador1) {
            this.a_Operador1 = p_Operador1;
        }

        //metodo que sobre cargamos
        @Override
        public Integer call() {
            int l_Digito1 = 0;
            int l_Digito2 = a_Operador1;
            int l_Suma = 0;
            int l_Almacenador=0;

            while (l_Digito2 != 0) {

                l_Digito1 = l_Digito2 % 10;
                l_Digito2/=10;
                l_Almacenador+=(l_Digito1+l_Digito2);

            }
            if (l_Almacenador%5==0){
                l_Suma=a_Operador1;
            }
            return l_Suma;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor l_Executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        List<Multiplos> l_ListaMultiplos = new ArrayList<Multiplos>();
        List<Integer> l_ResultadosMul = new ArrayList<>();
        List<Integer> l_ResultadosCal = new ArrayList<>();
        List<Calculo> l_ListaMenores = new ArrayList<>();
        int l_Contador;

        for (l_Contador = 0; l_Contador <= RANGO; l_Contador++) {
            Multiplos l_Multiplo = new Multiplos(l_Contador);
            Calculo l_Calculo = new Calculo(l_Contador);
            if (l_Multiplo.a_Operador1 % 5 == 0) {
                l_ListaMultiplos.add(l_Multiplo);
            }
            l_ListaMenores.add(l_Calculo);

        }
        List<Future<Integer>> l_ListaResultadoMul = l_Executor.invokeAll(l_ListaMultiplos);
        List<Future<Integer>> l_ListaResultadoCal = l_Executor.invokeAll(l_ListaMenores);
        l_Executor.shutdown();

        for (l_Contador = 0; l_Contador < l_ListaResultadoMul.size(); l_Contador++) {
            l_ResultadosMul.add(l_ListaResultadoMul.get(l_Contador).get());
        }
        for (l_Contador = 0; l_Contador < l_ListaResultadoCal.size(); l_Contador++) {
            l_ResultadosCal.add(l_ListaResultadoCal.get(l_Contador).get());
        }

        for (Integer l_Numero : l_ResultadosMul) {
            if (l_ResultadosCal.contains(l_Numero)) {
                System.out.println(l_Numero);

            }
        }
    }
}