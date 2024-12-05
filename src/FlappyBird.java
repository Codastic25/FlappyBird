import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener { //JPanel allow us to add function and variables that we need for the game
    int boardWidth = 360; //dimension large of my window in px
    int boardHeight = 640; //dimension height of my window in px

    //Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;


    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;//px
    int pipeHeight = 512;//px

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    //game logic
    Bird bird;
    int velocityX = -4;
    int velocityY = -6; //for the speed of my bird (vertically direction up)
    int gravity = 1; //gravity of the game, 1 px per frame (direction down vertically)

    ArrayList<Pipe> pipes;
    Random rand = new Random();

    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    double score = 0;

    FlappyBird() { //my constructor of my FlappyBird class
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.BLUE);// bg in blue
        setFocusable(true);
        addKeyListener(this);// we check the keys function

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        //place pipes timer
        placePipesTimer = new Timer(1200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000/60,this);
        gameLoop.start();// start timer
    }

    public void placePipes() {
        int randomPipeY =(int) (pipeY - pipeHeight/4 - Math.random()*pipeHeight/2);
        int openingSpace = boardWidth/2;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    //draw the images onto the background
    public void paintComponent(Graphics g) { //from the JPanel
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over : " + String.valueOf((int) score), 10, 35);// print of the game over
        }
        else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); //0 is the top of the screen, not going upper than the screen

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width){ //if bird pass the right side of the pipe, then instructions
                pipe.passed = true;
                score = score + 0.5;
            }

            if (collision(bird, pipe)){
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;// detecting collision
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) { //here are the actions
        move();
        repaint();

        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {// if I press SPACE, then conditions
            velocityY = -9;

            if (gameOver) {
                //then restart
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

}
