package AllObject;

import OptionClass.Resource;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class groundEnemy extends Enemy {
	
	public static final int Y_LAND = 350;
	
	private double posX;
	private int width;
	private int height;
	
	private BufferedImage image;
	private DinoCharacter mainCharacter;
	private int enemyCounts = 0;
	private Rectangle rectBound;
	private BufferedImage listCactus[] = new BufferedImage[2];
        
	public groundEnemy(DinoCharacter mainCharacter, int posX) {
                
                listCactus[0] = Resource.getResouceImage("data/cactus1.png");
                listCactus[1] = Resource.getResouceImage("data/cactus2.png");
                int numRandom = getImageNum();
		
		this.image = listCactus[numRandom];
                this.posX = posX + ((100 - image.getWidth()) / 2);
		
                this.width = image.getWidth() - 10;
		this.height = image.getHeight() - 10;
		this.mainCharacter = mainCharacter;
                
		rectBound = new Rectangle();
                
	}
        
        private int getImageNum() {
		Random rand = new Random();
		int type = rand.nextInt(2);
		if(type == 0){
                    return 0;
                }
		else{
			return 1;
		}
	}
	
	public void update(double posX) {
		this.posX -= mainCharacter.getSpeedX();
	}
	
	public void draw(Graphics g) {
            if(enemyCounts == 0){
                g.drawImage(image, (int) posX, Y_LAND - image.getHeight(), null);
		g.setColor(Color.red);
                Rectangle bound = getBound();
		g.drawRect(bound.x, bound.y, bound.width, bound.height);
            }
		
		
	}
	
	public Rectangle getBound() {
		rectBound = new Rectangle();
		rectBound.x = (int) posX + (image.getWidth() - width)/2;
		rectBound.y = Y_LAND - image.getHeight() + (image.getHeight() - height)/2;
		rectBound.width = width;
		rectBound.height = height;
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
