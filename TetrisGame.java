import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;


public class TetrisGame extends JFrame implements WindowListener{

    public GamePanel gamePanel = null;

    public boolean isCompletedOneRow = false;

    public boolean isCompletedTwoRow = false;

    public boolean isCompletedThreeRow = false;

    public boolean isCompletedFourRow = false;

    public boolean isRunning = false;

    public boolean isPaused = false;

    public boolean isGameFinished = false;

    public boolean isPointIncreased = false;

    public int playerPoint = 0;

    public int playerLevel = 1;

    public double gameSpeed = 100;

    public int bestScore = 0;

    public int completedRow = 0;

    public int minuteCount = 0;

    public String minuteStr = "00";

    public int timeCounter = 1;

    public String secondStr = "00";

    public int secondCount = 0;

    public int time = 500;

    public LeftPanel leftPanel = null;

    public RightPanel rightPanel = null;

    public static ArrayList<SquarePiece> squareList = null;

    public HashSet<Integer> pointControlSet = new HashSet<>();

    public ReentrantLock gameLock = new ReentrantLock();

    public PopUpScreen popUpScreen = null;

    public ExitPopUpScrren exitScreen = null;

    public RestartPopUpScrren restartScreen = null;

    public TetrisGame(){

        this.setLocationRelativeTo(this);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.setSize(1000, 1000);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (dim.width - this.getWidth()) / 2;
        int y = (dim.height - this.getHeight()) / 2;
        this.setLocation(x, y-40);

        this.addWindowListener(this);

        squareList = new ArrayList<>();

        leftPanel = new LeftPanel();
        leftPanel.setPreferredSize(new Dimension(250, 1000));
        leftPanel.setBackground(Color.BLACK);
        rightPanel = new RightPanel();
        rightPanel.setPreferredSize(new Dimension(250, 1000));
        rightPanel.setBackground(Color.BLACK);

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(500,1000));

        this.popUpScreen = new PopUpScreen();

        this.restartScreen = new RestartPopUpScrren();

        this.exitScreen = new ExitPopUpScrren();

