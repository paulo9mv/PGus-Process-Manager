public class Process{
    public static final int IN_EXECUTION = 1;
    public static final int READY = 2;
    public static final int BLOCKED = 3;

    private int id;
    private int state;

    private String name;

    private int cycles_to_complete;
    private int cycles_processed;

    private boolean printer;
    private boolean disk;

    private int printer_cycles_to_complete;
    private int printer_cycles_processed;

    private int disk_cycles_to_complete;
    private int disk_cycles_processed;

    public Process(int id, String name, int cycles, int disk, int printer){
        this.id = id;
        this.name = name;
        this.cycles_to_complete = cycles;
        this.printer_cycles_to_complete = printer;
        this.disk_cycles_to_complete = disk;

        if(disk > 0)
            this.disk = true;
        if(printer > 0)
            this.printer = true;

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

    public int getcycles_to_complete() {
        return cycles_to_complete;
    }

    public void setcycles_to_complete(int cycles_to_complete) {
        this.cycles_to_complete += cycles_to_complete;
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

    public int getPrinter_cycles_to_complete() {
        return printer_cycles_to_complete;
    }

    public void setPrinter_cycles_to_complete(int printer_cycles_to_complete) {
        this.printer_cycles_to_complete = printer_cycles_to_complete;
    }

    public int getDisk_cycles_to_complete() {
        return disk_cycles_to_complete;
    }

    public void setDisk_cycles_to_complete(int disk_cycles_to_complete) {
        this.disk_cycles_to_complete = disk_cycles_to_complete;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getcycles_processed() {
        return cycles_processed;
    }

    public void setcycles_processed(int cycles_processed) {
        this.cycles_processed = cycles_processed;
    }

    public int getPrinter_cycles_processed() {
        return printer_cycles_processed;
    }

    public void setPrinter_cycles_processed(int printer_cycles_processed) {
        this.printer_cycles_processed = printer_cycles_processed;
    }

    public int getDisk_cycles_processed() {
        return disk_cycles_processed;
    }

    public void setDisk_cycles_processed(int disk_cycles_processed) {
        this.disk_cycles_processed += disk_cycles_processed;
    }

    public boolean diskComplete(){
        if(disk_cycles_processed - disk_cycles_to_complete == 0)
            return true;
        return false;
    }
    public boolean printerComplete(){
        if(printer_cycles_processed - printer_cycles_to_complete == 0)
            return true;
        return false;
    }
    public boolean CPUComplete(){
        if(cycles_processed - cycles_to_complete == 0)
            return true;
        return false;
    }
    public boolean isDone(){
        if(CPUComplete() && printerComplete() && diskComplete())
            return true;
        return false;
    }
}
