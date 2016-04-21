package streamByArrayList;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CopyOnWriteArrayList;

public class WriterStream implements Runnable {

    private CopyOnWriteArrayList<String> arrayList;
    private String fileName;

    public WriterStream(CopyOnWriteArrayList<String> arrayList, String fileName) {
        this.arrayList = arrayList;
        this.fileName = fileName;
    }

    public WriterStream(Actionable actionable, String fileName) {
        this.arrayList = actionable.action();
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (String buffer : arrayList) {
                writer.write(buffer + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public CopyOnWriteArrayList<String> getArrayList() {
        return arrayList;
    }
}
