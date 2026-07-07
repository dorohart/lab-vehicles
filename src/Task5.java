import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

import static java.lang.System.*;
import static java.lang.System.out;

class Task5 {
    public static double getAverage(ITransport transport) {
        double res = 0;
        double[] prices = transport.getPricesModels();
        for (int i = 0; i < prices.length; i++) {
            res += prices[i];
        }
        if (transport.getCount() == 0) res = 0;
        else res /= transport.getCount();
        return res;
    }
    public static void printModels(ITransport transport) {
        String[] models = transport.getStrModels();
        out.println("All models of the " + transport.getBrand() + " brand:");
        for (int i = 0; i < models.length; i++) {
            out.print(models[i] + ";  ");
        }
        out.println();
    }
    public static void printPrices(ITransport transport) {
        double[] prices = transport.getPricesModels();
        out.println("All prices of the " + transport.getBrand() + " brand:");
        for (int i = 0; i < prices.length; i++) {
            out.print(prices[i] + ";  ");
        }
        out.println();
    }

    //3
    public static void outputTransport(ITransport v, OutputStream out)  throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] bytes;
        bytes = v.getClass().getName().getBytes();
        dos.writeInt(bytes.length);
        dos.write(bytes);
        bytes = v.getBrand().getBytes();
        dos.writeInt(bytes.length);
        dos.write(bytes);
        int cntModels = v.getCount();
        dos.writeInt(cntModels);
        String[] models = v.getStrModels();
        double[] priceModels = v.getPricesModels();
        for (int i = 0; i < v.getCount(); i++) {
            bytes = models[i].getBytes();
            dos.writeInt(bytes.length);
            dos.write(bytes);
            dos.writeDouble(priceModels[i]);
        }
        dos.flush();
    }
    public static ITransport inputTransport(InputStream in) throws IOException, DuplicateModelNameException, ReflectiveOperationException {
        DataInputStream dis = new DataInputStream(in);
        ITransport tr;
        byte[] bytes = new byte[dis.readInt()]; //читает 4байт и переводит его в число
        dis.readFully(bytes); //читает, пока не заполнит
        String name = new String(bytes);
        Class<?> trRefl = Class.forName(name);
        tr = (ITransport)trRefl.getDeclaredConstructor().newInstance();
        bytes = new byte[dis.readInt()];
        dis.readFully(bytes);
        name = new String(bytes);
        tr.setBrand(name);
        int cntModels = dis.readInt();
        double priceModel;
        for (int i = 0; i < cntModels; i++) {
            bytes = new byte[dis.readInt()];
            dis.readFully(bytes);
            name = new String(bytes);
            priceModel = dis.readDouble();
            tr.addModel(name, priceModel);
        }
        return tr;
    }
    public static void writeTransport(ITransport v, Writer out) throws IOException {
        PrintWriter pw = new PrintWriter(out); //не бросает IOExeption
        pw.format("%s%n", v.getClass().getName());
        pw.format("%s%n", v.getBrand());
        pw.format("%d%n", v.getCount());
        String[] models = v.getStrModels();
        double[] priceModels = v.getPricesModels();
        for (int i = 0; i < v.getCount(); i++) {
            pw.format("%s%n", models[i]);
            pw.format("%,.2f%n", priceModels[i]);
        }
        pw.flush();
    }
    public static ITransport readTransport(Reader in) throws IOException, DuplicateModelNameException,  ReflectiveOperationException {
        Scanner scan = new Scanner(in);
        ITransport transport;
        Class<?> tr = Class.forName(scan.nextLine());
        transport = (ITransport) tr.getDeclaredConstructor().newInstance();
        transport.setBrand(scan.nextLine());
        int cnt = Integer.parseInt(scan.nextLine());
        double price;
        String model;
        for (int i = 0; i < cnt; i++) {
            model = scan.nextLine();
            price = scan.nextDouble();
            scan.nextLine();
            transport.addModel(model, price);
        }
        return transport;
    }

    //5
    public static ITransport createTransport(String brandCar, int masLength, ITransport tr) {
        try {
            Constructor<?> c = tr.getClass().getConstructor(String.class, int.class);
            return (ITransport)c.newInstance(brandCar, masLength);
        }
        catch (Exception ex) {
            return null;
        }
    }
    public static double getAverage(ITransport... transports) {
        double allSum = 0;
        int allCount = 0;
        for (ITransport transport : transports) {
            double[] prices = transport.getPricesModels();
            for (int i = 0; i < transport.getCount(); i++) {
                allSum += prices[i];
                allCount++;
            }
        }
        if (allCount == 0) throw new ArithmeticException("Devide by zero");
        return allSum / allCount;
    }
}
