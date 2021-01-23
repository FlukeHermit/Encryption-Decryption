package encryptdecrypt;

import java.util.Scanner;

public class Main {    
    static char begin = 32;
    static char end = '~';
    static int size = 95;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String option = sc.nextLine();
        String text = sc.nextLine();
        char[] chars = text.toCharArray();
        int shift = sc.nextInt();

        if (option.equals("enc")) {
            encrypt(chars, shift);
        } else if (option.equals("dec")) {
            decrypt(chars, shift);        
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
