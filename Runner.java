// Taran's work - NPCs, GameState; rest done by Aditya
//Import the Color class from the awt library to set the background color of the window
import java.awt.Color;
//Import the Dimension class from the awt library to set the dimensions of the window
import java.awt.Dimension;
//Import the Graphics class from the awt library to draw the sprites on the window
import java.awt.Graphics;
//Import the Graphics2D class from the awt library to aid the Graphics class and draw better sprites on the window
import java.awt.Graphics2D;
//Import JPanel to implement all the visual and necessary features
import javax.swing.JPanel;

//Runner class which runs the game loop and calls all the necessary methods to run the game in the Main Class
//The class implements a Runable interface as it intends to use a gameThread as a game loop
public class Runner extends JPanel implements Runnable{

                //Screen settings
    public final int tileSize = 48; // 48 x 48 Tiles
    //How many columns are displayed on the window at a time
    public final int maxScreenCol = 16;
    //How many rows are displayed on the window at a time
    public final int maxScreenRow = 12;
    //The window's width
    public final int screenWidth = tileSize*maxScreenCol;
    //The window's height
    public final int screenHeight = tileSize* maxScreenRow;

                //World Settings
    //How many columns are in the world map
    public final int maxWorldCol = 65;
    //How many rows are in the world map
    public final int maxWorldRow = 57;

    //FPS - The FPS we wish to achieve for the game
    int FPS = 44;

                //Game Essentials
    //Create a tileManager object that handles all the tiles
    TileManager tileM = new TileManager(this);
    //Create a  class that handles all the key inputs
    Movement keyH = new Movement(this);
    //Create a sound object to play bgm
    Sound music = new Sound();
    //Another sound class object to play sound effects
    Sound soundEffect = new Sound();
    //Create a collision checker class to check whether the player is colliding with anything
    public Collision collisionCheck = new Collision(this);
    //Create a class that sets all the items at their particular places
    public ItemPlacer placer = new ItemPlacer(this);
    //UI class
    public Screen screen = new Screen(this);
    //Create a gameThread - This is basically the game loop and runs the game
    public Thread gameThread;
    //Create an Interaction class object
    public Pit damagePit = new Pit(this);

                //Entity and Object
    //Create a player Class and pass this runer class and the keyHandler object as arguements
    public Player player = new Player(this,keyH);
    
    //An array that stores all the different objects we create (Dragon Balls, Doors, Portal)
    public Item items[] = new Item[10];

    //An array that stores all the different types of npcs
    public NPC npc[] = new NPC[10]; //Taran

    //An array that stores all the different monsters
    public Enemy enemies[] = new Enemy[20];

    //                Taran
    // GAMESTATE - Whether the game is being played, is paused or the player is conversing with the NPC
    public int gameState;
    //  TitleState - Done by Aditya
    public final int titleState = 0;
    // If game is in playState, the player is playing the game normally
    public final int playState = 1;
    //Game has been paused
    public final int pauseState = 2;
    // Player is conversing with the NPC
    public final int dialogueState = 3;
    //Gameover
    public final int gameOverState = 4;
    // Victory State
    public final int victoryState = 5;
    
    /**
     * Sets the size of the window, its color and implements key input
     * Precondition: A keyHandler class object must be instantiated 
     * Postcondition: The game's window and key input has been setup
     */
    public Runner()
    {
        //Dimensions
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        //Add a background color
        this.setBackground(Color.white);
        //Double buffer for better rendering
        this.setDoubleBuffered(true);
        //Instanstiate keyhandler class
        this.addKeyListener(keyH);
        // Listens to key input
        this.setFocusable(true); 
    }

    /**
     * Setup the game by placing the objects in the proper postion and playing the game's bgm
     * Precondition: An assetsetter class object must be instantiated
     * Postcondition: All the objects have been placed on the map and the music plays continously
     */
    public void setupGame()
    {
        placer.placeItem();
        // Done by Taran
        placer.setNPC();
        //Place the enemies
        placer.placeEnemies();
        // playMusic(0);
        gameState = titleState;
    }

    /**
     * Runs the game loop by creating a game Thread - The game Thread allows us to do multiple tasks at the same time
     * Precondition: a gameThread object must have been declared
     * Postcondition: A gameThread has begun running
     */
    public void startGameThread()
    {
        //Create a new game thread - Runs the game
        gameThread = new Thread(this);
        //Calls the run() method in the class
        gameThread.start();
    }

