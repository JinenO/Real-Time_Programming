package Chapter5.Tutorial8;

public class NormalThread extends Thread{

    private int counter;

    @Override
    public void run(){
        for(int i=0; i<20; i++){
            process(i);
            counter++;
        }
    }

    public int getCounter(){
        return this.counter;
    }

    public void process(int i){
        try{
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
