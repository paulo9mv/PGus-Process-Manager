import java.util.LinkedList;
import java.util.Random;
public class Disk implements Runnable{
    private LinkedList<Process> list = new LinkedList<Process>();
    private Process tempProcess;
    private Despachante despachante;
    private Random mRandom = new Random();

    public boolean stop = false;
    public boolean first = true;

    public Disk(Despachante d){
        this.despachante = d;
    }

    public void newProcessDisk(Process process){
        list.addLast(process);
        if(first){
            tempProcess = process;
            first = false;
        }
    }

    public void processing(){ 
            if(!list.isEmpty()){
                System.out.printf("Disco Executando Processo %d\n", tempProcess.getId());
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

    @Override
    public void run() {
        while(true){
            processing();
        }
       
    }

}
