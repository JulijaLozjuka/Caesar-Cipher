package cryptoanalyser.cryptographer;

import cryptoanalyser.data.CaesarData;
import cryptoanalyser.util.Calculator;
import cryptoanalyser.util.LineReader;
import cryptoanalyser.util.MapKeyFinder;
import org.apache.commons.math3.stat.inference.ChiSquareTest;

import java.io.*;
import java.util.*;

public class Encoder {

    public void encryptWithKey(String sourceFileName, String destFileName, int key) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFileName));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destFileName))) {

            while (bufferedReader.ready()) {
                char[] charArray = new char[1024];
                int readCharsCount = bufferedReader.read(charArray);
                if (charArray.length > readCharsCount) {
                    charArray = Arrays.copyOf(charArray, readCharsCount);
                }
                String resultString = encryptCharArray(charArray, key);
                bufferedWriter.write(resultString);
                System.out.println("operation successful");
            }

        } catch (IOException e) {
            System.out.println("an error occurred during file reading or writing");

        }
    }

    public void decryptWithKey(String sourceFileName, String destFileName, int key) {
        encryptWithKey(sourceFileName, destFileName, -key);
    }

    public void decryptBruteForce(String sourceFileName, String destFileName) {
        List<String> fileLines = LineReader.get100LinesFromFile(sourceFileName);
        Map<Integer, Integer> matchingWordCountByKey = new HashMap<>();

        for (int i = 0; i < CaesarData.ALPHABET.length(); i++) {
            List<String> decryptedFileLines = decryptList(fileLines, i);
            int matchingWordCount = Calculator.calculateMatchingWordCount(decryptedFileLines);
            matchingWordCountByKey.put(i, matchingWordCount);
        }

        int bestKey = MapKeyFinder.findMaxValueKey(matchingWordCountByKey);
        if (bestKey == -1) {
            System.out.println("operation failed, unable to decrypt file");
        } else {
            System.out.println("best key found to be:" + bestKey);
            decryptWithKey(sourceFileName, destFileName, bestKey);
        }

    }

    public void decryptStatisticMethod(String sourceFileName, String destFileName, String exampleFileName) {
        List<String> exampleFileLines = LineReader.get100LinesFromFile(exampleFileName);
        List<String> fileLines = LineReader.get100LinesFromFile(sourceFileName);

        int fileLinesCharCount = fileLines.stream().map(String::length).reduce(0, Integer::sum);
        double[] exampleFileCharProbabilities = Calculator.calculateCharProbability(exampleFileLines);
        double[] expectedCharFrequencies = Arrays.stream(exampleFileCharProbabilities)
                .map(probability -> probability * fileLinesCharCount)
                .toArray();

        for (int i = 0; i < expectedCharFrequencies.length ; i++) {
            if (expectedCharFrequencies[i]==0){
                expectedCharFrequencies[i]=0.0001;
            }
        }

        Map<Integer, Double> charFrequencyDiffFromExample = new HashMap<>();
        for (int i = 0; i < CaesarData.ALPHABET.length(); i++) {
            List<String> decryptedFileLines = decryptList(fileLines, i);
            long[] realCharFrequencies = Calculator.calculateRealCharFrequencies(decryptedFileLines);
            double chiSquare = new ChiSquareTest().chiSquare(expectedCharFrequencies, realCharFrequencies);
            charFrequencyDiffFromExample.put(i, chiSquare);
        }

        int bestKey = MapKeyFinder.findMinValueKey(charFrequencyDiffFromExample);
        System.out.println("best key found to be:" + bestKey);
        decryptWithKey(sourceFileName, destFileName, bestKey);
    }

    private String encryptCharArray(char[] charArray, int key) {
        StringBuilder result = new StringBuilder();

        for (char character : charArray) {
            boolean isUpperCase = Character.isUpperCase(character);
            String stringCharacter = (character + "").toLowerCase();

            if (CaesarData.ALPHABET.contains(stringCharacter)) {
                int pos = CaesarData.ALPHABET.indexOf(stringCharacter);
                int newPos = (pos + key) % CaesarData.ALPHABET.length();
                char encryptedChar = newPos >= 0
                        ? CaesarData.ALPHABET.charAt(newPos)
                        : CaesarData.ALPHABET.charAt(CaesarData.ALPHABET.length() + newPos);
                stringCharacter = String.valueOf(encryptedChar);
            }

            if (isUpperCase) {
                stringCharacter = stringCharacter.toUpperCase();
            }

            result.append(stringCharacter);
        }

        return result.toString();
    }

    private String decryptCharArray(char[] charArray, int key) {
        return encryptCharArray(charArray, -key);
    }

    private List<String> decryptList(List<String> encryptedList, int key) {
        List<String> decryptedList = new ArrayList<>();
        for (String line : encryptedList) {
            String decryptedString = decryptCharArray(line.toCharArray(), key);
            decryptedList.add(decryptedString);
        }
        return decryptedList;
    }

}
