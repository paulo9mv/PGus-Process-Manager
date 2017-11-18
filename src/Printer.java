import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Printer implements Runnable{
    private LinkedList<Process> list = new LinkedList<Process>();
    private Process tempProcess;
    private Despachante despachante;
    private Random mRandom = new Random();

    public boolean stop = false;
    public boolean first = true;

    public Printer(Despachante d){
        this.despachante = d;
    }

    public void newProcessPrinter(Process process){
        list.addLast(process);
        if(first){
            tempProcess = process;
            first = false;
        }
    }

    public void processing(){       
            if(!list.isEmpty()){
               System.out.printf("Impressora Executando Processo %d\n", tempProcess.getId());
            if(tempProcess.printerComplete()){
                despachante.receiveBlockedProcess(tempProcess);
                
                try{
                tempProcess = list.removeFirst();
                }
                catch(Exception NoSuchElementException){
                    tempProcess = null;
                }
            }
            
            if(tempProcess != null){
            if(mRandom.nextInt(100) < 5){  //Random pause on process request I/O
                despachante.receiveBlockedProcess(tempProcess);

                 try{
                tempProcess = list.removeFirst();
                }
                catch(Exception NoSuchElementException){
                    
                }
            }
            else{
                tempProcess.setPrinter_cycles_processed(1);
                System.out.printf("Printer ciclo realizado processo %d Atual: %d\n", tempProcess.getId(), tempProcess.getPrinter_cycles_processed());
            }
            }
        }
    }
    

    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.MILLISECONDS.sleep(210);
            } catch (InterruptedException ex) {
                Logger.getLogger(Despachante.class.getName()).log(Level.SEVERE, null, ex);
            }
            processing();
        }
    }


}
