public class Vector {

    private byte[] vector;

    public Vector(String s) {
        this.vector = new byte[s.length()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (byte) (s.charAt(i) - 48);
        }
    }

    public Vector(int n) {
        this.vector = new byte[n];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (byte b : vector) {
            result.append(b);
        }
        return result.toString();
    }

    public void print() {
        for (byte b : vector) {
            System.out.print(b);
        }
    }

    public static Vector addition(Vector vector1, Vector vector2) {
        Vector result = new Vector(vector1.vector.length);
        for (int i = 0; i < result.vector.length; i++) {
            result.vector[i] = (byte) (vector1.vector[i] ^ vector2.vector[i]);
        }
        return result;
    }

}
