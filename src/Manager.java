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

    public void inicialize(){
        this.core = new Core(this);
        this.disk = new Disk(this);
        this.printer = new Printer(this);
        this.scheduling = new Scheduling(this);
    }

    public void sendToCore(){

        processToCore = scheduling.getnextProcess();
        /*if(processToCore == null){
            System.out.printf("Escalonador vazio!\n");
        }*/
        if(!core.isBusy() && processToCore != null){
            //System.out.printf("Enviando processo %d para a CPU\n", processToCore.getId());
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
        if(!process.isDone()){
            //System.out.printf("Enviando processo %d para o escalonador\n",process.getId());
            scheduling.insertnewProcess(process);
        }
        else{
            System.out.printf("Process "+process.getId()+" completed! %d %d %d || %d %d %d\n",  process.getcycles_to_complete(), process.getDisk_cycles_to_complete(), process.getPrinter_cycles_to_complete()
                    ,process.getcycles_processed(), process.getDisk_cycles_processed(), process.getPrinter_cycles_processed());
        }
    }



    public void fromCore(Process process, int flag){
        if(flag == Core.PRINTER){
           // System.out.printf("Enviando processo %d para printer.\n",process.getId());
            printer.newProcessPrinter(process);
        }
        else if(flag == Core.DISK){
           // System.out.printf("Enviando processo %d para disk.\n",process.getId());
            disk.newProcessDisk(process);
        }
        else{
            process.setState(Process.READY);
            System.out.printf("Process "+process.getId()+" completed! %d %d %d || %d %d %d\n",  process.getcycles_to_complete(), process.getDisk_cycles_to_complete(), process.getPrinter_cycles_to_complete()
                    ,process.getcycles_processed(), process.getDisk_cycles_processed(), process.getPrinter_cycles_processed());
        }
    }

    @Override
    public void run() {
        while(!stop){


            try {
                TimeUnit.MILLISECONDS.sleep(230);
            } catch (InterruptedException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
            sendToCore();
        }
    }

}
