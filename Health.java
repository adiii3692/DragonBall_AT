//Import all necessary libraries
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

//A class used to create the door -  A subclass of the item superclass
public class Health extends Item{

    // The heart images
    public ArrayList<BufferedImage> healthImages = new ArrayList<BufferedImage>();
    // The health frame
    public BufferedImage frame;
    // Runner class object
    Runner runner;

    /**
     * Constructor which instantiates the instnace variables for the health frames and hearts and loads the images
     * Precondition: A Runner class object must be passed to the method
     * Postcondition: The images are loaded and the instnace variables are instantiated
     */
    public Health(Runner runner)
    {

        // Instantiate the runner class object
        this.runner = runner;
        //Name of the item
        name = "health";

        //Load the images of the hearts and health frame
        try{
            
            // Full heart
            BufferedImage full = ImageIO.read(getClass().getResourceAsStream("./health/1.png"));
            full = helper.scaleImage(full, runner.tileSize, runner.tileSize);
            // Half heart
            BufferedImage half = ImageIO.read(getClass().getResourceAsStream("./health/2.png"));
            half = helper.scaleImage(half, runner.tileSize, runner.tileSize);
            // Empty Hearts
            BufferedImage zero = ImageIO.read(getClass().getResourceAsStream("./health/3.png"));
            zero = helper.scaleImage(zero, runner.tileSize, runner.tileSize);
            // Add the images to the ArrayList
            healthImages.add(full);
            healthImages.add(half);
            healthImages.add(zero);
            // Get the health frame image 
            frame = ImageIO.read(getClass().getResourceAsStream("./health/frame.png"));
            // Scale the image
            frame = helper.scaleImage(frame, runner.tileSize, runner.tileSize*3/2);
            
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
