package com.company;

/*
Write a method that accepts a string as an argument and returns a sorted string.
For example, sort("acb") returns abc.  Write a test program that reads strings
from a file and writes each sorted string to a different file.

created by Diem Pham 2/28/17
*/
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

    public class SortAString{
        public static void main(String[] args) throws IOException{
            File fileName = new File("A1.txt");
            sortAString(fileName);

        }

        public static void sortAString(File fileName) throws IOException {
            Scanner scanner = new Scanner(fileName);
            File newFile = new File("newFile.txt");
            newFile.createNewFile();
            FileWriter writer = new FileWriter(newFile);

            while(scanner.hasNextLine()){
                String line = scanner.nextLine().toLowerCase();
                String[] arrayOfString = line.split(" ");
                for(String s: arrayOfString){
                    char[] arrayOfChar = s.toCharArray();
                    Arrays.sort(arrayOfChar);
                    String str = String.valueOf(arrayOfChar);
                    writer.write(str + " ");
                }
                writer.write("\n");
            }
            writer.flush();
            writer.close();
        }
    }
