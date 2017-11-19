
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class Main{
    
    
    
    public static void main(String args[]) throws InterruptedException{
        final Despachante despachante = new Despachante();
        despachante.inicialize();
        new Thread(despachante).start();
       
        new Thread(despachante.getCore()).start();
        new Thread(despachante.getDisk()).start();
        new Thread(despachante.getPrinter()).start();
        
        Process[] mProcess = new Process[34];
        Random mRandom = new Random();
        
 try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Despachante.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        for(int i = 0; i < 10; i++){
            mProcess[i] = new Process(i, "Process" + i, mRandom.nextInt(10), mRandom.nextInt(10), mRandom.nextInt(10));
            
            despachante.toScheduling(mProcess[i]);
        }
 
    }
}
