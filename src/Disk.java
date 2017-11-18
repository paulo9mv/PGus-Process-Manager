import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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

                try{
                tempProcess = list.removeFirst();
                }
                catch(Exception NoSuchElementException){
                    
                }
            }
            else{
                System.out.printf("Disco nao completo -> %d %d\n", tempProcess.getDisk_cycles_processed(), tempProcess.getDisk_cycles_to_complete());
                if(mRandom.nextInt(100) < 5){  //Random pause on process request I/O
                despachante.receiveBlockedProcess(tempProcess);

                 try{
                tempProcess = list.removeFirst();
                }
                catch(Exception NoSuchElementException){
                    
                }
            }
            else
                tempProcess.setDisk_cycles_processed(1);
            }

            
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                TimeUnit.MILLISECONDS.sleep(220);
            } catch (InterruptedException ex) {
                Logger.getLogger(Despachante.class.getName()).log(Level.SEVERE, null, ex);
            }
            processing();
        }
       
    }

}
