package com.company;

/**
 * Created by diempham on 2/5/17.
 */

public class MyRectangle2D {
    public double x, y, width, height;

    public MyRectangle2D(){
        x = y = 0;
        width = height = 1;
    }

    public MyRectangle2D(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX(){
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getY(){
        return y;
    }

    public void getY(double y){
        this.y = y;
    }

    public double getWidth(){
        return width;
    }

    public void setWidth(double width){
        this.width = width;
    }

    public double getHeight(){
        return height;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public double getArea(){
        return width * height;
    }

    public double getPerimeter(){
        return width * 2 + height * 2;
    }

    public boolean contains(double x, double y){
        return Math.abs(x - this.x) <= width/2 && Math.abs(y - this.y) <= height/2;
    }

    public boolean contains(MyRectangle2D r){
        return contains(r.getX() - r.getWidth() / 2, r.getY() + r.getHeight() / 2) &&
                contains(r.getX() - r.getWidth() / 2, r.getY() - r.getHeight() / 2) &&
                contains(r.getX() + r.getWidth() / 2, r.getY() + r.getHeight() / 2) &&
                contains(r.getX() + r.getWidth() / 2, r.getY() - r.getHeight() / 2);
    }

    public boolean overlaps(MyRectangle2D r){
        return (Math.abs(r.getX()) - x) <= (r.getWidth() + width)/2 &&
                (Math.abs(r.getY() - y) <= (r.getHeight() + height)/2);
    }
}
