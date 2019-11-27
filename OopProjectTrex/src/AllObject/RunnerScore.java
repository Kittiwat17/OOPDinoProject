/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AllObject;

import java.util.logging.Level;
import java.util.logging.Logger;
import userinterface.GameScreen;

/**
 *
 * @author memory
 */
public class RunnerScore implements Runnable{
 
    @Override
    public void run() {
        while (true) {            
            
            GameScreen.setSpeed(10000);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RunnerScore.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    
    
    
}
