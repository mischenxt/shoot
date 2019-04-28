package cn.tedu.shoot;

import java.awt.image.BufferedImage;


public class BigAirplane extends FlyingObject implements Enemy {

    private static final long serialVersionUID = -1046332035777684629L;
    private static BufferedImage[] images;

    static {
        images = new BufferedImage[5];
        for (int i = 0; i < images.length; i++) {
            images[i] = loadImage("bigplane" + i + ".png");
        }
    }

    int deadIndex = 1;
    private int step;


    public BigAirplane() {
        width = 66;
        height = 99;
        x = (int) (Math.random() * (World.WIDTH - this.width));
        y = -this.height;
        step = 2;
    }


    public void step() {
        y += step;
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

    public int getScore() {
        return 3;
    }

    public boolean outOfBounds() {
        return this.y >= World.HEIGHT;
    }
}





