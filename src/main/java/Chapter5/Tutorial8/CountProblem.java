package Chapter5.Tutorial8;

import java.util.concurrent.atomic.AtomicInteger;

class CountProblem implements Runnable{

    private AtomicInteger count = new AtomicInteger();//new modification

    @Override
    public void run(){
        for(int i=1; i<=5; i++){
            processSomething(i);
            count.incrementAndGet();//new modification
        }
    }

    public int getCount(){
        return count.get();//new modification
    }

    private void processSomething(int i){
        try{
            Thread.sleep(i*200);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}