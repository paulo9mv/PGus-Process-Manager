import java.util.LinkedList;
import java.util.Random;
public class Despachante implements Runnable{
    private Core core;
    private Process processToCore;
    private Disk disk;

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
    private Printer printer;
    private Scheduling scheduling;

    public boolean stop = false;

    public LinkedList<Process> completedProcess = new LinkedList<Process>();

    public void inicialize(){
        this.core = new Core(this);
        this.disk = new Disk(this);
        this.printer = new Printer(this);
        this.scheduling = new Scheduling(this);
    }
    
    public void sendToCore(){
        System.out.printf("Despachante Thread\n");
        if(!core.isBusy() && scheduling.getnextProcess() != null)
            core.toProcess(scheduling.getnextProcess());
    }

    public void toScheduling(Process process){
        if(!process.isDone())
            scheduling.insertnewProcess(process);
        else
            completedProcess.addLast(process);
    }

    
    

    public void receiveBlockedProcess(Process process){
        if(!process.isDone()){
            scheduling.insertnewProcess(process);
        }
    }



    public void fromCore(Process process, int flag){
        if(flag == Core.PRINTER){
            printer.newProcessPrinter(process);
        }
        else if(flag == Core.DISK){
            disk.newProcessDisk(process);
        }
        else
            System.out.println("Process " +process.getId()+" completed!");
    }

    @Override
    public void run() {
        while(true){
            sendToCore();
        }
    }

}
