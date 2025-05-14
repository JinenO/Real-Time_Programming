package Chapter2.Activity3;

public class ThreadExample3 {
    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("My Thread is running.");
            System.out.println("My Thread Finished.");
        }
    }

    public static void main(String[] args) {
        Thread myThread = new Thread(new MyRunnable());
        myThread.start();
    }
}
