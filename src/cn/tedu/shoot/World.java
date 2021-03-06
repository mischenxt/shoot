package cn.tedu.shoot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;


public class World extends JPanel implements Runnable {

    public static final int WIDTH = 450;
    public static final int HEIGHT = 750;
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;
    private static final long serialVersionUID = -7645613151332238326L;
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage gameover;

    static {
        start = FlyingObject.loadImage("start.png");
        pause = FlyingObject.loadImage("pause.png");
        gameover = FlyingObject.loadImage("gameover.png");
    }

    Logic logic = new Logic();
    Socket socket = new Socket();
    ObjectOutputStream objos;
    DataInputStream dis;
    DataOutputStream dos;
    ByteArrayOutputStream bos;
    ByteArrayInputStream bis;
    Map<String, FlyingObject> map = new HashMap<>();
    int num = 1;
    int iospeed = 0;
    private int state = START;



    public void checkGameOverAction() {
        if (logic.getHero().getLife() <= 0) {
            state = GAME_OVER;
        }
    }

    public void io() {
        for (FlyingObject f : logic.getEnemies()) {
            map.put("e", f);
        }
        map.put("sky", logic.getSky());
        map.put("hero", logic.getHero());
        try {
            bos = new ByteArrayOutputStream();
            objos = new ObjectOutputStream(bos);
            objos.writeObject(logic.getHero());
            byte[] bytes = bos.toByteArray();
            OutputStream sos = socket.getOutputStream();
            sos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void action() {
        MouseAdapter l = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (state == RUNNING) {
                    int x = e.getX();
                    int y = e.getY();
                    logic.getHero().moveTo(x, y);
                }
            }

            public void mouseClicked(MouseEvent e) {
                switch (state) {
                    case START -> state = RUNNING;
                    case GAME_OVER -> {
                        logic.getScore();
                        logic.restart();
                        state = START;
                    }
                }
            }

            public void mouseExited(MouseEvent e) {
                if (state == RUNNING) {
                    state = PAUSE;
                }
            }

            public void mouseEntered(MouseEvent e) {
                if (state == PAUSE) {
                    state = RUNNING;
                }
            }
        };
        this.addMouseListener(l);
        this.addMouseMotionListener(l);

        Timer timer = new Timer();
        int intervel = 10;
        timer.schedule(new TimerTask() {
            public void run() {
                if (state == RUNNING) {
                    logic.enterAction();
                    logic.stepAction();
                    logic.shootAction();
                    logic.outOfBoundsAction();
                    logic.bangAction();
                    logic.hitAction();
                    checkGameOverAction();

                }
                repaint();
            }
        }, intervel, intervel);
    }

    public void paint(Graphics g) {
        logic.getSky().paint(g);
        logic.getHero().paint(g);
        IntStream.range(0, logic.getEnemies().length).forEach(i -> logic.getEnemies()[i].paint(g));
        IntStream.range(0, logic.getBullets().length).forEach(i -> logic.getBullets()[i].paint(g));

        g.drawString("SCORE: " + logic.getScore(), 10, 25);
        g.drawString("LIFE: " + logic.getHero().getLife(), 10, 45);

        switch (state) {
            case START -> g.drawImage(start, 0, 0, null);
            case PAUSE -> g.drawImage(pause, 0, 0, null);
            case GAME_OVER -> g.drawImage(gameover, 0, 0, null);
        }
    }


    public static void main(String[] args) {

        JFrame frame = new JFrame();
        World world = new World();
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        world.action();
    }

    @Override
    public void run() {

    }



}
