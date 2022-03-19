package com.javarush.cryptoanalyser;

import java.io.*;
import java.util.*;

public class CaesarEncoder {

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
        List<String> readLines = new ArrayList<>();
        Map<Integer, Integer> matchingWordCountByKey = new HashMap<>();



        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFileName))) {
            int currentStringCount = 0;
            while (bufferedReader.ready() && currentStringCount < 50) {
                readLines.add(bufferedReader.readLine());
                currentStringCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < CaesarData.ALPHABET.length(); i++) {
            List<String> decryptedWords = new ArrayList<>();
            int matchingWordCount = 0;

            for (String readLine : readLines) {
                char[] charArray = readLine.toCharArray();
                String decryptedString = getDecryptedStringFromArray(i, charArray);
                String[] splitDecryptedStrings = decryptedString.split(" ");
                for (String s : splitDecryptedStrings) {
                    if (!s.isBlank()) {
                        decryptedWords.add(s.trim());
                    }
                }

            }

            for (String decryptedWord : decryptedWords) {
                if (CaesarData.topWords.contains(decryptedWord)) {
                    matchingWordCount++;
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

        decryptWithKey(bestKey,sourceFileName,destFileName);

    }

    public void decryptStatisticMethod(String sourceFileName, String destFileName, String exampleFileName) {

    }



}
