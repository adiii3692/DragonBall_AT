//  Taran's Part - interactNpc() method; rest done by Aditya
//Import all necessary classes and libraries
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;

//Class which implements all functions of a player and it is a subclass of the Entity superclass
public class Player extends Character{

    //Declare a runner class object
    Runner runner;
    //Declare a keyhandler class object
    Movement keys;

    //The x coordinate on the screen
    public final int screenX;
    //The y coordinate on the screen
    public final int screenY;

    //How many dragon balls the player has
    int hasBalls = 0;

    //A string for which transformation the player is currently in
    public String transformation = "normal";

    //Check whether the player is invincible
    public boolean invincible = false;
    public int invincibleCounter = 0;

    //                 Different Images of the Player 
    //Powering up images
    public ArrayList<BufferedImage> powerUp = new ArrayList<BufferedImage>();

    //Blue transformation going up
    public ArrayList<BufferedImage> blueUp = new ArrayList<BufferedImage>();
    //Blue transforamtion going down
    public ArrayList<BufferedImage> blueDown = new ArrayList<BufferedImage>();
    //Blue transformation going left
    public ArrayList<BufferedImage> blueLeft = new ArrayList<BufferedImage>();
    //Blue transformation going right
    public ArrayList<BufferedImage> blueRight = new ArrayList<BufferedImage>();

    /**
     * A constructor for instantiating a player
     * Precondtion: A runner class object and keyHandler class object must be passed while instantiating a player
     * Postcondition: A player has been instantiated, with all the required isntance variables
     * @param runner - A Runner class object
     * @param keys - A KeyHandler class object
     */
    public Player(Runner runner, Movement keys)
    {
        // Call the super class' constructror
        super(runner);

        //Instantiate the runner and keyHandler class objects
        this.runner = runner;
        this.keys = keys;

        //name
        name = "player";

        //The player's x coordinate on screen
        screenX = runner.screenWidth/2 - (runner.tileSize/2); //Half the screenWidth minus half a tile
        screenY = runner.screenHeight/2 - (runner.tileSize/2); //Half the screenHeight minus half a tile

        //Create a rectangle to use as the player's hitbox
        hitBox = new Rectangle();
        //X coordinate of where the rectangle starts
        hitBox.x = 8;
        //Y coordinate of where the rectangle starts
        hitBox.y = 16; 
        //The hitbox's width
        hitBox.width = 32;
        //The hitbox's height
        hitBox.height = 32;
        //The hitbox's default x = the hitbox's x coordinate
        hitBoxDefaultX = hitBox.x;
        //The hitbox's default y = the hitbox's y coordinate
        hitBoxDefaultY = hitBox.y;

        //Place the player on the 23rd Column (X coordinate)
        worldX = runner.tileSize*23;
        //Place the player on the 21st Row (Y coordinate)
        worldY = runner.tileSize*21;
        //The player's speed
        speed = 8;
        //The initial direction in which the player faces
        direction = "down";
        //Player's maximum health
        maxHealth = 10;
        currentHealth = maxHealth;
    
        //Load the player's sprites
        loadPlayerImages(1);
    }

