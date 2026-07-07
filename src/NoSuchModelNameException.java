class NoSuchModelNameException extends Exception {
    private String nameModel;
    public String getNameModel() {
        return nameModel;
    }
    public NoSuchModelNameException(String sms, String nameModel) {
        super(sms);
        this.nameModel = nameModel;
    }
}
