package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static void setFiDeg(double fiDeg) {
        Stress.fiDeg = fiDeg;
    }

    private static double fiDeg = 0;       //radián
    private static double A = 0 /*Test területe*/;      //mm^2
    private static double M1 = 0;       //Nm
    private static double M2 = 0;
    private static double Mz = 0;
    private static double F  = 0;       //N
    private static double Fx = 0;       //mm
    private static double Fy = 0;

    /*
    * I1 I2 fi
    * */
    public static void calc1(){

        mid = ((Ix + Iy) / 2);
        fi = (Math.PI - Math.atan(Math.abs(Ixy) /
                ((Math.abs(Iy) - Math.abs(Ix)) / 2))) / 2;
        fiDeg = Math.toDegrees(fi);

        //double memoria tulcsordulás könyen elöfordul
        double gyb = (4 * Ixy * Ixy);
        gyb += (Ix - Iy) * (Ix - Iy);

        //System.out.println("gyb: " + gyb);
        double gyok = Math.sqrt(gyb);
        //System.out.println("gyök: " + gyok);

        I1 = mid + 0.5 * gyok;
        I2 = mid - 0.5 * gyok;
        R = Math.sqrt((Ixy * Ixy) + Math.pow((Iy - Ix) / 2, 2));

        double fi2 = Math.atan((Ix - I1) / Ixy);

        System.out.println("I1: " + I1);
        System.out.println("I2: " + I2);
        //System.out.println("fi: " + fi);
        System.out.println("fi deg: " + Stress.getFiDeg());
        //System.out.println("fi2: " + Math.toDegrees(fi2));
    }

    /*
     * F, Mx, My, Mz
     * */
    public static void calcF(){

        M1 = F * Stress.toGeneral(Fx, Fy).y;
        M2 = F * Stress.toGeneral(Fx, Fy).x;

        System.out.println("M1: " + M1);
        System.out.println("M2: " + M2);
    }

    public static double getFiDeg(){
        return fiDeg;
    }

    /*
     * feszültség 1 pontra fő rendszerbe
     * */
    public static double calcStress(Point p){
        return (F / A + M1 * p.y / I1 + M2 * p.x / I2); //szigma [MegaPascal]

    }

    /*
    * I1 I2 másik modszerel
    * */
    public static void reCalcI(){
        //még nem tökéletes
        System.out.println("I1 (BME): " + (Ix - Ixy * Math.tan(fi)));
        System.out.println("I2 (Bab): " + (Ixy / Math.tan(fi) - Iy));
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

    public static double getMaxStress(ArrayList<Point> list){
        ArrayList<Double> stress = Stress.calcStress(list);
        double max = 0;
        for (int i = 0; i < stress.size() -1; i++) {
            if (Math.abs(max) < Math.abs(stress.get(i)))
                max = stress.get(i);
        }
        return max;
    }

    public static double getMinStress(ArrayList<Point> list){
        ArrayList<Double> stress = Stress.calcStress(list);
        double min = 0;
        for (int i = 0; i < stress.size() -1; i++) {
            if (Math.abs(min) > Math.abs(stress.get(i)))
                min = stress.get(i);
        }
        return min;
    }

    /*
     * semleges tengely
     * Dekardba tér vissza
     * tengely metszetek eredeti kord ba
     * */
    public static void calcNeutral(){

        /*
        //értelem helyes modositás nyomatékra + - miatt
        M1 *= +1;
        M2 *= +1;
         */

        double gamma = Math.atan( (M2 * I1) / (M1 * I2) );      //semleges hajlásszög 1. tengelyhez képest
        //m1 re -1 vagy abs????
        double v0 = -(F * I1) / ((-1*M1) * A);        //2. tengely metszete
        System.out.println("v0: " + v0);

        //gamma M1 M2 elöjel helyeség abs???? -1x???
        //gamma *= -1;

        System.out.println("gamma: " + Math.toDegrees(gamma));

        //1, 2 kord to decard semleg pont
        double v02x = (v0 * Math.sin(-1 * fi));
        double v02y = (v0 * Math.cos(-1 * fi));

        v02x += xs;
        v02y += ys;

        System.out.println("v02x: " + v02x);
        System.out.println("v02y: " + v02y);

        double gamma2 = Math.tan(Math.abs(gamma) - Math.abs(fi)); //semleges dekárd hajlása
        System.out.println("gamma2: " + Math.toDegrees(Math.abs(gamma) - Math.abs(fi)));        //ez jó

        y0 = v02y - (gamma2 * v02x);     //semleges tengely eredeti kord y metszés
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

        double x2 = x * Math.cos(fi) + y * Math.sin(fi);
        double y2 = -x * Math.sin(fi) + y * Math.cos(fi);

        return new Point(x2, y2);
    }

    /*
     * Pont fő dekard kordinátába
     * Lehet nem igy kell!!!!
     * */
    public static Point toDekard(double x, double y){

        double x2 = x * Math.cos(-fi) + y * Math.sin(-fi);
        double y2 = -1 * x * Math.sin(-fi) + y * Math.cos(-fi);

        x2 = x2 + xs;
        y2 = y2 + ys;

        return new Point(x2, y2);

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

    public static double getM1() {
        return M1;
    }

    public static void setM1(double m1) {
        M1 = m1;
    }

    public double getMy() {
        return M2;
    }

    public static void setM2(double m2) {
        M2 = m2;
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
