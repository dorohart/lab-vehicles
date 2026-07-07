public class ThreadBrand implements Runnable {
    private ITransport tr;
    public ThreadBrand(ITransport tr) {
        this.tr = tr;
    }
    @Override
    public void run() {
        System.out.println(tr.getBrand());
    }
}
