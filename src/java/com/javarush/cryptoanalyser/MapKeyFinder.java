package com.javarush.cryptoanalyser;

import java.util.Map;

public class MapKeyFinder {
    public static int findMaxValueKey(Map<Integer, Integer> matchingWordCountByKey) {
        int bestKey = 0;
        int maxWordCount = 0;
        for (Map.Entry<Integer,Integer> pairs: matchingWordCountByKey.entrySet()){
            if (pairs.getValue()>maxWordCount){
                maxWordCount= pairs.getValue();
                bestKey = pairs.getKey();
            }
        }
        if (maxWordCount==0){
            return -1;
        }
        return bestKey;
    }

    public static int findMinValueKey(Map<Integer, Double> charFrequencyDiffFromExample) {
        int bestKey = 0;
        double minFrequencyDifference = Double.MAX_VALUE;
        for(Map.Entry<Integer,Double> pairs: charFrequencyDiffFromExample.entrySet()){
            if (pairs.getValue()<minFrequencyDifference){
                minFrequencyDifference= pairs.getValue();
                bestKey = pairs.getKey();
            }
        }
        return bestKey;
    }
}
