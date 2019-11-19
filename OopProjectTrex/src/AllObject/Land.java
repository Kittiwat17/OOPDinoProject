package AllObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import OptionClass.Resource;
import userinterface.GameScreen;

public class Land {
	
        public static ArrayList<Double> landList;
    
	public static final int LAND_POSY = 273;
	
	private List<ImageLand> listLand;
	private BufferedImage land1;
	private BufferedImage land2;
	private BufferedImage land3;
	private int pitCount = 0;
        double landPosX;
        int typeLand = 0;
        
     
        
	private DinoCharacter mainCharacter;
	
	public Land(int width, DinoCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		land1 = Resource.getResouceImage("data/blank.png");
		land2 = Resource.getResouceImage("data/land-2.png");
		land3 = Resource.getResouceImage("data/land-3.png");
		int numberOfImageLand = width / land1.getWidth() + 2;
                landList = new ArrayList<Double>();
		listLand = new ArrayList<ImageLand>();
		for(int i = 0; i < numberOfImageLand; i++) {
			ImageLand imageLand = new ImageLand();
			imageLand.posX = i * land1.getWidth();
			setImageLand(imageLand , getTypeOfLand(2));
			listLand.add(imageLand);
                        landPosX = i * land1.getWidth();
                        landList.add(landPosX);
		}
	}
	
	public void update(){
            int i = 0;
		Iterator<ImageLand> itr = listLand.iterator();//เอสlistใส่iterater ทำให้จัดการทุกobjectง่าย
                landList.clear();
		ImageLand firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX();
                landPosX = firstElement.posX;
              
		float previousPosX = firstElement.posX;
		while(itr.hasNext()) {

			ImageLand element = itr.next();
                        
			element.posX = previousPosX + land1.getWidth();
                        landPosX = element.posX;
                        if(element.numberOfPit == 1 && element.posX <= mainCharacter.getPosX() && element.posX + land1.getWidth() >= mainCharacter.getPosX() + mainCharacter.getDinoWidth()-9){
                            mainCharacter.setLAND_POSY(500);
                            System.out.println(element.numberOfPit);
                        }
                        else if(element.numberOfPit == 2 && element.posX <= mainCharacter.getPosX()+ mainCharacter.getDinoWidth() -9 && element.posX + land1.getWidth() >= mainCharacter.getPosX()+ mainCharacter.getDinoWidth() - 9){
                            mainCharacter.setLAND_POSY(500);
                            System.out.println(element.numberOfPit);
                        }
                        else if(element.numberOfPit == 0 && element.posX <= mainCharacter.getPosX() + mainCharacter.getDinoWidth() -9 && element.posX + land1.getWidth() >= mainCharacter.getPosX()){
                            mainCharacter.setLAND_POSY(300);
                            System.out.println(" ");
                        }
			previousPosX = element.posX;
                        
		}
                
		if(firstElement.posX < -land1.getWidth()) {
			listLand.remove(firstElement);
                        
			firstElement.posX = previousPosX/*แกนx objectสุดท้าย พื้นชิ้นสุดท้าย*/ + land1.getWidth(); //ย้ายแกนxไปตัวสุดท้าย
                        landPosX = firstElement.posX;
                        firstElement.numberOfPit = 0;
                        int type = getTypeOfLand(4);
			setImageLand(firstElement ,type);
			listLand.add(firstElement);
                    
                        
                        
                     
		}
	}
	
	private void setImageLand(ImageLand imgLand, int type) {
		
                int typeLand = type;
                
		if(typeLand == 3 && GameScreen.enemyAndLandCount < 2) {
                    pitCount += 1;
                    if(pitCount >= 3){
                        setImageLand(imgLand, getTypeOfLand(4));
                        pitCount = 0;
                        
                        
                    }
                    else{
                        imgLand.image = land1;
                        imgLand.numberOfPit = pitCount;
                        
                    }
		} else if(typeLand == 2) {
                        pitCount = 0;
			imgLand.image = land3;
		} else {
                        pitCount = 0;
                    	imgLand.image = land2;
                        
		}
	}
	
	public void draw(Graphics g) {
		for(ImageLand imgLand : listLand) {
                    if(imgLand.numberOfPit == 0){
                        g.drawImage(imgLand.image, (int) imgLand.posX, LAND_POSY, null);
                    }
			
		}
	}
	
	private int getTypeOfLand(int limit) {
		Random rand = new Random();
		int type = rand.nextInt(limit);
		if(type == 1) {
			return 1;
		} else if(type == 2) {
			return 3;
		} else {
			return 2;
		}
	}
	
	private class ImageLand {
		float posX;
		BufferedImage image;
                int numberOfPit = 0;
                
	}
        
        public void reset(){
            listLand.clear();
            int numberOfImageLand = 1000 / land1.getWidth() + 2;
            for(int i = 0; i < numberOfImageLand; i++) {
			ImageLand imageLand = new ImageLand();
			imageLand.posX = i * land1.getWidth();
			setImageLand(imageLand ,getTypeOfLand(2));
			listLand.add(imageLand);
		}
        }
	
}
