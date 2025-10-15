import java.util.concurrent.CountDownLatch;

public class Race {
    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new RacerRunnable(String.valueOf(i)));
            thread.setPriority(i);
            thread.start();
        }

        System.out.println("==================================");
        System.out.println("Iniciando segunda parte com CountDownLatch");
        System.out.println("==================================");

        for (int i = 1; i <= 10; i++) {
            Thread racerThread = new Thread(new Racer(i));
            racerThread.start();
        }
    }
}

class RacerRunnable implements Runnable {
    private String nome;

    public RacerRunnable(String nome) {
        this.nome = nome;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("RacerRunnable " + nome + " imprimindo " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        System.out.println("RacerRunnable " + nome + " finalizado.");
    }
}

class Racer implements Runnable {
    private int id;
    private static CountDownLatch latch = new CountDownLatch(5);

    public Racer(int id) {
        this.id = id;
    }

    public void run() {
        try {
            if (id % 2 == 0) {
                System.out.println("Racer " + id + " aguardando as ímpares...");
                latch.await();
            }

            for (int i = 0; i < 1000; i++) {
                System.out.println("Racer " + id + " - " + i);
            }

            if (id % 2 != 0) {
                latch.countDown();
                System.out.println("Racer ímpar " + id + " terminou. Restam " + latch.getCount());
            }

            System.out.println("Racer " + id + " finalizado.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
