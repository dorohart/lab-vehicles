class DuplicateModelNameException extends Exception {
    private String nameModel;
    public String getNameModel() {
        return nameModel;
    }
    public DuplicateModelNameException(String sms, String nameModel) {
        super(sms);
        this.nameModel = nameModel;
    }
}
