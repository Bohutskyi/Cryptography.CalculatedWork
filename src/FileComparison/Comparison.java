package FileComparison;

import java.util.ArrayList;

public class Comparison implements Runnable {
    private ArrayList<String> arrayList1, arrayList2;
    private boolean result = false;

    public Comparison() {
        this.arrayList1 = new ArrayList<>();
        this.arrayList2 = new ArrayList<>();
    }

    public void add1(String s) {
        arrayList1.add(s);
    }

    public void add2(String s) {
        arrayList2.add(s);
    }

    public ArrayList<String> getArrayList1() {
        return arrayList1;
    }

    public ArrayList<String> getArrayList2() {
        return arrayList2;
    }

    @Override
    public void run() {
//        while (!Thread.currentThread().isInterrupted()) {
        while (!result) {
//            System.out.println(arrayList1);
//            System.out.println(arrayList2);
            if (arrayList1.size() > 0 && arrayList2.size() > 0) {
                String temp = arrayList1.remove(0);
                if (! (temp.equals(arrayList2.remove(0)))) {
                    System.out.println("Not equals in " + temp);
                    result = true;
                }
            }
        }
    }
}
