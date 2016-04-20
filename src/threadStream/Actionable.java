package threadStream;

import java.util.concurrent.BlockingQueue;

@FunctionalInterface
public interface Actionable {

    BlockingQueue<String> action();

}
