//Import all necessary libraries
import java.io.IOException;
import javax.imageio.ImageIO;

//A class used to create the portal -  A subclass of the item superclass
public class Portal extends Item{

    /**
     * Constructor which instantiates the instnace variables for the portal
     * Precondition: No arguements must be passed to the method
     * Postcondition: The instance variables have been instantiated
     */
    public Portal()
    {
        //Name of the item
        name = "portal";
        try
        {
            //Load the image of the portal
            image = ImageIO.read(getClass().getResourceAsStream("./portal/1.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        //The portal can be collided with
        collision = true;
    }
    
}
