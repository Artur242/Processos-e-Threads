public class Deposito {
    private int itens = 0;
    private final int capacidade = 100;

    // Produz (adiciona uma caixa)
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

    // Consome (retira uma caixa)
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
        Deposito dep = new Deposito();

        Produtor p = new Produtor(dep, 50);
        Consumidor c1 = new Consumidor(dep, 150);
        Consumidor c2 = new Consumidor(dep, 100);
        Consumidor c3 = new Consumidor(dep, 150);
        Consumidor c4 = new Consumidor(dep, 100);
        Consumidor c5 = new Consumidor(dep, 150);

        p.start();

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();

        try {
            p.join(); // aguarda o produtor terminar
            c1.join(); c2.join(); c3.join(); c4.join(); c5.join(); // aguarda consumidores terminarem
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Execução finalizada. Total de caixas restantes: " + dep.getNumItens());
    }
}

class Produtor extends Thread {
    private Deposito deposito;
    private int tempo;

    public Produtor(Deposito dep, int tempo) {
        this.deposito = dep;
        this.tempo = tempo;
    }

    public void run() {
        int produzidas = 0;
        while (produzidas < 100) {
            if (deposito.colocar()) {
                produzidas++;
            }
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
    private Deposito deposito;
    private int tempo;

    public Consumidor(Deposito dep, int tempo) {
        this.deposito = dep;
        this.tempo = tempo;
    }

    public void run() {
        int consumidas = 0;
        while (consumidas < 20) {
            if (deposito.retirar()) {
                consumidas++;
            }
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumidor terminou (20 caixas).");
    }
}
