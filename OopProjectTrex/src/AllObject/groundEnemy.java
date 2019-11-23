package AllObject;

import OptionClass.Resource;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import userinterface.GameScreen;

public class groundEnemy extends Enemy {
	
	public static final int Y_LAND = 420;
	
	private double posX;
	private int width;
	private int height;
	
	private BufferedImage image;
	private DinoCharacter mainCharacter;
	
	private Rectangle rectBound;
	private BufferedImage listCactus[][] = new BufferedImage[4][3];
        
	public groundEnemy(DinoCharacter mainCharacter, int posX) {
                
                listCactus[0][0] = Resource.getResouceImage("Game Element/bush1.png");
                listCactus[0][1] = Resource.getResouceImage("Game Element/stone1.png");
                listCactus[0][2] = Resource.getResouceImage("Game Element/Tree1.png");
                
                 
                int numRandom = getImageNum();
		
		this.image = listCactus[GameScreen.countStage][numRandom];
                this.posX = posX + ((100 - image.getWidth()) / 2);
		
                this.width = image.getWidth() - 10;
		this.height = image.getHeight() - 10;
		this.mainCharacter = mainCharacter;
                
		rectBound = new Rectangle();
                
	}
        
        private int getImageNum() {
		Random rand = new Random();
		int type = rand.nextInt(3);
		if(type == 0){
                    return 0;
                }
                else if(type == 1){
                    return 1;
		}
                else{
                    return 2;
                }
	}
	
	public void update(double posX) {
		this.posX -= mainCharacter.getSpeedX();
	}
	
	public void draw(Graphics g) {
         
                g.drawImage(image, (int) posX, Y_LAND - image.getHeight(), null);
		
            
		
		
	}
	
	public Rectangle getBound() {
		rectBound = new Rectangle();
		rectBound.x = (int) posX + (image.getWidth() - width)/2 + 10;
		rectBound.y = Y_LAND - image.getHeight() + (image.getHeight() - height)/2 +20;
		rectBound.width = width - 20;
		rectBound.height = height - 20;
		return rectBound;
	}

	@Override
	public boolean isOutOfScreen() {
		if(posX < -image.getWidth()) {
			return true;
		}
		return false;
	}
	
        public double getPosX(){
            return posX;
        }
        
        public void setPosX(int x){
            this.posX = x;
        }
}
