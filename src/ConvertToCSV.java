import java.io.*;
import java.util.ArrayList;

public class ConvertToCSV {

    public static void main(String[] args) {
//        String fileName = "Results/BooleanFunction2/PropagationRates2.txt";
        String fileName = "Results/GeneralAnalysis2.txt";
        String destination = "Results/t.csv";
        ArrayList<String> results = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                results.add(buffer);
            }
            reader.close();
            FileWriter writer = new FileWriter(destination);
            for (String temp : results) {
                for (String t : temp.split(" ")) {
                    writer.write(t + ";");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
