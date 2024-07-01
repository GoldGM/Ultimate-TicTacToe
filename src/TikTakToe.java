import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.util.Objects;
import java.util.Random;

import static javax.swing.JOptionPane.showMessageDialog;

public class TikTakToe {

    public JButton[][] buttons = new JButton[3][3];
    int forInt=1;
    boolean gameOver=false;
    boolean gameWinnerX=false;
    boolean gameWinnerO=false;
    boolean winnerX=false;
    boolean winnerO=false;
    boolean isDraw=false;
    boolean tableOver=false;
    boolean isClicked=false;
    int clickedNumber1;
    int clickedNumber2;
    int  x;
    int  y;
    JButton winnerLabel = new JButton();
    public TikTakToe(JFrame frame,int x,int y){
        winnerLabel.setIcon(new ImageIcon("assets/blank.png"));
        winnerLabel.setBounds(x,y,90,90);
        winnerLabel.setVisible(false);
        frame.add(winnerLabel);

        this.x=x;
        this.y=y;
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                buttons[i][j]= new JButton(new ImageIcon("assets/blank.png"));
                if (i==forInt){
                    x=this.x;
                    y+=30;
                    forInt++;
                }
                buttons[i][j].setBounds(x,y,30,30);
                x+=30;
                int finalI = i;
                int finalJ = j;

                buttons[i][j].addActionListener(e -> {
                    if (gameWinnerO||gameWinnerX){
                        return;
                    }
                    if(!Game.gameStarted && !Game.playAgainstBotBool){
                        Game.gameStarted=true;
                    }
                    if (Game.firstPlayerTurn){
                        int currentTableX=0;
                        int currentTableY=0;
                        for (int k = 0; k < 3; k++) {
                            for (int l = 0; l < 3; l++) {
                                if(Game.gameTables[k][l].buttons[0][0].getX()==this.x&&Game.gameTables[k][l].buttons[0][0].getY()==this.y){
                                    currentTableX = k;
                                    currentTableY = l;
                                }
                            }
                        }
                        Game.lastMovesArray[Game.lastMoveIndicatorInt]=(Game.lastMoveIndicatorInt+1)+"X ["+ currentTableX +"]["+ currentTableY+"]{"+finalI+"}{"+finalJ+"}";
                        Game.lastMoves.setListData(Game.lastMovesArray);
                        Game.lastMoveIndicatorInt++;
                        buttons[finalI][finalJ].setIcon(new ImageIcon("assets/x.png"));
                        Game.firstPlayerTurn =false;
                        Game.currentPlayerLabel.setIcon(new ImageIcon("assets/o.png"));
                        buttons[finalI][finalJ].removeActionListener( buttons[finalI][finalJ].getActionListeners()[0]);
                        this.clickedNumber1=finalI;
                        this.clickedNumber2=finalJ;
                        this.isClicked=true;
                        for (int k = 0; k < buttons.length; k++) {
                            if (Objects.equals(buttons[k][0].getIcon().toString(),"assets/x.png") && Objects.equals(buttons[k][1].getIcon().toString(),"assets/x.png") && Objects.equals(buttons[k][2].getIcon().toString(),"assets/x.png")){
                                tableOver=true;
                                winnerX=true;
                            }else if(Objects.equals(buttons[0][k].getIcon().toString(),"assets/x.png") && Objects.equals(buttons[1][k].getIcon().toString(),"assets/x.png") && Objects.equals(buttons[2][k].getIcon().toString(),"assets/x.png")){
                                winnerX=true;
                                tableOver=true;
                            }else if (Objects.equals(buttons[0][0].getIcon().toString(),"assets/x.png") && Objects.equals(buttons[1][1].getIcon().toString(),"assets/x.png") && Objects.equals(buttons[2][2].getIcon().toString(),"assets/x.png")) {
                                tableOver=true;
                                winnerX=true;
                            } else if (Objects.equals(buttons[0][2].getIcon().toString(),"assets/x.png") && Objects.equals(buttons[1][1].getIcon().toString(),"assets/x.png") && Objects.equals(buttons[2][0].getIcon().toString(),"assets/x.png")) {
                                tableOver=true;
                                winnerX=true;
                            }
                        }
                        if (winnerX){
                            for (JButton[] button : buttons) {
                                for (int l = 0; l < buttons.length; l++) {
                                    if (!(button[l].getActionListeners().length == 0))
                                        button[l].removeActionListener(button[l].getActionListeners()[0]);

                                }
                            }
                            winnerLabel.setIcon(new ImageIcon("assets/xWinner.png"));
                            winnerLabel.setVisible(true);
                            this.setVisible(false);

                        }
                        for (int k = 0; k < Game.gameTables.length; k++) {
                            for (int l = 0; l < Game.gameTables.length; l++) {
                                Game.gameTables[k][l].setEnabled(false);
                            }
                        }
                        if (Game.gameTables[finalI][finalJ].tableOver) {
                            for (int k = 0; k < Game.gameTables.length; k++) {
                                for (int l = 0; l < Game.gameTables.length; l++) {
                                    Game.gameTables[k][l].setEnabled(true);
                                }
                            }
                            for (int k = 0; k < Game.gameTables.length; k++) {
                                for (int l = 0; l < Game.gameTables.length; l++) {
                                    if (Game.gameTables[k][l].tableOver) {
                                        Game.gameTables[k][l].setEnabled(false);
                                    }
                                }
                            }
                        }else{
                            Game.gameTables[finalI][finalJ].setEnabled(true);
                        }

                        playAgainstBotFunction();
                    }else{
                        int currentTableX=0;
                        int currentTableY=0;
                        for (int k = 0; k < 3; k++) {
                            for (int l = 0; l < 3; l++) {
                                if(Game.gameTables[k][l].buttons[0][0].getX()==this.x&&Game.gameTables[k][l].buttons[0][0].getY()==this.y){
                                    currentTableX = k;
                                    currentTableY = l;
                                }
                            }
                        }
                        Game.lastMovesArray[Game.lastMoveIndicatorInt]=(Game.lastMoveIndicatorInt+1)+"O ["+ currentTableX +"]["+ currentTableY+"]{"+finalI+"}{"+finalJ+"}";
                        Game.lastMoves.setListData(Game.lastMovesArray);
                        Game.lastMoveIndicatorInt++;
                        buttons[finalI][finalJ].setIcon(new ImageIcon("assets/o.png"));
                        Game.firstPlayerTurn =true;
                        Game.currentPlayerLabel.setIcon(new ImageIcon("assets/x.png"));
                        buttons[finalI][finalJ].removeActionListener( buttons[finalI][finalJ].getActionListeners()[0]);
                        for (int k = 0; k < buttons.length; k++) {
                            if (Objects.equals(buttons[k][0].getIcon().toString(),"assets/o.png") && Objects.equals(buttons[k][1].getIcon().toString(),"assets/o.png") && Objects.equals(buttons[k][2].getIcon().toString(),"assets/o.png")){
                                tableOver=true;
                                winnerO=true;
                            }else if(Objects.equals(buttons[0][k].getIcon().toString(),"assets/o.png") && Objects.equals(buttons[1][k].getIcon().toString(),"assets/o.png") && Objects.equals(buttons[2][k].getIcon().toString(),"assets/o.png")){
                                tableOver=true;
                                winnerO=true;
                            }else if (Objects.equals(buttons[0][0].getIcon().toString(),"assets/o.png") && Objects.equals(buttons[1][1].getIcon().toString(),"assets/o.png") && Objects.equals(buttons[2][2].getIcon().toString(),"assets/o.png")) {
                                tableOver=true;
                                winnerO=true;
                            } else if (Objects.equals(buttons[0][2].getIcon().toString(),"assets/o.png") && Objects.equals(buttons[1][1].getIcon().toString(),"assets/o.png") && Objects.equals(buttons[2][0].getIcon().toString(),"assets/o.png")) {
                                tableOver=true;
                                winnerO=true;
                            }
                        }

                        if (winnerO){
                            for (JButton[] button : buttons) {
                                for (int l = 0; l < buttons.length; l++) {
                                    if (!(button[l].getActionListeners().length == 0))
                                        button[l].removeActionListener(button[l].getActionListeners()[0]);

                                }
                            }
                            winnerLabel.setIcon(new ImageIcon("assets/oWinner.png"));
                            winnerLabel.setVisible(true);
                            this.setVisible(false);
                        }
                        for (int k = 0; k < Game.gameTables.length; k++) {
                            for (int l = 0; l < Game.gameTables.length; l++) {
                                Game.gameTables[k][l].setEnabled(false);
                            }
                        }
                        if (Game.gameTables[finalI][finalJ].tableOver) {
                            for (int k = 0; k < Game.gameTables.length; k++) {
                                for (int l = 0; l < Game.gameTables.length; l++) {
                                    Game.gameTables[k][l].setEnabled(true);

                                }
                            }
                            for (int k = 0; k < Game.gameTables.length; k++) {
                                for (int l = 0; l < Game.gameTables.length; l++) {
                                    if (Game.gameTables[k][l].tableOver) {
                                        Game.gameTables[k][l].setEnabled(false);
                                        Game.gameTables[k][l].setVisible(false);
                                    }
                                }
                            }
                        }else{
                            Game.gameTables[finalI][finalJ].setEnabled(true);
                        }
                    }


                    if (!Game.easterEgg) {
                        if (Objects.equals(Game.gameTables[0][1].buttons[0][1].getIcon().toString(),"assets/x.png") && Objects.equals(Game.gameTables[1][0].buttons[1][0].getIcon().toString(),"assets/x.png") && Objects.equals(Game.gameTables[1][2].buttons[1][2].getIcon().toString(),"assets/x.png")&& Objects.equals(Game.gameTables[2][0].buttons[2][0].getIcon().toString(),"assets/x.png")&& Objects.equals(Game.gameTables[2][2].buttons[2][2].getIcon().toString(),"assets/x.png")){
                            Game.easterEgg=true;
                            eggScreen();

                        }else if (Objects.equals(Game.gameTables[0][1].buttons[0][1].getIcon().toString(),"assets/o.png") && Objects.equals(Game.gameTables[1][0].buttons[1][0].getIcon().toString(),"assets/o.png") && Objects.equals(Game.gameTables[1][2].buttons[1][2].getIcon().toString(),"assets/o.png")&& Objects.equals(Game.gameTables[2][0].buttons[2][0].getIcon().toString(),"assets/o.png")&& Objects.equals(Game.gameTables[2][2].buttons[2][2].getIcon().toString(),"assets/o.png")){
                            Game.easterEgg=true;
                            eggScreen();
                        }
                    }



                    for (int k = 0; k < buttons.length; k++) {
                        if (Objects.equals(Game.gameTables[k][0].winnerLabel.getIcon().toString(),"assets/xWinner.png") && Objects.equals(Game.gameTables[k][1].winnerLabel.getIcon().toString(),"assets/xWinner.png") && Objects.equals(Game.gameTables[k][2].winnerLabel.getIcon().toString(),"assets/xWinner.png")){
                            gameOver=true;
                            gameWinnerX=true;
                        }else if(Objects.equals(Game.gameTables[0][k].winnerLabel.getIcon().toString(),"assets/xWinner.png") && Objects.equals(Game.gameTables[1][k].winnerLabel.getIcon().toString(),"assets/xWinner.png") && Objects.equals(Game.gameTables[2][k].winnerLabel.getIcon().toString(),"assets/xWinner.png")){
                            gameOver=true;
                            gameWinnerX=true;
                        }else if (Objects.equals(Game.gameTables[0][0].winnerLabel.getIcon().toString(),"assets/xWinner.png") && Objects.equals(Game.gameTables[1][1].winnerLabel.getIcon().toString(),"assets/xWinner.png") && Objects.equals(Game.gameTables[2][2].winnerLabel.getIcon().toString(),"assets/xWinner.png")) {
                            gameOver=true;
                            gameWinnerX=true;
                        } else if (Objects.equals(Game.gameTables[0][2].winnerLabel.getIcon().toString(),"assets/xWinner.png") && Objects.equals(Game.gameTables[1][1].winnerLabel.getIcon().toString(),"assets/xWinner.png") && Objects.equals(Game.gameTables[2][0].winnerLabel.getIcon().toString(),"assets/xWinner.png")) {
                            gameOver=true;
                            gameWinnerX=true;
                        }
                    }
                    if (!gameWinnerX) {
                        for (int k = 0; k < buttons.length; k++) {
                            if (Objects.equals(Game.gameTables[k][0].winnerLabel.getIcon().toString(),"assets/oWinner.png") && Objects.equals(Game.gameTables[k][1].winnerLabel.getIcon().toString(),"assets/oWinner.png") && Objects.equals(Game.gameTables[k][2].winnerLabel.getIcon().toString(),"assets/oWinner.png")){
                                gameOver=true;
                                gameWinnerO=true;
                            }else if(Objects.equals(Game.gameTables[0][k].winnerLabel.getIcon().toString(),"assets/oWinner.png") && Objects.equals(Game.gameTables[1][k].winnerLabel.getIcon().toString(),"assets/oWinner.png") && Objects.equals(Game.gameTables[2][k].winnerLabel.getIcon().toString(),"assets/oWinner.png")){
                                gameOver=true;
                                gameWinnerO=true;
                            }else if (Objects.equals(Game.gameTables[0][0].winnerLabel.getIcon().toString(),"assets/oWinner.png") && Objects.equals(Game.gameTables[1][1].winnerLabel.getIcon().toString(),"assets/oWinner.png") && Objects.equals(Game.gameTables[2][2].winnerLabel.getIcon().toString(),"assets/oWinner.png")) {
                                gameOver=true;
                                gameWinnerO=true;
                            } else if (Objects.equals(Game.gameTables[0][2].winnerLabel.getIcon().toString(),"assets/oWinner.png") && Objects.equals(Game.gameTables[1][1].winnerLabel.getIcon().toString(),"assets/oWinner.png") && Objects.equals(Game.gameTables[2][0].winnerLabel.getIcon().toString(),"assets/oWinner.png")) {
                                gameOver=true;
                                gameWinnerO=true;
                            }
                        }

                    }

                    int blankLabelCount =0;
                    for (int k = 0; k < Game.gameTables.length; k++) {
                        for (int l = 0; l < Game.gameTables.length; l++) {
                            if (Objects.equals(Game.gameTables[k][l].winnerLabel.getIcon().toString(),"assets/blank.png")) {
                                blankLabelCount++;
                            }

                        }
                    }
                    if (blankLabelCount == 0) {
                        if (!gameOver) {
                            for (int k = 0; k < Game.gameTables.length; k++) {
                                for (int l = 0; l < Game.gameTables.length; l++) {
                                    Game.gameTables[k][l].setEnabled(false);
                                }
                            }
                            showMessageDialog(null, "Draw");
                        }
                    }


                    if (!tableOver) {
                        int indicator=0;
                        for (JButton[] button : buttons) {
                            for (int b = 0; b < buttons.length; b++) {
                                if (button[b].getActionListeners().length == 0) {
                                    indicator++;
                                }

                            }
                        }
                        if (indicator==9){
                            isDraw=true;
                            tableOver=true;
                            winnerLabel.setIcon(new ImageIcon("assets/drawLabel.png"));
                            winnerLabel.setVisible(true);
                            this.setVisible(false);
                        }

                    }
                    if (!Game.isShowed){
                        if (gameWinnerX){
                            for (int k = 0; k < Game.gameTables.length; k++) {
                                for (int l = 0; l < Game.gameTables.length; l++) {
                                    Game.gameTables[k][l].setEnabled(false);
                                }
                            }
                            Game.isShowed=true;
                            showMessageDialog(null, "Winner X");
                        }else if (gameWinnerO){
                            for (int k = 0; k < Game.gameTables.length; k++) {
                                for (int l = 0; l < Game.gameTables.length; l++) {
                                    Game.gameTables[k][l].setEnabled(false);
                                }
                            }
                            Game.isShowed=true;
                            showMessageDialog(null, "Winner O");
                        }
                    }







                });

                frame.add(buttons[i][j]);

            }
        }

    }



    public void setVisible(boolean b) {
        for (JButton[] button : this.buttons) {
            for (int l = 0; l < buttons.length; l++) {
                button[l].setVisible(b);
            }
        }
    }

    public void setEnabled(boolean b) {
        for (JButton[] button : this.buttons) {
            for (int l = 0; l < buttons.length; l++) {
                button[l].setEnabled(b);
            }
        }
    }

    public void setBlank() {
        for (JButton[] button : this.buttons) {
            for (int l = 0; l < buttons.length; l++) {
                button[l].setIcon(new ImageIcon("assets/blank.png"));
            }
        }
    }

    public void eggScreen(){
        try {
            File musicPath = new File("assets/egg.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            JButton congButton = new JButton();
            congButton.setIcon(new ImageIcon("assets/CONGRATULATIONS.gif"));
            congButton.setBounds(541,600,838,78);
            JFrame eggScreen = new JFrame();
            eggScreen.setBounds(0,0,1920,1080);
            JLabel waitLabel = new JLabel(new ImageIcon("assets/wait.gif"));
            waitLabel.setBounds(0,0,320,210);
            JLabel shinjiLabel = new JLabel(new ImageIcon("assets/shinji.png"));
            shinjiLabel.setBounds(320,0,252,210);
            JLabel sigma = new JLabel(new ImageIcon("assets/sigma.gif"));
            sigma.setBounds(0,210,363,498);
            JLabel gameBanner = new JLabel(new ImageIcon("assets/bannerGames.gif"));
            gameBanner.setBounds(1788,0,132,498);
            JLabel familyGuy = new JLabel(new ImageIcon("assets/guyFamily.gif"));
            familyGuy.setBounds(1290,0,498,498);
            JLabel bigGif = new JLabel(new ImageIcon("assets/big.gif"));
            bigGif.setBounds(0,0,1920,1080);
            congButton.addActionListener(e -> {
                eggScreen.dispose();
                clip.stop();
            });
            eggScreen.add(gameBanner);
            eggScreen.add(familyGuy);
            eggScreen.add(sigma);
            eggScreen.add(shinjiLabel);
            eggScreen.add(waitLabel);
            eggScreen.add(congButton);
            eggScreen.add(bigGif);
            eggScreen.setLayout(null);
            eggScreen.setUndecorated(true);
            eggScreen.setVisible(true);




        }catch (Exception ex){

        }
    }

    public void playAgainstBotFunction(){
        if(gameWinnerO||gameWinnerX){
            return;
        }
        if (Game.playAgainstBotBool){
            while (true){
                    Random rnd = new Random();
                    int rand1 = rnd.nextInt(3);
                    int rand2 = rnd.nextInt(3);
                    int rand3 = rnd.nextInt(3);
                    int rand4 = rnd.nextInt(3);
                    if (Game.gameTables[rand1][rand2].buttons[rand3][rand4].isEnabled()){
                        if (Game.gameTables[rand1][rand2].buttons[rand3][rand4].getActionListeners().length!=0) {
                            Game.gameTables[rand1][rand2].buttons[rand3][rand4].doClick();
                            break;
                        }




                    }



            }

        }

    }




}



