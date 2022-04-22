package cryptoanalyser.cryptographer;

import cryptoanalyser.util.LineReader;

public class CaesarCipher {
   public static final String ENCRYPTION_TEXT = "file encryption with known key";
   public static final String DECR_KEY_TEXT = "file decryption with known key";
   public static final String DECR_BRUTE_FORCE_TEXT = "file decryption with unknown key using brute force method";
   public static final String DECR_STAT_ANALYSIS_TEXT = "file decryption with unknown key using statistical analysis method";
    private final Encoder encoder = new Encoder();

    public void performCryptanalysis() {
        Action action = LineReader.readAction();
        if(action==Action.EXIT){
            return;
        }
        printSelectedOption(action);
        System.out.print("select source file\n file path: ");
        String sourceFileName = LineReader.readSourceFileName();
        if(sourceFileName == null){
            return;
        }
        System.out.print("select dest file\n file path: ");
        String destFileName = LineReader.readDestFileName();
        if(destFileName == null){
            return;
        }
        switch (action) {
            case ENCR -> encryptOption(sourceFileName, destFileName);
            case DECR -> decryptOption(sourceFileName, destFileName);
            case BRUTE -> encoder.decryptBruteForce(sourceFileName, destFileName);
            case STAT -> {
                System.out.println("select sample text file\nfile path: ");
                String exampleFileName = LineReader.readSourceFileName();
                if (exampleFileName == null) {
                   return;
                }
                encoder.decryptStatisticMethod(sourceFileName, destFileName, exampleFileName);
            }
        }
    }

    private void printSelectedOption(Action action) {
        switch (action) {
            case ENCR -> printChosenOption(ENCRYPTION_TEXT);
            case DECR -> printChosenOption(DECR_KEY_TEXT);
            case BRUTE -> printChosenOption(DECR_BRUTE_FORCE_TEXT);
            case STAT -> printChosenOption(DECR_STAT_ANALYSIS_TEXT);
        }
    }

    private void printChosenOption(String encryptionText) {
        System.out.println("you chose " + encryptionText);
    }

    private void encryptOption(String sourceFileName, String destFileName) {
        Integer key = readKeyInput();
        if (checkDeEnCryptFields(sourceFileName, destFileName, key)) {
            return;
        }
        encoder.encryptWithKey(sourceFileName, destFileName, key);
    }

    private void decryptOption(String sourceFileName, String destFileName) {
        Integer key = readKeyInput();
        if (checkDeEnCryptFields(sourceFileName, destFileName, key)) {
            return;
        }
        encoder.decryptWithKey(sourceFileName, destFileName, key);
    }

    private boolean checkDeEnCryptFields(String sourceFileName, String destFileName, Integer key) {
        return sourceFileName == null && destFileName == null && key == null;
    }

    private Integer readKeyInput() {
        String stringKey = LineReader.readKey();
        Integer key = null;
        try {
            key = Integer.parseInt(stringKey);
        } catch (NumberFormatException e) {
            System.out.println("Bad key, enter a number");
        }
        return key;
    }
}
