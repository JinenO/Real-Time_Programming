package Chapter8.Tutorial12;

public class SynchronizedFlagExample {
    // Shared flag between threads (not volatile anymore)
    private static boolean running = true;

    // Synchronize access to 'running'
    private static synchronized boolean isRunning() {
        return running;
    }

    private static synchronized void stopRunning() {
        running = false;
    }

    public static void main(String[] args) {
        // Thread that runs continuously until 'running' becomes false
        Thread worker = new Thread(() -> {
            System.out.println("Worker thread started...");
            while (isRunning()) {
               //simulate work
            }
            System.out.println("Worker thread stopped");
        });

        worker.start();

        // Main thread sleeps for a bit then signals stop
        try {
            Thread.sleep(2000); // Let worker run for 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Safely stop the worker thread
        stopRunning();
        System.out.println("Synchronized: Main called stopRunning()");

        try{
            worker.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
