package Chapter3;

public class ThreadTimedWaiting {
    public static void main(String[] args) {
        Thread t= new Thread(){
            public void run(){
                try{
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(t.getState());
    }
}
//the output will be TIMED_WAITING
//because it have Thread.sleep at its run method
//it need to wait until the Thread stop sleep
//to continue