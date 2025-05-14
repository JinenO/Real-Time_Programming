package Chapter5.Tutorial8;

public class TestAtomicInteger1p {
    public static void main (String [] args) throws InterruptedException{

        CountProblem pt= new CountProblem();
        Thread t1= new Thread(pt,"t1");
        Thread t2= new Thread(pt,"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Count="+pt.getCount());
    }
}
