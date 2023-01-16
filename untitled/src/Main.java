import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        Key key = new Key();
        int x = 327;
        BigInteger encrypt = key.encrypt(x);


        System.out.println("Encrypted: " + encrypt);
        System.out.println("Decrypted: " + key.decrypt(encrypt));
    }
}