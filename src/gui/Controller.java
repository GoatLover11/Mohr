package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import utils.MFrame;

//import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{


    @FXML
    private TextArea logarea;

    @FXML
    private Button sv;
    @FXML
    private Button clear;
    @FXML
    private Button load;
    @FXML
    private Button ran;
    @FXML
    private Button calc;

    @FXML
    private ProgressBar pLine;

    @FXML
    private Pane inP;
    @FXML
    private Pane btnP;

    @FXML
    private TextField ix;
    @FXML
    private TextField iy;
    @FXML
    private TextField ixy;

    @FXML
    private Pane inP1;
    @FXML
    private Pane inP2;
    @FXML
    private Pane inP3;
    private ArrayList <Pane> InPane;

    @FXML
    private ScrollBar sc;

    @FXML
    private ImageView imgView;

    @FXML
    public void last(){
        int i = getTruePane();
        setPane(false);

        if (i-1 < 0)
            InPane.get(i-1).setVisible(true);
        else
            InPane.get(InPane.size() - 1).setVisible(true);
    }

    @FXML
    public void next(){
        int i = getTruePane();
        setPane(false);

        if (i+1 < InPane.size())
            InPane.get(i+1).setVisible(true);
        else
            InPane.get(0).setVisible(true);
    }

    private void setPane(boolean b){
        for (Pane p: InPane) {
            p.setVisible(b);

        }
    }

    private int getTruePane(){
        int i = 0;
        while (!InPane.get(i).isVisible()) {
            i++;
        }
        System.out.println("Pane: " + i);;
        return i;
    }

    @FXML
    public void calc(){
        pLineRun();

        double d = Integer.parseInt(ix.getText()) > Integer.parseInt(iy.getText()) ? sc.getValue()*Integer.parseInt(ix.getText()) : sc.getValue()*Integer.parseInt(iy.getText());
        d /= 800;
        log("Scale: " + (int)Math.round(d));
        try {
            MFrame mf = new MFrame(Double.valueOf(ix.getText()), Double.valueOf(iy.getText()), Double.valueOf(ixy.getText()), (int)Math.round(d));
            for (String s : mf.getData()) {
                log(s, Color.BLUE);
            }
        }catch (Exception e){
            log("Adat HIBA!!!!", Color.RED);
        }
    }

    /*
        kb k??sz
     */
    @FXML
    public void saveOptio(){
        ArrayList<String> list = getAll();
        try {
            File f = new File("src\\utils\\save.dat");

            if (f.exists()){
                f.delete();
            }
            if (f.createNewFile()){
                log("A file sikeresen l??trehozva!", Color.GREEN);
            }else {
                log("Nem siker??lt l??trehozni a file -t!", Color.RED);
            }

            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            for (String s : list){
                raf.writeBytes(s);
                raf.writeBytes("\n");
            }
            raf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log(e.toString(), Color.RED);
        } catch (IOException e) {
            e.printStackTrace();
            log(e.toString(), Color.RED);
        }

        disableAll(false);
    }

    @FXML
    public void random(){}

    @FXML
    public void clear(){}

    private ArrayList<String> getAll(){
        ArrayList<String> list = new ArrayList();
        list.add(ix.getText());
        list.add(iy.getText());
        list.add(ixy.getText());
        list.add("" + sc.getValue());

        for (String s : list){
            System.out.println(s);
        }

        return list;
    }

    @FXML
    public void loadOption(){
        File f = new File("src\\utils\\save.dat");
        ArrayList<String> list = new ArrayList<>();
        if (!f.exists()){
            log("Nincs elmentve be??lit??s!", Color.RED);
            return;
        }
        try {
            String line = "";
            RandomAccessFile raf = new RandomAccessFile(f, "r");
            while ((line = raf.readLine()) != null){
                list.add(line);
            }
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ix.setText(list.get(0));
            iy.setText(list.get(1));
            ixy.setText(list.get(2));
            sc.setValue(Double.valueOf(list.get(3)));

        }catch (Exception e){
            log("Sikertelen bet??lt??s!", Color.RED);
        }
        log("Sikeres bet??lt??s!", Color.GREEN);
        disableAll(false);
    }

    /*
        ???????????? wtf
     */
    @FXML
    public void loadImg(){
        try {
            //File f = new File("image.jpg");
            imgView.setImage(new Image(getClass().getResource("src\\gui\\image.png").toURI().toString()));
            log("A k??p sikeresen bet??ltve!", Color.GREEN);
            disableAll(false);
        }catch (Exception e){
            log("A k??p nem t??lthet?? be! " + e, Color.RED);
        }
    }

    /*
        progress
     */
    private void pLineRun() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log("Calculate!", Color.GREEN);
                    for (double i = 0; i < 1; i += 0.01) {
                        pLine.setProgress(i);
                        Thread.sleep(10);
                    }
                    Thread.sleep(100);
                    pLine.setProgress(0);
                    disableAll(false);
                    loadImg();
                    //sz??mol??s dolog indit??s...
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void log(String s, Color c){
        if (c.equals(Color.RED)) {
            logarea.setStyle("-fx-text-fill: #ff0000;");
        } else {
            logarea.setStyle("-fx-text-fill: #808080;");
        }
        log(s);
    }

    public void log(String s){
        logarea.setText(logarea.getText() + "\n" + s);
    }

    private void disableAll(boolean b){
        inP.setDisable(b);
        btnP.setDisable(b);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        logarea.setEditable(false);
        log("Start");

        InPane = new ArrayList<>();
        InPane.add(inP1);
        InPane.add(inP2);
        InPane.add(inP3);
        inP1.setVisible(true);
        inP2.setVisible(false);
        inP3.setVisible(false);

    }
}
