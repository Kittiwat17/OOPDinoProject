/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 *
 * @author delldolly
 */
public class StartWindow extends JFrame implements ActionListener{
    private String name;
    
    public static final int SCREEN_WIDTH = 1000;
    private GameWindow gameWindow;
    
    private JPanel sp;
    private JLabel lb;
    private JTextField n;
    private JButton start;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public StartWindow() {
        super("Dino Runner !!");
        setSize(SCREEN_WIDTH, 600);
        setLocation(screenSize.width / 2 - SCREEN_WIDTH / 2, screenSize.height / 2 - 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        sp = new JPanel();
        lb = new JLabel();
        n = new JTextField(20);
        start = new JButton("START");
        
        lb.setFont(new Font("Gurmukhi MN", Font.PLAIN, 40));
        lb.setText("Please enter you name.");
        
        n.setFont(new Font("Gurmukhi MN", Font.PLAIN, 22));
        n.setHorizontalAlignment(JTextField.CENTER);

        
        start.setSize(100, 50);
        start.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        start.setText("START");
        
        getContentPane().setLayout(null);
        sp.setBounds(0, 0, 1000, 600);
        
        lb.setBounds(285, 100, 700, 100);
        n.setBounds(250, 230, 500, 70);
        start.setBounds(400, 350, 200, 100);
        
        getContentPane().add(lb);
        getContentPane().add(n);
        getContentPane().add(start);
        
        getContentPane().add(sp);
        
//        sp.add(lb);
//        sp.add(n);
//        sp.add(start);
//        
//        add(sp);
        
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
