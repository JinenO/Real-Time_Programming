package Chapter1;

public class SimpleThreads {
    //For Week 1 task
    //Display a message, with the name of current threads
    static void threadMessage(String message){
        String threadName=Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }

    private static class MessageLoop implements Runnable {
        public void run() {
            String importantInfo[]=
                    {
                            "Mares eat oats",
                            "Does eat oats",
                            "Little lambs eat ivy",
                            "A kid will eat ivy too"
                    };
            try{
                for(int i=0;i<importantInfo.length;i++){
                    //Pause for 4 seconds
                    Thread.sleep(4000);
                    //Print a message after that
                    threadMessage(importantInfo[i]);
                }
            }catch(InterruptedException e){
                threadMessage("I wasn't done!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //Delay, in milliseconds before we interrupt MessageLoop
        //thread (default one hour).
        long patience= 1000*60*60;

        //if command line argument presents,
        //give patience in seconds.
        if(args.length>0){
            try{
                patience=Long.parseLong(args[0]);
            }catch(NumberFormatException e){
                System.err.println("Argument must be a number!");
                System.exit(1);
            }
        }
        threadMessage("Starting MessageLoop Thread");
        long startTime = System.currentTimeMillis();
        Thread t= new Thread(new MessageLoop());
        t.start();

        threadMessage("Waiting for MessageLoop to finish");
        //loop until MessageLoop
        //thread exits
        while(t.isAlive()){
            threadMessage("Waiting for MessageLoop to finish");
            //wait maximum 1 second
            t.join(1000);
            if(((System.currentTimeMillis()-startTime)>patience)&&(t.isAlive())){
                threadMessage("Tired for waiting");
                t.interrupt();
                t.join();
            }
        }
        threadMessage("Finally");
    }
}
