package cryptoanalyser.util;

import cryptoanalyser.data.CaesarData;

import java.util.Arrays;
import java.util.List;

public class Calculator {

    public static int calculateMatchingWordCount(List<String> linesFromFile) {
        int matchingWordCount = 0;

        for (String line : linesFromFile) {
            String[] splitDecryptedStrings = line.split(" ");
            for (String decryptedWord : splitDecryptedStrings) {
                String trimmedDecryptedWord = decryptedWord.trim();
                if (CaesarData.topWords.contains(trimmedDecryptedWord)) {
                    matchingWordCount++;
                }
            }
        }
        return matchingWordCount;
    }

    public static long[] calculateRealCharFrequencies(List<String> fileLines) {
        long[] realCharFrequencies = new long[CaesarData.ALPHABET.length()];
        for (String lines: fileLines){
            for (char character: lines.toCharArray()){
                if (CaesarData.ALPHABET.contains(character+"")){
                    realCharFrequencies[CaesarData.ALPHABET.indexOf(character+"")]+=1;
                }
            }
        }
        return realCharFrequencies;
    }

    public static double[] calculateCharProbability(List<String> linesFromFile){
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
