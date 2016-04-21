import threadStream.ReaderThread;
import threadStream.WriterThread;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class BooleanFunction {

    private final int power;
    private String fileName;
    private static final int m = 17;

    public BooleanFunction(int power, String fileName) {
        this.power = power;
        this.fileName = fileName;
    }

    //Обчислення таблиці істиності
    //f(x) = x ^ power mod p(x)
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


            return queue;
//            return arrayList;
//            return null;
        } ), fileName)).start();
//        try (BufferedReader reader = new BufferedReader(new FileReader("Results/states.txt"))) {
//            String temp;
//
//            ArrayList<Integer> tempPower = new ArrayList<>();
//            for (String t : Integer.toBinaryString(power).split("")) {
//                tempPower.add(Integer.parseInt(t));
//            }
//
//            FileWriter writer = new FileWriter(fileName, false);
//            while ((temp = reader.readLine()) != null) {
//                State state = new State(temp);
//                state = State.power(state, tempPower);
//                StringBuilder result = new StringBuilder();
//                result.append(state.toString());
//                while (result.length() < m) {
//                    result.insert(0, '0');
//                }
//                write(temp + " " + result.toString(), writer);
//            }
//            writer.close();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }

    private void write(String s, FileWriter fileWriter) {
        try {
            fileWriter.write(s + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isBalanced() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String temp;
            while ((temp = reader.readLine()) != null) {
                if (searchState(temp.split(" ")[1]) != 1) {
                    System.out.println(temp.split(" ")[1]);
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private int searchState(String state) {
//        int result = 0;
//        try (BufferedReader threadStream = new BufferedReader(new FileReader(fileName))) {
//            String temp;
//            while ((temp = threadStream.readLine()) != null) {
//                for (String s : temp.split(" ")) {
//                    if (s.equals(state)) {
//                        result++;
//                    }
//                }
//            }
//            threadStream.close();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return result;
        int result = 0;
        try (LineNumberReader lineNumberReader = new LineNumberReader(new BufferedReader(new FileReader(fileName)))) {
            String s;
            while ((s = lineNumberReader.readLine()) != null) {
//                if (s.contains(state)) {
//                    result++;
//                }
                if (s.split(" ")[1].equals(state)) {
//                    System.out.println(lineNumberReader.getLineNumber());
                    result++;
                }
            }
            lineNumberReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        BooleanFunction function = new BooleanFunction(131070, "Results/truthTable3.txt");
        function.calculateTruthTable();

//        long time = - System.currentTimeMillis();
//        System.out.println(function.isBalanced());
//        time += System.currentTimeMillis();
//        System.out.println("time = " + time);


//        long time = - System.currentTimeMillis();
//        function.searchState("01110111101111000");
//        time += System.currentTimeMillis();
//        System.out.println("time = " + time);

//        System.out.println(function.searchState("01110111101111000"));
//        System.out.println(function.searchState("11111111111100111"));
//        System.out.println(function.searchState("11100110110110001"));
//        System.out.println(function.searchState("01000100101011010"));
//        System.out.println(function.searchState("11111111111110101"));
//        function.calculateTruthTable("Results/truthTable1.txt");
//        function = new BooleanFunction(131069);
//        function.calculateTruthTable("Results/truthTable2.txt");

    }

}
