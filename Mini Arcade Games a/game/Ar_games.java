package game;
import java.util.*;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.*;
import javax.swing.JOptionPane;


public class Ar_games extends JFrame implements ActionListener,KeyListener{
	JLabel label;
	ImageIcon image;
	JLabel l;
    JLabel l1;
    JTextField t;
    ImageIcon i;
    JButton f;
    String name;
    Container c;
    Random rando;
    int mus;
    Clip cl;
    JButton off;
    JButton on;
	
	SnakeGameRun ssnake;
	GameFrame parking;

	 Ar_games(){
		Container c = this.getContentPane();
		JPanel b = new JPanel();
		rando=new Random();
		mus=rando.nextInt(3);
		
		
		c.setBackground(Color.BLACK);
		b.setLayout(new BorderLayout());
		b.setBackground(Color.BLACK);
		image = new ImageIcon(getClass().getResource("Images/image1.png"));
		label = new JLabel(image);
		b.add(label);
		c.add(b);
		this.setSize(2000,2000);
		this.setVisible(true);
		try{
			Thread.sleep(3000);
			
		}
		catch(Exception e){
			
		}
		this.userID();
}

	 void userID(){

		this.getContentPane().removeAll();		
		this.repaint();
		Container c = this.getContentPane();
		JPanel d = new JPanel();
	    d.setLayout(null);
	    f = new JButton("SUBMIT");
	    f.setBackground(Color.RED);
	    f.setFont(new Font("Tahoma", 3, 12));
	    f.setBounds(210,380,140,40);
	    f.addActionListener(this);
	    d.add(f);

	    l = new JLabel("USERNAME");
	    l.setBackground(Color.BLACK);
	    l.setFont(new Font("Arial", 3, 18));
	    l.setForeground(Color.RED);
	    l.setBounds(60,320,110,60);
	    d.add(l);

	    t = new JTextField(20);
	    //t.setFocusable(true);
	    //t.requestFocusInWindow();
	    
	    t.setFont(new Font("Tahoma",3,12));
	    t.addKeyListener(this);
	    t.setBounds(190,330,180,40);
	   
	    d.add(t);


	    l1 = new JLabel();
	    l1.setIcon(new ImageIcon(getClass().getResource("Images/image2.jpg")));
	    l1.setBounds(0,0,1600,900);
	    d.add(l1);

	    

	    c.add(d);
	  

	    this.setSize(1600,900);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	 }


