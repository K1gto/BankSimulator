import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Customer implements Runnable {
    private String name;
    private Bank bank;

    public Customer(String name, Bank bank) {
        this.name = name;
        this.bank = bank;
    }

    @Override
    public void run() {
        try {
            if (bank.isBankOpen()) {
                System.out.println(name + " приходить до банкомату...");

                if (bank.tryUseATM()) {
                    System.out.println(name + " користується банкоматом.");

                    Thread.sleep((int) (Math.random() * 5000));

                    System.out.println(name + " завершив операцію.");

                    bank.leaveATM();
                } else {
                    System.out.println(name + " не зміг знайти вільний банкомат.");
                }
            } else {
                System.out.println(name + " прийшов до закритого банку.");
            }
        } catch (InterruptedException e) {
            System.out.println(name + " був перерваний.");
        }
    }
}

class Bank {
    private Semaphore atms;
    private boolean open;

    public Bank(int numATMs) {
        this.atms = new Semaphore(numATMs);
        this.open = true;
    }

    public boolean tryUseATM() throws InterruptedException {
        return atms.tryAcquire(1, 2, TimeUnit.SECONDS);
    }

    public void leaveATM() {
        atms.release();
    }

    public boolean isBankOpen() {
        return open;
    }

    public void closeBank() {
        open = false;
        System.out.println("Банк закритий. Банкомати недоступні.");
    }
}
