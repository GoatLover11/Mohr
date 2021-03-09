package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MFrame extends JFrame {

    private double Ix = 0, Iy = 0, Ixy, R = 0, I1, I2, mid, fi;
    private Dimension DIM = new Dimension(1600, 800);
    private int SC;
    private BufferedImage IMG;

    public MFrame(){}

    public MFrame(double Ix, double Iy, double Ixy, int sc){
        this.Ix = Ix;
        this.Iy = Iy;
        this.Ixy = Ixy;
        this.SC = sc;

        calc();
        setDefault();
        draw();

        setVisible(true);

        try {
            File f = new File("src\\gui\\image.png");
            ImageIO.write(IMG, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDefault() {


        IMG = new BufferedImage(DIM.width, DIM.height, BufferedImage.TYPE_INT_RGB);
        IMG.createGraphics();

        setTitle("Mhor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false);
        setSize(DIM);
        setLocationRelativeTo(null);
        getContentPane().add(new JLabel(new ImageIcon(IMG)));

    }

    private void draw() {
        Graphics g = IMG.getGraphics();

        //xy rendszer
        g.setColor(Color.WHITE);
        g.drawLine(0, DIM.height/2, DIM.width, DIM.height/2);
        g.drawLine(DIM.width/4, 0, DIM.width/4, DIM.height);


        //mhor középont
        System.out.println("MH:");
        Point mh = to(new Point(mid, 0));
        //mhor r
        Point rp = new Point(R/SC, R/SC);
        g.drawOval(mh.getrX() -rp.getrX(), mh.getrY() - rp.getrY(), rp.getrX()*2, rp.getrY()*2);

        //fő másodrendű nyomatékok
        g.setColor(Color.RED);
        System.out.println("I1:");
        Point i1 = to(new Point(I1, 0));
        g.drawString(". I1: "+Math.round(I1), i1.getrX(), i1.getrY());

        System.out.println("I2:");
        Point i2 = to(new Point(I2, 0));
        g.drawString(". I2: "+Math.round(I2), i2.getrX(), i2.getrY());

        //Ix Iy draw
        g.setColor(Color.CYAN);
        System.out.println("Ixy");

        System.out.println("Ix:");
        Point ix = to(new Point(Ix, 0));
        g.drawLine(ix.getrX(), ix.getrY(), ix.getrX(), to(new Point(0, -Ixy)).getrY());
        g.drawString(". Ix: "+Math.round(Ix), ix.getrX(), ix.getrY());

        System.out.println("Iy:");
        Point iy = to(new Point(Iy, 0));
        g.drawLine(iy.getrX(), iy.getrY(), iy.getrX(), to(new Point(0, Ixy)).getrY());
        g.drawString(". Iy: "+Math.round(Iy), iy.getrX(), iy.getrY());


        //px    ??????
        g.setColor(Color.ORANGE);
        System.out.println("Px");
        Point px = to(new Point(Ix, -Ixy));
        g.drawLine(i2.getrX(), i2.getrY(), px.getrX(), px.getrY());
        g.drawLine(i1.getrX(), i1.getrY(), px.getrX(), px.getrY());
        g.drawString(". Px", px.getrX(), px.getrY());

        //py    ??????
        g.setColor(Color.MAGENTA);
        System.out.println("Py");
        Point py = to(new Point(Iy, Ixy));
        g.drawLine(i2.getrX(), i2.getrY(), py.getrX(), py.getrY());
        g.drawLine(i1.getrX(), i1.getrY(), py.getrX(), py.getrY());
        g.drawString(". Py", py.getrX(), py.getrY());

        /*
        System.out.println("TEST");
        g.setColor(Color.YELLOW);
        Point ptest = to(new Point(100000, 1000000));
        Point ptest2 = to(new Point(1500000, 700000));
        g.drawLine(ptest.getrX(), ptest.getrY(), ptest2.getrX(), ptest2.getrY());
        g.drawString(". Pt", ptest2.getrX(), ptest2.getrY());

         */

        /*
        System.out.println("RGB");
        for (int i = 0; i < 800; i++ ){
            int clr = IMG.getRGB(363, i);
            if (clr == Color.WHITE.getRGB()){
                System.out.println("y: " + i + " : " + i*SC);
            }
        }

         */


    }

    //kordináta to draw calc    ???
    private Point to(Point p){
        Point p2 = new Point((Math.round(p.getX()/SC) + Math.round(DIM.width/4)), (((-1*Math.round(p.getY()/SC)) + Math.round(DIM.height/2))));
        System.out.print(p2.getrX() + " : " + p2.getrY());
        System.out.println();
        return p2;
    }

    private void calc(){
        mid = (Ix+Iy)/2;
        R = Math.pow((Ixy*Ixy) + Math.pow((Iy-Ix)/2, 2), 0.5);
        I1 = mid + R;
        I2 = mid - R;
        fi = 180*Math.atan(Ixy/(Iy-I2))/Math.PI;
                //(180-Math.atan(Math.abs(2*Ixy)/(Math.abs(Iy)-Math.abs(Ix))))/2;


        //helyes!
        //System.out.println(Math.round(I1+I2) + "=" + Math.round(Ix+Iy));

        System.out.println("mid: " + mid);
        System.out.println("r: " + R);
        System.out.println("I1: " + I1);
        System.out.println("I2: " + I2);
        System.out.println("fi: " + fi);
        System.out.println();
    }
}
