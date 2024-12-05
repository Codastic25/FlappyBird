import javax.swing.*;

public class App {

    public static void main(String[] args) {
        int boardWidth = 360; //dimension large of my window in px
        int boardHeight = 640; //dimension height of my window in px

        JFrame frame = new JFrame("Flappy Bird"); // object frame
        frame.setSize(boardWidth, boardHeight); //to set the size of the window
        frame.setLocationRelativeTo(null); //place the window in the center of our screen
        frame.setResizable(false); //can't resize the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//when the user click on the red cross, it terminates the programs

        FlappyBird flappyBird = new FlappyBird();// instance of FlappyBird
        frame.add(flappyBird); //add to the frame
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true); //to make it visible after using the JPanel
    }

}
