package threadStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * http://stackoverflow.com/questions/29339933/read-and-write-files-in-java-using-separate-threads
 * You cannot control the order of thread execution. However, to perform read and write operation via separate threads,
 * you should use BlockingQueue which has the following properties:
 * A Queue that additionally supports operations that wait for the queue to become non-empty when retrieving an element,
 * and wait for space to become available in the queue when storing an element
 * ReaderThread will read from the input file.
 */

public class ReaderThread implements Runnable {

    private BlockingQueue<String> blockingQueue;
    private String fileName;

    public ReaderThread(BlockingQueue<String> blockingQueue, String fileName) {
        this.blockingQueue = blockingQueue;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String buffer = null;
            while ((buffer = reader.readLine()) != null) {
                blockingQueue.put(buffer);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public BlockingQueue<String> getBlockingQueue() {
        return blockingQueue;
    }

}
