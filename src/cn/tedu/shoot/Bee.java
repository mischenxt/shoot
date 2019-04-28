package cn.tedu.shoot;

import java.awt.image.BufferedImage;


public class Bee extends FlyingObject implements Award {

    private static final long serialVersionUID = 4200334689795722909L;
    private static BufferedImage[] images;

    static {
        images = new BufferedImage[5];
        for (int i = 0; i < images.length; i++) {
            images[i] = loadImage("bee" + i + ".png");
        }
    }

    int deadIndex = 1;
    private int xStep;
    private int yStep;
    private int awardType;


    public Bee() {
        width = 60;
        height = 50;
        x = (int) (Math.random() * (World.WIDTH - this.width));
        y = -this.height;
        xStep = 1;
        yStep = 2;
        awardType = (int) (Math.random() * 2);
    }


    public void step() {
        x += xStep;
        y += yStep;
        if (x >= World.WIDTH - this.width || x <= 0) {
            xStep *= -1;
        }
    }

    @Override
    public BufferedImage getImage() {
        if (isLife()) {
            return images[0];
        } else if (isDead()) {
            BufferedImage img = images[deadIndex++];
            if (deadIndex == images.length) {
                state = REMOVE;
            }
            return img;
        }
        return null;
    }

    public int getType() {
        return awardType;
    }

    public boolean outOfBounds() {
        return this.y >= World.HEIGHT;
    }
}










