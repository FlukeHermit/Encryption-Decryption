package encryptdecrypt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        char[] chars = text.toCharArray();
        int shift = sc.nextInt();

        char a = 'a';
        char z = 'z';
        int size = 26;

        for (char letter : chars) {
            if (letter >= a && letter <= z) {
                char shiftItem = (char) (((letter - a + shift) % size) + a);
                System.out.print(shiftItem);
            } else {
                System.out.print(letter);
            }
        }
    }
}
