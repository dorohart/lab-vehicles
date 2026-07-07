import java.io.Serializable;
import java.util.*;

public class Scooter implements ITransport {
    private String brand;
    private HashMap<String, Double> models;
    public Scooter(String brand, int length) {
        this.brand = brand;
        models = new HashMap<>();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            try {
                addModel("model" + (i + 1), rnd.nextInt(10_000, 1_000_001));
            }
            catch (DuplicateModelNameException ex) {}
        }
    }
    public Scooter() { this.models = new HashMap<>(); }
    @Override
    public String getBrand() { return brand; }
    @Override
    public void setBrand(String newBrand) { this.brand = newBrand; }
    @Override
    public void setModel(String mod, String newMod) throws DuplicateModelNameException, NoSuchModelNameException {
        if (!models.containsKey(newMod)) {
            if (models.containsKey(mod)) {
                double price = models.remove(mod);
                addModel(newMod, price);
            }
            else {
                String message;
                if (models.size() == 0) message = "Models not found. Please add models.";
                else message = "This model not found. ";
                throw new NoSuchModelNameException(message, mod);
            }
        }
        else throw new DuplicateModelNameException("This model already exist. ", newMod);
    }
    @Override
    public String[] getStrModels() {
        String[] models = new String[this.models.size()];
        Iterator<String> iter = this.models.keySet().iterator();
        for (int i = 0; i < models.length; i++) {
            models[i] = iter.next();
        }
        return models;
    }
    @Override
    public double getPrice(String model) throws NoSuchModelNameException {
        if (models.containsKey(model)) {
            return models.get(model);
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", model);
    }
    @Override
    public void setPrice(String model, double newPrice) throws NoSuchModelNameException {
        if (newPrice > 0) {
            if (models.containsKey(model)) models.put(model, newPrice);
            else {
                String message;
                if (models.size() == 0) message = "Models not found. Please add models.";
                else message = "This model not found. ";
                throw new NoSuchModelNameException(message, model);
            }
        }
        else throw new ModelPriceOutOfBoundsException("Fake price. ", newPrice);
    }
    @Override
    public double[] getPricesModels() {
        Iterator<Double> iter = models.values().iterator();
        double[] prices = new double[models.size()];
        for (int i = 0; i < prices.length; i++) {
            prices[i] = iter.next();
        }
        return prices;
    }
    @Override
     public void addModel(String model, double price) throws DuplicateModelNameException {
        if (price > 0) {
            if (!models.containsKey(model)) models.put(model, price);
            else throw new DuplicateModelNameException("This model already exist. ", model);
        }
        else throw new ModelPriceOutOfBoundsException("Fake price. ", price);
    }
    @Override
    public void deleteModel(String model) throws NoSuchModelNameException {
        if (models.containsKey(model)) models.remove(model);
        else throw new NoSuchModelNameException("This model not found. ", model);
    }
    @Override
    public int getCount() {
        return models.size();
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(brand + " ");
        sb.append(models.size());
        String[] modelsName = getStrModels();
        double[] prices = getPricesModels();
        for (int i = 0; i < models.size(); i++) {
            sb.append(" " + modelsName[i] + " ");
            sb.append(prices[i]);
        }
        return new String(sb);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Scooter && brand.equals(((Scooter)obj).brand) && getCount() == ((Scooter) obj).getCount()) {
            Iterator<Map.Entry<String, Double>> iterThis = models.entrySet().iterator();
            Iterator<Map.Entry<String, Double>> iter = ((Scooter) obj).models.entrySet().iterator();
            Map.Entry<String, Double> meThis;
            Map.Entry<String, Double> me;
            while (iter.hasNext()) {
                meThis = iterThis.next();
                me = iter.next();
                if (!me.getKey().equals(meThis.getKey()) || me.getValue() != meThis.getValue()) return false;
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
            Iterator<Map.Entry<String, Double>> iter = models.entrySet().iterator();
            Map.Entry<String, Double> me;
            while (iter.hasNext()) {
                me = iter.next();
                resMas = resMas + (7 * me.getKey().hashCode() + Double.hashCode(me.getValue()));
            }
            return 7 * brand.hashCode() + resMas;
        }
    }
    @Override
    public Scooter clone() throws CloneNotSupportedException {
        Scooter s = (Scooter)super.clone();
        s.models = new HashMap<>();
        Iterator<Map.Entry<String, Double>> iter = models.entrySet().iterator();
        Map.Entry<String, Double> me;
        while (iter.hasNext()) {
            me = iter.next();
            s.models.put(me.getKey(), me.getValue());
        }
        return s;
    }
}
