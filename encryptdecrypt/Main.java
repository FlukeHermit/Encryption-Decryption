package encryptdecrypt;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class Main {

    public static void main(String[] args) {

        Cipher cipher = new Cipher(args);
        cipher.crypt();
    }
}
interface CipherAlgorithm {
    String execute(char[] chars, int shift);

    char begin = 32;
    char end = '~';
    int size = 95;
}

class EncryptUnicodeAlgorithm implements CipherAlgorithm {

    public String execute(char[] chars, int shift) {
        StringBuilder result = new StringBuilder();
        for (char letter : chars) {
            if (letter >= begin && letter <= end) {
                char shiftItem = (char) (((letter - begin + shift) % size) + begin);
                result.append(shiftItem);
            }
        }
        return result.toString();
    }
}

class EncryptShiftAlgorithm implements CipherAlgorithm {
    public String execute(char[] chars, int shift) {
        StringBuilder result = new StringBuilder();
        char startLetter;

        for (char letter : chars) {
            if (Character.isAlphabetic(letter)) {
                startLetter = (Character.isUpperCase(letter)) ? 'A' : 'a';
                result.append((char) (startLetter + (letter - startLetter + shift) % 26));
            } else {
                result.append((char) letter);
            }
        }
        return result.toString();
        
    }
}

class DecryptUnicodeAlgorithm implements CipherAlgorithm {
    public String execute(char[] chars, int shift) {
        StringBuilder result = new StringBuilder();
        for (char letter : chars) {
            if (letter >= begin && letter <= end) {
                char shiftItem = (char) (((letter - begin - shift) % size) + begin);
                result.append(shiftItem);
            }
        }
        return result.toString();           
    }

}

class DecryptShiftAlgorithm implements CipherAlgorithm {
    public String execute(char[] chars, int shift) {
        StringBuilder result = new StringBuilder();
        char endLetter;

        for (char letter : chars) {
            if (Character.isAlphabetic(letter)) {
                endLetter = (Character.isUpperCase(letter)) ? 'Z' : 'z';
                result.append((char) (endLetter - (endLetter - letter + shift) % 26));
            } else {
                result.append((char) letter);
            }
        }
        return result.toString();
        
    }
}

class Cipher {
    private String option = "enc";
    private String shift = "0";
    private String text = "";
    private boolean isData = false;
    private boolean isOut = false;
    private boolean isIn = false;
    private String inPath = "";
    private String outPath = "";
    private CipherAlgorithm algorithm;
    private String alg = "shift";


    public Cipher(String[] args) {
        setParams(args);
        if (!isData && isIn) {
            loadData();
        }
        setAlgorithm();
    }

    private void setParams(String[] args) {
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
                    break;
                case "-out":
                    outPath = args[i + 1];
                    isOut = true;
                    break;
                case "-alg":
                    alg = args[i + 1];
                    break;           
            }
    
        }
    }
    private void loadData() {
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
    
    private void setAlgorithm() {
        if ("enc".equals(option) && "unicode".equals(alg)) {
            algorithm = new EncryptUnicodeAlgorithm();
        } else if ("enc".equals(option) && "shift".equals(alg)) {
            algorithm = new EncryptShiftAlgorithm();
        } else if ("dec".equals(option) && "unicode".equals(alg)) {
            algorithm = new DecryptUnicodeAlgorithm();
        } else if ("dec".equals(option) && "shift".equals(alg)) {
            algorithm = new DecryptShiftAlgorithm();
        }
    }

    
    public void saveData(String outputMsg) {
        File outFile = new File(outPath);
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            printWriter.println(outputMsg); 
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    } 


    public void crypt() {
        char[] chars = text.toCharArray();
        String result = algorithm.execute(chars, Integer.valueOf(shift));
        if (!isOut) {
            System.out.println(result);
        } else {
            saveData(result);
        }
    }
}