    /**
     * A method used to recursively load the player's images 
     * Precondtion: An integer containing the index of the image being loaded should be passed to the method
     * Postcondition: All of the player's sprites have been imported into their respective arraylists
     * @param index - The index of the image being loaded 
     */
    public void loadPlayerImages(int index)
    {
        //If the index of the image is greater than two, return to stop the recursion
        if (index>2)
        {
            return;
        }
        //Load the images into their respective ArrayLists
        try
        {
            //Sprites for going up as normal transformation
            normalUp.add(ImageIO.read(getClass().getResourceAsStream("./boy/f"+index+".png")));
            //Sprites for going down as normal transformation
            normalDown.add(ImageIO.read(getClass().getResourceAsStream("./boy/d"+index+".png")));
            //Sprites for going right as normal transformation
            normalRight.add(ImageIO.read(getClass().getResourceAsStream("./boy/r"+index+".png")));
            //Sprites for going left as normal transformation
            normalLeft.add(ImageIO.read(getClass().getResourceAsStream("./boy/L"+index+".png")));
            //Sprites for going up as blue transformation
            blueUp.add(ImageIO.read(getClass().getResourceAsStream("./boy/blue/Bu"+index+".png")));
            //Sprites for going down as blue transformation
            blueDown.add(ImageIO.read(getClass().getResourceAsStream("./boy/blue/Bd"+index+".png")));
            //Sprites for going right as blue transformation
            blueRight.add(ImageIO.read(getClass().getResourceAsStream("./boy/blue/Br"+index+".png")));
            //Sprites for going left as blue transformation
            blueLeft.add(ImageIO.read(getClass().getResourceAsStream("./boy/blue/Bl"+index+".png")));
            //Increment the index value to load the second set of images
            index ++;
            //Recursively call the function again with an incremented index
            loadPlayerImages(index);
        }
        //Check for any exceptions
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method which updates the player's position, transformations and checks for collisions
     * Precondtion: The runner class object must be instantiated
     * Postcondition: The player's position, transformation and collisions are continously updated and checked
     */
    public void update()
    {   
        //Change the direction of the player if the W key is pressed
        if (keys.upPressed==true)
        {
            direction = "up";
        }

        //Change the direction of the player if the S key is pressed
        else if (keys.downPressed==true)
        {
            direction = "down";
        }

        //Change the direction of the player if the A key is pressed
        else if (keys.leftPressed==true)
        {
            direction = "left";
        }

        //Change the direction of the player if the D key is pressed
        else if (keys.rightPressed==true)
        {
            direction = "right";
        }
        
                    //Check Tile Collision
        //The player is not undergoing any collision            
        colliding = false;
        //Check if the player will collide with anything depending on which direction the player is currently going in 
        runner.collisionCheck.tileCollision(this);

                    //Check object collision 
        //Get the index of the object the player is colliding with an item
        int objIndex = runner.collisionCheck.itemCollision(this, true);
        //pickup the object if the object can be picked up or else don't pick it up
        pickUpObject(objIndex);

        //Make the player transform into blue and walk over water if the player has collected 2 or more balls
        if (hasBalls>=2)
        {
            //transform into blue
            transformation = "blue";
        }

        // CHECK NPC COLLISION - Taran
        int npcIndex = runner.collisionCheck.checkCharacter(this, runner.npc);
        interactNPC(npcIndex);

        // check for Enemy Collision 
        int enemyIndex = runner.collisionCheck.checkCharacter(this, runner.enemies);      
        enemyDamage(enemyIndex);
        
        // Check if Falling into a pit
        runner.damagePit.checkFall();

        //If collision is false the player can move
        if (colliding==false)
        {
            //Check if the player is pressing any key and if so make the player move in that direction
            if ((keys.upPressed==true)||(keys.downPressed==true)||(keys.leftPressed==true)||(keys.rightPressed==true))
            {
                //Check which direction the player is moving in
                switch(direction)
                {
                    //If moving up, decrease the Y coordinate
                    case "up":
                        worldY -= speed;
                        break;
                    //If moving down, increase the Y coordinate
                    case "down":
                        worldY += speed;
                        break;
                    //If moving left, decrease the X coordinate
                    case "left":
                        worldX -= speed;
                        break;
                    //If moving right, increase the X coordinate
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            //Animation Loop
            spriteCounter++; // Used for counting the fps and only animating after a certain amount of time

            //If the spriteCounter has increased enough, increase the spriteNum and change the player's image to create an animation
            if (spriteCounter>25)
            {
                // Increase the sprite Num - The index of each sprite array
                spriteNum++; 
                //If the spriteNum has gone beyond the length of the arrayLists, change it back to 1
                if (spriteNum>2)
                    spriteNum = 1;
                //Change the spriteCounter back to 0 to once again wait for an animation to occur
                spriteCounter = 0;
            }
        }

        // Check whether the player is invincible and if they are, increase the invincibile Counter
        if (invincible==true)
        {
            // Increase the invincibleCounter
            invincibleCounter++;
            // If a second has passed (44 fps), make normal again 
            if (invincibleCounter> 44)
            {
                // invincible is now false
                invincible = false;
                // Reset invincible counter to 0 
                invincibleCounter = 0;
            }
        }

        // Check if the player has run out of lives
        if (currentHealth <= 0)
        {
            // Game Over State
            runner.gameState = runner.gameOverState;
            // Play game over sound effect
            runner.playSE(4);
        }
    }

    /**
     * Resets all important instance variables to restart the game
     * Precondition: The game should be in gameOverState() and the player should choose to restart the game
     * Postcondition: The game has been reset
     */
    public void reset()
    {
        //Place the player on the 23rd Column (X coordinate)
        worldX = runner.tileSize*23;
        //Place the player on the 21st Row (Y coordinate)
        worldY = runner.tileSize*21;
        //The player's speed
        speed = 8;
        //The initial direction in which the player faces
        direction = "down";
        //Player's maximum health and current health
        maxHealth = 10;
        currentHealth = maxHealth;
        //Reset the number of dragon balls too
        hasBalls = 0;
        // Reset the transformation
        transformation = "normal";
    }

    /**
     * Allow the player to pickup an object
     * Precondtion: An integer must be passed to the method
     * Postcondition: The player either picks up an object or doesn't 
     * @param index - The index of the object that the player wishes to pick up
     */
    public void pickUpObject(int index)
    {
        //If the index is not equal to 999, let the player pickup the object
        if (index!=999)
        {
            //Get the name of the object
            String objectName = runner.items[index].name;

            //Check what object it is
            switch(objectName)
            {
                //If it is a dragon ball, play a sound effect and make the object disappear by updating the player's dragon ball count
                case "ball":
                    //If the player collects the second ball, play "Vegito Blue!!!"
                    if (hasBalls==1)
                    {
                        runner.playSE(2);
                    }
                    //Otherwise play the beep sound
                    else
                    {
                        runner.playSE(1);
                    }
                    // If the player isn't collecting the second ball, display this text
                    if(hasBalls != 1)
                    {
                        runner.screen.showMessage("A Dragon Ball Has Been Obtained!");
                    }
                    // Otherwise display Transformation
                    else
                    {
                        runner.screen.showMessage("Transformation!!!... YOU CAN NOW WALK ON WATER!");
                    }
                    //Increase the dragon ball count
                    hasBalls++;
                    //Make the object disappear
                    runner.items[index] = null;
                    break;

                //If the player has all 7 dragon balls let the player pass through the door
                case "door":
                    // If the player has collected all dragon balls, let them pass through
                    if(hasBalls >= 7)
                    {
                        // Stop the music
                        runner.stopMusic();
                        // Play the door sound effect
                        runner.playSE(3);
                        // Make the door vanish
                        runner.items[index] = null;
                        // Displaye Message
                        runner.screen.showMessage("You have entered the Castle");
                    }
                    else
                    {
                        // If the player hasn't collected all dragon balls, display this message
                        runner.screen.showMessage("You need all 7 dragon balls!");
                    }
                    break;

                //If the player has all 7 dragon balls let them enter the portal
                case "portal":
                    // Play victory music
                    runner.playSE(5);
                    // Change to victoryState and display the victory screen
                    runner.gameState = runner.victoryState;
                    break;
            }
        }
    }

    /**
     * Make the player interact with the NPC
     * Precondition: An index containing the index of the NPC should be passed to the method
     * Postcondition: The player can interact with the NPC
     * @param index - The index of the NPC the player is interacting with
    */
    public void interactNPC(int index)
    {
        // Check if the index is not 999 and only then proceed
        if(index != 999)
        {   
            //Check if the player has pressed the enter key to make the NPC speak
            if(runner.keyH.enterPressed == true)
            {
                // Change into dialogueState
                runner.gameState = runner.dialogueState;
                // Make the NPC speak
                runner.npc[index].speak();
            }
        }

        // Change the enter pressed to false or else the NPC will speak even without pressing enter the next time
        runner.keyH.enterPressed = false;
    }

    /**
     * Deal damage to the player if in contact with an enemy
     * Precondition: An integer containing the index of the enemy should be passed
     * Postcondition: The player receives damage
     * @param index - The index of the enemy the player is colliding with
     */
    public void enemyDamage(int index)
    {
        // Check if the index is not 999 and only then proceed
        if(index != 999)
        {
            // Check whether the player is not invincible and only then proceed
            if (!invincible)
            {
                // Reduce the player's health by 2
                currentHealth -= 2;
                // Make the player invincible
                invincible = true;
            }
            
        }
    }

    /**
     * Draw the player on the screen
     * Precondition: A Graphics2D object must be passed to the method
     * Postcondition: The player is drawn on the screen
     * @param g2
     */
    public void draw(Graphics2D g2)
    {
        //The image which needs to be drawn
        BufferedImage image = null;
        //Blue transformations
        if (transformation.equals("blue"))
        {
            //If going up, iterate through the blueUp ArrayList
            if (direction.equals("up"))
            {
                image = blueUp.get(spriteNum-1);
            }
            //If going down, iterate through the blueDown ArrayList
            else if (direction.equals("down"))
            {
                image = blueDown.get(spriteNum-1);
            }
            //If going left, iterate through the blueLeft ArrayList
            else if (direction.equals("left"))
            {
                image = blueLeft.get(spriteNum-1);
            }
            //If going right, iterate through the blueRight ArrayList
            else if (direction.equals("right"))
            {
                image = blueRight.get(spriteNum-1);
            }
        }

        //Normal Vegito animations
        else
        {
            //If going up, iterate through the normalUp ArrayList
            if (direction.equals("up"))
            {
                image = normalUp.get(spriteNum-1);

            }
            
            //If going down, iterate through the normalDown ArrayList
            else if (direction.equals("down"))
            {
                image = normalDown.get(spriteNum-1);
            }

            //If going left, iterate through the normalLeft ArrayList
            else if (direction.equals("left"))
            {
                image = normalLeft.get(spriteNum-1);
            }

            //If going right, iterate through the normalRight ArrayList
            else if (direction.equals("right"))
            {
                image = normalRight.get(spriteNum-1);
            }
        }
        // Make the player a bit transparent if invincvble
        if (invincible ==true)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.7f));
        }

        //Draw the image on the window
        g2.drawImage(image,screenX,screenY,runner.tileSize,runner.tileSize,null);

        //Reset the alpha setting
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
}
 