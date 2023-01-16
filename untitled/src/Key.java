import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * The Key class is an implementation of the RSA encryption algorithm.
 * It generates large prime numbers, modulus and keys.
 */
public class Key {

    /**
     * privateKey is the private key used for decryption
     */
    private final BigInteger privateKey;
    /**
     * publicKey is the public key used for encryption
     */
    private final BigInteger publicKey;
    /**
     * modulus is used to encrypt and decrypt messages
     */
    private final BigInteger modulus;

    /**
     * Constructor for the Key class.
     * It generates large prime numbers, modulus and keys.
     */
    public Key() {
        Pair largePrimeNumbers = generateLargePrimeNumbers();
        BigInteger n = largePrimeNumbers.getA().multiply(largePrimeNumbers.getB());
        BigInteger r = generateAlmostLargeAsN(largePrimeNumbers);
        BigInteger e = computeRelativelyPrimeNumber(r);
        BigInteger d = e.modInverse(r);
        publicKey = e;
        privateKey = d;
        modulus = n;
    }

    /**
     * Returns the public key
     * @return publicKey
     */
    public BigInteger getPublicKey() {
        return publicKey;
    }

    /**
     * Returns the private key
     * @return privateKey
     */
    public BigInteger getPrivateKey() {
        return privateKey;
    }

    /**
     * Returns the modulus
     * @return modulus
     */
    public BigInteger getModulus() {
        return modulus;
    }

    /**
     * This method generates two large prime numbers
     * @return a pair of large prime numbers
     */
    private Pair generateLargePrimeNumbers() {
        return new Pair(BigInteger.probablePrime(1024, new SecureRandom()), BigInteger.probablePrime(1024, new SecureRandom()));
    }

    /**
     * This method generates a number almost as large as n. It uses the equation r = (p-1) * (q-1)
     * @param pair a pair of large prime numbers
     * @return a number almost as large as n
     */
    private BigInteger generateAlmostLargeAsN(Pair pair) {
        return (pair.getA().subtract(BigInteger.ONE).multiply(pair.getB().subtract(BigInteger.ONE)));
    }

    /**
     * This method computes a relatively prime number to the parameter r.
     * It starts with the number 2 and checks if it is relatively prime to r by checking if the greatest common denominator
     * between e and r is equal to 1. If it isn't, it checks the next probable prime number until a relatively prime number
     * is found.
     * @param r number to find a relatively prime number for
     * @return a relatively prime number to r
     */
    private BigInteger computeRelativelyPrimeNumber(BigInteger r) {
        BigInteger e = BigInteger.valueOf(2);
        while (!r.gcd(e).equals(BigInteger.ONE) && e.compareTo(r) < 0) {
            e = e.nextProbablePrime();
        }
        return e;
    }


}
