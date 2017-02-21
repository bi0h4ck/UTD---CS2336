//Homework 2.21 by Diem Pham

/* Write a program that reads in an investment amount, annual interest rate, and number of years, 
and displays the future investment value using the following formula:
futureInvestmentValue = investmentAmount * (1 + monthlyInterestRate) ^ (numberOfYears*12)
*/

import java.util.Scanner;
import java.text.DecimalFormat;

public class AccumulatedValue {
	public static void main(String []args){
		Scanner input = new Scanner(System.in);

		System.out.println("Enter investment amount: ");
		double investmentAmount = input.nextDouble();

		System.out.println("Enter annual interest rate in percentage: ");
		double annualInterestRate = input.nextDouble();
		double monthlyInterestRate = annualInterestRate / 1200;

		System.out.println("Enter number of years: ");
		int numberOfYears = input.nextInt();

		double result = investmentAmount * Math.pow(1 + monthlyInterestRate, numberOfYears * 12);

		DecimalFormat f = new DecimalFormat("##.##");
		System.out.println("Accumulated value is $" + f.format(result));
	}
}