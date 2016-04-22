import threadStream.ReaderThread;
import threadStream.WriterThread;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class CoordinateFunction {

//    private CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
    private String fileName;
    private ArrayList<String> arrayList = new ArrayList<>();

    public CoordinateFunction(String fileName) {
        this.fileName = fileName;
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(131072);
        Thread thread = new Thread(new ReaderThread(queue, fileName));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
//        arrayList = new CopyOnWriteArrayList<>(queue);
        for (int i = 0; i < queue.size(); i++) {
            try {
                arrayList.add(queue.take().split("")[0]);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        queue = null;
    }

    public ArrayList<String> calculateAlgebraicNormalForm() {
        ArrayList<String> result = new ArrayList<>(arrayList);
        ArrayList<Boolean> booleanArrayList = new ArrayList<>();
        for (int i = 0; i < this.arrayList.size(); i++) {
            booleanArrayList.add(true);
        }
        for (int i = 0; i < 17; i++) {
            System.out.println("i = " + i);
            for (int j = 0; j < 131072; j++) {
                if (booleanArrayList.get(j)) {
                    String u0 = getBinaryValue(j, 17);
                    String u1;
                    boolean isOnes;
                    if (u0.charAt(i) == '1') {
                        u1 = replace(u0, i, '0');
                        isOnes = true;
                    } else {
                        u1 = replace(u0, i, '1');
                        isOnes = false;
                    }

                    String vector = addition(result.get(Integer.parseInt(u0, 2)), result.get(Integer.parseInt(u1, 2)));

                    if (isOnes) {
                        result.set(Integer.parseInt(u0, 2), vector);
                    } else {
                        result.set(Integer.parseInt(u1, 2), vector);
                    }

                    booleanArrayList.set(Integer.parseInt(u0, 2), false);
                    booleanArrayList.set(Integer.parseInt(u1, 2), false);
                }
            }
            for (int j = 0; j < booleanArrayList.size(); j++) {
                booleanArrayList.set(j, true);
            }
        }
//        System.out.println(result);
//        System.out.println(arrayList);

        Thread thread = new Thread(new WriterThread( () -> {
            BlockingQueue<String> queue = new ArrayBlockingQueue<>(131072);
            for (int i = 0; i < result.size(); i++) {
                try {
                    queue.put(arrayList.get(i) + " " + result.get(i));
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            return queue;
        }, fileName));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static String getBinaryValue(int i, int size) {
        StringBuilder result = new StringBuilder();
        result.append(Integer.toBinaryString(i));
        while (result.length() < size) {
            result.insert(0, "0");
        }
        return result.toString();
    }

    public static String replace(String s, int i, char c) {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < s.length(); j++) {
            if (j != i) {
                result.append(s.charAt(j));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String addition(String s1, String s2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            byte c = (byte) (s1.charAt(i) - 48);
            c ^= (byte) (s2.charAt(i) - 48);
            result.append(c);
        }
        return result.toString();
    }

    public int getHadamardCoefficient() {
        int result = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            result += Math.pow(-1, Integer.parseInt(arrayList.get(i)));
        }
        return result;
    }

    public static void calculateHadamardCoefficient(String fileName, int n, String destinationFileName) {
        int[] results = new int[n];
        for (int i = 1; i <= n; i++) {
            CoordinateFunction coordinateFunction = new CoordinateFunction(fileName + i + ".txt");
            results[i - 1] = coordinateFunction.getHadamardCoefficient();
        }
        try (FileWriter writer = new FileWriter(destinationFileName)) {
            int count = 0;
            for (int i : results) {
                count++;
                if (count < 10) {
                    writer.write("C( " + count + ")[0] = " + i + "\n");
                } else {
                    writer.write("C(" + count + ")[0] = " + i + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        //Для обчлення АНФ
//        String fileName = "Results/BooleanFunction1/CoordinateFunction";
//        for (int i = 1; i <= 17; i++) {
//            StringBuilder s = new StringBuilder(fileName);
//            s.append(i);
//            s.append(".txt");
//            CoordinateFunction coordinateFunction = new CoordinateFunction(s.toString());
//            coordinateFunction.calculateAlgebraicNormalForm(s.toString());
//        }
//        fileName = "Results/BooleanFunction2/CoordinateFunction";
//        for (int i = 1; i <= 17; i++) {
//            StringBuilder s = new StringBuilder(fileName);
//            s.append(i);
//            s.append(".txt");
//            CoordinateFunction coordinateFunction = new CoordinateFunction(s.toString());
//            coordinateFunction.calculateAlgebraicNormalForm(s.toString());
//        }

//        CoordinateFunction coordinateFunction = new CoordinateFunction("Results/BooleanFunction1/CoordinateFunction1.txt");
//        System.out.println(coordinateFunction.arrayList);
//        System.out.println(coordinateFunction.getHadamardCoefficient());
        calculateHadamardCoefficient("Results/BooleanFunction1/CoordinateFunction", 17, "Results/BooleanFunction1/Imbalance1.txt");
        calculateHadamardCoefficient("Results/BooleanFunction2/CoordinateFunction", 17, "Results/BooleanFunction2/Imbalance2.txt");
    }

}
