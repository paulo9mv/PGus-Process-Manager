import java.util.LinkedList;
import java.util.Random;

public class Scheduling implements Runnable{
    private LinkedList<Process> list = new LinkedList<Process>();
    private Process nextProcess;
    private Despachante despachante;

    private int current_Algorithm;

    public Scheduling(Despachante d){
        this.despachante = d;
    }

    public Process getnextProcess(){
        try{
        this.nextProcess = this.list.removeFirst();
        }
        catch(Exception NoSuchElementException){
            
        }
        return nextProcess;
    }

    public void insertnewProcess(Process newProcess){
        newProcess.setState(Process.READY);
        this.list.addLast(newProcess);
        System.out.printf("Processo %d adicionado!\n", newProcess.getId());
    }

    @Override
    public void run() {
        
    }

}
