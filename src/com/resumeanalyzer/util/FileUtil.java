package com.resumeanalyzer.util;

import java.io.*;
import java.nio.file.*;

public class FileUtil {

    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static void writeReport(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes());
        System.out.println("  Report saved to: " + filePath);
    }
}
