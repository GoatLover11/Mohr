package utils;

public class Point {
    double x, y;

    public Point (){}

    public Point (double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public int getrX(){
        return (int) Math.round(x);
    }

    public double getY(){
        return y;
    }

    public int getrY(){
        return (int) Math.round(y);
    }

    /*
        N szeres növelő
     */
    public static Point nline (Point p1, Point p2, double n){
        return new Point();
    }
}
