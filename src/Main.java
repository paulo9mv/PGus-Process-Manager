import java.util.Random;
import java.util.Scanner;
public class Main{

    public static void main(String args[]) throws InterruptedException{
        Manager manager = new Manager();
        
        int process = choicesProcess(manager);
        manager.setTotalProcess(process);
        
        new Thread(manager).start();

        new Thread(manager.getCore()).start();
        if(manager.isMulticore())
            new Thread(manager.getCore2()).start();
        new Thread(manager.getDisk()).start();
        new Thread(manager.getPrinter()).start();
        
        Random mRandom = new Random();
        
        manager.setStart(System.currentTimeMillis());
        
        for(int i = 0; i < process; i++)
            manager.toScheduling(new Process(i, "Process" + i, mRandom.nextInt(10) + 1, mRandom.nextInt(10), mRandom.nextInt(10)));    
    }
    
    public static int choicesProcess(Manager m){
        Scanner scan = new Scanner(System.in);
        boolean mono, preemptive = false, multicore = false;
        int algorithm;
        
        System.out.printf("Choose:\n1 - Monoprogrammed, 2 - Multiprogrammed\n");
        if(scan.nextInt() == 1){
            mono = true;
        }
        else
            mono = false;
        
        if(mono){
            System.out.printf("Choose:\nMono: 0 - FIFO, 1 - SHORTEST, 3 - LESSTIME\n");
            algorithm = scan.nextInt();
        }
        else{
            System.out.printf("Choose:\n0 - Preemptive, 1 - Non Preemptive\n");
            if(scan.nextInt() == 0){
                preemptive = true;
            }
            else
                preemptive = false;
            if(preemptive){
                System.out.printf("Choose:\n0 - FIFO, 1 - SHORTEST, 2 - ROUNDROBIN, 3 - LESSTIME\n");
            algorithm = scan.nextInt();
            }
            else{
                System.out.printf("Choose:\n0 - FIFO, 1 - SHORTEST, 3 - LESSTIME\n");
            algorithm = scan.nextInt();
            }
        }
        if(!mono){
        System.out.printf("Choose:\n0 - Monocore, 1 - Multicore\n");
        if(scan.nextInt() == 0)
            multicore = false;
        else
            multicore = true;
        }
        
        m.inicialize(preemptive, algorithm, mono, multicore);
        
        
        System.out.printf("Number of process:\n");
        return scan.nextInt();
    }
}
