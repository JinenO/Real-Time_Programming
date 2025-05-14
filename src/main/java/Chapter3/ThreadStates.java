package Chapter3;

public class ThreadStates {
    public static void main(String[] args) {
        Thread t= new Thread();
        Thread.State state = t.getState();
        Thread.State [] ts=state.values();
        for(int i=0;i<ts.length;i++){
            System.out.println(ts[i]);
        }
    }
}
//There will be 6 output
//NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
