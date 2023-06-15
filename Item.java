//Import all the necessary libraries
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//A superclass for all items such as dragon balls, doors and portals
public class Item {
    //Stores the image of the item
    public BufferedImage image;
    //Name of the item
    public String name;
    //Whether the item can be collided with or not, and if the item can be walked over or not
    public boolean collision = false;
    //The position of the item on the world map
    public int worldX, worldY;
    //The item's hitbox
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
    //The coordinates of the hitbox
    public int hitBoxDefaultX = 0;
    public int hitBoxDefaultY = 0; 
    //Scaling class object
    Scaling helper = new Scaling();

    /**
     * Draw the items on the window 
     * Precondtion: A Graphics2D object and Runner object must be passed to the method
     * @param g2 - A Graphics2D class object
     * @param runner - A runner class object
     */
    public void draw(Graphics2D g2, Runner runner)
    {
        //The item's position on the screen
        int screenX = worldX -  runner.player.worldX + runner.player.screenX;
        int screenY = worldY - runner.player.worldY + runner.player.screenY;

        //Check if the item is on screen when the player is moving and if it is, draw it
        if ((worldX + runner.tileSize> (runner.player.worldX-runner.player.screenX)) &&
            (worldX - runner.tileSize< (runner.player.worldX+runner.player.screenX)) &&
            (worldY + runner.tileSize> (runner.player.worldY-runner.player.screenY)) &&
            (worldY - runner.tileSize< (runner.player.worldY+runner.player.screenY)))
        {
            //Draw the item on the window
            g2.drawImage(image,screenX,screenY,runner.tileSize,runner.tileSize,null);
        }
    }
}
