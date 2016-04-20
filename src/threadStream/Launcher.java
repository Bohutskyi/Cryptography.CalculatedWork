package threadStream;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Launcher {

    public static void main(String[] args) {
        String fileName = "Results/truthTable1.txt";
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(131072);
        ReaderThread reader = new ReaderThread(queue, fileName);
        System.out.println(queue);
//        long time = - System.nanoTime();
//        new Thread(reader).start();
//        time += System.nanoTime();
//        System.out.println("time = " + time);

        WriterThread writerThread = new WriterThread(new Actionable() {
            @Override
            public BlockingQueue<String> action() {
                BlockingQueue<String> queue1 = new ArrayBlockingQueue<String>(131072);
                ReaderThread readerThread = new ReaderThread(queue1, "Results/truthTable1.txt");
//                new Thread(readerThread).start();

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

//        queue = reader.getBlockingQueue();
//        WriterThread writer = new WriterThread(queue, "Results/temp.txt");
//        new Thread(writer).start();
    }

}
