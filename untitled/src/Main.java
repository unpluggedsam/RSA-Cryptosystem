import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        Key key = new Key();
        BigInteger plaintext = new BigInteger("12345");
        BigInteger ciphertext = encrypt(plaintext, key.getPublicKey());
        BigInteger decrypted = decrypt(ciphertext, key.getPrivateKey());

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Decrypted: " + decrypted);
    }

    public static BigInteger encrypt(BigInteger plaintext, Pair publicKey) {
        BigInteger e = publicKey.getA();
        BigInteger n = publicKey.getB();
        return plaintext.modPow(e, n);
    }

    public static BigInteger decrypt(BigInteger ciphertext, Pair privateKey) {
        BigInteger d = privateKey.getA();
        BigInteger n = privateKey.getB();
        return ciphertext.modPow(d, n);
    }

}