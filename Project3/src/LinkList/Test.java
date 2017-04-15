package LinkList;

//Diem Pham dtp160130
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {

    public void run() throws IOException {
        String[] files = {"A1base.txt", "A2base.txt", "A3base.txt"};
        Scanner scanner = new Scanner(System.in);

        Result result;
        Result reservation = new Result();
        String fileName;
        int auditoriumNumber;
        int row;
        int col;
        Boolean wantToReserve;
        Boolean backToMainMenu = false;
        do {
            //Show the main menu and get user input
            int userInput = showMainMenu(scanner, files);
            while (userInput == 2) {
                //display auditorium menu and get user input
                auditoriumNumber = auditoriumMenu(scanner);
                fileName = files[auditoriumNumber - 1];
                row = numOfRow(fileName);
                col = numOfColumn(fileName);
                displaySeatArrangement(fileName, row, col);
                userInput = showMainMenu(scanner, files);
            }
            //if user wants to reserve seat
            auditoriumNumber = auditoriumMenu(scanner);
            fileName = files[auditoriumNumber - 1];
            row = numOfRow(fileName);
            col = numOfColumn(fileName);
            displaySeatArrangement(fileName, row, col);

            //read the chosen auditorium into Result class
            result = readAuditorium(fileName, row, col);

            //Get the row number from user
            int rowNumber = getRowNumber(scanner, row);

            //Get starting seat number from user
            int startingSeatNumber = getSeatNumber(scanner, col);

            //Get number of tickets from user
            int numOfTickets = getNumOfTicket(scanner, col);

            //Check whether the user's choice is met
            Boolean isEnoughSeat = isEnoughSeat(result, rowNumber, startingSeatNumber, numOfTickets);

            //if the requirement is not met -> recommend seat
            if (!isEnoughSeat) {
                //possible starting seat
                if(result.openList.head == null){
                    System.out.println("Your requirement is not met in this auditorium.");
                    System.out.println("Please choose another auditorium");
                } else {
                    //Find all possible starting seats
                    LinkedList possibleStartingSeat = possibleStartingSeat(result, numOfTickets);
                    //Find the best seat in the entire auditorium
                    DoubleLinkNode bestSeat = bestSeat(possibleStartingSeat, col, row);
                    //If the best seat doesn't exist
                    if(bestSeat.getRow() == 0){
                        System.out.println("Your requirement is not met in this auditorium.");
                        System.out.println("Please choose another auditorium");
                    } else {
                        //Print best seats option
                        printSeatOption(bestSeat, numOfTickets);
                        wantToReserve = reserveTicket(scanner);
                        if (wantToReserve){
                            reservation = takeReservation(result, bestSeat, numOfTickets);
                            //update the auditorium file
                            writeDataToFile(fileName, reservation.reservedList, 1, 1, row, col);
                        }

                    }
                }
                backToMainMenu = true;
                //If the requirement is met
            } else {
                wantToReserve = reserveTicket(scanner);
                if (wantToReserve) {
                    reservation = takeReservation(result, result.openList.getHead().getNodeAt(rowNumber, startingSeatNumber), numOfTickets);
                    //update the auditorium file
                    writeDataToFile(fileName, reservation.reservedList, 1, 1, row, col);
                }
                backToMainMenu = true;
            }

        } while (backToMainMenu);
    }
    class Result{
        public LinkedList reservedList;
        public LinkedList openList;

        public Result(){
        }

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

    //remember to trim space
    public Result readAuditorium(String fileName, int row, int col) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        LinkedList reservedList = new LinkedList();
        LinkedList openList = new LinkedList();
        String line;
        int r = 1;
        while((line = bufferedReader.readLine()) != null){
            if(line.length() > 0){
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
            if(line.length() > 0){
                System.out.println(count + line);
                count++;
            }
        }
    }

    // show the main menu and get user input
    public int showMainMenu(Scanner scanner, String[] files) throws IOException {
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
            printReport(files);
            System.exit(1);
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
            if (cur.getRow() == rowNumber && cur.getSeat() == startingSeat) {
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

    //Find all nodes that has starting seat met the number of tickets
    public LinkedList possibleStartingSeat(Result result, int numOfTicket){
        LinkedList possibleStartingSeat = new LinkedList();
        LinkedList openList = result.openList;
        DoubleLinkNode cur = openList.head;
        DoubleLinkNode tmp = cur;
        int count = 1;
        //int row = cur.getRow();
        if(numOfTicket == 1){
            while(cur.next != null){
                possibleStartingSeat.addNode(cur);
                cur = cur.next;
            }
            possibleStartingSeat.addNode(cur);
        //If numOfTicket > 1
        } else {
            do {
                //while next seat next to tmp seat count++
                while (tmp.next != null && count < numOfTicket && (tmp.next.getRow() == tmp.getRow()) && (tmp.next.getSeat() - tmp.getSeat() == 1)) {
                    count++;
                    // if the numOfTicket is met, add that node
                    if (count == numOfTicket) {
                        DoubleLinkNode newNode = new DoubleLinkNode(cur.getRow(), cur.getSeat());
                        possibleStartingSeat.addNode(newNode);
                        count = 1;
                        cur = cur.next;
                        tmp = cur.prev;
                    }
                    //if the next node is null, return the linked list
                    if (tmp.next == null)
                        return possibleStartingSeat;
                    //else move the tmp to the next
                    else if (tmp.next != null)
                        tmp = tmp.next;
                }
                //if the tmp.next.getSeat() is on the same row but not next to temp.getSeat(), get the next node
                // or the next node is not on the same row with the tmp row
                if((tmp.next != null && tmp.next.getRow() == tmp.getRow() && tmp.next.getSeat() - tmp.getSeat() != 1) ||
                (tmp.next != null && tmp.next.getRow() != tmp.getRow())){
                    cur = tmp.getNext();
                    tmp = cur;
                    count = 1;
                }
            } while (tmp.next != null);
        }
//        System.out.println("possible starting seat");
//        possibleStartingSeat.printList();
        return possibleStartingSeat;
    }

    // Find the best starting seat
    public DoubleLinkNode bestSeat(LinkedList possibleStartingSeat, int col, int row){
        if(possibleStartingSeat.head == null){
            return new DoubleLinkNode();
        } else {
            DoubleLinkNode bestSeat = possibleStartingSeat.head;
            double closestDistance = distance(bestSeat.getSeat(), col);
            double closestRow = distanceR(bestSeat.getRow(), row);
            DoubleLinkNode tmp = possibleStartingSeat.head;
            for(int i = 0; i < possibleStartingSeat.getSize() - 1; i++){
                if (distance(tmp.next.getSeat(), col) == closestDistance){
                    if(distanceR(tmp.next.getRow(), row) < closestRow)
                        bestSeat = tmp.next;
                }
                else if (distance(tmp.next.getSeat(), col) < closestDistance) {
                    closestDistance = distance(tmp.next.getSeat(), col);
                    bestSeat = tmp.next;
                }
                tmp = tmp.getNext();
            }
            return bestSeat;
        }
    }

    public double distanceR(int rowNum, int row){
        double midRow = row/1.0/2;
        double distanceR = Math.abs(rowNum - midRow);
        return distanceR;
    }

    //find distance between the first starting seat and the mid row
    public double distance(int seatNumber, int col){
        double seatNum = seatNumber/1.0;
        double midRow;
        double dRow = col / 1.0;
//        if(col % 2 == 0){
//            midRow = dRow / 2 + 0.5;
//        } else{
            midRow = dRow / 2;
//        }
        double distance = Math.abs(seatNum - midRow);
        return distance;
    }


    // ask if user wants to reserve the tickets
    public boolean reserveTicket(Scanner scanner) {
        System.out.println("Would you like to reserve your tickets? Y/N");
        char yOrN = scanner.next().trim().charAt(0);
        if (yOrN == 'y' || yOrN == 'Y') return true;
        else return false;
    }


    //print out the recommended seats
    public void printSeatOption(DoubleLinkNode bestSeat, int numOfTickets) {
        System.out.println("The seats you chose are not available.");
        System.out.println("Best seats available at row " + bestSeat.getRow() + " are: ");

        for (int i = bestSeat.getSeat(); i < bestSeat.getSeat() + numOfTickets; i++) {
            System.out.println("seat number: " + i);
        }
    }

    public Result takeReservation(Result result, DoubleLinkNode bestSeat, int numOfTicket){
        LinkedList listOfSeat1 = listOfSeat(bestSeat, numOfTicket);
        LinkedList listOfSeat2 = listOfSeat(bestSeat, numOfTicket);
        return new Result(result.reservedList.insertLinkList(listOfSeat1), result.openList.removeNodes(listOfSeat2));
    }

    //put the required seats in a linked list and process the open list1
    public LinkedList listOfSeat(DoubleLinkNode bestSeat, int numOfTicket){
        LinkedList listOfSeat = new LinkedList();
        int row = bestSeat.row;
        int seat = bestSeat.seat;
        DoubleLinkNode currentSeat;
        for(int i = 0; i < numOfTicket; i++){
            currentSeat = new DoubleLinkNode(row, seat);
            listOfSeat.addNode(currentSeat);
            seat++;
        }
        return listOfSeat;
    }

    public int[] countSeats(String fileName) throws IOException {
        int row = numOfRow(fileName);
        int col = numOfColumn(fileName);
        Result result = readAuditorium(fileName, row, col);
        return new int[] {result.openList.getSize(), result.reservedList.getSize()};
    }

    //Recursive function convert nodes to string
    public String convertNodeIntoSeat(DoubleLinkNode head, String line, int rowStart, int seat, int row, int col) throws IOException{
        int r = head.getRow();
        int s = head.getSeat();

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
        return convertNodeIntoSeat(head, line, rowStart, seat, row, col);
    }

    //write data to file
    public void writeDataToFile(String fileName, LinkedList reservedList, int rowStart, int seat, int row, int col)
            throws IOException {
        String tmp = "tmp.txt";
        String line = "";
        FileWriter writer = new FileWriter(tmp, true);
        BufferedWriter out = new BufferedWriter(writer);
        String stringOfNodes = convertNodeIntoSeat(reservedList.getHead(), line, rowStart, seat, row, col);
        out.write(stringOfNodes);
        out.flush();
        out.close();

        File oldFile = new File(fileName);
        oldFile.delete();

        File newFile = new File(tmp);
        newFile.renameTo(oldFile);
    }

    //Print report
    public void printReport(String []files) throws IOException{
        String [] auditoriumName = {"Auditorium 1", "Auditorium 2", "Auditorium 3"};
        int ticketPrice = 7;
        int totalReservedSeats = 0;
        int totalOpenSeats = 0;
        int moneyEarn;
        int totalMoneyEarn = 0;
        System.out.println("\t\t\t\tOpen   Reserved  Money Earn" );
        for (int i = 0; i < files.length; i++){
            String file = files[i];

            int []count = countSeats(file);
            moneyEarn = count[1] * ticketPrice;
            totalMoneyEarn += moneyEarn;
            totalOpenSeats += count[0];
            totalReservedSeats += count[1];

            System.out.println(auditoriumName[i] + "\t" + count[0] +
                    "\t\t" + count[1] + "\t\t" + moneyEarn);

        }
        System.out.println("Total \t\t\t" + totalOpenSeats + "\t\t"
                + totalReservedSeats + "\t\t" + totalMoneyEarn);
    }

}


































