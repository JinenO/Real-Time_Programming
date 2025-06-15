package Chapter7.Tutorial11;

public class BankAccount {
    public static void main(String[] args) {
        //initial balance of the account is 300
        BankAccountWithLock account = new BankAccountWithLock(300.0); // Initial balance

        //create new thread named depositor
        Thread depositor = new Thread(() -> {
            //start loop that will execute 5 times
            for (int i = 0; i < 10; i++) {
                //call the deposit method to add 150 to account balance
                account.deposit(150.0);
                try {
                    Thread.sleep(100); // Simulate time delay
                } catch (InterruptedException e) {
                    //restore interrupted status if an exception occurs
                    Thread.currentThread().interrupt();
                }
            }
        }, "Depositor");

        //create new thread named withdrawer
        Thread withdrawer = new Thread(() -> {
            //start loop that will execute 5 times
            for (int i = 0; i < 10; i++) {
                //call withdraw method that subtract 200 from balance
                account.withdraw(200.0);
                try {
                    Thread.sleep(120); // Simulate time delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Withdrawer");

        // Create a thread for checking balance
        Thread balanceChecker = new Thread(() -> {
            //loop for 10 times
            for (int i = 0; i < 10; i++) {
                //call method to get balance
                account.getBalance();
                try {
                    Thread.sleep(80); // Simulate time delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "BalanceChecker");

        //start 3 thread simultanously
        balanceChecker.start();
        depositor.start();
        withdrawer.start();

        //join method makes tha main thread wait until each
        //thread complete execution
        try {
            balanceChecker.join();
            depositor.join();
            withdrawer.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final balance: " + account.getBalance());
    }
}
