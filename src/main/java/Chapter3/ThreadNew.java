package Chapter3;

public class ThreadNew {
    public static void main(String[] args) {
        Thread t= new Thread();
        System.out.println(t.getState());
    }
    //output will be NEW
}
