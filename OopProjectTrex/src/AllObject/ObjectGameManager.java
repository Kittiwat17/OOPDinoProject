/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AllObject;

import java.awt.image.BufferedImage;
import java.util.List;

import OptionClass.Resource;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import userinterface.GameScreen;
/**
 *
 * @author memory
 */
public class ObjectGameManager {
    
    public int countEnemy = 0;
    
    public static final int LAND_POSY = 273;
    
    private List<landBox> listLand;
    private BufferedImage land1;
    private BufferedImage land2;
    private BufferedImage land3;
    private int pitCount = 0;
    
    
    private DinoCharacter mainCharacter;
    
    
    private BufferedImage cactus1;
    private BufferedImage cactus2;
    private BufferedImage blank;
    private Random rand;
	
    private ArrayList<Enemy> listEnemies;
	
    private int boxWidth = 100;
    private ArrayList<Cactus> listEnemie;
    private blankBox blankBox;
        
        
    public ObjectGameManager(int width, DinoCharacter mainCharacter){
        this.mainCharacter = mainCharacter;
        land1 = Resource.getResouceImage("data/land-1.png");
		land2 = Resource.getResouceImage("data/land-2.png");
		land3 = Resource.getResouceImage("data/land-3.png");
		int numberOfImageLand = width / land1.getWidth() + 2;
                listEnemies = new ArrayList<Enemy>();
		listLand = new ArrayList<landBox>();
		for(int i = 0; i < numberOfImageLand; i++) {
			landBox imageLand = new landBox();
			imageLand.posX = i * land1.getWidth();
			setImageLand(imageLand ,1);
			listLand.add(imageLand);
                        listEnemies.add(createEnemy(0, i * 100));
		}
                
                
	
    }
    
    public void update(){
		Iterator<landBox> itr = listLand.iterator();//เอาlistใส่iterater ทำให้จัดการทุกobjectง่าย
             
		landBox firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX();
                
              
		float previousPosX = firstElement.posX;
		while(itr.hasNext()) {

			landBox element = itr.next();
                        
			element.posX = previousPosX + land1.getWidth();
                       
                        if(element.numberOfPit == 1 && element.posX <= mainCharacter.getPosX() && element.posX + land1.getWidth() >= mainCharacter.getPosX() + mainCharacter.getDinoWidth()-20){
                            mainCharacter.setLAND_POSY(500);
                            System.out.println(element.numberOfPit);
                        }
                        else if(element.numberOfPit == 2 && element.posX <= mainCharacter.getPosX()+ mainCharacter.getDinoWidth() -9 && element.posX + land1.getWidth() >= mainCharacter.getPosX()+ mainCharacter.getDinoWidth() - 9){
                            mainCharacter.setLAND_POSY(500);
                            System.out.println(element.numberOfPit);
                        }
                        else if(element.numberOfPit == 0 && element.posX <= mainCharacter.getPosX() + mainCharacter.getDinoWidth() -20 && element.posX + land1.getWidth() >= mainCharacter.getPosX()){
                            mainCharacter.setLAND_POSY(250);
                            System.out.println(" ");
                        }
			previousPosX = element.posX;
                        
		}
                for(Enemy e : listEnemies) {
                    
			e.update(0);
                      
		}
		if(firstElement.posX < -land1.getWidth()) {
			listLand.remove(firstElement);                       
			firstElement.posX = previousPosX/*แกนx objectสุดท้าย พื้นชิ้นสุดท้าย*/ + land1.getWidth(); //ย้ายแกนxไปตัวสุดท้าย
                        firstElement.numberOfPit = 0;
                        int lType = randomNumber(4);
                        int eType = randomNumber(4);
                        
                        if(lType == 0){
                            countEnemy += 1;
                            if(countEnemy < 3){
                                eType = 3;
                            }
                            else if(eType == 1 || eType == 2){
                                eType = 3;
                                lType = 2;
                                countEnemy = 0;
                            }
                            else if(eType != 1 && eType != 2){
                                lType = 1;
                                countEnemy = 0;
                            }
                            
                        }
                        else if(eType == 1 || eType == 2){
                            countEnemy += 1;
                            if(countEnemy >= 3){
                                eType = 3;
                                countEnemy = 0;
                            }
                        }
                        
                        
                        
			setImageLand(firstElement ,lType);
			listLand.add(firstElement);
                        
                        listEnemies.add(createEnemy(eType, 1100));
                        
                        
                        
		}
	}
    
    private void setImageLand(landBox imgLand, int type) {
		
                int typeLand = type;
                
		if(typeLand == 0) {
                    pitCount += 1;
                    if(pitCount >= 3){
                        setImageLand(imgLand, randomNumber(3) + 1);
                        pitCount = 0;                     
                    }
                    else{
                        imgLand.image = land1;
                        imgLand.numberOfPit = pitCount;
                        
                    }
		} else if(typeLand == 1) {
                        pitCount = 0;
			imgLand.image = land1;
		} else if(typeLand == 2) {
                        pitCount = 0;
			imgLand.image = land2;
		}  else {
                        pitCount = 0;
                    	imgLand.image = land3;
                        
		}
	}
    
    public Enemy createEnemy(int type, int posX){
            if(type == 1){
                return new Cactus(mainCharacter, posX);
                
            }
            if(type == 2){
                return new AirEnemy(mainCharacter, posX);
            }
            else{
                return new blankBox(mainCharacter, posX);
            }
        }
    
    public void draw(Graphics g) {
		for(landBox imgLand : listLand) {
                    if(imgLand.numberOfPit == 0){
                        g.drawImage(imgLand.image, (int) imgLand.posX, LAND_POSY, null);
                    }
		}
                for(Enemy e : listEnemies) {
                     e.draw(g);	
		}
                
	}
    
    public int randomNumber(int limit){
        Random rand = new Random();
        int num = rand.nextInt(limit);
        return num;
    }
    
    public boolean isCollision() {
	for(Enemy e : listEnemies) {
            if (mainCharacter.getBound().intersects(e.getBound())) {
		return true;
            }
	}
	return false;
    }
    
    public void reset(){
            listLand.clear();
            listEnemies.clear();
            int numberOfImageLand = 1000 / land1.getWidth() + 2;
            for(int i = 0; i < numberOfImageLand; i++) {
			landBox imageLand = new landBox();
			imageLand.posX = i * land1.getWidth();
			setImageLand(imageLand ,1);
			listLand.add(imageLand);
                        listEnemies.add(createEnemy(0, i * 100));
		}
        }
}
