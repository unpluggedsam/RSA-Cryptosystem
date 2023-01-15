import java.math.BigInteger;
import java.util.Random;

public class Key {

    private final Pair privateKey;

    private final Pair publicKey;


    public Key() {

        Pair largePrimeNumbers = generateLargePrimeNumbers();

        BigInteger n = largePrimeNumbers.getA().multiply(largePrimeNumbers.getB());
        BigInteger r = generateAlmostLargeAsN(largePrimeNumbers);
        BigInteger e = computeRelativelyPrimeNumber(r);
        BigInteger d = e.modInverse(r);

        System.out.println(e);
        System.out.println(d);
        System.out.println(n);

        publicKey = new Pair(e, n);
        privateKey = new Pair(d, n);

    }

    public int decrypt(BigInteger x) {
        return pow(x, privateKey.getA()).mod(privateKey.getB()).intValue();
    }

    public BigInteger encrypt(int x) {
        return pow(BigInteger.valueOf(x), publicKey.getA()).mod(publicKey.getB());
    }


    BigInteger pow(BigInteger base, BigInteger exponent) {
        BigInteger result = BigInteger.ONE;
        while (exponent.signum() > 0) {
            if (exponent.testBit(0)) result = result.multiply(base);
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    private Pair generateLargePrimeNumbers() {
        return new Pair(BigInteger.probablePrime(8, new Random()), BigInteger.probablePrime(8, new Random()));
    }

    private BigInteger generateAlmostLargeAsN(Pair pair) {
        return (pair.getA().subtract(BigInteger.ONE)).multiply(pair.getB().subtract(BigInteger.ONE));
    }

    private BigInteger computeRelativelyPrimeNumber(BigInteger r) {

        BigInteger e = BigInteger.valueOf(3);

        while (!r.gcd(e).equals(BigInteger.ONE)) {
            e = e.nextProbablePrime();
        }

        return e;
    }

}
