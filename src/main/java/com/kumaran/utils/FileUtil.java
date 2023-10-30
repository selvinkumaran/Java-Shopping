package com.kumaran.utils;

import java.io.File;

public class FileUtil {

    private static File credentailsFile;

    public static File getCredentialsFile() {
        if (credentailsFile == null)
            credentailsFile = new File("src/main/java/com/kumaran/asserts/credentials.csv");
        return credentailsFile;
    }

    public static String getFilePath() {
        return "src/main/java/com/kumaran/asserts/";
    }

    public static String getOrderPath() {
        return "src/main/java/com/kumaran/asserts/1698598694505.txt";
    }

    public static String getProductPath() {
        return "src/main/java/com/kumaran/asserts/products.csv";
    }

    public static String getCategoryProducts() {
        return "src/main/java/com/kumaran/asserts/categories.csv";
    }

}
