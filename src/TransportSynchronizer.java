public class TransportSynchronizer {
    private ITransport v;
    private volatile int current = 0;
    private Object lock = new Object();
    private boolean set = false;
    public TransportSynchronizer(ITransport v) {
        this.v = v;
    }
    public void printPrice() throws InterruptedException {
        double val;
        synchronized(lock) {
            double [] p = v.getPricesModels();
            if (!canPrintPrice()) throw new InterruptedException();
            while (set)
                lock.wait();
            val = p[current];
            System.out.println("Print price" + (current+1) + ": " + val);
            set = true;
            lock.notifyAll();
        }
    }
    public void printModel() throws InterruptedException {
        synchronized(lock) {
            String [] s = v.getStrModels();
            if (!canPrintModel()) throw new InterruptedException();
            while (!set)
                lock.wait();
            System.out.println("Print model" + (current+1) + ": " + s[current++]);
            set = false;
            lock.notifyAll();
        }
    }
    public boolean canPrintModel() {
        return current < v.getCount();
    }
    public boolean canPrintPrice() {
        return (!set && current < v.getCount()) || (set && current < v.getCount() - 1);
    }
}

