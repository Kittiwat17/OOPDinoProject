package AllObject;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import OptionClass.Animation;
import OptionClass.Resource;

public class DinoCharacter {

    public int score = 0;
    public static int LAND_POSY = 370;
    public float GRAVITY = 0.2f;

    private static final int NORMAL_RUN = 0;
    private static final int JUMPING = 1;
    private static final int DOWN_RUN = 2;
    private static final int DEATH = 3;

    private float posY;
    private float posX;
    private double speedX;
    private float speedY;
    private Rectangle rectBound;

    private int countJump = 0;
    private int hp = 400;

    private int state = NORMAL_RUN;

    private Animation normalRunAnim;
    private BufferedImage jumping;
    private Animation downRunAnim;
    private BufferedImage deathImage;

    private AudioClip jumpSound;
    private AudioClip deadSound;
    private AudioClip scoreUpSound;

    private BufferedImage normalState;
    private BufferedImage downState;

    public DinoCharacter() {
        posX = 50;
        posY = LAND_POSY;
        normalState = Resource.getResouceImage("data/main-character1.png");
        downState = Resource.getResouceImage("data/main-character5.png");
        rectBound = new Rectangle();
        normalRunAnim = new Animation(90);
        normalRunAnim.addFrame(Resource.getResouceImage("data/main-character1.png"));
        normalRunAnim.addFrame(Resource.getResouceImage("data/main-character2.png"));
        jumping = Resource.getResouceImage("data/main-character3.png");
        downRunAnim = new Animation(90);
        downRunAnim.addFrame(Resource.getResouceImage("data/main-character5.png"));
        downRunAnim.addFrame(Resource.getResouceImage("data/main-character6.png"));
        deathImage = Resource.getResouceImage("data/main-character4.png");

        try {
            jumpSound = Applet.newAudioClip(new URL("file", "", "data/jump.wav"));
            deadSound = Applet.newAudioClip(new URL("file", "", "data/dead.wav"));
            scoreUpSound = Applet.newAudioClip(new URL("file", "", "data/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
       
        this.speedX = speedX;
    }

    public void draw(Graphics g) {
        switch (state) {
            case NORMAL_RUN:
                g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) getPosY(), null);
                break;
            case JUMPING:
                g.drawImage(jumping, (int) posX, (int) getPosY(), null);
                break;
            case DOWN_RUN:
                g.drawImage(downRunAnim.getFrame(), (int) posX, (int) (getPosY() + 20), null);
                break;
            case DEATH:
                g.drawImage(deathImage, (int) posX, (int) getPosY(), null);
                break;
        }
//		Rectangle bound = getBound();
//		g.setColor(Color.RED);
//		g.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    public void update() {
        normalRunAnim.updateFrame();
        downRunAnim.updateFrame();
        this.setSpeedX(this.getSpeedX());
        if (getPosY() >= LAND_POSY) {
            posY = LAND_POSY;
            countJump = 0;
            if (state != DOWN_RUN) {
                state = NORMAL_RUN;
            }
        } else {
            speedY += GRAVITY;
            posY += speedY;
        }
    }

    public void jump() {
        if (getPosY() <= LAND_POSY && countJump < 2) {
            countJump += 1;
            if (jumpSound != null) {
                jumpSound.play();
            }
            GRAVITY = 0.3f;
            speedY = -8.5f;
            posY += speedY;
            state = JUMPING;
        }
    }

    public void down(boolean isDown) {

        if (isDown) {
            state = DOWN_RUN;
            GRAVITY = 2;
        } else {
            state = NORMAL_RUN;
        }
    }

    public Rectangle getBound() {
        rectBound = new Rectangle();
        if (state == DOWN_RUN) {
            rectBound.x = (int) posX + 5;
            rectBound.y = (int) getPosY() + 20;
            rectBound.width = downRunAnim.getFrame().getWidth() - 10;
            rectBound.height = downRunAnim.getFrame().getHeight();
        } else {
            rectBound.x = (int) posX + 5;
            rectBound.y = (int) getPosY();
            rectBound.width = normalRunAnim.getFrame().getWidth() - 10;
            rectBound.height = normalRunAnim.getFrame().getHeight();
        }
        return rectBound;
    }

    public void dead(boolean isDeath) {
        if (isDeath) {
            state = DEATH;
        } else {
            state = NORMAL_RUN;
        }
    }

    public void reset() {
        posY = LAND_POSY;
        setHp(400);
    }

    public void playDeadSound() {
        deadSound.play();
    }

    public void playScoreSound() {
        scoreUpSound.play();
    }

    public void setLAND_POSY(int g) {
        LAND_POSY = g;
    }

    public int getLAND_POSY() {
        return LAND_POSY;
    }

    public float getPosX() {
        return posX;
    }

    public float getDinoWidth() {
        if (state == NORMAL_RUN) {
            return normalState.getWidth();
        } else {
            return downState.getWidth();
        }
    }

    public float getPosY() {
        return posY;
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

}
