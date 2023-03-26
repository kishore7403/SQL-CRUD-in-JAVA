
package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class EraseContent extends FileOperations{

    public void Eraser(String filename) throws Exception {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("");
            writer.close();
            System.out.println("Content deleted successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }

    }
    }

