import convert.Convert;
import threadStream.Actionable;
import threadStream.ReaderThread;
import threadStream.WriterThread;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class BooleanFunction {

    private static final int n = 17;
    //    private static final int n = 16;
    private static final int MAX = 131072;
//    private static final int MAX = 65536;

    private final int power;
    private String fileName;

    public BooleanFunction(int power, String fileName) {
        this.power = power;
        this.fileName = fileName;
    }

    //Обчислення таблиці істиності
    //f(x) = x ^ power mod p(x)
    public void calculateTruthTable() {
        new Thread(new WriterThread((() -> {
            BlockingQueue<String> queue = new ArrayBlockingQueue<>(MAX);
            Thread thread = new Thread(new ReaderThread(queue, "Results/states.txt"));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            ArrayList<Integer> tempPower = new ArrayList<>();
            for (String t : Integer.toBinaryString(power).split("")) {
                tempPower.add(Integer.parseInt(t));
            }
            BlockingQueue<String> results = new ArrayBlockingQueue<>(MAX);
            for (String s : queue) {
                try {
                    State state = new State(queue.take());
                    state = State.power(state, tempPower);
                    StringBuilder temp = new StringBuilder(state.toString());
                    while (temp.length() < n) {
                        temp.insert(0, '0');
                    }
                    results.add(s + " " + temp.toString());
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            return results;
        }), fileName)).start();
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
        for (int i = 0; i < MAX; i++) {
            String u1;
            if (CoordinateFunction.getBinaryValue(i, BooleanFunction.n).charAt(n) == '1') {
                u1 = CoordinateFunction.replace(CoordinateFunction.getBinaryValue(i, BooleanFunction.n), n, '0');
            } else {
                u1 = CoordinateFunction.replace(CoordinateFunction.getBinaryValue(i, BooleanFunction.n), n, '1');
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

    private static String Derivative(int x, int a, ArrayList<String> arrayList) {
        String temp = arrayList.get(x);
        String temp2 = arrayList.get(x ^ a);
        return CoordinateFunction.additionByMod(temp, temp2);
    }

    public void maxDifferentialProbability(String fileName, String destination) {
        ArrayList<String> arrayList = new CoordinateFunction(fileName, 1).getArrayList();
        int[] results = new int[MAX];
        for (int i = 0; i < MAX; i++) {
            results[i] = 0;
        }
        ExecutorService executor = Executors.newFixedThreadPool(25);
        for (int a = 1; a < MAX; a++) {
//        for (int a = 1; a < 1000; a++) {
            executor.submit(new Task(a, results, arrayList));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Done");
        try (FileWriter writer = new FileWriter(destination)) {
            for (int i = 0; i < MAX; i++) {
                writer.write(CoordinateFunction.getBinaryValue(i, n) + " " + results[i] + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
//        calculateAllPropagationRates("Results/Vanya special/truthTable2.txt", "Results/Vanya special/GeneralAnalysis2.txt", 16);

        //Відносне відхилення
//        calculateRelativeDeviation("Results/GeneralAnalysis1.txt");
//        calculateRelativeDeviation("Results/GeneralAnalysis2.txt");

//        maxDifferentialProbability("Results/truthTable1.txt");
        BooleanFunction booleanFunction = new BooleanFunction(n, "Results/truthTable1.txt");
        booleanFunction.maxDifferentialProbability("Results/truthTable1.txt", "Results/DifferentialProbability1111.txt");

    }


    private class Task implements Runnable {
        private int a;
        private int[] map;
        private ArrayList<String> arrayList;

        Task(int a, int[] map, ArrayList<String> arrayList) {
            this.a = a;
            this.map = map;
            this.arrayList = arrayList;
        }

        @Override
        public void run() {
            if (a % 1000 == 0) {
                System.out.println("a = " + a);
            }
            for (int x = 0; x < MAX; x++) {
                map[Integer.parseInt(Derivative(x, a, arrayList), 2)]++;
            }
        }
    }
}
