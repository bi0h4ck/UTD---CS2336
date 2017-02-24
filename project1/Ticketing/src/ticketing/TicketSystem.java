package ticketing;

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

        public TResult(int mRecommendSeat, boolean mIsEnoughSeat) {
            recommendSeat = mRecommendSeat;
            isEnoughSeat = mIsEnoughSeat;
        }
    }


    public void run() throws Exception {
        boolean backToMainMenu = false;
        boolean wantToReserve;
        String[] files = {"A1.txt", "A2.txt", "A3.txt"};
        do {
            Scanner scanner = new Scanner(System.in);

            //Show the main menu and get user input
            int getUserInput = showMainMenu(scanner);
            String fileName = files[getUserInput - 1];

            //Read file, get number of row, number of column of that file
            FileReader fileReader = new FileReader(fileName);

            int row = numOfRow(fileReader);
            //System.out.println("row: " + row);
            int col = numOfColumn(fileName);
            //System.out.println("col: " + col);

            //Put the seats in that file into a 2D array of string
            String[][] arraySeat = readAuditorium(fileName, row, col);

            //Display the seat arrangement
            displaySeatArrangement(arraySeat, row, col);

            //Get the row number from user
            int rowNumber = getRowNumber(scanner, row) - 1;
            //System.out.println("rowNumber: " + rowNumber);

            //Assign the chosen row into chosenRow
            String[] chosenRow = arraySeat[rowNumber];

            for (int i = 0; i < chosenRow.length; i++)
                System.out.print(chosenRow[i]);
            //Get starting seat number from user
            int startingSeatNumber = getSeatNumber(scanner, chosenRow, col, rowNumber) - 1;


            //Get number of tickets from user
            int numOfTickets = getNumOfTicket(scanner, col);


            //return true if there is enough seat, and false if there is not enough seat
            Boolean isEnoughSeat = searchR(startingSeatNumber, chosenRow, numOfTickets).isEnoughSeat;

            double dRow = col / 1.0;
            int midRow = (int) Math.round(dRow / 2);

            //Search in the middle of the row
            TResult searchMiddle = searchMiddleOfRow(chosenRow, midRow, numOfTickets);
            //Search to the left
            SearchResult leftSearch = leftSearch(chosenRow, midRow, numOfTickets);
            //Search to the right
            SearchResult rightSearch = rightSearch(chosenRow, midRow, numOfTickets);


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
                if (!leftSearch.isEnoughSeat && !rightSearch.isEnoughSeat) {
                    System.out.println("There is not enough available seats that meet your requirement");
                    backToMainMenu = true;
                }
                //if there is enough seat in the left, return the seat
                if (leftSearch.isEnoughSeat && !rightSearch.isEnoughSeat) {
                    printSeatOption(leftSearch.startingIndex, rowNumber, numOfTickets);
                    wantToReserve = reserveTicket(scanner);
                    if (wantToReserve) {
                        editAuditorium(arraySeat, row, col, rowNumber, leftSearch.startingIndex, numOfTickets, fileName);
                        break;
                    } else
                        backToMainMenu = true;
                }
                // if there is enough seat in the right, return the seat
                if (!leftSearch.isEnoughSeat && rightSearch.isEnoughSeat) {
                    printSeatOption(rightSearch.startingIndex, rowNumber, numOfTickets);
                    wantToReserve = reserveTicket(scanner);
                    if (wantToReserve) {
                        editAuditorium(arraySeat, row, col, rowNumber, rightSearch.startingIndex, numOfTickets, fileName);
                        break;
                    } else
                        backToMainMenu = true;
                }
                // if there is enough seat in both side, pick the best seat
                if (leftSearch.isEnoughSeat && rightSearch.isEnoughSeat) {
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
        printReport(files);
    }

    //print out the recommended seats
    private void printSeatOption(int recommendSeat, int rowNumber, int numOfTickets) {
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
    private int getSeatNumber(Scanner scanner, String[] chosenRow, int col, int rowNumber) {
        int seatNumber = 0;
        do {
            try {
                System.out.println("Please enter your starting seat number: ");
                seatNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Your input is invalid");
            }
            scanner.nextLine();
        } while (seatNumber < 1 || seatNumber > col || chosenRow[seatNumber - 1].equals("."));

        return seatNumber;
    }

    // get the number of ticket
    private int getNumOfTicket(Scanner scanner, int col) throws IOException {
        int numOfTicket = 0;
        do {
            try {
                System.out.println("Please enter the number of ticket you want to reserve: ");
                numOfTicket = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Your value is invalid.");
            }
            scanner.nextLine();
        } while (numOfTicket < 1 || numOfTicket > col);

        return numOfTicket;
    }

    // show the main menu and get user input
    private int showMainMenu(Scanner scanner) {
        int userInput = 0;
        do {
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
    private boolean backToMainMenu(Scanner scanner) {
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
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String[][] seats = new String[row][col];
        String[] rowOfSeat;
        int mRow = 0;
        String lines;
        while ((lines = bufferedReader.readLine()) != null && mRow <= row) {
            rowOfSeat = lines.split("");
            for (int mCol = 0; mCol < col; mCol++) {
                seats[mRow][mCol] = rowOfSeat[mCol];
            }
            mRow++;
        }
        return seats;
    }

    //Display the seat arrangement
    private void displaySeatArrangement(String[][] arraySeat, int row, int col) {
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                System.out.print(arraySeat[x][y]);
            }
            System.out.print("\n");
        }
    }

    // get number of row
    private int numOfRow(FileReader fileReader) throws IOException {
        LineNumberReader lnr = new LineNumberReader(fileReader);
        lnr.skip(Long.MAX_VALUE);
        int lineNumber = lnr.getLineNumber() + 1;
        //System.out.println(lnr.getLineNumber() + 1);
        //Add 1 because line index starts at 0
        // Finally, the LineNumberReader object should be closed to prevent resource leak
        lnr.close();
        return lineNumber;
    }

    // get number of column
    private int numOfColumn(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line;
        int column = 0;
        while ((line = bufferedReader.readLine()) != null) {
            column = line.length();
        }
        bufferedReader.close();
        return column;
    }

    //search to the right from the pivot, return the number of available seats, starting seat number,
    // ending seat number, and check whether it meet the required number of tickets
    private SearchResult searchR(int pivot, String[] chosenRow, int numOfRequiredSeats) {
        int numOfAvailableSeats = 0;
        int count = 1;
        int availablePivot;

        if (chosenRow[pivot].equals(".")) {
            return new SearchResult(0, pivot, pivot, false);
        } else {
            availablePivot = pivot;
            while (availablePivot < chosenRow.length && chosenRow[availablePivot].equals("#") && count <= numOfRequiredSeats) {
                numOfAvailableSeats += 1;
                availablePivot++;
                count++;
            }
            return new SearchResult(numOfAvailableSeats, pivot, pivot + numOfAvailableSeats - 1,
                    numOfAvailableSeats == numOfRequiredSeats);
        }
    }

    //search to the left from the pivot, return the number of available seats, starting seat number,
    // ending seat number, and check whether it meet the required number of tickets
    private SearchResult searchL(int pivot, String[] chosenRow, int numOfRequiredSeats) {
        int numOfAvailableSeats = 0;
        int count = 1;
        int availablePivot;

        if (chosenRow[pivot].equals(".")) {
            return new SearchResult(0, pivot, pivot, false);
        } else {
            availablePivot = pivot;
            while (availablePivot >= 0 && chosenRow[availablePivot].equals("#") && count <= numOfRequiredSeats) {
                numOfAvailableSeats += 1;
                availablePivot--;
                count++;
            }
            return new SearchResult(numOfAvailableSeats, pivot - numOfAvailableSeats + 1, pivot,
                    numOfAvailableSeats == numOfRequiredSeats);
        }
    }

    //Find the closest seat to the middle of the row while both sides meet the required number of tickets
    private int bestSeat(int midRow, int endingLeftSeat, int startingRightSeat, int numOfTicket) {
        int bestSeat;
        if (midRow - endingLeftSeat <= startingRightSeat - midRow)
            bestSeat = endingLeftSeat - numOfTicket;
        else
            bestSeat = startingRightSeat;

        return bestSeat;
    }

    //find the left and right of the middle of the row to see if there are enough seats that meet the requirement
    private TResult searchMiddleOfRow(String[] chosenRow, int midRow, int numOfTickets) {
        SearchResult searchL = searchL(midRow - 1, chosenRow, numOfTickets);
        SearchResult searchR = searchR(midRow, chosenRow, numOfTickets);
        TResult tResult = new TResult(0, false);
        int totalSeat = searchL.numOfAvailableSeats + searchR.numOfAvailableSeats;
        if (totalSeat == 0) {
            tResult = new TResult(0, false);
        } else if (totalSeat == numOfTickets) {
            if (searchL.numOfAvailableSeats == 0)
                tResult = new TResult(searchR.startingIndex, true);
            else if (searchR.numOfAvailableSeats == 0 || ((searchL.startingIndex != 0) && (searchR.startingIndex != 0)))
                tResult = new TResult(searchL.startingIndex, true);
        }
        return tResult;
    }

    //keep searching to the left to find the starting seat number that meet the requirement
    private SearchResult leftSearch(String []chosenRow, int midRow, int numOfTickets){
        SearchResult searchL;
        int pivot = midRow - 1;

        do{
            searchL = searchL(pivot, chosenRow, numOfTickets);
            if(!searchL.isEnoughSeat){
                pivot -=1;
            }
        } while(pivot >= 0 && !searchL.isEnoughSeat);
        return new SearchResult(searchL.numOfAvailableSeats, searchL.startingIndex, searchL.endingIndex, searchL.isEnoughSeat);
    }
    //keep searching to the right to find the starting seat number that meet the requirement
    private SearchResult rightSearch(String []chosenRow, int midRow, int numOfTickets){
        SearchResult searchR;
        int pivot = midRow;
        do{
            searchR = searchR(pivot, chosenRow, numOfTickets);
            if(!searchR.isEnoughSeat){
                pivot += 1;
            }
        } while(pivot < chosenRow.length && !searchR.isEnoughSeat);
        return new SearchResult(searchR.numOfAvailableSeats, searchR.startingIndex, searchR.endingIndex, searchR.isEnoughSeat);
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
                    openSeats += 1;
                } else {
                    reservedSeats += 1;
                }
            }
        }
        return new int[] {openSeats, reservedSeats};
    }

    // print the report
    private void printReport(String []files) throws IOException{
        String [] auditoriumName = {"Auditorium 1", "Auditorium 2", "Auditorium 3"};
        int ticketPrice = 7;
        int totalReservedSeats = 0;
        int totalOpenSeats = 0;
        int moneyEarn;
        int totalMoneyEarn = 0;
        for (int i = 0; i < files.length; i++){
            String file = files[i];

            int []count = countSeats(file);
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
