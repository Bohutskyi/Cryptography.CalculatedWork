import threadStream.ReaderThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class CoordinateFunction {

    private CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public CoordinateFunction(String fileName) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(131072);
        Thread thread = new Thread(new ReaderThread(queue, fileName));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        arrayList = new CopyOnWriteArrayList<>(queue);
        queue = null;
    }

    public void calculateAlgebraicNormalForm() {
//        CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>(arrayList);
//        CopyOnWriteArrayList<String> buffer = new CopyOnWriteArrayList<>(arrayList);
//        for (int i = 0; i < 17; i++) {
//            for (int j = 0; j < 65536; j++) {
//                String u1, u2;
//                u1 = result.get(i);
//                StringBuilder temp = new StringBuilder();
//                temp.append(Integer.toBinaryString(i));
//                temp.insert(i, )
//            }
//        }
        BlockingQueue<String> result = new ArrayBlockingQueue<>(arrayList.size());
        CopyOnWriteArrayList<String> buffer = new CopyOnWriteArrayList<>(arrayList);
//        CopyOnWriteArrayList<String> previousBuffer = new CopyOnWriteArrayList<>(buffer);
        try {
            result.put(buffer.get(0));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 1; i < arrayList.size(); i++) {
            String temp;
            for (int j = arrayList.size() - 1 - i; j >= 0; j--) {
                temp = buffer.get(j);

                buffer.set(j, );
            }
        }
    }

    public static void main(String[] args) {
        CoordinateFunction coordinateFunction = new CoordinateFunction("Results/BooleanFunction1/CoordinateFunction1.txt");
        coordinateFunction.calculateAlgebraicNormalForm();

    }

}
