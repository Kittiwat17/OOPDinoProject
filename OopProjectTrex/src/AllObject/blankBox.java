/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AllObject;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author memory
 */
public class blankBox extends Enemy{
    private int posX;
    DinoCharacter mainCharacter;
    public blankBox(DinoCharacter mainCharacter, int posX){
       this.mainCharacter = mainCharacter; 
       this.posX = posX;
    }
    public void update(double posX) {
	this.posX -= mainCharacter.getSpeedX();
    }

    @Override
    public void draw(Graphics g) {
       g.drawLine(0, 0, 0, 0);
    }

    @Override
    public Rectangle getBound() {
        Rectangle rectBound = new Rectangle();
	rectBound.x = (int) 0;
	rectBound.y = 0;
	rectBound.width = 0;
	rectBound.height = 0;
	return rectBound;       
    }

    @Override
    public boolean isOutOfScreen() {
        if(posX < -100) {
            return true;
	}
	return false;
    }
    
}
