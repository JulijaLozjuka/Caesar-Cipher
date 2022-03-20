package com.javarush.cryptoanalyser;

public class Main {
    public static void main(String[] args) {
        CaesarEncoder caesarEncoder = new CaesarEncoder();
        caesarEncoder.encryptWithKey(5,"C:\\Users\\jinxt\\OneDrive\\Desktop\\TestFile.txt",
                "C:\\Users\\jinxt\\OneDrive\\Desktop\\TestFile2.txt");
//        caesarEncoder.decryptBruteForce("C:\\Users\\jinxt\\OneDrive\\Desktop\\TestFile2.txt",
//                "C:\\Users\\jinxt\\OneDrive\\Desktop\\TestFile3.txt");
        caesarEncoder.decryptStatisticMethod("C:\\Users\\jinxt\\OneDrive\\Desktop\\TestFile2.txt",
                "C:\\Users\\jinxt\\OneDrive\\Desktop\\TestFile3.txt","C:\\Users\\jinxt\\OneDrive\\Desktop\\TestFileExample.txt");
    }

}
