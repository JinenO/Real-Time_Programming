package Chapter8.Tutorial12;

public class ProducerConsumerDemo {

    static class SharedData{
        private boolean dataReady = false;
        private String data;

        //Producer thread
        public synchronized void produce(){
            try{
                System.out.println("Producer: Preparing data...");
                Thread.sleep(1000);
                data= "Hello from producer";
                dataReady = true;
                System.out.println("Producer: Data is ready.");
                notify();//or notify()
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        //consumer thread
        public synchronized void consume(int id){
            try{
                while(!dataReady){
                    System.out.println("Consumer"+ id+": Waiting for data...");
                    wait();
                }
                System.out.println("Consumer" + id+": Received ->"+data);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        public static void main(String [] args){
            SharedData sharedData = new SharedData();

            for(int i=0; i<3; i++){
                final int id=i;
                new Thread(()-> sharedData.consume(id)).start();
            }

            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }

            new Thread(()-> sharedData.produce()).start();
        }
    }
}
//if just have one consumer thread, notify and notifyAll
//will not have visible change
//but if have more than 1 consumer thread,
//notify will just notify 1 thread, that means other threads
//will still waiting for notify to continue progress
//while notifyAll notify all threads that will make
//all thread continue progress