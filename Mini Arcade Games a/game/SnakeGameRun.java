package game;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;
import java.io.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.PrintWriter;  

enum Direction{
	UP,DOWN,LEFT,RIGHT;
}
interface Updatable {
    void update();
}
enum C_STATE{
	MENU,GAME,SCORE,PVP;
}

class Piece{
	int x;
	int y;
	public Piece(int x,int y){
		this.x=x;
		this.y=y;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public String toString(){
		return "("+this.x+","+this.y+")";
	}
	public boolean runsInto(Piece piece){
		if(this.x==piece.getX()&&this.y==piece.getY()){
			return true;
		}
		return false;
	}
}
class Apple extends Piece{
	public Apple(int x,int y){
		super(x,y);
	}

}
class Food extends Piece{
	public Food(int x,int y){
		super(x,y);
	}
}
class Snake{
	int x;
	int y;
	Direction direction;
	ArrayList<Piece> snake;
	Iterator<Piece> iterator;
	boolean grow;
	C_STATE st;
	boolean moving;
	public Snake(int x,int y,Direction direction){
		this.snake=new ArrayList<Piece>();
		this.x=x;
		this.y=y;
		this.snake.add(new Piece(this.x,this.y));
		this.iterator=snake.iterator(); 
		this.direction=direction;
		this.grow=false;
		this.st=C_STATE.MENU;
		this.moving=false;



	}
	public Direction getDirection(){
		return this.direction;
	}
	public void changedDirection(Direction direction){
		if(direction==Direction.DOWN){
			setDirection(Direction.UP);
		}
		else if(direction==Direction.UP){
			setDirection(Direction.DOWN);
		}
		else if(direction==Direction.LEFT){
			setDirection(Direction.RIGHT);
		}
		else if(direction==Direction.RIGHT){
			setDirection(Direction.LEFT);
		}

	}
	public void setDirection(Direction direction){
		this.direction=direction;
	}
	public void resetMoving(){
		this.moving=false;
	}
	public boolean getMoving(){
		return this.moving;
	}
	public int snakeLength(){
		return snake.size();
	}
	public void setMoving(){
		this.moving=true;
	}
	public ArrayList<Piece> getPieces(){
		return this.snake;
	}
	public void move(){
		if(this.st==C_STATE.GAME||this.st==C_STATE.PVP){	
			int updateX=snake.get(snakeLength()-1).getX();
			int updateY=snake.get(snakeLength()-1).getY();

			if(this.direction==Direction.UP)
				updateY--;
			
			else if(this.direction==Direction.DOWN)
				updateY++;
			
			else if(this.direction==Direction.LEFT)
				updateX--;
			
			else if(this.direction==Direction.RIGHT)
				updateX++;
			 if(snakeLength()>2 && !grow){
				snake.remove(0);
				snake.add(new Piece(updateX,updateY));
				this.moving=true;
				//System.out.println(""+this.moving);
				


			}
			 if(snakeLength()<=2){
				snake.add(new Piece(updateX,updateY));
				this.moving=true;
			}
			 if(this.grow){
				snake.add(new Piece(updateX,updateY));
				this.grow=false;
			}
		}

		

	}
	public void grow(){
		this.grow=true;
	}
	public void growSmaller(){
		snake.remove(0);
	}
	public Piece head(){
		return (this.snake.get((this.snake.size()-1)));
	}
	public boolean runsInto(Piece piece){
		if(head().getX()==piece.getX ()&& head().getY()==piece.getY()){
			return true;
		}
		return false;
	}
	public C_STATE getCurrentState(){
		return this.st;
	}
	public void setC(){
		this.st=C_STATE.GAME;
	}
	public void setPvP(){
		this.st=C_STATE.PVP;
	}
	public void setCOff(){
		this.st=C_STATE.MENU;
	}
	public void setCScore(){
		this.st=C_STATE.SCORE;
	}
	public boolean runsIntoItself() {
        for (int i = 0; i < snakeLength() - 1; i++) {
            if (head().getX() == snake.get(i).getX() && head().getY() == snake.get(i).getY()) {
                return true;
            }
        }


        return false;
    }
    public boolean runsIntoItself(Snake snake1){
    	for(int i=0;i<=snakeLength()-1;i++){
    		if(snake1.head().getX()==snake.get(i).getX() && snake1.head().getY()==snake.get(i).getY()){
    			return true;
    		}

    	}
    	return false;
    }

}
class Obstacle{
	int x;
	int y;
	Direction direction;
	ArrayList<Piece> obstacle;
	Iterator<Piece> iterator;
	boolean grow;
	C_STATE st;
	Random random;
	public Obstacle(int x,int y){
		this.obstacle=new ArrayList<Piece>();
		this.x=x;
		this.y=y;
		random=new Random();
		this.obstacle.add(new Piece(random.nextInt(this.x),random.nextInt(this.y)));
		this.iterator=obstacle.iterator(); 
		this.direction=Direction.DOWN;
		
		



	}
	public Direction getDirection(){
		return this.direction;
	}
	public void setDirection(Direction direction){
		this.direction=direction;
	}

	public int obstacleLength(){
		return obstacle.size();
	}
	public ArrayList<Piece> getPieces(){
		return this.obstacle;
	}
	
	
	
