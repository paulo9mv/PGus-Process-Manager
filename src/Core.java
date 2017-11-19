import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Core implements Runnable{
    private int quantum;
    private Process actual_process;
    private Despachante despachante;

    public final static int PRINTER = 1;
    public final static int DISK = 2;
    public final static int END = 3;

    private Random mRandom = new Random();

    private boolean busy = false;
    public boolean stop = false;

    public Core(Despachante d){
        this.despachante = d;
    }

    public int getQuantum() {
        return quantum;
    }

    public Process getActual_process() {
        return actual_process;
    }

    public boolean isBusy() {
        return busy;
    }

    private void ioBlock(int flag){
        actual_process.setState(Process.BLOCKED);
        despachante.fromCore(actual_process, flag);
        busy = false;
    }

    public void toProcess(Process process){
        if(!busy){
            actual_process = process;
            busy = true;    
        }
    }

    private void processing(){
        if(busy){
            actual_process.setState(Process.IN_EXECUTION);
            
            while(actual_process.getState() == Process.IN_EXECUTION){
            if(actual_process.isDone())
                ioBlock(Core.END);
            if(!actual_process.CPUComplete()){
                actual_process.setcycles_processed(1);
            }
            if(mRandom.nextInt(100) < 20 && !actual_process.printerComplete()){
                this.ioBlock(Core.PRINTER);
                break;
            }
            else if(mRandom.nextInt(100) < 20 && !actual_process.diskComplete()){
                this.ioBlock(Core.DISK);
                break;
            }
            if(actual_process.CPUComplete()){
                if(!actual_process.diskComplete()){
                    ioBlock(Core.DISK);
                    break;
                }
                else if(!actual_process.printerComplete()){
                    ioBlock(Core.PRINTER);
                    break;
                }
            }
            
            }
        }
    }

    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Despachante.class.getName()).log(Level.SEVERE, null, ex);
            }
            processing();
        }
    }

}
