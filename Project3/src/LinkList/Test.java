package LinkList;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {

    public void run() throws IOException {
        String[] files = {"A1.txt", "A2.txt", "A3.txt"};
//        do {
        Scanner scanner = new Scanner(System.in);

            //Show the main menu and get user input
        int getUserInput = showMainMenu(scanner);
        String fileName = files[getUserInput - 1];

        FileReader fileReader = new FileReader(fileName);

        int row = numOfRow(fileReader);
        //System.out.println("row: " + row);
        int col = numOfColumn(fileName);
        //System.out.println("col: " + col);

        Result result = readAuditorium(fileName, row, col);
        //result.openList.printList();

        //Display the seat arrangement
        displaySeatArrangement(fileName);

        //Get the row number from user
        int rowNumber = getRowNumber(scanner, row);


        //Get starting seat number from user
        int startingSeatNumber = getSeatNumber(scanner, col);


        //Get number of tickets from user
        int numOfTickets = getNumOfTicket(scanner, col);

        Boolean isEnoughSeat = isEnoughSeat(result, rowNumber, startingSeatNumber, numOfTickets);
        System.out.println(isEnoughSeat);

        ArrayList possibleStartingSeat = possibleStartingSeat(result, numOfTickets);
        DoubleLinkNode bestSeat = bestSeat(possibleStartingSeat, col);
        System.out.println(bestSeat.row + " " + bestSeat.seat);

    }
    class Result{
        public LinkedList reservedList;
        public LinkedList openList;

        public Result(LinkedList reservedList, LinkedList openList){
            this.reservedList = reservedList;
            this.openList = openList;
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

    public Result readAuditorium(String fileName, int row, int col) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        LinkedList reservedList = new LinkedList();
        LinkedList openList = new LinkedList();
        String line;
        int r = 1;
        while((line = bufferedReader.readLine()) != null){
            for(int count = 1; count <= col; count++){
                if (line.split("")[count - 1].equals(".")){
                    DoubleLinkNode rdln = new DoubleLinkNode(r, count);
                    reservedList.addNode(rdln);
                } else {
                    DoubleLinkNode odln = new DoubleLinkNode(r, count);
                    openList.addNode(odln);
                }
            }
            r++;
        }
        return new Result(reservedList, openList);

    }

    private void displaySeatArrangement(String fileName) throws IOException{
        String line;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        while ((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }
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
    private int getSeatNumber(Scanner scanner, int col) {
        int seatNumber = 0;
        do {
            try {
                System.out.println("Please enter your starting seat number: ");
                seatNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Your input is invalid");
            }
            scanner.nextLine();
        } while (seatNumber < 1 || seatNumber > col);

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

    public boolean isEnoughSeat(Result result, int rowNumber, int startingSeatNumber, int numOfTicket) {
        DoubleLinkNode cur = result.openList.getHead();
        int count = 0;
        int startingSeat = startingSeatNumber;
        while(count < numOfTicket && cur != null){
            if(cur.row == rowNumber && cur.seat == startingSeat){
                count++;
                startingSeat++;
                cur = cur.getNext();
            } else {
                cur = cur.getNext();
            }
        }
        if (count == numOfTicket)
            return true;
        else return false;
    }

    //Find all nodes that has starting seat met the number of tickets
    public ArrayList possibleStartingSeat(Result result, int numOfTicket){
        ArrayList possibleStartingSeat = new ArrayList();
        LinkedList openList = result.openList;
        DoubleLinkNode cur = openList.getHead();
        //DoubleLinkNode tmp = cur;
        //DoubleLinkNode endSeat = new DoubleLinkNode(0, 0);
        int count = 1;
        int row = cur.row;
        do {
            while (cur.getNext() != null && count < numOfTicket && (cur.next.row == cur.row) && (cur.next.seat - cur.seat == 1)) {
                count++;
                if (count == numOfTicket) {
                    possibleStartingSeat.add(new DoubleLinkNode(row, cur.seat - numOfTicket + 2));
                    count = 1;
                    cur = cur.getNext();
                }
                if (cur.getNext() == null)
                    return possibleStartingSeat;
                else
                    cur = cur.getNext();
            }
            if(cur.getNext() != null && cur.next.row == cur.row && cur.next.seat - cur.seat != 1){
                cur = cur.getNext();
                count = 1;
            } else if (cur.getNext() != null && cur.next.row != cur.row){
                cur = cur.getNext();
                row = cur.row;
                count = 1;
            }

        } while (cur.getNext() != null) ;

            return possibleStartingSeat;

        }


//        while (cur.getNext() != null && count < numOfTicket && (cur.next.row == cur.row) && (cur.next.seat - cur.seat == 1)) {
//            count++;
//            if(count == numOfTicket){
//                possibleStartingSeat.add(new DoubleLinkNode(row, cur.seat - numOfTicket + 1));
//                count = 0;
//            }
//            cur = cur.getNext();
//        }




//            for (count = 1; count < numOfTicket; count ++){
//
//                endSeat = tmp.getNext();
//                tmp = endSeat;
//            }
//            if((cur.row == endSeat.row) && (Math.abs(cur.seat + numOfTicket - 1) == endSeat.seat)){
//                possibleStartingSeat.add(new DoubleLinkNode(row, endSeat.seat - numOfTicket + 1));
//                //cur = cur.getNext();
//            }
//            cur = cur.getNext();



    public DoubleLinkNode bestSeat(ArrayList possibleStartingSeat, int col){
        DoubleLinkNode bestSeat = new DoubleLinkNode(((DoubleLinkNode)possibleStartingSeat.get(0)).row,
                ((DoubleLinkNode)possibleStartingSeat.get(0)).seat);
        int closestSeat = distance(((DoubleLinkNode)possibleStartingSeat.get(0)).seat, col);
        for(int i = 1; i < possibleStartingSeat.size(); i++){
            if(distance(((DoubleLinkNode)possibleStartingSeat.get(i)).seat, col) < closestSeat){
                closestSeat = distance(((DoubleLinkNode)possibleStartingSeat.get(i)).seat, col);
                bestSeat = new DoubleLinkNode(((DoubleLinkNode)possibleStartingSeat.get(i)).row,
                        ((DoubleLinkNode)possibleStartingSeat.get(i)).seat);
            }
        }
        return bestSeat;
    }

    public int distance(int seatNumber, int col){
        double dRow = col / 1.0;
        int midRow = (int) Math.round(dRow / 2);
        int distance = Math.abs(seatNumber - midRow);
        return distance;
    }

//    public DoubleLinkNode search(Result result, int numOfTicket, int numOfRow){
//        DoubleLinkNode cur = result.openList.getHead();
//        int count = 0;
//        int row = 1;
//
//        ArrayList arrayOfRow = new ArrayList();
//        while(cur.row == row){
//            arrayOfRow.add(row * 100 + cur.seat);
//        }
//
//
//
//    }
//
//    public DoubleLinkNode bestSeatOfEachRow(ArrayList array, int row, int col, int numOfTicket){
//        double dRow = col / 1.0;
//        int midRow = (int) Math.round(dRow / 2);
//        int mid = row * 100 + midRow;
//        int size = array.size();
//        int[] arrayOfIndex = new int[size];
//        for (int i = 0; i < size; i++){
//            arrayOfIndex[i] = Math.abs(mid - (int)array.get(i));
//        }
//
//    }

//    public int searchLeft(int[] arrayOfIndex, int numOfTicket){
//        int startingSeatNumber = -1;
//        int indexMin = getMinValue(arrayOfIndex) + 1;
//        for(int i = 0; i < numOfTicket; i++){
//            if(Math.abs(arrayOfIndex[indexMin] - numOfTicket + 1) ==
//                    Math.abs(arrayOfIndex[indexMin - numOfTicket - numOfTicket + 1])){
//                startingSeatNumber = indexMin;
//            }
//        }
//        return startingSeatNumber;
//    }
//
//    // getting the miniumum value
//    public static int getMinValue(int[] array) {
//        int minValue = array[0];
//        int index = 0;
//        for (int i = 1; i < array.length; i++) {
//            if (array[i] < minValue) {
//                minValue = array[i];
//                index = i;
//            }
//        }
//        return index;
//    }

    // ask if user wants to reserve the tickets
    public boolean reserveTicket(Scanner scanner) {
        char yOrN;
        do {
            System.out.println("Would you like to reserve your tickets? Y/N");
            yOrN = scanner.next().trim().charAt(0);
        } while(yOrN != 'y' || yOrN != 'Y');

        if (yOrN == 'y' || yOrN == 'Y') return true;
        else return false;

    }
}


































