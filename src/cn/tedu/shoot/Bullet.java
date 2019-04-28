package cn.tedu.shoot;

import java.awt.image.BufferedImage;


public class Bullet extends FlyingObject {

    private static final long serialVersionUID = -8499617051085217044L;
    private static BufferedImage image;

    static {
        image = loadImage("bullet.png");
    }

    private int step;


    public Bullet(int x, int y) {
        width = 8;
        height = 14;
        this.x = x;
        this.y = y;
        step = 3;
    }


    public void step() {
        y -= step;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    public boolean outOfBounds() {
        return this.y <= -this.height;
    }
}







