package cn.tedu.shoot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;


public abstract class FlyingObject implements Serializable {

    public static final int LIFE = 0;
    public static final int DEAD = 1;
    public static final int REMOVE = 2;
    private static final long serialVersionUID = 1556988606406659122L;
    private static BufferedImage[] images;
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected int state = LIFE;
    int deadIndex = 1;

    public static BufferedImage loadImage(String fileName) {
        try {
            BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
            return img;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public abstract void step();

    public abstract BufferedImage getImage();

    public void paint(Graphics g) {
        g.drawImage(getImage(), x, y, null);
    }


    public boolean isLife() {
        return state == LIFE;
    }

    public boolean isDead() {
        return state == DEAD;
    }

    public boolean isRemove() {
        return state == REMOVE;
    }

    public void goDead() {
        state = DEAD;
    }

    public boolean hit(FlyingObject other) {
        int x1 = this.x - other.width;
        int x2 = this.x + this.width;
        int y1 = this.y - other.height;
        int y2 = this.y + this.height;
        int x = other.x;
        int y = other.y;

        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    public abstract boolean outOfBounds();
}








