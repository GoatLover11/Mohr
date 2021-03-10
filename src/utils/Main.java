package utils;

import gui.GUI;

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

        //test
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

        System.out.println("fi deg: " + Stress.getFiDeg());

    }
}
