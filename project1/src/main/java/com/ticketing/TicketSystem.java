package com.ticketing;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class TicketSystem {

    class SearchResult {
        public int numOfAvailableSeats;
        public int startingIndex;
        public int endingIndex;
        public boolean isEnoughSeat;

        public SearchResult(int mNumOfAvailableSeats, int mStartingIndex, int mEndingIndex, boolean mIsEnoughSeat) {
            endingIndex = mEndingIndex;
            numOfAvailableSeats = mNumOfAvailableSeats;
            startingIndex = mStartingIndex;
            isEnoughSeat = mIsEnoughSeat;
        }

    }

    class TResult {

        public boolean isEnoughSeat;
        public int recommendSeat;
        public TResult(int mRecommendSeat, boolean mIsEnoughSeat){
            recommendSeat = mRecommendSeat;
            isEnoughSeat = mIsEnoughSeat;
        }
    }



    public void run() throws Exception {
        boolean backToMainMenu = false;
        boolean wantToReserve;
        do {
            Scanner scanner = new Scanner(System.in);

            //Show the main menu and get user input
            String getUserInput = Integer.toString(showMainMenu(scanner)).trim();
            String fileName = "A"+getUserInput+".txt";
            //Read file, get number of row, number of column of that file
            String path = getClass().getClassLoader().getResource(fileName).getPath();
            FileReader fileReader = new FileReader(path);
            int row = numOfRow(fileReader);
            System.out.println("row: " + row);
            int col = numOfColumn(fileName);
            System.out.println("col: " + col);

            //Put the seats in that file into a 2D array of string
            String [][]arraySeat = readAuditorium(fileName, row, col);

            //Display the seat arrangement
            displaySeatArrangement(arraySeat, row, col);

            //Get the row number from user
            int rowNumber = getRowNumber(scanner, row) - 1;
            System.out.println("rowNumber: " + rowNumber);

            //Assign the chosen row into chosenRow
            String []chosenRow = arraySeat[rowNumber];

            for (int i = 0; i < chosenRow.length; i++)
                System.out.print(chosenRow[i]);
            //Get starting seat number from user
            int startingSeatNumber = getSeatNumber(scanner, chosenRow, col, rowNumber) - 1;
            System.out.println("startingSeatNumber: " + startingSeatNumber);

            //Get number of tickets from user
            int numOfTickets = getNumOfTicket(scanner, col);


            //return true if there is enough seat, and false if there is not enough seat
            Boolean isEnoughSeat = searchR(startingSeatNumber, chosenRow, numOfTickets).isEnoughSeat;
            System.out.println(isEnoughSeat);

            double dRow = col/ 1.0;
            System.out.println("middleRow: " + dRow);
            int midRow = (int)Math.round(dRow/2);
            System.out.println("89: " + midRow);
            //Call searchL, searchR, leftSearch, rightSearch method
            SearchResult searchL = searchL(midRow - 1, chosenRow, numOfTickets);
            SearchResult searchR = searchR(midRow, chosenRow, numOfTickets);
            TResult searchMiddle = searchMiddleOfRow(searchL, searchR, midRow, numOfTickets);
            //System.out.println("searchMiddle: " + searchMiddle.isEnoughSeat);
            SearchResult leftSearch = leftSearch(chosenRow, midRow, numOfTickets);
            //System.out.println("leftSearch.startingIndex: "+ leftSearch.startingIndex);
            SearchResult rightSearch = rightSearch(chosenRow, midRow, numOfTickets);
            //System.out.println("rightSearch.startingIndex: " + rightSearch.startingIndex);


            //If there is enough seat, ask if user wants to reserve the tickets, if not, back to the main menu
            if (isEnoughSeat) {
                wantToReserve = reserveTicket(scanner);
                if (wantToReserve)
                    editAuditorium(arraySeat, row, col, rowNumber, startingSeatNumber, numOfTickets, fileName);
                else
                    backToMainMenu = true;
            }

            //If there is not enough seat, and there is enough seat in the middle of the row, recommend it to user
            if (!isEnoughSeat && (searchMiddle.isEnoughSeat)) {
                printSeatOption(searchMiddle.recommendSeat, rowNumber, numOfTickets);
                wantToReserve = reserveTicket(scanner);
                if (wantToReserve) {
                    editAuditorium(arraySeat, row, col, rowNumber, searchMiddle.recommendSeat, numOfTickets, fileName);
                    break;
                } else
                    backToMainMenu = true;
            }


            if (!isEnoughSeat && (!searchMiddle.isEnoughSeat)) {
                // if there is not enough seats in the chosen row, ask if user want to go back to the main menu
                if (!leftSearch.isEnoughSeat && !rightSearch.isEnoughSeat){
                    System.out.println("There is not enough available seats that meet your requirement");
                    backToMainMenu = true;
                }
                //if there is enough seat in the left, return the seat
                if (leftSearch.isEnoughSeat && !rightSearch.isEnoughSeat){
                    printSeatOption(leftSearch.startingIndex, rowNumber,numOfTickets);
                    wantToReserve = reserveTicket(scanner);
                    if (wantToReserve) {
                        editAuditorium(arraySeat, row, col, rowNumber, leftSearch.startingIndex, numOfTickets, fileName);
                        break;
                    } else
                        backToMainMenu = true;
                }
                // if there is enough seat in the right, return the seat
                if (!leftSearch.isEnoughSeat && rightSearch.isEnoughSeat){
                    printSeatOption(rightSearch.startingIndex, rowNumber,numOfTickets);
                    wantToReserve = reserveTicket(scanner);
                    if (wantToReserve) {
                        editAuditorium(arraySeat, row, col, rowNumber, rightSearch.startingIndex, numOfTickets, fileName);
                        break;
                    } else
                        backToMainMenu = true;
                }
                // if there is enough seat in both side, pick the best seat
                if(leftSearch.isEnoughSeat && rightSearch.isEnoughSeat) {
                    int recommendSeat = bestSeat(midRow, leftSearch.endingIndex, rightSearch.startingIndex, numOfTickets);
                    printSeatOption(recommendSeat, rowNumber, numOfTickets);
                    wantToReserve = reserveTicket(scanner);
                    if (wantToReserve) {
                        editAuditorium(arraySeat, row, col, rowNumber, rightSearch.startingIndex, numOfTickets, fileName);
                        break;
                    } else
                        backToMainMenu = true;
                }

            }

        } while (backToMainMenu);

        //print report
        printReport();
    }

    //print out the recommended seats
    private void printSeatOption(int recommendSeat, int rowNumber, int numOfTickets){
        System.out.println("The seats you chose are not available.");
        System.out.println("Best seats available at row " + (rowNumber + 1) + " are: ");

        for (int i = recommendSeat; i < recommendSeat + numOfTickets; i++) {
            System.out.println("seat number: " + (i + 1));
        }
    }

    // get the row number from user input
    private int getRowNumber(Scanner scanner, int row) throws IOException {
        int rowNumber = 0;
        do {
            try {
                System.out.println("Please enter your row number: ");
                rowNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Your input is invalid.");
            }
            scanner.nextLine();
        } while (rowNumber < 1 || rowNumber > row);

        return rowNumber;
    }

    // get the starting seat number from user input
    private int getSeatNumber(Scanner scanner, String []chosenRow, int col, int rowNumber) {
        int seatNumber = 0;
        do {
            try{
                System.out.println("Please enter your starting seat number: ");
                seatNumber = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Your input is invalid");
            }
            scanner.nextLine();
        } while (seatNumber < 1 || seatNumber > col || chosenRow[seatNumber - 1].equals("."));

        return seatNumber;
    }
    // get the number of ticket
    private int getNumOfTicket(Scanner scanner, int col) throws IOException{
        int numOfTicket = 0;
        do{
            try{
                System.out.println("Please enter the number of ticket you want to reserve: ");
                numOfTicket = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Your value is invalid.");
            }
            scanner.nextLine();
        } while(numOfTicket < 1 || numOfTicket > col);

        return numOfTicket;
    }

    // show the main menu and get user input
    private int showMainMenu(Scanner scanner){
        int userInput = 0;
        do{
            try {
                System.out.println("1. Auditorium1");
                System.out.println("2. Auditorium2");
                System.out.println("3. Auditorium3");
                System.out.println("4. Exit");
                System.out.println("Please type 1, 2, 3 to choose auditorium number or 4 to exit: ");
                userInput = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Your input is invalid!");
            }
            scanner.nextLine();
        } while (userInput < 1 || userInput > 4);
        if (userInput == 4)
            System.exit(0);
        return userInput;
    }

    // ask if user wants to reserve the tickets
    private boolean reserveTicket(Scanner scanner) {
        System.out.println("Would you like to reserve your tickets? Y/N");
        char yOrN = scanner.next().trim().charAt(0);
        if (yOrN == 'y' || yOrN == 'Y') return true;
        else return false;
    }

    //Back to the main menu
    private boolean backToMainMenu(Scanner scanner){
        System.out.println("Your required number of seats are not available in this row.");
        System.out.println("Would you like to get back to the main menu? ");
        System.out.println("Y: get back to the main menu");
        System.out.println("N: exit");
        char yOrN = scanner.next().trim().charAt(0);
        if (yOrN == 'y' || yOrN == 'Y') return true;
        else return false;
    }

    // read the .txt file and store the seat info into 2D array of String
    private String[][] readAuditorium(String fileName, int row, int col) throws IOException {
        String path = getClass().getClassLoader().getResource(fileName).getPath();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String [][]seats = new String[row][col];
        String []rowOfSeat;
        int mRow = 0;
        String lines;
        while ((lines = bufferedReader.readLine()) != null) {
            rowOfSeat = lines.split("");
            for (int mCol = 0; mCol < col; mCol++) {
                seats[mRow][mCol] = rowOfSeat[mCol];
            }
            mRow++;
        }
        return seats;
    }

    //Display the seat arrangement
    private void displaySeatArrangement(String [][]arraySeat, int row, int col) {
        for (int x = 0; x < row; x++){
            for (int y = 0; y < col; y++){
                System.out.print(arraySeat[x][y]);
            }
            System.out.print("\n");
        }
    }

    // get number of row
    private int numOfRow(Reader fileReader) throws IOException {
        LineNumberReader lnr = new LineNumberReader(fileReader);
        lnr.skip(Long.MAX_VALUE);
        int lineNumber = lnr.getLineNumber();
        //System.out.println(lnr.getLineNumber() + 1);
        //Add 1 because line index starts at 0
        // Finally, the LineNumberReader object should be closed to prevent resource leak
//        lnr.close();
        return lineNumber;
    }

    // get number of column
    private int numOfColumn(String fileName) throws IOException {
        String path = getClass().getClassLoader().getResource(fileName).getPath();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        int column = 0;
        while ((line = bufferedReader.readLine()) != null) {
            column = line.length();
        }
        return column;
    }

    //search to the left from the pivot, return the number of available seats, starting seat number,
    // ending seat number, and check whether it meet the required number of tickets
    private SearchResult searchR(int pivot, String []chosenRow, int numOfRequiredSeats) {
        int numOfAvailableSeats = 0;
        int count = 1;
        while (chosenRow[pivot].equals(".") && pivot < chosenRow.length){
            pivot ++;
        }
        int availablePivot = pivot;
        while(chosenRow[availablePivot].equals("#") && count <= numOfRequiredSeats && availablePivot < chosenRow.length) {
            numOfAvailableSeats +=1;
            availablePivot ++;
            count ++;
        }
        int startingIndex = availablePivot - numOfAvailableSeats;
        int endingIndex = availablePivot - 1;
        boolean isEnoughSeat = (numOfAvailableSeats == numOfRequiredSeats);
        return new SearchResult(numOfAvailableSeats, startingIndex, endingIndex, isEnoughSeat);
    }

    //search to the right from the pivot, return the number of available seats, starting seat number,
    // ending seat number, and check whether it meet the required number of tickets
    private SearchResult searchL(int pivot, String []chosenRow, int numOfRequiredSeats) {
        int numOfAvailableSeats = 0;
        int count = 1;
        while (chosenRow[pivot].equals(".") && pivot > 0){
            pivot --;
        }
        int availablePivot = pivot + 1;
        while (chosenRow[availablePivot].equals("#") && (count <= numOfRequiredSeats) && availablePivot > 0) {
            numOfAvailableSeats += 1;
            availablePivot--;
            count++;
        }
        int startingIndex = availablePivot;
        int endingIndex = availablePivot + numOfAvailableSeats;
        boolean isEnoughSeat = numOfAvailableSeats == numOfRequiredSeats;
        return new SearchResult(numOfAvailableSeats, startingIndex, endingIndex, isEnoughSeat);
    }

    //Find the closest seat to the middle of the row while both sides meet the required number of tickets
    private int bestSeat (int midRow, int endingLeftSeat, int startingRightSeat, int numOfTicket){
        int bestSeat;
        if(midRow - endingLeftSeat <= startingRightSeat - midRow)
            bestSeat = endingLeftSeat - numOfTicket;
        else
            bestSeat = startingRightSeat;

         return bestSeat;
    }

    //find the left and right of the middle of the row to see if there are enough seats that meet the requirement
    private TResult searchMiddleOfRow(SearchResult searchL, SearchResult searchR, int midRow, int numOfRequiredTickets) {
        int recommendSeat;
        int numOfSeatEachSide;
        boolean isEnoughSeat;


        int totalSeat = searchL.numOfAvailableSeats + searchR.numOfAvailableSeats;

        if ((searchL.endingIndex + 1 == searchR.startingIndex) && (totalSeat == numOfRequiredTickets)) {
            recommendSeat = searchL.startingIndex;
            isEnoughSeat = true;
        }
        else if ((searchL.endingIndex + 1 == searchR.startingIndex) && (totalSeat > numOfRequiredTickets)) {
            numOfSeatEachSide = midRow - numOfRequiredTickets / 2;
            if(numOfSeatEachSide > searchL.numOfAvailableSeats){
                recommendSeat = searchL.startingIndex;
            }
            if(numOfSeatEachSide > searchR.numOfAvailableSeats){
                recommendSeat = midRow - (numOfRequiredTickets - searchR.numOfAvailableSeats);
            } else{
                recommendSeat = midRow - numOfSeatEachSide;
            }
            isEnoughSeat = true;
        } else{
            recommendSeat = -1;
            isEnoughSeat = false;
        }
        return new TResult(recommendSeat, isEnoughSeat);
        }

    //keep searching to the left to find the starting seat number that meet the requirement
    private SearchResult leftSearch(String []chosenRow, int midRow, int numOfTickets){
        boolean isEnoughSeat = true;
        int startingIndex = 0;
        int endingIndex = 0;
        int numOfAvailableSeat = 0;
        int pivot = midRow - 1;

        SearchResult searchL = searchL(pivot, chosenRow, numOfTickets);
        while (pivot > 0 && !searchL.isEnoughSeat){
            searchL = searchL(pivot, chosenRow, numOfTickets);
            pivot --;
        }

        System.out.println("pivot - leftSearch: "+ pivot);
        System.out.println(isEnoughSeat = searchL.isEnoughSeat);
        System.out.println(numOfAvailableSeat = searchL.numOfAvailableSeats);
        startingIndex = pivot;
        endingIndex = pivot + numOfAvailableSeat;

        return new SearchResult(numOfAvailableSeat, startingIndex, endingIndex, isEnoughSeat);
    }
    //keep searching to the right to find the starting seat number that meet the requirement
    private SearchResult rightSearch(String []chosenRow, int midRow, int numOfTickets){
        int pivot = midRow;
        SearchResult searchR = searchR(pivot, chosenRow, numOfTickets);
        while (pivot < chosenRow.length && !searchR.isEnoughSeat){
            searchR = searchL(pivot, chosenRow, numOfTickets);
            pivot ++;
        }

        System.out.println("pivot - rightSearch: "+ pivot);
        boolean isEnoughSeat = searchR.isEnoughSeat;
        int startingIndex = pivot;
        int endingIndex = pivot + numOfTickets - 1;
        int numOfAvailableSeat = searchR.numOfAvailableSeats;
        return new SearchResult(numOfAvailableSeat, startingIndex, endingIndex, isEnoughSeat);
    }

//edit the seat info when the user wants to reserve the tickets
    private void editAuditorium (String [][]seatArray, int row, int col, int rowNumber, int recommendedSeat, int numOfTicket, String fileName) throws IOException {
        String tmp = "tmp.txt";
        BufferedWriter out = new BufferedWriter(new FileWriter(tmp));
        for (int i = recommendedSeat; i < recommendedSeat + numOfTicket; i ++){
            seatArray[rowNumber][i] = seatArray[rowNumber][i].replace("#", ".");
        }
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                out.write(seatArray[i][j]);
            }
            out.write("\n");
        }
        out.flush();
        out.close();

        File oldFile = new File(fileName);
        oldFile.delete();

        File newFile = new File(tmp);
        newFile.renameTo(oldFile);

    }

    //counting open seats and reserved seats
    private int[] countSeats(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);

        int col = numOfColumn(fileName);
        int row = numOfRow(fileReader);
        String [][] arraySeat = readAuditorium(fileName, row, col);
        int openSeats = 0;
        int reservedSeats = 0;

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (arraySeat[i][j].equals("#")) {
                    openSeats +=1;
                } else {
                    reservedSeats +=1;
                }
            }
        }
        return new int[] {openSeats, reservedSeats};
    }

    // print the report
    private void printReport() throws IOException{
        String [] auditoriumName = {"Auditorium 1", "Auditorium 2", "Auditorium 3"};
        int ticketPrice = 7;
        int totalReservedSeats = 0;
        int totalOpenSeats = 0;
        int moneyEarn;
        int totalMoneyEarn = 0;
        for (int i = 1; i < 4; i++){
            int []count = countSeats("A" + Integer.toString(i) + ".txt");
            moneyEarn = count[1] * ticketPrice;
            totalMoneyEarn += moneyEarn;
            totalOpenSeats +=count[0];
            totalReservedSeats +=count[1];

            System.out.println(auditoriumName[i] + "\t" + count[0] +
                    "\t" + count[1] + "\t" + moneyEarn);

        }
        System.out.println("Total \t" + totalOpenSeats + "\t"
                + totalReservedSeats + "\t" + totalMoneyEarn);
    }

}
