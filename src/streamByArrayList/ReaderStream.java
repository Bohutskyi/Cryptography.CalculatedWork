package streamByArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReaderStream implements Runnable {

    private CopyOnWriteArrayList<String> arrayList;
    private String fileName;

    public ReaderStream(CopyOnWriteArrayList<String> arrayList, String fileName) {
        this.arrayList = arrayList;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                arrayList.add(buffer);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public CopyOnWriteArrayList<String> getArrayList() {
        return arrayList;
    }

}
