import java.util.LinkedList;
import java.util.Random;

public class Scheduling implements Runnable{
    private LinkedList<Process> list = new LinkedList<Process>();
    private Process nextProcess;
    private Manager manager;

    private int current_Algorithm;

    public Scheduling(Manager d){
        this.manager = d;
    }

    public void apply(){
        if(!list.isEmpty())
        this.list.removeFirst();

    }

    public Process getnextProcess(){
        nextProcess = null;

        try{
        this.nextProcess = this.list.getFirst();
        }
        catch(Exception NoSuchElementException){

        }
        return nextProcess;
    }

    public void insertnewProcess(Process newProcess){
        newProcess.setState(Process.READY);
        this.list.addLast(newProcess);
        //System.out.printf("Processo %d adicionado! CPU:%d DISK:%d PRINTER:%d\n", newProcess.getId(), newProcess.getcycles_processed(), newProcess.getDisk_cycles_processed(),newProcess.getPrinter_cycles_processed());
    }

    @Override
    public void run() {

    }

}
