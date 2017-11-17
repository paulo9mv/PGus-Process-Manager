import java.util.LinkedList;
import java.util.Random;
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
            System.out.printf("Printer Thread\n");
            if(!list.isEmpty()){
               System.out.printf("Impressora Executando Processo %d\n", tempProcess.getId());
            if(tempProcess.printerComplete()){
                despachante.receiveBlockedProcess(tempProcess);

                tempProcess = list.removeFirst();
            }

            if(mRandom.nextInt(100) < 20){  //Random pause on process request I/O
                despachante.receiveBlockedProcess(tempProcess);

                tempProcess = list.removeFirst();
            }
            else
                tempProcess.setPrinter_cycles_to_complete(1);
        }
    }
    

    @Override
    public void run() {
        while(true){
            processing();
        }
    }


}
