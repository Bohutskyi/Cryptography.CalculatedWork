package streamByArrayList;

import java.util.concurrent.CopyOnWriteArrayList;

public class Launcher {

    public static void main(String[] args) {
        String fileName = "Results/truthTable1.txt";

        new Thread(new WriterStream( () -> {
            CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
            ReaderStream readerStream = new ReaderStream(arrayList, "Results/truthTable1.txt");
            Thread thread = new Thread(readerStream);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return readerStream.getArrayList();
        }, "Results/temp1.txt")).start();

    }

}
