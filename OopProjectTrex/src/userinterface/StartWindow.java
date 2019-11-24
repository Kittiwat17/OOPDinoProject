/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import OptionClass.Resource;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

public class StartWindow extends JFrame implements ActionListener{
    private String name;
    
    public static final int SCREEN_WIDTH = 1000;
    private GameWindow gameWindow;
    
    private BufferedImage background;
    private BufferedImage head1;
    private BufferedImage head2;
    
    private JPanel sp;
    private JTextField n;
    private JButton start;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public StartWindow() {
        super("Running Dino!");
        setSize(SCREEN_WIDTH, 600);
        setLocation(screenSize.width / 2 - SCREEN_WIDTH / 2, screenSize.height / 2 - 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        background = Resource.getResouceImage("Game Element/bgStart.jpg");
        head1 = Resource.getResouceImage("Game Element/running.png");
        head2 = Resource.getResouceImage("Game Element/dino.png");
        
        sp = new JPanel();
        n = new JTextField(20);
        start = new JButton("START");
        
        n.setFont(new Font("Gurmukhi MN", Font.PLAIN, 22));
        n.setHorizontalAlignment(JTextField.CENTER);

        
        start.setSize(100, 50);
        start.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        start.setText("START");
        
        getContentPane().setLayout(null);
        sp.setBounds(0, 0, 1000, 600);
        
//        lb.setBounds(285, 100, 700, 100);
        n.setBounds(250, 300, 500, 50);
        start.setBounds(400, 400, 200, 100);
        
        getContentPane().add(n);
        getContentPane().add(start);
        
        getContentPane().add(sp);
        
        start.addActionListener(this);
        n.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (n.getText() != null) {
                        name = n.getText();
                    } else {
                        name = "";
                    }
                    gameWindow = new GameWindow();
                    gameWindow.startGame(name);
                    gameWindow.setVisible(true);
                    dispose();
                }
            }
        });
    }
    
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.drawImage(head1, 240, 40, null);
        g.drawImage(head2, 350, 170, null);
    }
    
    public void startPage() {
        setVisible(true);
    }

    public static void main(String args[]) {
        (new StartWindow()).startPage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(start)) {
            if (n.getText() != null) {
                name = n.getText();
            } else {
                name = "";
            }
            gameWindow = new GameWindow();
            gameWindow.startGame(name);
            gameWindow.setVisible(true);
            dispose();
        }
    }
}
