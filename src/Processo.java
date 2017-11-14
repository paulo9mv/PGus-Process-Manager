public class Processo{
    private int id;
    private int state;

    private String name;
    private int time_to_complete;
    private int time_processed;

    private boolean printer;
    private boolean disk;

    private int printer_time_to_complete;
    private int printer_time_processed;

    private int disk_time_to_complete;
    private int disk_time_processed;
    
    
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

    public int getTime_to_complete() {
        return time_to_complete;
    }

    public void setTime_to_complete(int time_to_complete) {
        this.time_to_complete = time_to_complete;
    }

    public boolean isPrinter() {
        return printer;
    }

    public void setPrinter(boolean printer) {
        this.printer = printer;
    }

    public boolean isDisk() {
        return disk;
    }

    public void setDisk(boolean disk) {
        this.disk = disk;
    }

    public int getPrinter_time_to_complete() {
        return printer_time_to_complete;
    }

    public void setPrinter_time_to_complete(int printer_time_to_complete) {
        this.printer_time_to_complete = printer_time_to_complete;
    }

    public int getDisk_time_to_complete() {
        return disk_time_to_complete;
    }

    public void setDisk_time_to_complete(int disk_time_to_complete) {
        this.disk_time_to_complete = disk_time_to_complete;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTime_processed() {
        return time_processed;
    }

    public void setTime_processed(int time_processed) {
        this.time_processed = time_processed;
    }

    public int getPrinter_time_processed() {
        return printer_time_processed;
    }

    public void setPrinter_time_processed(int printer_time_processed) {
        this.printer_time_processed = printer_time_processed;
    }

    public int getDisk_time_processed() {
        return disk_time_processed;
    }

    public void setDisk_time_processed(int disk_time_processed) {
        this.disk_time_processed = disk_time_processed;
    }
    
    

}
