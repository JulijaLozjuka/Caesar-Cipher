package com.javarush.cryptoanalyser;

public class Main {
    public static void main(String[] args) {
        CaesarEncoder caesarEncoder = new CaesarEncoder();
        caesarEncoder.decryptWithKey(5,"C:\\Users\\jinxt\\OneDrive\\Desktop\\TestFile2.txt","C:\\Users\\jinxt\\OneDrive\\Desktop\\TestFile.txt");
    }

}