	//public boolean runsInto(Piece piece){
	//	if(head().getX()==piece.getX ()&& head().getY()==piece.getY()){
	//		return true;
	//	}
	//	return false;
	//}
	public void addHObstacle(int x,int y,int xbound,int ybound){
		int randx=-1;
		int randy=-1;
		if(x==0){
			x=y;
		}
		if(y==0){
			y=x;
		}
		if(x>0 && y>0){
			
				//System.out.println("val : "+x);
				//System.out.println("val : "+y);
				randx=random.nextInt(x);

				randy=random.nextInt(y);
				if(randx>0 && randx<xbound && randy>0 && randy<ybound){
					obstacle.add(new Piece(randx,randy));
					obstacle.add(new Piece(randx+1,randy));
					obstacle.add(new Piece(randx+2,randy));
				}
			

			//System.out.println("rand x: "+randx);
			//System.out.println("rand y: "+randy);
		
			
		}
	}
	public void addVObstacle(int x,int y,int xbound,int ybound){
		int randx=-1;
		int randy=-1;
		if(x<=0 ){
			x=y;
		}
		else if(y<=0){
			y=x;
		}
		if(x>0 && y>0){
			
				//System.out.println("val : "+x);
				//System.out.println("val : "+y);
				randx=random.nextInt(x);
				
				randy=random.nextInt(y);
				if(randx>0 && randx<xbound && randy>0 && randy<ybound){
					obstacle.add(new Piece(randx,randy));
					obstacle.add(new Piece(randx,randy+1));
					obstacle.add(new Piece(randx,randy+2));		
				}
			
			//System.out.println("rand x: "+randx);
			//System.out.println("rand y: "+randy);
		
			
		}
	}
	public void removeObstacle(){
		obstacle.remove(0);
	}
	public boolean runsInto(Piece piece){
		for(int i=0;i<=obstacleLength()-1;i++){
			if(piece.getX()==this.obstacle.get(i).getX() && piece.getY()==this.obstacle.get(i).getY()){
				return true;
			}
		}
		return false;
	}

	
	
	
	
	
    public boolean runsIntoItself(Snake snake1){
    	for(int i=0;i<=obstacleLength()-1;i++){
    		if(snake1.head().getX()==this.obstacle.get(i).getX() && snake1.head().getY()==this.obstacle.get(i).getY()){
    			return true;
    		}

    	}
    	return false;
    }

}

class SnakeGame extends Timer implements ActionListener{
	int height;
	int width;
	Direction direction;
	Snake snake;
	Snake snake2;
	String userName;
	Apple apple;
	Random random;
	Food food;
	Updatable update;
	boolean on;
	int score;
	int j;
	File f;
	File f2;
	File f3;
	File f4;
	File f5;
	File f6;
	int snakeCount;
	boolean legend;
	Obstacle obstacle;
	int scoreTrack;
	int count;
	int score2;
	boolean fx;
	boolean fx1;
	String l1;
	String l2;
	String l3;
	boolean toggle;
	public SnakeGame(int height,int width,String userName){
		super(1000,null);
		this.height=height;
		this.width=width;
		this.direction=Direction.DOWN;
		this.random=new Random();
		this.snake=new Snake(this.height/2+3,this.width/2,this.direction);
		this.snakeCount=1;
		this.snake2=null;
		this.apple=new Apple(random.nextInt(height),random.nextInt(width));
		this.on=true;
		this.food=null;
		this.score=0;
		this.j=0;
		this.toggle=false;
		this.score2=0;
		this.scoreTrack=0;
		this.count=0;
		this.fx=false;
		this.fx1=false;
		this.l1="";
		this.l2="";
		this.l3="";
		this.legend=false;

		this.userName=userName;
		obstacle=new Obstacle(this.height/2,this.width/2);
		this.f=new File("scores.txt");
		this.f2=new File("scores2.txt");
		this.f3=new File("scores3.txt");
		this.f4=new File("usersc1.txt");
		this.f5=new File("usersc2.txt");
		this.f6=new File("usersc3.txt");
		
		addActionListener(this);
		setInitialDelay(2000);
	}
	public Snake getSnake(){
		return this.snake;
	}
	public String getUserName(){
		return this.userName;
	}
	public void rainbow(){
		if(!this.toggle){
			this.toggle=true;
		}
		else if(this.toggle){
			this.toggle=false;
		}
	}
	public boolean isValid(){
		return this.toggle;
	}
	public void createSnake2(){
		this.snake2=new Snake(this.height/3,this.width/2,this.direction);
		this.snakeCount=2;
	}
	public Snake getSnake2(){
		return this.snake2;
	}
	public void setSnake(Snake snake){
		this.snake=snake;
	}
	public void setUpdatable(Updatable updatable) {
        this.update = updatable;
    }
    public boolean status(){
    	return this.on;
    }
    public void setStatus(){
    	this.on=true;
    }
	public Apple getApple(){
		return this.apple;
	}
	public void setApple(Apple apple){
		this.apple=apple;
	}
	public void setFood(Food food){
		this.food=food;
	}
	public Food getFood(){
		return this.food;
	}
	public File getFile(){
		return this.f;
	}
	public File getFile2(){
		return this.f2;
	}

	public File getFile3(){
		return this.f3;
	}
	public File getFile4(){
		return this.f4;
	}
	public File getFile5(){
		return this.f5;
	}
	public File getFile6(){
		return this.f6;
	}
	public boolean legendStatus(){
		if(getUserName().equals(this.l1)){
			this.legend=true;
		}
		else if(getUserName().equals(this.l2)){
			this.legend=true;
		}
		else if(getUserName().equals(this.l3)){
			this.legend=true;
		}
		return this.legend;
	}

	
	public int getScoreTrack(){
		return this.scoreTrack;
	}
	public void setScoreTracker(int score){
		this.scoreTrack=score;
	}
	public int getSnakeCount(){
		return this.snakeCount;
	}
	public void resetSnakeCount(){
		this.snakeCount=1;
	}
	public void createVObstacle(){
		
			this.obstacle.addVObstacle(this.snake.head().getX(),this.snake.head().getY(),this.width,this.height);
		
		

	}
	public void createHObstacle(){
		
			this.obstacle.addHObstacle(this.snake.head().getX(),this.snake.head().getY(),this.width,this.height);
		

	}
	public Obstacle getObstacle(){
		return this.obstacle;
	}
	public void setObstacle(){
		
		while(this.obstacle.obstacleLength()!=0){
			this.obstacle.removeObstacle();
		}
	}


