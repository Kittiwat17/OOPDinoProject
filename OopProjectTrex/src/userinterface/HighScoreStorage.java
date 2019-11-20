
package userinterface;

import java.io.*;

public class HighScoreStorage {
    private static String name;
    private static int highscore;
    
    public static int getHighscore() {
        File f = new File("Highscore.dat");
        if (f.exists()) {
            try {
                FileInputStream fin = new FileInputStream("Highscore.dat");
                DataInputStream din = new DataInputStream(fin);
                
                highscore = din.readInt();
                
                din.close();
                fin.close();
            } catch(IOException e) {
                System.out.println("not read high score");
            }
            
        } else {
            highscore = 0;
        }
        
        return highscore;
    }
    
    public static String getName() {
        File f = new File("Name.dat");
        if (f.exists()) {
            try {
                FileInputStream fin = new FileInputStream("Name.dat");
                DataInputStream din = new DataInputStream(fin);
                
                name = din.readUTF();
                
                din.close();
                fin.close();
            } catch(IOException e) {
                System.out.println("not read name");
            }
            
        } else {
            name = "(none)";
        }
        
        return name;
    }
    
//    public static void saveHighscore(int hs, String n) {
//        try {
//            FileOutputStream fout = new FileOutputStream("Highscore.dat");
//            DataOutputStream dout = new DataOutputStream(fout);
//            
//            dout.writeInt(hs);
//            dout.writeUTF(n);
//            
//            dout.close();
//            fout.close();
//        } catch (IOException ex) {
//            System.out.println("not save high score");
//        }
//    }
    
    public static void saveHighscore(int hs) {
        try {
            FileOutputStream fout = new FileOutputStream("Highscore.dat");
            DataOutputStream dout = new DataOutputStream(fout);
            
            dout.writeInt(hs);
            
            dout.close();
            fout.close();
        } catch (IOException ex) {
            System.out.println("not save high score");
        }
    }
    
    public static void saveName(String n) {
        try {
            FileOutputStream fout = new FileOutputStream("Name.dat");
            DataOutputStream dout = new DataOutputStream(fout);
            
            dout.writeUTF(n);
            
            dout.close();
            fout.close();
        } catch (IOException ex) {
            System.out.println("not save name");
        }
    }
}
