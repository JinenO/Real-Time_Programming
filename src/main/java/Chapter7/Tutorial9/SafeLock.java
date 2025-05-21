package Chapter7.Tutorial9;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class SafeLock {
    static class Friend{
        private final String name;
        private final Lock lock= new ReentrantLock();
        //ReentrantLock lock to control bowing access

        public Friend(String name) {
            this.name= name;
        }

        //return friend's name
        public String getName() {
            return this.name;
        }

        //Tries to get both lock (this friend's and the bower's)
        //if it can't get both, it releases any acquires lock to prevent deadlock
        public boolean impendingBow (Friend bower){
            Boolean myLock= false;
            Boolean yourLock=false;
            try{
                myLock= lock.tryLock();//try to get own lock
                yourLock= bower.lock.tryLock();//try to get friend's lock
            }finally{
                if(!(myLock && yourLock)){
                    if(myLock){
                        lock.unlock();
                    }
                    if(yourLock){
                        bower.lock.unlock();
                    }
                }
            }
            return myLock && yourLock;
        }

        //if both locks are acquired, the friend bows
        //and the other bow back
        //Otherwise, prints a message about conflict
        public void bow(Friend bower){
            if(impendingBow(bower)){//to safely try acquiring both locks
                try{
                    System.out.format("%s: %s has"+" bowed to me!%n",
                            this.name, bower.getName());
                    bower.bowBack(this);
                }finally{
                    lock.unlock();
                    bower.lock.unlock();
                }
            }else{
                System.out.format("%s: %s started"+
                        " to bow to me, but saw that"+
                        " I was already bowing to"+
                        " him.%n", this.name, bower.getName());
            }
        }

        public void bowBack(Friend bower){
            System.out.format("%s: %s has"+
                    " bowed to me!%n", this.name, bower.getName());
        }
    }

    static class BowLoop implements Runnable{
        private Friend bower;
        private Friend bowee;

        public BowLoop(Friend bower, Friend bowee){
            this.bower= bower;
            this.bowee= bowee;
        }

        public void run(){
            Random random= new Random();
            for(;;){//infinite loop
                try{
                    Thread.sleep(random.nextInt(10));
                }catch(InterruptedException e){
                }
                bower.bow(bowee);//try to bow to the other
            }
        }

        public static void main(String [] args){
            final Friend alphonse= new Friend("Alphonse");
            final Friend gaston= new Friend("Gaston");
            new Thread(new BowLoop(alphonse, gaston)).start();
            //Alphonse bows to Gaston
            new Thread(new BowLoop(gaston, alphonse)).start();
            //Gaston bows to Alphonse
        }
    }
}
//there will be non-stop output
//Alphonse: Gaston has bowed to me!
//Gaston: Alphonse has bowed to me!
//Gaston: Alphonse started to bow to me, but saw that I was already bowing to him.
//Alphonse: Gaston started to bow to me, but saw that I was already bowing to him.
//because there is no termination condition
//no deadlock because of tryLock() and correct release logic