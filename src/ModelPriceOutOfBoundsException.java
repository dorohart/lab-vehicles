class ModelPriceOutOfBoundsException extends RuntimeException {
    private double price;
    public double getPrice() {
        return price;
    }
    public ModelPriceOutOfBoundsException(String sms, double price) {
        super(sms);
        this.price = price;
    }
}