import java.util.LinkedList;
import java.util.Random;

public class Core{
    private int quantum;
    private Process actual_process;
    private Despachante despachante;

    public final static int PRINTER = 1;
    public final static int DISK = 2;
    public final static int END = 3;

    private Random random = new Random();

    private boolean busy = false;

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
        processing(process);
    }

    private void processing(Process process){
        busy = true;
        actual_process = process;

        actual_process.setState(Process.IN_EXECUTION);

        while(actual_process.getState() == Process.IN_EXECUTION){

        if(random.nextInt(100) < 20){
            if(actual_process.hasDisk() && !actual_process.diskComplete())
                ioBlock(DISK);
        }
        else if(random.nextInt(100) < 20){
            if(actual_process.hasPrinter() && !actual_process.printerComplete())
                ioBlock(PRINTER);
        }
        else if(!actual_process.CPUComplete()){
            actual_process.setcycles_to_complete(1);
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
        if(actual_process.CPUComplete())
            despachante.fromCore(actual_process, END);

        busy = false;
    }

}
