package com.javarush.cryptoanalyser;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

import java.io.*;
import java.util.*;

public class Encoder {
    private final LineReader lineReader = new LineReader();

    public void encryptWithKey(int key, String sourceFileName, String destFileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFileName));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destFileName))) {
            while (bufferedReader.ready()) {
                char[] charArray = new char[1024];
                int readCharsCount = bufferedReader.read(charArray);
                if (charArray.length > readCharsCount) {
                    charArray = Arrays.copyOf(charArray, readCharsCount);
                }
                String resultString = encryptStringFromArray(key, charArray);
                bufferedWriter.write(resultString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decryptWithKey(int key, String sourceFileName, String destFileName) {
        encryptWithKey(-key, sourceFileName, destFileName);
    }

    public void decryptBruteForce(String sourceFileName, String destFileName) {
        List<String> fileLines = lineReader.get100LinesFromFile(sourceFileName);

        Map<Integer, Integer> matchingWordCountByKey = new HashMap<>();
        for (int i = 0; i < CaesarData.ALPHABET.length(); i++) {
            List<String> decryptedFileLines = decryptList(fileLines,i);
            int matchingWordCount = Calculator.calculateMatchingWordCount(decryptedFileLines);
            matchingWordCountByKey.put(i,matchingWordCount);
        }
        int bestKey = MapKeyFinder.findMaxValueKey(matchingWordCountByKey);
        System.out.println(bestKey);
        decryptWithKey(bestKey,sourceFileName,destFileName);
    }

    public void decryptStatisticMethod(String sourceFileName, String destFileName, String exampleFileName) {
        List<String> exampleFileLines = lineReader.get100LinesFromFile(exampleFileName);
        List<String> fileLines = lineReader.get100LinesFromFile(sourceFileName);
        int fileLinesCharCount = fileLines.stream().map(String::length).reduce(0,Integer::sum);
        double[] exampleFileCharProbabilities = Calculator.calculateCharProbability(exampleFileLines);
        double[] expectedCharFrequencies = Arrays.stream(exampleFileCharProbabilities)
                .map(probability -> probability * fileLinesCharCount)
                .toArray();
        Map<Integer,Double> charFrequencyDiffFromExample = new HashMap<>();
        for (int i = 0; i <CaesarData.ALPHABET.length() ; i++) {
            List<String> decryptedFileLines = decryptList(fileLines,i);
            long[] realCharFrequencies = Calculator.calculateRealCharFrequencies(decryptedFileLines);
            double chiSquare = new ChiSquareTest().chiSquare(expectedCharFrequencies, realCharFrequencies);
            charFrequencyDiffFromExample.put(i,chiSquare);
        }
        int bestKey = MapKeyFinder.findMinValueKey(charFrequencyDiffFromExample );
        System.out.println(bestKey);
        decryptWithKey(bestKey,sourceFileName,destFileName);
    }

    private String encryptStringFromArray(int key, char[] charArray) {
        StringBuilder result = new StringBuilder();
        for (char c : charArray) {
            boolean isUpperCase = Character.isUpperCase(c);
            String s = (c + "").toLowerCase();
            if (CaesarData.ALPHABET.contains(s)) {
                int pos = CaesarData.ALPHABET.indexOf(s);
                int newPos = (pos + key) % CaesarData.ALPHABET.length();
                char encryptedChar = newPos >= 0
                        ? CaesarData.ALPHABET.charAt(newPos)
                        : CaesarData.ALPHABET.charAt(CaesarData.ALPHABET.length() + newPos);

                s = String.valueOf(encryptedChar);
            }
            if (isUpperCase) {
                s = s.toUpperCase();
            }
            result.append(s);
        }
        return result.toString();
    }

    private String decryptStringFromArray(char[] charArray, int key) {
        return encryptStringFromArray(-key, charArray);
    }

    private List<String> decryptList(List<String> encryptedList, int key){
        List<String> decryptedList = new ArrayList<>();
        for (String line:encryptedList){
            String decryptedString = decryptStringFromArray(line.toCharArray(),key);
            decryptedList.add(decryptedString);
        }
        return decryptedList;
    }

}
