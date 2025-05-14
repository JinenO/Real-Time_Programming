package Chapter2.Activity3;

public class ThreadExample2 {
    public static class MyThread extends Thread {
        public void run() {
            System.out.println("MyThread Running");
            System.out.println("MyThread Running");
        }
    }

    public static void main(String[] args) {
        MyThread mythread= new MyThread();
        mythread.start();
    }
}
