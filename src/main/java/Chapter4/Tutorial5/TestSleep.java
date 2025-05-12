package Chapter4.Tutorial4;

public class TestSleep {
    public static void printSleep(int thread) {
        System.out.println("Thread-"+thread+" ONE");
        System.out.println("Thread-"+thread+" TWO");
        System.out.println("Thread-"+thread+" THREE");
        System.out.println("Thread-"+thread+" xxxxxxxxxxxx");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            final int thread = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    printSleep(thread);
                }
            });
            t.start();

            try{
                Thread.sleep(0);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
