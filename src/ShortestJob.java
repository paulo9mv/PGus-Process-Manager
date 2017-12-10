
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
        if(o1.getDiskCyclesToComplete() + o1.getCyclesToComplete() + o1.getPrinterCyclesToComplete() > o2.getDiskCyclesToComplete() + o2.getCyclesToComplete() + o2.getPrinterCyclesToComplete())
            return 1;
        if(o1.getDiskCyclesToComplete() + o1.getCyclesToComplete() + o1.getPrinterCyclesToComplete() < o2.getDiskCyclesToComplete() + o2.getCyclesToComplete() + o2.getPrinterCyclesToComplete())
            return -1;
        return 0;       
    }   
}
