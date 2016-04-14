import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class PolynomialBasis {

    private static final int M = 180;

    private static ArrayList<Integer> generator;
    private static ArrayList<Integer> neutralOnAddition;
    private static ArrayList<Integer> neutralOnMultiplication;

    static {
        generator = new ArrayList<>();
        neutralOnAddition = new ArrayList<>();
        neutralOnMultiplication = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            generator.add(0);
            neutralOnAddition.add(0);
            neutralOnMultiplication.add(1);
        }
        generator.set(0, 1);
        generator.set(175, 1);
        generator.set(177, 1);
        generator.set(178, 1);
        generator.set(179, 1);
    }

    private ArrayList<Integer> array;

    public PolynomialBasis() {
        this.array = new ArrayList<>();
        for (int i = 0; i < M - 1; i++) {
            array.add(0);
        }
    }

    public PolynomialBasis(int size) {
        this.array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            array.add(0);
        }
    }

    public PolynomialBasis(ArrayList<Integer> array) {
        this.array = new ArrayList<>();
        this.array.addAll(array);
    }

    public PolynomialBasis(int[] array) {
        this.array = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            this.array.add(i);
        }
    }

    public PolynomialBasis(String[] array) {
        this.array = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            this.array.add(i);
        }
    }

    public PolynomialBasis(String s) {
        this.array = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            this.array.add(Character.getNumericValue(s.charAt(i)));
        }
    }

    public void clear(int value) {
        for (int i = 0; i < this.array.size(); i++) {
            this.array.set(i, value);
        }
    }

    public void setRandomValues() {
        Random random = new Random();
        for (int i = 0; i < array.size(); i++) {
            array.set(i, random.nextInt(2));
        }
    }

    public PolynomialBasis copy() {
        return new PolynomialBasis(this.array);
    }

    public void print() {
        for (int i = 0; i < this.array.size(); i++) {
            System.out.print(array.get(i));
        }
    }

    public void printHexNumber() {
    }

    public PolynomialBasis getGenerator() {
        return new PolynomialBasis(generator);
    }

    public PolynomialBasis getNeutralOnAddition() {
        return new PolynomialBasis(neutralOnAddition);
    }

    public PolynomialBasis getNeutralOnMultiplication() {
        return new PolynomialBasis(neutralOnMultiplication);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    public static PolynomialBasis addition(PolynomialBasis a, PolynomialBasis b) {
        PolynomialBasis c;
//        for (int i = 0; i < M; i++) {
//            c.array.set(i, a.array.get(i) + b.array.get(i) & 1);
//        }
        if (a.array.size() > b.array.size()) {
            c = new PolynomialBasis(a.array.size());
            int difference = a.array.size() - b.array.size();
            for (int i = b.array.size() - 1; i >= 0; i--) {
                c.array.set(difference + i, (a.array.get(difference + i) + b.array.get(i)) & 1);
            }
            for (int i = difference - 1; i >= 0; i--) {
                c.array.set(i, a.array.get(i) & 1);
            }
        } else {
            c = new PolynomialBasis(b.array.size());
            int difference = b.array.size() - a.array.size();
            for (int i = a.array.size() - 1; i >= 0; i--) {
                c.array.set(difference + i, (a.array.get(i) + b.array.get(difference + i)) & 1);
            }
            for (int i = difference - 1; i >= 0; i--) {
                c.array.set(i, b.array.get(i) & 1);
            }
        }
        return c;
    }

    public static int cmp(PolynomialBasis A, PolynomialBasis B) {
        while (A.array.get(0) == 0 && A.array.size() != 1) {
            A.array.remove(0);
        }
        while (B.array.get(0) == 0 && B.array.size() != 1) {
            B.array.remove(0);
        }

        if (A.array.size() > B.array.size()) {
            return 1;
        } else if (A.array.size() < B.array.size()) {
            return -1;
        } else {
            int i = 0;
            while (A.array.get(i) == B.array.get(i)) {
                i++;
                if (i == A.array.size()) {
                    break;
                }
            }
            if (i == A.array.size()) {
                return 0;
            } else if (A.array.get(i) > B.array.get(i)) {
                return 1;
            } else {
                return -1;
            }
        }
//        int k1, k2;
//        if (a.array.size() == b.array.size()) {
//            k1 = a.getFirstOne();
//            k2 = b.getFirstOne();
//        } else if (a.array.size() > b.array.size()) {
//            int difference = a.array.size() - b.array.size();
//            k1 = a.getFirstOne();
//            k2 = b.getFirstOne() + difference;
//        } else {
//            int difference = b.array.size() - a.array.size();
//            k1 = a.getFirstOne() + difference;
//            k2 = b.getFirstOne();
//        }
//        if (k1 == k2) {
//            return 0;
//        } else if (k1 > k2) {
//            return -1;
//        } else {
//            return 1;
//        }
    }

    public void landslide() {
        this.array.add(0);
    }

    public int getFirstOne() {
        int k = 0;
        while (true) {
            if (k == (this.array.size() - 1)) {
                return -1;
            }
            if (this.array.get(k) == 1) {
                return k;
            }
            k++;
        }
    }

    public static PolynomialBasis sub(PolynomialBasis a, PolynomialBasis b) {
        PolynomialBasis c;
        if (a.array.size() > b.array.size()) {
            c = new PolynomialBasis(a.array.size());
            int difference = a.array.size() - b.array.size();
            for (int i = b.array.size() - 1; i >= 0; i--) {
                c.array.set(difference + i, (a.array.get(difference + i) ^ b.array.get(i)));
            }
            for (int i = difference - 1; i >= 0; i--) {
                c.array.set(i, a.array.get(i) ^ 0);
            }
        } else {
            c = new PolynomialBasis(b.array.size());
            int difference = b.array.size() - a.array.size();
            for (int i = a.array.size() - 1; i >= 0; i--) {
                c.array.set(difference + i, (a.array.get(i) ^ b.array.get(difference + i)));
            }
            for (int i = difference - 1; i >= 0; i--) {
                c.array.set(i, b.array.get(i) ^ 0);
            }
        }

        while (c.array.get(0) == 0 && c.array.size() > generator.size()) {
            c.array.remove(0);
        }

        return c;
    }

    public static PolynomialBasis mod(PolynomialBasis a) {
        PolynomialBasis result = a.copy();
        PolynomialBasis g = new PolynomialBasis(generator);
        PolynomialBasis temp;
        while (PolynomialBasis.cmp(result, g) == 1) {
            int k = result.array.size() - g.array.size();
            temp  = g.copy();
            for (int i = 0; i < k; i++) {
                temp.landslide();
            }
            result = PolynomialBasis.sub(result, temp);
            temp = null;
        }
        return result;
    }

    public static PolynomialBasis multiplication(PolynomialBasis a, PolynomialBasis b) {
        PolynomialBasis result = new PolynomialBasis(a.array.size() + b.array.size() + 1);
//        for (int i = 0; i < b.array.size(); i++) {
        for (int i = b.array.size() - 1; i >= 0 ; i--) {
            if (b.array.get(i) != 0) {
                PolynomialBasis temp = a.copy();
                for (int j = 0; j < b.array.size() - i - 1; j++) {
                    temp.landslide();
                }
                result = PolynomialBasis.addition(result, temp);
            }
        }

        while (result.array.get(0) == 0 && result.array.size() > generator.size()) {
            result.array.remove(0);
        }

        result = PolynomialBasis.mod(result);

        return result;
    }

    public static PolynomialBasis square(PolynomialBasis a) {
        PolynomialBasis result = a.copy();
        for (int i = 0; i < 2 * a.array.size(); i++) {
            result.array.add(i+1, 0);
            i++;
        }
        result.array.remove(result.array.size() - 1);
        result = PolynomialBasis.mod(result);
        return result;
    }

    public static PolynomialBasis power(PolynomialBasis a, PolynomialBasis n) {
        PolynomialBasis result = new PolynomialBasis(1);
        result.clear(1);
        for (int i = n.array.size() - 1; i >= 0; i--) {
            if (n.array.get(i) == 1) {
                result = PolynomialBasis.multiplication(result, a);
            }
            a = PolynomialBasis.multiplication(a, a);
        }
        return result;
    }

    public static PolynomialBasis getReverse(PolynomialBasis a) {
        BigInteger i = new BigInteger("2");
        i = i.pow(M - 1);
        i = i.subtract(new BigInteger("2"));
        String s = i.toString(2);
        PolynomialBasis temp = new PolynomialBasis(s);
        return PolynomialBasis.power(a, temp);
    }

    public static PolynomialBasis getTrace(PolynomialBasis a) {
        PolynomialBasis result = new PolynomialBasis(1);
        result.clear(1);

        PolynomialBasis temp = a.copy();
        result = PolynomialBasis.addition(result, temp);
        for (int i = 1; i < M - 1; i++) {
            temp = square(temp);
            result = PolynomialBasis.addition(result, temp);
        }
        return result;
    }

    public static PolynomialBasis trace(PolynomialBasis a) {
        PolynomialBasis result = new PolynomialBasis(1);
        result.clear(1);

        for (int i = 0; i < M - 1; i++) {
            BigInteger j = new BigInteger("2");
            j = j.pow(i);
            String s = j.toString(2);
            PolynomialBasis temp = new PolynomialBasis(s);
            temp = PolynomialBasis.power(a, temp);
            result = PolynomialBasis.addition(result, temp);
        }
        return result;
    }

}
