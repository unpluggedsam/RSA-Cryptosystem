import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        String text = "I LOVE eating some stuff occasionally and having friends.";

        Computer computer1 = new Computer();
        Computer computer2 = new Computer();

        computer1.sendMessage(computer2, text, OneTimePad.KEY_SIZE.ONE_HUNDRED_TWENTY_EIGHT);

       System.out.println(computer2.getRecentMessages().get(0));


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


}