    /**
     * Default gameThread required method which runs the game
     * Precondition: a gamethread object must have been instantiated
     * Postcondition: Implements the FPS functionality and runs the game by calling the update and repaint method
     */
    public void run()
    {
        //Creates the FPS
        double timeInterval = 1000000000/FPS;
        double delta = 0;
        long previousTime = System.nanoTime();
        long currentTime;

        while (gameThread!=null)
        {
            currentTime = System.nanoTime();
            delta += (currentTime - previousTime)/timeInterval;
            previousTime = currentTime;

            if (delta>=1)
            {
                // 1. Update: update information about the character's position
                update();
                // 2. Draw:   draw the screen with the updated information
                repaint();
                delta--;
            }
        }
    }

    /**
     * Calls the player's update method and updates the player's information 
     * Precondition: A player must have been instantiated and created
     * Postcondition: The player's information has been updated
     */
    public void update()
    {
        if(gameState == playState)
        {
            //Update the player's information (Position)
            player.update();

            //Update the npc's information
            for(int i = 0; i < npc.length;i++)
            {
                    if(npc[i] != null)
                    {
                        npc[i].update();
                    }
            }

            //Update the enemy's information
            for(int i = 0; i < enemies.length;i++)
            {
                    if(enemies[i] != null)
                    {
                        enemies[i].update();
                    }
            }
        }
    }

    /**
     * Method which calls on the Graphics2D class to draw the tiles and the 
     * Precondition: A Graphics class object must be passed to the method
     * Postcondition: The tiles and sprites are painted on the screen
     * @param g - A Graphics class object that is used to draw sprites and tiles on the window
     */
    public void paintComponent(Graphics g)
    {
        //Call the super class 
        super.paintComponent(g);

        //Convert the Graphics class object to a Graphics2D object to have access to more methods
        Graphics2D g2 = (Graphics2D)g;

        //title screen
        if (gameState == titleState)
        {   
            screen.draw(g2);
        }
        //Others
        else
        {
            //Tile - Draw the tiles
            tileM.draw(g2);

            //Object
            for(int i=0;i<items.length;i++)
            {
                if (items[i] != null)
                {
                    //Draw the object on the display window
                    items[i].draw(g2, this);
                }
            }

            // NPC
            for(int i=0;i<npc.length;i++)
            {
                if (npc[i] != null)
                {
                    npc[i].draw(g2);
                }
            }

            //Enemies
            for(int i=0;i<enemies.length;i++)
            {
                if (enemies[i] != null)
                {
                    enemies[i].draw(g2);
                }
            }

            //Player - Draw on screen
            player.draw(g2);

            //Screen
            screen.draw(g2);
        }

        //Get rid of the memory that was storing the components to increase efficiency
        g2.dispose();
    }

    /**
     * Method which resets all the important assets to restart the game
     * Precondition: The player must choose to restart the game from the gameOver or Victory Screen
     * Postcondition: All assets have been reverted and the game restarts
     */
    public void retry()
    {
        //Reset the player
        player.reset();
        //Reset the items 
        placer.placeItem();
        // Done by Taran - Reset the NPC's position
        placer.setNPC();
        //Place the enemies
        placer.placeEnemies();
    }

    /**
     * Plays the music
     * Precondition: An integer should be passed as a parameter to the method
     * Postcondition: A specific sound is played continuously on a loop
     * 
     * @param index - The index of which sound to play
     */
    public void playMusic(int index)
    {
        //Get the music to be played
        music.loadAudio(index);
        //Play the chosen music
        music.play();
        //Loop the chosen music
        music.loop();
    }

    /**
     * Stops the music
     * Precondition: Music must be playing
     * Postcondition: Music stops playing
     */
    public void stopMusic()
    {
        music.stop();
    }

    /**
     * Plays a sound effect
     * Precondition: An integer must be passed as an arguement to the method
     * Postcondition: The specific sound effect starts playing
     * @param index - The index of which sound effect to play
     */
    public void playSE(int index)
    {
        //Select the sound effect to be played
        soundEffect.loadAudio(index);
        //Play the sound effect but not loop it as it only needs to be played once
        soundEffect.play();
    }
}
