import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Core implements Runnable{
    private int quantum = 1000;
    private Process runningProcess;
    private Manager manager;

    public final static int PRINTER = 1;
    public final static int DISK = 2;
    public final static int END = 3;
    public final static int QUANTUM = 4;

    private Random mRandom = new Random();

    private boolean busy = false;
    private boolean stop = false;

    /**
     * 
     * @return Retorna se o processador encerrou.
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * Encerra o processador.
     * @param stop Se true, o processador será encerrado. Se false, o processador será reinicializado.     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    private boolean preemptive;
    private int id;

    /**
     * 
     * @param d O despachante responsável pelo controle e envio de processos.
     * @param b True para sistemas preemptivos, false caso contrário.
     * @param id ID do processador.
     */
    public Core(Manager d, boolean b, int id){
        this.manager = d;
        this.preemptive = b;
        this.id = id;
    }
    /**
     * Seta o valor do quantum do processador em milissegundos.
     * 
     */
    public void setQuantum(int quantum){
        this.quantum = quantum;
    }
    /**
     * 
     * @return Retorna o valor do quantum em milissegundos.
     */
    public int getQuantum() {
        return quantum;
    }
    /**
     * 
     * @return Retorna o atual processo em processamento no processador.
     */
    public Process getRunningProcess() {
        return runningProcess;
    }
    
    /**
     * 
     * @return Retorna true se o processador estiver ocupado e false, caso contrário.
     */
    public boolean isBusy() {
        return busy;
    }
    private void ioBlock(int flag){
        runningProcess.setState(Process.BLOCKED);
        manager.fromCore(runningProcess, flag);
        busy = false;
    }
    
    
    /**
     * Insere um processo no processador. O processo não será aceito caso outro esteja ocupando o processador.
     * 
     * 
     */
    public void toProcess(Process process){
        if(!busy){
            runningProcess = process;
            busy = true;
        }
    }
    private void preemptive(){
        long start = System.currentTimeMillis();
        long end = 0;
        
        do{
            if(runningProcess.isDone())
                ioBlock(Core.END);
            if(!runningProcess.CPUComplete())
                runningProcess.setCyclesProcessed(1);
            
            if(mRandom.nextInt(100) < 20 && !runningProcess.printerComplete()){
                this.ioBlock(Core.PRINTER);
                break;
            }
            else if(mRandom.nextInt(100) < 20 && !runningProcess.diskComplete()){
                this.ioBlock(Core.DISK);
                break;
            }
            if(runningProcess.CPUComplete()){
                if(!runningProcess.diskComplete()){
                    ioBlock(Core.DISK);
                    break;
                }
                else if(!runningProcess.printerComplete()){
                    ioBlock(Core.PRINTER);
                    break;
                }     
            }
            end = System.currentTimeMillis();
        }while((end - start < quantum) && runningProcess.getState() == Process.IN_EXECUTION);
        
        if(end - start < quantum){
            if(runningProcess.getState() == Process.IN_EXECUTION)
                ioBlock(Core.QUANTUM);
        }
        
    }
    private void nonPreemptive(){
        while(runningProcess.getState() == Process.IN_EXECUTION){
            if(runningProcess.isDone())
                ioBlock(Core.END);
            if(!runningProcess.CPUComplete())
                runningProcess.setCyclesProcessed(1);
            
            if(mRandom.nextInt(100) < 20 && !runningProcess.printerComplete()){
                this.ioBlock(Core.PRINTER);
                break;
            }
            else if(mRandom.nextInt(100) < 20 && !runningProcess.diskComplete()){
                this.ioBlock(Core.DISK);
                break;
            }
            if(runningProcess.CPUComplete()){
                if(!runningProcess.diskComplete()){
                    ioBlock(Core.DISK);
                    break;
                }
                else if(!runningProcess.printerComplete()){
                    ioBlock(Core.PRINTER);
                    break;
                }
            }
        }
    }
    
    private void processing(){
        if(busy){
            runningProcess.setState(Process.IN_EXECUTION);
            if(!preemptive)
                this.nonPreemptive();         
            else
                this.preemptive();
        }
    }

    @Override
    public void run() {
        while(!stop){
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
            processing();
        }
    }

}
