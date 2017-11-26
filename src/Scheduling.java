
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Scheduling {
    public final static int FIFO = 0;
    public final static int SHORTEST = 1;
    public final static int ROUNDROBIN = 2;
    public final static int LESSREMAINING = 3;

    private LinkedList<Process> list = new LinkedList<Process>();
    private PriorityBlockingQueue<Process> priorityQueue;
    private Comparator<Process> comparator;

    private Process nextProcess;
    private Manager manager;

    private int currentAlgorithm;

    public Scheduling(Manager d, int algorithm){
        this.manager = d;
        this.currentAlgorithm = algorithm;
        
        if(this.currentAlgorithm == SHORTEST)
            this.comparator = new ShortestJob();
        else if(this.currentAlgorithm == LESSREMAINING)
            this.comparator = new CyclesComparator();
        
        this.priorityQueue = new PriorityBlockingQueue<Process>(11, comparator);
    }

    public void apply() {    
        if(currentAlgorithm == FIFO || currentAlgorithm == ROUNDROBIN)
            if (!list.isEmpty())
                this.list.removeFirst();
        if(currentAlgorithm == SHORTEST || currentAlgorithm == LESSREMAINING)           
            if(!priorityQueue.isEmpty())    
                this.priorityQueue.poll();
    }

    public Process getnextProcess(){
        nextProcess = null;
        try{
            if (currentAlgorithm == FIFO || currentAlgorithm == ROUNDROBIN)
                this.nextProcess = this.list.getFirst();
            else if (currentAlgorithm == SHORTEST|| currentAlgorithm == LESSREMAINING)
                nextProcess = priorityQueue.peek();
        }
        catch(NoSuchElementException e){
            
        }        
        return nextProcess;
    }

    public void insertnewProcess(Process newProcess) {       
        newProcess.setState(Process.READY);
        if(currentAlgorithm == FIFO || currentAlgorithm == ROUNDROBIN)
            this.list.addLast(newProcess);
        else if(currentAlgorithm == SHORTEST|| currentAlgorithm == LESSREMAINING)           
            this.priorityQueue.add(newProcess);                  
    }
}
