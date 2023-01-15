import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        Key key = new Key();
        int x = 29;
        BigInteger encrypt = key.encrypt(x);
        System.out.println(encrypt);
        System.out.println(key.decrypt(encrypt));
    }
}