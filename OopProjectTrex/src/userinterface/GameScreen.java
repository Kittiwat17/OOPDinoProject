package userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import AllObject.Clouds;
import AllObject.DinoCharacter;
import AllObject.ObjectGameManager;
import AllObject.RunnerScore;
import OptionClass.Resource;
import java.awt.Font;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameScreen extends JPanel implements Runnable, KeyListener {
    public static int countStage = 0;
    
    private String name;
    private String nameHs;
    private int highscore;
    private int jumpcount = 0;

    private BufferedImage bg;
    private BufferedImage bg2;
    private BufferedImage bg3;
    private BufferedImage bg4;
    private BufferedImage gameOver;
    private double backgroundPoint = 0;
    private BufferedImage[] Background = new BufferedImage[12];
    
    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;

    private ObjectGameManager manager;
    private DinoCharacter mainCharacter;
    private Clouds clouds;
    private Thread thread;
    private RunnerScore score;
    private Thread runScore;
    private boolean isKeyPressed;
    public static int enemyAndLandCount = 0;
    public static boolean blankBox = false;

    //array เก็บจำนวนobject ของ land
    private int gameState = START_GAME_STATE;

    private static int speedGameM = 9;
    private static int speedGameN = 999999;
    
    private double scores = 0;
    int point = 0;
    public GameScreen() {

        bg = Resource.getResouceImage("Game Element/allmapVer1.jpg");
        bg2 = Resource.getResouceImage("Game Element/bg2.jpg");
        bg3 = Resource.getResouceImage("Game Element/bg3.jpg");
        bg4 = Resource.getResouceImage("Game Element/bg4.jpg");
        
//        up = 
        gameOver = Resource.getResouceImage("Game Element/banner.png");

        mainCharacter = new DinoCharacter();
        manager = new ObjectGameManager(GameWindow.SCREEN_WIDTH, mainCharacter);

        mainCharacter.setSpeedX(3);

        clouds = new Clouds(GameWindow.SCREEN_WIDTH, mainCharacter);
        score = new RunnerScore();
        
//        ดึง high score
        nameHs = HighScoreStorage.getName();
        highscore = HighScoreStorage.getHighscore();
    }
    
    public void getNameFirst(String n) {
        name = n;
    }

    public void startGame() {
        thread = new Thread(this);
        thread.start();
        runScore = new Thread(score);

    }

    public void gameUpdate() {

        if (gameState == GAME_PLAYING_STATE) {
            clouds.update();
            manager.update();
            mainCharacter.update();
            backgroundPoint = -(scores / 3 + point);
              
            if(backgroundPoint <= -4300 && point < 4200){
                point += 2;
            }
                        else if(backgroundPoint <= -2400 && point < 2800){
                point += 2;
            }
                        else if(backgroundPoint <= -500 && point < 1400){
                point += 2;
            }
            if (scores %6000 > 4500) {
                countStage = 3;
              
            }
            else if (scores % 6000 > 3000) {
                countStage = 2;
                
            }

            else if (scores % 6000 > 1500) {
                countStage = 1;
                
            }
            
            else {
                point = 0;
                countStage = 0;
                backgroundPoint = 0;
            }
            
            if (highscore < scores) {
                nameHs = name;
                highscore = (int) scores;
            }
            
            if ((int) scores % 200 == 0 && !(scores >= 0 && scores < 1)) {
                mainCharacter.playScoreSound();
                mainCharacter.setHp(mainCharacter.getHp() + 5);
                if (mainCharacter.getHp() > 400) {
                    mainCharacter.setHp(400);
                }
            }

            if (manager.isCollision()) {
                mainCharacter.playDeadSound();
                mainCharacter.setHp(mainCharacter.getHp() - 3);
                setSpeed(5000);
                
            }
            if (mainCharacter.getPosY() > 500 || mainCharacter.getHp() <= 0) {
                mainCharacter.playDeadSound();

                gameState = GAME_OVER_STATE;
                mainCharacter.dead(true);
                runScore.suspend();
                System.out.println(runScore.getState());
            }
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.decode("#c0c0c0"));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(bg, (int) backgroundPoint, 0, null);
        switch (gameState) {
            case START_GAME_STATE:
//                mainCharacter.draw(g);
                manager.draw(g);
                g.setColor(Color.white);
                g.fillRect(60, 14, 406, 21);
                g.setColor(Color.green);
                g.fillRect(63, 17, mainCharacter.getHp(), 15);
                g.setColor(Color.black);
                g.setFont(new Font("Gurmukhi MN", Font.BOLD, 17));
                g.drawString("HP ", 30, 30);
                g.drawString("HI : [ " + nameHs + " : " + highscore + " ]", 650, 30);
                g.drawString("SCORE : " + (int) scores, 860, 30);
                g.setColor(Color.white);
                g.setFont(new Font("Gurmukhi MN", Font.BOLD, 32));
                
                g.drawString("How to play?", 380, 100);
                g.setFont(new Font("Gurmukhi MN", Font.BOLD, 20));
                g.drawImage(Resource.getResouceImage("Game Element/UPbutton.png"), 260, 150, this);
                g.drawString("or", 295, 170);
                g.drawImage(Resource.getResouceImage("Game Element/Wbutton.png"), 320, 150, this);
                g.drawString("or", 355, 170);
                g.drawImage(Resource.getResouceImage("Game Element/SPACEbutton.png"), 380, 150, this);
                g.drawString(": Jump and Double Jump", 470, 170);
                
                g.drawImage(Resource.getResouceImage("Game Element/DOWNbutton.png"), 300, 200, this);
                g.drawString("or", 355, 220);
                g.drawImage(Resource.getResouceImage("Game Element/Sbutton.png"), 400, 200, this);
                g.drawString(": Down and Gravity", 470, 220);
                
                g.setFont(new Font("Gurmukhi MN", Font.PLAIN, 16));
                g.drawString("Don't bump into any obstacles, even flying things.", 285, 270);
                g.drawString("For every 200 points your blood will be recovered 100", 270, 300);
                g.drawString("But will you survive until then? ;)", 350, 330);
                
                g.setFont(new Font("Gurmukhi MN", Font.BOLD, 18));
                g.drawString("Tap Spece bar to START", 365, 390);
                break;
            case GAME_PLAYING_STATE:
                
                scores += 0.05;
            case GAME_OVER_STATE:
                clouds.draw(g);
                manager.draw(g);
                mainCharacter.draw(g);
                
                // HP Bar
                g.setColor(Color.white);
                g.fillRect(60, 14, 406, 21);
                if (mainCharacter.getHp() > 265) {
                    g.setColor(Color.green);
                } else if (mainCharacter.getHp() > 130 && mainCharacter.getHp() <= 265) {
                    g.setColor(Color.orange);
                } 
                else if (mainCharacter.getHp() > 0 && mainCharacter.getHp() <= 130) {
                    g.setColor(Color.red);
                }
                g.fillRect(63, 17, mainCharacter.getHp(), 15);
                
                // String
                g.setFont(new Font("Gurmukhi MN", Font.BOLD, 17));
                // color
                if (scores%6000 <= 1520 || scores%6000 > 4520) {
                    g.setColor(Color.black);
                } else if (scores%6000 > 1520 && scores%6000 <= 4520) {
                    g.setColor(Color.white);
                }
//                g.setColor(Color.black);
                g.drawString("HP ", 30, 30);
                g.drawString("HI : [ " + nameHs + " : " + highscore + " ]", 650, 30);
                g.drawString("SCORE : " + (int) scores, 860, 30);
                if ((int) scores%200 == 0 && !(scores >= 0 && scores < 1)) {
                    g.setColor(Color.white);
                    g.drawString("HP +100", (int) mainCharacter.getPosX() + 10, (int) mainCharacter.getPosY() - 20);
                }
                if (gameState == GAME_OVER_STATE) {
                    g.drawImage(gameOver, 100, 60, this);
                    g.setColor(Color.white);
                    g.setFont(new Font("Gurmukhi MN", Font.BOLD, 30));
                    g.drawString("You have " + (int) scores + " score", 370, 295);
                    g.setFont(new Font("Gurmukhi MN", Font.BOLD, 14));
                    g.drawString("Tap Spece bar to play again.", 420, 330);
                    if ((int) scores == highscore) {
                        HighScoreStorage.saveName(nameHs);
                        HighScoreStorage.saveHighscore(highscore);
                    }
                }
                break;
        }
    }

    @Override
    public void run() {

        double fps = 100;
        long msPerFrame = (long) (1000 * 1000000 / fps);
        long lastTime = 0;
        long elapsed;

        int msSleep;
        int nanoSleep;

        long endProcessGame;
        long lag = 0;

        while (true) {
//            mainCharacter.setSpeedX(score.getScore()*2/1000 + 3);
//            fps += RunnerScore.getTimeUpdate() * 2;
            gameUpdate();
            repaint();
            
            try {
                Thread.sleep(speedGameM, speedGameN);
                
//            endProcessGame = System.nanoTime();
//            elapsed = (lastTime + msPerFrame - System.nanoTime());
//            msSleep = (int) (elapsed / 1000000);
//            nanoSleep = (int) (elapsed % 1000000);
//            if (msSleep <= 0) {
//                lastTime = System.nanoTime();
//                continue;
//            }
//            try {
//                Thread.sleep(msSleep, nanoSleep);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            lastTime = System.nanoTime();
            } catch (InterruptedException ex) {
                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }

    public int getCountStage() {
        return countStage;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isKeyPressed) {
            isKeyPressed = true;
            switch (gameState) {
                case START_GAME_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        runScore.start();
                        gameState = GAME_PLAYING_STATE;
                    }
                    break;
                case GAME_PLAYING_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                        mainCharacter.jump();

                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                        mainCharacter.down(true);
                    }
                    break;
                case GAME_OVER_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        gameState = GAME_PLAYING_STATE;
                        resetGame();
                        runScore.resume();
                        System.out.println(runScore.getState());
                    }
                    break;

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isKeyPressed = false;
        if (gameState == GAME_PLAYING_STATE) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                mainCharacter.down(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    private void resetGame() {
        manager.reset();
        mainCharacter.dead(false);
         countStage = 0;
         scores = 0;
        mainCharacter.reset();
       
       point = 0;
        speedGameM = 9;
        speedGameN = 999999;
    }
    
    public static void setSpeed(int d){
        if(speedGameN - d <= 0){
            speedGameM -= 1;
            speedGameN = 999999;
        }
        else{
            speedGameN -= d;
        }
        
    }
}
