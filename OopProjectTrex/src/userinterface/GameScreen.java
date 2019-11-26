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

    private BufferedImage bg1;
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

    private BufferedImage replayButtonImage;
    private BufferedImage gameOverButtonImage;

    private static int speedGameM = 9;
    private static int speedGameN = 999999;
    
    private double scores = 0;
    public GameScreen() {

        bg1 = Resource.getResouceImage("Game Element/bg1.jpg");
        bg2 = Resource.getResouceImage("Game Element/bg2.jpg");
        bg3 = Resource.getResouceImage("Game Element/bg3.jpg");
        bg4 = Resource.getResouceImage("Game Element/bg4.jpg");
        gameOver = Resource.getResouceImage("Game Element/banner.png");

        Background[0] = bg1;
        Background[1] = bg2;
        Background[2] = bg3;
        Background[3] = bg4;

        mainCharacter = new DinoCharacter();
        manager = new ObjectGameManager(GameWindow.SCREEN_WIDTH, mainCharacter);

        mainCharacter.setSpeedX(3);
//        replayButtonImage = Resource.getResouceImage("data/replay_button.png");
//        gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");

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
            backgroundPoint = -(scores % 1501) / 3;
            if (scores >= 4500) {
                countStage = 3;
            } else if (scores >= 3000) {
                countStage = 2;
            } else if (scores >= 1500) {
                countStage = 1;
            } else {
                countStage = 0;
            }
            
            if (highscore < scores) {
                nameHs = name;
                highscore = (int) scores;
            }
            
            if (scores % 100 == 0) {
                mainCharacter.playScoreSound();
            }

            if (manager.isCollision()) {
                mainCharacter.playDeadSound();
                mainCharacter.setHp(mainCharacter.getHp() - 3);
                setSpeed(500);
                
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
        g.drawImage(Background[countStage], (int) backgroundPoint, 0, null);
        switch (gameState) {
            case START_GAME_STATE:
                mainCharacter.draw(g);
                g.setColor(Color.black);
                g.setFont(new Font("Gurmukhi MN", Font.BOLD, 17));
                g.drawString("HP ", 30, 30);
                g.setColor(Color.white);
                g.fillRect(60, 14, 406, 21);
                g.setColor(Color.green);
                g.fillRect(63, 17, mainCharacter.getHp(), 15);
                g.setColor(Color.white);
                g.drawString("Tap Spece bar to START", 400, 300);
                g.setColor(Color.black);
                g.drawString("HI : [ " + nameHs + " : " + highscore + " ]", 650, 30);
                g.drawString("SCORE : " + (int) scores, 860, 30);
                break;
            case GAME_PLAYING_STATE:
                
                scores += 0.03;
            case GAME_OVER_STATE:
                clouds.draw(g);
                manager.draw(g);
                mainCharacter.draw(g);
                
                g.setColor(Color.black);
                g.setFont(new Font("Gurmukhi MN", Font.BOLD, 17));
                g.drawString("HP ", 30, 30);
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
                g.setColor(Color.black);
                g.drawString("HI : [ " + nameHs + " : " + highscore + " ]", 650, 30);
                g.drawString("SCORE : " + (int) scores, 860, 30);
                if (gameState == GAME_OVER_STATE) {
                    g.drawImage(gameOver, 100, 60, this);
//                    g.drawImage(gameOverButtonImage, 400, 180, null);
                    g.setColor(Color.white);
                    g.setFont(new Font("Gurmukhi MN", Font.BOLD, 30));
                    g.drawString("You have " + (int) scores + " score", 360, 300);
//                    g.drawImage(replayButtonImage, 480, 370, null);
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
                    if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
                        mainCharacter.jump();

                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
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
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
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
        mainCharacter.reset();
        scores = 0;
        countStage = 0;
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
