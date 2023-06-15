//Import all necessary libraries
import java.io.IOException;
import javax.imageio.ImageIO;

//A class used to create dragon balls - A subclass of the Item superclass
public class DragonBall extends Item{

    /**
     * A constructor used to instantiate the instance variables
     * Precondition: No arguements must be passed to the method
     * Postcondition: The instance variables have been instantiated
     * @param number - The number of the dragon ball we wish to create (1 star dragonball, 7 star dragonball...)
     */
    public DragonBall(int number)
    {
        //Name of the item
        name = "ball";
        //Get the path to the images depending on the ball number
        String path = "./balls/"+number+".png";

        try
        {
            //Load the images
            image = ImageIO.read(getClass().getResourceAsStream(path));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        //the dragon balls can be collided into
        collision = true;
    }
    
}
