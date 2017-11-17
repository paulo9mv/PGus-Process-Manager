import java.util.LinkedList;
import java.util.Random;
public class Despachante{
    private Core core;
    private Process processToCore;
    private Disk disk;
    private Printer printer;
    private Scheduling scheduling;

    public boolean stop = false;

    public LinkedList<Process> completedProcess = new LinkedList<Process>();

    public Despachante(Core core){
        this.core = core;
    }

    public void toScheduling(Process process){
        if(!process.isDone())
            scheduling.insertnewProcess(process);
        else
            completedProcess.addLast(process);
    }

    public void sendToCore(){
        while(!stop){
        while(core.isBusy()){

        }

        if(scheduling.getnextProcess() != null)
            core.toProcess(scheduling.getnextProcess());
    }
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

}
