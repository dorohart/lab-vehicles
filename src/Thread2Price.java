public class Thread2Price implements Runnable {
    private TransportSynchronizer ts;
    public Thread2Price(TransportSynchronizer ts) {
        this.ts = ts;
    }
    @Override
    public void run() {
        try {
            while (ts.canPrintPrice())
                ts.printPrice();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
