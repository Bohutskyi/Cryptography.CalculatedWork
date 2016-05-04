import java.util.ArrayList;

public class State {

    private static final int m = 18;
//    private static final int m = 17;
    private static final ArrayList<Integer> generator;

    private ArrayList<Integer> array;

    static {
        generator = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            generator.add(0);
        }
        generator.set(0, 1);
        generator.set(14, 1);
        generator.set(17, 1);
//        generator.set(0, 1);
//        generator.set(4, 1);
//        generator.set(13, 1);
//        generator.set(15, 1);
//        generator.set(16, 1);
    }

    public State(String s) {
        array = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            this.array.add(Character.getNumericValue(s.charAt(i)));
        }
    }

    public State(ArrayList<Integer> arrayList) {
        this.array = new ArrayList<>();
        for (int i : arrayList) {
            this.array.add(i);
        }
    }

    public State(int size) {
        this.array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            array.add(0);
        }
    }

    public static void print(State state) {
        for (int i : state.array) {
            System.out.print(i);
        }
    }

    public static void print(ArrayList<Integer> arrayList) {
        for (int i : arrayList) {
            System.out.print(i);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i : array) {
            result.append(i);
        }
        return result.toString();
    }

    //******************************************************************************************************************

//    public static State addition(State state1, State state2) {
//        State result = new State(0);
//        System.out.println(result.toString());
//        for (int i = 0; i < state1.array.size(); i++) {
//            result.array.add(state1.array.get(i) ^ state2.array.get(i));
//        }
//        return result;
//    }

//    public static State mod(State state) {
//
//    }
//
//    public static State power(State state, int power) {
//
//    }

    public static State addition(State a, State b) {
        State c;
        if (a.array.size() > b.array.size()) {
            c = new State(a.array.size());
            int difference = a.array.size() - b.array.size();
            for (int i = b.array.size() - 1; i >= 0; i--) {
                c.array.set(difference + i, (a.array.get(difference + i) + b.array.get(i)) & 1);
            }
            for (int i = difference - 1; i >= 0; i--) {
                c.array.set(i, a.array.get(i) & 1);
            }
        } else {
            c = new State(b.array.size());
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

    public State copy() {
        return new State(this.array);
    }

    public static int cmp(State A, State B) {
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
    }

    public void landslide() {
        this.array.add(0);
    }

    public static State sub(State a, State b) {
        State c;
        if (a.array.size() > b.array.size()) {
            c = new State(a.array.size());
            int difference = a.array.size() - b.array.size();
            for (int i = b.array.size() - 1; i >= 0; i--) {
                c.array.set(difference + i, (a.array.get(difference + i) ^ b.array.get(i)));
            }
            for (int i = difference - 1; i >= 0; i--) {
                c.array.set(i, a.array.get(i) ^ 0);
            }
        } else {
            c = new State(b.array.size());
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

    public static State mod(State a) {
        State result = a.copy();
        State g = new State(generator);
        State temp;
        while (State.cmp(result, g) == 1) {
            int k = result.array.size() - g.array.size();
            temp  = g.copy();
            for (int i = 0; i < k; i++) {
                temp.landslide();
            }
            result = State.sub(result, temp);
            temp = null;
        }
        return result;
    }

    public static State multiplication(State a, State b) {
        State result = new State(a.array.size() + b.array.size() + 1);
//        for (int i = 0; i < b.array.size(); i++) {
        for (int i = b.array.size() - 1; i >= 0 ; i--) {
            if (b.array.get(i) != 0) {
                State temp = a.copy();
                for (int j = 0; j < b.array.size() - i - 1; j++) {
                    temp.landslide();
                }
                result = State.addition(result, temp);
            }
        }

        while (result.array.get(0) == 0 && result.array.size() > generator.size()) {
            result.array.remove(0);
        }

        result = State.mod(result);

        return result;
    }

    public static State square(State a) {
        State result = a.copy();
        for (int i = 0; i < 2 * a.array.size(); i++) {
            result.array.add(i+1, 0);
            i++;
        }
        result.array.remove(result.array.size() - 1);
        result = State.mod(result);
        return result;
    }

    public void clear(int value) {
        for (int i = 0; i < this.array.size(); i++) {
            this.array.set(i, value);
        }
    }

    public static State power(State a, ArrayList<Integer> n) {
        State result = new State(1);
        result.clear(1);
        for (int i = n.size() - 1; i >= 0; i--) {
            if (n.get(i) == 1) {
                result = State.multiplication(result, a);
            }
            a = State.multiplication(a, a);
        }
//        result = mod(result);
//        while (cmp(result, new State(generator)) == -1) {
        while (result.array.size() >= generator.size()) {
//            System.out.println("result = " + result);
            result = sub(result, new State(generator));
//            System.out.println(result );
            while (result.array.get(0) == 0 && result.array.size() > 1) {
                result.array.remove(0);
            }
//            System.out.println(result);
        }
        return result;
    }

    //******************************************************************************************************************

    public static void main(String[] args) {
        State state = new State("10000000000000000");
        State state2 = new State("10001101010010010");
        print(state);
        print(State.addition(state, state2));
        System.out.println();
        print(generator);
        /**/
        System.out.println("--------------");
        System.out.println(cmp(new State("1011"), new State(generator)));
        System.out.println("--------------");
        System.out.println();
        state = new State("00101110010111000");
        int power = 131070;
        ArrayList<Integer> tempPower = new ArrayList<>();
        for (String t : Integer.toBinaryString(power).split("")) {
            tempPower.add(Integer.parseInt(t));
        }
        System.out.println(power(state, tempPower));
    }

}
