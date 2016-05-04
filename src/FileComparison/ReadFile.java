package FileComparison;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile implements Runnable {

    private String fileName;
    private boolean b;
    private Comparison comparison;

    public ReadFile(String fileName, Comparison comparison, boolean b) {
        this.fileName = fileName;
        this.comparison = comparison;
        this.b = b;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                if (b) {
                    comparison.add1(buffer);
                } else {
                    comparison.add2(buffer);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
