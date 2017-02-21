import java.util.Scanner;

public class CCVerifying {
	public static void main (String []args) {
		String line;
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter a credit card number: ");
		line = scanner.next();
		
		int lengthOfLine = line.length();
		int []arrayOfInt = stringToArrayOfInt(line, lengthOfLine);

		
		
		
		

	}



	public static int [] stringToArrayOfInt(String line, int lengthOfLine) {
		String []arrayOfString = line.split("");
		int []arrayOfInt = new int[lengthOfLine];
		for (int i = 0; i < lengthOfLine; i++) {
			arrayOfInt[i] = Integer.parseInt(arrayOfString[i]);
		}
		return arrayOfInt;
	}

	public static boolean isLongEnough(int lengthOfLine) {
		boolean isLongEnough = true;
		if (lengthOfLine < 13 || lengthOfLine > 16) {
			System.out.println("The credit card number must be between 13 and 16 number long.");
			isLongEnough = false;
		}
		return isLongEnough;
	}


	public static boolean isValidPattern(int []arrayOfInt) {
		boolean isValidPattern = false;
		int firstDigit = arrayOfInt[0];
		int secondDigit = arrayOfInt[1];

		if (firstDigit == 3) {
			if (secondDigit == 7)
				isValidPattern = true;
			else 
				System.out.println("This credit card number is not valid.");
		} if (firstDigit == 4 || firstDigit == 5 || firstDigit == 6) {
			isValidPattern = true;
		} else {
			System.out.println("This credit card number is not valid.");
		}

		return isValidPattern;
	}

	public static int sumOfDoubleEvenPlace(int []arrayOfInt, int lengthOfLine){
		int sum;
		for (int i = lengthOfLine - 1; i > -1; i -=2) {
			int ele = getDigit(arrayOfInt[lengthOfLine - 1] * 2);
			sum += ele;
		}			
		return sum;
		}
	}

	public static int getDigit(int number) {
		String  num = String.valueOf(num);
		int sum = 0;
		for (int i = 0; i < number.length(); i++) {
			int j = Character.digit(num.charAt(i), 10);
			sum += j;
		}
		return sum;
	}

	public static int sumOfOddPlace(int []arrayOfInt){

	}
}

















