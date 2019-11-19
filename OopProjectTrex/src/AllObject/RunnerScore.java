/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AllObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author memory
 */
public class RunnerScore implements Runnable{
    private boolean caseRun = false;
    private int score = 0;
    private int runnerTime = 500;
    private static double timeUpdate = 100;
    @Override
    public void run() {
        while (true) {            
            score = score + 1;
            try {
                Thread.sleep(runnerTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(RunnerScore.class.getName()).log(Level.SEVERE, null, ex);
            }
            timeUpdate += timeUpdate / 500;
            if(runnerTime - timeUpdate / 500 <= 50){
                runnerTime = 50;
            }
            else{
                runnerTime -= timeUpdate / 500;
            }
            
            
            
        }
        
    }
    
    public int getScore(){
        return score;
    }
    
    public void reRunnerScore(){
        score = 0;
        timeUpdate = 100;
        runnerTime = 500;
      
    }
    
    public static double getTimeUpdate(){
        return timeUpdate;
    }
    
    
}