	@Override
	public void actionPerformed(ActionEvent ae) {

		if(this.snake.getCurrentState()==C_STATE.GAME){
			if(getSnakeCount()==1){	
				if(!on){

					return;
				}
				this.snake.move();
				while(this.snake.runsInto(this.apple)){
					this.snake.grow();
					this.score+=25;
					this.apple=new Apple(random.nextInt(this.height),random.nextInt(this.width));
				}
				if(this.obstacle.runsInto(this.apple)){
					this.apple=new Apple(random.nextInt(this.height),random.nextInt(this.width));
				}
				if(this.score==5||this.score%10==0){
					this.j++;
					this.food=new Food(random.nextInt(this.height),random.nextInt(this.width));


				}
				if(this.snake.runsInto(this.food)){
					this.score+=75;
					this.food=new Food((this.height+this.width),(this.width+this.height));
				}
				//if(this.j>0){
					
					//	try{
					//		Thread.sleep(1000);
					//	}
					//	catch(Exception e){
					//	}
					//	finally{
					//	this.j++;
						
					//}
					//this.j=0;
					//this.food=new Food(this.height+this.width,this.height+this.width);
				
				if(this.snake.runsIntoItself()){
					this.on=false;
				}
				if(this.obstacle.runsIntoItself(this.snake)){
					this.on=false;
				}
				else if(this.snake.head().getX()==this.width||snake.head().getX()<0){
					this.on=false;
				}
				else if(this.snake.head().getY()==this.height||snake.head().getY()<0){
					this.on=false;
				}
				update.update();
				setDelay(1000 / snake.snakeLength());
				}
				if(getSnakeCount()==2){
					if(!on){

					return;
				}
				this.snake.move();
				this.snake2.move();
			
				while(this.snake.runsInto(this.apple)){
					this.snake.grow();
					this.score+=25;
					this.apple=new Apple(random.nextInt(this.height),random.nextInt(this.width));
				}
				while(this.snake2.runsInto(this.apple)){
					this.snake2.grow();
					this.score+=25;
					this.apple=new Apple(random.nextInt(this.height),random.nextInt(this.width));
				}
				if(this.score==5||this.score%10==0){
					this.j++;
					this.food=new Food(random.nextInt(this.height),random.nextInt(this.width));


				}
				if(this.snake.runsInto(this.food)){
					this.score+=75;
					this.food=new Food((this.height+this.width),(this.width+this.height));
				}
				if(this.snake2.runsInto(this.food)){
					this.score+=75;
					this.food=new Food((this.height+this.width),(this.width+this.height));
				}
			
			
				//if(this.j>0){
					
					//	try{
					//		Thread.sleep(1000);
					//	}
					//	catch(Exception e){
					//	}
					//	finally{
					//	this.j++;
						
					//}
					//this.j=0;
					//this.food=new Food(this.height+this.width,this.height+this.width);
				
				if(this.snake.runsIntoItself()){
					this.on=false;
				}
				if(this.snake2.runsIntoItself()){
					this.on=false;
				}
				if(this.snake.head().getX()==this.width||this.snake.head().getX()<0){
					this.on=false;
				}
				else if(this.snake2.head().getX()==this.width||this.snake2.head().getX()<0){
					this.on=false;
				}
				else if(this.snake.head().getY()==this.height||snake.head().getY()<0){
					this.on=false;
				}
				else if(this.snake2.head().getY()==this.height||snake2.head().getY()<0){
					this.on=false;
				}
				
				if(this.snake.runsIntoItself(this.snake2)){
					this.on=false;
				}
				else if(this.snake2.runsIntoItself(this.snake)){
					this.on=false;
				}
				
				
				
				update.update();
				if(snake.snakeLength()>snake2.snakeLength()){
					setDelay(1000 / (snake.snakeLength()));
				}
				else{
					setDelay(1000/snake2.snakeLength());
				}

				}
			}
			if(this.snake.getCurrentState()==C_STATE.PVP){
				if(getSnakeCount()<2){
					createSnake2();
					this.snake2=getSnake2();
				}
					if(!on){

						return;
					}
					this.snake.move();
					this.snake2.move();
				
					while(this.snake.runsInto(this.apple)){
						this.snake.grow();
						this.score+=25;
						this.apple=new Apple(random.nextInt(this.height),random.nextInt(this.width));
					}
					while(this.snake2.runsInto(this.apple)){
						this.snake2.grow();
						this.score2+=25;
						this.apple=new Apple(random.nextInt(this.height),random.nextInt(this.width));
					}
					if(this.score==5||this.score%10==0){
						this.j++;
						this.food=new Food(random.nextInt(this.height),random.nextInt(this.width));


					}
					if(this.snake.runsInto(this.food)){
						this.score+=75;
						this.food=new Food((this.height+this.width),(this.width+this.height));
					}
					if(this.snake2.runsInto(this.food)){
						this.score2+=75;
						this.food=new Food((this.height+this.width),(this.width+this.height));
					}
				
				
					
					
					
					if(this.snake.head().getX()==this.width||this.snake.head().getX()<0){
						this.snake.changedDirection(this.snake.getDirection());
						this.score-=25;
						update.update();
					}
					else if(this.snake2.head().getX()==this.width||this.snake2.head().getX()<0){
						this.snake2.changedDirection(this.snake2.getDirection());
						this.score2-=25;
						update.update();
					}
					else if(this.snake.head().getY()==this.height||snake.head().getY()<0){
						this.snake.changedDirection(this.snake.getDirection());
						this.score-=25;
						update.update();
					}
					else if(this.snake2.head().getY()==this.height||snake2.head().getY()<0){
						this.snake2.changedDirection(this.snake2.getDirection());
							this.score2-=25;
							update.update();					}
					else if(this.snake.head().getY()>this.height||snake.head().getY()<0){
						this.on=false;
					}
					else if(this.snake2.head().getY()>this.height||snake2.head().getY()<0){
						this.on=false;
					}
					else if(this.snake2.head().getX()==this.width||this.snake2.head().getX()<0){
						this.on=false;
					}
					else if(this.snake.head().getY()==this.height||snake.head().getY()<0){
						this.on=false;
					}
					if(this.snake.runsIntoItself(this.snake2)){
							this.snake2.changedDirection(this.snake2.getDirection());
							this.snake2.growSmaller();
							this.snake.grow();
							this.fx=true;
							this.score2-=50;

							update.update();
					}

					else if(this.snake2.runsIntoItself(this.snake)){
							this.snake.changedDirection(this.snake.getDirection());
							this.snake.growSmaller();
							this.snake2.grow();
							this.score-=50;
							this.fx1=true;
							
							update.update();
					}
					if(this.score<0||this.score2<0){
						this.on=false;
					}

					
					
					
					update.update();
					if(snake.snakeLength()>snake2.snakeLength()){
						setDelay(1000 / (snake.snakeLength()));
					}
					else{
						setDelay(1000/snake2.snakeLength());
					}

				}
			

		}
		public int getWidth(){
			return this.width;
		}
		public boolean getFx(){
			return this.fx;

		}
		public boolean getFx2(){
			return this.fx1;
		}
		public void setFxOff(){
			this.fx1=false;
			this.fx=false;
		}
		public int getHeight(){
			return this.height;
		}
		public void setWidth(int wid){
			this.width=wid;

		}
		public void setHeight(int heigh){
			this.height=heigh;
		}
		public String getScore(){
			return "Score:"+Integer.toString(this.score);
		}
		public int getSc(){
			return this.score;
		}
		public String getScore1(){
			return "Player1 : "+Integer.toString(this.score);
		}
		public void setScore(){
			this.score=200;
		}
		public void setScore2(){
			this.score2=200;
		}
		public int getSc2(){
			return this.score2;
		}
		public void resetScore(){
			this.score=0;
		}
		public int getHighScore(){
			Scanner reader=null;
			int line=0;
			try {
	            reader = new Scanner(f);
	        } catch (Exception e) {
	            System.out.println("Couldn't read file : Error: " + e.getMessage());
	            return 0; 
	        }
			while(reader.hasNextLine()){
				line=Integer.parseInt(reader.nextLine());
			}
			return line;

		}
		public String getUserScore(int n){
			Scanner reader=null;
			String line="";
			int i=0;
			if(n==1){
				try {
				this.l1="";
	            reader = new Scanner(f4);
	            
	        	} catch (Exception e) {
	            System.out.println("Couldn't read file : Error: " + e.getMessage());
	             
	        	}
	        }

	        else if(n==2){
	        	try {
	        		this.l2="";
	            	reader = new Scanner(f5);
	        	}catch (Exception e) {
	            		System.out.println("Couldn't read file : Error: " + e.getMessage());
	            		
	        	}
	        }
	        	else if(n==3){
	        		try {
	        			this.l3="";
	           			 reader = new Scanner(f6);
	        		} catch (Exception e) {
	            		System.out.println("Couldn't read file : Error: " + e.getMessage());
	            		 
	        		}
	        	}
	        	while(reader.hasNextLine()){
	        		line=reader.nextLine();
	        	}
	        	while(line.charAt(i)!=':'){
	        		if(n==1){
	        			this.l1+=line.charAt(i);
	        			
	        		}
	        		else if(n==2){
	        			this.l2+=line.charAt(i);
	        		}
	        		else if(n==3){
	        			this.l3+=line.charAt(i);
	        		}
	        		i++;

	        		
	        	}

	        	return line;

	        }
	

