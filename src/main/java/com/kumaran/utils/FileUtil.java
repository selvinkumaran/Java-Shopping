package com.kumaran.utils;

import java.io.File;

public class FileUtil {

    private static File credentailsFile;

    public static File getCredentialsFile() {
        if (credentailsFile == null)
            credentailsFile = new File("src/main/java/com/kumaran/assests/credentials.csv");
        return credentailsFile;
    }

    public static String getFilePath() {
        return "src/main/java/com/kumaran/assests/";
    }
}
