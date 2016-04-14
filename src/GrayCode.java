import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GrayCode {

    private int n;
    private int count = 0;
    private static final int MIDDLE_COUNT = 65536;
    private FileWriter writer;
    private ArrayList<Integer> arrayList;

    public GrayCode(ArrayList<Integer> arrayList, int n) {
        this.arrayList = arrayList;
        this.n = n;
        try {
            writer = new FileWriter("Results/states1.txt", false);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void calculate(int k) {
        gray(k);
        System.out.println("Here");
        System.out.println(arrayList);
        System.out.println("Here");
    }

    private void gray(int k) {
        if (k == n + 1) {
            action();
        } else {
            arrayList.set(k, 0);
            gray(k + 1);
            arrayList.set(k ,1);
            rgray(k + 1);
        }
    }

    private void rgray(int k) {
        if (k == n + 1) {
            action();
        } else {
            arrayList.set(k ,1);
            gray(k + 1);
            arrayList.set(k, 0);
            rgray(k + 1);
        }
    }

    private void action() {
        count++;
        if (count == MIDDLE_COUNT) {
            try {
                writer.close();
                writer = new FileWriter("Results/states2.txt", false);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i : arrayList) {
            result.append(i);
        }
        try {
            writer.write(result.toString() + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            temp.add(0);
        }
        System.out.println(temp);
        GrayCode code = new GrayCode(temp, temp.size() - 1);
        code.calculate(0);
        System.out.println(code.count);
        System.out.println(code.arrayList);
    }
}
