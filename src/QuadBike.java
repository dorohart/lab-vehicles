import java.io.Serializable;
import java.util.*;

public class QuadBike implements ITransport {
    private String brand;
    private ArrayList<Model> models;
    private class Model implements Serializable {
        private String model;
        private double price;
        public Model(String model, double price) {
            this.model = model;
            this.price = price;
        }
    }
    public QuadBike(String brand, int length) {
        this.brand = brand;
        models = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            try {
                addModel("model" + (i + 1), rnd.nextInt(100_000, 3_000_001));
            }
            catch (DuplicateModelNameException ex) {}
        }
    }
    public QuadBike() { this.models = new ArrayList<>(0); }
    @Override
    public String getBrand() { return brand; }
    @Override
    public void setBrand(String newBrand) { this.brand = newBrand; }
    @Override
    public void setModel(String model, String newModel) throws DuplicateModelNameException, NoSuchModelNameException {
        if (models.size() != 0) {
            int index = -1;
            for (int i = 0; i < models.size(); i++) {
                if (newModel.equals(models.get(i).model)) throw new DuplicateModelNameException("This model already exist. ", newModel);
                if (model.equals(models.get(i).model)) index = i;
            }
            if (index != -1) models.get(index).model = newModel;
            else throw new NoSuchModelNameException("This model not found. ", model);
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", newModel);
    }
    @Override
    public String[] getStrModels() {
        String[] models = new String[this.models.size()];
        for (int i = 0; i < this.models.size(); i++) {
            models[i] = this.models.get(i).model;
        }
        return models;
    }
    @Override
    public double getPrice(String mod) throws NoSuchModelNameException {
        if (models.size() != 0) {
            for (int i = 0; i < models.size(); i++){
                if (mod.equals(models.get(i).model)) return models.get(i).price;
            }
            throw new NoSuchModelNameException("This model not found. ", mod);
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", mod);
    }
    @Override
    public void setPrice(String mod, double newPrice) throws NoSuchModelNameException {
        if (newPrice <= 0) throw new ModelPriceOutOfBoundsException("Fake price. ", newPrice);
        else {
            if (models.size() != 0) {
                boolean flag = false;
                for (int i = 0; i < models.size() && !flag; i++) {
                    if (mod.equals(models.get(i).model)) {
                        models.get(i).price = newPrice;
                        flag = true;
                    }
                }
                if (!flag) throw new NoSuchModelNameException("This model not found. ", mod);
            }
            else throw new NoSuchModelNameException("Models not found. Please add models.", mod);
        }
    }
    @Override
    public double[] getPricesModels() {
        double[] prices = new double[models.size()];
        for (int i = 0; i < models.size(); i++) {
            prices[i] = models.get(i).price;
        }
        return prices;
    }
    @Override
    public void addModel(String mod, double price) throws DuplicateModelNameException {
        if (price <= 0) throw new ModelPriceOutOfBoundsException("Fake price", price);
        else {
            for (int i = 0; i < models.size(); i++) {
                if (mod.equals(models.get(i).model)) throw new DuplicateModelNameException("This model already exist. ", mod);
            }
            models.add(new Model(mod, price));
        }
    }
    @Override
    public void deleteModel(String mod) throws NoSuchModelNameException {
        for (int i = 0; i < models.size(); i++) {
            if (mod.equals(models.get(i).model)) {
                models.remove(i);
                return;
            }
        }
        throw new NoSuchModelNameException("This model not found. ", mod);
    }
    @Override
    public int getCount() {
        return models.size();
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(brand + " ");
        sb.append(getCount());
        for (Model m : models) {
            sb.append(" " + m.model + " ");
            sb.append(m.price);
        }
        return new String(sb);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QuadBike && brand.equals(((QuadBike)obj).brand) && getCount() == ((QuadBike) obj).getCount()) {
            String[] names = ((QuadBike) obj).getStrModels();
            double[] prices = ((QuadBike) obj).getPricesModels();
            for(int i = 0; i < getCount(); i++) {
                if (!models.get(i).model.equals(names[i]) || models.get(i).price != prices[i]) return false;
            }
            return true;
        }
        else return false;
    }
    @Override
    public int hashCode() {
        if (brand == null) return 0;
        else {
            int resMas = 0;
            for (Model m : models) {
                resMas = resMas + (7 * m.model.hashCode() + Double.hashCode(m.price));
            }
            return 7 * brand.hashCode() + resMas;
        }
    }
    @Override
    public QuadBike clone() throws CloneNotSupportedException {
        QuadBike qb = (QuadBike)super.clone();
        qb.models = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            try {
                qb.addModel(models.get(i).model, models.get(i).price);
            }
            catch (DuplicateModelNameException ex) {}
        }
        return qb;
    }
}
