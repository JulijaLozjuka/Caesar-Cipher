package cryptoanalyser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LineReader {
    private static final String ASK_FOR_ACTION = "choose one of the following:\n" +
            "type \"1\" for " + CaesarCipher.ENCRYPTION + "\n" +
            "type \"2\" for " + CaesarCipher.DECR_KEY + "\n" +
            "type \"3\" for " + CaesarCipher.DECR_BRUTE_FORCE + "\n" +
            "type \"4\" for " + CaesarCipher.DECR_STAT_ANALYSIS + "\n" +
            "type \"exit\" for exit from application";

    List<String> get100LinesFromFile(String sourceFileName) {
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

    String readAction() {
        System.out.println(ASK_FOR_ACTION);
        String action = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (!action.equals("1") && !action.equals("2") && !action.equals("3") && !action.equals("4") && !action.equals("exit")) {
            try {
                action = bufferedReader.readLine();
                action = action.trim();
                if (!action.equals("1") && !action.equals("2") && !action.equals("3") && !action.equals("4") && !action.equals("exit")) {
                    System.out.println("you entered wrong command, please try again");
                }
            } catch (IOException exception) {
                System.out.println("error occurred, please try again");
            }
        }
        return action;
    }

    String readSourceFileName() {
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
                Path filePath = Paths.get(fileName);
                if (Files.notExists(filePath)) {
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

    String readDestFileName() {
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
                Path filePath = Paths.get(fileName);
                if (Files.exists(filePath)) {
                    System.out.println("file already exists, do you want to override it?\n" +
                            " type \"y\" for yes, \"n\" for no,  or type \"exit\" for exit ");
                    boolean answerAccepted = false;
                    while (!answerAccepted) {
                        String answer = bufferedReader.readLine();
                        answer = answer.trim();
                        switch (answer) {
                            case "n":
                                System.out.print("type another file path: ");
                                answerAccepted = true;
                                break;
                            case "y":
                                answerAccepted = true;
                                fileAccepted = true;
                                break;
                            case "exit":
                                return null;
                            default:
                                System.out.println("invalid command, try again");
                        }
                    }
                } else {
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

    String readKey() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String key = "";
        boolean keyAccepted = false;
        while (!keyAccepted) {
            try {
                System.out.print("enter the key: ");
                key = bufferedReader.readLine();
                key = key.trim();
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
