package com.javarush.cryptoanalyser;

import java.io.*;

public class CaesarEncoder {


    public void encryptWithKey(int key, String sourceFileName, String destFileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFileName));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destFileName))) {

            while (bufferedReader.ready()) {
                StringBuilder result = new StringBuilder();
                char[] charArray = new char[1024];
                int readBytes = bufferedReader.read(charArray);
                for (int i = 0; i <readBytes ; i++) {
                    boolean isUpperCase = Character.isUpperCase(charArray[i]);
                    String s = (charArray[i] + "").toLowerCase();
                    if (CaesarData.ALPHABET.contains(s)) {
                        int pos = CaesarData.ALPHABET.indexOf(s);
                        int newPos = (pos + key) % CaesarData.ALPHABET.length();
                        char encryptedChar = newPos>=0
                                ? CaesarData.ALPHABET.charAt(newPos)
                                :CaesarData.ALPHABET.charAt(CaesarData.ALPHABET.length()+newPos);

                            s = String.valueOf(encryptedChar);
                    }
                    if(isUpperCase){
                        s=s.toUpperCase();
                    }
                    result.append(s);
                }
                bufferedWriter.write(result.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void decryptWithKey(int key, String sourceFileName, String destFileName){
        encryptWithKey(-key,sourceFileName,destFileName);
    }

    public void decryptBruteForce(String sourceFileName, String destFileName){
        for (int i = 1; i <CaesarData.ALPHABET.length() ; i++) {
            decryptWithKey(i,sourceFileName,destFileName);


        }

    }

    public void decryptStatisticMethod(String sourceFileName, String destFileName, String exampleFileName){

    }



}
