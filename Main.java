//import jframe from swing
import javax.swing.JFrame;

//Main class with main method which calls all the necessary methods to run the game
public class Main {
    /**
     * Main Function which calls all the necessary methods to keep the game running
     * Preconditions: The JFrame class form the swing library must be imported
     * Postconditions: The game runs
     * @param args - Default main method parameter
     */
    public static void main(String[] args)
    {
        //Create a new window using JFrame
        JFrame window = new JFrame();
        //Allow the user to exit by clicking on the close button
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Do not allow the user to resize the window
        window.setResizable(false);
        //The title of the game and the window
        window.setTitle("Dragon Ball AT!!!");

        //Create a new Runner object
        Runner runner = new Runner();

        //Add the runner object to the window so that the game loop can be run and the runner class can dispay the sprites
        window.add(runner);

        //Pack the window - This makes the window the perfect size and layout that fits its elements
        window.pack();

        //Place the window on the centre of the screen
        window.setLocationRelativeTo(null);
        //Make the window visible
        window.setVisible(true);

        //Call up all the necessary setup methods
        runner.setupGame();
        //Run the game by running the gameThread method of the 
        runner.startGameThread();

        // THANK YOU FOR PLAYING OUR GAME !!!!!
        System.out.println("A AND T DEVS THANK YOU FOR PLAYING OUR GAME !!!");
    }
}
