//Restart, Score, Pause, Better Graphics,

	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.FontMetrics;
	import java.awt.Graphics;
	import java.awt.Image;
	import java.awt.Toolkit;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyAdapter;
	import java.awt.event.KeyEvent;
	import javax.swing.ImageIcon;
	import javax.swing.JPanel;
	import javax.swing.Timer;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	

	public class Board extends JPanel implements ActionListener {

	    private final int B_WIDTH = 500;
	    private final int B_HEIGHT = 500;
	    private final int DOT_SIZE = 10;
	    private final int ALL_DOTS = 250000;
	    private final int RAND_POS = 50;
	    private final int DELAY = 100;
	    

	    private final int x[] = new int[ALL_DOTS];
	    private final int y[] = new int[ALL_DOTS];

	    private int dots;
	    private int orange_x;
	    private int orange_y;
	    private int pear_x;
	    private int pear_y;
	    private int score;
	    
	    private String pausetext;

	    private boolean leftDirection = false;
	    private boolean rightDirection = true;
	    private boolean upDirection = false;
	    private boolean downDirection = false;
	    private boolean inGame = true;
	    private boolean hasrestarted=false;
	    private boolean pause=false;

	    private Timer timer;
	    private Image ball;
	    private Image orange;
	    private Image pear;
	    private Image head;
	    private Image line;
	    private Image pauseb;

	    public Board() {
	        
	        initBoard();
	        score=0;
	        pausetext="";
	    }
	    
	    private void initBoard() {
	    	
	        addKeyListener(new TAdapter());
	        addMouseListener(new MouseInputAdapter());
	    	
	       Color blue2 = new Color(70,100,130);
	        setBackground(blue2);
	        setFocusable(true);

	        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
	        loadImages();
	        initGame();
	    }

	    private void loadImages() {

	        ImageIcon iid = new ImageIcon("src/resources/dot.png");
	        ball = iid.getImage();

	        ImageIcon iia = new ImageIcon("src/resources/orange.png");
	        orange = iia.getImage();

	        ImageIcon iih = new ImageIcon("src/resources/head.png");
	        head = iih.getImage();
	        
	        ImageIcon iil = new ImageIcon("src/resources/lineS.png");
	        line = iil.getImage();
	        
	        ImageIcon iip = new ImageIcon("src/resources/pear.png");
	        pear = iip.getImage();
	        
	        ImageIcon iib = new ImageIcon("src/resources/pause.png");
	        pauseb = iib.getImage();
	    }

	    private void initGame() {

	        dots = 3;

	        for (int z = 0; z < dots; z++) {
	            x[z] = 50- z * 10;
	            y[z] = 50;
	        }
	        
	        locateOrange();
	        locatePear();
	        if(hasrestarted!=true) {
	        timer = new Timer(DELAY, this);
	        timer.start();
	        }
	        
	    }

	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        doDrawing(g);
	    }
	    
	    private void doDrawing(Graphics g) {
	        
	        if (inGame ) {
	        	score(g);
	        	pausemet(g);
	        	 Color blue2 = new Color(70,100,130);
	 	        setBackground(blue2);
	        	g.drawImage(line,0,-20,this);
	        	g.drawImage(line,50,-20,this);
	        	g.drawImage(line,-200,-20,this);
	            g.drawImage(orange, orange_x, orange_y, this);
	            g.drawImage(pear,pear_x,pear_y,this);
	            g.drawImage(pauseb,440,-1,this);
	            
	            {
	            for (int z = 0; z < dots; z++) {
	                if (z == 0) {
	                    g.drawImage(head, x[z], y[z], this);
	                } else {
	                    g.drawImage(ball, x[z], y[z], this);
	                }
	            }
	            }

	            Toolkit.getDefaultToolkit().sync();

	        } else {

	            gameOver(g);
	        }        
	    }

	    private void gameOver(Graphics g) {
	        
	        String msg = "Game over";
	        Font small = new Font("Helvetica", Font.BOLD, 15);
	        FontMetrics metr = getFontMetrics(small);
	        setBackground(Color.black);
	        
	        String restarts = "(press enter to restart)";
	       
	        String points = String.valueOf(score);
	        g.setColor(Color.red);
	        g.setFont(small);
	        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
	        g.drawString(points, (B_WIDTH - metr.stringWidth(points)) / 2, (B_HEIGHT / 2)+20);
	        g.drawString(restarts, (B_WIDTH - metr.stringWidth(restarts)) / 2, (B_HEIGHT / 2)+50);
	    }
	    private void score(Graphics g) {
	        
	        String points = String.valueOf(score);
	        Font small = new Font("Anton", Font.BOLD, 25);
	        FontMetrics metr = getFontMetrics(small);

	        g.setColor(Color.cyan);
	        g.setFont(small);
	        g.drawString("Score: "+ points, (B_WIDTH - (metr.stringWidth(points)+ 85)) / 2, B_HEIGHT -475);
	        
	    }
	    private void pausemet(Graphics g) {
	    	
	        Font small = new Font("Arial", Font.BOLD, 25);
	        

	        g.setColor(Color.black);
	        g.setFont(small);
	        g.drawString(pausetext, ((B_WIDTH/2)-50), B_HEIGHT /2);
	    }
	    private void restart() {
	    	score=0;
	    	
	    	leftDirection = false;
            upDirection = false;
            downDirection = false;
            rightDirection=true;
            timer.start();
            pause=false;
            inGame=true;
            initGame();
	    	
	    }
	    private void checkOrange() {

	        if ((x[0] == orange_x) && (y[0] == orange_y)) {

	            dots++;
	            score++;
	            
	            locateOrange();
	        }
	      
	    }
	    private void checkPear() {

	        if ((x[0] == pear_x) && (y[0] == pear_y)) {

	            dots++;
	            score++;
	            
	            locatePear();
	        }
	      
	    }

	    private void move() {
	    	if(pause==false) {
	        for (int z = dots; z > 0; z--) {
	            x[z] = x[(z - 1)];
	            y[z] = y[(z - 1)];
	        }

	        if (leftDirection) {
	            x[0] -= DOT_SIZE;
	        }

	        if (rightDirection) {
	            x[0] += DOT_SIZE;
	        }

	        if (upDirection) {
	            y[0] -= DOT_SIZE;
	        }

	        if (downDirection) {
	            y[0] += DOT_SIZE;
	        }
	    	}
	    }

	    private void checkCollision() {

	        for (int z = dots; z > 0; z--) {

	            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
	                inGame = false;
	            }
	        }

	        if (y[0] >= B_HEIGHT) {
	            inGame = false;
	        }

	        if (y[0] < 40) {
	            inGame = false;
	        }

	        if (x[0] >= B_WIDTH) {
	            inGame = false;
	        }

	        if (x[0] < 0) {
	            inGame = false;
	        }
	        
	        if (!inGame) {
	            timer.stop();
	        }
	    }

	    private void locateOrange() {

	        int r = (int) (Math.random() * RAND_POS);
	        orange_x = ((r * DOT_SIZE));

	        r = (int) (Math.random() * RAND_POS);
	        
	        orange_y = ((r * DOT_SIZE));
	        if(orange_y<40) {
	        	locateOrange();
	        }
	        System.out.println("Orange coords: "+orange_x +" "+ orange_y);
	        
	    }
	    private void locatePear() {

	        int r = (int) (Math.random() * RAND_POS);
	        pear_x = ((r * DOT_SIZE));

	        r = (int) (Math.random() * RAND_POS);
	        
	        pear_y = ((r * DOT_SIZE));
	        if(pear_y<40) {
	        	locatePear();
	        }
	        System.out.println("pear coords: "+pear_x +" "+ pear_y);
	        
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (inGame ) {

	            checkOrange();
	            checkPear();
	            checkCollision();
	            move();
	        }

	        repaint();
	    }

	    private class TAdapter extends KeyAdapter {

	        @Override
	        public void keyPressed(KeyEvent e) {

	            int key = e.getKeyCode();

	            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
	                leftDirection = true;
	                upDirection = false;
	                downDirection = false;
	            }

	            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
	                rightDirection = true;
	                upDirection = false;
	                downDirection = false;
	            }
	            if((key==KeyEvent.VK_P)) {
	            	
	            }
	            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
	                upDirection = true;
	                rightDirection = false;
	                leftDirection = false;
	            }

	            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
	                downDirection = true;
	                rightDirection = false;
	                leftDirection = false;
	            }
	            if ((key == KeyEvent.VK_ENTER) ) {
	            	
	            	hasrestarted=true;
	            	inGame=true;
	            	restart();
	            }
	        }
	    }
	    private class MouseInputAdapter extends MouseAdapter{
	    	public void mouseClicked(MouseEvent e) {
	    		System.out.println("clicked");
	    		int x=e.getX();
	    	    int y=e.getY();
	    	    
	    	   
	    	    System.out.println(x + " " + y);
	    	    if(x>440 && x<480 && y>0 && y<40) {
	    	    	if(pause==true) {
	            		pause=false;
	            		pauseText();
	            	}
	            	else {
	            		pause=true;
	            		pauseText();
	            	}
	    	    }
	    	}
	    	public void pauseText() {
	    	
	    		if(pause==true) {
	    			 pausetext="(paused)";
	    		}
	    		else {
	    			pausetext="";
	    		}
	    	}
	    }
	}

