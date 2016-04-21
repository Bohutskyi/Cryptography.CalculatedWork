package convert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Convert {

    public static BlockingQueue<String> convert(CopyOnWriteArrayList<String> arrayList) {
        BlockingQueue<String> result = new ArrayBlockingQueue<String>(arrayList.size());
        while (0 != arrayList.size()) {
            result.add(arrayList.remove(0));
        }
        return result;
    }

//    public static void main(String[] args) {
//        CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
//        arrayList.add("1");
//        arrayList.add("2");
//        arrayList.add("3");
//        arrayList.add("4");
//        arrayList.add("5");
//        System.out.println(arrayList);
//        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);
//        for (String s : convert(arrayList)) {
//            System.out.println(s);
//            try {
//                queue.put(s);
//            } catch (InterruptedException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        arrayList = new CopyOnWriteArrayList<>(queue);
//        System.out.println("arrayList");
//        System.out.println(arrayList);
//
//    }

}
