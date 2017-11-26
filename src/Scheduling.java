
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Scheduling {
    public final static int FIFO = 0;
    public final static int SHORTEST = 1;
    public final static int ROUNDROBIN = 2;

    private LinkedList<Process> list = new LinkedList<Process>();
    private PriorityBlockingQueue<Process> priorityQueue;
    private Comparator<Process> comparator;

    private Process nextProcess;
    private Manager manager;

    private int currentAlgorithm = 1;

    public Scheduling(Manager d) {
        this.manager = d;
        this.comparator = new CyclesComparator();
        this.priorityQueue = new PriorityBlockingQueue<Process>(11, comparator);
    }

    public void apply() {    
        if(currentAlgorithm == FIFO)
            if (!list.isEmpty())
                this.list.removeFirst();
        if(currentAlgorithm == SHORTEST){           
            if(!priorityQueue.isEmpty()){              
                this.priorityQueue.poll();
            }
                
        }
    }

    public Process getnextProcess() {
        nextProcess = null;
        try{
            if (currentAlgorithm == FIFO)
                this.nextProcess = this.list.getFirst();
            else if (currentAlgorithm == SHORTEST)
                nextProcess = priorityQueue.peek();
        }
        catch(NoSuchElementException e){
            
        }
        
        return nextProcess;
    }

    public void insertnewProcess(Process newProcess) {
        
        newProcess.setState(Process.READY);
        if(currentAlgorithm == FIFO)
        this.list.addLast(newProcess);
        else if(currentAlgorithm == SHORTEST){
            //System.out.println("Inserting");
            this.priorityQueue.add(newProcess);
            //System.out.printf("Adicionando %s -> %d %d %d\n", newProcess.getName(), newProcess.getDisk_cycles_to_complete(), newProcess.getDisk_cycles_to_complete(), newProcess.getPrinter_cycles_to_complete());
        }
    }
}
