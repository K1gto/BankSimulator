public class BankSimulator {
    public static void main(String[] args) {
        Bank bank = new Bank(3);

        Thread[] customers = new Thread[10];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Thread(new Customer("Клієнт " + (i + 1), bank));
            customers[i].start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bank.closeBank();

        for (Thread customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Симуляція завершена.");
    }
}
