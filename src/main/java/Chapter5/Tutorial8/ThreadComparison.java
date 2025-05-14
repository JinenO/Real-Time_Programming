package Chapter5.Tutorial8;

public class ThreadComparison {
    public static void main(String[] args) throws InterruptedException{
        int normalCount=0;
        int syncCount=0;
        NormalThread[] normal= new NormalThread[10];
        long startNormal=System.currentTimeMillis();

        for(int i=0;i<10;i++){
            normal[i]= new NormalThread();
            normal[i].start();
        }

        for(int i=0;i<10;i++){
            try{
                normal[i].join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        for(int i=0;i<10;i++){
            normalCount=normalCount+normal[i].getCounter();
        }

        long endNormal=System.currentTimeMillis();
        double durationNormal=(endNormal-startNormal)/1000.0;

        System.out.printf("Normal thread = %.9f seconds\n",durationNormal);
        System.out.println("Normal thread count="+normalCount);

        SynchronizedThread[] syncThread= new SynchronizedThread[10];
        long startSync=System.currentTimeMillis();

        for(int i=0;i<10;i++){
            syncThread[i]=new SynchronizedThread();
            syncThread[i].start();
        }

        for(int i=0;i<10;i++){
            try{
                syncThread[i].join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        for(int i=0;i<10;i++){
            syncCount=syncCount+syncThread[i].getCounter();
        }

        long endSync=System.currentTimeMillis();
        double durationSync=(endSync-startSync)/1000.0;

        System.out.printf("Synchronized thread =  %.9f seconds\n",durationSync);
        System.out.println("Synchronized thread count="+syncCount);
    }
}
