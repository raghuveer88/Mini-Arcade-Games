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
import java.io.File;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.PrintWriter; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

class Music{
	Clip clip;
	AudioInputStream audio;
	public Music(String filepath){
		try{
			File path=new File(filepath);
			if(path.exists()){
				 audio=AudioSystem.getAudioInputStream(path);
				 clip=AudioSystem.getClip();//daemon thread
				 clip.open(audio);
				 
				
				 
				
			}
			else{
				System.out.println("not found");
			}

		}
		catch(Exception e){
			e.printStackTrace();

		}


	}
	Clip playMusic(){
		clip.start();  
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		return clip;
	}
	
	void stopMusic(){
	try{
		if(clip!=null){
			clip.start();
			clip.stop();
			clip.close();
		}
				
	}
	catch(Exception e){

	}



	}
}
