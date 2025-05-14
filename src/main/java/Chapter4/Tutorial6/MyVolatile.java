package Chapter4.Tutorial6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyVolatile {
    public static void main(String[] args) {
        MyThread t=new MyThread();
        t.start();

        System.out.println("Press Enter to exit.");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        try{
            br.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }
        t.shutdown();
        try{
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("My thread exited.");
    }
}
