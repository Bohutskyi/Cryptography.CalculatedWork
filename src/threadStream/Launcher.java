package threadStream;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Launcher {

    public static void main(String[] args) {
        String fileName = "Results/truthTable1.txt";

        WriterThread writerThread = new WriterThread(new Actionable() {
            @Override
            public BlockingQueue<String> action() {
                BlockingQueue<String> queue1 = new ArrayBlockingQueue<String>(131072);
                ReaderThread readerThread = new ReaderThread(queue1, "Results/truthTable1.txt");

                Thread temp = new Thread(readerThread);
                temp.start();
                try {
                    temp.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                return readerThread.getBlockingQueue();
            }
        }, "Results/temp.txt");
        new Thread(writerThread).start();

    }

}
