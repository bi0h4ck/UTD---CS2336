/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicketReservationSystem;

import java.util.Scanner;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diempham
 */
public class TicketReservationSystem {
    public static void main(String []args) throws IOException {
        Scanner input = new Scanner(System.in);
        
        
        
        int num;
        String fileName = null;
        
        
        
        
        do {
            System.out.println("1. Auditorium1");
            System.out.println("2. Auditorium2");
            System.out.println("3. Auditorium3");
            System.out.println("4. Exit");
            System.out.println("Please type 1, 2, 3 to choose auditorium number or 4 to exit: ");
            num = input.nextInt();  
        } while (num < 1 || num > 4);
        
        if (num == 1) {
            fileName = "A1.txt";
        }
        if (num == 2) {
            fileName = "A2.txt";
        }
        if (num == 3) {
            fileName = "A3.txt";
        }
        
        int row = rowReader(fileName);
        int col = columnReader(fileName);
        
        String [][]seatArray = showSeatArangement(fileName, row, col);
        
        System.out.println("Row number: ");
        int rowNumber = input.nextInt() - 1;
        System.out.println("Starting seat number: ");
        int startingSeatNumber = input.nextInt() -1;
        System.out.println("Number of tickets: ");
        int numOfTickets = input.nextInt();
        
        pickSeats(seatArray, rowNumber, col, startingSeatNumber, numOfTickets);
        
    }


    public static String[][] showSeatArangement(String fileName, int row, int col) throws FileNotFoundException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String [][]seats = new String[row][col];        
        String []w = new String[col];
        int i = 0;
        String lines;
        while ((lines = bufferedReader.readLine()) != null) {
            w = lines.split("");
            for (int j = 0; j < col; j++) {
                seats[i][j] = w[j];
                //System.out.print(seats[i][j]);
            }            
            i++;            
        }
        for (int x = 0; x < row; x++){
            for (int y = 0; y < col; y++){
                System.out.print(seats[x][y]);
            }
            System.out.print("\n");
        }
        return seats;
    }
    

    
    
    public static int rowReader(String fileName) throws FileNotFoundException, IOException {
        LineNumberReader  lnr = new LineNumberReader(new FileReader(fileName));
        lnr.skip(Long.MAX_VALUE);
        int lineNumber = lnr.getLineNumber();
        //System.out.println(lnr.getLineNumber() + 1); 
        //Add 1 because line index starts at 0
        // Finally, the LineNumberReader object should be closed to prevent resource leak
        lnr.close();
        return lineNumber;
    }
    
    public static int columnReader(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));        
        String line;
        int column = 0;     
        while ((line = bufferedReader.readLine()) != null) {
            column = line.length();
        }
        return column;
    }
    
    public static void pickSeats(String [][] seatArray, int rowNumber, 
            int col, int startingSeatNumber, int numOfTicket) { 
        System.out.println(startingSeatNumber);
        //numberOfTicket = startingSeatNumber + numberOfTicket;
        String [] chosenRow = seatArray[rowNumber];
        String [] ticketBooked = new String[numOfTicket];
        
        for (int y = 0; y < col; y++) {
            System.out.print(chosenRow[y]);
        }
        
        int x = 0;
        for (int i = startingSeatNumber; i < (startingSeatNumber + numOfTicket); i++) {
            if (chosenRow[i] == ".") {
                System.out.println("Chosen seats are not available!");
                break;                
            } else {                
                ticketBooked[x] = "#";
                x++;
            }
        }
        
        
            
        }
        
    
    

    

}
    
    

        
   
    






































