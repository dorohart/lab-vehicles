import java.io.Serializable;
import java.util.*;

public class Moped implements ITransport {
    private String brand;
    private LinkedList<Model> models;
    private class Model implements Serializable {
        private String model;
        private double price;
        public Model(String model, double price) {
            this.model = model;
            this.price = price;
        }
    }
    public Moped(String brand, int length) {
        this.brand = brand;
        models = new LinkedList<>();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            try {
                addModel("model" + (i + 1), rnd.nextInt(10_000, 1_000_001));
            }
            catch (DuplicateModelNameException ex) {
            }
        }
    }
    public Moped() { this.models = new LinkedList<>(); }
    @Override
    public String getBrand() { return brand; }
    @Override
    public void setBrand(String newBrand) { this.brand = newBrand; }
    @Override
    public void setModel(String model, String newModel) throws DuplicateModelNameException, NoSuchModelNameException {
        if (models.size() != 0) {
            Model mod = null;
            for (Model m : models) {
                if (newModel.equals(m.model)) throw new DuplicateModelNameException("This model already exist. ", newModel);
                if (model.equals(m.model)) mod = m;
            }
            if (mod != null) mod.model = newModel;
            else throw new NoSuchModelNameException("This model not found. ", model);
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", newModel);
    }
    @Override
    public String[] getStrModels() {
        String[] models = new String[this.models.size()];
        int i = 0;
        for (Model m : this.models) {
            models[i] = m.model;
            i++;
        }
        return models;
    }
    @Override
    public double getPrice(String mod) throws NoSuchModelNameException {
        if (models.size() != 0) {
            for (Model m : models) {
                if (mod.equals(m.model)) return m.price;
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
                for (Model m : models) {
                    if (mod.equals(m.model)) {
                        m.price = newPrice;
                        return;
                    }
                }
                throw new NoSuchModelNameException("This model not found. ", mod);
            }
            else throw new NoSuchModelNameException("Models not found. Please add models.", mod);
        }
    }
    @Override
    public double[] getPricesModels() {
        double[] prices = new double[models.size()];
        int i = 0;
        for (Model m : models) {
            prices[i] = m.price;
            i++;
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
            models.addLast(new Model(mod, price));
        }
    }
    @Override
    public void deleteModel(String mod) throws NoSuchModelNameException {
        Iterator<Model> iter = models.iterator();
        Model m;
        while (iter.hasNext()) {
            m = iter.next();
            if (mod.equals(m.model)) {
                iter.remove();
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
        if (obj instanceof Moped && brand.equals(((Moped)obj).brand) && getCount() == ((Moped) obj).getCount()) {
            String[] names = ((Moped) obj).getStrModels();
            double[] prices = ((Moped) obj).getPricesModels();
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
    public Moped clone() throws CloneNotSupportedException {
        Moped m = (Moped)super.clone();
        m.models = new LinkedList<>();
        for (int i = 0; i < getCount(); i++) {
            try {
                m.addModel(models.get(i).model, models.get(i).price);
            }
            catch (DuplicateModelNameException ex) {}
        }
        return m;
    }
}
