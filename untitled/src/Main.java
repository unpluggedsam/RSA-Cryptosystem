import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        Key key = new Key();
        BigInteger plaintext = new BigInteger("12345");
        BigInteger ciphertext = cipher(plaintext, key.getPublicKey(), key.getModulus());
        BigInteger decrypted = cipher(ciphertext, key.getPrivateKey(), key.getModulus());

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Decrypted: " + decrypted);
    }

    public static BigInteger cipher(BigInteger plaintext, BigInteger key, BigInteger mod) {
        return plaintext.modPow(key, mod);
    }

}