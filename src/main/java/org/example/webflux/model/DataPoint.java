package org.example.webflux.model;

public class DataPoint {
    private double x;
//    private int y;

    public DataPoint(double x/*, int y*/) {
        this.x = x;
//        this.y = y;
    }

    public DataPoint() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
}
