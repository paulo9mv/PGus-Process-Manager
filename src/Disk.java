import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Disk implements Runnable{
    private ConcurrentLinkedQueue<Process> list = new ConcurrentLinkedQueue<Process>();
    private Process tempProcess;
    private Manager manager;
    private Random mRandom = new Random();

    public boolean stop = false;

    public Disk(Manager d){
        this.manager = d;
    }

    public void newProcessDisk(Process process){
        list.add(process);      
    }

    private void processing(){
        if(!list.isEmpty()){
            tempProcess = list.element();

            tempProcess.setDisk_cycles_processed(1);

            if(mRandom.nextInt(100) < 20 || tempProcess.diskComplete()){
                manager.receiveBlockedProcess(tempProcess);
                list.poll();
            }
        }
    }

    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
            processing();
        }
    }
}
