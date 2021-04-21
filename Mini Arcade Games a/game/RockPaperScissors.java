package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


public class RockPaperScissors extends JFrame implements ActionListener{
    JLabel o1;
    JLabel o2;
    JLabel o3;
    JLabel o4;
    JLabel o5;
    JLabel o6;
    JLabel o7;
    JLabel o8;
    JLabel o9;
    JLabel o10;
    JLabel o11;
    JButton q10;
    JLabel b;
    JLabel b2;
    JLabel b3;
    JPanel p1;
    JButton q1;
    JButton q2;
    JButton q3;
    JButton q4;
    JButton q5;
    JButton q6;
    Container n;
    JPanel p;
    JLabel o14;
    JLabel o15;
    JLabel o16;
    JLabel o17;
    JButton q9;
    String z; 
    String w;
    int score1,score2;
    String s1 = "5";
    String s2 = "4";
    String s3 = "3";
    
    String name;

    TreeMap<String, String> highScoresArray;
    int lowestHS = 0;
    int highScore = 0;

RockPaperScissors(String text){
    name = text;
    
    Start();
}

public void Start(){
    highScoresArray = new TreeMap<String, String>(Collections.reverseOrder());

    this.getContentPane().removeAll();
    this.repaint();
     JPanel p = new javax.swing.JPanel();
    JLabel o13 = new javax.swing.JLabel();
    JButton q7 = new javax.swing.JButton();
    JButton q8 = new javax.swing.JButton();
    Container n = this.getContentPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        p.setBackground(java.awt.Color.black);

        p.setLayout(null);

        o13.setFont(new java.awt.Font("Segoe UI Black", 3, 36)); // NOI18N
        o13.setForeground(java.awt.Color.red);
        o13.setText("ROCK PAPER SCISSORS");
        p.add(o13);
        o13.setBounds(90, 120, 450, 60);

        q7.setBackground(java.awt.Color.red);
        q7.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        q7.setText("PLAY");
        q7.addActionListener(this);
        p.add(q7);
        q7.setBounds(90, 310, 160, 70);

        q8.setBackground(java.awt.Color.red);
        q8.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        q8.setText("HIGH SCORE");
        q8.addActionListener(this);
        p.add(q8);
        q8.setBounds(370, 310, 160, 70);
        n.add(p);
        this.setSize(615,545);
        this.setVisible(true);
}


public void HighScores(){
    this.getContentPane().removeAll();
    this.repaint();
    Container n = this.getContentPane();
        JPanel p = new javax.swing.JPanel();
       JLabel o14 = new javax.swing.JLabel();
       JLabel o15[] = new javax.swing.JLabel[3];
       for (int i = 0; i < 3;i++) {
       o15[i] = new javax.swing.JLabel();
   }
       JLabel o16 = new javax.swing.JLabel();
       JLabel o17 = new javax.swing.JLabel();
       JButton q9 = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        p.setBackground(java.awt.Color.black);
        p.setLayout(null);

        o14.setFont(new java.awt.Font("Arial Black", 3, 24)); // NOI18N
        o14.setForeground(java.awt.Color.red);
        o14.setText("HIGH SCORE");
        p.add(o14);
        o14.setBounds(50, 40, 320, 80);
        int i = 1;
        getHighScores();
        for (Map.Entry<String,String> event : highScoresArray.entrySet()) {
        o15[i-1].setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        o15[i-1].setForeground(java.awt.Color.red);
        o15[i-1].setText("  "+event.getValue()+"    "+event.getKey());
        p.add(o15[i-1]);
        o15[i-1].setBounds(40, 160 + i*40, 290, 30);
        i++;
    }



        q9.setBackground(java.awt.Color.black);
        q9.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        q9.setForeground(java.awt.Color.red);
        q9.setText("back");
        q9.addActionListener(this);
        p.add(q9);
        q9.setBounds(0, 0, 80, 29);

        n.add(p);
        this.setSize(615,545);
        this.setVisible(true);
}



