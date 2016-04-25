import threadStream.ReaderThread;
import threadStream.WriterThread;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CoordinateFunction {

    private static final int n = 17;

//    private CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
    private String fileName;
    private ArrayList<String> arrayList = new ArrayList<>();

    public CoordinateFunction(String fileName) {
        this(fileName, 0);
    }

//    public CoordinateFunction(String fileName) {
    public CoordinateFunction(String fileName, int k) {
        this.fileName = fileName;
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(131072);
        Thread thread = new Thread(new ReaderThread(queue, fileName));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        arrayList = new ArrayList<>(queue);
        for (int i = 0; i < arrayList.size(); i++ ){
//            arrayList.set(i, arrayList.get(i).split("")[0]);
            arrayList.set(i, arrayList.get(i).split(" ")[k]);
        }
//        for (int i = 0; i < queue.size(); i++) {
//            try {
//                System.out.print(queue.take());
//                arrayList.add(queue.take().trim().split("")[0]);
//                System.out.print(queue.take().trim().split("")[0]);
//            } catch (InterruptedException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        System.out.println("here = " + arrayList);
//        System.out.println("here = " + arrayList.size());
        queue = null;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
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

                    String vector = additionByMod(result.get(Integer.parseInt(u0, 2)), result.get(Integer.parseInt(u1, 2)));

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

    public ArrayList<Integer> fastConversionWalsh() {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Boolean> booleanArrayList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            result.add((int) Math.pow(-1, Integer.parseInt(arrayList.get(i), 2)));
            booleanArrayList.add(true);
        }
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 131072; j++) {
                if (booleanArrayList.get(j)) {
                    String u0 = getBinaryValue(j ,17);
                    String u1;
                    boolean isOnes;
                    if (u0.charAt(i) == '1') {
                        u1 = replace(u0, i, '0');
                        isOnes = true;
                    } else {
                        u1 = replace(u0, i, '1');
                        isOnes = false;
                    }
                    int value1 = result.get(Integer.parseInt(u0, 2)) + result.get(Integer.parseInt(u1, 2));
                    int value2 = result.get(Integer.parseInt(u0, 2)) - result.get(Integer.parseInt(u1, 2));
                    if (isOnes) {
                        result.set(Integer.parseInt(u0, 2), value2);
                        result.set(Integer.parseInt(u1, 2), value1);
                    } else {
                        result.set(Integer.parseInt(u0, 2), value1);
                        result.set(Integer.parseInt(u1, 2), value2);
                    }
                    booleanArrayList.set(Integer.parseInt(u0, 2), false);
                    booleanArrayList.set(Integer.parseInt(u1, 2), false);
                }
            }
            for (int j = 0; j < booleanArrayList.size(); j++) {
                booleanArrayList.set(j, true);
            }
        }
        return result;
    }

    public static void AllFastConversionWalsh(String fileName, int n) {
        for (int i = 1; i <= n; i++) {
            StringBuilder temp = new StringBuilder(fileName);
            temp.append(i + ".txt");
            reWrite(temp.toString(), new CoordinateFunction(temp.toString()).fastConversionWalsh());
        }
    }

    public static void reWrite(String fileName, ArrayList arrayList) {
        File inputFile = new File(fileName);
        String tempFileName = "Results/BooleanFunction1/temp.txt";
        File outputFile = new File(tempFileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(outputFile);
            String buffer;
            int count = 0;
            while ((buffer = reader.readLine()) != null) {
                writer.write(buffer + " " + arrayList.get(count) + "\n");
                count++;
            }
            reader.close();
            writer.close();
            inputFile.delete();
            boolean t = outputFile.renameTo(inputFile);
            System.out.println(t);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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

    public static String additionByMod(String s1, String s2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            byte c = (byte) (s1.charAt(i) - 48);
            c ^= (byte) (s2.charAt(i) - 48);
            result.append(c);
        }
        return result.toString();
    }

    public static String addition(String s1, String s2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            byte c = (byte) (s1.charAt(i) - 48);
            c += (byte) (s2.charAt(i) - 48);
            result.append(c);
        }
        return result.toString();
    }

    public static String subtraction(String s1, String s2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            byte c = (byte) (s1.charAt(i) - 48);
            c -= (byte) (s2.charAt(i) - 48);
            result.append(c);
        }
        return result.toString();
    }

    public int getHadamardCoefficient() {
        int result = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            result += Math.pow(-1, Integer.parseInt(arrayList.get(i), 2));
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

    public static ArrayList<Integer> calculateNonlinearity(String fileName, int n) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 1; i<= n; i++) {
            StringBuilder temp = new StringBuilder(fileName);
            temp.append(i + ".txt");
            int max = 0;
            CoordinateFunction coordinateFunction = new CoordinateFunction(temp.toString());
            for (int k : coordinateFunction.fastConversionWalsh()) {
                if (Math.abs(k) > max) {
                    max = Math.abs(k);
                }
            }
//            System.out.println("max = " + max);
            results.add((int) Math.pow(2, 16) - max / 2);
        }
        return results;
    }

    public static void calculateAllNonlinearlities(String finalFileName, String fileName, int n) {
        reWrite(finalFileName, calculateNonlinearity(fileName, n));

    }

    public static void deleteLastColumn(String fileName) {
        File inputFile = new File(fileName);
        String tempFileName = "Results/BooleanFunction1/temp.txt";
        File outputFile = new File(tempFileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(outputFile);
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                String[] temp = buffer.split(" ");
                for (int i = 0; i < temp.length - 1; i++) {
                    writer.write(temp[i] + " ");
                }
                writer.write("\n");
            }
            reader.close();
            writer.close();
            inputFile.delete();
            boolean t = outputFile.renameTo(inputFile);
            System.out.println(t);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int calculatePropagationRate(int i) {
        int result = 0;
        for (int j = 0; j < 131072; j++) {
            //f(x)
            String temp = (arrayList.get(j));

            //set x + ei
            String vector;
            if (getBinaryValue(j, 17).charAt(i) == '1') {
                vector = replace(getBinaryValue(j, 17), i, '0');
            } else {
                vector = replace(getBinaryValue(j, 17), i, '1');
            }

            //f(x) + f(x+ei)
            temp = additionByMod(temp, arrayList.get(Integer.parseInt(vector, 2)));

            result += Integer.parseInt(temp, 2);
        }
        return result;
    }

    public ArrayList<Integer> calculateAllPropagationRates() {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            results.add(calculatePropagationRate(i));
        }
        return results;
    }

    public static void calculateAllPropagationRatesForAllFunctions(String sourceFileName, String destinationFileName) {
        try {
            FileWriter writer = new FileWriter(destinationFileName);
            for (int i = 0; i < n; i++) {
                StringBuilder temp = new StringBuilder(sourceFileName);
                temp.append((i + 1) + ".txt");
                CoordinateFunction coordinateFunction = new CoordinateFunction(temp.toString());
                writer.write("F[" + (i + 1) + "]: ");
                for (int k : coordinateFunction.calculateAllPropagationRates()) {
                    writer.write(k + " ");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void calculateAllRelativeDeviations(String fileName, String destination) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String buffer;
            int count = 0;
            while (((buffer = reader.readLine()) != null) && (count != n)) {
                count++;
                ArrayList<String> arrayList = new ArrayList<>();
                int k = 0;
                for (String s : buffer.trim().split(" ")) {
                    if (k != 0) {
                        arrayList.add(String.format("%.3f", 100 * calculateRelativeDeviation(Integer.parseInt(s))));
                    }
                    k++;
                }
                arrayList.add(0, "F[" + count + "]:");
                appending(destination, arrayList);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static double calculateRelativeDeviation(int i) {
        return Math.abs(i - Math.pow(2, n - 1)) / (Math.pow(2, n - 1));
    }

    public static void appending(String fileName, ArrayList arrayList) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            for (int i = 0; i < arrayList.size(); i++) {
                writer.write(arrayList.get(i) + " ");
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getWeight(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                result++;
            }
        }
        return result;
    }

    public static int getWeight(int i) {
        return getWeight(Integer.toBinaryString(i));
    }


    public static int getCorrelationImmunityLevel(String fileName) {
        CoordinateFunction coordinateFunction = new CoordinateFunction(fileName, 2);
        return f(0, coordinateFunction.arrayList);
    }

    private static int f(int i, ArrayList<String> arrayList) {
        for (int k = 0; k < 131072; k++) {
            if (getWeight(k) == (i + 1)) {
                if (!arrayList.get(k).equals("0")) {
                    return i;
                }
            }
        }
        return f(i + 1, arrayList);
    }

    public static void calculateAllCorrelationImmunityLevel(String fileName, String destination, int n) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            StringBuilder temp = new StringBuilder(fileName);
            temp.append(i + ".txt");
            results.add(getCorrelationImmunityLevel(temp.toString()));
        }
        try (FileWriter writer = new FileWriter(destination)) {
            int count = 1;
            for (int i : results) {
                writer.write("Correlation Immunity Level [" + count + "] = " + i + "\n");
                count++;
            }
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
//            coordinateFunction.calculateAlgebraicNormalForm();
//        }
//        fileName = "Results/BooleanFunction2/CoordinateFunction";
//        for (int i = 1; i <= 17; i++) {
//            StringBuilder s = new StringBuilder(fileName);
//            s.append(i);
//            s.append(".txt");
//            CoordinateFunction coordinateFunction = new CoordinateFunction(s.toString());
//            coordinateFunction.calculateAlgebraicNormalForm();
//        }

//        CoordinateFunction coordinateFunction = new CoordinateFunction("Results/BooleanFunction1/CoordinateFunction1.txt");
//        System.out.println(coordinateFunction.arrayList);
//        System.out.println(coordinateFunction.getHadamardCoefficient());

        //Обчислення дисбалансу
//        calculateHadamardCoefficient("Results/BooleanFunction1/CoordinateFunction", 17, "Results/BooleanFunction1/Imbalance1.txt");
//        calculateHadamardCoefficient("Results/BooleanFunction2/CoordinateFunction", 17, "Results/BooleanFunction2/Imbalance2.txt");

        //Обчислення коефи уолша
//        AllFastConversionWalsh("Results/BooleanFunction1/CoordinateFunction", 17);
//        AllFastConversionWalsh("Results/BooleanFunction2/CoordinateFunction", 17);

//        System.out.println(calculateNonlinearity("Results/BooleanFunction1/CoordinateFunction", 17));
        //Обчислення нелінійностей
//        calculateAllNonlinearlities("Results/BooleanFunction1/Imbalance1.txt", "Results/BooleanFunction1/CoordinateFunction", 17);
//        calculateAllNonlinearlities("Results/BooleanFunction2/Imbalance2.txt", "Results/BooleanFunction2/CoordinateFunction", 17);

//        CoordinateFunction coordinateFunction = new CoordinateFunction("Results/BooleanFunction1/CoordinateFunction1.txt");
//        coordinateFunction.calculatePropagationRate(0);
//        System.out.println(coordinateFunction.calculateAllPropagationRates());

        //Обчислення коефів розповсюдження помилок
//        calculateAllPropagationRatesForAllFunctions("Results/BooleanFunction1/CoordinateFunction", "Results/BooleanFunction1/PropagationRates1.txt");
//        calculateAllPropagationRatesForAllFunctions("Results/BooleanFunction2/CoordinateFunction", "Results/BooleanFunction2/PropagationRates2.txt");

        //Обчислення відносного відхилення
//        calculateAllRelativeDeviations("Results/BooleanFunction1/PropagationRates1.txt", "Results/BooleanFunction1/PropagationRates1.txt");
//        calculateAllRelativeDeviations("Results/BooleanFunction2/PropagationRates2.txt", "Results/BooleanFunction2/PropagationRates2.txt");

//        System.out.println(getCorrelationImmunityLevel("Results/BooleanFunction1/CoordinateFunction1.txt"));
        //обчислення кореляційного імунітету
//        calculateAllCorrelationImmunityLevel("Results/BooleanFunction1/CoordinateFunction", "Results/BooleanFunction1/CorrelationImmunityLevel1.txt", 17);
//        calculateAllCorrelationImmunityLevel("Results/BooleanFunction2/CoordinateFunction", "Results/BooleanFunction2/CorrelationImmunityLevel2.txt", 17);


    }

}
