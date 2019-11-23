package AllObject;

import OptionClass.Animation;
import OptionClass.Resource;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AirEnemy extends Enemy {
	
	public static int Y_LAND = 295;
	
	private double posX;
	private int width;
	private int height;
	
	private BufferedImage image;
	private DinoCharacter mainCharacter;
	private int enemyCounts = 0;
	private Rectangle rectBound;
	private BufferedImage listCactus[] = new BufferedImage[2];
        
        
        private Animation flyAnim;
	public AirEnemy(DinoCharacter mainCharacter, int posX) {
            flyAnim = new Animation(90);
            flyAnim.addFrame(Resource.getResouceImage("Game Element/Mons1.png"));
            flyAnim.addFrame(Resource.getResouceImage("Game Element/Mons2.png"));    
            
            
                listCactus[0] = Resource.getResouceImage("data/cactus1.png");
                listCactus[1] = Resource.getResouceImage("data/cactus2.png");
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
                    return 200;
                }
		else{
			return 220;
		}
	}
	
	public void update(double posX) {
            flyAnim.updateFrame();
		this.posX -= mainCharacter.getSpeedX();
	}
	
	public void draw(Graphics g) {
            if(enemyCounts == 0){
                g.drawImage(flyAnim.getFrame(), (int) posX, Y_LAND - flyAnim.getFrame().getHeight(), null);
               
		g.setColor(Color.red);
                Rectangle bound = getBound();
		g.drawRect(bound.x, bound.y, bound.width, bound.height);
            }
		
		
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
		if(posX < -flyAnim.getFrame().getWidth()) {
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
