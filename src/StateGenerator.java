import threadStream.WriterThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
* Даний клас генерує усі бітові вектори у лексикографічному порядку та записує їх у файл
* */

public class StateGenerator {

    public static void generate(int n, int length, String fileName) {
        new Thread( new WriterThread( () -> {
            BlockingQueue<String> queue = new ArrayBlockingQueue<>(n);
//            CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
            for (int i = 0; i < n; i++) {
                StringBuilder result = new StringBuilder();
                result.append(Integer.toBinaryString(i));
                while (result.length() < length) {
                    result.insert(0, '0');
                }
                try {
                    queue.put(result.toString());
//                    arrayList.add(result.toString());
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            return queue;
//            return arrayList;
        }, fileName)).start();
    }

    public static void main(String[] args) {
        generate(131072, 17, "Results/states.txt");
    }

}
