package utils;

import gui.GUI;

import java.util.ArrayList;

//Mohr kör rajzolo
public class Main {
    public static void main(String[] args) {

        /*
        double Ix = 1303465.859;      //hf
        //double Ix = 1000;
        double Iy = 1303465.859;        //hf
        //double Iy = 3000;
        double Ixy = -341357.0;     //hf
        //double Ixy = 500;

        MFrame mf = new MFrame(Ix, Iy, Ixy, (int)Math.round(Iy/800));


        GUI gui = new GUI();
        gui.laun(args);

         */

        //krisData();
        sajatData();


    }

    public static void pdfData(){

        //pdf random
        Stress.setA(7075.07972);
        Stress.setXs(59.31702);
        Stress.setYs(41.10195);

        Stress.setIx(3008441.81495);
        Stress.setIy(7355927.81861);
        Stress.setIxy(2201542.80112);

        Stress.setF(505000);
        Stress.setFx(54);
        Stress.setFy(43);

        Stress.calc1();
        Stress.calcF();
        Stress.calcNeutral();
        //Stress.reCalcI();

        //I1: 8300000mm
        //I2: 2100000mm
        //fi: -67.31798°

    }

    public static void krisData(){
        //kristof
        Stress.setA(5349.289914);
        Stress.setXs(48.6633008);
        Stress.setYs(36.76327745);

        Stress.setIx(2248486.392);
        Stress.setIy(3170133.851);
        Stress.setIxy(1280744.023);

        Stress.setF(347000);
        Stress.setFx(38);
        Stress.setFy(38);

        Stress.calc1();
        Stress.calcF();
        Stress.calcNeutral();

    }
    public static void babData(){
        //bablena
        Stress.setA(6913.4094);
        Stress.setXs(48.3252);
        Stress.setYs(43.2196);

        Stress.setIx(651911.8);
        Stress.setIy(1303466);
        Stress.setIxy(-341357);

        Stress.setI1(6186270.5);
        Stress.setI2(2499762.095);
        Stress.setFi(Math.toRadians(-59.7978));
        Stress.setFiDeg(-59.7978);

        Stress.setF(319000);
        Stress.setFx(48);
        Stress.setFy(27);

        //M1    2692471
        //M2:   4419533
        //gamma jó
        //x0: -188
        //y0: 55
        //v0: 106

        //Stress.calc1();
        Stress.calcF();
        Stress.calcNeutral();

    }

    public static void sajatData(){
        //saját
        Stress.setA(3182.976);
        Stress.setXs(41.12752);
        Stress.setYs(29.14331);

        Stress.setIx(651911.8);
        Stress.setIy(1303466);
        Stress.setIxy(-341357);

        Stress.setF(245000);
        Stress.setFx(45);
        Stress.setFy(24);

        Stress.calc1();
        Stress.calcF();
        Stress.calcNeutral();
        //Stress.reCalcI();

        ArrayList list = new ArrayList();
        list.add(new Point(0,23));
        list.add(new Point(22, 23));
        list.add(new Point(37, 0));
        list.add(new Point(75, 0));
        list.add(new Point(75, 30));
        list.add(new Point(52, 53));
        list.add(new Point(0, 53));

        System.out.println("Max: " + Stress.getMaxStress(list));
        System.out.println("Min: " + Stress.getMinStress(list));
    }
}
