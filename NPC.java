// import all necessary libraries
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

// A Class that is used to create the NCP - A subclass of the Character superclass
public class NPC extends Character{
    
    /**
     * Constructor that instanitates the NPC's instance variables and creates a hitbox
     * Precondition: A Runner class object must be passed to the method
     * Postcondition: The NPC's instance variables have been instantiated
     * @param runner - The Runner class object
     */
    public NPC(Runner runner)
    {
        // Call the superclass' constructor
        super(runner);

        // Name of NPC
        name = "law";

        // Initial direction
        direction = "down";

        // Speed of NPC
        speed = 2;

        // Create the NPC's hitbox
        hitBox = new Rectangle();
        hitBox.x = 12;
        hitBox.y = 12; 
        hitBox.width = 12;
        hitBox.height = 12;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        // Load the images
        getImage(1);
        // Set the dialogues of the NPC
        setDialogue();
    }

    /**
     * A method used to recursively load the npc's images 
     * Precondtion: An integer containing the index of the image being loaded should be passed to the method
     * Postcondition: All of the npc's sprites have been imported into their respective arraylists
     * @param index - The index of the image being loaded 
     */
    public void getImage(int index)
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
            normalUp.add(ImageIO.read(getClass().getResourceAsStream("./npc/u"+index+".png")));
            //Sprites for going down as normal transformation
            normalDown.add(ImageIO.read(getClass().getResourceAsStream("./npc/d"+index+".png")));
            //Sprites for going right as normal transformation
            normalRight.add(ImageIO.read(getClass().getResourceAsStream("./npc/r"+index+".png")));
            //Sprites for going left as normal transformation
            normalLeft.add(ImageIO.read(getClass().getResourceAsStream("./npc/L"+index+".png")));
            //Increment the index value to load the second set of images
            index ++;
            //Recursively call the function again with an incremented index
            getImage(index);
        }
        //Check for any exceptions
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set the NPC's dialogues
     * Precondition: The dialogues array must be declared 
     * Postcondition: The dialogues have been set
     */
    public void setDialogue()
    {
        dialogue[0] = "Greetings, MORTAL. I am Dr.Law \nand this is EXPERIMENT #43";
        dialogue[1] = "You have been put in this experimental \nplane of existance as a test subject to \ncheck the power of the DRAGON BALLS.";
        dialogue[2] = "Your objective is to collect all 7 balls and \nhopefully transform to gain abilities \nlike walking on water.";
        dialogue[3] = "The balls are scattered across the island.";
        dialogue[4] = "After finding all the balls, you will have \nthe ability to escape from this place using \nthe portal behind the sealed door.";
        dialogue[5] = "But beware of the hidden traps and the \n enemy pirates 'Zoro'";
        dialogue[6] = "INITIATE SEQUENCE!!!";
    }

    /**
     * Sets the behavior of the npc (Randomly change directions every second)
     * Precondition: The Random class must be imported
     * Postcondition: The npc's direction changes at random
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
     * Allow the NPC to speak with the player
     * Preconditions: The dialgoues must already  be set
     * Postcondition: The NPC can converse with the player
     */
    public void speak()
    {
        // Check if the dialogue array element is null and if it is, reset the diloueIndex to 0
        if(dialogue[dialogueIndex] == null)
            dialogueIndex = 0;
        
        // Get the dialogue that needs to be displayd on screen
        runner.screen.currentDialogue = dialogue[dialogueIndex];
        // Increase the dialogue index
        dialogueIndex++;
        
        // Get the direction of the player and make the NPC turn in the opposite direction to face the player
        switch(runner.player.direction)
        {
            case "up":
                direction = "down";
                break;
            case "left":
                direction = "right";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    /**
     * Update the NPC to create a working enemy
     * Precondition: The setBehavior() method must be functional
     * Postcondition: The NPC is constantly updated to create a working NPC
     */
    public void update()
    {
        // Call the setBehaviour method();
        setBehaviour();
        // Set colliding to false - Will change if the npc is colliding later on
        colliding = false;
        // Check for tile Collision
        runner.collisionCheck.tileCollision(this);
        // Check for item collision
        runner.collisionCheck.itemCollision(this, false);
        // Check for player collision
        runner.collisionCheck.checkPlayer(this);
        // Check if colliding with any enemies
        runner.collisionCheck.checkCharacter(this, runner.enemies);
        // If not colliding move the player
        if (colliding==false)
        {
            // Get the appropriate direction
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
     * Draw the NPC on the display screen
     * Precondition: A Graphics2D object must be passed to the method
     * Postcondition: The NPC is drawn on the display screen
     * @param g2 - A Graphics2D object
     */
    public void draw(Graphics2D g2)
    {
        // the image of the npc
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

        // Check whether the NPC is "ON SCREEN" and draw the NPC
        if ((worldX + runner.tileSize> (runner.player.worldX-runner.player.screenX)) &&
            (worldX - runner.tileSize< (runner.player.worldX+runner.player.screenX)) &&
            (worldY + runner.tileSize> (runner.player.worldY-runner.player.screenY)) &&
            (worldY - runner.tileSize< (runner.player.worldY+runner.player.screenY)))   
        {
            g2.drawImage(image,screenX,screenY,runner.tileSize,runner.tileSize,null);
        }
    }   
}
