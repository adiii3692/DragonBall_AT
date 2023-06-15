// Import all necessary libraries
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// A class that helps scale the image to the right size 
public class Scaling {

    /**
     * Scale the image to the right size
     * Precondition: The original image, new width and height must be passed to the method
     * Postcondition: The original image has been resized to create a new image
     * @param og - The original image
     * @param width - The new width
     * @param height - The new height
     * @return resizedImage - The resized image
     */
    public BufferedImage scaleImage(BufferedImage og, int width, int height)
    {
        // Crreate a resized image
        BufferedImage resizedImage = new BufferedImage(width, height,og.getType());
        // Convert the resized Image into a Graphics2D object that can now be drawn
        Graphics2D g2 = resizedImage.createGraphics();
        // Draw the image
        g2.drawImage(og, 0, 0, width,height,null);
        // Clear memory
        g2.dispose();

        // Return the resized Image
        return resizedImage;
    }
}
