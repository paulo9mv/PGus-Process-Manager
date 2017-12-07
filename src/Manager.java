import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager implements Runnable{
    private Core core;
    private Core core2;
    private Process processToCore;
    private Disk disk;
    private Printer printer;
    private Scheduling scheduling;
    public long start;

    public ConcurrentLinkedQueue<Process> completedProcess = new ConcurrentLinkedQueue<Process>();

    public boolean stop = false;
    public boolean mono;
    public boolean multicore;
    private boolean turn = false;
    private int totalProcess;

    public Core getCore() {
        return core;
    }
    public Core getCore2(){
        return core2;
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

    public void inicialize(boolean preemptive, int algorithm, boolean mono, boolean multicore){
        this.core = new Core(this, preemptive, 1);
        this.multicore = multicore;
        if(multicore){
            this.core2 = new Core(this, preemptive, 2);
        }
        
        this.disk = new Disk(this);
        this.printer = new Printer(this);
        this.scheduling = new Scheduling(this, algorithm, mono);
        this.mono = mono;
    }

    private boolean turn(){
        this.turn = !this.turn;
        return this.turn;
    }
    
    public void sendToCore(){
        processToCore = scheduling.getnextProcess();
      
        if(!this.multicore){
            if(!core.isBusy() && processToCore != null){
                if(!mono){
                    scheduling.apply();
                    core.toProcess(processToCore);
                }
                else if(processToCore.getState() != Process.BLOCKED)
                    core.toProcess(processToCore);
            }
        }
        else
        {
            this.turn = turn();
            if(turn && !core.isBusy() && processToCore != null){
                if(!mono){
                    scheduling.apply();
                    core.toProcess(processToCore);
                }
                else if(processToCore.getState() != Process.BLOCKED)
                    core.toProcess(processToCore);
            }
            else if(!turn && !core2.isBusy() && processToCore != null){

                if(!mono){
                    scheduling.apply();
                    core2.toProcess(processToCore);
                }
                else if(processToCore.getState() != Process.BLOCKED)
                    core2.toProcess(processToCore);       
            }
        }

    }//End of sendToCore()

    public void toScheduling(Process process){
        if(!process.isDone())
            scheduling.insertnewProcess(process);
        else         
            end(process);
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


    
    public void setTotalProcess(int p){
        this.totalProcess = p;
    }

    public int getTotalProcess(){
        return this.totalProcess;
    }
    
    private void end(Process process){
        this.completedProcess.add(process);
        System.out.printf("%s completed! %b\n", process.getName(), process.isDone());
        if(this.completedProcess.size() == totalProcess){
            System.out.printf("\nAll process concluded! %d " + (System.currentTimeMillis() - start) + "\n", totalProcess);
            this.stop = true;
            this.core.setStop(true);
            this.disk.setStop(true);
            this.printer.setStop(true);
            if(multicore)
                this.core2.setStop(true);
            
        }
    }
    
    public void fromCore(Process process, int flag){
        if(flag == Core.PRINTER)
            printer.newProcessPrinter(process);
        else if(flag == Core.DISK)
            disk.newProcessDisk(process);
        else if(flag == Core.QUANTUM)
            this.toScheduling(process);
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
