// homework 3.24 by Diem Pham

/* Revise Programming Exercise 3.32 to test whether a point is on a line segment. 
Write a program that prompts the user to enter the three points for p0, p1, and p2 
and displays whether p2 is on the line segment from p0 to p1.
*/

import java.util.Scanner;

public class PointOnLineSegment {
	public static void main(String []args){
		Scanner input = new Scanner(System.in);
		double [] p = new double[6];

		System.out.println("Enter 3 points for p0, p1, and p2: ");
		for (int i = 0; i < 6; i++) {
			p[i] = input.nextDouble();
		}

		double result = (p[2] - p[0]) * (p[5] - p[1]) - (p[4] - p[0]) * (p[3] - p[1]);
		if(result == 0 
			&& ((p[4] < p[0]) || (p[4] < p[2])) 
			&& ((p[5] < p[1]) || (p[5] < p[3]))) {
			System.out.println("(" + p[4] + ", " + p[5] + ") is on segment line" + 
				" from (" + p[0] + ", " + p[1] + ") to (" + p[2] + ", " + p[3] + ")");
		}
		else {
			System.out.println("(" + p[4] + ", " + p[5] + ") is not on segment line" +
				" from (" + p[0] + ", " + p[1] + ") to (" + p[2] + ", " + p[3] + ")");
		}
	}
}