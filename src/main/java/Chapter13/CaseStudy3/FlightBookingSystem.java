package Chapter13.CaseStudy3;

//CyclicBarrier allow fixed number of thread to wait
//at a synchronization point (barrier) until all threads have reached it
import java.util.concurrent.CyclicBarrier;

public class FlightBookingSystem {
    private static final int AGENCY_COUNT=3;
    //create a CyclicBarrier for AGENCY_COUNT(3)threads
    //second parameter is a barrier action: a task that will run once,
    //automatically when all 3 threads have reached the barrier
    private static final CyclicBarrier barrier = new CyclicBarrier(AGENCY_COUNT, ()->{
        System.out.println("All agencies have selected seats. Confirming bookings...");
        try{
            //stimulate "Confirming the bookings" with 1 second pause
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("Bookings confirmed!\n");
    });

    public static void main(String[] args) {
        System.out.println("Starting booking process...\n");
        for(int i=1; i<=AGENCY_COUNT; i++){
            new Thread(new BookingAgency("Agency-" +i)).start();
        }
    }

    static class BookingAgency implements Runnable{
        private final String agencyName;

        public BookingAgency(String agencyName){
            this.agencyName=agencyName;
        }

        @Override
        public void run() {
            try{
                System.out.println(agencyName+ " is Selecting seats...");
                Thread.sleep((int)(Math.random()*1000));
                System.out.println(agencyName+ " finished selecting seats. Waiting for others...");
                //once all 3 threads call barrier.await(), the barrier action
                //run (Confirming booking...), then all threads released
                barrier.await();
                System.out.println(agencyName+ " proceeds after confirmation.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
