package userinterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
	
	public static final int SCREEN_WIDTH = 1000;
	private GameScreen gameScreen;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public GameWindow() {
		super("Java T-Rex game");
		setSize(SCREEN_WIDTH, 600);
		setLocation(screenSize.width / 2 - SCREEN_WIDTH / 2, screenSize.height / 2 - 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		gameScreen = new GameScreen();
		addKeyListener(gameScreen);
		add(gameScreen);
	}
	
	public void startGame(String n) {
		setVisible(true);
                gameScreen.getNameFirst(n);
		gameScreen.startGame();
	}
        
}
