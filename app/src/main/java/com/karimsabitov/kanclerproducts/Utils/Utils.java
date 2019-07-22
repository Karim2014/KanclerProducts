package com.karimsabitov.kanclerproducts.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatString(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static File openFile(String name) {
        return new File(name);
    }


    public static String readTextFile(String fileName) {
        File file = new File(fileName);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    return text.toString();
                }
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isFileExists(String fileName) {
        return new File(fileName).exists();
    }

    public static void writeToTextFile(String fileName, String json) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(json);
        fileWriter.flush();
        fileWriter.close();
    }
}
