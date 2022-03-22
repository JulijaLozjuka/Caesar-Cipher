package com.javarush.cryptoanalyser;

public class CaesarCipher {
    static final String ENCRYPTION = "file encryption with known key";
    static final String DECR_KEY = "file decryption with known key";
    static final String DECR_BRUTE_FORCE = "file decryption with unknown key using brute force method";
    static final String DECR_STAT_ANALYSIS = "file decryption with unknown key using statistical analysis method";
    private final Encoder encoder = new Encoder();
    private final LineReader lineReader = new LineReader();
    private String sourceFileName;
    private String destFileName;
    private String exampleFileName;
    private int key;

    public void performCryptanalysis() {
        String action = lineReader.readAction();
        switch (action) {
            case "1" -> {
                System.out.println("you chose " + ENCRYPTION);
                initializeFields(1);
                if (key != -1) {
                    encoder.encryptWithKey(sourceFileName, destFileName, key);
                }
            }
            case "2" -> {
                System.out.println("you chose " + DECR_KEY);
                initializeFields(2);
                if (key != -1) {
                    encoder.decryptWithKey(sourceFileName, destFileName, key);
                }
            }
            case "3" -> {
                System.out.println("you chose " + DECR_BRUTE_FORCE);
                initializeFields(3);
                if (destFileName != null) {
                    encoder.decryptBruteForce(sourceFileName, destFileName);
                }
            }
            case "4" -> {
                System.out.println("you chose " + DECR_STAT_ANALYSIS);
                initializeFields(4);
                if (exampleFileName != null) {
                    encoder.decryptStatisticMethod(sourceFileName, destFileName, exampleFileName);
                }
            }
        }

    }

    private void initializeFields(int operationNumber) {
        System.out.print("select source file\n" +
                "file path: ");
        sourceFileName = lineReader.readSourceFileName();
        if (sourceFileName == null) {
            destFileName = null;
            exampleFileName = null;
            key = -1;
            return;
        }
        System.out.println("select file to write \n" +
                "file path: ");
        destFileName = lineReader.readDestFileName();
        if (destFileName == null) {
            exampleFileName = null;
            key = -1;
            return;
        }
        if (operationNumber == 1 || operationNumber == 2) {
            String stringKey = lineReader.readKey();
            if (stringKey == null) {
                exampleFileName = null;
                this.key = -1;
                return;
            }
            this.key = Integer.parseInt(stringKey);
        }
        if (operationNumber == 4) {
            System.out.print("select sample text file \n" +
                    "file path: ");
            exampleFileName = lineReader.readSourceFileName();
        }
    }


}
