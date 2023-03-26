package org.example;

import org.example.FileOperations;
import org.example.Query;
import org.example.UserData;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashvalue = no.toString(16);
            while (hashvalue.length() < 32) {
                hashvalue = "0" + hashvalue;
            }
            return hashvalue;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //reference:https://www.geeksforgeeks.org/md5-hash-in-java/
    }


    public static int entryCounter() {
        int entries = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("UserInfo.txt"))) {
            while (reader.readLine() != null) {
                entries++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public static void replaceStringInFile(String filename, String oldString, String newString) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".tmp"));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll(oldString, newString);
            writer.write(line + "\n");
        }

        reader.close();
        writer.close();

        // Replace the original file with the updated one
        if (!new java.io.File(filename + ".tmp").renameTo(new java.io.File(filename))) {
            throw new IOException("Error replacing file " + filename);
        }
    }


    public static void main(String[] args) throws Exception {
        int n=1,choice;
        String seperator = "+";
        Scanner myscanner=new Scanner(System.in);



        while(n==1) {
            System.out.println("Options");
            System.out.println("1.Sign-up");
            System.out.println("2.Sign-in");
            System.out.println("3.exit");
            choice =myscanner.nextInt();
            switch (choice) {
                case 1 -> {
                    try {
                        UserData userData = new UserData();
                        FileWriter writer = new FileWriter("UserInfo.txt", true);
                        Scanner myScanner = new Scanner(System.in);
                        System.out.println("FirstName:");
                        userData.setFirstName(myScanner.nextLine());
                        System.out.println("LastName:");
                        userData.setLastName(myScanner.nextLine());
                        System.out.println("Username:");
                        userData.setUsername(myScanner.nextLine());
                        System.out.println("Password:");
                        userData.setPassword(getMd5(myScanner.nextLine()));
                        System.out.println("Add security question:");
                        userData.setSecurityQuestion(myScanner.nextLine());
                        System.out.println("Answer:");
                        userData.setSecurityAnswer(getMd5(myScanner.nextLine()));
                        userData.setDatabaseCount("0");
                        userData.setUserDatabase("null");

                        if (entryCounter() > 0) {
                            writer.write("\n");
                        }
                        int lines = entryCounter();
                        writer.write("E" + String.valueOf(++lines) +seperator + userData.getDatabaseCount()+seperator+userData.getUserDatabase()+ seperator + userData.getFirstName() + seperator + userData.getLastName() + seperator + userData.getUsername() + seperator + userData.getPassword() + seperator + userData.getSecurityQuestion() + seperator + userData.getSecurityAnswer());
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    try {
                        Scanner myScanner = new Scanner(System.in);
                        String username, password;
                        String query;
                        System.out.println("Username:");
                        username = myScanner.nextLine();
                        System.out.println("Password:");
                        password = getMd5(myScanner.nextLine());

                        File myObj = new File("UserInfo.txt");
                        Scanner myReader = new Scanner(myObj);
                        while (myReader.hasNextLine()) {
                            String data = myReader.nextLine();
                            if (data.contains(username + "+" + password)) {
                                String[] security = data.split("\\+");
                                System.out.println("Security question:" + security[7] + "?");
                                System.out.println("Answer: ");
                                if (getMd5(myScanner.nextLine()).equals(security[8])) {
                                    UserData userData= new UserData();
                                    userData.setUserId(security[0]);
                                    userData.setDatabaseCount(security[1]);
                                    userData.setUserDatabase(security[2]);
                                    userData.setFirstName(security[3]);
                                    userData.setLastName(security[4]);
                                    userData.setUsername(security[5]);
                                    userData.setPassword(security[6]);
                                    userData.setSecurityQuestion(security[7]);
                                    userData.setSecurityAnswer(security[8]);
                                    System.out.println("SignIn successfull");
                                    System.out.println("Database options");
                                    System.out.println("1.Create");
                                    System.out.println("2.Insert");
                                    System.out.println("3.Select");
                                    System.out.println("4.Update");
                                    System.out.println("5.Delete");
                                    System.out.println("Choice:");
                                    int queryChoice=myScanner.nextInt();
                                    switch (queryChoice){
                                        case 1 :{
                                            int queryResult;
                                            Scanner myscanner1=new Scanner(System.in);
                                            System.out.println("Write query");
                                            query=myscanner1.nextLine();
                                            Query queryObj=new Query();
                                            FileOperations fileops= new FileOperations();
                                            queryResult=queryObj.createQuery(userData,query,security,fileops);
                                            if (queryResult==1){
                                                System.out.println("database created");
                                            }
                                            else {
                                                System.out.println("database not created");
                                            }
                                            break;
                                        }
                                        case 2 :{
                                            int queryResult;
                                            Scanner myscanner1=new Scanner(System.in);
                                            System.out.println("Write query");
                                            query=myscanner1.nextLine();
                                            Query queryObj=new Query();
                                            queryResult=queryObj.insertIntoTable(userData,query,security);
                                            if (queryResult==1){
                                                System.out.println("Insertion done");
                                            }
                                            else {
                                                System.out.println("Insertion failed");
                                            }
                                            break;

                                        }

                                        case 3:{
                                            int queryResult;
                                            Scanner myscanner1=new Scanner(System.in);
                                            System.out.println("Write query");
                                            query=myscanner1.nextLine();
                                            Query queryObj=new Query();
                                            queryObj.selectFromTable(userData,query,security);

                                        }
                                        case 4:{
                                            Md5 m= new Md5();
                                            m.getMd5("password@1234");
                                        }
                                        case 5:{
                                            int queryResult;
                                            Scanner myscanner1=new Scanner(System.in);
                                            System.out.println("Write query");
                                            query=myscanner1.nextLine();
                                            Query queryObj=new Query();
                                            queryObj.deleteTable(userData,query,security);
                                        }

                                    }
                                }
                            }
                            else {
                                System.out.println("SignIn unsucessfull");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 ->{
                    System.exit(-1);
                }
                default -> System.exit(-1);
            }
        }
    }
}