package Chapter4.Tutorial6;

public class PrintTutorial6 {
    public static void print(int thread){
        for (int i = 1; i < 4; i++) {
            System.out.println("Thread-"+thread+": "
                    +(thread+1)+"*"+i+"="+((thread+1)*i));
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            final int count = i;
            Thread t= new Thread(new Runnable() {
                @Override
                public void run() {
                    print(count);
                }
            });
            t.start();

            try {
                Thread.sleep(200);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
