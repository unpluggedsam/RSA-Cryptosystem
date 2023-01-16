import java.math.BigInteger;

public class Pair {
    private final BigInteger a;
    private final BigInteger b;

    public Pair(BigInteger a, BigInteger b) {
        this.a = a;
        this.b = b;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }
}
