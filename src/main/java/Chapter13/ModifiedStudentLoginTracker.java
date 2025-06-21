package Chapter13;

import java.util.concurrent.ConcurrentHashMap;

public class ModifiedStudentLoginTracker {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> loginCounts= new ConcurrentHashMap<>();

        //Initialize both students with 0 login count
        loginCounts.put("student123", 0);
        loginCounts.put("student456", 0);

        //Runnable for incrementing student123
        Runnable incrementLoginStudent123=()->{
            for(int i=0; i<500; i++){
                loginCounts.compute("student123", (k,v)->v+1);
            }
        };

        //Runnable for incrementing student456
        Runnable incrementLoginStudent456=()->{
            for(int i=0; i<500; i++){
                loginCounts.compute("student456", (k,v)->v+1);
            }
        };

        //create thread for both students
        Thread t1 = new Thread(incrementLoginStudent123);
        Thread t2 = new Thread(incrementLoginStudent123);
        Thread t3 = new Thread(incrementLoginStudent456);
        Thread t4 = new Thread(incrementLoginStudent456);

        //start all threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try{
            //wait for all threads to complete
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final login count for student123: "+loginCounts.get("student123"));
        System.out.println("Final login count for student456: "+loginCounts.get("student456"));
        System.out.println("Total login count: " + (loginCounts.get("student123") + loginCounts.get("student456")));

    }
}
