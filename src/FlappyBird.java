import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener { //JPanel allow us to add function and variables that we need for the game
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

    //game logic
    Bird bird;

    Timer gameLoop;

    FlappyBird() { //my constructor of my FlappyBird class
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.BLUE);// bg in blue

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);

        //game timer
        gameLoop = new Timer(1000/60,this);
        gameLoop.start();// start timer
    }

    //draw the images onto the background
    public void paintComponent(Graphics g) { //from the JPanel
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        System.out.println("caca");
        //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) { //here are the actions
    repaint();
    }

}
