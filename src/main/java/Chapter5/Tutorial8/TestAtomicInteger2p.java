package Chapter5.Tutorial8;

public class TestAtomicInteger2p {
    public static void main (String [] args) throws InterruptedException{

        CountNum pn= new CountNum();
        Thread t1= new Thread(pn,"t1");
        Thread t2= new Thread(pn,"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Count="+pn.getCount());
    }
}