package cn.tedu.shoot;

import java.awt.image.BufferedImage;


public class Hero extends FlyingObject {

    private static final long serialVersionUID = 5301011950837327996L;
    private static BufferedImage[] images;

    static {
        images = new BufferedImage[2];
        for (int i = 0; i < images.length; i++) {
            images[i] = loadImage("hero" + i + ".png");
        }
    }

    int deadIndex = 2;
    private int life;
    private int doubleFire;
    private int index = 0;


    public Hero() {
        width = 97;
        height = 124;
        x = 140;
        y = 400;
        life = 3;
        doubleFire = 0;
    }

    public void step() {
    }


    public void moveTo(int x, int y) {
        this.x = x - this.width / 2;
        this.y = y - this.height / 2;
    }

    public BufferedImage getImage() {
        if (isLife()) {
            return images[index++ % 2];
        }
        return null;
    }

    public Bullet[] shoot() {
        int xStep = this.width / 4;
        int yStep = 20;
        if (doubleFire > 0) {
            Bullet[] bs = new Bullet[2];
            bs[0] = new Bullet(this.x + 1 * xStep, this.y - yStep);
            bs[1] = new Bullet(this.x + 3 * xStep, this.y - yStep);
            doubleFire -= 2;
            return bs;
        } else {
            Bullet[] bs = new Bullet[1];
            bs[0] = new Bullet(this.x + 2 * xStep, this.y - yStep);
            return bs;
        }
    }

    public void addLife() {
        life++;
    }

    public void addDoubleFire() {
        doubleFire += 40;
    }

    public int getLife() {
        return life;
    }

    public void subtractLife() {
        life--;
    }

    public int getDoubleFire() {
        return doubleFire;
    }

    public void clearDoubleFire() {
        doubleFire = 0;
    }

    public boolean outOfBounds() {
        return false;
    }

}








