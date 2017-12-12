public class Process{
    public static final int END = 0;
    public static final int IN_EXECUTION = 1;
    public static final int READY = 2;
    public static final int BLOCKED = 3;

    private int id;
    private int state;

    private String name;

    private int cyclesToComplete;
    private int cyclesProcessed;

    private boolean printer;
    private boolean disk;

    private int printerCyclesToComplete;
    private int printerCyclesProcessed;

    private int diskCyclesToComplete;
    private int diskCyclesProcessed;

    public Process(int id, String name, int cycles, int disk, int printer){
        this.id = id;
        this.name = name;
        this.cyclesToComplete = cycles;
        this.printerCyclesToComplete = printer;
        this.diskCyclesToComplete = disk;
        this.disk = disk > 0;
        this.printer = printer > 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCyclesToComplete() {
        return cyclesToComplete;
    }

    public void setCyclesToComplete(int cyclesToComplete) {
        this.cyclesToComplete = cyclesToComplete;
    }

    public boolean hasPrinter() {
        return printer;
    }

    public void setPrinter(boolean printer) {
        this.printer = printer;
    }

    public boolean hasDisk() {
        return disk;
    }

    public void setDisk(boolean disk) {
        this.disk = disk;
    }

    public int getPrinterCyclesToComplete() {
        return printerCyclesToComplete;
    }

    public void setPrinterCyclesToComplete(int printerCyclesToComplete) {
        this.printerCyclesToComplete = printerCyclesToComplete;
    }

    public int getDiskCyclesToComplete() {
        return diskCyclesToComplete;
    }

    public void setDiskCyclesToComplete(int diskCyclesToComplete) {
        this.diskCyclesToComplete = diskCyclesToComplete;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCyclesProcessed() {
        return cyclesProcessed;
    }

    public void setCyclesProcessed(int cyclesProcessed) {
        this.cyclesProcessed += cyclesProcessed;
    }

    public int getPrinterCyclesProcessed() {
        return printerCyclesProcessed;
    }

    public void setPrinterCyclesProcessed(int printerCyclesProcessed) {
        this.printerCyclesProcessed += printerCyclesProcessed;
    }

    public int getDiskCyclesProcessed() {
        return diskCyclesProcessed;
    }

    public void setDiskCyclesProcessed(int diskCyclesProcessed) {
        this.diskCyclesProcessed += diskCyclesProcessed;
    }

    public boolean diskComplete(){
        return diskCyclesProcessed >= diskCyclesToComplete;
    }
    public boolean printerComplete(){
        return printerCyclesProcessed >= printerCyclesToComplete;
    }
    public boolean CPUComplete(){
        return cyclesProcessed >= cyclesToComplete;
    }
    public boolean isDone(){
        return CPUComplete() && printerComplete() && diskComplete();
    }
}
