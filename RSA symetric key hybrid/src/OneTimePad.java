import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OneTimePad {

    enum KEY_SIZE {
        ONE_HUNDRED_TWENTY_EIGHT(128), ONE_HUNDRED_NINETY_SIX(196), TWO_HUNDRED_FIFTY_SIX(256);

        final int size;

        KEY_SIZE(int size) {
            this.size = size;
        }

        int getSize() {
            return size;
        }
    }

    public int[] encryptString(String value, int[] key) {
        int[] binary = convertStringToBinary(value);
        List<int[]> blockBits = blockBinaryBits(binary);
        return encryptBlockBits(blockBits, key, binary.length);
    }

    public String decryptBinary(int[] binary, int[] key) {
        List<int[]> blockBits = blockBinaryBits(binary);
        int[] decryptedBits = decryptBlockBits(blockBits, key, binary.length);
        return convertBinaryToString(decryptedBits);
    }

    private int[] convertStringToBinary(String value) {

        StringBuilder strBuilder = new StringBuilder();
        char[] chars = value.toCharArray();
        for (char aChar : chars) {
            strBuilder.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")
            );
        }

        String str = strBuilder.toString();
        int[] result = new int[str.length()];

        for (int i = 0; i < str.length(); i++) {
            result[i] = str.charAt(i) - '0';
        }

        return result;
    }

    /**
     * Decrypts Cipher Block Chaining. First, it decrypts the block using operation D, and then
     * decrypts that decrypted block using the cipher text previous to that block.
     */
    private int[] decryptBlockBits(List<int[]> blockBits, int[] key, int size) {

        List<Integer> result = new ArrayList<>(size);

        result.addAll(Arrays.stream(XORBinary(blockBits.get(0), key)).boxed().toList());

        for (int i = 1; i < blockBits.size(); i++) {
            result.addAll(Arrays.stream(XORBinary(XORBinary(blockBits.get(i), key), blockBits.get(i - 1))).boxed().toList());
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }


    /**
     * Cipher Block Chaining. Essentially, if the operation to encrypt is E, then each block is encrypted with both E and the previous block.
     */
    private int[] encryptBlockBits(List<int[]> blockBits, int[] key, int size) {

        List<int[]> encryptedBlockBits = new ArrayList<>(size);
        List<Integer> result = new ArrayList<>(size);

        encryptedBlockBits.add(XORBinary(blockBits.get(0), key));
        result.addAll(Arrays.stream(XORBinary(blockBits.get(0), key)).boxed().toList());

        for (int i = 1; i < blockBits.size(); i++) {
            encryptedBlockBits.add(XORBinary(XORBinary(blockBits.get(i), encryptedBlockBits.get(i - 1)), key));
            result.addAll(Arrays.stream(XORBinary(XORBinary(blockBits.get(i), encryptedBlockBits.get(i - 1)), key)).boxed().toList());
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * XOR operation.
     * 0 ⊕ 0 = 0
     * 0 ⊕ 1 = 1
     * 1 ⊕ 0 = 1
     * 1 ⊕ 1 = 0
     * essentially  x ⊕ 0 = x
     * and x ⊕ 1 = opposite of x
     * ^ is the XOR operator
     **/
    private int[] XORBinary(int[] binary, int[] key) {

        int[] result = new int[binary.length];

        for (int z = 0; z < binary.length; z++) {
            result[z] = binary[z] ^ key[z];
        }

        return result;
    }

    /**
     * Chops up the binary into a bunch of blocks,
     * this makes it so the key can be reapplied.
     * If the binary does not reach the 128 block chunk
     * then a smaller array is created.
     */
    private List<int[]> blockBinaryBits(int[] binary) {
        List<int[]> blockBits = new ArrayList<>();
        for (int i = 0; i < binary.length; i += 128) {
            int size = Math.min(binary.length - i, 128);
            int[] block = new int[size];
            System.arraycopy(binary, i, block, 0, size);
            blockBits.add(block);
        }
        return blockBits;
    }

    private String convertBinaryToString(int[] binaryArray) {
        String binary = Arrays.stream(binaryArray)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());


        StringBuilder sb = new StringBuilder();

        Arrays.stream(
                binary.split("(?<=\\G.{8})")
        ).forEach(s ->
                sb.append((char) Integer.parseInt(s, 2))
        );

        return sb.toString();
    }


    public int[] generateKey(KEY_SIZE keySize) {

        SecureRandom random = new SecureRandom();

        int[] key = new int[keySize.getSize()];

        for (int i = 0; i < keySize.getSize(); i++) {
            key[i] = random.nextInt(2);
        }
        return key;
    }

}