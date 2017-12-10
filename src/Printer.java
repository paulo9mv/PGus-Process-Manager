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

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Printer(Manager d){
        this.manager = d;
    }

    public void newProcessPrinter(Process process){
        list.add(process);
    }

    private void processing(){
        if(!list.isEmpty()){
            tempProcess = list.element();

            tempProcess.setPrinterCyclesProcessed(1);

            if(mRandom.nextInt(100) < 20 || tempProcess.printerComplete()){
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
