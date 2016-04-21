package streamByArrayList;

import java.util.concurrent.CopyOnWriteArrayList;

@FunctionalInterface
public interface Actionable {

    CopyOnWriteArrayList<String> action();

}
