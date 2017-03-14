public static int recommendSeats(String [][] seatArray, int col, int rowNumber, int numOfTicket) {
        String [] chosenRow = seatArray[rowNumber];
        //System.out.println("col: " + col);
        int midRow = col / 2;
        int recommendSeat = 0;
//        System.out.println("midRow: "+midRow);
//        System.out.println("chosenRow[i]: " + chosenRow[midRow]);
        int left = 0; 
        int right = 0;
        int lIndex = 0;
        int rIndex = 0;
        int flIndex = 0;
        int frIndex = 0;
        int fLeft = 0;
        int fRight = 0;
        for(int i = midRow -1; i > -1; i--){
            if(chosenRow[i].equals("#")) {
                for(int j = 0; j < numOfTicket; j ++) {
                    if (chosenRow[i - j].equals("#")) {
                        left +=1;
                        System.out.println("left: "+ left);
                    }                    
                    else {
                        break;
                    }                                           
                }
                if (left < numOfTicket && (i == midRow - 1)) {
                    flIndex = midRow - 1;
                    System.out.println("flIndex: " + flIndex);
                    fLeft = left;
                    System.out.println("fLeft: " + fLeft);
                    left = 0;
                }
                if (left == numOfTicket) {
                    lIndex = i;
                    System.out.println("lindex: " + lIndex);
                    break;
                } else 
                    lIndex = 0;
            }                  
        }                    
        for (int i = midRow; i < col; i++) {
            if(chosenRow[i].equals("#")) {
                for (int j = 0; j < numOfTicket; j++){
                    if(chosenRow[i + j].equals("#")){
                        right +=1;
                        System.out.println("right: " + right);
                    }                       
                    else {                        
                        break;
                    }
                }
                if (right < numOfTicket && (i == midRow)){
                    frIndex = midRow;
                    System.out.println("frIndex: " + frIndex);
                    fRight = right;
                    System.out.println("fRight: " + fRight);
                    right = 0;
                }
                if (right == numOfTicket){
                    rIndex = i;
                    System.out.println("rIndex: " + rIndex);
                    break;
                } else
                    rIndex = 0;
            }
        }
//        System.out.println("lIndex: "+ lIndex);
//        System.out.println("rIndex: " + rIndex);
        if ((flIndex == midRow -1) && (frIndex == midRow)) {
            int totalSeats = fLeft + fRight;
            if (totalSeats <= numOfTicket){
                recommendSeat = midRow - 1 - numOfTicket/2;
            }
        } 
        else if ((lIndex == 0 && rIndex == 0))      
            System.out.println("Your number of required seats is not met!");
        else if ((lIndex == 0 && rIndex != 0) || ((rIndex - midRow) < (midRow - lIndex)))
            recommendSeat = rIndex;
        else if ((rIndex == 0 && lIndex != 0) || ((rIndex - midRow) > (midRow - lIndex)))
            recommendSeat = lIndex - numOfTicket;
        

//        if (lIndex == 0 || ((rIndex - lIndex != 1) && ((rIndex - midRow) < (midRow - lIndex))))
//            recommendSeat = rIndex + 1;
//        if (rIndex == 0 || ((rIndex - lIndex != 1) && ((rIndex - midRow) > (midRow - lIndex))))
//            recommendSeat = (lIndex - numOfTicket) + 1;
       
        return recommendSeat;
    } 