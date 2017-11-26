import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Printer implements Runnable{
    private ConcurrentLinkedQueue<Process> list = new ConcurrentLinkedQueue<Process>();
    private Process tempProcess;
    private Manager manager;
    private Random mRandom = new Random();

    public boolean stop = false;

    public Printer(Manager d){
        this.manager = d;
    }

    public void newProcessPrinter(Process process){
        list.add(process);
    }

    public void processing(){
        if(!list.isEmpty()){
            tempProcess = list.element();

            tempProcess.setPrinter_cycles_processed(1);

            if(mRandom.nextInt() < 20 || tempProcess.printerComplete()){
                manager.receiveBlockedProcess(tempProcess);
                list.poll();
            }
        }
    }


    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
            processing();
        }
    }


}
