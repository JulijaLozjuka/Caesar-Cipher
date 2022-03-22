package cryptoanalyser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the cryptanalysis application \"Caesar-Cipher\"");
        CaesarCipher caesarCipher = new CaesarCipher();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean isExit = false;
        while (!isExit) {
            caesarCipher.performCryptanalysis();
            System.out.println("do you want to encrypt/decrypt another file? type \"y\" for yes, \"n\" for no");
            try {
                while (true) {
                    String answer = bufferedReader.readLine().trim();
                    if (answer.equals("n") || answer.equals("exit")) {
                        isExit = true;
                        break;
                    } else if (answer.equals("y")) {
                        break;
                    } else {
                        System.out.println("invalid command, try again");
                    }
                }
            } catch (IOException exception) {
                System.out.println("an error occurred");
                isExit = true;
            }
        }
    }
}