        this.add(leftPanel,BorderLayout.WEST);
        this.add(rightPanel,BorderLayout.EAST);
        this.add(gamePanel,BorderLayout.CENTER);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();

    }

    public void startGame(){

        if(isRunning == false){

            isRunning = true;

            gamePanel.gameThread.start();

        }

    }

    public class PopUpScreen extends JFrame{

        public Button yesButton = null, noButton = null;

        public PopUpScreen(){

            this.setLayout(null);

            yesButton = new Button("YES",Color.GREEN);
            this.setTitle(" TETRIS GAME ");
            yesButton.setBounds(40,160,80,30);
            noButton = new Button("NO", Color.red);
            noButton.setBounds(150,160,80,30);

            this.add(yesButton);
            this.add(noButton);

            this.setLocationRelativeTo(TetrisGame.this.gamePanel);
            this.setBounds(400,500,300,250);

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            int x = (dim.width - this.getWidth()) / 2;
            int y = (dim.height - this.getHeight()) / 2;
            this.setLocation(x, y);

        }

        public void paint(Graphics g){

            super.paint(g);

            g.setColor(Color.RED);

            g.draw3DRect(30,60,220,40,true);

            g.setColor(Color.green);

            g.draw3DRect(30,110,220,40,true);

            g.setColor(Color.BLACK);

            g.setFont(new Font("",Font.BOLD,15));

            g.drawString(" Your score     :     "+playerPoint,40,135);

            g.setColor(Color.BLACK);

            g.setFont(new Font("",Font.BOLD,15));

            g.drawString("Game is Finished !!!!",50,85);

            g.drawString("    Do you want to exit ", 50,175);

        }

    }

    public class ExitPopUpScrren extends JFrame{

        public Button yesButton = null, noButton = null;

        public ExitPopUpScrren(){

            this.setTitle(" TETRIS GAME ");

            this.setLayout(null);

            this.setLayout(null);
            this.setLocationRelativeTo(TetrisGame.this.gamePanel);
            this.setBounds(400,500,300,250);

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            int x = (dim.width - this.getWidth()) / 2;
            int y = (dim.height - this.getHeight()) / 2;
            this.setLocation(x, y);

            yesButton = new Button("YES",Color.GREEN);
            this.setTitle(" TETRIS GAME ");
            yesButton.setBounds(40,160,80,30);
            noButton = new Button("NO", Color.red);
            noButton.setBounds(150,160,80,30);

            this.add(yesButton);
            this.add(noButton);

        }
        
        public void paint(Graphics g){

            super.paint(g);
            
            g.setColor(Color.RED);

            g.draw3DRect(30,50,240,50,true);

            g.setColor(Color.green);

            g.draw3DRect(30,110,220,40,true);

            g.setColor(Color.BLACK);

            g.setFont(new Font("",Font.BOLD,15));

            g.drawString(" Exiting Screen          ",40,135);

            g.setColor(Color.BLACK);

            g.setFont(new Font("",Font.BOLD,15));

            g.drawString("   Game is not finished",50,70);
            g.drawString("Your progress will not be saved",40,90);

            g.drawString("    Do you want to exit ", 50,175);

        }

    }


    public class RestartPopUpScrren extends JFrame{

        public Button yesButton = null, noButton = null;

        public RestartPopUpScrren(){

            this.setTitle(" TETRIS GAME ");

            this.setLayout(null);

            this.setLayout(null);
            this.setLocationRelativeTo(TetrisGame.this.gamePanel);
            this.setBounds(400,500,300,250);

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            int x = (dim.width - this.getWidth()) / 2;
            int y = (dim.height - this.getHeight()) / 2;
            this.setLocation(x, y);

            yesButton = new Button("YES",Color.GREEN);
            this.setTitle(" TETRIS GAME ");
            yesButton.setBounds(40,160,80,30);
            noButton = new Button("NO", Color.red);
            noButton.setBounds(150,160,80,30);

            this.add(yesButton);
            this.add(noButton);

        }
        
        public void paint(Graphics g){

            super.paint(g);
            
            g.setColor(Color.RED);

            g.draw3DRect(30,50,240,50,true);

            g.setColor(Color.green);

            g.draw3DRect(30,110,220,40,true);

            g.setColor(Color.BLACK);

            g.setFont(new Font("",Font.BOLD,15));

            g.drawString(" Restart Screen          ",40,135);

            g.setColor(Color.BLACK);

            g.setFont(new Font("",Font.BOLD,15));

            g.drawString("   Game is not finished",50,70);
            g.drawString("Your progress will not be saved",40,90);

            g.drawString("Do you want to restart the game ? ", 40,175);

        }

    }



    public void saveBestScore(){

        try{

            PrintWriter printWriter = new PrintWriter(new File("gameInfo.txt"));
            printWriter.println(bestScore);
            printWriter.close();

        }
        catch( Exception e){

        }

    }

    public int getBestScore(){

        int best = 0;

        try{

            Scanner scanner = new Scanner(new File("gameInfo.txt"));
            best = scanner.nextInt();
            scanner.close();

        }
        catch(Exception e){

        }

        return best;

    }


    public class GamePanel extends JPanel implements KeyListener{

        public Shape currentShape = null;

        public Color randomColors[] = null;

        public int randomX[] = null;

        public String randomShapeType[] = null;

        public GameThread gameThread = null;

        public BufferedImage image = null;

        public GamePanel (){

            this.addKeyListener(this);
            this.setFocusable(true);
            this.setBackground(Color.DARK_GRAY);

            randomColors = new Color[]{Color.RED,Color.GREEN,Color.YELLOW,Color.ORANGE,Color.PINK,Color.CYAN,Color.MAGENTA};

            randomShapeType = new String[] {"square","rectangle","leftZ","rightZ","leftL","rightL","T"};

            randomX = new int[] {100,150,200,250,300,350,350};

            gameThread = new GameThread();

            try {
                image = ImageIO.read(new File("paused.jpg.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void paintComponent(Graphics g){

            super.paintComponent(g);

            drawTheLines(g);

            if(currentShape != null)
            drawShape(currentShape, g);

           for(int i=0; i<squareList.size(); i++){

             SquarePiece temp = squareList.get(i);

             g.setColor(temp.squareColor);

             g.fill3DRect(temp.x,temp.y,temp.width,temp.height, true);

           }

           if(isPaused){
            g.drawImage(image,25,250,this);
           }


        }

        public void drawTheLines(Graphics g){

            int width = 50;

            for(int i=0; i<=10; i++){

                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(width,0,width,1000);

                width+=50;

                
            }

            width = 50;

            for(int i=0; i<=20; i++){

                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0,width,500,width);

                width+=50;

                
            }

        }

        public void drawShape(Shape shape, Graphics g){

            g.setColor(shape.shapeColor);

            for(int i=0; i<shape.squares.length; i++){

                g.fill3DRect(shape.squares[i].x,shape.squares[i].y,shape.squares[i].width,shape.squares[i].height,true);

            }

        }


        public boolean borderControl(Shape shape){

            int startX = 0, startY = 0;

            for(int i=0; i<shape.squares.length; i++){

                startX = shape.squares[i].x;

                startY = shape.squares[i].y;

                if( startX < 0 || startX > 450 || startY<0 || startY > 950) return false;

            }

            return true;

        }

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();

            boolean control = false;

            if(keyCode == KeyEvent.VK_DOWN){

                currentShape.moveDown();

                control =borderControl(currentShape);

                if(control == false || currentShape.isIntersect()) currentShape.moveUp();


            }

            else if(keyCode == KeyEvent.VK_LEFT){

                currentShape.moveLeft();

                control =borderControl(currentShape);

                if(control == false || currentShape.isIntersect()) currentShape.moveRight();

            }

            else if(keyCode == KeyEvent.VK_RIGHT){

                currentShape.moveRight();

                control =borderControl(currentShape);

                if(control == false || currentShape.isIntersect()) currentShape.moveLeft();


            }

            else if(keyCode == KeyEvent.VK_A){

                currentShape.turnClockWise90();

                control = borderControl(currentShape);

                if(control == false || currentShape.isIntersect()) currentShape.turnAntiClockWise90(); 

            }

            else if(keyCode == KeyEvent.VK_D){

                currentShape.turnAntiClockWise90();

                control = borderControl(currentShape);

                if(control == false || currentShape.isIntersect()) currentShape.turnClockWise90(); 

            }

            this.repaint();

        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }

        public class GameThread extends Thread{

            public void run(){

                Timer timer = new Timer();

                TimerTask task = new TimerTask() {

                    public void run() {

                        if(secondCount <= 9) secondStr = "0"+secondCount;

                        else secondStr = ""+secondCount;

                        if(minuteCount <= 9) minuteStr = "0"+minuteCount;

                        else minuteStr = ""+minuteCount;

                        secondCount++;

                        if(secondCount >= 60){

                            secondCount = 0;
                            minuteCount++;

                        }
                    }
                };
                timer.schedule(task, 0, 1000);

                currentShape = giveShape();

                long start =0, end = 0;

                start = System.currentTimeMillis();

                leftPanel.leftPanelMiniShape = currentShape.makeShapeMini();

                bestScore = getBestScore();

                pointControlSet.add(0);

                while(!isGameFinished){

                    repaint();

                    gameLock.lock();

                    if(isPointIncreased && !pointControlSet.contains(playerPoint / 1000)) {

                        pointControlSet.add(playerPoint / 1000);
                        playerLevel++;
                        time = (-50 * playerLevel + 600);
                        gameSpeed = 50 /( (double)time /1000);
                        isPointIncreased = false;

                    }
                    

                    if(playerPoint >= bestScore) bestScore = playerPoint;

                    currentShape.moveDown();

                    if( currentShape.startY >=50 && borderControl(currentShape) == false || currentShape.isIntersect()){

                        currentShape.moveUp();

                        squareList.add(currentShape.squares[0]);
                        squareList.add(currentShape.squares[1]);
                        squareList.add(currentShape.squares[2]);
                        squareList.add(currentShape.squares[3]);

                        makeControl();

                        currentShape = giveShape();

                        leftPanel.leftPanelMiniShape = currentShape.makeShapeMini();

                    }

                    

                    for(int i=0; i<squareList.size(); i++){

                       if(squareList.get(i).y <= 0){

                          saveBestScore();

                          isGameFinished = true;

                          popUpScreen.setVisible(true);

                          break;

                        }

                   }   

                    repaint();

                    leftPanel.repaint();

                    rightPanel.repaint();
                  
                    gameLock.unlock();


                   try{
                        Thread.sleep(time);
                    }
                    catch(Exception e){

                    }

                    

                }


            }


            public void makeControl(){

                int numFirstRow = 0, numSecondRow =0, numThirdRow =0, numFourtRow =0; 

                    for(int i=0; i<squareList.size(); i++){

                        if(squareList.get(i).y == 950) numFirstRow++;

                        else if(squareList.get(i).y == 900) numSecondRow++;

                        else  if(squareList.get(i).y == 850) numThirdRow++;

                        else  if(squareList.get(i).y == 800) numFourtRow++;


                    }

                    if(numFirstRow == 10 && numSecondRow == 10 && numThirdRow == 10 && numFourtRow == 10){

                        for(int i=0; i<squareList.size(); i++){

                            if(squareList.get(i).y == 950){
                                squareList.remove(i);
                                i--;
                            }

                            else if(squareList.get(i).y == 900){
                                squareList.remove(i);
                                i--;
                            }
                            else if(squareList.get(i).y == 850){
                                squareList.remove(i);
                                i--;
                            }
                            else if(squareList.get(i).y == 800){
                                squareList.remove(i);
                                i--;
                            }

                        }

                        for(int i=0; i<squareList.size(); i++){
                            squareList.get(i).y+=200;
                        }

                        completedRow+=4;
                        playerPoint+=800;
                        isPointIncreased = true;
                        isCompletedFourRow = true;

                    }

                    else if(numFirstRow == 10 && numSecondRow == 10 && numThirdRow == 10){

                        for(int i=0; i<squareList.size(); i++){

                            if(squareList.get(i).y == 950){
                                squareList.remove(i);
                                i--;
                            }

                            else if(squareList.get(i).y == 900){
                                squareList.remove(i);
                                i--;
                            }
                            else if(squareList.get(i).y == 850){
                                squareList.remove(i);
                                i--;
                            }
                            
                        }

                        for(int i=0; i<squareList.size(); i++){
                            squareList.get(i).y+=150;
                        }
                        
                        completedRow+=3;
                        playerPoint+=500;
                        isPointIncreased = true;
                        isCompletedThreeRow = true;

                    }

                    else if(numFirstRow == 10 && numSecondRow == 10){

                        for(int i=0; i<squareList.size(); i++){

                            if(squareList.get(i).y == 950){
                                squareList.remove(i);
                                i--;
                            }

                            else if(squareList.get(i).y == 900){
                                squareList.remove(i);
                                i--;
                            }
                           
                        }

                        for(int i=0; i<squareList.size(); i++){
                            squareList.get(i).y+=100;
                        }
                        
                        completedRow+=2;
                        playerPoint+=300;
                        isPointIncreased = true;
                        isCompletedTwoRow = true;

                    }

                    else if (numFirstRow == 10){

                        for(int i=0; i<squareList.size(); i++){

                            if(squareList.get(i).y == 950){
                                squareList.remove(i);
                                i--;
                            }

                        }

                        for(int i=0; i<squareList.size(); i++){
                            squareList.get(i).y+=50;
                        }

                        completedRow++;
                        playerPoint+=100;
                        isPointIncreased = true;
                        isCompletedOneRow = true;

                    }

            }


            public Shape giveShape (){

                Color randomColor = null;

                 int randomColorIndex = 0;

                 String  type = null;

                int randomTypeIndex = 0;

                int randomIndex = 0;

                int startX = 0, startY = -200;

                randomColorIndex = ((int) ( 7*Math.random()));

                randomTypeIndex = ((int) ( 7*Math.random()));

                randomIndex = ((int) (7 *Math.random()));

                startX = randomX[randomIndex];

                type = randomShapeType[randomTypeIndex];

                randomColor = randomColors[randomColorIndex];

                if(type.equals("square")) return new Square(startX,startY,randomColor);

                if(type.equals("rectangle")) return new LineRectangle(startX, startY, randomColor);

                if(type.equals("leftZ")) return new LeftZ(startX, startY, randomColor);

                if(type.equals("rightZ")) return new RightZ(startX, startY, randomColor);

                if(type.equals("leftL")) return new LeftL(startX, startY, randomColor);

                if(type.equals("rightL")) return new RightL(startX, startY, randomColor);

                else return new NormalT(startX, startY, randomColor);
            }


        }



    }


    public class Button extends JButton implements ActionListener{

        public Color buttonColor = null;

        public Button (String message){

            super(message);

            this.buttonColor = Color.WHITE;

            this.addActionListener(this);

        }

        public Button (String message, Color buttonColor){

            this(message);

            this.buttonColor = buttonColor;

            this.setBackground(buttonColor);



        }

        public void actionPerformed(ActionEvent event){

            if(this.getText().equals("START")){

                TetrisGame.this.startGame();

                this.buttonColor = Color.ORANGE;

                this.setBackground(buttonColor);

                this.setText("Restart");

                gamePanel.requestFocusInWindow(); // so important code

            }

            else if(this.getText().equals("Restart")){

                TetrisGame.this.restartScreen.setVisible(true);

            }

            else if(this.getText().equals("Pause")){

                gamePanel.repaint();

                isPaused = true;

                gameLock.lock();

                this.buttonColor = Color.cyan;

                this.setBackground(buttonColor);

                this.setText("Resume");

            }
            else if(this.getText().equals("Resume")){

                isPaused = false;

                gameLock.unlock();

                this.buttonColor = Color.RED;

                this.setBackground(buttonColor);

                this.setText("Pause");

                gamePanel.requestFocusInWindow(); // so important code

            }

            else if(this.getText().equals("YES")){

                if(TetrisGame.this.restartScreen.isVisible()){

                    isGameFinished = true;

                    TetrisGame t = new TetrisGame();

                    TetrisGame.this.restartScreen.setVisible(false);

                    TetrisGame.this.dispose();

                }
                else{

                    TetrisGame.this.dispose();
                    System.exit(0);

                }

                

            }

            else if(this.getText().equals("NO")){

                if(TetrisGame.this.popUpScreen.isVisible())
                   TetrisGame.this.popUpScreen.setVisible(false);
                
                else if(TetrisGame.this.exitScreen.isVisible())   
                   TetrisGame.this.exitScreen.setVisible(false);

                else if(TetrisGame.this.restartScreen.isVisible())
                   TetrisGame.this.restartScreen.setVisible(false);

            }

            

        }


    }

    public class LeftPanel extends JPanel{

        public Color panelColor = null;

        public Shape leftPanelMiniShape = null;

        public JLabel playerPointLabel = null;

        public JPanel playerPointPanel = null;

        public JLabel playerLevelLabel = null;

        public JPanel playerLevelPanel = null;

        public JLabel miniShapeLabel = null;

        public JPanel miniShapePanel = null;

        public JLabel pictureLabel = null;


        public LeftPanel (){

            this.setLayout(null);

            ImageIcon imageIcon = new ImageIcon("tetriseffect_featured.jpg");

            pictureLabel = new JLabel(imageIcon);

            pictureLabel.setBounds(20,840,200,140);

            Image image = imageIcon.getImage();

            Image scaledImage = image.getScaledInstance(200,140,java.awt.Image.SCALE_SMOOTH);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            pictureLabel.setIcon(scaledIcon);

            this.add(pictureLabel);

            playerPointPanel = new JPanel();
            playerPointPanel.setBackground(Color.gray);
            playerPointPanel.setBounds(30, 100,120,40);

            playerLevelPanel = new JPanel();
            playerLevelPanel.setBackground(Color.gray);
            playerLevelPanel.setBounds(30, 200, 120,40);

            miniShapePanel = new JPanel();
            miniShapePanel.setBackground(Color.CYAN);
            miniShapePanel.setBounds(25,300,200,40);

            miniShapeLabel = new JLabel("Current Shape Notation ");
            miniShapeLabel.setBounds(0,10,100,20);
            miniShapeLabel.setFont(new Font("Current Shape Notation ", Font.CENTER_BASELINE,15));

            miniShapePanel.add(miniShapeLabel);

            playerLevelLabel = new JLabel("Player level : ");

            playerLevelLabel.setBounds(0,10,100,20);
            playerLevelLabel.setBackground(Color.white);
            playerLevelLabel.setFont(new Font("Player level : ", Font.BOLD, 15));
            
            playerLevelPanel.add(playerLevelLabel,BorderLayout.CENTER);

            playerPointLabel = new JLabel("Player point : ");

            playerPointLabel.setBounds(0,10,100,20);
            playerPointLabel.setBackground(Color.white);
            playerPointLabel.setFont(new Font("Player point : ", Font.BOLD, 15));

            playerPointPanel.add(playerPointLabel,BorderLayout.CENTER);

            this.add(playerPointPanel);
            this.add(playerLevelPanel);
            this.add(miniShapePanel);

            bestScore = getBestScore(); 
        

        }

        public void paintComponent(Graphics g){

            super.paintComponent(g);

            g.setColor(Color.ORANGE);

            g.fill3DRect(22,16,208,54,true);

            g.setFont(new Font("", Font.LAYOUT_LEFT_TO_RIGHT,20));

            g.setColor(Color.DARK_GRAY);

            g.drawString("Tetris Game",60,48);

            g.setColor(Color.red);

            g.setFont(new Font(""+playerPoint,Font.BOLD,12));

            g.draw3DRect(24,94,200,52,true);

            g.setColor(Color.ORANGE);

            g.draw3DRect(24,194,200,50,true);

            g.setColor(Color.gray);

            g.draw3DRect(19,294,210,50,true);

            g.setColor(Color.white);

            g.drawString(""+playerPoint,160,125);

            g.drawString(""+playerLevel, 160,225);

            g.setColor(Color.GREEN);

            g.draw3DRect(20,640,210,50,true);

            g.setColor(Color.WHITE);

            g.setFont(new Font("",Font.BOLD,15));

            g.drawString("  Max Score      :     "+bestScore,30,670);

            g.setColor(Color.BLUE);

            g.draw3DRect(20,700,210,50,true);

            g.setColor(Color.WHITE);

            g.setFont(new Font("",Font.BOLD,15));

            g.drawString("  Game speed  :     "+(int)gameSpeed,30,730);

            g.setColor(Color.RED);

            g.draw3DRect(20,760,210,50,true);

            g.setColor(Color.WHITE);

            g.setFont(new Font("",Font.BOLD,15));

            g.drawString(" Completed rows  :     "+completedRow,30,787);

            if(leftPanelMiniShape != null){

                g.setColor(leftPanelMiniShape.shapeColor);

                g.drawLine(24,380,220,380);
                
                if(leftPanelMiniShape instanceof LineRectangle) {

                    g.drawLine(24,620,220,620);

                    g.drawLine(24,380,24,620);

                    g.drawLine(220,380,220,620);
                
                }

                else if(leftPanelMiniShape instanceof RightL || leftPanelMiniShape instanceof LeftL){

                    g.drawLine(24,570,220,570);

                    g.drawLine(24,380,24,570);

                    g.drawLine(220,380,220,570);

                } 

                else{

                    g.drawLine(24,520,220,520);

                    g.drawLine(24,380,24,520);

                    g.drawLine(220,380,220,520);

                } 



                g.setColor(leftPanelMiniShape.shapeColor);

                for(int i=0; i<leftPanelMiniShape.squares.length; i++)
                   g.fill3DRect(leftPanelMiniShape.squares[i].x, leftPanelMiniShape.squares[i].y, leftPanelMiniShape.squares[i].width,leftPanelMiniShape.squares[i].height,true);

            }
                 
        }

    }

    public class RightPanel extends JPanel{

        public Color paneColor = null;

        public Button pauseResumeButton = null;

        public Button startButton = null;

        public JLabel pictureLabel = null;

        public RightPanel(){

            this.setLayout(null);

            ImageIcon imageIcon = new ImageIcon("ocr.jpg");

            pictureLabel = new JLabel(imageIcon);

            pictureLabel.setBounds(25,300,200,400);

            Image image = imageIcon.getImage();

            Image scaledImage = image.getScaledInstance(200,400,java.awt.Image.SCALE_SMOOTH);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            pictureLabel.setIcon(scaledIcon);

            //this.add(pictureLabel);

            startButton = new Button("START",Color.GREEN);

            startButton.setBounds(50,100,150,50);

            pauseResumeButton = new Button("Pause",Color.red);

            pauseResumeButton.setBounds(50,200,150,50);

            this.add(startButton);

            this.add(pauseResumeButton);

        }

        public void paintComponent(Graphics g){

            super.paintComponent(g);

            g.fill3DRect(25,300,200,400,true);

            g.setColor(Color.DARK_GRAY);

            g.setFont(new Font("", Font.BOLD, 25));

            if(isCompletedOneRow || isCompletedTwoRow || isCompletedThreeRow || isCompletedFourRow){

                g.setColor(TetrisGame.this.gamePanel.currentShape.shapeColor);

                isCompletedOneRow = isCompletedTwoRow = isCompletedThreeRow = isCompletedFourRow = false;
            }

            g.fill3DRect(30,30,190,50,true);

            g.setColor(Color.BLACK);

            g.drawString("     TETRIS  ", 40, 60);

            g.setColor(Color.RED);

            g.drawString("TIME",90,750);

            g.setColor(Color.white);

            g.draw3DRect(20,780,210,100, true);

            g.setFont(new Font("", Font.BOLD, 50));

            g.drawString(minuteStr+" : "+secondStr,50,850);

            g.setColor(Color.DARK_GRAY);

            g.fill3DRect(160,920,50,50,true);
            g.fill3DRect(100,920,50,50,true);
            g.fill3DRect(40,920,50,50,true);

            g.setColor(Color.white);

            g.setFont(new Font("", Font.BOLD, 15));
            g.drawString("<<",55,950);
            g.drawString("&",120,950);
            g.drawString(">>",178,950);
            




        }


    }



    public static void main(String args[]){

        TetrisGame t =new TetrisGame();





       


    
    
        
    }



    @Override
    public void windowOpened(WindowEvent e) {
       
    }



    @Override
    public void windowClosing(WindowEvent e) {

        this.exitScreen.setVisible(true);

    }



    @Override
    public void windowClosed(WindowEvent e) {
       
    }



    @Override
    public void windowIconified(WindowEvent e) {
        
    }



    @Override
    public void windowDeiconified(WindowEvent e) {
       
    }



    @Override
    public void windowActivated(WindowEvent e) {
      
    }



    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }


    
}