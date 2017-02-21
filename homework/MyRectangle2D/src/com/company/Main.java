
/*
Diem Pham
NET id: dtp160130
CS2336.002
 */

package com.company;

public class Main {

    public static void main(String[] args) {
	    MyRectangle2D rectangle = new MyRectangle2D(2, 2, 5.5, 4.9);
	    System.out.println("Area is: " + rectangle.getArea());
	    System.out.println("Perimeter is: " + rectangle.getPerimeter());
	    System.out.println("Rectangle contains (3, 3): " + rectangle.contains(3, 3));
	    System.out.println("Rectangle contains a new rectangle(4, 5, 10.5, 3.2): " +
                rectangle.contains(new MyRectangle2D(4, 5, 10.5, 3.2)));
	    System.out.println("Rectangle overlaps a new rectangle(3, 5, 2.3, 5.4)" +
                rectangle.overlaps(new MyRectangle2D(3, 5, 2.3, 5.4)));
    }
}

















