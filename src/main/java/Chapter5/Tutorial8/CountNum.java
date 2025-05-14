package Chapter5.Tutorial8;

class CountNum implements Runnable{
    private int count;

    @Override
    public void run() {
        for(int i=1;i<=5;i++){
            processSomething(i);
            synchronized (this){//new modified
                count++;
            }
        }
    }

    public int getCount(){
        return this.count;
    }

    private void processSomething(int i){
        try{
            Thread.sleep(i*200);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

