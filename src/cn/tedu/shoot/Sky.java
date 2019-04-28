package cn.tedu.shoot;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Sky extends FlyingObject {

    private static final long serialVersionUID = -6316347524480329367L;
    private static BufferedImage image;

    static {
        image = loadImage("background.png");
    }

    private int step;
    private int y1;


    public Sky() {
        width = World.WIDTH;
        height = World.HEIGHT;
        x = 0;
        y = 0;
        step = 1;
        y1 = -this.height;
    }


    public void step() {
        y += step;
        y1 += step;
        if (y >= this.height) {
            y = -this.height;
        }
        if (y1 >= this.height) {
            y1 = -this.height;
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void paint(Graphics g) {
        g.drawImage(getImage(), x, y, null);
        g.drawImage(getImage(), x, y1, null);
    }

    public boolean outOfBounds() {
        return false;
    }

}










