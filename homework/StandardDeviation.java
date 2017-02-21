/* homework 5.45 by Diem Pham
In business applications, you are often asked to compute the mean and standard deviation of data. 
The mean is simply the average of the numbers. The standard deviation is a statistic that tells you 
how tightly all the various data are clustered around the mean in a set of data.  
Write a program that prompts the user to enter ten numbers, and displays the mean and standard deviations 
of these numbers using the formula on pg. 200.  
*/

import java.util.Scanner;

public class StandardDeviation {
	public static void main(String []args) {
		int NUM_OF_INPUT = 10;
		double []numbers = new double[NUM_OF_INPUT];
		double total = 0.0;
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter 10 numbers: ");
		for (int i = 0; i < NUM_OF_INPUT; i++){
			double input = scanner.nextDouble();
			numbers[i] = input;
			total += input;
		}

		double mean = mean(total, NUM_OF_INPUT);
		double standardDeviation = standardDeviation(mean, numbers, NUM_OF_INPUT);

		System.out.println("The mean is " + mean);
		System.out.printf("The standard deviation %.5f", standardDeviation);

	}

	public static double mean(double total, int numOfInput) {
		double mean = total / numOfInput;
		return mean;
	}

	public static double standardDeviation(double mean, double []input, int numOfInput){
		double sumOfx_mean = 0.0;
		for (int i = 0; i < numOfInput; i++){
			double x_mean = input[i] - mean;
			sumOfx_mean += Math.pow(x_mean, 2);
		}

		double standardDeviation = Math.sqrt(sumOfx_mean/(numOfInput - 1));

		return standardDeviation;
	}

}














