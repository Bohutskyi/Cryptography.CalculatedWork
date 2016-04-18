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

//        WriterThread writerThread = new WriterThread(new Actionable() {
//            @Override
//            public BlockingQueue<String> action() {
//
//                return null;
//            }
//        }, "Results/temp.txt");

        queue = reader.getBlockingQueue();
        WriterThread writer = new WriterThread(queue, "Results/temp.txt");
        long time = - System.nanoTime();
        new Thread(writer).start();
        time += System.nanoTime();
        System.out.println("time = " + time);
    }

}
