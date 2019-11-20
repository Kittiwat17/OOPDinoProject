package userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gameobject.Clouds;
import gameobject.EnemiesManager;
import gameobject.Land;
import gameobject.MainCharacter;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Resource;

public class GameScreen extends JPanel implements Runnable, KeyListener {
    Thread runner;
    
    private String name;
    private String nameHs;
    private int highscore;

    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;

    private Land land;
    private MainCharacter mainCharacter;
    private EnemiesManager enemiesManager;
    private Clouds clouds;
    private Thread thread;

    private boolean isKeyPressed;

    private int gameState = START_GAME_STATE;

    private BufferedImage replayButtonImage;
    private BufferedImage gameOverButtonImage;

    public GameScreen() {
        mainCharacter = new MainCharacter();
        land = new Land(GameWindow.SCREEN_WIDTH, mainCharacter);
        mainCharacter.setSpeedX(4);
        replayButtonImage = Resource.getResouceImage("data/replay_button.png");
        gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
        enemiesManager = new EnemiesManager(mainCharacter);
        clouds = new Clouds(GameWindow.SCREEN_WIDTH, mainCharacter);
//        hs = new HighScoreStorage(nameHs, highscore);
        nameHs = HighScoreStorage.getName();
        highscore = HighScoreStorage.getHighscore();
    }
    
    public void getNameFirst(String n) {
        name = n;
    }

    public void startGame() {
        this.runner = new Thread(this);
        this.runner.start();
    }

    public void gameUpdate() {
        if (gameState == GAME_PLAYING_STATE) {
            clouds.update();
            land.update();
            mainCharacter.update();
            mainCharacter.upScore();
            enemiesManager.update();
            if (highscore < mainCharacter.getScore()) {
                nameHs = name;
                highscore = mainCharacter.getScore();
            }
            if (enemiesManager.isCollision()) {
                mainCharacter.setHp(mainCharacter.getHp() - 5);
                mainCharacter.playDeadSound();
                if (mainCharacter.getHp() == 0) {
//                    mainCharacter.playDeadSound();
                    gameState = GAME_OVER_STATE;
                    mainCharacter.dead(true);
                }
//                gameState = GAME_OVER_STATE;
//                mainCharacter.dead(true);
            }
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());

        switch (gameState) {
            case START_GAME_STATE:
//                mainCharacter.draw(g);
                g.setColor(Color.BLACK);
                g.drawString("HP ", 20, 20);
                g.fillRect(50, 5, mainCharacter.getHp(), 15);
                g.drawString("Tap Spece bar to START", 400, 300);
                g.drawString("HI : [ " + nameHs + " : " + highscore + " ]", 750, 20);
                g.drawString("SCORE : " + mainCharacter.getScore(), 900, 20);
                break;
            case GAME_PLAYING_STATE:
            case GAME_OVER_STATE:
                clouds.draw(g);
                land.draw(g);
                enemiesManager.draw(g);
                mainCharacter.draw(g);
                g.setColor(Color.BLACK);
                g.drawString("HP ", 20, 20);
                g.fillRect(50, 5, mainCharacter.getHp(), 15);
                g.drawString("HI : [ " + nameHs + " : " + highscore + " ]", 750, 20);
                g.drawString("SCORE : " + mainCharacter.getScore(), 900, 20);
                if (gameState == GAME_OVER_STATE) {
                    g.drawImage(gameOverButtonImage, 400, 70, null);
                    g.drawString("You have " + mainCharacter.getScore() + " score", 435, 110);
                    g.drawImage(replayButtonImage, 480, 130, null);
                    if (mainCharacter.getScore() == highscore) {
//                        HighScoreStorage.saveHighscore(highscore, nameHs);
                        HighScoreStorage.saveName(nameHs);
                        HighScoreStorage.saveHighscore(highscore);
                    }
                }
                break;
        }
    }

    @Override
    public void run() {
        
//        while (true) {
//            gameUpdate();
//            repaint();
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

        int fps = 100;
        long msPerFrame = 1000 * 1000000 / fps;
        long lastTime = 0;
        long elapsed;

        int msSleep;
        int nanoSleep;

        long endProcessGame;
        long lag = 0;

        while (true) {
            gameUpdate();
            repaint();
            endProcessGame = System.nanoTime();
            elapsed = (lastTime + msPerFrame - System.nanoTime());
            msSleep = (int) (elapsed / 1000000);
            nanoSleep = (int) (elapsed % 1000000);
            if (msSleep <= 0) {
                lastTime = System.nanoTime();
                continue;
            }
            try {
                Thread.sleep(msSleep, nanoSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastTime = System.nanoTime();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isKeyPressed) {
            isKeyPressed = true;
            switch (gameState) {
                case START_GAME_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
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
                        gameState = START_GAME_STATE;
                        resetGame();
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
        enemiesManager.reset();
        mainCharacter.dead(false);
        mainCharacter.reset();
    }

}
