import java.util.LinkedList;
import java.util.Random;

public class Scheduling{
    private LinkedList<Process> list = new LinkedList<Process>();
    private Process nextProcess;
    private Despachante despachante;

    private int current_Algorithm;

    public Process getnextProcess(){
        this.nextProcess = this.list.removeFirst();
        return nextProcess;
    }

    public void insertnewProcess(Process newProcess){
        newProcess.setState(Process.READY);
        this.list.addLast(newProcess);
    }

}
