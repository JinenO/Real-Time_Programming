package Chapter4.Tutorial6;

public class MyThread extends Thread {
    private volatile boolean running=true;
    @Override
    public void run() {
        while(running){
            System.out.println("System is running...");
            try{
                Thread.sleep(200);
            } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
            }
        }
        System.out.println("Thread is shutting down...");
    }
    public void shutdown(){
        running=false;
    }
}
