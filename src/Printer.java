import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Printer implements Runnable{
    private LinkedList<Process> list = new LinkedList<Process>();
    private Process tempProcess;
    private Despachante despachante;
    private Random mRandom = new Random();

    public boolean stop = false;

    public Printer(Despachante d){
        this.despachante = d;
    }

    public void newProcessPrinter(Process process){
        list.addLast(process);
    }

    public void processing(){       
        if(!list.isEmpty()){
            tempProcess = list.getFirst();
            
            tempProcess.setPrinter_cycles_processed(1);
            
            if(mRandom.nextInt() < 20 || tempProcess.printerComplete()){
                despachante.receiveBlockedProcess(tempProcess);
                list.removeFirst();
            }
        }
    }
    

    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.MILLISECONDS.sleep(210);
            } catch (InterruptedException ex) {
                Logger.getLogger(Despachante.class.getName()).log(Level.SEVERE, null, ex);
            }
            processing();
        }
    }


}
