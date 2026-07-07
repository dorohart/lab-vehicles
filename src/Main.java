import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.*;
public class Main {
    public static void main(String[] args) {//отдельный
        /*
        ITransport tr = new Car("Haval", 2);
        //ITransport moto = new Moto("Honda", 2);
        tr.setBrand("BMW");
        //moto.setBrand("Yamaha");
        //out.println(car.getBrand() + "   " + moto.getBrand());
        //out.println(car.getCount() + "       " + moto.getCount());
        Task5.printModels(tr);

        Task5.printPrices(tr);
        out.println(Task5.getAverage(tr));
        //int cnt = 1;
        //boolean flag = false;
        //Scanner scan = new Scanner(in);
        while (!flag) {
            try {
                if (cnt == 1) {
                    out.print("Введите модель машины, название которой хотите поменять: ");
                    String s1 = scan.nextLine();
                    out.print("На какое название хотите поменять: ");
                    String s2 = scan.nextLine();
                    tr.setModel(s1, s2);
                    cnt++;
                }
                if (cnt == 2) {
                    out.print("Введите модель машины, цену которой хотите получить: ");
                    String s1 = scan.nextLine();
                    out.println(tr.getPrice(s1));
                    cnt++;
                }
                if (cnt == 3) {
                    out.print("Введите марку машины, которую хотите удалить: ");
                    String s = scan.nextLine();
                    tr.deleteModel(s);
                    cnt++;
                }
                flag = true;
            }
            catch (DuplicateModelNameException ex) {
                out.println(ex.getMessage() + " Duplicate is " + ex.getNameModel() + ", cnt = " + cnt);
            }
            catch (NoSuchModelNameException ex) {
                out.println(ex.getMessage() + " Not is " + ex.getNameModel() + ", cnt = " + cnt);
            }
        }
        //Task5.printModels(tr);
        //Task5.printModels(moto);
        //Task5.printPrices(moto);
        tr = new Moto("Honda", 2);
        //flag = false;
        //cnt = 1;
        /*while (!flag) {
            try {
                if (cnt == 1) {
                    out.print("Введите модель мото, которую хотите добавить: ");
                    String s = scan.nextLine();
                    out.print("Дайте ей стартовую цену: ");
                    boolean flag2 = false;
                    int price = 0;
                    while (!flag2) {
                        if (scan.hasNextInt()) {
                            price = scan.nextInt();
                            scan.nextLine();
                            flag2 = true;
                        } else {
                            out.print("Введите число: ");
                            scan.next();
                        }
                    }
                    tr.addModel(s, price);
                    cnt++;
                }
                if (cnt == 2) {
                    out.print("Введите модель мото, которую хотите поменять: ");
                    String s1 = scan.nextLine();
                    out.print("На какое название хотите поменять: ");
                    String s2 = scan.nextLine();
                    tr.setModel(s1, s2);
                    cnt++;
                }
                flag = true;
            }
            catch (DuplicateModelNameException ex) {
                out.println(ex.getMessage() + " Duplicate is " + ex.getNameModel() + ", cnt = " + cnt);
            }
            catch (NoSuchModelNameException ex) {
                out.println(ex.getMessage() + " Not is " + ex.getNameModel() + ", cnt = " + cnt);
            }
        }
        Task5.printModels(tr);
        Task5.printPrices(tr);
        out.println(tr.getClass());
        out.println("____________________________________________________________________");
        out.println("лаба3");

        //3

        ITransport tr2;
        boolean equals;
        try {
            OutputStream os = new FileOutputStream("t.byte");
            Task5.outputTransport(tr, os);
            os.close();

            InputStream is = new FileInputStream("t.byte");
            tr2 = Task5.inputTransport(is);
            equals = tr.getBrand().equals(tr2.getBrand());
            if (equals) {
                equals = (tr.getCount() == tr2.getCount());
                if (equals) {
                    String[] modelsTr = tr.getStrModels();
                    String[] modelsTr2 = tr2.getStrModels();
                    double[] priceTr = tr.getPricesModels();
                    double[] priceTr2 = tr2.getPricesModels();
                    for (int i = 0; i < tr.getCount() && equals; i++) {
                        equals = modelsTr[i].equals(modelsTr2[i]);
                        if (equals) equals = (priceTr[i] == priceTr2[i]);
                    }
                }
            }
            out.println("(byte) This transport and readed transport is equals: " + equals);
            out.println("Start transport.");
            out.println("Name brand: " + tr.getBrand());
            out.println("Count models: " + tr.getCount());
            Task5.printModels(tr);
            Task5.printPrices(tr);
            out.println("Stop transport.");
            out.println("Name brand: " + tr2.getBrand());
            out.println("Count models: " + tr2.getCount());
            Task5.printModels(tr2);
            Task5.printPrices(tr2);
            is.close();

            Writer w = new FileWriter("c.txt");
            Task5.writeTransport(tr, w);
            w.close();

            Reader r = new FileReader("c.txt");
            tr2 = Task5.readTransport(r);
            equals = tr.getBrand().equals(tr2.getBrand());
            if (equals) {
                equals = (tr.getCount() == tr2.getCount());
                if (equals) {
                    String[] modelsTr = tr.getStrModels();
                    String[] modelsTr2 = tr2.getStrModels();
                    double[] priceTr = tr.getPricesModels();
                    double[] priceTr2 = tr2.getPricesModels();
                    for (int i = 0; i < tr.getCount() && equals; i++) {
                        equals = modelsTr[i].equals(modelsTr2[i]);
                        if (equals) equals = (priceTr[i] == priceTr2[i]);
                    }
                }
            }
            out.println("(char) This transport and readed transport is equals: " + equals);
            r.close();

            out.println("________________________________________________________________________________________________________");
            out.println("Please, enter with new line <Car/Moto>, <nameBrand>, <countModels>, <nameModel>, <priceModel>, ...");
            Reader r2 = new InputStreamReader(in);
            tr2 = Task5.readTransport(r2);
            r2.close();

            Writer w2 = new OutputStreamWriter(out);
            Task5.writeTransport(tr2, w2);
            out.flush();

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("f.ser"));
            oos.writeObject(tr2);
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("f.ser"));
            ITransport tr3 = (ITransport)ois.readObject();
            equals = tr2.getBrand().equals(tr3.getBrand());
            if (equals) {
                equals = (tr2.getCount() == tr3.getCount());
                if (equals) {
                    String[] modelsTr = tr2.getStrModels();
                    String[] modelsTr2 = tr3.getStrModels();
                    double[] priceTr = tr2.getPricesModels();
                    double[] priceTr2 = tr3.getPricesModels();
                    for (int i = 0; i < tr2.getCount() && equals; i++) {
                        equals = modelsTr[i].equals(modelsTr2[i]);
                        if (equals) equals = (priceTr[i] == priceTr2[i]);
                    }
                }
            }
            out.println("____________________________________________________________________");
            out.println("(ser) This transport and readed transport is equals: " + equals);
            ois.close();
        }
        catch (IOException ex) {
            out.println("IO Exception");
            ex.getStackTrace();
        }
        catch (DuplicateModelNameException ex) {
            out.println(ex.getMessage() + " Duplicate is " + ex.getNameModel());
            ex.getStackTrace();
        }
        catch (ReflectiveOperationException ex) {
            out.println(ex.getMessage());
            ex.getStackTrace();
        }

        out.println("____________________________________________________________________");
        out.println("лаба4");

        //4
        ITransport transport1 = new Car("Mercedes", 2);
        out.println(transport1);
        ITransport transport2 = transport1;
        if (transport1.hashCode() == transport2.hashCode()) {
            out.println(transport1.equals(transport2));
        }
        transport2 = new Car();
        out.println(transport1.hashCode() == transport2.hashCode());
        out.println(transport1.equals(transport2));
        try {
            transport2 = (ITransport)((Car)transport1).clone();
            out.println("Default transport.");
            out.println("Name brand: " + transport1.getBrand());
            out.println("Count models: " + transport1.getCount());
            Task5.printModels(transport1);
            Task5.printPrices(transport1);
            out.println("Clone transport.");
            out.println("Name brand: " + transport2.getBrand());
            out.println("Count models: " + transport2.getCount());
            Task5.printModels(transport2);
            Task5.printPrices(transport2);
            try {
                transport2.addModel("Q7", 1234567);
            } catch (DuplicateModelNameException e) {
                out.println("Duplicate");
            }
            Task5.printModels(transport1);
            Task5.printPrices(transport1);
            Task5.printModels(transport2);
            Task5.printPrices(transport2);
            if (transport1.hashCode() == transport2.hashCode()) {
                out.println(transport1.equals(transport2));
            }
        }
        catch (CloneNotSupportedException ex) {
            out.println("Error! Car not realization Clonable.");
        }

        transport1 = new Moto("Yamaha", 2);
        out.println(transport1.toString());
        transport2 = transport1;
        if (transport1.hashCode() == transport2.hashCode()) {
            out.println(transport1.equals(transport2));
        }
        transport2 = new Moto();
        out.println(transport1.hashCode() == transport2.hashCode());
        out.println(transport1.equals(transport2));
        try {
            transport2 = (ITransport)((Moto)transport1).clone();
            out.println("Default transport.");
            out.println("Name brand: " + transport1.getBrand());
            out.println("Count models: " + transport1.getCount());
            Task5.printModels(transport1);
            Task5.printPrices(transport1);
            out.println("Clone transport.");
            out.println("Name brand: " + transport2.getBrand());
            out.println("Count models: " + transport2.getCount());
            Task5.printModels(transport2);
            Task5.printPrices(transport2);
            try {
                transport2.addModel("Q5", 1234567);

            } catch (DuplicateModelNameException e) {
                out.println("Duplicate");
            }
            Task5.printModels(transport1);
            Task5.printPrices(transport1);
            Task5.printModels(transport2);
            Task5.printPrices(transport2);
            if (transport1.hashCode() == transport2.hashCode()) { //тест на глубокое клонирование как в лекции
                out.println(transport1.equals(transport2)); //
            }
        }
        catch (CloneNotSupportedException ex) {
            out.println("Error! Car not realization Clonable.");
        }
        */

        //5
        /*
        out.println("____________________________________________________________________");
        out.println("лаба5");

        try {
            Class<?> cl = Class.forName(args[0]);
            ITransport transport = (ITransport)cl.newInstance();
            transport.setBrand("test");
            transport.addModel("model1", 1_100_000);
            transport.addModel("model2", 3_230_000);
            Task5.printModels(transport);
            Task5.printPrices(transport);
            Method method = cl.getMethod(args[1], String.class, double.class);
            method.invoke(transport, args[2], Double.parseDouble(args[3]));
            Task5.printPrices(transport);

            ITransport transport2 = Task5.createTransport("Honda", 4, transport);
            if (transport2 != null) {
                Task5.printModels(transport2);
                Task5.printPrices(transport2);
                out.println("class: " + transport.getClass());
                out.println("class of the new object: " + transport2.getClass());
                ITransport transport3 = new Moto("BMW", 2);
                Task5.printPrices(transport3);
                out.println("average = " + Task5.getAverage(transport, transport2, transport3));
            }
            else out.println("reflective exception.");
        } catch (ReflectiveOperationException | DuplicateModelNameException ex) {
            out.println("exception");
            ex.printStackTrace();
        }
        out.println();

        try {
            ITransport transport = new Moped("Motoland", 2);
            ITransport test;
            transport.addModel("Scout", 57_000);
            transport.setPrice("Scout", 45_000);
            Task5.printModels(transport);
            Task5.printPrices(transport);
            transport.deleteModel("model1");
            transport.setModel("Scout", "Scout200");
            Task5.printModels(transport);
            Task5.printPrices(transport);
            test = (ITransport)transport.clone();
            out.println(test.getClass() + " " + test.getBrand() + " " + test.getPrice("model2"));
            out.println();

            transport = new Scooter("Stels", 3);
            transport.addModel("Tactic", 57_000);
            transport.setPrice("Tactic", 45_000);
            Task5.printModels(transport);
            Task5.printPrices(transport);
            transport.deleteModel("model1");
            transport.setModel("Tactic", "Tactic200");
            Task5.printModels(transport);
            Task5.printPrices(transport);
            test = (ITransport)transport.clone();
            out.println(test.getClass() + " " + test.getBrand() + " " + test.getPrice("model2"));
            out.println();

            transport = new QuadBike("Kayo", 2);
            transport.addModel("A300", 570_000);
            transport.setPrice("A300", 450_000);
            Task5.printModels(transport);
            Task5.printPrices(transport);
            transport.deleteModel("model1");
            transport.setModel("A300", "B2000");
            Task5.printModels(transport);
            Task5.printPrices(transport);
            test = (ITransport)transport.clone();
            out.println(test.getClass() + " " + test.getBrand() + " " + test.getPrice("model2"));
            out.println();
        }
        catch (DuplicateModelNameException | NoSuchModelNameException | CloneNotSupportedException ex) {
            ex.printStackTrace();
        }

        ITransport tr = new Car("Haval", 2);
        ITransport tr2;
        boolean equals;
        try {
            Writer w = new FileWriter("c.txt");
            Task5.writeTransport(tr, w);
            w.close();

            Reader r = new FileReader("c.txt");
            tr2 = Task5.readTransport(r);
            equals = tr.getBrand().equals(tr2.getBrand());
            if (equals) {
                equals = (tr.getCount() == tr2.getCount());
                if (equals) {
                    String[] modelsTr = tr.getStrModels();
                    String[] modelsTr2 = tr2.getStrModels();
                    double[] priceTr = tr.getPricesModels();
                    double[] priceTr2 = tr2.getPricesModels();
                    for (int i = 0; i < tr.getCount() && equals; i++) {
                        equals = modelsTr[i].equals(modelsTr2[i]);
                        if (equals) equals = (priceTr[i] == priceTr2[i]);
                    }
                }
            }
            out.println("(char) This transport and readed transport is equals: " + equals);
            r.close();
        }
        catch (IOException | DuplicateModelNameException | ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
        */

        //6
        out.println("____________________________________________________________________");
        out.println("лаба6");

        ITransport transport = new Car("BMW", 4);

       /* Thread thread1 = new Thread1Model(transport);
        Thread thread2 = new Thread1Price(transport);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.start();
        thread1.start();
*/
        TransportSynchronizer ts = new TransportSynchronizer(transport);
        Thread t1 = new Thread(new Thread2Model(ts));
        Thread t2 = new Thread(new Thread2Price(ts));
        t2.start();
        t1.start();

        /*ReentrantLock rl = new ReentrantLock();
        Thread t1 = new Thread(new Thread3Model(rl, transport));
        Thread t2 = new Thread(new Thread3Price(rl, transport));
        t1.start();
        t2.start();*/

     /*   ExecutorService pool = Executors.newFixedThreadPool(2);
        ITransport tr1 = new Car("BMW", 3);
        ITransport tr2 = new Moto("Yamaha", 3);
        ITransport tr3 = new QuadBike("Kayo", 2);
        ITransport tr4 = new Scooter("Stels", 2);
        pool.submit(new ThreadBrand(tr1));
        pool.submit(new ThreadBrand(tr2));
        pool.submit(new ThreadBrand(tr3));
        pool.submit(new ThreadBrand(tr4));
        pool.shutdown();*/

        /*ArrayBlockingQueue<ITransport> transports = new ArrayBlockingQueue<>(1);
        Thread thread;
        String[] files = {"Car.txt", "Moto.txt", "Moped.txt", "QuadBike.txt", "Scooter.txt"};
        for (int i = 0; i < files.length; i++) {
            thread = new Thread(new ThreadForFile(files[i], transports));
            thread.start();
        }
        for (int i = 0; i < files.length; i++) {
            try {
                ITransport tr = transports.take();
                out.println(tr.getClass() + ", brand: " + tr.getBrand());
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }*/
    }
}

