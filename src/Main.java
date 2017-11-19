
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class Main{


    
    public static void main(String args[]) throws InterruptedException{
        final Manager manager = new Manager();
        manager.inicialize();
        new Thread(manager).start();

        new Thread(manager.getCore()).start();
        new Thread(manager.getDisk()).start();
        new Thread(manager.getPrinter()).start();

        Process[] mProcess = new Process[34];
        Random mRandom = new Random();

 try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }

        for(int i = 0; i < 10; i++){
            mProcess[i] = new Process(i, "Process" + i, mRandom.nextInt(10), mRandom.nextInt(10), mRandom.nextInt(10));

            manager.toScheduling(mProcess[i]);
        }

    }
}
