package cryptoanalyser.util;

import cryptoanalyser.cryptographer.Action;
import cryptoanalyser.cryptographer.CaesarCipher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LineReader {
    private static final String ASK_FOR_ACTION = "Enter one of the following:\n" +
            " \"encr\" - " + CaesarCipher.ENCRYPTION_TEXT + "\n" +
            " \"decr\" - " + CaesarCipher.DECR_KEY_TEXT + "\n" +
            " \"brute\" - " + CaesarCipher.DECR_BRUTE_FORCE_TEXT + "\n" +
            " \"stat\" - " + CaesarCipher.DECR_STAT_ANALYSIS_TEXT + "\n" +
            " \"exit\" - exit from application";

    public static List<String> get100LinesFromFile(String sourceFileName) {
        List<String> readLines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFileName))) {
            int currentStringCount = 0;
            while (bufferedReader.ready() && currentStringCount < 100) {
                readLines.add(bufferedReader.readLine());
                currentStringCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readLines;
    }

    public  static Action readAction() {
        System.out.println(ASK_FOR_ACTION);
        String stringAction = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                stringAction = bufferedReader.readLine();
                stringAction = stringAction.toUpperCase().trim();
                return Action.valueOf(stringAction);
            } catch (IllegalArgumentException exception){
                System.out.println("you entered wrong command, please try again");
            }
            catch (IOException exception) {
                System.out.println("error occurred, please try again");
            }
        }
    }

    public static String readSourceFileName() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = "";
        boolean fileAccepted = false;

        while (!fileAccepted) {
            try {
                fileName = bufferedReader.readLine();
                fileName = fileName.trim();

                if (fileName.equals("exit")) {
                    return null;
                }
                if (Files.notExists(Paths.get(fileName))) {
                    System.out.println("file cannot be found, please try again or type \"exit\" for exit ");
                } else {
                    fileAccepted = true;
                }

            } catch (InvalidPathException exception) {
                System.out.println("invalid file path, please try again or type \"exit\" for exit");
            } catch (IOException exception) {
                System.out.println("error occurred, please try again or type \"exit\" for exit");
            }
        }

        return fileName;
    }

   public static String readDestFileName() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = "";
        boolean fileAccepted = false;

        while (!fileAccepted) {
            try {
                fileName = bufferedReader.readLine().trim();

                if (fileName.equals("exit")) {
                    return null;
                }
                if (Files.exists(Paths.get(fileName))) {
                    System.out.println("file already exists, do you want to override it?\n" +
                            " type \"y\" for yes, \"n\" for no,  or type \"exit\" for exit ");
                        String answer = bufferedReader.readLine().trim();
                        switch (answer) {
                            case "n"-> System.out.print("type another file path: ");
                            case "y"-> fileAccepted = true;
                            case "exit" -> {
                                return null;
                            }
                        }
                }
                else {
                    fileAccepted = true;
                }
            } catch (InvalidPathException exception) {
                System.out.println("invalid file path, please try again or type \"exit\" for exit");
            } catch (IOException exception) {
                System.out.println("an error occurred, please try again or type \"exit\" for exit");
            }
        }

        return fileName;
    }

    public  static String readKey() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String key = "";
        boolean keyAccepted = false;

        while (!keyAccepted) {

            try {
                System.out.print("enter the key: ");
                key = bufferedReader.readLine().trim();

                if (key.equals("exit")) {
                    return null;
                }
                if (Integer.parseInt(key) < 0) {
                    System.out.println("key must be positive ");
                } else {
                    keyAccepted = true;
                }

            } catch (NumberFormatException exception) {
                System.out.println("key must be a number, please try again or type \"exit\" for exit");
            } catch (IOException exception) {
                System.out.println("an error occurred, please try again or type \"exit\" for exit");
            }
        }

        return key;
    }
}
