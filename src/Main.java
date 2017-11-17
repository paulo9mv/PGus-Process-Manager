public class Main{
    public static void main(String args[]){
        final Despachante despachante = new Despachante();
        despachante.inicialize();
        new Thread(despachante).start();
       
        new Thread(despachante.getCore()).start();
        new Thread(despachante.getDisk()).start();
        new Thread(despachante.getPrinter()).start();
        
        Process[] mProcess = new Process[15];

        for(int i = 1; i < 11; i++){
            mProcess[i] = new Process(i, "Process" + i, i, 15 - i, 11 - i);
            System.out.printf("Processo %d adicionado!\n", i);
            despachante.toScheduling(mProcess[i]);
        }
        

    }
}
