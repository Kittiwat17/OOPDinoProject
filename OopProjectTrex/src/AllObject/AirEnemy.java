package AllObject;

import OptionClass.Animation;
import OptionClass.Resource;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import userinterface.GameScreen;

public class AirEnemy extends Enemy {
	
	public static int Y_LAND = 350;
	
	private double posX;
	private int width;
	private int height;
	
	private BufferedImage image;
	private DinoCharacter mainCharacter;
	private Rectangle rectBound;
	private BufferedImage listMons[][] = new BufferedImage[4][2];
        
        
        private Animation flyAnim;
        
	public AirEnemy(DinoCharacter mainCharacter, int posX) {
            listMons[0][0] = Resource.getResouceImage("Game Element/Mons1.png");
            listMons[0][1] = Resource.getResouceImage("Game Element/Mons2.png");
            
            listMons[1][0] = Resource.getResouceImage("Game Element/flame1.png");
            listMons[1][1] = Resource.getResouceImage("Game Element/flame2.png");
            
            listMons[2][0] = Resource.getResouceImage("Game Element/rowel1.png");
            listMons[2][1] = Resource.getResouceImage("Game Element/rowel2.png");
            
            listMons[3][0] = Resource.getResouceImage("Game Element/santa1.png");
            listMons[3][1] = Resource.getResouceImage("Game Element/santa2.png");
            
  
            flyAnim = new Animation(90);
            flyAnim.addFrame(listMons[GameScreen.countStage][0]);
            flyAnim.addFrame(listMons[GameScreen.countStage][1]);    
           int numRandom = randomLandY();           
            
                
		
		this.Y_LAND = numRandom;
                this.posX = posX + ((100 - flyAnim.getFrame().getWidth()) / 2);
		
                this.width = flyAnim.getFrame().getWidth() - 10;
		this.height = flyAnim.getFrame().getHeight() - 10;
		this.mainCharacter = mainCharacter;
                
		rectBound = new Rectangle();
                
	}
        
        private int randomLandY() {
		Random rand = new Random();
		int type = rand.nextInt(2);
		if(type == 0){
                    return 270;
                }
		else{
			return 340;
		}
	}
	
	public void update(double posX) {
            flyAnim.updateFrame();
		this.posX -= mainCharacter.getSpeedX();
	}
	
	public void draw(Graphics g) {
           
                g.drawImage(flyAnim.getFrame(), (int) posX, Y_LAND - flyAnim.getFrame().getHeight(), null);
               
            
		
		
	}
	
	public Rectangle getBound() {
		rectBound = new Rectangle();
		rectBound.x = (int) posX + (flyAnim.getFrame().getWidth() - width)/2;
		rectBound.y = Y_LAND - flyAnim.getFrame().getHeight() + (flyAnim.getFrame().getHeight() - height)/2 + 10;
		rectBound.width = width;
		rectBound.height = height - 20;
		return rectBound;
	}

	@Override
	public boolean isOutOfScreen() {
		if(posX < -100) {
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