	public int getHighScore2(){
		Scanner reader=null;
		int line=0;
		try {
            reader = new Scanner(f2);
        } catch (Exception e) {
            System.out.println("We couldn't read the file. Error: " + e.getMessage());
            return 0; // we exit the method
        }
		while(reader.hasNextLine()){
			line=Integer.parseInt(reader.nextLine());
		}
		return line;

	}
	public int getHighScore3(){
		Scanner reader=null;
		int line=0;
		try {
            reader = new Scanner(f3);
        } catch (Exception e) {
            System.out.println("We couldn't read the file. Error: " + e.getMessage());
            return 0; // we exit the method
        }
		while(reader.hasNextLine()){
			line=Integer.parseInt(reader.nextLine());
		}
		return line;

	}
	public int getCount(){
		return this.count;

	}
	public void setCount(int i){
		this.count=i;
	}
	public String getScore2(){
		return "Player2 : "+Integer.toString(this.score2);
	}


}
class CursorListener implements MouseListener{
	SnakeGame sg;
	JFrame frame;
	public CursorListener(SnakeGame sg,JFrame frame){
		this.sg=sg;
		this.frame=frame;
	}
	@Override 
	public void mousePressed(MouseEvent me){
		if(sg.getSnake().getCurrentState()==C_STATE.MENU){
			int x=me.getX();
			int y=me.getY();
			if(x>=this.sg.getHeight()*1 && x<=sg.getHeight()*1+100){
				if(y>=this.sg.getWidth()*8 && y<=this.sg.getWidth()*8+50){
					this.sg.getSnake().setC();
					this.sg.resetSnakeCount();
				}
			}
			if(x>=this.sg.getHeight()*14 && x<=sg.getHeight()*14+100){
				if(y>=this.sg.getWidth()*8 && y<=this.sg.getWidth()*8+50){
					this.sg.getSnake().setPvP();
					
				}
			}
			if(x>=this.sg.getHeight()*8 && x<=this.sg.getHeight()*8+100){
				if(y>=this.sg.getWidth()*16 && y<=this.sg.getWidth()*16+50){
					this.frame.dispose();
				}
			}
			if(x>=this.sg.getHeight()*8 && x<=this.sg.getHeight()*8+100){
				if(y>=this.sg.getWidth()*12 && y<=this.sg.getWidth()*12+50){
					this.sg.getSnake().setCScore();
					sg.update.update();
					

				}
			}	
		}


	}
	@Override
	public void mouseReleased(MouseEvent me){

	}
	@Override
	public void  mouseExited(MouseEvent me){

	}
	@Override
	public void mouseEntered(MouseEvent me){

	}
	@Override
	public void mouseClicked(MouseEvent me){

	}
}
class KeyBListener implements KeyListener{
	Snake snake;
	Snake snake2;
	SnakeGame sg;

	public KeyBListener(Snake snake,SnakeGame sg,Snake snake2){
		this.snake = snake;
		this.sg=sg;
		this.snake2=snake2;

	}
	
