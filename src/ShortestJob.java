import java.util.Comparator;

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
