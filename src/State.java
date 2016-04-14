import java.util.ArrayList;

public class State {

    private static final int m = 18;
    private static final ArrayList<Integer> generator;

    private ArrayList<Integer> array;

    static {
        generator = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            generator.add(0);
        }
        generator.set(0, 1);
        generator.set(14, 1);
        generator.set(17, 1);
    }



}
