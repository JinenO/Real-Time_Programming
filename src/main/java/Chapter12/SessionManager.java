package Chapter12;

//ConcurrentHashMap is a thread-safe map for storing sessions
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SessionManager {
    //a static map where keys are sessionID (like "user1") and values are lastActionTime in milliseconds.
    private static final ConcurrentHashMap<String, Long> sessions= new ConcurrentHashMap<>();
    //Session timeout is 10 seconds.
    private static final long SESSION_TIMEOUT = 10000; //10 seconds for demo

    //stimulate user activity
    public static void updateSession(String sessionId){
        //update the session's timestamp to current time (now)
        sessions.put(sessionId, System.currentTimeMillis());
        System.out.println("Updated session:"+sessionId);
    }

    //Background task to clean up old sessions
    //run a background thread that checks every 5 seconds
    public static void startSessionCleanupTask(){
        //create a ScheduledExecutorService with only one thread (newSingleThreadScheduledExecutor())
        //.scheduleAtFixedRate() schedule a task to run repeatedly at fixed interval
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            //set now into current time
            long now=System.currentTimeMillis();
            //sessions.keySet() return a Set<String> containing all the keys in the map
            //String sessionID:... loops through each key
            for(String sessionId:sessions.keySet()){
                long lastActive=sessions.get(sessionId);
                //if any session has been inactive for more than 10s, it's removed
                if(now-lastActive>SESSION_TIMEOUT){
                    //remove session
                    sessions.remove(sessionId);
                    System.out.println("Removed expired session:"+sessionId);
                }
            }
        },0,5, TimeUnit.SECONDS);
    }

    //Monitor active sessions
    //Display all currently active sessions and their last active timestamps
    public static void printSessions(){
        System.out.println("Active Sessions:");
        //lambda expression acts like a short version of a for-loop
        //means for each entry (id, time) in thr map, do something
        sessions.forEach((id,time)->
                System.out.println("-"+id+" (Last Active: "+time+")"));
    }

    public static void main(String [] args) throws InterruptedException {
        //Start cleanup thread that remove expired sessions every 5 seconds
        startSessionCleanupTask();

        //stimulate user actions in different threads
        //Stimulate user1 interacting every 2 seconds (5times=10seconds total)
        Thread user1= new Thread(() ->{
                for(int i=0; i<5; i++) {
                    //stimulate user1 activity
                    updateSession("user1");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
                }
        });

        //stimulate user2 interacting every 4 seconds (3times=12 seconds total).
        Thread user2= new Thread(() ->{
            for(int i=0; i<3; i++) {
                //stimulate user2 activity
                updateSession("user2");
                try{
                    Thread.sleep(4000);
                }catch(InterruptedException ignored){}
            }
        });

        //Starts both user threads and waits for them to finish.
        user1.start();
        user2.start();

        user1.join();
        user2.join();

        //Final state
        //waits another 12 seconds to give time for session cleanup to happen
        Thread.sleep(12000);
        //prints active sessions at the end
        printSessions();

    }
}
