public class Core{
    private int quantum;
    private Process actual_process;
    private Random random = new Random();

    private boolean busy = false;

    public int getQuantum() {
        return quantum;
    }

    public Process getActual_process() {
        return actual_process;
    }

    public boolean isBusy() {
        return busy;
    }

    public void processing(Process process){
        busy = true;
        actual_process = process;

        actual_process.setState(Process.IN_EXECUTION);

        while(actual_process.getState == Process.IN_EXECUTION){
        if(random.nextInt(100) < 20){
            if(actual_process.hasDisk() && !actual_process.diskComplete()){
                actual_process.setState(Process.BLOCKED);

                busy = false;
            }
        }
        else if(!actual_process.CPUComplete()){
            actual_process.setcycles_to_complete(1);
        }
        }
    }

}
