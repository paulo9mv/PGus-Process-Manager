
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
public class Main{
    
    
    
    public static void main(String args[]){
        final Despachante despachante = new Despachante();
        despachante.inicialize();
        new Thread(despachante).start();
       
        new Thread(despachante.getCore()).start();
        new Thread(despachante.getDisk()).start();
        new Thread(despachante.getPrinter()).start();
        
        Process[] mProcess = new Process[34];
        Random mRandom = new Random();
        
        for(int i = 0; i < 1; i++){
            mProcess[i] = new Process(i, "Process" + i, mRandom.nextInt(5), mRandom.nextInt(5), mRandom.nextInt(5));
            
            despachante.toScheduling(mProcess[i]);
        }
 
    }
}
