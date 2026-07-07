public class Thread1Price extends Thread {
    private ITransport transport;
    public Thread1Price(ITransport transport) {
        this.transport = transport;
    }
    @Override
    public void run() {
        double[] prices = transport.getPricesModels();
        for (int i = 0; i < prices.length; i++) {
            System.out.println("price of the model" + (i + 1) + ": " + prices[i] + " rub");
        }
    }
}
