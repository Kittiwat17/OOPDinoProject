package AllObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import userinterface.GameWindow;
import OptionClass.Resource;
import userinterface.GameScreen;

public class Clouds {
	public List<ImageCloud> listCloud;
	private BufferedImage cloud[][] = new BufferedImage[4][2];
	
	private DinoCharacter mainCharacter;
	
	public Clouds(int width, DinoCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		cloud[0][0] = Resource.getResouceImage("Game Element/Cloud1.png");
                cloud[0][1] = Resource.getResouceImage("Game Element/Cloud2.png");
                
                cloud[1][0] = Resource.getResouceImage("Game Element/CloudLava1.png");
                cloud[1][1] = Resource.getResouceImage("Game Element/CloudLava2.png");
                
                cloud[2][0] = Resource.getResouceImage("Game Element/CloudDrone1.png");
                cloud[2][1] = Resource.getResouceImage("Game Element/CloudDrone2.png");
                
                cloud[3][0] = Resource.getResouceImage("Game Element/CloudSnow1.png");
                cloud[3][1] = Resource.getResouceImage("Game Element/CloudSnow2.png");
		listCloud = new ArrayList<ImageCloud>();
		
		ImageCloud imageCloud = new ImageCloud();
		imageCloud.posX = 0;
		imageCloud.posY = 30;
                imageCloud.numCloud = 0;
        
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 150;
		imageCloud.posY = 40;
                imageCloud.numCloud = 0;

		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 300;
		imageCloud.posY = 50;
                imageCloud.numCloud = 1;

		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 450;
		imageCloud.posY = 20;
                imageCloud.numCloud = 0;
    
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 600;
		imageCloud.posY = 60;
                imageCloud.numCloud = 1;
     
		listCloud.add(imageCloud);
	}
	
	public void update(){
		Iterator<ImageCloud> itr = listCloud.iterator();
		ImageCloud firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX()/8;
		while(itr.hasNext()) {
			ImageCloud element = itr.next();
			element.posX -= mainCharacter.getSpeedX()/8;
		}
		if(firstElement.posX < -cloud[GameScreen.countStage][firstElement.numCloud].getWidth()) {
			listCloud.remove(firstElement);
			firstElement.posX = GameWindow.SCREEN_WIDTH;
                        
			listCloud.add(firstElement);
		}
	}
	
	public void draw(Graphics g) {
		for(ImageCloud imgLand : listCloud) {
			g.drawImage(cloud[GameScreen.countStage][imgLand.numCloud], (int) imgLand.posX, imgLand.posY, null);
		}
	}
	
	private class ImageCloud {
		float posX;
		int posY;
        
                int numCloud;
	}
}
