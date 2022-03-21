package com.javarush.cryptoanalyser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineReader {
    public List<String> get100LinesFromFile(String sourceFileName) {
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
}
