// import Rectangle class to create a hitbox for the pit
import java.awt.Rectangle;

// A class used to check for interactions - such as falling into a pit
public class Pit 
{
    // A runner class object
    Runner runner;
    // Create a rectangle for the pit's hitbox
    Rectangle pitHitBox;
    // The pit's hitbox's default X and Y values - Used to reset the values after updating them 
    int pitHitBoxDefaultX, pitHitBoxDefaultY;
    // A boolean array that checks whether the player is colliding with a pit
    boolean checkPits[] = new boolean[10];

    /**
     * A constructor that instantiates the pit's hitbox and the Runner class object
     * Precondition: A Runner class object must be passed to the constructor
     * Postcondition: The pit's hitbox has been updated
     * @param runner - A Runner class object used to access the player
     */
    public Pit(Runner runner)
    {
        // Instantiate the runner class object
        this.runner = runner;

        // Instantiate the pit's hitbox
        pitHitBox = new Rectangle();
        pitHitBox.x = 23;
        pitHitBox.y = 23;
        pitHitBox.width = 2;
        pitHitBox.height = 2;
        pitHitBoxDefaultX = pitHitBox.x;
        pitHitBoxDefaultY = pitHitBox.y;
    }

    /**
     * Check for any collision with the pit
     * Precondition: A player must be created and the pit's hitbox must be already instantiated
     * Postcondition: Checks for any pit damage and updates the player's health
     */
    public void checkFall()
    {
        // Add the different pits to the checkPits[] boolean array to check whether the player is colliding with them
        checkPits[0] = (hit(46,48,"right")==true);
        checkPits[1] = (hit(49,16,"right")==true);
        checkPits[2] = (hit(44,9,"right")==true);
        checkPits[3] = (hit(41,11,"left")==true);
        checkPits[4] = (hit(43,28,"right")==true);
        checkPits[5] = (hit(11,47,"any")==true);
        // Check if the player is colliding with any of the pits and is not invincible
        if ((checkPits[0]||checkPits[1]||checkPits[2]||checkPits[3]||checkPits[4]||checkPits[5])&&runner.player.invincible==false)
        {
            // If true, then display a message and deal damage to the player
            pitFall();
            // Make the player invincible for a short while
            runner.player.invincible = true;
        }
    }

    /**
     * Checks whether the player is colliding with a pit
     * Precondition: Two integers containing the row and column number of the pit must be passed along with a String containing the required direction of the player
     * Postcondition: Return whether the player is colliding with a pit or not
     * @param pitCol - The column number of the pit on the world map
     * @param pitRow - The row number of the pit on the world map
     * @param reqDirection - The direction in which the player should be moving in order to collide with the pit
     * @return hit - A boolean that checks whether the player is colliding with a pit
     */
    public boolean hit(int pitCol, int pitRow, String reqDirection)
    {
        // Instantiate the hit variable that checks whether the player is hitting a pit
        boolean hit = false;

        // Get the player's hitbox values
        runner.player.hitBox.x = runner.player.worldX + runner.player.hitBox.x;
        runner.player.hitBox.y = runner.player.worldY + runner.player.hitBox.y;
        // Get the pit's hitbox's values
        pitHitBox.x = pitCol*runner.tileSize + pitHitBox.x;
        pitHitBox.y = pitRow*runner.tileSize + pitHitBox.y;

        // Check whether the two hitbox's intersect
        if (runner.player.hitBox.intersects(pitHitBox))
        {
            // If the player is moving in the required direction or the required direction is 'any,' hit = true
            if ((runner.player.direction.equals(reqDirection))||(reqDirection.equals("any")))
            {
                hit = true;
            }
        }

        // Reset the player's hitbox
        runner.player.hitBox.x = runner.player.hitBoxDefaultX;
        runner.player.hitBox.y = runner.player.hitBoxDefaultY;
        // Reset the pit's hitbox
        pitHitBox.x = pitHitBoxDefaultX;
        pitHitBox.y = pitHitBoxDefaultY;

        // Return hit
        return hit;
    }

    /**
     * Deals damage to the player if colliding with a pit
     * Precondition: The player must be colliding with a pit for this method to be used
     * Postcondition: The player loses health and a message is displayed on screen
     */
    public void pitFall()
    {
        // Display a message
        runner.screen.currentDialogue = "You fell into a pit !!";
        // Change into dialogue state
        runner.gameState = runner.dialogueState;
        // Reduce the player's health
        runner.player.currentHealth -= 1;
    }
}
