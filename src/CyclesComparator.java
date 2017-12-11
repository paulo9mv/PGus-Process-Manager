import java.util.Comparator;

public class CyclesComparator implements Comparator<Process>{

    @Override
    public int compare(Process o1, Process o2) {
       
        int cyclesToEnd1 = o1.getDiskCyclesToComplete() - o1.getDiskCyclesProcessed();
        int cyclesToEnd2 = o2.getDiskCyclesToComplete() - o2.getDiskCyclesProcessed();
        
        int diskToEnd1 = o1.getDiskCyclesToComplete() - o1.getDiskCyclesProcessed();
        int diskToEnd2 = o2.getDiskCyclesToComplete() - o2.getDiskCyclesProcessed();
                
        int printerToEnd1 = o1.getPrinterCyclesToComplete() - o1.getPrinterCyclesProcessed();
        int printerToEnd2 = o2.getPrinterCyclesToComplete() - o2.getPrinterCyclesProcessed();
        
        if(cyclesToEnd1 + diskToEnd1 + printerToEnd1 > cyclesToEnd2 + diskToEnd2 + printerToEnd2)
            return 1;
        if(cyclesToEnd1 + diskToEnd1 + printerToEnd1 < cyclesToEnd2 + diskToEnd2 + printerToEnd2)
            return -1;
        return 0;
       
    }   
}
