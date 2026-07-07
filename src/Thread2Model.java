public class Thread2Model implements Runnable {
    private TransportSynchronizer ts;
    public Thread2Model(TransportSynchronizer ts) {
        this.ts = ts;
    }
    @Override
    public void run() {
        try {
            while(ts.canPrintModel())
                ts.printModel();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
