public class Thread1Model extends Thread {
    private ITransport transport;
    public Thread1Model(ITransport transport) {
        this.transport = transport;
    }
    @Override
    public void run() {
        String[] models = transport.getStrModels();
        for (int i = 0; i < models.length; i++) {
            System.out.println("model" + (i + 1) + ": " + models[i]);
        }
    }
}
