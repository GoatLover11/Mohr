package utils;

import gui.GUI;

//Mohr kör rajzolo
public class Main {
    public static void main(String[] args) {

        double Ix = 1303465.859;      //hf
        //double Ix = 1000;
        double Iy = 1303465.859;        //hf
        //double Iy = 3000;
        double Ixy = -341357.0;     //hf
        //double Ixy = 500;

        MFrame mf = new MFrame(Ix, Iy, Ixy, (int)Math.round(Iy/800));

        /*
        GUI gui = new GUI();
        gui.laun(args);

         */
    }
}
