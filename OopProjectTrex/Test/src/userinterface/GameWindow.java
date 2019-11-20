package userinterface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//
public class GameWindow implements ActionListener{
    private String name;
    
    public static final int SCREEN_WIDTH = 1000;
    private GameScreen gameScreen;
//    private CollectName collectName;
    
    private JFrame fr;
    private JPanel sp;
    private JTextField n;
    private JButton start;

    public GameWindow() {
        fr = new JFrame("Java T-Rex game");
        fr.setSize(SCREEN_WIDTH, 600);
        fr.setLocation(400, 200);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setResizable(false);
        
        sp = new JPanel();
        n = new JTextField(100);
        start = new JButton("START");
        start.setSize(100, 50);
        sp.setPreferredSize(new Dimension(200, 100));
        sp.setLocation(200, 200);
        sp.setLayout(new GridLayout(3, 1));
        
        sp.add(new JLabel("Please Enter your name."));
        sp.add(n);
        sp.add(start);
        
        fr.add(sp);
        
        start.addActionListener(this);
        
//        gameScreen = new GameScreen();
//        fr.addKeyListener(gameScreen);
//        fr.add(gameScreen);
    }

    public void startGame() {
//        setVisible(true);
        gameScreen = new GameScreen();
        fr.add(gameScreen);
        fr.addKeyListener(gameScreen);
        
//        gameScreen.startGame();
        gameScreen.getNameFirst(name);
//        gameScreen.setVisible(true);
    }
    
    public void startPage() {
        fr.setVisible(true);
        sp.setVisible(true);
    }

    public static void main(String args[]) {
//        (new GameWindow()).startGame();
        (new GameWindow()).startPage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(start)) {
            if (n.getText() != null) {
                name = n.getText();
                sp.setVisible(false);
                startGame();
                gameScreen.startGame();
            } else {
                name = "";
                sp.setVisible(false);
                startGame();
                gameScreen.startGame();
            }
//            sp.setVisible(false);
//            startGame();
        }
    }

}
