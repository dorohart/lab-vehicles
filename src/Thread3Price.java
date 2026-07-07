import java.util.concurrent.locks.ReentrantLock;

public class Thread3Price implements Runnable {
    private ReentrantLock rl;
    private ITransport tr;
    public Thread3Price(ReentrantLock rl, ITransport tr) {
        this.rl = rl;
        this.tr = tr;
    }
    @Override
    public void run() {
        rl.lock();
        try {
            double[] p = tr.getPricesModels();
            for (int i = 0; i < p.length; i++) {
                System.out.println("Print price" + (i + 1) + ": " + p[i]);
            }
        }
        finally {
            rl.unlock();
        }
    }
}
