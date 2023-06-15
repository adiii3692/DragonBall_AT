//Import all necessary libraries
import java.io.IOException;
import javax.imageio.ImageIO;

//A class used to create the door -  A subclass of the item superclass
public class Door extends Item{

    /**
     * Constructor which instantiates the instnace variables for the door
     * Precondition: No arguements must be passed to the method
     * Postcondition: The instance variables have been instantiated
     */
    public Door()
    {
        //Name of the item
        name = "door";
        try{
            //Load the image of the door
            image = ImageIO.read(getClass().getResourceAsStream("./door/1.png"));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        //The door can be collided with
        collision = true;
    }
    
}
