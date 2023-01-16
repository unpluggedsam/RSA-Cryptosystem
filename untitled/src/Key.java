import java.math.BigInteger;
import java.security.SecureRandom;

public class Key {

    private final BigInteger privateKey;
    private final BigInteger publicKey;
    private final BigInteger modulus;


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

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    private Pair generateLargePrimeNumbers() {
        return new Pair(BigInteger.probablePrime(1024, new SecureRandom()), BigInteger.probablePrime(1024, new SecureRandom()));
    }

    private BigInteger generateAlmostLargeAsN(Pair pair) {
        return (pair.getA().subtract(BigInteger.ONE).multiply(pair.getB().subtract(BigInteger.ONE)));
    }

    private BigInteger computeRelativelyPrimeNumber(BigInteger r) {

        BigInteger e = BigInteger.valueOf(2);

        while (!r.gcd(e).equals(BigInteger.ONE) && e.compareTo(r) < 0) {
            e = e.nextProbablePrime();
        }

        return e;
    }

}