	@Override
	public void keyPressed(KeyEvent ke){
		
			
			if(ke.getKeyCode()==KeyEvent.VK_ESCAPE){
				this.snake=new Snake(sg.getHeight()/2,sg.getWidth()/2,Direction.DOWN);
				this.snake.setCOff();
				sg.setSnake(this.snake);
				sg.resetScore();
				sg.setStatus();
				sg.update.update();
				
			}
			if(this.snake.getCurrentState()==C_STATE.PVP){
					if(ke.getKeyCode()==KeyEvent.VK_SPACE){
						sg.resetSnakeCount();
						this.snake=new Snake(sg.getHeight()/2,sg.getWidth()/2,Direction.DOWN);
						this.snake.setPvP();
						sg.setSnake(this.snake);
						sg.resetScore();
						sg.setStatus();
						sg.setCount(0);

					}
					if(ke.getKeyCode()==KeyEvent.VK_UP){
							this.snake.setDirection(Direction.UP);
					}
					 if(ke.getKeyCode()==KeyEvent.VK_DOWN){
						this.snake.setDirection(Direction.DOWN);
					}
					 if(ke.getKeyCode()==KeyEvent.VK_LEFT){
						this.snake.setDirection(Direction.LEFT);
					}
					if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
						this.snake.setDirection(Direction.RIGHT);
					}
					if(ke.getKeyCode()==KeyEvent.VK_W){

							this.sg.getSnake2().setDirection(Direction.UP);
					}
					 if(ke.getKeyCode()==KeyEvent.VK_S){
							this.sg.getSnake2().setDirection(Direction.DOWN);
					}
					 if(ke.getKeyCode()==KeyEvent.VK_A){
							this.sg.getSnake2().setDirection(Direction.LEFT);
					}
					 if(ke.getKeyCode()==KeyEvent.VK_D){
							this.sg.getSnake2().setDirection(Direction.RIGHT);
					}

			}
			
			else if(this.sg.getSnakeCount()==2){

				if(this.snake.getCurrentState()==C_STATE.GAME){
					if(ke.getKeyCode()==KeyEvent.VK_SPACE){
							this.snake=new Snake(sg.getHeight()/2,sg.getWidth()/2,Direction.DOWN);
							this.snake.setC();
							sg.resetSnakeCount();
							sg.setSnake(this.snake);
							sg.resetScore();
							sg.setStatus();



					}

					if(ke.getKeyCode()==KeyEvent.VK_UP  && sg.getSnake().getDirection()!=Direction.DOWN && sg.getSnake().getMoving()){
							sg.getSnake().setDirection(Direction.UP);
							sg.getSnake().resetMoving();
					}
					 if(ke.getKeyCode()==KeyEvent.VK_DOWN  && sg.getSnake().getDirection()!=Direction.UP && sg.getSnake().getMoving()){
						sg.getSnake().setDirection(Direction.DOWN);
						sg.getSnake().resetMoving();					
					}

					 if(ke.getKeyCode()==KeyEvent.VK_LEFT  && sg.getSnake().getDirection()!=Direction.RIGHT && sg.getSnake().getMoving()){
						sg.getSnake().setDirection(Direction.LEFT);
						sg.getSnake().resetMoving();
					}
					if(ke.getKeyCode()==KeyEvent.VK_RIGHT  && sg.getSnake().getDirection()!=Direction.LEFT && sg.getSnake().getMoving()){
						sg.getSnake().setDirection(Direction.RIGHT);
						sg.getSnake().resetMoving();
					}
					if(ke.getKeyCode()==KeyEvent.VK_W  && sg.getSnake2().getDirection()!=Direction.DOWN && sg.getSnake2().getMoving()){

							sg.getSnake2().setDirection(Direction.UP);
							sg.getSnake2().resetMoving();
						}
					 if(ke.getKeyCode()==KeyEvent.VK_S && sg.getSnake2().getDirection()!=Direction.UP && sg.getSnake2().getMoving()){
							sg.getSnake2().setDirection(Direction.DOWN);
							sg.getSnake2().resetMoving();						
						}
					 if(ke.getKeyCode()==KeyEvent.VK_A && sg.getSnake2().getDirection()!=Direction.RIGHT && sg.getSnake2().getMoving()){
							sg.getSnake2().setDirection(Direction.LEFT);
							sg.getSnake2().resetMoving();
						}
					 if(ke.getKeyCode()==KeyEvent.VK_D && sg.getSnake2().getDirection()!=Direction.LEFT && sg.getSnake2().getMoving()){
							sg.getSnake2().setDirection(Direction.RIGHT);
							sg.getSnake2().resetMoving();
						}
					}

			}
			if(this.sg.getSnakeCount()==1){
				//System.out.println(""+sg.getSnake().getMoving());

					if(this.snake.getCurrentState()==C_STATE.GAME){
						if(ke.getKeyCode()==KeyEvent.VK_SPACE){
							this.snake=new Snake(sg.getHeight()/2,sg.getWidth()/2,Direction.DOWN);
							this.snake.setC();
							this.snake.resetMoving();
							sg.setObstacle();
							sg.setSnake(this.snake);
							sg.resetScore();
							sg.setStatus();
							



					}
					if(ke.getKeyCode()==KeyEvent.VK_N){
						sg.createSnake2();
						this.snake2=sg.getSnake2();
						this.snake2.setC();
						sg.setStatus();
						sg.update.update();

					}
					if(sg.legendStatus()){
						if(ke.getKeyCode()==KeyEvent.VK_M){
							sg.rainbow();
						}
					}
					if(ke.getKeyCode()==KeyEvent.VK_UP && sg.getSnake().getDirection()!=Direction.DOWN && sg.getSnake().getMoving()){
						sg.getSnake().setDirection(Direction.UP);
						sg.getSnake().resetMoving();
					}
					else if(ke.getKeyCode()==KeyEvent.VK_DOWN && sg.getSnake().getDirection()!=Direction.UP && sg.getSnake().getMoving()){
						sg.getSnake().setDirection(Direction.DOWN);
						sg.getSnake().resetMoving();
					}

					else if(ke.getKeyCode()==KeyEvent.VK_LEFT && sg.getSnake().getDirection()!=Direction.RIGHT&& sg.getSnake().getMoving()){
						sg.getSnake().setDirection(Direction.LEFT);
						sg.getSnake().resetMoving();
					}
					else if(ke.getKeyCode()==KeyEvent.VK_RIGHT && sg.getSnake().getDirection()!=Direction.LEFT && sg.getSnake().getMoving()){
						sg.getSnake().setDirection(Direction.RIGHT);
						sg.getSnake().resetMoving();
					}
				}
			}
	}
	
	@Override
	public void keyReleased(KeyEvent ke){

	}
	@Override
	public void keyTyped(KeyEvent ke){

	}

}
class Draw extends JPanel implements Updatable{
	SnakeGame sg;
	int dimension;
	Random random;
	int check;
	int c;
	Obstacle obstacle;
	
	int i;
	public Draw(SnakeGame sg,int dimension){
		this.sg=sg;
		this.random=new Random();
		this.dimension=dimension;
		this.check=0;
		this.i=0;
		this.c=0;
		
		
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.gray);

