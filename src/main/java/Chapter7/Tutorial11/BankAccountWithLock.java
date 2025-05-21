package Chapter7.Tutorial11;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccountWithLock {
    private double balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock= lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public BankAccountWithLock(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        readLock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+" reads balance: "+balance);
            return balance;
        }finally {
            readLock.unlock();
        }
    }

    public void deposit (double amount) {
        writeLock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+" deposits amount: "+amount);
            balance += amount;
        }finally{
            writeLock.unlock();
        }
    }

    public void withdraw (double amount) {
        writeLock.lock();

        try{
            if(balance>=amount){
                System.out.println(Thread.currentThread().getName()+" withdraws : "+amount);
                balance -= amount;
            }else{
                System.out.println(Thread.currentThread().getName()+" insufficient funds for: "+amount);
            }
        }finally{
            writeLock.unlock();
        }
    }
}
