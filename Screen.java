//import all necessary libraries
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

// A class that is used to draw the different game Screens
public class Screen {
    
    // Runner class object
    Runner runner;

    // Graphics2D class object
    Graphics2D g2;

    // Different fonts
    Font ariel_40, ariel_80B, ariel_20B,gameFont;
    // Checks whether message needs to be displayed
    public boolean messageOn = false;
    // The message that needs to be displayed (Ball has been collected, etc...)
    public String message = "";
    // The timer for how long the message has been displayed on screen
    int messageCounter = 0;
    // The current dialogue that needs to be displayed 
    public String currentDialogue;
    // The menu choice of the user (QUIT, RETRY, NEW GAME)
    public int menuChoice=0;
    // Whether on the main title screen or the Instructions title screen
    public int titleScreenState=0;
    // The titleScreenText
    public ArrayList<String> titleScreenText = new ArrayList <String>(Arrays.asList("Dragon Ball A-T !","NEW GAME","QUIT","MADE BY: ADITYA  &  TARAN","'W' AND 'S' KEYS FOR MOVING CURSOR"));
    // The instructionsScreenText
    public ArrayList<String> instructionsText = new ArrayList <String>(Arrays.asList("INSTRUCTIONS","1) 'W' to move UP","2) 'S' to move DOWN","3) 'A' to move LEFT","4) 'D' to move RIGHT","5) Get your objective from Dr. Law","6) Press Enter to talk with Dr. Law","7) DODGE PIRATE HUNTER ZORO","8) PRESS ENTER TO BEGIN !!!","START !!!"));
    // The cursor images
    public ArrayList<BufferedImage> cursorImages = new ArrayList <BufferedImage>();
    // The full health, half health, zero health, health frame, seven star dragon ball and vegito blue sprite images
    public BufferedImage fullHealth, halfHealth, zeroHealth,healthFrame, seventhBall, bluePowerUp;
    // A Scaling class object - used to scale images to the right size
    Scaling helper = new Scaling();
    

