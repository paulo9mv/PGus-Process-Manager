
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
public class ShortestJob implements Comparator<Process>{

    @Override
    public int compare(Process o1, Process o2) {
        if(o1.getDisk_cycles_to_complete() + o1.getcycles_to_complete() + o1.getPrinter_cycles_to_complete() > o2.getDisk_cycles_to_complete() + o2.getcycles_to_complete() + o2.getPrinter_cycles_to_complete())
            return 1;
        if(o1.getDisk_cycles_to_complete() + o1.getcycles_to_complete() + o1.getPrinter_cycles_to_complete() < o2.getDisk_cycles_to_complete() + o2.getcycles_to_complete() + o2.getPrinter_cycles_to_complete())
            return -1;
        return 0;       
    }   
}
