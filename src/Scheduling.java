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
    
    public void apply(){
        if(!list.isEmpty())
        this.list.removeFirst();
        if(list.isEmpty())
            System.out.printf("Removeu. Agora escalonador vazio!\n");
    }
    
    public Process getnextProcess(){
        try{
        this.nextProcess = this.list.getFirst();
        }
        catch(Exception NoSuchElementException){    
            System.out.printf("Nao ha nada no escalonador\n");
            return null;
        }
        return nextProcess;
    }

    public void insertnewProcess(Process newProcess){
        newProcess.setState(Process.READY);
        this.list.addLast(newProcess);
        System.out.printf("Processo %d adicionado! CPU:%d DISK:%d PRINTER:%d\n", newProcess.getId(), newProcess.getcycles_processed(), newProcess.getDisk_cycles_processed(),newProcess.getPrinter_cycles_processed());
    }

    @Override
    public void run() {
        
    }

}
