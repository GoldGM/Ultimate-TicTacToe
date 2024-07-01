import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static javax.swing.JOptionPane.showMessageDialog;


public class Game {

    public JLabel playAgainstBotLabel;
    public JButton playAgainstBot;
    public static JLabel currentPlayerLabel;
    public static boolean imaginaryBool =false;
    public static boolean easterEgg;
    public static boolean isShowed;
    public static boolean gameStarted;
    public static String[] lastMovesArray;
    public static int lastMoveIndicatorInt;
    public static JList<String> lastMoves;
    public static TikTakToe[][] gameTables = new TikTakToe[3][3];
    public static boolean firstPlayerTurn;
    public static boolean playAgainstBotBool;
    List<String> loadedMovesList = new ArrayList<>();
    int[] positions = {40,140,240};
    public Game(JFrame frame) {
        isShowed=false;
        easterEgg = false;
        playAgainstBotBool=false;
        gameStarted=false;
        lastMovesArray = new String[81];
        lastMoveIndicatorInt =0;
        JScrollPane scrollPane = new JScrollPane();
        lastMoves=new JList<>(lastMovesArray);
        scrollPane.setViewportView(lastMoves);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane);
        panel.setBounds(350,200,110,200);
        firstPlayerTurn=true;
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(350,85,80,30);
        saveButton.addActionListener(e -> {
            try {
                saveGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton loadButton = new JButton("Load");
        loadButton.setBounds(350,120,80,30);
        loadButton.addActionListener(e -> {
            try {
                loadGame();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        playAgainstBotLabel= new JLabel();
        playAgainstBotLabel.setBounds(25,3,201,35);
        playAgainstBotLabel.setIcon(new ImageIcon("assets/botPlayOn.gif"));
        playAgainstBot = new JButton("Bot Play");
        playAgainstBot.setBounds(350,155,80,30);
        if(imaginaryBool){
            playAgainstBotLabel.setVisible(true);

        }else{
            playAgainstBotLabel.setVisible(false);
        }
        playAgainstBot.addActionListener(e -> {
            if (!gameStarted){
                playAgainstBotBool=!playAgainstBotBool;
                if (playAgainstBotBool){
                    playAgainstBotLabel.setVisible(true);
                }else
                    playAgainstBotLabel.setVisible(false);
            }else
                showMessageDialog(null, "You must not have made any moves to enable playing against bots, please restart the game.");

        });
        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setIcon(new ImageIcon("assets/x.png"));
        currentPlayerLabel.setBounds(420,15,30,30);

        JLabel currentPlayerLabelText = new JLabel("Current Player");
        currentPlayerLabelText.setBounds(330,15,90,30);
        frame.add(panel);
        frame.add(currentPlayerLabel);
        frame.add(currentPlayerLabelText);
        frame.add(playAgainstBotLabel);
        frame.add(playAgainstBot);
        frame.add(saveButton);
        frame.add(loadButton);
        for (int i = 0; i < gameTables.length; i++) {
            for (int j = 0; j <gameTables.length ; j++) {
                gameTables[i][j]= new TikTakToe(frame, positions[j], positions[i]);

            }
        }

    }

    public void saveGame() throws IOException {
        File myObj = new File("filename.txt");
        PrintWriter myWriter = new PrintWriter("filename.txt");
        myWriter.println(playAgainstBotBool ? 1 : 0);
        for (String s : lastMovesArray) {
            myWriter.println(s==null ? "" : s);
        }
        myWriter.close();
        showMessageDialog(null, "Game successfully saved");

    }

    public void loadGame() throws FileNotFoundException {
        File myObj = new File("filename.txt");
        if (!myObj.exists()){
            showMessageDialog(null, "there is no saved game");
            return;
        }
        Scanner myScanner = new Scanner(myObj);

        while (myScanner.hasNextLine()){
            loadedMovesList.add(myScanner.nextLine());
        }

        boolean bufferBool = false;
        for (int i = 0; i < loadedMovesList.size(); i++) {
            if (!(loadedMovesList.get(i).isEmpty())) {
                if (i==0){
                    if (Objects.equals(loadedMovesList.get(i), "1")){
                        bufferBool=true;
                        imaginaryBool=true;
                    }else {
                        imaginaryBool=false;
                    }
                    Main.resetButton.doClick();
                }
                if(i>9){
                    gameTables[(loadedMovesList.get(i).toCharArray()[5]-'0')][(loadedMovesList.get(i).toCharArray()[8]-'0')].buttons[(loadedMovesList.get(i).toCharArray()[11]-'0')][(loadedMovesList.get(i).toCharArray()[14]-'0')].doClick();
                }
                else if(i!=0){
                    gameTables[(loadedMovesList.get(i).toCharArray()[4]-'0')][(loadedMovesList.get(i).toCharArray()[7]-'0')].buttons[(loadedMovesList.get(i).toCharArray()[10]-'0')][(loadedMovesList.get(i).toCharArray()[13]-'0')].doClick();

                }
            }

        }
        if (bufferBool){
            gameStarted=false;
            playAgainstBot.doClick();


        }
        showMessageDialog(null, "Game successfully loaded");


    }

}
