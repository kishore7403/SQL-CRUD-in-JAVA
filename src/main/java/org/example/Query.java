package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Query {

    FileOperations fileOperations =new FileOperations();

    public int createQuery(UserData userData, String query, String[] security, FileOperations fileOperations) throws Exception {
        String oldText = userData.getUserId() + "\\+" + userData.databaseCount + "\\+" + userData.getUserDatabase();
        String[] queryElements = query.split(" ");
        if (query.contains("create database") && userData.getUserDatabase().equals("null")) {
            userData.userDatabase = queryElements[queryElements.length - 1].replace(";", "");
            userData.databaseCount = "1";
            String newText = String.format("%s\\+1\\+%s", userData.getUserId(), userData.getUserDatabase());
            System.out.println(fileOperations);
            fileOperations.setValue(1);
//            System.out.println(this.fileOperations.value);
            fileOperations.replaceStringInFile("UserInfo.txt", oldText, newText);
            FileWriter logwriter = new FileWriter("log.txt",true);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            logwriter.write(userData.getUserId()+"+"+userData.getUsername()+"+"+timeStamp+"+"+query);
            logwriter.close();
            return 1;
        } else if (query.contains("create table")) {
            String tableName = queryElements[2].replace(";", "");
            String fileName = String.format("%s-%s.txt", tableName, userData.getUserId());
            String values = query.substring(query.indexOf("(") + 1, query.indexOf(";") - 1);
            String[] attributes = values.split(",");
            FileWriter writer = new FileWriter(fileName, true);
            for (String a : attributes) {
                writer.write(a.trim().split(" ")[0] + " ");
            }
            writer.write("\n");
            writer.close();
            System.out.println("table created");
            FileWriter logwriter = new FileWriter("log.txt",true);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            logwriter.write(userData.getUserId()+"+"+userData.getUsername()+"+"+timeStamp+"+"+query);
            logwriter.close();
            return 1;
        } else {
            System.out.println("Error in creation");
            return -1;
        }
    }

    public int insertIntoTable(UserData userData, String query, String[] security) throws IOException {
        String[] queryElements = query.split(" ");
        String tableName = queryElements[2].replace(";", "");
        String values = query.substring(query.indexOf("(") + 1, query.indexOf(";") - 1);
        String[] attributes = values.split(",");
        FileWriter writer = new FileWriter(String.format("%s-%s.txt", tableName, userData.getUserId()), true);
        for (String a : attributes) {
            writer.write(a.trim().split(" ")[0] + " ");
        }
        writer.write("\n");
        writer.close();
        FileWriter logwriter = new FileWriter("log.txt",true);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        logwriter.write(userData.getUserId()+"+"+userData.getUsername()+"+"+timeStamp+"+"+query);
        logwriter.close();
        return 1;
    }

    public void selectFromTable(UserData userData, String query, String[] security) throws IOException {
        String[] queryElements = query.split(" ");
        String tableName = queryElements[3].replace(";", "");
        String attribute = queryElements[1];
        File myObj = new File(String.format("%s-%s.txt", tableName, userData.getUserId()));
        Scanner myReader = new Scanner(myObj);

        if(attribute.equals("*")) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            FileWriter logwriter = new FileWriter("log.txt",true);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            logwriter.write(userData.getUserId()+"+"+userData.getUsername()+"+"+timeStamp+"+"+query);
            logwriter.close();
        }
        else {
            int index = 0;
            String data = myReader.nextLine();

            String[] dataElements = data.split(" ");
            for (int i = 0; i < dataElements.length; i++) {
                if (dataElements[i].equals(attribute)) {
                    index = i;
                }
            }
            System.out.println(data.split(" ")[index]);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                System.out.println(data.split(" ")[index]);
            }
            FileWriter logwriter = new FileWriter("log.txt",true);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            logwriter.write(userData.getUserId()+"+"+userData.getUsername()+"+"+timeStamp+"+"+query);
            logwriter.close();
        }

    }

    public void deleteTable(UserData userData, String query, String[] security) throws Exception {
        String[] queryElements = query.split(" ");
        String tableName = queryElements[2].replace(";", "");
        File myObj = new File(String.format("%s-%s.txt", tableName, userData.getUserId()));
        myObj.delete();
        System.out.println(String.format("%s-%s.txt", tableName, userData.getUserId())+" is deleted");
        FileWriter logwriter = new FileWriter("log.txt",true);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        logwriter.write(userData.getUserId()+"+"+userData.getUsername()+"+"+timeStamp+"+"+query);
        logwriter.close();
    }
}
