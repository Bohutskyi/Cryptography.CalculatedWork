package FileComparison;

public class Launch {

    public static void main(String[] args) {
        Comparison comparison = new Comparison();
        Thread thread1 = new Thread(comparison);
        Thread thread2 = new Thread(new ReadFile("Results/t1.txt", comparison, true));
        Thread thread3 = new Thread(new ReadFile("Results/t2.txt", comparison, false));
//        thread1.start();
        thread2.start();
        thread3.start();
        thread1.start();
    }

}
