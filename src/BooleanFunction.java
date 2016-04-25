import convert.Convert;
import threadStream.Actionable;
import threadStream.ReaderThread;
import threadStream.WriterThread;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BooleanFunction {

    private static final int n = 17;

    private final int power;
    private String fileName;
    private static final int m = 17;

    public BooleanFunction(int power, String fileName) {
        this.power = power;
        this.fileName = fileName;
    }

    //Обчислення таблиці істиності
    //f(x) = x ^ power mod p(x)
    //?????
    public void calculateTruthTable() {
        new Thread(new WriterThread(( () -> {
            BlockingQueue<String> queue = new ArrayBlockingQueue<String>(131072);
//            CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<String>();
            Thread thread = new Thread(new ReaderThread(queue, "Results/states.txt"));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
//            System.out.println(queue);

            ArrayList<Integer> tempPower = new ArrayList<>();
            for (String t : Integer.toBinaryString(power).split("")) {
                tempPower.add(Integer.parseInt(t));
            }
//            System.out.println(tempPower);
            BlockingQueue<String> results = new ArrayBlockingQueue<>(131072);
            for (String s : queue) {
                try {
                    State state = new State(queue.take());
                    state = State.power(state, tempPower);
                    StringBuilder temp = new StringBuilder(state.toString());
                    while (temp.length() < 17) {
                        temp.insert(0, '0');
                    }
                    results.add(s + " " + temp.toString());
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }



//            return queue;
            return results;
//            return arrayList;
//            return null;
        } ), fileName)).start();
    }

    private void write(String s, FileWriter fileWriter) {
        try {
            fileWriter.write(s + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /****************************************************************************************************************/

    public static int calculatePropagationRate(String fileName, int n) {
        ArrayList<String> arrayList = new CoordinateFunction(fileName, 1).getArrayList();
        int result = 0;
        for (int i = 0; i < 131072; i++) {
            String u1;
//            System.out.println(CoordinateFunction.getBinaryValue(i, 17));
//            System.out.println("n = " + n);
            if (CoordinateFunction.getBinaryValue(i, 17).charAt(n) == '1') {
                u1 = CoordinateFunction.replace(CoordinateFunction.getBinaryValue(i, 17), n, '0');
            } else {
                u1 = CoordinateFunction.replace(CoordinateFunction.getBinaryValue(i, 17), n, '1');
            }
            String temp = CoordinateFunction.additionByMod(arrayList.get(i), arrayList.get(Integer.parseInt(u1, 2)));
            result += CoordinateFunction.getWeight(temp);
        }
        return result;
    }

    public static void calculateAllPropagationRates(String source, String destination, int n) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            results.add(calculatePropagationRate(source, i));
        }
        try (FileWriter writer = new FileWriter(destination)) {
            int count = 1;
            for (int i : results) {
                writer.write("[" + count + "] = " + i + "\n");
                count++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void calculateRelativeDeviation(String fileName) {
        ArrayList<Double> temp = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                temp.add(Double.parseDouble(buffer.trim().split(" ")[2]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < temp.size(); i++) {
            //String.format("%.3f", 100 * calculateRelativeDeviation(Integer.parseInt(s)))
            temp.set(i, calculateSingleRelativeDeviation(temp.get(i)));
        }
        ArrayList<String> results = new ArrayList<>();
        while (temp.size() != 0) {
            results.add(String.format("%.3f", 100 * temp.remove(0)));
        }
        CoordinateFunction.reWrite(fileName, results);
    }

    public static double calculateSingleRelativeDeviation(double d) {
        return Math.abs(d - n * Math.pow(2, n - 1)) / (n * Math.pow(2, n - 1));
    }

    public static boolean KroneckerDelta(String s1, String s2) {
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static String Derivative(String x, String a, ArrayList<String> arrayList) {
        String temp = arrayList.get(Integer.parseInt(x, 2));
        String temp2 = arrayList.get(Integer.parseInt(CoordinateFunction.additionByMod(x, a), 2));
        return CoordinateFunction.additionByMod(temp, temp2);
    }

    public static double calculateDifferentialProbability(String a, String b, ArrayList<String> arrayList) {
        int result = 0;
        for (int i = 0; i < 131072; i++) {
            if (KroneckerDelta(Derivative(CoordinateFunction.getBinaryValue(i, 17), a, arrayList), b)) {
                result++;
            }
        }
        return result * 1. / Math.pow(2, n);
    }

    public static double maxDifferentialProbability(String fileName) {
        CoordinateFunction coordinateFunction = new CoordinateFunction(fileName, 1);
        double max = 0;
        for (int i = 0; i < 131072; i++) {
            System.out.println("i = " + i);
            for (int j = 0; j < 131072; j++) {
                double temp = calculateDifferentialProbability(CoordinateFunction.getBinaryValue(i, 17), CoordinateFunction.getBinaryValue(j, 17), coordinateFunction.getArrayList());
                if (temp > max) {
                    max = temp;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        //обчислення таблиць істиності
//        BooleanFunction function = new BooleanFunction(131070, "Results/truthTable1.txt");
//        function.calculateTruthTable();
//        function = new BooleanFunction(131069, "Results/truthTable2.txt");
//        function.calculateTruthTable();


//        System.out.println(calculatePropagationRate("Results/truthTable1.txt", 0));
//        System.out.println(calculatePropagationRate("Results/truthTable2.txt", 0));
        //Розповсюдження помилок
//        calculateAllPropagationRates("Results/truthTable1.txt", "Results/GeneralAnalysis1.txt", 17);
//        calculateAllPropagationRates("Results/truthTable2.txt", "Results/GeneralAnalysis2.txt", 17);

        //Відносне відхилення
//        calculateRelativeDeviation("Results/GeneralAnalysis1.txt");
//        calculateRelativeDeviation("Results/GeneralAnalysis2.txt");

        //

        maxDifferentialProbability("Results/truthTable1.txt");


    }

}