    public void CreateFrame(String text){
        this.getContentPane().removeAll();
        this.repaint();
        Container n = this.getContentPane();
        p1 = new JPanel();
        o1 = new JLabel();
        q1 = new JButton();
        q2 = new JButton();
        q3 = new JButton();
        o2 = new JLabel();
        o3 = new JLabel();
        o4 = new JLabel();
        o5 = new JLabel();
        o6 = new JLabel();
        o7 = new JLabel();
        q4 = new JButton();
        o8 = new JLabel();
        b = new JLabel();
        b2 = new JLabel();
        b3 = new JLabel();
        z = "0";
        w = "0";

         name = text;
         p1.setLayout(null);

        
        

        o1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        o1.setText(text);
        o1.setForeground(Color.YELLOW);
        p1.add(o1);
        o1.setBounds(100, 10, 140, 40);

        q1.setBackground(java.awt.Color.YELLOW);
        q1.setFont(new java.awt.Font("Artifakt Element", 3, 12)); // NOI18N
        q1.setText("ROCK");
        q1.addActionListener(this);
        p1.add(q1);
        q1.setBounds(10, 80, 70, 20);

        q2.setBackground(java.awt.Color.YELLOW);
        q2.setFont(new java.awt.Font("Artifakt Element", 3, 12)); // NOI18N
        q2.setText("PAPER");
        q2.addActionListener(this);
        p1.add(q2);
        q2.setBounds(100, 80, 73, 20);

        q3.setBackground(java.awt.Color.YELLOW);
        q3.setFont(new java.awt.Font("Artifakt Element", 3, 12)); // NOI18N
        q3.setText("SCISSORS");
        q3.addActionListener(this);
        p1.add(q3);
        q3.setBounds(190, 80, 100, 21);

        

        o3.setBackground(java.awt.Color.black);
        o3.setForeground(Color.YELLOW);
        o3.setFont(new java.awt.Font("Candara", 2, 18)); // NOI18N
        o3.setText("WINS - "+ z);
        p1.add(o3);
        o3.setBounds(30, 363, 80, 40);

        b.setBackground(Color.BLACK);
        b.setOpaque(true);
        p1.add(b);
        b.setBounds(0, 0, 300, 430);

        
        

        o4.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        o4.setForeground(Color.YELLOW);
        o4.setText("COMPUTER ");
        p1.add(o4);
        o4.setBounds(380, 20, 150, 40);

        

        o6.setBackground(java.awt.Color.black);
        o6.setForeground(Color.YELLOW);
        o6.setFont(new java.awt.Font("Candara", 2, 18)); // NOI18N
        o6.setText("WINS - "+w);
        p1.add(o6);
        o6.setBounds(340, 363, 80, 40);

        b2.setBackground(Color.RED);
        b2.setOpaque(true);
        p1.add(b2);
        b2.setBounds(300, 0, 300, 430);

        
      

        q4.setBackground(java.awt.Color.red);
        q4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        q4.setText("END GAME");
        q4.addActionListener(this);
        p1.add(q4);
        q4.setBounds(500,480, 98, 23);

        b3.setBackground(Color.YELLOW);
        b3.setOpaque(true);
        p1.add(b3);
        b3.setBounds(0, 430, 600, 100);

        n.add(p1);
        this.setSize(615,545);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }


    public void actionPerformed(ActionEvent e){
        String r = e.getActionCommand();

        if(r == "ROCK"){
         try{   
             o2.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images/rock.png"))); // NOI18N
             o2.setText("jLabel3");
             p1.add(o2);
             o2.setBounds(70, 160, 150, 150);

              b.setBackground(Color.BLACK);
              b.setOpaque(true);
              p1.add(b);
              b.setBounds(0, 0, 300, 430);

              p1.repaint();
              p1.revalidate();
             gamePart(r,name);
             n.add(p1);
             
             

             }
         catch(Exception x){
        
              }
        }

        else if(r == "PAPER"){
         try{   
             o2.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images/paper.png"))); // NOI18N
             o2.setText("jLabel3");
             p1.add(o2);
             o2.setBounds(70, 160, 150, 150);

              b.setBackground(Color.BLACK);
              b.setOpaque(true);
              p1.add(b);
              b.setBounds(0, 0, 300, 430);

             p1.revalidate();
             p1.repaint();
             gamePart(r,name);
             n.add(p1);
             

             }
         catch(Exception x){
        
              }
        }

        else if(r == "SCISSORS"){
         try{   
             o2.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images/scissors.png"))); // NOI18N
             o2.setText("jLabel3");
             p1.add(o2);
             o2.setBounds(70, 160, 150, 150);
             
             b.setBackground(Color.BLACK);
             b.setOpaque(true);
             p1.add(b);
             b.setBounds(0, 0, 300, 430);

             p1.revalidate();
             p1.repaint();
             gamePart(r,name);
             n.add(p1);
             

             }
         catch(Exception x){
        
              }
        }

        else if(r == "END GAME"){
            
            this.congratulations(name);

        }

        else if(r == "PLAY AGAIN"){
            this.getContentPane().removeAll();
            this.repaint();
            this.CreateFrame(name);
        }

        else if(r == "EXIT"){
            dispose();
        }

        else if(r == "PLAY"){
            CreateFrame(name);
        }

        else if(r == "HIGH SCORE"){
            HighScores();
        }

        else if(r == "back"){
            Start();
        }
        else if(r == "HOME SCREEN"){
            Start();
        }
    }


