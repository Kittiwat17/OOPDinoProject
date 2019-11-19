package AllObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import OptionClass.Resource;
import userinterface.GameScreen;

public class EnemiesManager {
	
	private BufferedImage cactus1;
	private BufferedImage cactus2;
        private BufferedImage blank;
	private Random rand;
	
	private DinoCharacter mainCharacter;
        private ArrayList<Enemy> listEnemies;
	
        private int boxWidth = 100;
        private ArrayList<Cactus> listEnemie;
        private blankBox blankBox;
        int previousPosX = 1100;
	public EnemiesManager(DinoCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		int numberOfImageEnemy = 1000 / 100 + 2;
                listEnemies = new ArrayList<Enemy>();
		for(int i = 0; i < numberOfImageEnemy; i++) {
                    listEnemies.add(createEnemy(0, i * 100));
		}
	}
	
	public void update() {
            int i = 0;
		for(Enemy e : listEnemies) {
                    
			e.update(0);
                      i += 1;
		}
		Enemy enemy = listEnemies.get(0);
		if(enemy.isOutOfScreen()) {
                        int type = getTypeOfEnemy();
			listEnemies.remove(enemy);
			listEnemies.add(createEnemy(type, previousPosX));
                        
		}
	}
	
	public void draw(Graphics g) {
		for(Enemy e : listEnemies) {
                     e.draw(g);	
		}
	}
	
	private int getTypeOfEnemy() {
		Random rand = new Random();
		int type = rand.nextInt(2);
		if(type == 0) {
			return 0;
		}else {
			return 1;
		}
	}
        
        public Enemy createEnemy(int type, int posX){
            if(type == 1){
                return new Cactus(mainCharacter, posX);
                
            }
            else{
                return new blankBox(mainCharacter, posX);
            }
        }
        
        public int randomType(){
            Random rand = new Random();
            int type = rand.nextInt(1);
            if(type == 1){
                return 1;
            }
            else{
                return 0;
            }
        }
	
	public boolean isCollision() {
		for(Enemy e : listEnemies) {
			if (mainCharacter.getBound().intersects(e.getBound())) {
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		listEnemies.clear();
                int numberOfImageEnemy = 1000 / 100 + 2;
		for(int i = 0; i < numberOfImageEnemy; i++) {
                    listEnemies.add(createEnemy(0, i * 100));
		}
	}
	
}
