package threadStream;

import java.util.concurrent.BlockingQueue;

public interface Actionable {

    BlockingQueue<String> action();

}