		if(sg.getSnake().getCurrentState()==C_STATE.PVP){
			
			if(sg.getCount()==0){
				sg.setScore();
				sg.setScore2();
				sg.createSnake2();
				sg.getSnake2().setPvP();
				sg.update.update();
				sg.setCount(1);
			}
			if(sg.getCount()>0){

				
				for(Piece piece:this.sg.getSnake().getPieces()){
					g.setColor(Color.BLACK);
					g.fill3DRect(this.dimension*piece.getX(),this.dimension*piece.getY(),this.dimension,this.dimension,true);
				}
				for(Piece piece:this.sg.getSnake2().getPieces()){
					g.setColor(Color.WHITE);
					g.fill3DRect(this.dimension*piece.getX(),this.dimension*piece.getY(),this.dimension,this.dimension,true);
				}

			}
				if(sg.getSc()>sg.getSc2()){
					setBackground(Color.darkGray);
				}
				else if(sg.getSc2()>sg.getSc()){
					setBackground(Color.lightGray);
				}
				else if(sg.getSc()==sg.getSc2()){
					setBackground(Color.gray);
				}
				g.setColor(Color.RED);
				g.fillOval(this.dimension*this.sg.getApple().getX(),this.dimension*this.sg.getApple().getY(),this.dimension,this.dimension);
				g.setColor(Color.YELLOW);
				g.drawString(sg.getScore1(),sg.getHeight(),sg.getWidth());
				g.setColor(Color.BLUE);
				g.drawString(sg.getScore2(),sg.getHeight()*14,sg.getWidth());

			if(!sg.status()){
					if(this.sg.getSnake().head().getX()==this.sg.getWidth()||this.sg.getSnake().head().getX()<0){
						g.setColor(Color.WHITE);
						g.setFont(new Font("Helvetica", Font.BOLD+Font.ITALIC, 18));
						g.drawString("Player 2 Wins",sg.getHeight()*7,sg.getWidth()*4);
						g.drawString("Press Space to restart!",sg.getHeight()*5,sg.getWidth()*8);
						g.drawString("Press Esc to return to title screen",sg.getHeight()*2,sg.getWidth()*12);
					}
					else if(this.sg.getSnake().head().getY()==this.sg.getHeight()||this.sg.getSnake().head().getY()<0){
						g.setColor(Color.WHITE);
						g.setFont(new Font("Helvetica", Font.BOLD+Font.ITALIC, 18));
						g.drawString("Player 2 Wins",sg.getHeight()*7,sg.getWidth()*4);
						g.drawString("Press Space to restart!",sg.getHeight()*5,sg.getWidth()*8);
						g.drawString("Press Esc to return to title screen",sg.getHeight()*2,sg.getWidth()*12);
					}
					else if(this.sg.getSnake2().head().getX()==this.sg.getWidth()||this.sg.getSnake2().head().getX()<0){
						g.setColor(Color.BLACK);
						g.setFont(new Font("Helvetica", Font.BOLD+Font.ITALIC, 18));
						g.drawString("Player 1 Wins",sg.getHeight()*7,sg.getWidth()*4);
						g.drawString("Press Space to restart!",sg.getHeight()*5,sg.getWidth()*8);
						g.drawString("Press Esc to return to title screen",sg.getHeight()*2,sg.getWidth()*12);
						
					}
					else if(this.sg.getSnake2().head().getY()==this.sg.getHeight()||this.sg.getSnake2().head().getY()<0){
						g.setColor(Color.BLACK);
						g.setFont(new Font("Helvetica", Font.BOLD+Font.ITALIC, 18));
						g.drawString("Player 1 Wins",sg.getHeight()*7,sg.getWidth()*4);
						g.drawString("Press Space to restart!",sg.getHeight()*5,sg.getWidth()*8);
						g.drawString("Press Esc to return to title screen",sg.getHeight()*2,sg.getWidth()*12);
					}
					else if(this.sg.getSc()>this.sg.getSc2()){
						g.setColor(Color.BLACK);
						g.setFont(new Font("Helvetica", Font.BOLD+Font.ITALIC, 18));
						g.drawString("Player 1 Wins",sg.getHeight()*7,sg.getWidth()*4);	
						g.drawString("Press Space to restart!",sg.getHeight()*5,sg.getWidth()*8);
						g.drawString("Press Esc to return to title screen",sg.getHeight()*2,sg.getWidth()*12);
					}
					else if(this.sg.getSc2()>this.sg.getSc()){
						g.setColor(Color.WHITE);
						g.setFont(new Font("Helvetica", Font.BOLD+Font.ITALIC, 18));
						g.drawString("Player 2 Wins",sg.getHeight()*7,sg.getWidth()*4);
						g.drawString("Press Space to restart!",sg.getHeight()*5,sg.getWidth()*8);
						g.drawString("Press Esc to return to title screen",sg.getHeight()*2,sg.getWidth()*12);	
					}
					else if(this.sg.getSc2()==this.sg.getSc() && this.sg.getSc()!=200){
						g.setColor(Color.CYAN);
						g.setFont(new Font("Helvetica", Font.BOLD+Font.ITALIC, 18));
						g.drawString("Draw , Go at it againt!!",sg.getHeight()*6,sg.getWidth()*4);
						g.drawString("Press Space to restart!",sg.getHeight()*5,sg.getWidth()*8);
						g.drawString("Press Esc to return to title screen",sg.getHeight()*2,sg.getWidth()*12);		
					}

					

			}
			


		}
		if(sg.getSnake().getCurrentState()==C_STATE.GAME){	
			
			if(sg.getSnakeCount()==1 && sg.getSc()<=100){
				for(Piece piece:this.sg.getSnake().getPieces()){
					if(sg.isValid()){
						int a=random.nextInt(4);
						if(a==0){
							g.setColor(Color.WHITE);
						}
						else if(a==1){
							g.setColor(Color.BLUE);
						}
						else if(a==2){
							g.setColor(Color.GREEN);
						}
						else if(a==3){
							g.setColor(Color.YELLOW);
						}
					}
						else{
							g.setColor(Color.BLACK);
						}


						g.fill3DRect(this.dimension*piece.getX(),this.dimension*piece.getY(),this.dimension,this.dimension,true);
					
				}
			}
			else if(sg.getSnakeCount()==1 && sg.getSc()>100){
				
				for(Piece piece:this.sg.getSnake().getPieces()){
					if(sg.isValid()){
						int a=random.nextInt(4);
						if(a==0){
							g.setColor(Color.WHITE);
						}
						else if(a==1){
							g.setColor(Color.BLUE);
						}
						else if(a==2){
							g.setColor(Color.GREEN);
						}
						else if(a==3){
							g.setColor(Color.YELLOW);
						}
					}
						else{
							g.setColor(Color.BLACK);
						}
					


					g.fill3DRect(this.dimension*piece.getX(),this.dimension*piece.getY(),this.dimension,this.dimension,true);
					
				}
				
				
				g.setColor(Color.CYAN);
				if(this.check>sg.getSc()){
					this.check=0;
					this.c=0;
				}
				if(this.check+100<=sg.getSc() && this.c==0 && sg.status()){
					
					sg.createVObstacle();
					
					this.c++;
				}
				if(this.check+200<=sg.getSc() && sg.status()){
					sg.createHObstacle();
					this.check=sg.getSc();
					this.c=0;
				}
				if(sg.getObstacle().obstacleLength()>7){
					sg.getObstacle().removeObstacle();

				}
				this.obstacle=sg.getObstacle();
				for(Piece piece:this.obstacle.getPieces()){
					g.fill3DRect(this.dimension*piece.getX(),this.dimension*piece.getY(),this.dimension,this.dimension,true);


				}


			}
			else if(sg.getSnakeCount()==2 && sg.getSnake().getCurrentState()==C_STATE.GAME){
				for(Piece piece:this.sg.getSnake().getPieces()){
					g.setColor(Color.BLACK);
					g.fill3DRect(this.dimension*piece.getX(),this.dimension*piece.getY(),this.dimension,this.dimension,true);
				}
				for(Piece piece:this.sg.getSnake2().getPieces()){
					g.setColor(Color.WHITE);
					g.fill3DRect(this.dimension*piece.getX(),this.dimension*piece.getY(),this.dimension,this.dimension,true);
				}
			}

			//if(sg.getSc()<=250 || sg.getSnakeCount()!=1){

				g.setColor(Color.RED);
				g.fillOval(this.dimension*this.sg.getApple().getX(),this.dimension*this.sg.getApple().getY(),this.dimension,this.dimension);
				g.drawString(sg.getScore(),sg.getHeight(),sg.getWidth());
			//}
			
			if(this.sg.getSc()==5||this.sg.getSc()%10==0 && this.sg.getSc()!=0){
				int b=random.nextInt(4);
				if(b==0){
					g.setColor(Color.WHITE);
				}
				else if(b==1){
					g.setColor(Color.BLUE);
				}
				else if(b==2){
					g.setColor(Color.GREEN);
				}
				else if(b==3){
					g.setColor(Color.YELLOW);
				}
				g.fillOval(this.dimension*this.sg.getFood().getX(),this.dimension*this.sg.getFood().getY(),this.dimension,this.dimension);
			}
			if(!sg.status()){
				g.setColor(Color.WHITE);
				g.setFont(new Font("Helvetica", Font.BOLD+Font.ITALIC, 18));
				g.drawString("Game Over",sg.getHeight()*7,sg.getWidth()*4);
				g.setColor(Color.GREEN);
				g.drawString(this.sg.getScore(),sg.getHeight()*7,sg.getWidth()*6);
				g.setColor(Color.WHITE);
				g.drawString("Press Space to restart",sg.getHeight()*5,sg.getWidth()*8);
				g.drawString("Press 'N' to add 2nd player",sg.getHeight()*4,sg.getWidth()*10);
				g.drawString("Press Esc to return to title screen",sg.getHeight()*2,sg.getWidth()*12);
				if(this.sg.getSc()>this.sg.getHighScore()){
					
					PrintWriter writer=null;
					PrintWriter writer9=null;
					int temp1=sg.getHighScore();
					String t2=sg.getUserScore(1);
					try{
						writer=new PrintWriter(this.sg.getFile());
						}
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());
					}	
						
						writer.print("");
						writer.print(""+this.sg.getSc());
						writer.close();
						
					try{
						writer9=new PrintWriter(this.sg.getFile4());
					}
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());
					}
						writer9.print("");
						writer9.print(""+this.sg.getUserName()+":"+this.sg.getSc());
						writer9.close();

					PrintWriter writer1=null;
					PrintWriter writer11=null;
					 int temp2=sg.getHighScore2();
					 String t3=sg.getUserScore(2);
					
						try{
						writer1=new PrintWriter(this.sg.getFile2());
						}
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());
					}	
						writer1.print("");
						writer1.print(""+temp1);
						writer1.close();
						try{
							writer11=new PrintWriter(this.sg.getFile5());
						}
						catch(Exception e){
							System.out.println("Error!"+e.getMessage());
						}
						writer11.print("");
						writer11.print(""+t2);
						writer11.close();

						PrintWriter writer2=null;
						PrintWriter writer22=null;
						
					try{
						writer2=new PrintWriter(this.sg.getFile3());
						}
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());
					}	
						writer2.print("");
						writer2.print(""+temp2);
						
					try{
						writer22=new PrintWriter(this.sg.getFile6());
					}	
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());
					}
						writer22.print("");
						writer22.print(""+t3);
						writer22.close();

				
				}

				else if(this.sg.getSc()>this.sg.getHighScore2()){
					PrintWriter writer1=null;
					PrintWriter writer11=null;
					int temp2=sg.getHighScore2();
					String t2=sg.getUserScore(2);
						try{
						writer1=new PrintWriter(this.sg.getFile2());
						}
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());
					}	
						writer1.print("");
						writer1.print(""+this.sg.getSc());
						writer1.close();
						try{
							writer11=new PrintWriter(this.sg.getFile5());
						}
						catch(Exception e){
							System.out.println("Error!"+e.getMessage());

						}
						writer11.print("");
						writer11.print(""+this.sg.getUserName()+":"+this.sg.getSc());
						writer11.close();
						PrintWriter writer2=null;
						PrintWriter writer22=null;
						
					try{
						writer2=new PrintWriter(this.sg.getFile3());
						}
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());
					}	
						writer2.print("");
						writer2.print(""+temp2);
						writer2.close();
					try{
						writer22=new PrintWriter(this.sg.getFile6());
					}
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());

					}
					writer22.print("");
					writer22.print(""+t2);
					writer22.close();
				

				}
				else if(this.sg.getSc()>this.sg.getHighScore3()){
					PrintWriter writer=null;
					PrintWriter writer9=null;
					try{
						writer=new PrintWriter(this.sg.getFile3());
						}
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());
					}	
						writer.print("");
						writer.print(""+this.sg.getSc());
						writer.close();
					try{
						writer9=new PrintWriter(this.sg.getFile6());
					}
					catch(Exception e){
						System.out.println("Error!"+e.getMessage());
					}
					writer9.print("");
					writer9.print(""+this.sg.getUserName()+":"+this.sg.getSc());
					writer9.close();
				}


			}
		}
		else if(this.sg.getSnake().getCurrentState()==C_STATE.MENU){
			BufferedImage background=null;
			try{
				background=ImageIO.read(new File("snak.jpg"));
			}
			catch(IOException e){
				//e.printStackTraace();
			}
			//setBackground(Color.YELLOW);
			
			g.drawImage(background,0,0,null);
			g.setFont(new Font("Helvetica", Font.BOLD+Font.ITALIC, 18));
			g.setColor(Color.RED);
			//g.drawString("SNAKE GAME",sg.getHeight()*8,sg.getWidth()*2);
			Graphics2D g2d=(Graphics2D)g;
			Rectangle button=new Rectangle(sg.getHeight()*1,sg.getWidth()*8,100,50);
			Rectangle bbutton=new Rectangle(sg.getHeight()*14,sg.getWidth()*8,100,50);
			Rectangle button1=new Rectangle(sg.getHeight()*8,sg.getWidth()*12,100,50);
			Rectangle button2=new Rectangle(sg.getHeight()*8,sg.getWidth()*16,100,50);
			g.setFont(new Font("arial",Font.BOLD,30));
			g.drawString("Start!",button.x+17,button.y+30);
			g.drawString("PvP!",bbutton.x+17,bbutton.y+30);
			g.setFont(new Font("arial",Font.BOLD,15));
			g.drawString("High Scores",button1.x+10,button1.y+30);
			g.setFont(new Font("arial",Font.BOLD,30));
			g.drawString("Quit",button2.x+17,button2.y+30);
			g.setColor(Color.BLACK);
			g2d.draw(button);
			g2d.draw(button1);
			g2d.draw(button2);
			g2d.draw(bbutton);

			//this.sg.getSnake().setC();
		}
		else if(this.sg.getSnake().getCurrentState()==C_STATE.SCORE){
			BufferedImage background2=null;
			try{
				background2=ImageIO.read(new File("leg.jpg"));
			}
			catch(IOException e){
				//e.printStackTraace();
			}
			g.drawImage(background2,0,0,null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Serif",Font.PLAIN,30));
			g.drawString("Legendary Snakes",sg.getHeight()*5,sg.getWidth()*2);
			g.setFont(new Font("Serif",Font.PLAIN,25));
			g.setColor(Color.YELLOW);
			g.drawString("1) "+sg.getUserScore(1),sg.getHeight()*5,sg.getWidth()*7);
			g.setColor(Color.WHITE);
			g.drawString("2) "+sg.getUserScore(2),sg.getHeight()*5,sg.getWidth()*10);
			g.setColor(Color.CYAN);
			g.drawString("3) "+sg.getUserScore(3),sg.getHeight()*5,sg.getWidth()*13);
			g.setColor(Color.BLUE);
			g.setFont(new Font("Segoe Print",Font.PLAIN,11));
			g.drawString("If you're legend is here , press m in solo play to adorn your legend",sg.getHeight(),sg.getWidth()*15);
		}


	}
	@Override
    public void update() {
        super.repaint();
    }
}
class Exec2 {
	SnakeGame sg;
	String name;
	Exec2(String name) {
		 super();
		 this.name=name;
		 sg = new SnakeGame(20, 20,this.name);
 		 sg.setHeight(20);
		 sg.setWidth(20);
		
		
		new Exec();
	}
class UserInterface implements Runnable{
	Thread th;
	JFrame frame;
	Draw draw;
	int length;
	
