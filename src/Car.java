import java.io.Serializable;
import java.util.*;
import static java.lang.System.*;

public class Car implements ITransport {
    private class Model implements Serializable {
        private String model;
        private double price;
        public Model(String model, double price) {
            this.model = model;
            this.price = price;
        }
    }
    private Model[] models;
    private String brand;
    public Car(String brand, int length) {
        this.brand = brand;
        models = new Model[length];
        Random rnd = new Random();
        for (int i = 0; i < models.length; i++) {
            models[i] = new Model("model" + (i + 1), rnd.nextInt(1_000_000, 10_000_001));
        }
    }
    public Car() {
        models = new Model[0];
    }
    @Override
    public String getBrand() {
        return brand;
    }
    @Override
    public void setBrand(String value) {
        brand = value;
    }
    @Override
    public void setModel(String mod, String newMod) throws DuplicateModelNameException, NoSuchModelNameException {
        if (models.length != 0) {//1
            boolean flag = false;
            int key = -1;
            for (int i = 0; i < models.length; i++) {
                if (newMod.equals(models[i].model)) throw new DuplicateModelNameException("This model already exist: " + newMod, newMod);
                if (mod.equals(models[i].model)) {
                    key = i;
                }
            }
            if (key == -1) throw new NoSuchModelNameException("This model not found: " + mod, mod);
            else models[key].model = newMod;
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", newMod);
    }
    @Override
    public String[] getStrModels() {
        String[] mas = new String[models.length];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = models[i].model;
        }
        return mas;
    }
    @Override
    public double getPrice(String model) throws NoSuchModelNameException {
        boolean flag = false;
        double res = 0;
        if (models.length != 0) {
            for (int i = 0; i < models.length && !flag; i++) {
                if (model.equals(models[i].model)) {
                    flag = true;
                    res = models[i].price;
                }
            }
            if (!flag) throw new NoSuchModelNameException("This model not found: " + model, model);
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", model);
        return res;
    }
    @Override
    public void setPrice(String model, double newPrice) throws NoSuchModelNameException {
        if (newPrice <= 0) throw new ModelPriceOutOfBoundsException("This fake price: " + newPrice, newPrice);
        else {
            if (models.length != 0) {
                boolean flag = false;
                for (int i = 0; i < models.length && !flag; i++) {
                    if (model.equals(models[i].model)) {
                        flag = true;
                        models[i].price = newPrice;
                    }
                }
                if (!flag) throw new NoSuchModelNameException("This model not found: " + model, model);
            }
            else throw new NoSuchModelNameException("Models not found. Please add models.", model);
        }
    }
    @Override
    public double[] getPricesModels() {
        double[] mas = new double[models.length];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = models[i].price;
        }
        return mas;
    }
    @Override
    public void addModel(String model, double price) throws DuplicateModelNameException {
        if (price <= 0) throw new ModelPriceOutOfBoundsException("This fake price: " + price, price);
        else {
            int key = 0;
            for (int i = 0; i < models.length && models[i] != null; i++) {
                if (model.equals(models[i].model)) throw new DuplicateModelNameException("This model already exist: " + model, model);
                key++;
            }
            if (key == models.length) {//2
                models = Arrays.copyOf(models, models.length + 1);
                models[models.length - 1] = new Model(model, price);
            }
            else models[key] = new Model(model, price);
        }
    }
    @Override
    public void deleteModel(String model) throws NoSuchModelNameException {
        if (models.length != 0) {
            int i = 0;
            boolean flag = false;
            while (i < models.length && !flag) {
                if (model.equals(models[i].model)) flag = true;
                else i++;
            }
            if (i == models.length) throw new NoSuchModelNameException("This model not found: " + model, model);
            else {//2
                System.arraycopy(models, i + 1, models, i, models.length - 1 - i);
                models = Arrays.copyOf(models, models.length - 1);
            }
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", model);
    }
    @Override
    public int getCount() {
        return models.length;
    }

    //4
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(brand + " ");
        sb.append(getCount());
        for (Model i : models) {
            sb.append(" " + i.model + " ");
            sb.append(i.price);
        }
        String str = new String(sb);
        return str;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Car && brand.equals(((Car) obj).brand) && getCount() == ((Car) obj).getCount()) {
            for (int i = 0; i < getCount(); i++) {
                if (!models[i].model.equals(((Car) obj).models[i].model) || models[i].price != ((Car) obj).models[i].price) return false;
            }
            return true;
        }
        else return false;
    }
    @Override
    public int hashCode() {
        if (brand == null) return 0;
        else {
            int resMas = 1;
            for (int i = 0; i < getCount(); i++) {
                resMas = 7 * resMas + models[i].model.hashCode();
                resMas = 7 * resMas + Double.hashCode(models[i].price);
            }
            return 7 * brand.hashCode() + resMas;
        }
    }
    @Override
    public Car clone() throws CloneNotSupportedException {
        Car obj;
        obj = (Car)super.clone();
        obj.models = new Model[this.getCount()];
        for (int i = 0; i < this.getCount(); i++) {
            obj.models[i] = new Model(this.models[i].model, this.models[i].price);
        }
        return obj;
    }
}