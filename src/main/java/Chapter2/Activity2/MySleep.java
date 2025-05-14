package Chapter2.Activity2;

public class MySleep extends Thread {
    public static void main(String[] args) {
        new Thread(new MySleep()).start();
    }

    @Override
    public void run() {
        try{
            for(int i=0; i<1000; i++){
                System.out.println(i);
                sleep(2000);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
