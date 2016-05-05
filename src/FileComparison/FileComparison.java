package FileComparison;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileComparison implements Runnable {

    private String fileName1, fileName2;

    public FileComparison(String fileName1, String fileName2) {
        this.fileName1 = fileName1;
        this.fileName2 = fileName2;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(fileName1));
            BufferedReader reader2 = new BufferedReader(new FileReader(fileName2));
            int count = 1;
            String buffer1 = "", buffer2 = "";

            while (true) {
                buffer1 = reader1.readLine();
                buffer2 = reader2.readLine();
                if (buffer1 == null || buffer2 == null) {
                    break;
                }
                if (buffer1.equals(buffer2)) {
                    count++;
                } else {
                    System.out.println("Not equals in " + count);
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        FileComparison comparison = new FileComparison("Results/t1.txt", "Results/t2.txt");
        new Thread(comparison).start();
    }
}
