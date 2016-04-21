import threadStream.ReaderThread;
import threadStream.WriterThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Даний клас читає з файлу багатовимірну БФ і розбиває її на координатні фенції
 * Кожна координатна функція у свою чергу записується в окреми файл
 * */
public class CoordinateFunctionRecognizer {

    public static void readAllCoordinateFunctions(String sourceFileName, String destinationFileName, int n) {
        for (int i = 0; i < n; i++) {
            StringBuilder temp = new StringBuilder();
            temp.append(destinationFileName);
            temp.append(i + 1);
            temp.append(".txt");
            readCoordinateFunction(sourceFileName, temp.toString(), n, i);
        }
    }

    public static void readCoordinateFunction(String sourceFileName, String destinationFileName, int n, int i) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(131072);
        ReaderThread readerThread = new ReaderThread(queue, sourceFileName);
        Thread thread = new Thread(readerThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        new Thread(new WriterThread( () -> {
            BlockingQueue<String> temp = new ArrayBlockingQueue<String>(131072);
            for (int j = 0; j < 131072; j++) {
                try {
                    char c = queue.take().charAt(i + 18);
                    temp.add(c + "");
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            return temp;
        }, destinationFileName)).start();
    }

    public static void main(String[] args) {
        readAllCoordinateFunctions("Results/truthTable1.txt", "Results/BooleanFunction1/CoordinateFunction", 17);
        readAllCoordinateFunctions("Results/truthTable2.txt", "Results/BooleanFunction2/CoordinateFunction", 17);
    }

}
