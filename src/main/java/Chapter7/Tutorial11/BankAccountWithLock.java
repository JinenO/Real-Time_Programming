package Chapter7.Tutorial11;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccountWithLock {
    private double balance;//variable storing account balance
    //create ReentrantReadWriteLock that provide seperate read and write lock to manage access
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    //exacts readlock from reeentrant lock
    private final Lock readLock= lock.readLock();
    //exacts writelock from reentrant lock
    private final Lock writeLock = lock.writeLock();

    //constructor
    public BankAccountWithLock(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        //acquire readlock, allow multi threads to read
        //block if write is in progress
        readLock.lock();
        //try-finally ensure readlock always released
        try{
            //print which thread is reading
            System.out.println(Thread.currentThread().getName()+" reads balance: "+balance);
            //return current balance
            return balance;
        }finally {
            //release readlock
            readLock.unlock();
        }
    }

    public void deposit (double amount) {
        //acquires exclusive write lock
        //block other read and write lock
        writeLock.lock();
        //try-finally always ensure writelock realeased
        try{
            //print which thread as reading
            System.out.println(Thread.currentThread().getName()+" deposits amount: "+amount);
            //modify balance by adding the deposit amount
            balance += amount;
        }finally{
            //release writelock
            writeLock.unlock();
        }
    }

    public void withdraw (double amount) {
        //acquires exclusive write lock
        //block other read and write lock
        writeLock.lock();
        //try-finally always ensure writelock realeased
        try{
            //only withdraw if sufficient
            if(balance>=amount){
                //print which thread os reading
                System.out.println(Thread.currentThread().getName()+" withdraws : "+amount);
                //modify balance by subtracting the withdrawal amount
                balance -= amount;
            }else{
                //print which thread os reading
                System.out.println(Thread.currentThread().getName()+" insufficient funds for: "+amount);
            }
        }finally{
            //release lock
            writeLock.unlock();
        }
    }
}
