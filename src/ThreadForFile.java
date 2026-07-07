import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadForFile implements Runnable {
    private String fileName;
    private ArrayBlockingQueue<ITransport> transports;
    public ThreadForFile(String fileName, ArrayBlockingQueue<ITransport> transports) {
        this.fileName = fileName;
        this.transports = transports;
    }
    @Override
    public void run() {
        try {
            Scanner scan = new Scanner(new FileReader(fileName));
            Class<?> tr = Class.forName(scan.nextLine());
            ITransport newTr = (ITransport)tr.getConstructor().newInstance();
            newTr.setBrand(scan.nextLine());
            transports.put(newTr);
        }
        catch (Exception ex) {
            System.out.println("Transport not create");
            ex.printStackTrace();
        }
    }
}
