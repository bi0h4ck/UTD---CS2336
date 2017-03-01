
/*
Write a program that will count the number of characters, 
words and lines in a file.  Words are separated by whitespace characters.
Created by Diem Pham 2/28/17
*/

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CharWordLineCounter {
    public static void main(String[] args) throws IOException{
        File fileName = new File("A1.txt");
        counter(fileName);

    }

    public static void counter(File fileName) throws IOException{
        int numOfChar = 0;
        int numOfLine = 0;
        int numOfWord = 0;
        String line;
        Scanner scanner = new Scanner(fileName);
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            String[] arrayOfString = line.split(" ");
            numOfWord += arrayOfString.length;
            for (String w : arrayOfString){
                char[] arrayOfChar = w.toCharArray();
                numOfChar += arrayOfChar.length;
            }
            numOfLine++;
        }
        System.out.println("Number of char: " + numOfChar);
        System.out.println("Number of word: " + numOfWord);
        System.out.println("Number of line: " + numOfLine);
    }
}