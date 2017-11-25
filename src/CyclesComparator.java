
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Paulo Vitor
 */
public class CyclesComparator implements Comparator<Process>{

    @Override
    public int compare(Process o1, Process o2) {
        int cyclesToEnd1 = o1.getDisk_cycles_to_complete() - o1.getDisk_cycles_processed();
        int cyclesToEnd2 = o2.getDisk_cycles_to_complete() - o2.getDisk_cycles_processed();
        
        int diskToEnd1 = o1.getDisk_cycles_to_complete() - o1.getDisk_cycles_processed();
        int diskToEnd2 = o2.getDisk_cycles_to_complete() - o2.getDisk_cycles_processed();
                
        int printerToEnd1 = o1.getPrinter_cycles_to_complete() - o1.getPrinter_cycles_processed();
        int printerToEnd2 = o2.getPrinter_cycles_to_complete() - o2.getPrinter_cycles_processed();
        
        if(cyclesToEnd1 + diskToEnd1 + printerToEnd1 > cyclesToEnd2 + diskToEnd2 + printerToEnd2)
            return 1;
        if(cyclesToEnd1 + diskToEnd1 + printerToEnd1 < cyclesToEnd2 + diskToEnd2 + printerToEnd2)
            return -1;
        return 0;
    }
    
}
