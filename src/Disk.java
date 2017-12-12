import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class Disk implements Runnable{
    private ConcurrentLinkedQueue<Process> list = new ConcurrentLinkedQueue<Process>();
    private Process tempProcess;
    private Manager manager;
    private Random mRandom = new Random();
    private boolean stop;

    /**
     * 
     * @return Retorna se o disco está parado.
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * 
     * @param stop Caso true, interrompe o disco. Caso false, inicia o disco.
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
    * Construtor recebe a instância do despachante responsável pela movimentação dos processos.
    */
    public Disk(Manager d){
        this.manager = d;
    }

    /**
    * Recebe um processo para ser inserido na fila do disco.
    */
    public void newProcessDisk(Process process){
        list.add(process);      
    }

    private void processing(){
        if(!list.isEmpty()){
            tempProcess = list.element();
            //System.out.printf("Disco processando %s\n",tempProcess.getName());
            tempProcess.setDiskCyclesProcessed(1);

            if(mRandom.nextInt(100) < 20 || tempProcess.diskComplete()){
                manager.receiveBlockedProcess(tempProcess);
                list.poll();
            }
        }
    }

    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException ex) {
                
            }
            processing();
        }
    }
}
