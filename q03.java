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
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumidor finalizou após consumir " + consumidas + " caixas.");
    }
}
