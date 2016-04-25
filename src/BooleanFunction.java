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


            return queue;
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

    public static void calculatePropagationRates(String fileName) {

    }

    public static void main(String[] args) {
        BooleanFunction function = new BooleanFunction(131070, "Results/truthTable3.txt");
        function.calculateTruthTable();
    }

}
