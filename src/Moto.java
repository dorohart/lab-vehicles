import java.io.Serializable;
import java.util.*;
import static java.lang.System.*;
class Moto implements ITransport {
    private class Model implements Serializable {
        String model = null;
        double price = Double.NaN;
        Model prev = null;
        Model next = null;
        public Model(String model, double price) {
            this.model = model;
            this.price = price;
        }
        private Model() {}
    }
    private int size = 0;
    private Model head;
    private transient long lastModified;
    private String brand;
    {
        lastModified = System.currentTimeMillis();
        head = new Model();
        head.next = head;
        head.prev = head;
    }
    public long getLastModified() {
        return lastModified;
    }
    public Moto(String brand, int length) {
        this.brand = brand;
        int cnt = 0;
        Random rnd = new Random();
        while (cnt < length) {
            try {
                addModel("model" + (cnt + 1), rnd.nextInt(1_000_000, 10_000_001));
            }
            catch (DuplicateModelNameException ex) {
                out.println(ex.getMessage() + " Duplicate is " + ex.getNameModel() + ", cnt = " + cnt);
            }
            cnt++;
        }
    }
    public Moto() {}
    @Override
    public String getBrand() {
        return brand;
    }
    @Override
    public void setBrand(String value) {
        brand = value;
        lastModified = System.currentTimeMillis();
    }
    @Override
    public void setModel(String mod, String newMod) throws DuplicateModelNameException, NoSuchModelNameException {
        if (head.next != head) {//1
            Model m = head.next;
            Model n = null;
            while (m != head) {
                if (m.model.equals(newMod)) throw new DuplicateModelNameException("This model already exist: " + mod, mod);
                if (m.model.equals(mod)) n = m;
                m = m.next;
            }
            if (n == null) throw new NoSuchModelNameException("This model not found: " + mod, mod);
            else {
                n.model = newMod;
            }
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", newMod);
    }
    @Override
    public String[] getStrModels() {
        String[] str = new String[getCount()];
        if (head.next != head) {
            Model m = head.next;
            int i = 0;
            while (m != head) {
                str[i] = m.model;
                i++;
                m = m.next;
            }
        }
        else str = new String[0];
        return str;
    }
    @Override
    public double getPrice(String mod) throws NoSuchModelNameException {
        double res = 0;
        boolean flag = false;
        if (head.next != head) {
            Model m = head.next;
            while (m != head && !flag) {
                if (m.model.equals(mod)) {
                    res = m.price;
                    flag = true;
                }
                else m = m.next;
            }
            if (!flag) throw new NoSuchModelNameException("This model not found: " + mod, mod);
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", mod);
        return res;
    }
    @Override
    public void setPrice(String mod, double newPrice) throws  NoSuchModelNameException {
        if (newPrice <= 0) throw new ModelPriceOutOfBoundsException("This fake price: " + newPrice, newPrice);
        else {
            if (head.next != head) {
                Model m = head.next;
                boolean flag = false;
                while (m != head && !flag) {
                    if (m.model.equals(mod)) {
                        m.price = newPrice;
                        flag = true;
                        lastModified = System.currentTimeMillis();
                    }
                    else m = m.next;
                }
                if (m == head) throw new NoSuchModelNameException("This model not found: " + mod, mod);
            }
            else throw new NoSuchModelNameException("Models not found. Please add models.", mod);
        }
    }
    @Override
    public double[] getPricesModels() {
        double[] res = new double[getCount()];
        if (head.next != head) {
            Model m = head.next;
            int i = 0;
            while (m != head) {
                res[i] = m.price;
                i++;
                m = m.next;
            }
        }
        return res;
    }
    @Override
    public void addModel(String mod, double price) throws DuplicateModelNameException {
        if (price <= 0) throw new ModelPriceOutOfBoundsException("This fake price: " + price, price);
        else {
            Model m = head.next;
            while (m != head) {
                if (m.model.equals(mod)) throw new DuplicateModelNameException("This model already exist: " + mod, mod);
                else m = m.next;
            }
            Model newModel = new Model(mod, price);
            newModel.next = head;
            newModel.prev = head.prev;
            head.prev.next  = newModel;
            head.prev = newModel;
            size++;
            lastModified = System.currentTimeMillis();
        }
    }
    @Override
    public void deleteModel(String mod) throws NoSuchModelNameException {
        if (head.next != head) {
            Model m = head.next;
            boolean flag = false;
            while (m != head && !flag) {
                if (m.model.equals(mod)) {
                    m.prev.next = m.next;
                    m.next.prev = m.prev;
                    flag = true;
                    lastModified = System.currentTimeMillis();
                    size--;
                }
                else m = m.next;
            }
            if (m == head) throw new NoSuchModelNameException("This model not found: " + mod, mod);
        }
        else throw new NoSuchModelNameException("Models not found. Please add models.", mod);
    }
    @Override
    public int getCount() {
        return size;
    }

    //4
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(brand + " ");
        sb.append(size);
        Model m = head.next;
        while (m != head) {
            sb.append(" " +  m.model + " ");
            sb.append(m.price);
            m = m.next;
        }
        String str = new String(sb);
        return str;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Moto && brand.equals(((Moto) obj).brand) && size == ((Moto)obj).size) {
            Model m = head.next;
            Model m2 = ((Moto) obj).head.next;
            while (m != head && m2 != ((Moto)obj).head) {
                if (!m.model.equals(m2.model) || m.price != m2.price) return false;
                m = m.next;
                m2 = m2.next;
            }
            return true;
        }
        else return false;
    }
    @Override
    public int hashCode() {
        if (brand == null) return 0;
        else {
            int resList = 1;
            Model m = head.next;
            while (m != head) {
                resList = 7 * resList + m.model.hashCode();
                resList = 7 * resList + Double.hashCode(m.price);
                m = m.next;
            }
            return 7 * brand.hashCode() + resList;
        }
    }
    @Override
    public Moto clone() throws CloneNotSupportedException {
        Moto obj;
        obj = (Moto)super.clone();
        obj.head = new Model();
        obj.head.next = obj.head;
        obj.head.prev = obj.head;
        Model m = this.head.next;
        while (m != this.head) {
            Model objMod = new Model(m.model, m.price);
            objMod.next = obj.head;
            objMod.prev = obj.head.prev;
            obj.head.prev.next = objMod;
            obj.head.prev = objMod;
            m = m.next;
        }
        return obj;
    }
}