package encryptdecrypt;

import java.util.Scanner;

public class Main {
    static char begin = 32;
    static char end = '~';
    static int size = 95;
    static String option = "enc";
    static String shift = "0";
    static String text = "";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < args.length; i += 2) {
            if (!args[i].startsWith("-") || args[i + 1].startsWith("-")) {
                System.out.println("Error: Illegal Arguments");
                break;
            }

            switch (args[i]) {
                case "-mode":
                    option = args[i+1];
                    break;
                case "-key":
                    shift = args[i+1];
                    break;
                case "-data":
                    text = args[i+1];
                    break;
            }

        }

        char[] chars = text.toCharArray();

        if (option.equals("enc")) {
            encrypt(chars, Integer.valueOf(shift));
        } else if (option.equals("dec")) {
            decrypt(chars, Integer.valueOf(shift));
        }

    }

    public static void encrypt(char[] chars, int shift) {
        for (char letter : chars) {
            if (letter >= begin && letter <= end) {
                char shiftItem = (char) (((letter - begin + shift) % size) + begin);
                System.out.print(shiftItem);
            }
        }
    }

    public static void decrypt(char[] chars, int shift) {
        for (char letter : chars) {
            if (letter >= begin && letter <= end) {
                char shiftItem = (char) (((letter - begin - shift) % size) + begin);
                System.out.print(shiftItem);
            }
        }
    }
}
