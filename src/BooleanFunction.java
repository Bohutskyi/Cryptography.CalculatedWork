import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BooleanFunction {

    private final int power;
    private static final int m = 17;

    public BooleanFunction(int power) {
        this.power = power;
    }

    public void calculateTruthTable() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Results/states.txt"))) {
            String temp;

            ArrayList<Integer> tempPower = new ArrayList<>();
            for (String t : Integer.toBinaryString(power).split("")) {
                tempPower.add(Integer.parseInt(t));
            }

//            int count = 0;
            FileWriter writer = new FileWriter("Results/truthTable.txt", false);
            while ((temp = reader.readLine()) != null) {
                State state = new State(temp);
                state = State.power(state, tempPower);
                StringBuilder result = new StringBuilder();
                result.append(state.toString());
                while (result.length() < m) {
                    result.insert(0, '0');
                }
                write(temp + " " + result.toString(), writer);
//                count++;
//                System.out.println(count);
//                if (count == 1) {
//                    System.out.println("here1");
//                    System.out.println("temp = " + temp);
//                    State state = new State(temp);
//                    State.print(state);
//                    state = State.power(state, tempPower);
//                    System.out.println();
//                    State.print(state);
//                    System.out.println();
//                }
//                if ((count == 131072) || (count == 4823) || (count == 43666)) {
//                    System.out.println("here2");
//                    State state = new State(temp);
//                    State.print(state);
//                    state = State.power(state, tempPower);
//                    System.out.println();
//                    State.print(state);
//                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void write(String s, FileWriter fileWriter) {
        try {
            fileWriter.write(s + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        BooleanFunction function = new BooleanFunction(131070);
        function.calculateTruthTable();
    }

}
