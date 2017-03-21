package LinkList;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {

    public void run() throws IOException {
        String[] files = {"A1.txt", "A2.txt", "A3.txt"};
        Scanner scanner = new Scanner(System.in);

        //Show the main menu and get user input
        int getUserInput = showMainMenu(scanner);
        int auditoriumNumber = auditoriumMenu(scanner);
        String fileName = files[auditoriumNumber - 1];

        int row = numOfRow(fileName);

        int col = numOfColumn(fileName);
        

        Result result = readAuditorium(fileName, row, col);
        //result.openList.printList();

        //Display the seat arrangement
        displaySeatArrangement(fileName, row, col);

        //Get the row number from user
        int rowNumber = getRowNumber(scanner, row);


        //Get starting seat number from user
        int startingSeatNumber = getSeatNumber(scanner, col);


        //Get number of tickets from user
        int numOfTickets = getNumOfTicket(scanner, col);

        //Check whether the user's choice is met
        Boolean isEnoughSeat = isEnoughSeat(result, rowNumber, startingSeatNumber, numOfTickets);


        //possible starting seat
        ArrayList possibleStartingSeat = possibleStartingSeat(result, numOfTickets);

        //Find the best seat in the entire auditorium
        DoubleLinkNode bestSeat = bestSeat(possibleStartingSeat, col);
        System.out.println(bestSeat.row + " " + bestSeat.seat);

        result.reservedList.insertNode(bestSeat);
        result.openList.removeNode(bestSeat);

        writeDataToFile(result.reservedList, 1, 1, row, col);

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
    private int numOfRow(String fileName) throws IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(fileName));
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

    private void displaySeatArrangement(String fileName, int row, int col) throws IOException{
        System.out.print(" ");
        for (int i = 1; i <= col; i++){
            if (i < 10)
                System.out.print(i);
            else
                System.out.print(i % 10);
        }
        System.out.print("\n");
        String line;
        int count = 1;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        while ((line = bufferedReader.readLine()) != null){
            System.out.println(count + line);
            count++;
        }
    }

    // show the main menu and get user input
    public int showMainMenu(Scanner scanner) {
        int userInput = 0;
        do {
            try {
                System.out.println("1. Reserve Seats");
                System.out.println("2. View Auditorium");
                System.out.println("3. Exit");
                userInput = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Your input is invalid!");
            }
            scanner.nextLine();
        } while (userInput < 1 || userInput > 3);
        if(userInput == 3){
            System.exit(0);
        }
        return userInput;
    }

    //show auditorium menu and get user's input
    public int auditoriumMenu(Scanner scanner){
        int auditoriumNumber = 0;
        do{
            try{
                System.out.println("1. Auditorium 1");
                System.out.println("2. Auditorium 2");
                System.out.println("3. Auditorium 3");
                auditoriumNumber = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Your input must be 1, 2 or 3");
            }
            scanner.nextLine();
        } while (auditoriumNumber < 1 || auditoriumNumber > 3);
        return auditoriumNumber;
    }

    // get the row number from user input
    private int getRowNumber(Scanner scanner, int row) throws IOException {
        int rowNumber = 0;
        do {
            try {
                System.out.println("Please enter your row number: ");
                rowNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Your row number must be an integer between 1 and " + row);
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
                System.out.println("Your row number must be an integer between 1 and " + col);
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
                System.out.println("Your number of ticket must be an integer between 1 and " + col);
            }
            scanner.nextLine();
        } while (numOfTicket < 1 || numOfTicket > col);

        return numOfTicket;
    }


    public boolean isEnoughSeat(Result result, int rowNumber, int startingSeatNumber, int numOfTicket) {
        DoubleLinkNode cur = result.openList.getHead();
        int count = 0;
        int startingSeat = startingSeatNumber;
        while (count < numOfTicket && cur != null) {
            if (cur.row == rowNumber && cur.seat == startingSeat) {
                count++;
                startingSeat++;
                cur = cur.getNext();
            } else {
                cur = cur.getNext();
            }
        }
        if (count == numOfTicket)
            return true;
        else
            return false;
    }


    //Find all nodes that has starting seat met the number of tickets (>= 2)
    public ArrayList possibleStartingSeat(Result result, int numOfTicket){
        ArrayList possibleStartingSeat = new ArrayList();
        LinkedList openList = result.openList;
        DoubleLinkNode cur = openList.getHead();
        DoubleLinkNode tmp = cur;
        int count = 1;
        int row = cur.row;
        if(numOfTicket == 1){
            while(cur.next != null){
                possibleStartingSeat.add(new DoubleLinkNode(row, cur.seat));
                cur = cur.getNext();
                row = cur.row;
            }
            possibleStartingSeat.add(new DoubleLinkNode(row, cur.seat));
        } else{
            do {
                while (tmp.next != null && count < numOfTicket && (tmp.next.row == tmp.row) && (tmp.next.seat - tmp.seat == 1)) {
                    count++;
                    if (count == numOfTicket) {
                        possibleStartingSeat.add(new DoubleLinkNode(row, cur.seat));
                        count = 1;
                        cur = cur.getNext();
                        tmp = cur.getPrev();
                    }
                    if (tmp.next == null)
                        return possibleStartingSeat;
                    else if (tmp.next != null)
                        tmp = tmp.getNext();
                }
                if(tmp.next != null && tmp.next.row == tmp.row && tmp.next.seat - tmp.seat != 1){
                    cur = tmp.getNext();
                    tmp = cur;
                    count = 1;
                } else if (tmp.next != null && tmp.next.row != tmp.row){
                    cur = tmp.getNext();
                    tmp = cur;
                    row = cur.row;
                    count = 1;
                }
            } while (tmp.next != null);

        }
        return possibleStartingSeat;
    }

    // Find the best starting seat
    public DoubleLinkNode bestSeat(ArrayList possibleStartingSeat, int col){
        DoubleLinkNode bestSeat = new DoubleLinkNode(((DoubleLinkNode)possibleStartingSeat.get(0)).row,
                ((DoubleLinkNode)possibleStartingSeat.get(0)).seat);
        double closestSeat = distance(((DoubleLinkNode)possibleStartingSeat.get(0)).seat, col);
        for(int i = 1; i < possibleStartingSeat.size(); i++){
            if(distance(((DoubleLinkNode)possibleStartingSeat.get(i)).seat, col) < closestSeat){
                closestSeat = distance(((DoubleLinkNode)possibleStartingSeat.get(i)).seat, col);
                bestSeat = new DoubleLinkNode(((DoubleLinkNode)possibleStartingSeat.get(i)).row,
                        ((DoubleLinkNode)possibleStartingSeat.get(i)).seat);
            }
        }
        return bestSeat;
    }

    //find distance between the the middle of number of ticket and the mid row
    public double distance(int seatNumber, int col){
        double seatNum = seatNumber/1.0;
        double midRow;
        double dRow = col / 1.0;
        if(col % 2 == 0){
            midRow = dRow / 2 + 0.5;
        } else{
            midRow = dRow / 2;
        }
        double distance = Math.abs(seatNum - midRow);
        return distance;
    }


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

    public File writeDataToFile(LinkedList reservedList, int rowStart, int seat, int row, int col) throws IOException {
        File file = new File("tmp.txt");
        String line = "";
        FileWriter writer = new FileWriter(file, true);
        BufferedWriter out = new BufferedWriter(writer);
        String l = write(reservedList.getHead(), reservedList.getTail(), line, rowStart, seat, row, col);
        out.write(l);
        out.flush();
        out.close();
        return file;
    }

    public String write(DoubleLinkNode head, DoubleLinkNode tail, String line, int rowStart, int seat, int row, int col) throws IOException{
        int r = head.row;
        int s = head.seat;

        if(rowStart == r && seat == s){
            line = line.concat(".");
            if(head.next != null)
                head = head.getNext();
        }
        else
            line = line.concat("#");

        if(seat == col){
            if(rowStart + 1 <= row){
                line = line.concat("\n");
                rowStart++;
                seat = 0;
            } else
                return line;
        }
        seat++;
        return write(head, tail, line, rowStart, seat, row, col);
    }



}


































