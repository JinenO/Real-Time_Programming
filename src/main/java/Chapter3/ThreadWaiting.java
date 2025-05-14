package Chapter3;

public class ThreadWaiting {
    public static void main(String[] args) {
        Thread t1= new Thread(){
            public void run(){
                try{
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        Thread t2= new Thread(){
            public void run(){
                try{
                    t1.join();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        t2.start();

        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(t1.getState());
        System.out.println(t2.getState());
    }
}
//if without the try threadsleep at main,
//output will be RUNNABLE, WAITING
//but if include the sleep, output will be TIMED_WAITING, WAITING
//bacause without thread sleep in main
//the cpu run fast and the t1 still running
//and do not run until the thread.sleep at its run method
//so it will be runnable but not timed-waiting if without sleep in main