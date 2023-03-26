package org.example;

import java.io.*;

public class FileOperations extends Query{
    Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void replaceStringInFile(String filePath, String oldString, String newString) {
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "", content = "";
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
            reader.close();
            content = content.replaceAll(oldString, newString);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class FileContentDeleter {
        public static void Eraser(String fileName) {
            try {
                FileWriter writer = new FileWriter(fileName);
                writer.write("");
                writer.close();
                System.out.println("Content deleted successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
