package encryptdecrypt;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
public class Main {

    static char begin = 32;
    static char end = '~';
    static int size = 95;
    static String option = "enc";
    static String shift = "0";
    static String text = "";
    static boolean isData = false;
    static boolean isOut = false;
    static boolean isIn = false;
    static String inPath = "";
    static String outPath = "";
    static Scanner sc = new Scanner(System.in);
    static String outputMsg = "";

    public static void main(String[] args) {


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
                    isData = true;
                    break;
                case "-in":
                    inPath = args[i + 1];
                    isIn = true;
                case "-out":
                    outPath = args[i + 1];
                    isOut = true;                
            }

        }
        if (!isData && isIn) {
            text = "";
            File file = new File(inPath);
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNext()) {
                    text = text + sc.nextLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
        StringBuilder result = new StringBuilder();
        for (char letter : chars) {
            if (letter >= begin && letter <= end) {
                char shiftItem = (char) (((letter - begin + shift) % size) + begin);
                result.append(shiftItem);
            }
        }
        output(result.toString());
        
    }

    public static void decrypt(char[] chars, int shift) {
        StringBuilder result = new StringBuilder();
        for (char letter : chars) {
            if (letter >= begin && letter <= end) {
                char shiftItem = (char) (((letter - begin - shift) % size) + begin);
                result.append(shiftItem);
            }
        }
        output(result.toString());

    }
    public static void output(String outputMsg) {

        if (isOut) {
            File outFile = new File(outPath);
            try (PrintWriter printWriter = new PrintWriter(outFile)) {
                printWriter.println(outputMsg); 
            } catch (IOException e) {
                System.out.printf("An exception occurs %s", e.getMessage());
            }
        } else {
            System.out.println(outputMsg);
        }
    }
}

