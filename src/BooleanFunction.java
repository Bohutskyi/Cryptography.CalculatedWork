import java.io.FileWriter;
import java.io.IOException;

public class BooleanFunction {

    private final int power;
    private static final int m = 17;

    public BooleanFunction(int power) {
        this.power = power;
    }

    public void calculateTruthTable() {
        try (FileWriter writer = new FileWriter("states1.txt", false)) {
            for (int i = 0; i < m; i++) {
                writer.write(Integer.toString(i) + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void main(String[] args) {
        BooleanFunction function = new BooleanFunction(131070);
        function.calculateTruthTable();
    }

}
