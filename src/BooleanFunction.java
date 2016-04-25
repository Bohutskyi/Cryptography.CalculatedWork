import convert.Convert;
import threadStream.Actionable;
import threadStream.ReaderThread;
import threadStream.WriterThread;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
        for (int i = 0; i < arrayList.size(); i++) {
            String u0 = arrayList.get(i);
            String u1;
            if (CoordinateFunction.getBinaryValue(n, 17).charAt(n) == '1') {
                u1 = CoordinateFunction.replace(CoordinateFunction.getBinaryValue(i, 17), n, '0');
            } else {
                u1 = CoordinateFunction.replace(CoordinateFunction.getBinaryValue(i, 17), n, '1');
            }
//            System.out.println("u0 = " + u0);
//            String temp = CoordinateFunction.additionByMod(arrayList.get(i), arrayList.get(Integer.parseInt(u1, 2)));
            String temp = CoordinateFunction.additionByMod(u0, arrayList.get(Integer.parseInt(u1, 2)));
            result += CoordinateFunction.getWeight(temp);
        }
        return result;
    }

    public static void main(String[] args) {
        //обчислення таблиць істиності
//        BooleanFunction function = new BooleanFunction(131070, "Results/truthTable1.txt");
//        function.calculateTruthTable();
//        function = new BooleanFunction(131069, "Results/truthTable2.txt");
//        function.calculateTruthTable();

//        System.out.println(calculatePropagationRate("Results/temp.txt", 0));
//        System.out.println(calculatePropagationRate("Results/truthTable1.txt", 0));
//        System.out.println(calculatePropagationRate("Results/truthTable2.txt", 0));

    }

}
