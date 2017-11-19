import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Disk implements Runnable{
    private LinkedList<Process> list = new LinkedList<Process>();
    private Process tempProcess;
    private Despachante despachante;
    private Random mRandom = new Random();

    public boolean stop = false;
    public boolean first = true;

    public Disk(Despachante d){
        this.despachante = d;
    }

    public void newProcessDisk(Process process){
        list.addLast(process);
    }

    public void processing(){ 
        if(!list.isEmpty()){
            tempProcess = list.getFirst();
        
            tempProcess.setDisk_cycles_processed(1);
        
            if(mRandom.nextInt() < 20 || tempProcess.diskComplete()){
                despachante.receiveBlockedProcess(tempProcess);
                list.removeFirst();
            }
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                TimeUnit.MILLISECONDS.sleep(220);
            } catch (InterruptedException ex) {
                Logger.getLogger(Despachante.class.getName()).log(Level.SEVERE, null, ex);
            }
            processing();
        }
       
    }

}
