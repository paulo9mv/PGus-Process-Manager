import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager implements Runnable{
    private Core core;
    private Process processToCore;
    private Disk disk;
    private Printer printer;
    private Scheduling scheduling;

    public LinkedList<Process> completedProcess = new LinkedList<Process>();

    public boolean stop = false;

    public Core getCore() {
        return core;
    }

    public Disk getDisk() {
        return disk;
    }

    public Printer getPrinter() {
        return printer;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void inicialize(boolean preemptive, int algorithm){
        this.core = new Core(this, preemptive);
        this.disk = new Disk(this);
        this.printer = new Printer(this);
        this.scheduling = new Scheduling(this, algorithm);
    }

    public void sendToCore(){
        processToCore = scheduling.getnextProcess();
      
        if(!core.isBusy() && processToCore != null){                     
            scheduling.apply();
            core.toProcess(processToCore);
        }
    }

    public void toScheduling(Process process){
        if(!process.isDone()){
            scheduling.insertnewProcess(process);
        }
        else
            completedProcess.addLast(process);
    }


    public void receiveBlockedProcess(Process process){
        if(!process.isDone())
            scheduling.insertnewProcess(process);        
        else{            
            System.out.printf(process.getName() + " " + process.isDone() + "\n");
            qtd++;
        }
    }

    public int qtd = 0;

    public void fromCore(Process process, int flag){
        if(flag == Core.PRINTER)
            printer.newProcessPrinter(process);
        else if(flag == Core.DISK)
            disk.newProcessDisk(process);
        
        else{
            process.setState(Process.READY);
            System.out.printf("Process "+process.getId()+" completed! %d %d %d || %d %d %d\n",  process.getcycles_to_complete(), process.getDisk_cycles_to_complete(), process.getPrinter_cycles_to_complete()
                    ,process.getcycles_processed(), process.getDisk_cycles_processed(), process.getPrinter_cycles_processed());
            qtd++;
        }
    }

    @Override
    public void run() {
        while(!stop){

            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
            sendToCore();
        }
    }

}
