import java.io.FileWriter;
import java.io.IOException;

public class StateGenerator {

    public static void generate(int n, int length, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (int i = 0; i < n; i++) {
                StringBuilder result = new StringBuilder();
                result.append(Integer.toBinaryString(i));
                while (result.length() < length) {
                    result.insert(0, '0');
                }
                writer.write(result + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        generate(131072, 17, "Results/states.txt");
    }

}