	 void gameRoom(String text){
	 	this.getContentPane().removeAll();		
		this.repaint();
	 	Container c = this.getContentPane();
	    JPanel i = new JPanel();
	    i.setBackground(Color.BLACK);
	    i.setLayout(null);

	    JButton j = new JButton("Snake");
	    j.setIcon(new ImageIcon(getClass().getResource("Images/snake logo.png")));
	    j.setBounds(290-125,170,160,130);
	    j.addActionListener(this);
	    i.add(j);

	    JButton j2 = new JButton("Parking");
	    j2.setIcon(new ImageIcon(getClass().getResource("Images/car logo.jpg")));
	    j2.setBounds(1150-125,180,160,130); 
	    j2.addActionListener(this);
	    i.add(j2);


	    this.on=new JButton("Music On");
        //this.on.setIcon(new ImageIcon(getClass().getResource("km1.jpg")));
        this.on.setBackground(Color.BLACK);
        this.on.setFont(new Font("Segoe Print", 3, 18));
        this.on.setForeground(Color.GREEN);
        this.on.setBounds(1180-125,620,140,30);
        this.on.addActionListener(this);
        i.add(on);

        this.off=new JButton("Music Off");
        this.off.setBackground(Color.BLACK);
        this.off.setFont(new Font("Segoe Print", 3, 18));
        this.off.setForeground(Color.RED);
        this.off.setBounds(1180-125,620,140,30);
        
        this.off.addActionListener(this);
        i.add(this.off);
        this.off.setEnabled(false);
        this.off.setVisible(false);


	    JLabel k = new JLabel();
	    k.setFont(new Font("Arial Black",1,14));
	    k.setForeground(Color.YELLOW);
	    k.setText("SNAKE GAME");
	    k.setBounds(300-125,310,140,30);
	    i.add(k);


	    JLabel k2 = new JLabel();
	    k2.setFont(new Font("Arial Black",1,14));
	    k2.setForeground(Color.YELLOW);
	    k2.setText("CAR PARKING");
	    k2.setBounds(1180-125,320,120,30);
	    i.add(k2);


	    JLabel k3 = new JLabel();
	    k3.setFont(new Font("Arial Black", 3, 18));
        k3.setForeground(Color.gray);
        name = text;
        k3.setText("HELLO "+ name);
        k3.setBounds(700-125, 80, 250, 50);
        i.add(k3);

        JButton j4 = new JButton("RPS");
	    j4.setIcon(new ImageIcon(getClass().getResource("Images/game logo.png")));
	    j4.setBounds(290-125,480,160,130);
	    j4.addActionListener(this);
	    i.add(j4);

	    JLabel k5 = new JLabel();
	    k5.setFont(new Font("Arial Black",1,14));
	    k5.setForeground(Color.YELLOW);
	    k5.setText("ROCK PAPER SCISSORS");
	    k5.setBounds(280-125,620,200,30);
	    i.add(k5);
        

        JLabel k4 = new JLabel();
        k4.setIcon(new ImageIcon(getClass().getResource("Images/image3.jpg")));
        k4.setBounds(300-125, -230, 1000, 1300);
        i.add(k4);

        
        


        JButton j3 = new JButton("back");
        j3.setBackground(Color.BLACK);
        j3.setFont(new Font("Comic Sans MS", 3, 18));
        j3.setForeground(Color.YELLOW);
        j3.setBounds(20, 10, 100, 100);
        j3.addActionListener(this);
        i.add(j3);

        c.add(i);
        this.setSize(1600,811);
    	this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	 }


	 public void actionPerformed(ActionEvent ae){
	 
	 	String action1 = ae.getActionCommand();
	 	String text = t.getText();
	 	Music music=new Music("3.wav");
	 	if(mus==0 ){
				 music = new Music("1.wav");
				
			}
			else if (mus==1){
				 music = new Music("2.wav");
				
			}
			else if(mus==2){
				 music = new Music("3.wav");
				
			}
	 	 if(action1 == "SUBMIT"&& text.isEmpty() || text == ""){
	 		this.userID();
	 		
	 	}
	 	else if(action1=="Music On"){
	 		cl=music.playMusic();
	 		this.on.setEnabled(false);
	 		this.on.setVisible(false);
	 		this.off.setVisible(true);
	 		this.off.setEnabled(true);
	 		
				
	 		
			

	 	}
	 	else if(action1=="Music Off"){
	 		cl.stop();
	 		this.off.setEnabled(false);
	 		this.off.setVisible(false);
	 		this.on.setVisible(true);
	 		this.on.setEnabled(true);

	 	}
	 	else if(action1 == "SUBMIT"){
	 		this.gameRoom(text);	
	 	}

	 	else if(action1 == "back"){
	 		this.userID();
	 	}

	 	else if(action1 == "RPS"){
	 		new RockPaperScissors(text);
	 	}

		
		else if(action1 == "Snake"){
			String[] args={this.name};
			SnakeGameRun.main(args);
 	
			
	 		 
		}
    

	 	
		
		else if(action1 == "Parking"){
	 		
			parking = new GameFrame(text);
			parking.setTitle("Parking");
			parking.setSize(615, 438);
			parking.setVisible(true);
			parking.setResizable(false);
			
	 	}

	};


	
	public void keyPressed(KeyEvent k){
		String text = t.getText();
	    if(k.getKeyCode() == KeyEvent.VK_ENTER){
	 		  this.gameRoom(text);
	    	}
	    }

	public void keyReleased(KeyEvent k){

	}

	public void keyTyped(KeyEvent k){

	}
	


	public static void main (String[] args){
		Ar_games a = new Ar_games();
	}		
}