    /**
     * Load the cursor's images into an ArrayList
     * Precondition: An integer containing the index of the image being loaded has to be passed as an arguement
     * Postcondition: The cursor's images have been loaded
     * @param index - The index of the image being loaded (1.png, 2.png)
     */
    public void loadCursorImages(int index)
    {
        // Base case, to return if we have crossed the last index (2)
        if (index>3)
            return;
        // Load the cursor images
        try
        {
            cursorImages.add(ImageIO.read(getClass().getResourceAsStream("./cursor/"+index+".png")));
            index++;
            // Recursively call the method to load all sets of the images
            loadCursorImages(index);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Constructor that instantiates all the instance variables
     * Precondition: A Runner class object must be passed to the method
     * Postcondition: All instance variables have been instantiated
     * @param runner - A Runner class object
     */
    public Screen(Runner runner)
    {
        // Instantiate the Runner class object
        this.runner = runner;
        try
        {   
            // Load the game font
            InputStream stream = getClass().getResourceAsStream("./fonts/gameFont.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, stream);
            // Load the image of the seven star dragon ball
            seventhBall = ImageIO.read(getClass().getResourceAsStream("./balls/7.png"));
            // Load the image of vegito blue
            bluePowerUp = ImageIO.read(getClass().getResourceAsStream("./tag/blue.png"));
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        // Load the cursor's images
        loadCursorImages(1);

        // Load some additional fonts
        ariel_40 = new Font("Arial", Font.PLAIN, 40);
        ariel_80B = new Font("Arial", Font.BOLD, 80);
        ariel_20B = new Font("Arial", Font.BOLD,20);

        //Create hearts
        Health heart = new Health(runner);
        fullHealth = heart.healthImages.get(0);
        halfHealth = heart.healthImages.get(1);
        zeroHealth = heart.healthImages.get(2);
        healthFrame = heart.frame;
    }

    /**
     * Display message on the screen
     * Precondition: A String containing the message text should be passed as an arguement
     * Postcondition: The message can now be displayed on screen
     * @param text - The message that needs to be displayed on screen
     */
    public void showMessage(String text)
    {
        message = text;
        messageOn = true;
    }

    /**
     * Draw the different game screens on the display window
     * Precondition: A Graphics2D class object must be passed to the method
     * Postcondition: The different game screens are drawn on the display window
     * @param g2  - The Graphics2D class object
     */
    public void draw(Graphics2D g2)
    {
        // Instantiate the Graphics2D instance object
        this.g2 = g2;
        // Set the font to the gameFont
        g2.setFont(gameFont);
        // Set the color
        g2.setColor(Color.white);

        //Title State
        if (runner.gameState == runner.titleState)
        {
            // Draw the Title screen
            drawTitleScreen();
        }

        // GAME PLAY STATE
        if(runner.gameState == runner.playState)
        {
            // Draw the player's hearts and health frame
            drawPlayerHealth();
            // Draw the Play screen
            drawPlayScreen();   
        }

        // GAME PAUSE STATE
        if(runner.gameState == runner.pauseState)
        {
            // Draw the player's health
            drawPlayerHealth();
            // Draw the Pause screen
            drawPauseScreen();
        }
        // GAME DIALOGUE STATE
        if(runner.gameState == runner.dialogueState)
        {
            // Draw the player's health
            drawPlayerHealth();
            // Draw the Dialogue screen
            drawDialogueScreen();
        }

        //Game Over State
        if (runner.gameState == runner.gameOverState)
        {
            // Stop music
            runner.stopMusic();
            drawGameOverScreen(1);
        }

        // Victory State
        if (runner.gameState == runner.victoryState)
        {
            // Stop music
            runner.stopMusic();
            drawGameOverScreen(2);
        }

    }

    /**
     * Draw the player's health using the health frame and the heart sprites
     * Precondition: The heart sprites and the health frame sprite must be loaded
     * Postcondition: Draws and updates the player's health
     */
    public void drawPlayerHealth()
    {
        // X coordinates for the hearts
        int x = runner.tileSize*3/2;
        // Y coordinates for the hearts
        int y = runner.tileSize*3/2;
        int i = 0;

        // Draw the healthFrame
        g2.drawImage(healthFrame,runner.tileSize/2, runner.tileSize*4/3, null);

        //Draw the zero Hearts as a base - The actual hearts will be drawn over them
        while (i<runner.player.maxHealth/2)
        {
            // Draw 3 Hearts
            g2.drawImage(zeroHealth, x, y,null);
            i++;
            // Each heart is one tile apart
            x +=  runner.tileSize;
        }

        //Reset x values
        x = runner.tileSize*3/2;
        i = 0;

        // Draw current life
        while (i<runner.player.currentHealth)
        {
            // Draw half hearts first 
            g2.drawImage(halfHealth, x, y, null);
            i++;
            // Then check if a full heart can be drawn (Every 2 Health is worth one heart. 6 Max Health)
            if (i<runner.player.currentHealth)
            {
                // Draw full hearts
                g2.drawImage(fullHealth, x, y, null);
            }
            i++;
            x += runner.tileSize;
        }
    }

    /**
     * Draw the Title and instructions text on the display window 
     * Preconditions: The display text ArrayList, and the shadow and main text Colors must be passed to the method
     * @param text - An ArrayList of the text that needs to be displayed
     * @param shadow - The Color of the shadow
     * @param main - The Color of the main text
     */
    public void drawText(ArrayList<String> text,Color shadow, Color main)
    {
        // Y coordinates
        int y=0;

        // If it is the title screen Text
        if (text.size()==5)
        {
            // Draw Vegito Blue
            g2.drawImage(bluePowerUp, runner.screenWidth/2 - runner.tileSize*3/2, runner.tileSize*7/2, null);
        }
        // Loop through the ArrayList
        for (int i=0;i<text.size();i++)
        {
            // Get the text that needs to be displayed
            String displayText = text.get(i);
            // Get the Centered X coordinates
            int x = getXpaused(displayText);
            
            // If it's the Title Screen Text and the main Title
            if (i==0 && text.size()==5)
            {
                // Set the font
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,96));
                // Get the Updated Coordinates
                x = getXpaused(displayText);
                y = runner.tileSize*3;
            }
            else if (i==1 && text.size()==5)
            {
                //NEW GAME
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
                // Get the Updated Coordinates
                x = getXpaused(displayText);
                y += runner.tileSize*5;
                // This is the first menu choice and the cursor should be here
                if (menuChoice==0)
                {
                    // Draw the cursor
                    g2.drawImage(cursorImages.get(0), x-runner.tileSize*3/2, y-runner.tileSize, null);
                }
            }
            else if (i==2 && text.size()==5)
            {
                //QUIT
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
                // Get the Updated Coordinates
                y += runner.tileSize*2;
                // This is the second menu choice and the cursor should be here
                if (menuChoice==1)
                {
                    // Draw the cursor
                    g2.drawImage(cursorImages.get(1), x-runner.tileSize*3/2, y-runner.tileSize, null);
                }
            }
            else if (i==3 && text.size()==5)
            {
                //MADE BY
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
                // Get the Updated Coordinates
                x = 20;
                y += runner.tileSize;
            }
             else if (i==4 && text.size()==5)
            {
                // CURSOR MOVEMENT
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
                // Get the Updated Coordinates
                x = runner.tileSize*10;
            }
            // NOW THE INSTRUCTIONS SCREEN
            else if (i==0 && text.size()>5)
            {
                //Title name
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,60f));
                // Get the Updated Coordinates
                x = getXpaused(displayText);
                y = runner.tileSize+15;
            }
            else if (i==1 && text.size()>5)
            {
                //The Menu
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
                // Get the Updated Coordinates
                y += 69;
            }
            else if (i>1 && i<9 && text.size()>5)
            {
                //The Menu
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
                // Get the Updated Coordinates
                y += runner.tileSize;
            }
            else
            {
                //The Menu
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
                // Get the Updated Coordinates
                y += runner.tileSize*3/2;
                // This is the first menu choice
                if (menuChoice==0)
                {
                    // Draw the cursor image
                    g2.drawImage(cursorImages.get(2), x-runner.tileSize*3/2, y-runner.tileSize, null);
                }
            }

            //The shadow
            g2.setColor(shadow);
            //Draw the same text a bit shifted to create a shadow effect
            g2.drawString(displayText, x+3, y+3);
            //The actual text 
            g2.setColor(main);
            g2.drawString(displayText, x, y);
        }
    }

