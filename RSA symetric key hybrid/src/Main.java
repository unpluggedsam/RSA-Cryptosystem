import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        String text = "I LOVE eating some stuff occasionally and having friends.";

        Key key = new Key();

        OneTimePad pad = new OneTimePad();

        int[] binaryKey = pad.generateKey(OneTimePad.KEY_SIZE.ONE_HUNDRED_NINETY_SIX);

        System.out.println("Binary Key: ");
        printArray(binaryKey);

        int[] encryptedBinary = pad.encryptString(text, binaryKey);

        System.out.println(convertBinaryToString(encryptedBinary));

        BigInteger parsedInt = convertBinaryToDecimal(binaryKey);
        System.out.println("Decimal binary: " + parsedInt);
        BigInteger encryptedKey = cipher(parsedInt, key.getPublicKey(), key.getModulus());

        System.out.println("Encrypted key: " + encryptedKey);

        BigInteger decryptedKey = cipher(encryptedKey, key.getPrivateKey(), key.getModulus());
        System.out.println("Decrypted key: " + decryptedKey);

        int[] decryptedBinaryKey = convertDecimalToBinary(decryptedKey);
        System.out.println("Decrypted binary key: ");
        printArray(decryptedBinaryKey);

        String decryptedBinary = pad.decryptBinary(encryptedBinary, convertIntToArray(decryptedKey));
        System.out.println(decryptedBinary);

    }

    private static BigInteger convertBinaryToDecimal(int[] binary) {
        BigInteger decimal = BigInteger.ZERO;
        BigInteger two = BigInteger.TWO;
        for (int i = 0; i < binary.length; i++) {
            decimal = decimal.add(BigInteger.valueOf(binary[i]).multiply(two.pow(binary.length - i - 1)));
        }
        return decimal;
    }

    private static int[] convertDecimalToBinary(BigInteger decimal) {
        ArrayList<Integer> binaryList = new ArrayList<>();
        while (!decimal.equals(BigInteger.ZERO)) {
            BigInteger[] divideAndRemainder = decimal.divideAndRemainder(BigInteger.TWO);
            binaryList.add(divideAndRemainder[1].intValue());
            decimal = divideAndRemainder[0];
        }

        int[] binary = new int[binaryList.size()];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = binaryList.get(binary.length - 1 - i);
        }
        return binary;
    }

    private static int convertArrayToInt(int[] input) {
        int result = 0;
        for (int i = 0; i < input.length; i++) {
            result += input[i] * (int) Math.pow(10, input.length - i - 1);
        }
        return result;
    }

    public static int[] convertIntToArray(BigInteger input) {
        String inputString = Integer.toString(input.intValue());
        int[] result = new int[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            result[i] = inputString.charAt(i) - '0';
        }
        return result;
    }


    private static BigInteger cipher(BigInteger parsedInt, BigInteger publicKey, BigInteger modulus) {
        return parsedInt.modPow(publicKey, modulus);
    }

    private static String convertBinaryToString(int[] binaryArray) {
        String binary = Arrays.stream(binaryArray)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());


        StringBuilder sb = new StringBuilder(); // Some place to store the chars

        Arrays.stream( // Create a Stream
                binary.split("(?<=\\G.{8})") // Splits the input string into 8-char-sections (Since a char has 8 bits = 1 byte)
        ).forEach(s -> // Go through each 8-char-section...
                sb.append((char) Integer.parseInt(s, 2)) // ...and turn it into an int and then to a char
        );

        return sb.toString();
    }

    private static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}