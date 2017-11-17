import java.util.LinkedList;

public class Scheduling{
    private LinkedList<Process> list = new LinkedList<Process>();
    private Process nextProcess;
    
    private int current_Algorithm;
    
    public Process getnextProcess(){
        this.nextProcess = this.list.removeFirst();
        return nextProcess;
    }
    
    public void insertnewProcess(Process newProcess){
        this.list.addLast(newProcess);
    }

}
