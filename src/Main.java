import java.awt.*;
import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
public class Main {
    public static JButton resetButton;
    public static JFrame frame;
    private static Game currentGame;

    public static void gamePlay(Rectangle r){


        resetButton = new JButton("Reset");


        if(frame!=null){
            frame.dispose();
        }
        frame = new JFrame("Ultimate Tik Tak Toe");


        resetButton.setBounds(350,50,80,30);
        currentGame = new Game(frame);


        resetButton.addActionListener(e -> {
            Rectangle r1 = frame.getBounds();
            gamePlay(r1);
        });

        frame.add(resetButton);
        if (r==null){
            frame.setBounds(600,200,500,600);

        }else {
            frame.setBounds(r);
        }

        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public static void main(String[] args) {

        gamePlay(null);


    }
}