    public void gamePart(String u,String name){
        Random rand = new Random();
        int c = rand.nextInt(3);
        String computer = ""; 
        int y;
        int s;



    
        


        if(c == 0){
            try{
                computer = "ROCK";
                o5.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images/rock.png"))); // NOI18N
                o5.setText("jLabel3");
                o5.setBounds(390, 160, 150, 150);
                p1.add(o5);

                b2.setBackground(Color.RED);
                b2.setOpaque(true);
                p1.add(b2);
                b2.setBounds(300, 0, 300, 430);
                p1.revalidate();
                p1.repaint();
                
                n.add(p1);
            }
            catch(Exception x){

            }            
        }

        else if(c == 1){
            try{
                computer = "PAPER";
                o5.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images/paper.png"))); // NOI18N
                o5.setText("jLabel3");
                o5.setBounds(390, 160, 150, 150);
                p1.add(o5);
                b2.setBackground(Color.RED);
                b2.setOpaque(true);
                p1.add(b2);
                b2.setBounds(300, 0, 300, 430);
                
                p1.revalidate();
                p1.repaint();
                
                n.add(p1);
            }
            catch(Exception x){

            }            
        }

        else if(c == 2){
            try{
                computer = "SCISSORS";
                o5.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images/scissors.png"))); // NOI18N
                o5.setText("jLabel3");
                o5.setBounds(390, 160, 150, 150);
                p1.add(o5);
                b2.setBackground(Color.RED);
                b2.setOpaque(true);
                p1.add(b2);
                b2.setBounds(300, 0, 300, 430);
                
                p1.revalidate();
                p1.repaint();
                
                n.add(p1);
            }
            catch(Exception x){

            }            
        }


        if(u == "ROCK" && computer == "SCISSORS" || u == "PAPER" && computer == "ROCK" || u == "SCISSORS" && computer == "PAPER" ){
                y = Integer.parseInt(z);
                y = y+1;
                 z = ""+y;

                
                 o3.setFont(new java.awt.Font("Candara", 2, 18)); // NOI18N
                 o3.setText("WINS - "+ z);
                 o3.setBounds(30, 363, 80, 40);
                 p1.add(o3);
                 
                
                 p1.revalidate();
                 p1.repaint();
                 
                 

                 o7.setFont(new java.awt.Font("Arial Black", 2, 18)); // NOI18N
                 o7.setForeground(java.awt.Color.red);
                 o7.setText(" " +name +" WIN");
                 o7.setBounds(240, 450, 180, 40);  
                 p1.add(o7);

                  b.setBackground(Color.BLACK);
                 b.setOpaque(true);
                 p1.add(b);
                 b.setBounds(0, 0, 300, 430);
                 

                 b3.setBackground(Color.YELLOW);
                 b3.setOpaque(true);
                 p1.add(b3);
                 b3.setBounds(0, 430, 600, 100);

                 p1.revalidate();
                 p1.repaint();
                 
                
                 n.add(p1);
        }

         else if(computer == "ROCK" && u == "SCISSORS" || computer == "PAPER" && u == "ROCK" || computer == "SCISSORS" && u == "PAPER" ){
                s = Integer.parseInt(w);
                s = s+1;
                 w = ""+s;

                 o6.setBackground(java.awt.Color.black);
                 o6.setFont(new java.awt.Font("Candara", 2, 18)); // NOI18N
                 o6.setText("WINS - "+w);
                 o6.setBounds(340, 363, 80, 40);
                 p1.add(o6);

                 

                 p1.revalidate();
                 p1.repaint();
                 
                

                 o7.setFont(new java.awt.Font("Arial Black", 2, 18)); // NOI18N
                 o7.setForeground(java.awt.Color.red);
                 o7.setText("COMPUTER WIN");
                 o7.setBounds(240, 450, 180, 40);  
                 p1.add(o7);

                 b2.setBackground(Color.RED);
                 b2.setOpaque(true);
                 p1.add(b2);
                 b2.setBounds(300, 0, 300, 430);

                 b3.setBackground(Color.YELLOW);
                 b3.setOpaque(true);
                 p1.add(b3);
                 b3.setBounds(0, 430, 600, 100);

                 p1.revalidate();
                 p1.repaint();
                 
                
                 n.add(p1);
        }


         else if(computer == "ROCK" && u == "ROCK" || computer == "PAPER" && u == "PAPER" || computer == "SCISSORS" && u == "SCISSORS" ){
                

                 o7.setFont(new java.awt.Font("Arial Black", 2, 18)); // NOI18N
                 o7.setForeground(java.awt.Color.red);
                 o7.setText("    DRAW");
                 o7.setBounds(240, 450, 180, 40);  
                 p1.add(o7);

                 b3.setBackground(Color.YELLOW);
                 b3.setOpaque(true);
                 p1.add(b3);
                 b3.setBounds(0, 430, 600, 100);

                 p1.revalidate();
                 p1.repaint();
                 
                
                 n.add(p1);
        }   

    }


