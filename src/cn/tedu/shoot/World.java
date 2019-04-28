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

 
public class World extends JPanel {



	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	private static final long serialVersionUID = -7645613151332238326L;


	Logic logic = new Logic();

	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;

	Socket socket = new Socket();

	ObjectOutputStream objos ;

	DataInputStream dis ;
	DataOutputStream dos ;

	ByteArrayOutputStream bos ;
	ByteArrayInputStream bis ;



	
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	static{
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.png");
	}
	public void checkGameOverAction(){
		if(logic.getHero().getLife()<=0){
			state = GAME_OVER;
		}
	}
	Map<String, FlyingObject> map = new HashMap<>();
	int num = 1;
	int iospeed = 0 ;
	public void io(){
		for(FlyingObject f : logic.getEnemies()){
			map.put("e",f);
		}
		map.put("sky",logic.getSky());
		map.put("hero",logic.getHero());
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

	public void action(){

		MouseAdapter l = new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
					int x = e.getX();
					int y = e.getY();
					logic.getHero().moveTo(x, y);
				}
			}
			public void mouseClicked(MouseEvent e){
				switch(state){
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					logic.getScore();
					logic.restart();
					state = START;
					break;
				}
			}
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state = PAUSE;
				}
			}
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		
		Timer timer = new Timer();
		int intervel = 10;
		timer.schedule(new TimerTask(){
			public void run(){
				if(state==RUNNING){
					logic.enterAction();
					logic.stepAction();
					logic.shootAction();
					logic.outOfBoundsAction();
					logic.bangAction();
					logic.hitAction();
					checkGameOverAction();
//					io();

				}
				repaint();
			}
		},intervel,intervel);
	}
	
	public void paint(Graphics g){
		logic.getSky().paint(g);
		logic.getHero().paint(g);
		for(int i=0;i<logic.getEnemies().length;i++){
			logic.getEnemies()[i].paint(g);
		}
		for(int i=0;i<logic.getBullets().length;i++){
			logic.getBullets()[i].paint(g);
		}
		
		g.drawString("SCORE: "+logic.getScore(),10,25);
		g.drawString("LIFE: "+logic.getHero().getLife(),10,45);
		
		switch(state){
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
			break;
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
}
