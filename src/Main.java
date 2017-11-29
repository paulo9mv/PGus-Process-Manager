
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class Main{

//TRUE 0
//TRUE 2
    
    public static void main(String args[]) throws InterruptedException{
        Manager manager = new Manager();
        manager.inicialize(false, 0, false);
        new Thread(manager).start();

        new Thread(manager.getCore()).start();
        new Thread(manager.getDisk()).start();
        new Thread(manager.getPrinter()).start();

        Thread.sleep(1);
        
        Process[] mProcess = new Process[1010];
        Random mRandom = new Random();
       
        for(int i = 0; i < 200; i++){
            mProcess[i] = new Process(i, "Process" + i, mRandom.nextInt(10), mRandom.nextInt(10), mRandom.nextInt(10));

            manager.toScheduling(mProcess[i]);
        }
        Thread.sleep(4000);
            System.out.printf("%d\n",manager.completedProcess.size());
       
    }
}