     void getHighScores() {
        String line;
        int hs = 0, ls = 1000000000;
        try {
            FileReader file = new FileReader("high.txt");
            BufferedReader buff = new BufferedReader(file);
            while ((line = buff.readLine()) != null) {
                int count = line.length(), sc;
                String scr = new String();
                String userN = new String();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ':') {
                        count = i;
                    }
                    if (i < count) {
                        scr += line.charAt(i);
                    }
                    if (i > count) {
                        userN += line.charAt(i);
                    }
                }
                sc = Integer.parseInt(scr);
                if (sc > hs) {
                    hs = sc;
                }
                if (sc < ls) {
                    ls = sc;
                }
                highScoresArray.put(scr, userN);
            }
            lowestHS = ls;
            highScore = hs;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void saveFile() {
        try {
            FileWriter fileWriter = new FileWriter("high.txt");

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Map.Entry<String, String> entry : highScoresArray.entrySet()) {
                bufferedWriter.write(entry.getKey() + ':' + entry.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void congratulations(String text){

        score1 = Integer.parseInt(z) - Integer.parseInt(w);
        if (score1 < 0) {score1 = 0;}
        
        this.getContentPane().removeAll();
        this.repaint();
        Container n = this.getContentPane();
         JPanel p5 = new JPanel();
         p5.setLayout(null);
         int y = Integer.parseInt(z);        
         int s = Integer.parseInt(w);

         if (score1 > highScore) {
        if (highScoresArray.size() >= 3) {
           while (highScoresArray.size() >= 3) {
            highScoresArray.remove(highScoresArray.lastKey());
           }
           
        }
        highScoresArray.put(Integer.toString(score1), name);
    }  
    if(score1>0){
    saveFile();
     }
         o9 = new JLabel();
         o10 = new JLabel();
         o11 = new JLabel();
         q5 = new JButton();
         q6 = new JButton();
         q9 = new JButton();
         

         o10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
         o10.setText("WINNER");
         p5.add(o10);
         o10.setBounds(240, 160, 120, 60);
         

         if(y>s){
            o9.setBackground(java.awt.Color.white);
            o9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
            o9.setForeground(java.awt.Color.red);
            o9.setText(text);
            p5.add(o9);
            o9.setBounds(230, 210, 160, 70);
         }
        else if(s>y){
            o9.setBackground(java.awt.Color.white);
            o9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
            o9.setForeground(java.awt.Color.red);
            o9.setText(" Computer ");
            p5.add(o9);
            o9.setBounds(230, 210, 160, 70);
         }

         else{
            
            o10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
            o10.setText("DRAW GAME");
            p5.add(o10);
            o10.setBounds(220, 160,200, 60);
         }


          q5.setBackground(java.awt.Color.yellow);
        q5.setFont(new java.awt.Font("Cambria", 3, 14)); // NOI18N
        q5.setText("PLAY AGAIN");
        q5.addActionListener(this);
        p5.add(q5);
        q5.setBounds(170, 400, 130, 23);

        q6.setBackground(java.awt.Color.yellow);
        q6.setFont(new java.awt.Font("Cambria", 3, 14)); // NOI18N
        q6.setText("EXIT");
        q6.addActionListener(this);
        p5.add(q6);
        q6.setBounds(330, 400, 120, 23);


        q9.setBackground(java.awt.Color.yellow);
        q9.setFont(new java.awt.Font("Cambria", 3, 14)); // NOI18N
        q9.setText("HOME SCREEN");
        q9.addActionListener(this);
        p5.add(q9);
        q9.setBounds(240, 350, 130, 23);


         o11.setIcon(new javax.swing.ImageIcon(getClass().getResource("congratulations.gif"))); // NOI18N
         o11.setText("jLabel2");
         p5.add(o11);
         o11.setBounds(0, 0, 610, 540);

         n.add(p5);
         
          this.setSize(615,545);
         this.setVisible(true);


    }



    /*public static void main(String[] args){
        RockPaperScissors3 m = new RockPaperScissors3("Raghuveer");
        
    }*/
}