    /**
     * Draw the title screen 
     * Precondition: Must be in titleState
     * Postcondition: Draws the title screen
     */
    public void drawTitleScreen()
    {
        // If the first title screen - THE MAIN TITLE SCREEN
        if (titleScreenState==0)
        {
            //Background Colored Rect
            g2.setColor(new Color(70,120,80));
            g2.fillRect(0, 0, runner.screenWidth, runner.screenHeight);

            // Draw the text on the display window
            drawText(titleScreenText, Color.black, Color.white);
            
        }
        // If the INSTRUCTIONS SCREEN
        else if (titleScreenState==1)
        {
            //Background Colored Rect
            g2.setColor(new Color(70,120,80));
            g2.fillRect(0, 0, runner.screenWidth, runner.screenHeight);

            // Draw the text on the display window
            drawText(instructionsText, Color.black, Color.white);
        }
        
    }

    /**
     * Draw the play Screen
     * Precondition: Must be in play state
     * Postcondition: The play screen is displayed
     */
    public void drawPlayScreen()
    {
        // Set the font
        g2.setFont(ariel_20B);
        g2.setFont(g2.getFont().deriveFont(40f));
        // Set the color
        g2.setColor(Color.white);
        // Draw the Seven Star Dragon Ball
        g2.drawImage(helper.scaleImage(seventhBall,runner.tileSize,runner.tileSize), runner.tileSize/2, runner.tileSize/3, null);
        // Draw the current dragon ball count on the display window
        g2.drawString(" = " + runner.player.hasBalls, runner.tileSize*3/2, runner.tileSize*115/100);
        
        // Revert the font size for a message
        g2.setFont(ariel_20B);

        // MESSAGE
        if(messageOn == true)
        {
            // The postiion of the message
            g2.drawString(message, runner.tileSize/2, runner.tileSize*11);
            // Increase message Counter
            messageCounter++;
            // Display the message only for 2 seconds (44 FPS)
            if( messageCounter> 88)
            {   
                messageCounter = 0;
                messageOn = false;
            }
        }

    }

    /**
     * Draw the pause screen
     * Precondition: Must be in pause state
     * Postcondition: Display the pause screen
     */
    public void drawPauseScreen()
    {
        // Set the font
        g2.setFont(ariel_80B);
        // The message
        String text = "PAUSED";
        // Coordinates
        int x = getXpaused(text);
        int y = runner.screenHeight/2;
        // Draw the message
        g2.drawString(text, x, y);
    }

