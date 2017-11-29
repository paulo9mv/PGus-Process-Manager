import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager implements Runnable{
    private Core core;
    private Process processToCore;
    private Disk disk;
    private Printer printer;
    private Scheduling scheduling;

    public ConcurrentLinkedQueue<Process> completedProcess = new ConcurrentLinkedQueue<Process>();

    public boolean stop = false;
    public boolean mono;
     public int qtd = 0;

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

    public void inicialize(boolean preemptive, int algorithm, boolean mono){
        this.core = new Core(this, preemptive);
        this.disk = new Disk(this);
        this.printer = new Printer(this);
        this.scheduling = new Scheduling(this, algorithm, mono);
        this.mono = mono;
    }

    public void sendToCore(){
        processToCore = scheduling.getnextProcess();
      
        if(!core.isBusy() && processToCore != null){
            if(!mono){
            scheduling.apply();
            core.toProcess(processToCore);
            }
            else if(processToCore.getState() != Process.BLOCKED){
                core.toProcess(processToCore);
            }
        }
    }

    public void toScheduling(Process process){
        if(!process.isDone()){
            scheduling.insertnewProcess(process);
        }
        else{         
            end(process);
        }
    }


    public void receiveBlockedProcess(Process process){
        if(!process.isDone())
            if(!mono)
                scheduling.insertnewProcess(process);
            else
                process.setState(Process.READY);
        else{            
            end(process);
            if(mono)
                this.scheduling.setCurrentProcess();
        }
    }

    private void end(Process process){
        this.completedProcess.add(process);
        System.out.printf("%s completed! %b\n", process.getName(), process.isDone());
    }
    
    public void fromCore(Process process, int flag){
        if(flag == Core.PRINTER)
            printer.newProcessPrinter(process);
        else if(flag == Core.DISK)
            disk.newProcessDisk(process);       
        else{
            if(mono)
                this.scheduling.setCurrentProcess();
            process.setState(Process.READY);
            end(process);
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
            sendToCore();
        }
    }

}
