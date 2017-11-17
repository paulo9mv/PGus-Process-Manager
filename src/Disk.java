import java.util.LinkedList;
import java.util.Random;
public class Disk{
    private LinkedList<Process> list = new LinkedList<Process>();
    private Process tempProcess;
    private Despachante despachante;
    private Random mRandom = new Random();

    public boolean stop = false;
    public boolean first = true;

    public void newProcessDisk(Process process){
        list.addLast(process);
        if(first){
            tempProcess = process;
            first = false;
        }
    }

    public void processing(){
        while(!stop){
            if(!list.isEmpty()){
            if(tempProcess.diskComplete()){
                despachante.receiveBlockedProcess(tempProcess);

                tempProcess = list.removeFirst();
            }

            if(mRandom.nextInt(100) < 20){  //Random pause on process request I/O
                despachante.receiveBlockedProcess(tempProcess);

                tempProcess = list.removeFirst();
            }
            else
                tempProcess.setDisk_cycles_to_complete(1);
        }
    }
    }

}
