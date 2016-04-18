package threadStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class WriterThread implements Runnable {

    private BlockingQueue<String> blockingQueue;
    private String fileName;

    public WriterThread(BlockingQueue<String> blockingQueue, String fileName) {
        this.blockingQueue = blockingQueue;
        this.fileName = fileName;
    }

    public WriterThread(Actionable actionable, String fileName) {
        this.blockingQueue = actionable.action();
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (String buffer : blockingQueue) {
                writer.write(buffer + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
