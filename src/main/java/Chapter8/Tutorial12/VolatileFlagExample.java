package Chapter8.Tutorial12;

public class VolatileFlagExample {

    //Shared flag between threads
    private static volatile boolean running= true;

    public static void main(String[] args) {
        //Thread that runs continuously until 'running' become false
        Thread worker= new Thread(()->{
            System.out.println("Worker thread started...");
            while(running){
                //stimulate work
            }
            System.out.println("Worker thread stopped");
        });

        worker.start();

        //Main thread sleeps for a bit the signal stop
        try{
            Thread.sleep(2000);//let worker run for 2 seconds
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        running=false;
        System.out.println("Volatile: Main set running= false");

        try{
            worker.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
