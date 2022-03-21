package com.javarush.cryptoanalyser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CaesarCipher {
    private static final String GREETINGS = "Welcome to the cryptanalysis application \"Caesar-Cipher\"";
    private static final String ENCRYPTION = "file encryption with known key";
    private static final String DECR_KEY = "file decryption with known key";
    private static final String DECR_BRUTE_FORCE = "file decryption with unknown key using brute force method";
    private static final String DECR_STAT_ANALYSIS = "file decryption with unknown key using statistical analysis method";

    private static final String ASK_FOR_ACTION = "Choose one of the following:\n" +
            "type \"1\" for " + ENCRYPTION +  "\n" +
            "type \"2\" for " + DECR_KEY + "\n" +
            "type \"3\" for " + DECR_BRUTE_FORCE + "\n" +
            "type \"4\" for " + DECR_STAT_ANALYSIS + "\n" +
            "type \"exit\" for exit from application";
    private static final String SELECT_FILE = "Select source file\n" +
            "File path: ";
    private static final String SELECT_DEST_FILE = "Select file to write \n" +
            "File path: ";

    public void performCryptanalysis(){
        System.out.println(GREETINGS);
        System.out.println(ASK_FOR_ACTION);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String action = "";
        while(!action.equals("1")&&!action.equals("2")&&!action.equals("3")&&!action.equals("4")&&!action.equals("exit")) {
            try {
                action = bufferedReader.readLine();
                if (!action.equals("1")&&!action.equals("2")&&!action.equals("3")&&!action.equals("4")&&!action.equals("exit")){
                    System.out.println("You entered wrong command, please try again");
                }
            } catch (IOException exception) {
                System.out.println("error occurred, please try again");
            }
        }
        if (action.equals("1")){
            System.out.println("You chose " + ENCRYPTION);
            String fileName = readSourceFileName();
            if (fileName == null){
                return;
            }
            String destFileName = readDestFileName();
            if (destFileName == null){
                return;
            }
            String stringKey = readKey();
            if (stringKey == null){
                return;
            }
            int key = Integer.parseInt(stringKey);


        }

        if (action.equals("2")){
            System.out.println("You chose " + DECR_KEY);

        }
        if (action.equals("3")){
            System.out.println("You chose " + DECR_BRUTE_FORCE);

        }
        if (action.equals("4")){
            System.out.println("You chose " + DECR_STAT_ANALYSIS);

        }
        if (action.equals("exit")){
            return;

        }



    }

    private String readSourceFileName(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = "";
        boolean fileAccepted = false;
        while(!fileAccepted) {
            try {
                System.out.println(SELECT_FILE);
                fileName = bufferedReader.readLine();
                if (fileName.equals("exit")){
                    return null;
                }
                Path filePath = Paths.get(fileName);
                if (Files.notExists(filePath)){
                    System.out.println("File cannot be found, please try again or type \"exit\" for exit ");
                }
                else {
                    fileAccepted = true;
                }

            } catch (InvalidPathException exception){
                System.out.println("Invalid file path, please try again or type \"exit\" for exit");
            }
            catch (IOException exception) {
                System.out.println("Error occurred, please try again or type \"exit\" for exit");
            }
        }
        return fileName;
    }
    private String readDestFileName(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = "";
        boolean fileAccepted = false;
        while(!fileAccepted) {
            try {
                System.out.println(SELECT_DEST_FILE);
                fileName = bufferedReader.readLine();
                if (fileName.equals("exit")){
                    return null;
                }
                Path filePath = Paths.get(fileName);
                if (Files.exists(filePath)){
                    System.out.println("File already exists, do you want to override it?\n" +
                            " Type \"y\" for yes, \"no\" for no,  or type \"exit\" for exit ");
                    String answer = bufferedReader.readLine();
                    if (answer.equals("y")){
                        fileAccepted = true;
                    }
                    if (answer.equals("exit")){
                        return null;
                    }

                }
                else {
                    fileAccepted = true;
                }

            } catch (InvalidPathException exception){
                System.out.println("Invalid file path, please try again or type \"exit\" for exit");
            }
            catch (IOException exception) {
                System.out.println("Error occurred, please try again or type \"exit\" for exit");
            }
        }
        return fileName;
    }
    private String readKey(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String key = "";
        boolean keyAccepted = false;
        while(!keyAccepted) {
            try {
                System.out.print("Enter the key: ");
                key = bufferedReader.readLine();
                if (key.equals("exit")){
                    return null;
                }
                if (Integer.parseInt(key)<0){
                    System.out.println("Key must be positive ");
                }
                else {
                    keyAccepted = true;
                }

            } catch (NumberFormatException exception){
                System.out.println("Key must be a number, please try again or type \"exit\" for exit");
            }
            catch (IOException exception) {
                System.out.println("Error occurred, please try again or type \"exit\" for exit");
            }
        }
        return key;
    }


}
