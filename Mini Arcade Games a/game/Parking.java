package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Parking extends javax.swing.JPanel implements KeyListener {

    JPanel layeredLook;

    int score;
    TreeMap<String, String> highScoresArray;
    boolean startScreen = true;
    boolean user = true;
    int highScore, lowestHS;
    String userName;
    FileReader file;
    BufferedReader buff;

    GameOutline gameOutline;
    Level1 level1;
    Level2 level2;
    Level3 level3;
    Level4 level4;
    Level5 level5;
    EndScreen endScreen;
    AreYouSure areYouSure;
    HighScores highScores;
    CarSelect carSelect;
    CardLayout cards;
    Congrats congrats;

    public static enum STATE {
        OUTLINE,LEVEL1,LEVEL2,LEVEL3,LEVEL4,LEVEL5,
        ENDSCREEN,AREYOUSURE,HIGHSCORES,CARSELECT,CONGRATS
    }

    public static enum LEVELS {
        L1, L2, L3, L4, L5
    }

    public static enum CAR {
        BLUE, GREEN, RED
    }

    STATE State = STATE.OUTLINE;
    LEVELS Level = LEVELS.L1;
    CAR Car = CAR.BLUE;

    public Parking(String name) {
        initComponents();
        setLayout(null);
        userName = name;
        highScoresArray = new TreeMap<String,String>(Collections.reverseOrder());
        
        getHighScores();
        
        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        score = 0;

        layeredLook = new JPanel();
        layeredLook.setSize(600, 400);
        layeredLook.setLocation(0, 0);
        layeredLook.setVisible(true);
        layeredLook.setOpaque(false);

        cards = new CardLayout();
        layeredLook.setLayout(cards);
        this.add(layeredLook);

        init();
    }

    void init() {
        switch (State) {
            case OUTLINE:
                gameOutline = new GameOutline();
                layeredLook.add(gameOutline, "GameOutline");
                cards.show(layeredLook, "GameOutline");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case LEVEL1:
                level1 = new Level1();
                layeredLook.add(level1, "Level1");
                level1.start();
                cards.show(layeredLook, "Level1");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case LEVEL2:
                level2 = new Level2();
                layeredLook.add(level2, "Level2");
                level2.start();
                cards.show(layeredLook, "Level2");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case LEVEL3:
                level3 = new Level3();
                layeredLook.add(level3, "Level3");
                level3.start();
                cards.show(layeredLook, "Level3");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case LEVEL4:
                level4 = new Level4();
                layeredLook.add(level4, "Level4");
                level4.start();
                cards.show(layeredLook, "Level4");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case LEVEL5:
                level5 = new Level5();
                layeredLook.add(level5, "Level5");
                level5.start();
                cards.show(layeredLook, "Level5");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case ENDSCREEN:
                switch (Level) {
                    case L1:
                        endScreen = new EndScreen(level1.gameWon, 0, score);
                        break;
                    case L2:
                        endScreen = new EndScreen(level2.gameWon, 0, score);
                        break;
                    case L3:
                        endScreen = new EndScreen(level3.gameWon, 0, score);
                        break;
                    case L4:
                        endScreen = new EndScreen(level4.gameWon, 0, score);
                        break;
                    case L5:
                        endScreen = new EndScreen(level5.gameWon, 0, score);
                        break;

                }
                layeredLook.add(endScreen, "EndScreen");
                cards.show(layeredLook, "EndScreen");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case AREYOUSURE:
                areYouSure = new AreYouSure();
                layeredLook.add(areYouSure, "AreYouSure");
                cards.show(layeredLook, "AreYouSure");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case HIGHSCORES:
                highScores = new HighScores();
                layeredLook.add(highScores, "HighScores");
                cards.show(layeredLook, "HighScores");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case CARSELECT:
                carSelect = new CarSelect();
                layeredLook.add(carSelect, "CarSelect");
                cards.show(layeredLook, "CarSelect");
                layeredLook.repaint();
                layeredLook.revalidate();
                break;
            case CONGRATS:
                if (score > lowestHS) {
                    if (highScoresArray.size() >= 5) {
                        while(highScoresArray.size() >= 5) {
							highScoresArray.remove(highScoresArray.lastKey()); 
						}
					} 
                    highScoresArray.put(Integer.toString(score), userName);
                }
                saveFile();
                congrats = new Congrats();
                layeredLook.add(congrats, "Congrats");
                cards.show(layeredLook, "Congrats");
                layeredLook.repaint();
                layeredLook.revalidate();
            default:
                break;
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

    int[] ax;
    int[] ay;
    double tempAngle;
    Polygon mainCar;
    String line;

    void getHighScores() {
        int hs = 0, ls = 1000000000;
        try {
            file = new FileReader("temp.txt");
            buff = new BufferedReader(file);
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
            FileWriter fileWriter = new FileWriter("temp.txt");

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

    void drawCar(Graphics g, Car mainC) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform old = g2d.getTransform();

        g2d.rotate(mainC.getAngle(), mainC.getX(), mainC.getY());
        // Car Body
        if (Car == CAR.BLUE) {
            g2d.setColor(Color.blue);
        } else if (Car == CAR.GREEN) {
            g2d.setColor(Color.green);
        } else if (Car == CAR.RED) {
            g2d.setColor(Color.red);
        }
        g2d.fillRect((int) mainC.getX(), (int) mainC.getY(),
                mainC.getWidth() / 2 - (int) mainC.ww,
                mainC.getHeight() / 2);
        g2d.fillRect((int) mainC.getX() - mainC.getWidth() / 2 + (int) mainC.ww, (int) mainC.getY(),
                mainC.getWidth() / 2 - (int) mainC.ww,
                mainC.getHeight() / 2);
        g2d.fillRect((int) mainC.getX() - mainC.getWidth() / 2 + (int) mainC.ww, (int) mainC.getY() - mainC.getHeight() / 2,
                mainC.getWidth() / 2 - (int) mainC.ww,
                mainC.getHeight() / 2);
        g2d.fillRect((int) mainC.getX(), (int) mainC.getY() - mainC.getHeight() / 2,
                mainC.getWidth() / 2 - (int) mainC.ww,
                mainC.getHeight() / 2);
        g2d.fillRect((int) mainC.getX() + mainC.getWidth() / 2 - (int) mainC.ww,
                (int) mainC.getY() - mainC.getHeight() / 2 + (int) mainC.wh,
                (int) mainC.ww, mainC.getHeight() - 2 * (int) mainC.wh);
        g2d.fillRect((int) mainC.getX() - mainC.getWidth() / 2,
                (int) mainC.getY() - mainC.getHeight() / 2 + (int) mainC.wh,
                (int) mainC.ww, mainC.getHeight() - 2 * (int) mainC.wh);
        //HeadLights
        g2d.setColor(Color.yellow);
        g2d.fillRect((int) mainC.getX() - mainC.getWidth() / 2 + 3 * (int) (mainC.ww / 2), (int) mainC.getY() - mainC.getHeight() / 2,
                (int) mainC.ww, (int) mainC.wh / 4);
        g2d.fillRect((int) mainC.getX() + mainC.getWidth() / 2 - 5 * (int) (mainC.ww / 2), (int) mainC.getY() - mainC.getHeight() / 2,
                (int) mainC.ww, (int) mainC.wh / 4);
        //bonnet
        g2d.setColor(Color.black);
        g2d.drawLine((int) mainC.getX() - mainC.getWidth() / 2 + 5 * (int) (mainC.ww / 2),
                (int) mainC.getY() - mainC.getHeight() / 2 + (int) (mainC.wh / 3),
                (int) mainC.getX() + mainC.getWidth() / 2 - 5 * (int) (mainC.ww / 2),
                (int) mainC.getY() - mainC.getHeight() / 2 + (int) (mainC.wh / 3));
        g2d.drawLine((int) mainC.getX() - mainC.getWidth() / 2 + 5 * (int) (mainC.ww / 2),
                (int) mainC.getY() - mainC.getHeight() / 2 + (int) (2 * mainC.wh / 3),
                (int) mainC.getX() + mainC.getWidth() / 2 - 5 * (int) (mainC.ww / 2),
                (int) mainC.getY() - mainC.getHeight() / 2 + (int) (2 * mainC.wh / 3));
        g2d.drawLine((int) mainC.getX() - mainC.getWidth() / 2 + 5 * (int) (mainC.ww / 2),
                (int) mainC.getY() - mainC.getHeight() / 2 + (int) (mainC.wh / 2),
                (int) mainC.getX() + mainC.getWidth() / 2 - 5 * (int) (mainC.ww / 2),
                (int) mainC.getY() - mainC.getHeight() / 2 + (int) (mainC.wh / 2));
        //roof
        if (user) {
            g2d.setColor(Color.white);
        } else {
            g2d.setColor(Color.black);
        }
        g2d.fillRect((int) mainC.getX() - mainC.getWidth() / 2 + 3 * (int) (mainC.ww / 2),
                (int) mainC.getY() - mainC.getHeight() / 2 + (int) (mainC.wh),
                mainC.getWidth() - 3 * (int) mainC.ww, mainC.getHeight() - 2 * (int) mainC.wh);
        //Wheels
        g2d.setColor(Color.black);
        g2d.fillRect((int) mainC.getX() + mainC.getWidth() / 2 - (int) mainC.ww, (int) mainC.getY() + mainC.getHeight() / 2 - (int) mainC.wh,
                (int) mainC.ww, (int) mainC.wh);
        g2d.fillRect((int) mainC.getX() - mainC.getWidth() / 2, (int) mainC.getY() + mainC.getHeight() / 2 - (int) mainC.wh,
                (int) mainC.ww, (int) mainC.wh);
        g2d.fillRect((int) mainC.getX() + mainC.getWidth() / 2 - (int) mainC.ww, (int) mainC.getY() - mainC.getHeight() / 2,
                (int) mainC.ww, (int) mainC.wh);
        g2d.fillRect((int) mainC.getX() - mainC.getWidth() / 2, (int) mainC.getY() - mainC.getHeight() / 2,
                (int) mainC.ww, (int) mainC.wh);

        g2d.setTransform(old);
    }

    Polygon getRekt(Car veh) {
        Polygon main = new Polygon();
        double x, y, tempX, tempY, cos, sin;
        ax = new int[4];
        ay = new int[4];
        
        x = (int) veh.getX() - veh.getWidth() / 2;
        y = (int) veh.getY() + veh.getHeight() / 2;
        cos = Math.cos(Math.toRadians(tempAngle));
        sin = Math.sin(Math.toRadians(tempAngle));
        x -= veh.getX();
        y -= veh.getY();
        tempX = x * cos - y * sin;
        tempY = x * sin + y * cos;
        x = veh.getX() + tempX;
        y = veh.getY() + tempY;
        ax[0] = (int) x;
        ay[0] = (int) y;
        main.addPoint((int) x, (int) y);
        
        x = (int) veh.getX() + veh.getWidth() / 2;
        y = (int) veh.getY() + veh.getHeight() / 2;
        cos = Math.cos(Math.toRadians(tempAngle));
        sin = Math.sin(Math.toRadians(tempAngle));
        x -= veh.getX();
        y -= veh.getY();
        tempX = x * cos - y * sin;
        tempY = x * sin + y * cos;
        x = veh.getX() + tempX;
        y = veh.getY() + tempY;
        ax[1] = (int) x;
        ay[1] = (int) y;
        main.addPoint((int) x, (int) y);
        
        x = (int) veh.getX() - veh.getWidth() / 2;
        y = (int) veh.getY() - veh.getHeight() / 2;
        cos = Math.cos(Math.toRadians(tempAngle));
        sin = Math.sin(Math.toRadians(tempAngle));
        x -= veh.getX();
        y -= veh.getY();
        tempX = x * cos - y * sin;
        tempY = x * sin + y * cos;
        x = veh.getX() + tempX;
        y = veh.getY() + tempY;
        ax[2] = (int) x;
        ay[2] = (int) y;
        main.addPoint((int) x, (int) y);
        
        x = (int) veh.getX() + veh.getWidth() / 2;
        y = (int) veh.getY() - veh.getHeight() / 2;
        cos = Math.cos(Math.toRadians(tempAngle));
        sin = Math.sin(Math.toRadians(tempAngle));
        x -= veh.getX();
        y -= veh.getY();
        tempX = x * cos - y * sin;
        tempY = x * sin + y * cos;
        x = veh.getX() + tempX;
        y = veh.getY() + tempY;
        ax[3] = (int) x;
        ay[3] = (int) y;
        main.addPoint((int) x, (int) y);
        return main;
    }

    boolean contain(int[] x, int[] y, Rectangle p) {
        for (int i = 0; i < 4; i++) {
            if (p.contains(x[i], y[i])) {
                return true;
            }
        }
        Polygon a = new Polygon();
        for (int i = 0; i < 4; i++) {
            a.addPoint(x[i], y[i]);
        }
        if (a.contains(p.x, p.y)) {
            return true;
        } else if (a.contains(p.x + p.width, p.y)) {
            return true;
        } else if (a.contains(p.x, p.y + p.height)) {
            return true;
        } else if (a.contains(p.x + p.width, p.y + p.height)) {
            return true;
        }

        return false;
    }

    //GAME OUTLINE Code
    public class GameOutline extends javax.swing.JPanel {
        public GameOutline() {
            super();
            initComponents();
        }
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            start = new javax.swing.JButton();
            highScores = new javax.swing.JButton();
            carSelect = new javax.swing.JButton();
            close = new javax.swing.JButton();
            jLabel1 = new javax.swing.JLabel();

            setLayout(null);

            start.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/StartButton.png"))); // NOI18N
            start.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(204, 51, 0), new java.awt.Color(204, 51, 0)));
            start.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    startActionPerformed(evt);
                }
            });
            add(start);
            start.setBounds(315, 160, 103, 42);

            highScores.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images\\HighScoresButton.png"))); // NOI18N
            highScores.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(204, 51, 0), new java.awt.Color(204, 51, 0)));
            highScores.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    highScoresActionPerformed(evt);
                }
            });
            add(highScores);
            highScores.setBounds(315, 220, 103, 42);

            carSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images\\CarSelectButton.png"))); // NOI18N
            carSelect.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(204, 51, 0), new java.awt.Color(204, 51, 0)));
            carSelect.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    carSelectActionPerformed(evt);
                }
            });
            add(carSelect);
            carSelect.setBounds(315, 280, 103, 42);

            close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/Close.png"))); // NOI18N
            close.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    closeActionPerformed(evt);
                }
            });
            add(close);
            close.setBounds(572, 4, 24, 25);

            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/Title Screen.png"))); // NOI18N
            jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));
            add(jLabel1);
            jLabel1.setBounds(0, 0, 600, 400);
        }// </editor-fold>                        

        private void startActionPerformed(java.awt.event.ActionEvent evt) {
            State = STATE.LEVEL1;
            Level = LEVELS.L1;
            init();
        }

        private void highScoresActionPerformed(java.awt.event.ActionEvent evt) {
            State = STATE.HIGHSCORES;
            init();
        }

        private void carSelectActionPerformed(java.awt.event.ActionEvent evt) {
            State = STATE.CARSELECT;
            init();
        }

        private void closeActionPerformed(java.awt.event.ActionEvent evt) {
            startScreen = false;
            State = STATE.AREYOUSURE;
            init();
        }
                    
        private javax.swing.JButton carSelect;
        private javax.swing.JButton close;
        private javax.swing.JButton highScores;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JButton start;                
    }

    //LEVEL 1 Code
    public class Level1 extends javax.swing.JPanel implements Runnable {

        Car car = new Car(300, 300, 73, 130, 90, 10, 30);
        boolean moveForward, moveBackward;
        boolean gameFinished;
        boolean gameWon = false;
        boolean upPressed, downPressed, rightPressed, leftPressed;
        double carTempWidth, carTempHeight;
        Thread looper;
        double secondsLeft = 20;
        Image grassImg, parkingSlotParkImg, parkingSlotDontParkImg, roadImg, carUserImg;

        public Level1() {
            initComponents();
            initLevel();
        }

        private void initLevel() {
            try {
                ImageIcon grass = new ImageIcon(getClass().getResource("Images\\grass100x100.png"));
                grassImg = grass.getImage();
                ImageIcon parkingSlotPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(Park)100x150.png"));
                parkingSlotParkImg = parkingSlotPark.getImage();
                ImageIcon parkingSlotDontPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(DontPark)100x150.png"));
                parkingSlotDontParkImg = parkingSlotDontPark.getImage();
                ImageIcon road = new ImageIcon(getClass().getResource("Images\\Road100x100.png"));
                roadImg = road.getImage();
                ImageIcon carUser = new ImageIcon(getClass().getResource("Images\\UserCar52x130.png"));
                carUserImg = carUser.getImage();
            } catch (Exception e) {
                System.out.println(e);
            }
            gameFinished = false;
            score = 0;
            upPressed = downPressed = leftPressed = rightPressed = false;
            moveForward = moveBackward = false;
            tempAngle = 0;
        }

        public synchronized void start() {
            looper = new Thread(this);
            looper.start();
        }

        @Override
        public void run() {
            long timer = System.currentTimeMillis();
            while (!gameFinished) {

                if (System.currentTimeMillis() - timer > 1000) {
                    secondsLeft--;
                    timer = System.currentTimeMillis();
                }
                if (secondsLeft == 0) {
                    gameFinished = true;
                    gameWon = false;
                }
                repaint();
                play();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {}
            }
            if (gameWon) {
                score += (int) (secondsLeft / 30 * 5000);
            }
            State = STATE.ENDSCREEN;
            init();
        }

        void addBackground(Graphics g) {
            for (int i = 0; i < this.getWidth(); i += 100) {
                for (int j = 0; j < this.getHeight(); j += 100) {
                    if (i == 200 || i == 300) {
                        g.drawImage(roadImg, i, j, 100, 100, null);
                    } else {
                        g.drawImage(grassImg, i, j, 100, 100, null);
                    }
                }
            }
            g.drawImage(parkingSlotParkImg, 200, 0, 100, 150, null);
            g.drawImage(parkingSlotDontParkImg, 300, 0, 100, 150, null);
        }

        @Override
        public void paint(Graphics g) {
            level1Paint(g);
            g.setColor(Color.yellow);
            Font font = new Font("Segoe Print", Font.BOLD, 18);
            g.setFont(font);
            g.drawString("Time Left: " + secondsLeft, 420, 50);
            font = new Font("Segoe Print", Font.BOLD, 24);
            g.setFont(font);
            g.drawString("Level 1", 20, 50);
        }

        void level1Paint(Graphics g) {
            addBackground(g);
            drawCar(g, car);
        }

        public void play() {
            if (upPressed) {
                moveForward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle += 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle -= 0;
                }
            } else {
                moveForward = false;
            }
            if (downPressed) {
                moveBackward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle -= 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle += 0;
                }
            } else {
                moveBackward = false;
            }

            if (tempAngle >= 360) {
                tempAngle = 0;
            }
            if (tempAngle < 0) {
                tempAngle = 359;
            }
            car.setAngle((int) tempAngle);
            if (moveForward) {
                car.moveForward();
            }
            if (moveBackward) {
                car.moveBackward();
            }
            rotationTemps();
            level1InsideCheck();
            level1WonCheck();
        }

        void level1WonCheck() {
            boolean left = (car.getX() - carTempWidth >= 206);
            boolean right = (car.getX() + carTempWidth <= 294);
            boolean up = (car.getY() - carTempHeight >= 6);
            boolean down = (car.getY() + carTempHeight <= 144);
            if (left && right && down && up && (!moveForward && !moveBackward)) {
                gameFinished = true;
                gameWon = true;
            }
        }

        void level1InsideCheck() {
            boolean left = (car.getX() - carTempWidth <= 200);
            boolean right = (car.getX() + carTempWidth >= 400);
            boolean up = (car.getY() - carTempHeight <= 0);
            boolean down = (car.getY() + carTempHeight >= 400);
            if (left || right || up || down) {
                gameFinished = true;
            }
            gameWon = false;
        }

        void rotationTemps() {
            double cos = Math.cos(Math.toRadians(tempAngle));
            double sin = Math.sin(Math.toRadians(tempAngle));
            if (tempAngle < 90 && tempAngle >= 0) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 180 && tempAngle >= 90) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 270 && tempAngle >= 180) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 360 && tempAngle >= 270) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            }
        }
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(0, 568, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(0, 367, Short.MAX_VALUE))
            );
        }// </editor-fold>                                      
    }

    //LEVEL 2 Code
    public class Level2 extends javax.swing.JPanel implements Runnable {

        Car car = new Car(450, 350, 73, 130, 0, 10, 30);
        boolean moveForward, moveBackward;
        boolean gameFinished;
        boolean gameWon = false;
        boolean upPressed, downPressed, rightPressed, leftPressed;
        double carTempWidth, carTempHeight;
        Thread looper;
        double secondsLeft = 30;
        Car tempCar[];
        int noOfCars = 7;
        Image grassImg, parkingSlotParkImg, parkingSlotDontParkImg, roadImg, carUserImg;

        public Level2() {
            initComponents();
            initLevel();
            initCars();
        }

        void initCars() {
            tempCar = new Car[noOfCars];
            for (int i = 0; i < 4; i++) {
                tempCar[i] = new Car(75, i * 100 + 50, 73, 130, 90, 10, 30);
            }
            tempCar[4] = new Car(525, 50, 73, 130, 90, 10, 30);
            tempCar[5] = new Car(525, 250, 73, 130, 90, 10, 30);
            tempCar[6] = new Car(215, 125, 73, 130, 0, 10, 30);
        }

        private void initLevel() {
            try {
                ImageIcon grass = new ImageIcon(getClass().getResource("Images\\grass100x100.png"));
                grassImg = grass.getImage();
                ImageIcon parkingSlotPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(Park)150x100.png"));
                parkingSlotParkImg = parkingSlotPark.getImage();
                ImageIcon parkingSlotDontPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(DontPark)150x100.png"));
                parkingSlotDontParkImg = parkingSlotDontPark.getImage();
                ImageIcon road = new ImageIcon(getClass().getResource("Images\\Road100x100.png"));
                roadImg = road.getImage();
                ImageIcon carUser = new ImageIcon(getClass().getResource("Images\\UserCar52x130.png"));
                carUserImg = carUser.getImage();
            } catch (Exception e) {
                System.out.println(e);
            }
            gameFinished = false;
            upPressed = downPressed = leftPressed = rightPressed = false;
            moveForward = moveBackward = false;
            tempAngle = 270;
        }

        public synchronized void start() {
            looper = new Thread(this);
            looper.start();
        }

        @Override
        public void run() {
            long timer = System.currentTimeMillis();
            while (!gameFinished) {
                if (System.currentTimeMillis() - timer > 1000) {;
                    secondsLeft--;
                    timer = System.currentTimeMillis();
                }
                if (secondsLeft == 0) {
                    gameFinished = true;
                    gameWon = false;
                }
                repaint();
                play();

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {

                }
            }
            if (gameWon) {
                score += secondsLeft / 60 * 10000;
            }
            State = STATE.ENDSCREEN;
            init();
        }

        void addBackground(Graphics g) {
            for (int i = 0; i < this.getWidth(); i += 100) {
                for (int j = 0; j < this.getHeight(); j += 100) {
                    g.drawImage(roadImg, i, j, 100, 100, null);
                }
            }
            for (int i = 0; i < this.getWidth(); i += 100) {
                g.drawImage(parkingSlotDontParkImg, 0, i, 150, 100, null);
            }
            for (int i = 0; i < this.getWidth(); i += 100) {
                if (i == 100) {
                    g.drawImage(parkingSlotParkImg, 450, i, 150, 100, null);
                } else {
                    g.drawImage(parkingSlotDontParkImg, 450, i, 150, 100, null);
                }
            }

        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            level2Paint(g);
            g.setColor(Color.yellow);
            Font font = new Font("Segoe Print", Font.BOLD, 18);
            g.setFont(font);
            g.drawString("Time Left: " + secondsLeft, 420, 50);
            font = new Font("Segoe Print", Font.BOLD, 24);
            g.setFont(font);
            g.drawString("Level 2", 20, 50);
        }

        void level2Paint(Graphics g) {
            addBackground(g);
            CAR tempCarColor = Car;

            for (int i = 0; i < noOfCars; i++) {
                int temp = 0;
                if (i % 3 == 0) {
                    temp = i;
                }
                user = false;
                if (temp % 2 == 0) {
                    if (i % 3 == 0) {
                        Car = CAR.BLUE;
                    }
                    if (i % 3 == 1) {
                        Car = CAR.RED;
                    }
                    if (i % 3 == 2) {
                        Car = CAR.GREEN;
                    }
                } else {
                    if (i % 3 == 0) {
                        Car = CAR.RED;
                    }
                    if (i % 3 == 1) {
                        Car = CAR.GREEN;
                    }
                    if (i % 3 == 2) {
                        Car = CAR.BLUE;
                    }
                }

                drawCar(g, tempCar[i]);
            }
            user = true;
            Car = tempCarColor;
            drawCar(g, car);
        }

        public void play() {
            if (upPressed) {
                moveForward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle += 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle -= 0;
                }
            } else {
                moveForward = false;
            }
            if (downPressed) {
                moveBackward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle -= 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle += 0;
                }
            } else {
                moveBackward = false;
            }

            if (tempAngle >= 360) {
                tempAngle = 0;
            }
            if (tempAngle < 0) {
                tempAngle = 359;
            }
            car.setAngle((int) tempAngle);
            if (moveForward) {
                car.moveForward();
            }
            if (moveBackward) {
                car.moveBackward();
            }
            rotationTemps();
            level2InsideCheck();
            level2WonCheck();
        }

        void level2WonCheck() {
            boolean left = (car.getX() - carTempWidth >= 456);
            boolean right = (car.getX() + carTempWidth <= 594);
            boolean up = (car.getY() - carTempHeight >= 106);
            boolean down = (car.getY() + carTempHeight <= 194);
            if (left && right && down && up && (!moveForward && !moveBackward)) {
                gameFinished = true;
                gameWon = true;
            }
        }

        void level2InsideCheck() {
            Rectangle a = new Rectangle();
            mainCar = getRekt(car);
            for (int i = 0; i < 4; i++) {
                a.setBounds(10, i * 100 + 13, 130, 73);
                if (contain(ax, ay, a)) {
                    gameFinished = true;
                    gameWon = false;
                }
            }
            for (int i = 0; i < 2; i++) {
                a.setBounds(460, i * 200 + 13, 130, 73);
                if (contain(ax, ay, a)) {
                    gameFinished = true;
                    gameWon = false;
                }
            }
            a.setBounds(215 - 37, 125 - 65, 73, 130);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            if (isOutOfBounds(0, 0, 600, 400)) {
                gameFinished = true;
                gameWon = false;
            }
        }

        boolean isOutOfBounds(double a, double b, double c, double d) {
            if (car.getX() - carTempWidth <= a) {
                return true;
            }
            if (car.getX() + carTempWidth >= c) {
                return true;
            }
            if (car.getY() - carTempHeight <= b) {
                return true;
            }
            if (car.getY() + carTempHeight >= d) {
                return true;
            }
            return false;
        }

        void rotationTemps() {
            double cos = Math.cos(Math.toRadians(tempAngle));
            double sin = Math.sin(Math.toRadians(tempAngle));
            if (tempAngle < 90 && tempAngle >= 0) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 180 && tempAngle >= 90) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 270 && tempAngle >= 180) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 360 && tempAngle >= 270) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            }
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 592, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 392, Short.MAX_VALUE)
            );
        }// </editor-fold>                               
    }

    //LEVEL 3 Code
    public class Level3 extends javax.swing.JPanel implements Runnable {

        Car car = new Car(550, 350, 37, 65, 0, 5, 15);
        boolean moveForward, moveBackward;
        boolean gameFinished;
        boolean gameWon = false;
        boolean upPressed, downPressed, rightPressed, leftPressed;
        double carTempWidth, carTempHeight;
        Thread looper;
        double secondsLeft = 60;
        Car tempCar[];
        int noOfCars = 13;
        Image grassImg, parkingSlotParkImg, parkingSlotDontParkImg, roadImg, carUserImg;

        public Level3() {
            initComponents();
            initLevel();
            initCars();
        }

        void initCars() {
            tempCar = new Car[noOfCars];
            for (int i = 0; i < 6; i++) {
                tempCar[i] = new Car(463, i * 50 + 125, 37, 65, 90, 5, 15);
            }
            for (int i = 6; i < 12; i++) {
                if (i == 7) {
                    tempCar[i] = new Car(250, 100, 37, 65, 0, 5, 15);
                } else {
                    tempCar[i] = new Car(138, (i - 6) * 50 + 25, 37, 65, 270, 5, 15);
                }
            }
            tempCar[12] = new Car(350, 300, 37, 65, 180, 5, 15);
        }

        private void initLevel() {
            try {
                ImageIcon grass = new ImageIcon(getClass().getResource("Images\\grass50x50.png"));
                grassImg = grass.getImage();
                ImageIcon parkingSlotPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(Park)75x50.png"));
                parkingSlotParkImg = parkingSlotPark.getImage();
                ImageIcon parkingSlotDontPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(DontPark)75x50.png"));
                parkingSlotDontParkImg = parkingSlotDontPark.getImage();
                ImageIcon road = new ImageIcon(getClass().getResource("Images\\Road100x100.png"));
                roadImg = road.getImage();
            } catch (Exception e) {
                System.out.println(e);
            }
            gameFinished = false;
            upPressed = downPressed = leftPressed = rightPressed = false;
            moveForward = moveBackward = false;
            tempAngle = 0;
        }

        public synchronized void start() {
            looper = new Thread(this);
            looper.start();
        }

        @Override
        public void run() {
            long timer = System.currentTimeMillis();
            while (!gameFinished) {
                if (System.currentTimeMillis() - timer > 1000) {;
                    secondsLeft--;
                    timer = System.currentTimeMillis();
                }
                if (secondsLeft == 0) {
                    gameFinished = true;
                    gameWon = false;
                }
                repaint();
                play();

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {

                }
            }
            if (gameWon) {
                score += secondsLeft / 60 * 15000;
            }
            State = STATE.ENDSCREEN;
            init();
        }

        void addBackground(Graphics g) {
            for (int i = 0; i < this.getWidth(); i += 100) {
                for (int j = 0; j < this.getHeight(); j += 100) {
                    g.drawImage(roadImg, i, j, 100, 100, null);
                }
            }
            for (int i = 100; i < this.getHeight(); i += 50) {
                g.drawImage(grassImg, 375, i, 50, 50, null);
            }
            for (int i = 0; i < this.getHeight() - 100; i += 50) {
                g.drawImage(grassImg, 175, i, 50, 50, null);
            }
            for (int i = 100; i < this.getHeight(); i += 50) {
                //g.drawImage(parkingSlotParkImg, 450, i, 150, 100, null);
                g.drawImage(parkingSlotDontParkImg, 425, i, 75, 50, null);
            }
            for (int i = 0; i < this.getHeight() - 100; i += 50) {
                if (i == 50) {
                    g.drawImage(parkingSlotParkImg, 100, i, 75, 50, null);
                } else {
                    g.drawImage(parkingSlotDontParkImg, 100, i, 75, 50, null);
                }
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            level3Paint(g);
            g.setColor(Color.yellow);
            Font font = new Font("Segoe Print", Font.BOLD, 18);
            g.setFont(font);
            g.drawString("Time Left: " + secondsLeft, 420, 50);
            font = new Font("Segoe Print", Font.BOLD, 24);
            g.setFont(font);
            g.drawString("Level 3", 20, 50);
        }

        void level3Paint(Graphics g) {
            addBackground(g);
            CAR tempCarColor = Car;

            for (int i = 0; i < noOfCars; i++) {
                int temp = 0;
                if (i % 3 == 0) {
                    temp = i;
                }
                user = false;
                if (temp % 2 == 0) {
                    if (i % 3 == 0) {
                        Car = CAR.BLUE;
                    }
                    if (i % 3 == 1) {
                        Car = CAR.RED;
                    }
                    if (i % 3 == 2) {
                        Car = CAR.GREEN;
                    }
                } else {
                    if (i % 3 == 0) {
                        Car = CAR.RED;
                    }
                    if (i % 3 == 1) {
                        Car = CAR.GREEN;
                    }
                    if (i % 3 == 2) {
                        Car = CAR.BLUE;
                    }
                }

                drawCar(g, tempCar[i]);
            }
            user = true;
            Car = tempCarColor;
            drawCar(g, car);
        }

        public void play() {
            if (upPressed) {
                moveForward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle += 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle -= 0;
                }
            } else {
                moveForward = false;
            }
            if (downPressed) {
                moveBackward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle -= 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle += 0;
                }
            } else {
                moveBackward = false;
            }

            if (tempAngle >= 360) {
                tempAngle = 0;
            }
            if (tempAngle < 0) {
                tempAngle = 359;
            }
            car.setAngle((int) tempAngle);
            if (moveForward) {
                car.moveForward();
            }
            if (moveBackward) {
                car.moveBackward();
            }
            rotationTemps();
            level3InsideCheck();
            level3WonCheck();
        }

        void level3WonCheck() {
            boolean left = (car.getX() - carTempWidth >= 102);
            boolean right = (car.getX() + carTempWidth <= 173);
            boolean up = (car.getY() - carTempHeight >= 52);
            boolean down = (car.getY() + carTempHeight <= 98);
            if (left && right && down && up && (!moveForward && !moveBackward)) {
                gameFinished = true;
                gameWon = true;
                System.out.println("You Won");
            }
        }

        void level3InsideCheck() {
            Rectangle a = new Rectangle();
            mainCar = getRekt(car);
            for (int i = 0; i < 6; i++) {
                a.setBounds(430, (i + 2) * 50 + 7, 65, 37);
                if (contain(ax, ay, a)) {
                    gameFinished = true;
                    gameWon = false;
                }
            }
            for (int i = 0; i < 6; i++) {
                if (i == 1) {
                } else {
                    a.setBounds(105, i * 50 + 7, 65, 37);
                    if (contain(ax, ay, a)) {
                        gameFinished = true;
                        gameWon = false;
                    }
                }
            }
            a.setBounds(232, 100 - 37, 37, 65);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            a.setBounds(332, 300 - 37, 37, 65);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            a.setBounds(375, 100, 50, 300);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            a.setBounds(175, 0, 50, 300);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            if (isOutOfBounds(0, 0, 600, 400)) {
                gameFinished = true;
                gameWon = false;
            }
        }

        boolean isOutOfBounds(double a, double b, double c, double d) {
            if (car.getX() - carTempWidth <= a) {
                return true;
            }
            if (car.getX() + carTempWidth >= c) {
                return true;
            }
            if (car.getY() - carTempHeight <= b) {
                return true;
            }
            if (car.getY() + carTempHeight >= d) {
                return true;
            }
            return false;
        }

        void rotationTemps() {
            double cos = Math.cos(Math.toRadians(tempAngle));
            double sin = Math.sin(Math.toRadians(tempAngle));
            if (tempAngle < 90 && tempAngle >= 0) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 180 && tempAngle >= 90) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 270 && tempAngle >= 180) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 360 && tempAngle >= 270) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            }
        }
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 592, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 392, Short.MAX_VALUE)
            );
        }// </editor-fold>                                  
    }

    //LEVEL 4 Code
    public class Level4 extends javax.swing.JPanel implements Runnable {

        Car car = new Car(450, 350, 37, 65, 0, 5, 15);
        boolean moveForward, moveBackward;
        boolean gameFinished;
        boolean gameWon = false;
        boolean upPressed, downPressed, rightPressed, leftPressed;
        double carTempWidth, carTempHeight;
        Thread looper;
        double secondsLeft = 90;
        Car tempCar[];
        int noOfCars = 5;
        Image grassImg, parkingSlotParkImg, parkingSlotDontParkImg, roadImg, carUserImg;

        public Level4() {
            initComponents();
            initLevel();
            initCars();
        }

        void initCars() {
            tempCar = new Car[noOfCars];
            for (int i = 0; i < 4; i++) {
                tempCar[i] = new Car(263, i * 50 + 125, 37, 65, 90, 5, 15);
            }
            tempCar[4] = new Car(188, 275, 37, 65, 90, 5, 15);
        }

        private void initLevel() {
            try {
                ImageIcon grass = new ImageIcon(getClass().getResource("Images\\grass100x100.png"));
                grassImg = grass.getImage();
                ImageIcon parkingSlotPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(Park)75x50.png"));
                parkingSlotParkImg = parkingSlotPark.getImage();
                ImageIcon parkingSlotDontPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(DontPark)75x50.png"));
                parkingSlotDontParkImg = parkingSlotDontPark.getImage();
                ImageIcon road = new ImageIcon(getClass().getResource("Images\\Road100x100.png"));
                roadImg = road.getImage();
            } catch (Exception e) {
                System.out.println(e);
            }
            gameFinished = false;
            upPressed = downPressed = leftPressed = rightPressed = false;
            moveForward = moveBackward = false;
            tempAngle = 0;
        }

        public synchronized void start() {
            looper = new Thread(this);
            looper.start();
        }

        @Override
        public void run() {
            long timer = System.currentTimeMillis();
            while (!gameFinished) {
                if (System.currentTimeMillis() - timer > 1000) {;
                    secondsLeft--;
                    timer = System.currentTimeMillis();
                }
                if (secondsLeft == 0) {
                    gameFinished = true;
                    gameWon = false;
                }
                repaint();
                play();

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {

                }
            }
            if (gameWon) {
                score += secondsLeft / 90 * 20000;
            }
            State = STATE.ENDSCREEN;
            init();
        }

        void addBackground(Graphics g) {
            for (int i = 0; i < this.getWidth() - 200; i += 100) {
                g.drawImage(grassImg, i, 300, 100, 100, null);
            }
            for (int i = 100; i < this.getHeight() - 100; i += 100) {
                g.drawImage(grassImg, 300, i, 100, 100, null);
            }

            for (int j = 0; j < this.getHeight(); j += 100) {
                g.drawImage(roadImg, 400, j, 100, 100, null);
            }
            for (int j = 0; j < this.getHeight(); j += 100) {
                g.drawImage(grassImg, 500, j, 100, 100, null);
            }

            for (int j = 0; j < this.getWidth() - 200; j += 100) {
                g.drawImage(roadImg, j, 0, 100, 100, null);
            }
            for (int i = 0; i < this.getWidth() - 300; i += 100) {
                for (int j = 100; j < this.getHeight() - 100; j += 100) {
                    g.drawImage(roadImg, i, j, 100, 100, null);
                }
            }
            for (int i = 100; i < this.getHeight() - 100; i += 50) {
                g.drawImage(parkingSlotDontParkImg, 225, i, 75, 50, null);
            }
            for (int i = 0; i < this.getWidth() - 375; i += 75) {
                if (i == 75) {
                    g.drawImage(parkingSlotParkImg, i, 250, 75, 50, null);
                } else {
                    g.drawImage(parkingSlotDontParkImg, i, 250, 75, 50, null);
                }
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            level4Paint(g);
            g.setColor(Color.yellow);
            Font font = new Font("Segoe Print", Font.BOLD, 18);
            g.setFont(font);
            g.drawString("Time Left: " + secondsLeft, 420, 50);
            font = new Font("Segoe Print", Font.BOLD, 24);
            g.setFont(font);
            g.drawString("Level 4", 20, 50);
        }

        void level4Paint(Graphics g) {
            addBackground(g);
            CAR tempCarColor = Car;

            for (int i = 0; i < noOfCars; i++) {
                int temp = 0;
                if (i % 3 == 0) {
                    temp = i;
                }
                user = false;
                if (temp % 2 == 0) {
                    if (i % 3 == 0) {
                        Car = CAR.BLUE;
                    }
                    if (i % 3 == 1) {
                        Car = CAR.RED;
                    }
                    if (i % 3 == 2) {
                        Car = CAR.GREEN;
                    }
                } else {
                    if (i % 3 == 0) {
                        Car = CAR.RED;
                    }
                    if (i % 3 == 1) {
                        Car = CAR.GREEN;
                    }
                    if (i % 3 == 2) {
                        Car = CAR.BLUE;
                    }
                }

                drawCar(g, tempCar[i]);
            }
            user = true;
            Car = tempCarColor;
            drawCar(g, car);
        }

        public void play() {
            if (upPressed) {
                moveForward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle += 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle -= 0;
                }
            } else {
                moveForward = false;
            }
            if (downPressed) {
                moveBackward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle -= 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle += 0;
                }
            } else {
                moveBackward = false;
            }

            if (tempAngle >= 360) {
                tempAngle = 0;
            }
            if (tempAngle < 0) {
                tempAngle = 359;
            }
            car.setAngle((int) tempAngle);
            if (moveForward) {
                car.moveForward();
            }
            if (moveBackward) {
                car.moveBackward();
            }
            rotationTemps();
            level4InsideCheck();
            level4WonCheck();
        }

        void level4WonCheck() {
            boolean left = (car.getX() - carTempWidth >= 77);
            boolean right = (car.getX() + carTempWidth <= 148);
            boolean up = (car.getY() - carTempHeight >= 252);
            boolean down = (car.getY() + carTempHeight <= 298);
            if (left && right && down && up && (!moveForward && !moveBackward)) {
                gameFinished = true;
                gameWon = true;
            }
        }

        void level4InsideCheck() {
            Rectangle a = new Rectangle();
            mainCar = getRekt(car);
            for (int i = 2; i < 6; i++) {
                a.setBounds(230, i * 50 + 7, 65, 37);
                if (contain(ax, ay, a)) {
                    gameFinished = true;
                    gameWon = false;
                }
            }
            a.setBounds(155, 257, 130, 73);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            a.setBounds(500, 0, 100, 400);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            a.setBounds(0, 300, 400, 100);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            a.setBounds(300, 100, 100, 200);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            if (isOutOfBounds(0, 0, 600, 400)) {
                gameFinished = true;
                gameWon = false;
            }
        }

        boolean isOutOfBounds(double a, double b, double c, double d) {
            if (car.getX() - carTempWidth <= a) {
                return true;
            }
            if (car.getX() + carTempWidth >= c) {
                return true;
            }
            if (car.getY() - carTempHeight <= b) {
                return true;
            }
            if (car.getY() + carTempHeight >= d) {
                return true;
            }
            return false;
        }

        void rotationTemps() {
            double cos = Math.cos(Math.toRadians(tempAngle));
            double sin = Math.sin(Math.toRadians(tempAngle));
            if (tempAngle < 90 && tempAngle >= 0) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 180 && tempAngle >= 90) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 270 && tempAngle >= 180) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 360 && tempAngle >= 270) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            }
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 592, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 392, Short.MAX_VALUE)
            );
        }// </editor-fold>                                  
    }

    //LEVEL 5 Code
    public class Level5 extends javax.swing.JPanel implements Runnable {

        Car car = new Car(450, 350, 37, 65, 0, 5, 15);
        boolean moveForward, moveBackward;
        boolean gameFinished;
        boolean gameWon = false;
        boolean upPressed, downPressed, rightPressed, leftPressed;
        double carTempWidth, carTempHeight;
        Thread looper;
        double secondsLeft = 90;
        Car tempCar[];
        int noOfCars = 6;
        Image grassImg, parkingSlotParkImg, parkingSlotDontParkImg, roadImg, carUserImg;

        public Level5() {
            initComponents();
            initLevel();
            initCars();
        }

        void initCars() {
            tempCar = new Car[noOfCars];
            for (int i = 0; i < 6; i++) {
                if (i != 4) {
                    tempCar[i] = new Car(263, i * 50 + 125, 37, 65, 90, 5, 15);
                }
            }
            tempCar[4] = new Car(50, 50, 37, 65, 90, 5, 15);
        }

        private void initLevel() {
            try {
                ImageIcon grass = new ImageIcon(getClass().getResource("Images\\grass100x100.png"));
                grassImg = grass.getImage();
                ImageIcon parkingSlotPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(Park)75x50.png"));
                parkingSlotParkImg = parkingSlotPark.getImage();
                ImageIcon parkingSlotDontPark = new ImageIcon(getClass().getResource("Images\\ParkingSlot(DontPark)75x50.png"));
                parkingSlotDontParkImg = parkingSlotDontPark.getImage();
                ImageIcon road = new ImageIcon(getClass().getResource("Images\\Road100x100.png"));
                roadImg = road.getImage();
            } catch (Exception e) {
                System.out.println(e);
            }
            gameFinished = false;
            upPressed = downPressed = leftPressed = rightPressed = false;
            moveForward = moveBackward = false;
            tempAngle = 0;
        }

        public synchronized void start() {
            looper = new Thread(this);
            looper.start();
        }

        @Override
        public void run() {
            long timer = System.currentTimeMillis();
            while (!gameFinished) {
                if (System.currentTimeMillis() - timer > 1000) {;
                    secondsLeft--;
                    timer = System.currentTimeMillis();
                }
                if (secondsLeft == 0) {
                    gameFinished = true;
                    gameWon = false;
                }
                repaint();
                play();

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {

                }
            }
            if (gameWon) {
                score += secondsLeft / 90 * 25000;
            }
            State = STATE.ENDSCREEN;
            init();
        }

        void addBackground(Graphics g) {
            for (int i = 0; i < this.getWidth(); i += 100) {
                for (int j = 0; j < this.getHeight() - 100; j += 100) {
                    g.drawImage(roadImg, i, j, 100, 100, null);
                }
            }
            for (int i = 0; i < this.getWidth(); i += 100) {
                for (int j = 100; j < this.getHeight(); j += 100) {
                    if (i == 100 || i == 200 || i == 400) {
                        g.drawImage(roadImg, i, j, 100, 100, null);
                    } else {
                        g.drawImage(grassImg, i, j, 100, 100, null);
                    }
                }
            }

            for (int i = 100; i < this.getHeight(); i += 50) {
                if (i == 300) {
                    g.drawImage(parkingSlotParkImg, 225, i, 75, 50, null);
                } else {
                    g.drawImage(parkingSlotDontParkImg, 225, i, 75, 50, null);
                }
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            level5Paint(g);
            g.setColor(Color.yellow);
            Font font = new Font("Segoe Print", Font.BOLD, 18);
            g.setFont(font);
            g.drawString("Time Left: " + secondsLeft, 420, 50);
            font = new Font("Segoe Print", Font.BOLD, 24);
            g.setFont(font);
            g.drawString("Level 5", 20, 50);
        }

        void level5Paint(Graphics g) {
            addBackground(g);
            CAR tempCarColor = Car;

            for (int i = 0; i < noOfCars; i++) {
                int temp = 0;
                if (i % 3 == 0) {
                    temp = i;
                }
                user = false;
                if (temp % 2 == 0) {
                    if (i % 3 == 0) {
                        Car = CAR.BLUE;
                    }
                    if (i % 3 == 1) {
                        Car = CAR.RED;
                    }
                    if (i % 3 == 2) {
                        Car = CAR.GREEN;
                    }
                } else {
                    if (i % 3 == 0) {
                        Car = CAR.RED;
                    }
                    if (i % 3 == 1) {
                        Car = CAR.GREEN;
                    }
                    if (i % 3 == 2) {
                        Car = CAR.BLUE;
                    }
                }

                drawCar(g, tempCar[i]);
            }
            user = true;
            Car = tempCarColor;
            drawCar(g, car);
        }

        public void play() {
            if (upPressed) {
                moveForward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle += 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle -= 0;
                }
            } else {
                moveForward = false;
            }
            if (downPressed) {
                moveBackward = true;
                if (rightPressed && !leftPressed) {
                    tempAngle -= Math.toRadians(30);
                } else if (!rightPressed && !leftPressed) {
                    tempAngle -= 0;
                } else if (leftPressed && !rightPressed) {
                    tempAngle += Math.toRadians(30);
                } else if (!leftPressed && !rightPressed) {
                    tempAngle += 0;
                }
            } else {
                moveBackward = false;
            }

            if (tempAngle >= 360) {
                tempAngle = 0;
            }
            if (tempAngle < 0) {
                tempAngle = 359;
            }
            car.setAngle((int) tempAngle);
            if (moveForward) {
                car.moveForward();
            }
            if (moveBackward) {
                car.moveBackward();
            }

            if (tempCar[4].getX() + tempCar[4].getHeight() / 2 >= 0
                    && tempCar[4].getX() - tempCar[4].getHeight() / 2 <= 600) {
                tempCar[4].moveForward();
            } else {
                tempCar[4] = new Car(0, 50, 37, 65, 90, 5, 15);
            }

            rotationTemps();
            level5InsideCheck();
            level5WonCheck();
        }

        void level5WonCheck() {
            boolean left = (car.getX() - carTempWidth >= 227);
            boolean right = (car.getX() + carTempWidth <= 298);
            boolean up = (car.getY() - carTempHeight >= 302);
            boolean down = (car.getY() + carTempHeight <= 348);
            if (left && right && down && up && (!moveForward && !moveBackward)) {
                gameFinished = true;
                gameWon = true;
                System.out.println("You Won");
            }
        }

        void level5InsideCheck() {
            Rectangle a = new Rectangle();
            mainCar = getRekt(car);
            for (int i = 2; i < 8; i++) {
                if (i != 6) {
                    a.setBounds(230, i * 50 + 7, 65, 37);
                    if (contain(ax, ay, a)) {
                        gameFinished = true;
                        gameWon = false;
                    }
                }
            }
            int tempX = (int) tempCar[4].getX() - tempCar[4].getHeight() / 2;
            int tempY = (int) tempCar[4].getY() - tempCar[4].getWidth() / 2;
            a.setBounds(tempX, tempY, 65, 37);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            a.setBounds(500, 100, 100, 500);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            a.setBounds(0, 100, 100, 500);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            a.setBounds(300, 100, 100, 500);
            if (contain(ax, ay, a)) {
                gameFinished = true;
                gameWon = false;
            }
            if (isOutOfBounds(0, 0, 600, 400)) {
                gameFinished = true;
                gameWon = false;
            }
        }

        boolean isOutOfBounds(double a, double b, double c, double d) {
            if (car.getX() - carTempWidth <= a) {
                return true;
            }
            if (car.getX() + carTempWidth >= c) {
                return true;
            }
            if (car.getY() - carTempHeight <= b) {
                return true;
            }
            if (car.getY() + carTempHeight >= d) {
                return true;
            }
            return false;
        }

        void rotationTemps() {
            double cos = Math.cos(Math.toRadians(tempAngle));
            double sin = Math.sin(Math.toRadians(tempAngle));
            if (tempAngle < 90 && tempAngle >= 0) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 180 && tempAngle >= 90) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 270 && tempAngle >= 180) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            } else if (tempAngle < 360 && tempAngle >= 270) {
                if (cos < 0) {
                    cos = -cos;
                }
                if (sin < 0) {
                    sin = -sin;
                }
                carTempWidth = (car.getWidth() * cos + car.getHeight() * sin) / 2;
                carTempHeight = (car.getWidth() * sin + car.getHeight() * cos) / 2;
            }
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 592, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 392, Short.MAX_VALUE)
            );
        }// </editor-fold>                        
                  
    }

    //END SCREEN Code
    public class EndScreen extends javax.swing.JPanel {
        public EndScreen() {
            initComponents();
        }

        public EndScreen(boolean gameWon, int highScore, int score) {
            initComponents();
            if (gameWon) {
                //display winning screen 
                youWonMessage.setVisible(true);
                youLostMessage.setVisible(false);
                retryButton.setVisible(false);
                nextLevelButton.setVisible(true);
                menuButton.setVisible(true);
                dispScore.setText("Score: " + score);
                dispScore.setVisible(true);
            } else {
                //display retry screen
                youWonMessage.setVisible(false);
                youLostMessage.setVisible(true);
                retryButton.setVisible(true);
                nextLevelButton.setVisible(false);
                menuButton.setVisible(true);
                dispScore.setText("Score: " + score);
                dispScore.setVisible(true);
            }
        }
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            retryButton = new javax.swing.JButton();
            nextLevelButton = new javax.swing.JButton();
            menuButton = new javax.swing.JButton();
            youWonMessage = new javax.swing.JLabel();
            dispScore = new javax.swing.JLabel();
            youLostMessage = new javax.swing.JLabel();
            jLabel1 = new javax.swing.JLabel();

            setBackground(new java.awt.Color(0, 200, 150));
            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));
            setLayout(null);

            retryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images\\Retry.png"))); // NOI18N
            retryButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(0, 204, 51), new java.awt.Color(0, 204, 51)));
            retryButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    retryButtonActionPerformed(evt);
                }
            });
            add(retryButton);
            retryButton.setBounds(460, 230, 89, 37);

            nextLevelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/NextLevel.png"))); // NOI18N
            nextLevelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(0, 204, 51), new java.awt.Color(0, 204, 51)));
            nextLevelButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    nextLevelButtonActionPerformed(evt);
                }
            });
            add(nextLevelButton);
            nextLevelButton.setBounds(460, 230, 89, 37);

            menuButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/Menu.png"))); // NOI18N
            menuButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(0, 204, 51), new java.awt.Color(0, 204, 51)));
            menuButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    menuButtonActionPerformed(evt);
                }
            });
            add(menuButton);
            menuButton.setBounds(140, 320, 89, 37);

            youWonMessage.setFont(new java.awt.Font("Segoe Print", 1, 30)); // NOI18N
            youWonMessage.setForeground(new java.awt.Color(255, 255, 0));
            youWonMessage.setText("YOU WON!");
            add(youWonMessage);
            youWonMessage.setBounds(60, 130, 180, 72);

            dispScore.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
            dispScore.setForeground(new java.awt.Color(255, 255, 0));
            dispScore.setText("YOUR SCORE");
            add(dispScore);
            dispScore.setBounds(300, 10, 290, 50);

            youLostMessage.setFont(new java.awt.Font("Segoe Print", 2, 30)); // NOI18N
            youLostMessage.setForeground(new java.awt.Color(255, 255, 0));
            youLostMessage.setText("YOU LOST :(");
            add(youLostMessage);
            youLostMessage.setBounds(50, 130, 210, 70);

            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images\\EndScreenBackGround1.jpg"))); // NOI18N
            jLabel1.setText("jLabel1");
            add(jLabel1);
            jLabel1.setBounds(5, 5, 590, 390);
        }// </editor-fold>                        

        private void retryButtonActionPerformed(java.awt.event.ActionEvent evt) {
            if (null != Level) 
            {
                switch (Level) {
                    case L1:
                        State = STATE.LEVEL1;
                        Level = LEVELS.L1;
                        init();
                        break;
                    case L2:
                        State = STATE.LEVEL2;
                        Level = LEVELS.L2;
                        init();
                        break;
                    case L3:
                        State = STATE.LEVEL3;
                        Level = LEVELS.L3;
                        init();
                        break;
                    case L4:
                        State = STATE.LEVEL4;
                        Level = LEVELS.L4;
                        init();
                        break;
                    case L5:
                        State = STATE.LEVEL5;
                        Level = LEVELS.L5;
                        init();
                        break;
                    default:
                        break;
                }
            }
        }

        private void nextLevelButtonActionPerformed(java.awt.event.ActionEvent evt) {
            if (Level == LEVELS.L1) {
                State = STATE.LEVEL2;
                Level = LEVELS.L2;
                init();
            } else if (Level == LEVELS.L2) {
                State = STATE.LEVEL3;
                Level = LEVELS.L3;
                init();
            } else if (Level == LEVELS.L3) {
                State = STATE.LEVEL4;
                Level = LEVELS.L4;
                init();
            } else if (Level == LEVELS.L4) {
                State = STATE.LEVEL5;
                Level = LEVELS.L5;
                init();
            } else if (Level == LEVELS.L5) {
                State = STATE.CONGRATS;
                Level = LEVELS.L1;
                init();
            }

        }

        private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {
            startScreen = true;
            State = STATE.AREYOUSURE;
            init();
        }

        // Variables declaration - do not modify                     
        private javax.swing.JLabel dispScore;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JButton menuButton;
        private javax.swing.JButton nextLevelButton;
        private javax.swing.JButton retryButton;
        private javax.swing.JLabel youLostMessage;
        private javax.swing.JLabel youWonMessage;
        // End of variables declaration                   
    }

    //EXIT GAMEPROMPT
    public class AreYouSure extends javax.swing.JPanel {
        public AreYouSure() {
            initComponents();
            if (startScreen) {
                jLabel1.setVisible(true);
                quit.setVisible(false);
            } else {
                quit.setVisible(true);
                jLabel1.setVisible(false);
            }
        }
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            noButton = new javax.swing.JButton();
            yesButton = new javax.swing.JButton();
            jLabel1 = new javax.swing.JLabel();
            jLabel2 = new javax.swing.JLabel();
            jLabel3 = new javax.swing.JLabel();
            jLabel4 = new javax.swing.JLabel();
            youSure = new javax.swing.JLabel();
            loseProgess = new javax.swing.JLabel();
            quit = new javax.swing.JLabel();

            setBackground(new java.awt.Color(0, 0, 0));
            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 0), 4, true));
            setLayout(null);

            noButton.setBackground(new java.awt.Color(0, 0, 0));
            noButton.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
            noButton.setForeground(new java.awt.Color(255, 51, 0));
            noButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/No.jpg"))); // NOI18N
            noButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0), 4));
            noButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    noButtonActionPerformed(evt);
                }
            });
            add(noButton);
            noButton.setBounds(340, 230, 100, 60);

            yesButton.setBackground(new java.awt.Color(0, 0, 0));
            yesButton.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
            yesButton.setForeground(new java.awt.Color(255, 51, 0));
            yesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/Yes.jpg"))); // NOI18N
            yesButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 0), 4, true));
            yesButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    yesButtonActionPerformed(evt);
                }
            });
            add(yesButton);
            yesButton.setBounds(160, 230, 100, 60);

            jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
            jLabel1.setForeground(new java.awt.Color(255, 51, 0));
            jLabel1.setText("You Will Lose Progress!");
            add(jLabel1);
            jLabel1.setBounds(160, 130, 280, 90);

            jLabel2.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
            jLabel2.setForeground(new java.awt.Color(255, 51, 0));
            jLabel2.setText("Are You Sure?");
            add(jLabel2);
            jLabel2.setBounds(170, 60, 260, 90);

            jLabel3.setForeground(new java.awt.Color(255, 255, 0));
            jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/DEVIL.jpg"))); // NOI18N
            jLabel3.setText("jLabel3");
            add(jLabel3);
            jLabel3.setBounds(450, 10, 140, 140);

            jLabel4.setForeground(new java.awt.Color(255, 255, 0));
            jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/DEVIL.jpg"))); // NOI18N
            jLabel4.setText("jLabel4");
            add(jLabel4);
            jLabel4.setBounds(450, 250, 140, 140);

            youSure.setForeground(new java.awt.Color(255, 255, 0));
            youSure.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/DEVIL.jpg"))); // NOI18N
            youSure.setText("jLabel4");
            add(youSure);
            youSure.setBounds(10, 10, 140, 140);

            loseProgess.setForeground(new java.awt.Color(255, 255, 0));
            loseProgess.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/DEVIL.jpg"))); // NOI18N
            loseProgess.setText("jLabel4");
            add(loseProgess);
            loseProgess.setBounds(10, 250, 140, 140);

            quit.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
            quit.setForeground(new java.awt.Color(255, 51, 0));
            quit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            quit.setText("You want to Quit?");
            add(quit);
            quit.setBounds(160, 150, 290, 50);
        }// </editor-fold>                        

        private void yesButtonActionPerformed(java.awt.event.ActionEvent evt) {
            if (startScreen) {
                State = STATE.OUTLINE;
                init();
            } else {
                this.setVisible(false);
            };
        }

        private void noButtonActionPerformed(java.awt.event.ActionEvent evt) {
            if (startScreen) {
                State = STATE.ENDSCREEN;
                init();
            } else {
                State = STATE.OUTLINE;
                init();
            };
        }

        // Variables declaration - do not modify                     
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel loseProgess;
        private javax.swing.JButton noButton;
        private javax.swing.JLabel quit;
        private javax.swing.JButton yesButton;
        private javax.swing.JLabel youSure;
        // End of variables declaration                   
    }

    //HIGH SCORES SCREEN
    public class HighScores extends javax.swing.JPanel {
        public HighScores() {
            initComponents();
            
        }
        
        public void paint(Graphics g) {
            super.paint(g);
            int i = 1;
            Font f = new Font("Segoe Print", Font.BOLD, 20);
            for(Map.Entry<String, String> entry : highScoresArray.entrySet()) {
                g.setColor(Color.yellow);
                g.setFont(f);
                g.drawString(i + ") " + entry.getValue() + " -> " + entry.getKey(), 350, i*50 + 100);
                i++;
            }
        }
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            highScoreText = new javax.swing.JLabel();
            backButton = new javax.swing.JButton();
            jLabel1 = new javax.swing.JLabel();
            highScroresBackground = new javax.swing.JLabel();

            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));
            setLayout(null);

            highScoreText.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
            highScoreText.setForeground(new java.awt.Color(255, 255, 0));
            highScoreText.setText("HIGH SCORES:");
            add(highScoreText);
            highScoreText.setBounds(40, 60, 290, 90);

            backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images\\BackButton.png"))); // NOI18N
            backButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    backButtonActionPerformed(evt);
                }
            });
            add(backButton);
            backButton.setBounds(200, 230, 120, 50);

            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/HighScoresBack.jpg"))); // NOI18N
            add(jLabel1);
            jLabel1.setBounds(5, 5, 590, 390);
            add(highScroresBackground);
            highScroresBackground.setBounds(350,150, 250, 250);
        }// </editor-fold>                        

        private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
            State = STATE.OUTLINE;
            init();
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton backButton;
        private javax.swing.JLabel highScoreText;
        private javax.swing.JLabel highScroresBackground;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   
    }

    //CAR SELECTION SCREEN
    public class CarSelect extends javax.swing.JPanel {
        public CarSelect() {
            initComponents();
            blueCar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));
            greenCar.setBorder(new javax.swing.border.LineBorder(new Color(255, 255, 0), 0, false));
            redCar.setBorder(new javax.swing.border.LineBorder(new Color(255, 255, 0), 0, false));
        }
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            blueCar = new javax.swing.JButton();
            greenCar = new javax.swing.JButton();
            redCar = new javax.swing.JButton();
            backButton = new javax.swing.JButton();
            colorSelect = new javax.swing.JLabel();
            jLabel2 = new javax.swing.JLabel();
            jLabel1 = new javax.swing.JLabel();

            setLayout(null);

            blueCar.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images\\BlueCar.png"))); // NOI18N
            blueCar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));
            blueCar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    blueCarActionPerformed(evt);
                }
            });
            add(blueCar);
            blueCar.setBounds(60, 70, 120, 214);

            greenCar.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images\\GreenCar.png"))); // NOI18N
            greenCar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    greenCarActionPerformed(evt);
                }
            });
            add(greenCar);
            greenCar.setBounds(240, 70, 120, 214);

            redCar.setIcon(new javax.swing.ImageIcon(getClass().getResource("Images\\RedCar.png"))); // NOI18N
            redCar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    redCarActionPerformed(evt);
                }
            });
            add(redCar);
            redCar.setBounds(420, 70, 120, 214);

            backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/BackButton.png"))); // NOI18N
            backButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    backButtonActionPerformed(evt);
                }
            });
            add(backButton);
            backButton.setBounds(0, 350, 120, 50);

            colorSelect.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
            colorSelect.setForeground(new java.awt.Color(255, 255, 0));
            colorSelect.setText("Blue Car");
            add(colorSelect);
            colorSelect.setBounds(290, 310, 220, 60);

            jLabel2.setFont(new java.awt.Font("Segoe Print", 1, 48)); // NOI18N
            jLabel2.setForeground(new java.awt.Color(255, 255, 0));
            jLabel2.setText("Select A Car!");
            add(jLabel2);
            jLabel2.setBounds(130, 10, 380, 50);

            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/HighScoresBack.jpg"))); // NOI18N
            add(jLabel1);
            jLabel1.setBounds(0, 0, 600, 400);
        }// </editor-fold>                        

        private void blueCarActionPerformed(java.awt.event.ActionEvent evt) {
            Car = CAR.BLUE;
            blueCar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));
            greenCar.setBorder(new javax.swing.border.LineBorder(new Color(255, 255, 0), 0, false));
            redCar.setBorder(new javax.swing.border.LineBorder(new Color(255, 255, 0), 0, false));
            colorSelect.setText("Blue Car");
        }

        private void greenCarActionPerformed(java.awt.event.ActionEvent evt) {
            Car = CAR.GREEN;
            greenCar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));
            blueCar.setBorder(new javax.swing.border.LineBorder(new Color(255, 255, 0), 0, false));
            redCar.setBorder(new javax.swing.border.LineBorder(new Color(255, 255, 0), 0, false));
            colorSelect.setText("Green Car");
        }

        private void redCarActionPerformed(java.awt.event.ActionEvent evt) {
            Car = CAR.RED;
            redCar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));
            blueCar.setBorder(new javax.swing.border.LineBorder(new Color(255, 255, 0), 0, false));
            greenCar.setBorder(new javax.swing.border.LineBorder(new Color(255, 255, 0), 0, false));
            colorSelect.setText("Red Car");
        }

        private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
            State = STATE.OUTLINE;
            init();
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton backButton;
        private javax.swing.JButton blueCar;
        private javax.swing.JLabel colorSelect;
        private javax.swing.JButton greenCar;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JButton redCar;
        // End of variables declaration                   
    }

    //CONGRATS SCREEN
    public class Congrats extends javax.swing.JPanel {
        public Congrats() {
            initComponents();

            yourScore.setText("Score: " + score);
            
            if (score >= highScore) {
                highScore = score;
                highScoreDisp.setText("High Score: " + highScore);
                thisIsHS.setVisible(true);
            } else {
                highScoreDisp.setText("High Score: " + highScore);
                //thisIsHS.setVisible(true);
                thisIsHS.setVisible(false);
            }
        }
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            yourScore = new javax.swing.JLabel();
            highScoreDisp = new javax.swing.JLabel();
            thisIsHS = new javax.swing.JLabel();
            backButton = new javax.swing.JButton();
            jLabel1 = new javax.swing.JLabel();

            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 4, true));
            setLayout(null);

            yourScore.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
            yourScore.setForeground(new java.awt.Color(255, 255, 0));
            yourScore.setText("Score: 3912893");
            add(yourScore);
            yourScore.setBounds(370, 280, 210, 40);

            highScoreDisp.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
            highScoreDisp.setForeground(new java.awt.Color(255, 255, 0));
            highScoreDisp.setText("High Score: 8348229");
            add(highScoreDisp);
            highScoreDisp.setBounds(20, 280, 280, 40);

            thisIsHS.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
            thisIsHS.setForeground(new java.awt.Color(255, 255, 0));
            thisIsHS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            thisIsHS.setText("This is a HIGH SCORE!!!");
            add(thisIsHS);
            thisIsHS.setBounds(60, 10, 490, 50);

            backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/BackButton.png"))); // NOI18N
            backButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    backButtonActionPerformed(evt);
                }
            });
            add(backButton);
            backButton.setBounds(5, 345, 120, 50);

            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/game/Images/YouWin2.jpg"))); // NOI18N
            add(jLabel1);
            jLabel1.setBounds(5, 5, 590, 390);
        }// </editor-fold>                        

        private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
            State = STATE.OUTLINE;
            init();
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton backButton;
        private javax.swing.JLabel highScoreDisp;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel thisIsHS;
        private javax.swing.JLabel yourScore;
        // End of variables declaration                   
    }
	
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void keyPressed(KeyEvent e) {
        if (State == STATE.LEVEL1) {
            if (e.getKeyCode() == e.VK_UP) {
                level1.upPressed = true;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level1.downPressed = true;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level1.leftPressed = true;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level1.rightPressed = true;
            }
        } else if (State == STATE.LEVEL2) {
            if (e.getKeyCode() == e.VK_UP) {
                level2.upPressed = true;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level2.downPressed = true;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level2.leftPressed = true;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level2.rightPressed = true;
            }
        } else if (State == STATE.LEVEL3) {
            if (e.getKeyCode() == e.VK_UP) {
                level3.upPressed = true;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level3.downPressed = true;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level3.leftPressed = true;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level3.rightPressed = true;
            }
        } else if (State == STATE.LEVEL4) {
            if (e.getKeyCode() == e.VK_UP) {
                level4.upPressed = true;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level4.downPressed = true;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level4.leftPressed = true;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level4.rightPressed = true;
            }
        } else if (State == STATE.LEVEL5) {
            if (e.getKeyCode() == e.VK_UP) {
                level5.upPressed = true;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level5.downPressed = true;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level5.leftPressed = true;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level5.rightPressed = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (State == STATE.LEVEL1) {
            if (e.getKeyCode() == e.VK_UP) {
                level1.upPressed = false;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level1.downPressed = false;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level1.leftPressed = false;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level1.rightPressed = false;
            }
        } else if (State == STATE.LEVEL2) {
            if (e.getKeyCode() == e.VK_UP) {
                level2.upPressed = false;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level2.downPressed = false;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level2.leftPressed = false;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level2.rightPressed = false;
            }
        } else if (State == STATE.LEVEL3) {
            if (e.getKeyCode() == e.VK_UP) {
                level3.upPressed = false;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level3.downPressed = false;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level3.leftPressed = false;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level3.rightPressed = false;
            }
        } else if (State == STATE.LEVEL4) {
            if (e.getKeyCode() == e.VK_UP) {
                level4.upPressed = false;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level4.downPressed = false;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level4.leftPressed = false;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level4.rightPressed = false;
            }
        } else if (State == STATE.LEVEL5) {
            if (e.getKeyCode() == e.VK_UP) {
                level5.upPressed = false;
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                level5.downPressed = false;
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                level5.leftPressed = false;
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                level5.rightPressed = false;
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
