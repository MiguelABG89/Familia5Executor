import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**********************************************************************************************************************************************
 *   APLICACIÓN: "Famila5Executor"                                                                                                          *
 **********************************************************************************************************************************************
 *   PROGRAMACIÓN DE SERVICIOS Y PROCESOS 2DAM  -  IntelliJ IDEA 2022.2.2 (Community Edition)                                                 *
 **********************************************************************************************************************************************
 *   @author  Miguel A. Brand Gaviria                                                                                                         *
 *   @version 1.0 - Versión inicial del algoritmo.                                                                                            *
 *   @since   16/10/2023                                                                                                                      *
 **********************************************************************************************************************************************
 *   COMENTARIOS:                                                                                                                             *
 *        - programa en Java capaz de encontrar todos los multiplos de 5 y que sean más pequeños de 100.                                      *
 *        - En este programa se usa el metodo call                                                                                            *
 **********************************************************************************************************************************************/
public class Main {
    private static final int RANGO=100;
    static class Multiplos implements Callable<Integer> {
        private int a_Operador1;

        public Multiplos(int p_Operador1) {
            this.a_Operador1 = p_Operador1;
        }
        //metodo que sobre cargamos
        @Override
        public Integer call() throws Exception
        {
            int l_Numero = 0;
            if(a_Operador1%5==0) {
                l_Numero= a_Operador1;
            }
            return l_Numero;

        }
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor l_Executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        List<Multiplos> l_ListaMultiplos = new ArrayList<Multiplos>();
        List<Integer> l_ResultadosMul= new ArrayList<>();
        List<Multiplos> l_ListaMenores = new ArrayList<Multiplos>();
        int l_Contador;

        for (l_Contador = 0; l_Contador <= RANGO; l_Contador++) {
            Multiplos l_Calculo = new Multiplos(l_Contador);
            l_ListaMultiplos.add(l_Calculo);
        }
        List<Future<Integer>> l_ListaResultadoMul = l_Executor.invokeAll(l_ListaMultiplos);
        l_Executor.shutdown();

        for (l_Contador=0;l_Contador<l_ListaResultadoMul.size();l_Contador++) {
            l_ResultadosMul.add(l_ListaResultadoMul.get(l_Contador).get());
        }

        for (Integer l_Numero: l_ResultadosMul) {
            System.out.println(l_Numero);
        }
    }
}