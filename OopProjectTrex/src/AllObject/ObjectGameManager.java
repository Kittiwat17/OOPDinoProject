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
import java.awt.Graphics2D;
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
    
    public static final int LAND_POSY = 403;
    
    private List<landBox> listLand;
    private BufferedImage land[] = new BufferedImage[4];
    private int pitCount = 0;
    
    
    private DinoCharacter mainCharacter;
    
    
    private BufferedImage blank;
    private BufferedImage pit[][] = new BufferedImage[4][3];
    private Random rand;
	
    private ArrayList<Enemy> listEnemies;
	
    private int boxWidth = 100;
    private blankBox blankBox;
        
    private landBox pre;   
    public ObjectGameManager(int width, DinoCharacter mainCharacter){
        this.mainCharacter = mainCharacter;
        land[0] = Resource.getResouceImage("Game Element/Floor1.jpg");
		land[1] = Resource.getResouceImage("Game Element/Floor2.jpg");
		land[2] = Resource.getResouceImage("Game Element/Floor3.jpg");
                land[3] = Resource.getResouceImage("Game Element/Floor4.jpg");
                
                pit[0][0] = Resource.getResouceImage("Game Element/Hole1.png");
                pit[0][1] = Resource.getResouceImage("Game Element/HoleL1.png");
                pit[0][2] = Resource.getResouceImage("Game Element/HoleR1.png");
                
                pit[1][0] = Resource.getResouceImage("Game Element/lava1B.png");
                pit[1][1] = Resource.getResouceImage("Game Element/lava2Bver1.png");
                pit[1][2] = Resource.getResouceImage("Game Element/lava2Bver2.png");
                
                pit[2][0] = Resource.getResouceImage("Game Element/Chemis1B.png");
                pit[2][1] = Resource.getResouceImage("Game Element/Chemis2Bver1.png");
                pit[2][2] = Resource.getResouceImage("Game Element/Chemis2Bver2.png");
                
                pit[3][0] = Resource.getResouceImage("Game Element/rope1B.png");
                pit[3][1] = Resource.getResouceImage("Game Element/rope2Bver1.png");
                pit[3][2] = Resource.getResouceImage("Game Element/rope2Bver2.png");
		int numberOfImageLand = width / land[0].getWidth() + 2;
                listEnemies = new ArrayList<Enemy>();
		listLand = new ArrayList<landBox>();
		for(int i = 0; i < numberOfImageLand; i++) {
			landBox imageLand = new landBox();
			imageLand.posX = i * land[0].getWidth();
			setImageLand(imageLand ,1);
			listLand.add(imageLand);
     
		}
                
                
                
	
    }
    
    public void update(){
		Iterator<landBox> itr = listLand.iterator();//เอาlistใส่iterater ทำให้จัดการทุกobjectง่าย
             
		landBox firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX();
                pre = firstElement;
              
		float previousPosX = firstElement.posX;
		while(itr.hasNext()) {

			landBox element = itr.next();
                        if(element.numberOfPit != 0 && pre.numberOfPit != 0){
                       
                               pre.image = pit[GameScreen.countStage][1];
                                element.image = pit[GameScreen.countStage][2]; 
                         
                            
                        }
			element.posX = previousPosX + land[0].getWidth();
                       
                        if(element.numberOfPit == 1 && element.posX <= mainCharacter.getPosX() && element.posX + land[0].getWidth() >= mainCharacter.getPosX() + mainCharacter.getDinoWidth()-20){
                            mainCharacter.setLAND_POSY(500);
                            System.out.println(element.numberOfPit);
                        }
                        else if(element.numberOfPit == 2 && element.posX <= mainCharacter.getPosX()+ mainCharacter.getDinoWidth() -9 && element.posX + land[0].getWidth() >= mainCharacter.getPosX()+ mainCharacter.getDinoWidth() - 9){
                            mainCharacter.setLAND_POSY(500);
                            System.out.println(element.numberOfPit);
                        }
                        else if(element.numberOfPit == 0 && element.posX <= mainCharacter.getPosX() + mainCharacter.getDinoWidth() -20 && element.posX + land[0].getWidth() >= mainCharacter.getPosX()){
                            mainCharacter.setLAND_POSY(370);
                            System.out.println(" ");
                        }
			previousPosX = element.posX;
                        pre = element;
                        
		}
                for(Enemy e : listEnemies) {
                    
			e.update(0);
                      
		}
		if(firstElement.posX < -land[0].getWidth()) {
			listLand.remove(firstElement);                       
			firstElement.posX = previousPosX/*แกนx objectสุดท้าย พื้นชิ้นสุดท้าย*/ + land[0].getWidth(); //ย้ายแกนxไปตัวสุดท้าย
                        firstElement.numberOfPit = 0;
                        int lType = randomNumber(4);
                        int eType = randomNumber(4);
                        
                        if(lType == 0){
                            countEnemy += 1;
                            if(countEnemy < 3){
                                eType = 0;
                            }
                            else if(eType != 0){
                                eType = 0;
                                lType = 2;
                                countEnemy = 0;
                            }
                            else if(eType == 0){
                                lType = 1;
                                countEnemy = 0;
                            }
                            
                        }
                        else if(eType != 0){
                            countEnemy += 1;
                            if(countEnemy >= 3){
                                eType = 0;
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
                        imgLand.image = pit[GameScreen.countStage][0];
                        imgLand.numberOfPit = pitCount;
                        
                    }
		} else if(typeLand == 1) {
                        pitCount = 0;
			imgLand.image = land[GameScreen.countStage];
		} else if(typeLand == 2) {
                        pitCount = 0;
			imgLand.image = land[GameScreen.countStage];
		}  else {
                        pitCount = 0;
                    	imgLand.image = land[GameScreen.countStage];
                        
		}
                
	}
    
    public Enemy createEnemy(int type, int posX){
            if(type == 0){
                return new blankBox(mainCharacter, posX); 
                
            }
            if(type == 1){
                return new AirEnemy(mainCharacter, posX);
            }
            else{
                return new groundEnemy(mainCharacter, posX);
            }
        }
    
    public void draw(Graphics g) {
		for(landBox imgLand : listLand) {
                    g.drawImage(imgLand.image, (int) imgLand.posX, LAND_POSY, null);
                    
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
    
    public void newStage(){
        listEnemies.clear();
        listLand.clear();
        int numberOfImageLand = 1000 / land[GameScreen.countStage].getWidth() + 2;
            for(int i = 0; i < numberOfImageLand; i++) {
			landBox imageLand = new landBox();
			imageLand.posX = i * land[GameScreen.countStage].getWidth();
			setImageLand(imageLand ,1);
			listLand.add(imageLand);
                        listEnemies.add(createEnemy(0, i * 100));
		}
    }
    
    public void reset(){
            listLand.clear();
            listEnemies.clear();
            GameScreen.countStage = 0;
            int numberOfImageLand = 1000 / land[GameScreen.countStage].getWidth() + 2;
            for(int i = 0; i < numberOfImageLand; i++) {
			landBox imageLand = new landBox();
			imageLand.posX = i * land[GameScreen.countStage].getWidth();
			setImageLand(imageLand ,1);
			listLand.add(imageLand);
                        listEnemies.add(createEnemy(0, i * 100));
		}
        }
}