    /**
     * Draw the dialogue on the display window
     * Precondition: Must be in dialogue state
     * Postcondition: Draw the dialogues
     */
    public void drawDialogueScreen()
    {
        // WINDOW Coordinates
        int x = runner.tileSize;
        int y = runner.tileSize/2;

        // Window width and height
        int width = runner.screenWidth - (runner.tileSize*2);
        int height = runner.tileSize*4;

        // Draw a subwindow
        drawSubWindow(x,y,width,height);

        // Set the font
        g2.setFont(ariel_80B);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32));
        // Get the coordinates for the text
        x += runner.tileSize;
        y += runner.tileSize; 
        // Display the currentDialogue on the subwindow
        for(String line : currentDialogue.split("\n"))
        {
            // Draw the currentDialogue
            g2.drawString(line, x, y);
            // Increment the y coordinate after each line
            y += 40;
        }
    }

    /**
     * Draw a subwindow
     * Precondition: The subwindow's coordinates, width and height must be passed to the method
     * @param x - X Coordinate
     * @param y - Y Coordinate
     * @param w - Width of the subwindow
     * @param h - Height of the subwindow
     */
    public void drawSubWindow(int x, int y, int w, int h)
    {
        // Black somewhat transparent subwindow color
        Color c = new Color(0,0,0,215); 
        g2.setColor(c);
        // Create a rectangle with rounded edges
        g2.fillRoundRect(x, y, w, h, 35, 35);
        // White border of the subwindow
        c = new Color(255,255,255);
        // Change the opacity and width
        g2.setStroke(new BasicStroke(5));
        g2.setColor(c);
        // Draw a rounded rectangle as a border 
        g2.drawRoundRect(x+5, y+5, w-10, h-10, 25, 25);
    }

    /**
     * Return the x coordinates to display text centered on the display window
     * Precondition: The display text must be passed to the method
     * Postcondition: The x coordinate is returned
     * @param text - The display text
     * @return x - The x coordinate
     */
    public int getXpaused(String text)
    {
        // Get the centered X coordinate depending on the font size and the display text's length
        int x = runner.screenWidth/2 - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth())/2;
        // Return the x coordinate
        return x;
    }


    /**
     * Draw the game over screen or the victory screen
     * Precondition: An integer containing whether it is a game over screen or victory screen must be passed to the method
     * Postcondition: Draw the game over or victory screen on the window display
     * @param situation - An integer whether it is game over or victory
     */
    public void drawGameOverScreen(int situation)
    {
        // Background colored rect
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, runner.screenWidth, runner.screenHeight);

        // Coordinates of the display text
        int x,y; 
        String displayText;
        // Set font
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));

        // Game Over
        if (situation == 1)
        {
            displayText = "GAME OVER";
        }
        // Victory
        else
        {
            displayText = "VICTORY !";
            // g2.setFont(g2.getFont().deriveFont(Font.BOLD,60f));
        }

        // Update coordinates
        x = getXpaused(displayText);
        y = runner.tileSize*4;

         //The shadow
        g2.setColor(Color.black);
        //Draw the same text a bit shifted to create a shadow effect
        g2.drawString(displayText, x+5, y+5);
        //The actual text 
        g2.setColor(Color.white);
        g2.drawString(displayText, x, y);

        // Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        displayText = "RETRY";
        x = getXpaused(displayText);
        y += runner.tileSize*4;
        //The shadow
        g2.setColor(Color.black);
        //Draw the same text a bit shifted to create a shadow effect
        g2.drawString(displayText, x+5, y+5);
        //The actual text 
        g2.setColor(Color.white);
        g2.drawString(displayText, x, y);
        // Retry is the first menu choice
        if (menuChoice==0)
        {
            // Draw the cursor image
            g2.drawImage(cursorImages.get(0), x-runner.tileSize*3/2, y-runner.tileSize, null);
        }

        //Quit
        displayText = "QUIT";
        x = getXpaused(displayText);
        y += runner.tileSize*2;
        //The shadow
        g2.setColor(Color.black);
        //Draw the same text a bit shifted to create a shadow effect
        g2.drawString(displayText, x+5, y+5);
        //The actual text 
        g2.setColor(Color.white);
        g2.drawString(displayText, x, y);
        // QUIT is the second menu choice
        if (menuChoice==1)
        {
            // Draw the cursor image
            g2.drawImage(cursorImages.get(1), x-runner.tileSize*3/2, y-runner.tileSize, null);
        }
    }
}
