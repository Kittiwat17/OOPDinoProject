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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameScreen extends JPanel implements Runnable, KeyListener {

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
        
        public static int enemyList = 12;
        
	private int gameState = START_GAME_STATE;

	private BufferedImage replayButtonImage;
	private BufferedImage gameOverButtonImage;

	public GameScreen() {
            
            mainCharacter = new DinoCharacter();
            manager = new ObjectGameManager(GameWindow.SCREEN_WIDTH, mainCharacter);
            
            mainCharacter.setSpeedX(3);
            replayButtonImage = Resource.getResouceImage("data/replay_button.png");
            gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
           
            clouds = new Clouds(GameWindow.SCREEN_WIDTH, mainCharacter);
            score = new RunnerScore();
                
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
		g.setColor(Color.decode("#f7f7f7"));
		g.fillRect(0, 0, getWidth(), getHeight());

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
                    fps += RunnerScore.getTimeUpdate();
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
