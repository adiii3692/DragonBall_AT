// NPC part and the constructor implemented by Taran; rest done by Aditya
//import all the necessary libraries and classes
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//A class used as a superclass for all characters - Player and NPC
public class Character {

    //          Instance variables
    //The character's x and y coordinate on the world map
    public int worldX, worldY;
    //The character's speed
    public int speed;
    //The character's name
    public String name;

    //          Different images of the player and NPC
    //Normal transformation looking up
    public ArrayList<BufferedImage> normalUp = new ArrayList<BufferedImage>();
    //Normal transformation looking down
    public ArrayList<BufferedImage> normalDown = new ArrayList<BufferedImage>();
    //Normal transformation looking left
    public ArrayList<BufferedImage> normalLeft = new ArrayList<BufferedImage>();
    //Normal transformation looking right
    public ArrayList<BufferedImage> normalRight = new ArrayList<BufferedImage>();

    //The direction in which the character is facing
    public String direction;

    //Keeps count of how many times the game has displayed a frame to create an animation effect
    public int spriteCounter = 0;
    //Used to loop through the sprite images of a character and to create an animation effect
    public int spriteNum = 1;

    //The character's hitBox where the character collides with others
    public Rectangle hitBox;

    //The character's hitbox's X and Y coordinates
    public int hitBoxDefaultX;
    public int hitBoxDefaultY;

    //A boolean which checks if the character is undergoing a collision
    public boolean colliding = false;

    //Runner class object
    Runner runner;

    // Character's Status
    public int maxHealth;
    public int currentHealth;

    //          NPC - Taran
    public int actionLockCounter = 0;
    //An Array containing all the dialogues of the NPC
    String dialogue[] = new String[8];
    // The index of the dialogue the NPC is on 
    int dialogueIndex = 0;

    /**
     * Constructor for instantiating the runner class object
     * @param runner
     */
    public Character(Runner runner) {
        this.runner = runner;
    }
}
