package Chapter3;

public class ThreadRunnable {
    public static void main(String[] args) {
        Thread t= new Thread();
        t.start();
        System.out.println(t.getState());
    }
}
//Output will be RUNNABLE
