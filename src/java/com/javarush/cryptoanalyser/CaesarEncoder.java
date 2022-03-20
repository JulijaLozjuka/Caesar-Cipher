package com.javarush.cryptoanalyser;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

import java.io.*;
import java.util.*;

public class CaesarEncoder {

    public void encryptWithKey(int key, String sourceFileName, String destFileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFileName));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destFileName))) {

            while (bufferedReader.ready()) {
                char[] charArray = new char[1024];
                int readCharsCount = bufferedReader.read(charArray);
                if (charArray.length > readCharsCount) {
                    charArray = Arrays.copyOf(charArray, readCharsCount);
                }

                String resultString = getEncryptedStringFromArray(key, charArray);

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
        Map<Integer, Integer> matchingWordCountByKey = new HashMap<>();
        List<String> readLines = get100LinesFromFile(sourceFileName);

        for (int i = 0; i < CaesarData.ALPHABET.length(); i++) {
            int matchingWordCount = 0;
            for (String readLine : readLines) {
                char[] charArray = readLine.toCharArray();
                String decryptedString = getDecryptedStringFromArray(i, charArray);
                String[] splitDecryptedStrings = decryptedString.split(" ");
                for (String decryptedWord : splitDecryptedStrings) {
                    String trimmedDecryptedWord = decryptedWord.trim();
                    if (CaesarData.topWords.contains(trimmedDecryptedWord)) {
                        matchingWordCount++;
                    }
                }
            }
            matchingWordCountByKey.put(i,matchingWordCount);
        }

        int bestKey = 0;
        int maxWordCount = 0;
        for (Map.Entry<Integer,Integer> pairs: matchingWordCountByKey.entrySet()){
            if (pairs.getValue()>maxWordCount){
                maxWordCount= pairs.getValue();
                bestKey = pairs.getKey();
            }
        }
        System.out.println("Key found: " + bestKey);

        decryptWithKey(bestKey,sourceFileName,destFileName);

    }


    public void decryptStatisticMethod(String sourceFileName, String destFileName, String exampleFileName) {
        Map<Integer,Double> charFrequencyDiffFromExample = new HashMap<>();
        List<String> exampleFileLines = get100LinesFromFile(exampleFileName);
        double[] exampleFileCharProbabilities = calculateCharProbability(exampleFileLines);
        List<String> readFileLines = get100LinesFromFile(sourceFileName);
        int totalReadCharCount = readFileLines.stream().map(String::length).reduce(0,Integer::sum);
        double[] expectedCharFrequencies = Arrays.stream(exampleFileCharProbabilities)
                .map(probability -> probability * totalReadCharCount)
                .toArray();
        System.out.println(Arrays.toString(expectedCharFrequencies));
        for (int i = 0; i <CaesarData.ALPHABET.length() ; i++) {
            long[] realCharFrequencies = new long[CaesarData.ALPHABET.length()];
            for (String readLine: readFileLines){
                char[] charsFromLine = readLine.toCharArray();
                String decryptedString = getDecryptedStringFromArray(i, charsFromLine);
                for (char decryptedChar: decryptedString.toCharArray()){
                    if (CaesarData.ALPHABET.contains(decryptedChar+"")){
                        realCharFrequencies[CaesarData.ALPHABET.indexOf(decryptedChar+"")]+=1;
                    }
                }
            }
            double chiSquare = new ChiSquareTest().chiSquare(expectedCharFrequencies, realCharFrequencies);
            System.out.println(chiSquare+" key " + i);
            charFrequencyDiffFromExample.put(i,chiSquare);
        }
        int bestKey = 0;
        double minFrequencyDifference = Double.MAX_VALUE;
        for(Map.Entry<Integer,Double> pairs: charFrequencyDiffFromExample.entrySet()){
            if (pairs.getValue()<minFrequencyDifference){
                minFrequencyDifference= pairs.getValue();
                bestKey = pairs.getKey();
            }
        }
        System.out.println("Key found: " + bestKey);

    }

    private String getEncryptedStringFromArray(int key, char[] charArray) {
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

    private String getDecryptedStringFromArray(int key, char[] charArray) {
        return getEncryptedStringFromArray(-key, charArray);
    }

    private List<String> get100LinesFromFile(String sourceFileName) {
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

    private double[] calculateCharProbability(List<String> linesFromFile){
        int totalCharCount = linesFromFile.stream().map(String::length).reduce(0,Integer::sum);
        double[] countPerChar = new double[CaesarData.ALPHABET.length()];
        double[] charProbabilities;

        for (String line:linesFromFile){
            for (char charFromLine:line.toCharArray()){
                if (CaesarData.ALPHABET.contains(charFromLine+"")){
                    countPerChar[CaesarData.ALPHABET.indexOf(charFromLine+"")] +=1;
                }
            }
        }
        charProbabilities = Arrays.stream(countPerChar)
                .map(count -> count / totalCharCount)
                .toArray();
        return charProbabilities;

    }

}
