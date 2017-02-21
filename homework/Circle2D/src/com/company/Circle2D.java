package com.company;

/**
 * Created by diempham on 2/6/17.
 */
public class Circle2D {
    public double x, y, radius;

    public Circle2D(double x, double y, double radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Circle2D(){
        x = y = 0;
        radius = 1;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getRadius(){
        return radius;
    }

    public double getArea(){
        return 3.14 * radius * radius;
    }

    public double getPerimeter(){
        return 2 * 3.14 * radius;
    }

    public boolean containsPoint(double x, double y){
        return Math.sqrt(Math.pow(this.x-x, 2) + Math.pow(this.x -x, 2)) < radius;
    }

    public boolean contains(Circle2D c){
        if(this.radius >radius && this.x-this.radius <= x-radius && x-radius <= this.x+ this.radius &&
                this.x-this.radius <= x+radius && x+radius <= this.x+this.radius &&
                this.y-this.radius <= y-radius && y-radius <= this.y+ this.radius && this.y-this.radius <=
                y+radius && y+radius <= this.y+this.radius)
            return true;
        else
            return false;
    }

    public boolean overlaps(Circle2D c){
        double leftXcircle = this.x-this.radius;
        double leftXc = x-radius;
        double rightXcircle = this.x+this.radius;
        double rightXc = x+radius;
        double lowYcircle = this.y-this.radius;
        double lowYc = y-radius;
        double topYcircle = this.y+this.radius;
        double topYc = y+radius;

        if( ((leftXc > leftXcircle && leftXc < rightXcircle) ||
                (rightXc > leftXcircle && rightXc < rightXcircle)) &&
                ((lowYc > lowYcircle && lowYc < topYcircle) ||
                (topYc > lowYcircle && topYc < topYcircle)) )
            return true;
        else
            return false;

    }

}


























