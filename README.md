# RSA-Cryptosystem
Implementation of the RSA Cryptosystem

This repository contains an implementation of the RSA encryption algorithm in Java. The algorithm is implemented in the Key class, which generates large prime numbers, a modulus, and both private and public keys.

## How it works

 The process of this RSA algorithim implementation can be broken down into 9 steps. 

<ol>
  <li>The Key class is instantiated, which triggers the constructor.</li>
  <li>The constructor starts by generating two large prime numbers using the BigInteger.probablePrime() method and a SecureRandom object. The probablePrime() method generates a random BigInteger that is probably prime, with the specified bit length. The bit length is passed as an argument and in this case it is set to 1024. A new SecureRandom object is also created to provide a source of randomness when generating the prime numbers.</li>
  <li>The constructor then calculates the modulus, n, by multiplying the two large prime numbers together using the BigInteger.multiply() method. The modulus is used as a common factor in both the encryption and decryption process.</li>
  <li>The constructor next calculates the value of r, which is almost as large as n, by using the equation r = (p-1) * (q-1), where p and q are the two large prime numbers. The BigInteger.subtract() method is used to subtract 1 from each of the prime numbers, and the BigInteger.multiply() method is used to multiply these values together.</li>
  <li>The constructor then finds a relatively prime number to r by starting with the number 2 and checking if it is relatively prime to r by checking if the greatest common denominator (GCD) between e and r is equal to 1. This is done using the BigInteger.gcd() method. If the GCD is not equal to 1, meaning e is not relatively prime to r, it checks the next probable prime number using the BigInteger.nextProbablePrime() method until a relatively prime number is found.</li>
  <li>The constructor then calculates the public key, e, by using the BigInteger.modInverse() method with the private key and r. The modInverse() method returns the modular multiplicative inverse of this BigInteger.</li>
  <li>The constructor then sets the private key, public key, and modulus to the values that have been generated or calculated.</li>
  <li>The getPublicKey(), getPrivateKey(), and getModulus() methods can be called to retrieve the values of the public key, private key, and modulus respectively.</li>
  <li>After that, the public key can be used for encrypting a message, and the private key for decrypting it, by using the following formula: c = m^e mod n, where m is the original message, c is the encrypted message, e is the public key, and n is the modulus. To decrypt the message, the formula d = c^d mod n is used, where d is the private key.</li>
</ol>

Please note that the above implementation is not a full fledged RSA encryption implementation and should not be used in production. It is missing several important security considerations, such as padding and key validation.

## Sample Code

```Java
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
```


