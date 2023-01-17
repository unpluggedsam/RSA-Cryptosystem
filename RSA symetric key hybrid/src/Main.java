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
}