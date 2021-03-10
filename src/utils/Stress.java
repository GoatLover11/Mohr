package utils;

import java.util.ArrayList;

public class Stress {


    private static double Ix = 0;      //mm^4
    private static double Iy = 0;      //mm^4
    private static double Ixy = 0;      //mm^4
    private static double R = 0 /*Mohr sugár*/;     //mm
    private static double I1  = 0;      //mm^4
    private static double I2 = 0;       //mm^4

    public static double getX0() {
        return x0;
    }

    public static void setX0(double x0) {
        Stress.x0 = x0;
    }

    public static double getY0() {
        return y0;
    }

    public static void setY0(double y0) {
        Stress.y0 = y0;
    }

    private static double x0 = 0;        //mm
    private static double y0 = 0;
    private static double xs = 0;        //mm

    public static double getXs() {
        return xs;
    }

    public static void setXs(double xs) {
        Stress.xs = xs;
    }

    public static double getYs() {
        return ys;
    }

    public static void setYs(double yS) {
        Stress.ys = yS;
    }

    private static double ys = 0;
    private static double mid = 0 /*Mohr közép*/;
    private static double fi = 0;       //radián
    private static double fiDeg = 0;       //radián
    private static double A = 0 /*Test területe*/;      //mm^2
    private static double Mx = 0;       //Nm
    private static double My = 0;
    private static double Mz = 0;
    private static double F  = 0;       //N
    private static double Fx = 0;       //mm
    private static double Fy = 0;

    /*
    * I1 I2 fi
    * */
    public static void calc1(){
        mid = (Ix + Iy) / 2;
        fi = (Math.PI - Math.atan(Math.abs(Ixy) / ((Math.abs(Iy) - Math.abs(Ix)) / 2))) / 2;
        fiDeg = Math.toDegrees(fi);

        I1 = mid + Math.sqrt(((Iy - Ix) / 2));
        I2 = mid - Math.sqrt(((Iy - Ix) / 2));
        R = Math.sqrt((Ixy * Ixy) + Math.pow((Iy - Ix) / 2, 2));

        System.out.println("I1: " + I1);
        System.out.println("I2: " + I2);
        System.out.println("fi: " + fi);
    }

    /*
     * F, Mx, My, Mz
     * */
    public static void calcF(){

        Fx = Stress.toGeneral(Fx, Fy).x;
        Fy = Stress.toGeneral(Fx, Fy).y;

        Mx = F * Fy;
        My = F * Fx;

        System.out.println("Mx: " + Mx);
        System.out.println("My: " + My);
    }

    public static double getFiDeg(){
        return fiDeg;
    }

    /*
     * feszültség 1 pontra fő rendszerbe
     * */
    public static double calcStress(Point p){
        return (F / A + Mx * p.y / I1 + My * p.x / I2); //szigma [MegaPascal]

    }

    /*
     * feszültség 1 pontra fő rendszerbe listára
     * */
    public static ArrayList<Double> calcStress(ArrayList<Point> l1){

        ArrayList<Double> l2 = new ArrayList();

        for (Point p:l1) {
            l2.add(Stress.calcStress(p));
        }

        return l2;

    }

    /*
     * semleges tengely
     * Dekardba tér vissza
     * tengely metszetek eredeti kord ba
     * */
    public static void calcNeutral(){

        double gamma = Math.atan( (My * I1) / (Mx * I2) );      //semleges hajlásszög 1. tengelyhez képest
        double v0 = (F * I1) / (Mx * A);        //2. tengely metszete

        System.out.println("gamma: " + Math.toDegrees(gamma));

        Point v02 = Stress.toDekard(0.0, v0);

        double gamma2 = Math.tan(Math.abs(gamma) - Math.abs(fi)); //semleges dekárd hajlása

        y0 = v02.y - gamma2 * v02.x;     //semleges tengely eredeti kord y metszés
        x0 = -y0 / gamma2 ;    //semleges metszi eredeti kord rendszer x tengelye

        System.out.println("x0: " + x0);
        System.out.println("y0: " + y0);

    }

    /*
    * Pont fő kordinátába
    * */
    public static Point toGeneral(double x, double y){

        x = x - xs;
        y = y - ys;

        x = x * Math.cos(fi) + y * Math.sin(fi);
        y = -1 * x * Math.sin(-fi) + y * Math.cos(-fi);

        return new Point(x, y);
    }

    /*
     * Pont fő dekard kordinátába
     * Lehet nem igy kell!!!!
     * */
    public static Point toDekard(double x, double y){

        x = x * Math.cos(-fi) + y * Math.sin(-fi);
        y = -1 * x * Math.sin(-fi) + y * Math.cos(-fi);

        x = x + xs;
        y = y + ys;

        return new Point(x, y);

    }

    public static Point toDekard(Point p){
        return Stress.toDekard(p.x, p.y);
    }

    public static Point toGeneral(Point p){
        return Stress.toGeneral(p.x, p.y);
    }


    public static double getIx() {
        return Ix;
    }

    public static void setIx(double ix) {
        Ix = ix;
    }

    public static double getIy() {
        return Iy;
    }

    public static void setIy(double iy) {
        Iy = iy;
    }

    public static double getIxy() {
        return Ixy;
    }

    public static void setIxy(double ixy) {
        Ixy = ixy;
    }

    public static double getR() {
        return R;
    }

    public static void setR(double r) {
        R = r;
    }

    public static double getI1() {
        return I1;
    }

    public static void setI1(double i1) {
        I1 = i1;
    }

    public static double getI2() {
        return I2;
    }

    public static void setI2(double i2) {
        I2 = i2;
    }

    public static double getMid() {
        return mid;
    }

    public static void setMid(double mid) {
        Stress.mid = mid;
    }

    public static double getFi() {
        return fi;
    }

    public static void setFi(double fi) {
        Stress.fi = fi;
    }

    public static double getA() {
        return A;
    }

    public static void setA(double a) {
        A = a;
    }

    public static double getMx() {
        return Mx;
    }

    public static void setMx(double mx) {
        Mx = mx;
    }

    public double getMy() {
        return My;
    }

    public static void setMy(double my) {
        My = my;
    }

    public static double getMz() {
        return Mz;
    }

    public static void setMz(double mz) {
        Mz = mz;
    }

    public static double getF() {
        return F;
    }

    public static void setF(double f) {
        F = f;
    }

    public static double getFx() {
        return Fx;
    }

    public static void setFx(double fx) {
        Fx = fx;
    }

    public static double getFy() {
        return Fy;
    }

    public static void setFy(double fy) {
        Fy = fy;
    }



}
