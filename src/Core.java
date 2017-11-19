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

    private Random random = new Random();

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
    }

    public void toProcess(Process process){
       // System.out.println("Core nao ocupado, startando");
        if(!busy){
            actual_process = process;
            busy = true;    
        }
    }

    private void processing(){
        if(busy){
        actual_process.setState(Process.IN_EXECUTION);
        
        if(actual_process.isDone()){
            System.out.println("Processo pronto, encerrando.");
            despachante.fromCore(actual_process, END);
        }

        while(actual_process.getState() == Process.IN_EXECUTION){
        System.out.printf("CPU Processo %d adicionado! CPU:%d DISK:%d PRINTER:%d\n%d C = %d D = %d P = %d\n", actual_process.getId(), actual_process.getcycles_processed(), actual_process.getDisk_cycles_processed(),
                actual_process.getPrinter_cycles_processed(), actual_process.getId(),actual_process.getcycles_to_complete(), actual_process.getDisk_cycles_to_complete(), actual_process.getPrinter_cycles_to_complete());
        if(actual_process.isDone()){
            System.out.printf("Processo pronto! Saindo CPU\n");
            break;
        }
        if(random.nextInt(100) < 20){
            if(actual_process.hasDisk() && !actual_process.diskComplete())
                ioBlock(DISK);
        }
        else if(random.nextInt(100) < 20){
            if(actual_process.hasPrinter() && !actual_process.printerComplete())
                ioBlock(PRINTER);
        }
        else if(!actual_process.CPUComplete()){
            
            actual_process.setcycles_processed(1);
            System.out.printf("CPU executou. Processo %d. Atual %d\n",actual_process.getId(), actual_process.getcycles_processed());
        }
        else{
            if(actual_process.hasPrinter() && !actual_process.printerComplete()){
                ioBlock(PRINTER);
            }
            else if(actual_process.hasDisk() && !actual_process.diskComplete()){
                ioBlock(PRINTER);
            }
        }
        
        }
        if(actual_process.isDone())
            despachante.fromCore(actual_process, END);

        busy = false;
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
