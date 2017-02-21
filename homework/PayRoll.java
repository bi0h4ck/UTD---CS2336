//4.23 by Diem Pham

/*
Write a program that reads the following information from a file and prints a payroll statement.  For each employee, all of his/her information will be listed on one line of the file separated by spaces.

Employee’s name (e.g., Smith)
Number of hours worked in a week (e.g., 10)
Hourly pay rate (e.g., 9.75)
Federal tax withholding rate (e.g., 20%)
State tax withholding rate (e.g., 9%)
*/

import java.io.*;

public class PayRoll {
	public static void main(String []args) {
		String fileName = "payRoll.txt";
		String line = null;
		String [] w = new String[5];
		try {
			// FileReader reads text files in the default encoding
			FileReader fileReader = new FileReader(fileName);

			// wrap FileReader in BufferedReader
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				w = line.split(" ");
			}
			// close files
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open the file.");
		} catch (IOException ex) {
			//System.out.println("Error: The file can't be read");
		}

		String name = w[0];
		int workedHours = Integer.parseInt(w[1]);
		double payRate = Double.parseDouble(w[2]);
		String w3 = w[3];
		Double federalRate = Double.parseDouble(w3.replaceAll("%", "")) / 100;

		String w4 = w[4];
		Double stateRate = Double.parseDouble(w4.replaceAll("%", "")) / 100;
		
		double grossPay = workedHours * payRate;

		double federalWithholding = grossPay * federalRate;
		double stateWithholding = grossPay * stateRate;

		double totalDeduction = federalWithholding + stateWithholding;
		double netPay = grossPay - totalDeduction;

		System.out.println("Employee Name: " + name);
		System.out.println("Hours Worked: " + workedHours);
		System.out.println("Pay Rate: $" + payRate);
		System.out.println("GrossPay: $" + grossPay);
		System.out.println("Deduction: \n");
		System.out.printf("\tFederal Withholding (" + w3 + "%): $%.2f \n", federalWithholding);
		System.out.printf("\tState Withholding (" + w4 + "%): $%.2f \n", stateWithholding);
		System.out.printf("\tTotal Deduction: $%.2f \n", totalDeduction);
		System.out.printf("Net Pay: $%.2f \n", netPay);
	}
}