import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Scheduling {
    public final static int FIFO = 0;
    public final static int SHORTEST = 1;
    public final static int ROUNDROBIN = 2;
    public final static int LESSREMAINING = 3;

    private ConcurrentLinkedQueue<Process> list = new ConcurrentLinkedQueue<Process>();
    private final PriorityBlockingQueue<Process> priorityQueue;
    private Comparator<Process> comparator;

    private Process nextProcess;
    private Process currentProcess;
    private final Manager manager;

    private final int currentAlgorithm;
    public boolean mono;

    public Scheduling(Manager d, int algorithm, boolean mono){
        this.manager = d;
        this.currentAlgorithm = algorithm;
        this.mono = mono;
        
        if(this.currentAlgorithm == SHORTEST)
            this.comparator = new ShortestJob();
        else if(this.currentAlgorithm == LESSREMAINING)
            this.comparator = new CyclesComparator();
        
        this.priorityQueue = new PriorityBlockingQueue<Process>(11, comparator);
    }

    public void apply(){    
        if(currentAlgorithm == FIFO || currentAlgorithm == ROUNDROBIN)
            if (!list.isEmpty())
                this.list.poll();
        if(currentAlgorithm == SHORTEST || currentAlgorithm == LESSREMAINING)           
            if(!priorityQueue.isEmpty())    
                this.priorityQueue.poll();
    }
    
    public void setCurrentProcess(){
         if(currentAlgorithm == FIFO || currentAlgorithm == ROUNDROBIN)
            if (!list.isEmpty())
                currentProcess = this.list.poll();
         else
                currentProcess = null;
            if(currentAlgorithm == SHORTEST || currentAlgorithm == LESSREMAINING)           
            if(!priorityQueue.isEmpty())    
                currentProcess = this.priorityQueue.poll();
            else 
                currentProcess = null;
    }
    
    public Process getnextProcess(){
        nextProcess = null;
        if(mono){
            return currentProcess;
        }
        else{
            try{
                if (currentAlgorithm == FIFO || currentAlgorithm == ROUNDROBIN)
                    this.nextProcess = this.list.element();
                else if (currentAlgorithm == SHORTEST|| currentAlgorithm == LESSREMAINING)
                    nextProcess = priorityQueue.peek();
            }
            catch(NoSuchElementException e){
            
            }
        }
        return nextProcess;
    }

    public void insertnewProcess(Process newProcess) {       
        newProcess.setState(Process.READY);
        if(currentProcess == null && mono){
            currentProcess = newProcess;
        }
        else{
            if(currentAlgorithm == FIFO || currentAlgorithm == ROUNDROBIN)
                this.list.add(newProcess);
            else if(currentAlgorithm == SHORTEST|| currentAlgorithm == LESSREMAINING)          
                this.priorityQueue.add(newProcess);                     
        }
    }
}
