package Chapter3;

public class ThreadStatesInJava {
    public static void main(String[] args) {
        Thread.State[] states = Thread.State.values();
        for (Thread.State state : states) {
            System.out.println(state);
        }
    }
}
//have the same output as ThreadStates but more simple and efficient way
