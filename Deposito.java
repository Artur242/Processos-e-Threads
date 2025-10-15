public class q01 {
    private int itens = 0;
    private final int capacidade = 100;

    public synchronized boolean colocar() {
        if (itens < capacidade) {
            itens++;
            System.out.println("Produziu uma caixa. Total: " + itens);
            return true;
        } else {
            System.out.println("Depósito cheio! Aguardando consumo...");
            return false;
        }
    }

    public synchronized boolean retirar() {
        if (itens > 0) {
            itens--;
            System.out.println("Consumiu uma caixa. Total: " + itens);
            return true;
        } else {
            System.out.println("Depósito vazio! Não foi possível retirar.");
            return false;
        }
    }

    public int getNumItens() {
        return itens;
    }

    public static void main(String[] args) {
        q01 dep = new q01();
        Produtor p = new Produtor(dep, 50);
        Consumidor c1 = new Consumidor(dep, 150);
        Consumidor c2 = new Consumidor(dep, 100);
        Consumidor c3 = new Consumidor(dep, 150);
        Consumidor c4 = new Consumidor(dep, 100);
        Consumidor c5 = new Consumidor(dep, 150);

        p.start();
        c1.start(); c2.start(); c3.start(); c4.start(); c5.start();

        System.out.println("Execução do main da classe Depósito terminada.");
    }
}

class Produtor extends Thread {
    private q01 deposito;
    private int tempo;

    public Produtor(q01 dep, int tempo) {
        this.deposito = dep;
        this.tempo = tempo;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            deposito.colocar();
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Produção encerrada (100 caixas).");
    }
}

class Consumidor extends Thread {
    private q01 deposito;
    private int tempo;

    public Consumidor(q01 dep, int tempo) {
        this.deposito = dep;
        this.tempo = tempo;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            deposito.retirar();
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumidor terminou (20 caixas).");
    }
}