import java.io.Serializable;

interface ITransport extends Serializable, Cloneable {
    String getBrand();
    void setBrand(String value);
    void setModel(String model, String newModel) throws DuplicateModelNameException, NoSuchModelNameException;
    String[] getStrModels();
    double getPrice(String model) throws NoSuchModelNameException;
    void setPrice(String model, double newPrice) throws NoSuchModelNameException;
    double[] getPricesModels();
    void addModel(String model, double price) throws DuplicateModelNameException;
    void deleteModel(String model) throws NoSuchModelNameException;
    int getCount();
    String toString();
    boolean equals(Object obj);
    int hashCode();
    Object clone() throws CloneNotSupportedException;
}