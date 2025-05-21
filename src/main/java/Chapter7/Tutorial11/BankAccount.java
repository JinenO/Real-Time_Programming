package Chapter7.Tutorial11;

public class BankAccount {
    public static void main(String[] args) {
        BankAccountWithLock account = new BankAccountWithLock(1000.0); // Initial balance

        Thread depositor = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(200.0);
                try {
                    Thread.sleep(100); // Simulate time delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Depositor");

        Thread withdrawer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(150.0);
                try {
                    Thread.sleep(120); // Simulate time delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Withdrawer");

        // Create a thread for checking balance
        Thread balanceChecker = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.getBalance();
                try {
                    Thread.sleep(80); // Simulate time delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "BalanceChecker");

        balanceChecker.start();
        depositor.start();
        withdrawer.start();

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
