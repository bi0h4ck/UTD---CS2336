/*
Create a custom exception called HexFormatException.  Create a function that converts a hex string to a decimal number.
Implement the method to throw a HexFormatException if the string is not a hex string.
Create a driver program to test the conversion function and the exception handling.

Created by Diem Pham 2/28/17
 */


import java.util.Scanner;

public class HexConversion {
    public static void main(String[] args) throws HexFormatException{
        Scanner scanner = new Scanner(System.in);
        String hex;

        System.out.println("Enter a hex number: ");
        hex = scanner.nextLine();
        boolean isHex = isHexNumber(hex);
        if(!isHex)
			throw new HexFormatException("Invalid hex format");
        else{
            System.out.println("Hex to Decimal of " + hex + " is: " + hexToDecimal(hex));
        }
    }

    public static boolean isHexNumber(String s){
        try{
            Long.parseLong(s, 16);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }

    public static int hexToDecimal(String s) throws HexFormatException{
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for(int count = 0; count < s.length(); count++){
            char c = s.charAt(count);
            int i = digits.indexOf(c);
            val = 16 * val + i;
        }
        return val;
    }
}

class HexFormatException extends Exception {
    public HexFormatException(){
        super("Illegal hex character");
    }

    public HexFormatException(String msg){
        super(msg);
    }


}