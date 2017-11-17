public class Main{
    public static void main(String args[]){
        System.out.printf("Oi");
        Despachante despachante = new Despachante();
        despachante.inicialize();

        Process[] mProcess = new Process[15];

        for(int i = 1; i < 11; i++){
            mProcess[i] = new Process(i, "Process" + i, i, 15 - i, 11 - i);
        }

    }
}
