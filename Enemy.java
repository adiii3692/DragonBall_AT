// Import all necessary libraries
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

// A class that implements enemies that deal damage to the player - A subclass of the Character class
public class Enemy extends Character{

    // A Scaling class object - Used to scale images and change their size
    Scaling helper = new Scaling();

    /**
     * A constructor that instantiates all the instance variables and the enemy's hitbox
     * Precondition: A Runner class object must be passed to the method
     * Postcondition: The instance variables are instantiated
     * @param runner - A Runner class object
     */
    public Enemy(Runner runner) {
        // Call the superclass' constructor
        super(runner);
        
        // Name
        name = "Enemy";
        // Speed
        speed = 4;
        // Health
        maxHealth = 4;
        currentHealth = maxHealth;

        // Hitbox
        hitBox = new Rectangle();
        hitBox.x = 3;
        hitBox.y = 18;
        hitBox.width = 42;
        hitBox.height = 30;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        // Load all the images
        loadEnemyImages(1);
    }

    /**
     * Loads all the images of the enemy
     * Precondition: An integer containing the index of the image must be passed to the method
     * Postcondition: All the images of the enemy are loaded
     * @param index - The index of the image we are trying to load (1.png, 2.png)
     */
    public void loadEnemyImages(int index)
    {
        //If the index of the image is greater than two, return to stop the recursion
        if (index>2)
        {
            return;
        }
        //Load the images into their respective ArrayLists
        try
        {
            //Sprites for going up 
            normalUp.add(ImageIO.read(getClass().getResourceAsStream("./marimo/up/"+index+".png")));
            //Sprites for going down 
            normalDown.add(ImageIO.read(getClass().getResourceAsStream("./marimo/down/"+index+".png")));
            //Sprites for going right 
            normalRight.add(ImageIO.read(getClass().getResourceAsStream("./marimo/right/"+index+".png")));
            //Sprites for going left 
            normalLeft.add(ImageIO.read(getClass().getResourceAsStream("./marimo/left/"+index+".png")));
            
            //Increment the index value to load the second set of images
            index ++;
            //Recursively call the function again with an incremented index
            loadEnemyImages(index);
        }
        //Check for any exceptions
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sets the behavior of the enemies (Randomly change directions every second)
     * Precondition: The Random class must be imported
     * Postcondition: The enemy's direction changes at random
     */
    public void setBehaviour()
    {
        // A timer that checks how much time has elapsed based on the FPS (44 FPS)
        actionLockCounter++;

        // If one second has passed, change the direction
        if(actionLockCounter == 44)
        {
            // Random class object
            Random random =  new Random();
        
            // Choose a random integer 0 - 100  
            int i = random.nextInt(100)+1;
        
            // 1/4 chance of going up
            if(i <= 25)
            {
                direction = "up";
            }
            // 1/4 chance of going down
            if(i>25 && i<=50)
            {
                direction = "down";
            }
            // 1/4 chance of going left
            if(i>50 && i<=75)
            {
                direction = "left";
            }
            // 1/4 chance of going right
            if(i>75 && i<=100)
            {
                direction = "right";
            }

            // Reset the timer
            actionLockCounter = 0;
        }
    }

    /**
     * Update the enemy to create a working enemy
     * Precondition: The setBehavior() method must be functional
     * Postcondition: The enemy is constantly updated to create a working enemy
     */
    public void update()
    {
        // Call the setBehaviour() method
        setBehaviour();
        // Change colliding instance variable to false
        colliding = false;
        // Check for any tile Collisions
        runner.collisionCheck.tileCollision(this);
        // Check for any item Collisions
        runner.collisionCheck.itemCollision(this, false);
        // Check for player collision
        runner.collisionCheck.checkPlayer(this);
        // Check for npc collision
        runner.collisionCheck.checkCharacter(this, runner.npc);
        // Check if colliding with other enemies
        runner.collisionCheck.checkCharacter(this, runner.enemies);

        // If not colliding with anything
        if (colliding==false)
        {
            // Check direction and make the enemy move around
            switch(direction)
            {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        // If colliding with the player, deal damage to the player
        if (runner.collisionCheck.checkPlayer(this))
        {
            runner.player.enemyDamage(Arrays.asList(runner.enemies).indexOf(this));
        }

        //Animation Loop
        spriteCounter++; // Used for counting the fps and only animating after a certain amount of time
        if (spriteCounter>25)
        {
            spriteNum++; // Increase the sprite Num - The index of each sprite array
            if (spriteNum>2)
                spriteNum = 1;
            spriteCounter = 0;
        }
    }

    /**
     * Draw the enemy on the display screen
     * Precondition: A Graphics2D object must be passed to the method
     * Postcondition: The enemy is drawn on the display screen
     * @param g2 - A Graphics2D object
     */
    public void draw(Graphics2D g2)
    {
        // the image of the enemy
        BufferedImage image = null;
        
        // Get the x coordinates on the screen
        int screenX = worldX -  runner.player.worldX + runner.player.screenX;
        // Get the y coordinates on the screen
        int screenY = worldY - runner.player.worldY + runner.player.screenY;

        // Get the image based on the direction
        if (direction.equals("up"))
        {
            image = normalUp.get(spriteNum-1);
        }
        // Get the image based on the direction
        else if (direction.equals("down"))
        {
            image = normalDown.get(spriteNum-1);
        }
        // Get the image based on the direction
        else if (direction.equals("left"))
        {
            image = normalLeft.get(spriteNum-1);
        }
        // Get the image based on the direction
        else if (direction.equals("right"))
        {
            image = normalRight.get(spriteNum-1);
        }

        // Check whether the enemy is "ON SCREEN" and draw the enemy
        if ((worldX + runner.tileSize> (runner.player.worldX-runner.player.screenX)) &&
            (worldX - runner.tileSize< (runner.player.worldX+runner.player.screenX)) &&
            (worldY + runner.tileSize> (runner.player.worldY-runner.player.screenY)) &&
            (worldY - runner.tileSize< (runner.player.worldY+runner.player.screenY)))   
        {
            g2.drawImage(image,screenX,screenY,runner.tileSize,runner.tileSize,null);
        }
    }
    
}
 