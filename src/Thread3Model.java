import java.util.concurrent.locks.ReentrantLock;

public class Thread3Model implements Runnable {
    private ReentrantLock rl;
    private ITransport tr;
    public Thread3Model(ReentrantLock rl, ITransport tr) {
        this.rl = rl;
        this.tr = tr;
    }
    @Override
    public void run() {
        rl.lock();
        try {
            String[] m = tr.getStrModels();
            for (int i = 0; i < m.length; i++) {
                System.out.println("Print model" + (i + 1) + ": " + m[i]);
            }
        }
        finally {
            rl.unlock();
        }
    }
}
