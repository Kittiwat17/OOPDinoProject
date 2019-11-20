package userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import AllObject.Clouds;
import AllObject.EnemiesManager;
import AllObject.Land;
import AllObject.DinoCharacter;
import AllObject.ObjectGameManager;
import AllObject.RunnerScore;
import OptionClass.Resource;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameScreen extends JPanel implements Runnable, KeyListener {
    
    private int countStage = 0;
    
    private BufferedImage bg1;
    private BufferedImage bg2;
        private double backgroundPoint = 0;
        private BufferedImage[] Background = new BufferedImage[12];
	private static final int START_GAME_STATE = 0;
	private static final int GAME_PLAYING_STATE = 1;
	private static final int GAME_OVER_STATE = 2;
	
        private ObjectGameManager manager;
	private Land land;
	private DinoCharacter mainCharacter;
	private EnemiesManager enemiesManager;
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

        
        private AudioClip scoreUpSound;
	public GameScreen() {
            
            bg1 = Resource.getResouceImage("data/bg1.png");
            bg2 = Resource.getResouceImage("data/bg2.png");
            
            Background[0] = bg1;
            Background[1] = bg2;
            Background[2] = bg1;
            Background[3] = bg2;
            
            mainCharacter = new DinoCharacter();
            manager = new ObjectGameManager(GameWindow.SCREEN_WIDTH, mainCharacter);
            
            mainCharacter.setSpeedX(3);
            replayButtonImage = Resource.getResouceImage("data/replay_button.png");
            gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
           
            clouds = new Clouds(GameWindow.SCREEN_WIDTH, mainCharacter);
            score = new RunnerScore();
            
            try {
                scoreUpSound =  Applet.newAudioClip(new URL("file","","data/scoreup.wav"));
            } catch (MalformedURLException ex) {
                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
                        backgroundPoint = -(score.getScore() % 1501) /3;
                        if(score.getScore() >= 4500){
                            countStage = 3;
                        }
                        else if(score.getScore() >= 3000){
                            countStage = 2;
                        }
                        else if(score.getScore() >= 1500){
                            countStage = 1;
                        }
			else{
                            countStage = 0;
                        }
                        
			if (manager.isCollision()) {
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
			break;
		case GAME_PLAYING_STATE:
		case GAME_OVER_STATE:
			clouds.draw(g);
			manager.draw(g);
			mainCharacter.draw(g);
			g.setColor(Color.BLACK);
			g.drawString("HI " + score.getScore(), 500, 20);
                        mainCharacter.upScore(score.getScore());
			if (gameState == GAME_OVER_STATE) {
				g.drawImage(gameOverButtonImage, (getWidth()/2)-98, 30, null);
				g.drawImage(replayButtonImage, 283, 50, null);
				
			}
			break;
		}
	}

	@Override
	public void run() {

		double fps = 100 ;
		long msPerFrame = (long) (1000 * 1000000 / fps);
		long lastTime = 0;
		long elapsed;
		
		int msSleep;
		int nanoSleep;

		long endProcessGame;
		long lag = 0;

		while (true) {
                    fps += RunnerScore.getTimeUpdate() * 2;
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

        public int getCountStage(){
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
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                                    
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
                score.reRunnerScore();
	}

}