	//JFrame	dupe;
	public UserInterface(int length){
		
		
		this.length=length;
		frame = new JFrame("Snake");
		this.th=new Thread(this);
		this.th.start();
	}
	@Override
	public void run(){
		
        int width = (sg.getWidth() + 1) * length + 10;
        int height = (sg.getHeight() + 2) * length + 10;
        //System.out.println(width);
        //System.out.println(height);

        frame.setPreferredSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        createComponents(frame.getContentPane());
        //dupe.setContentPane(frame.getContentPane());

        frame.pack();
        //dupe.setVisible(false);
        frame.setVisible(true);

    }
    public void createComponents(Container container){
    	draw=new Draw(sg,length);
    	container.add(draw);
    	Snake s=sg.getSnake();
    	//snakeListener sl=new snakeListener(frame,sg,s);
    	//sg.addActionListener(sl);
    	frame.addKeyListener(new KeyBListener(sg.getSnake(),sg,sg.getSnake2()));
    	frame.addMouseListener(new CursorListener(sg,frame));

    }
    public Updatable getUpdatable() {
        return draw;
    }



}

 class Exec{
 	Draw draw;
 	UserInterface ui;
 	public Exec(){

        UserInterface ui = new UserInterface( 20);
        SwingUtilities.invokeLater(ui);

        while (ui.getUpdatable() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("The drawing board hasn't been created yet.");
            }
        }
        sg.setUpdatable(ui.getUpdatable());
        sg.start();

 	}
 }
}

public class SnakeGameRun{
	public static void main(String[] args){
		String name =args[0];

		Exec2 c=new Exec2(name);
	}
}
		/* SnakeGame game = new SnakeGame(20, 20);

        UserInterface ui = new UserInterface(game, 20);
        SwingUtilities.invokeLater(ui);

        while (ui.getUpdatable() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("The drawing board hasn't been created yet.");
            }
        }
        game.setUpdatable(ui.getUpdatable());
        game.start();
    }
}
*/
