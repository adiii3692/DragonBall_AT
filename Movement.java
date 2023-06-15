// Taran's part - Pause State and Dialogue State; rest done by Aditya
//Import all necessary libraries
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//A class used to implement movement for the player
public class Movement implements KeyListener{

     // Runner class object
     Runner runner;
     //Check if the player has pressed the up key, down key, left key, the right key or the enter key
     public boolean upPressed, downPressed, leftPressed, rightPressed,enterPressed;

    /**
     * Constructor for the movement class
     * Precondition: A Runner class object must be passed to the method
     * Postcondition: The Runner class object has been instantiated
     * @param runner - The Runner class object
     */
    public Movement(Runner runner)
    {
          this.runner = runner;
    }

    /**
     * A necessary method that needs to be created to implement the KeyListener interface
     * We don't use this method as the keys aren't used to type anything
     * Hence, the method is empty
     */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    /**
     * A method that checks if a key has been pressed
     * Precondition: A KeyEvent object must be passed as an arguement to the method
     * Postcondition: The upPressed, downPressed, leftPressed and rightPressed instance variables are updated
     * @param e - A KeyEvent object that checks for any key input
     */
    @Override
    public void keyPressed(KeyEvent e) 
    {
     
     // Get the code of the key that has been pressed
     int code = e.getKeyCode();

     //TITLE STATE
     if (runner.gameState == runner.titleState)
     {
          // Check if it is the title Screen with the game name (NOT THE INSTRUCTIONS SCREEN)
          if (runner.screen.titleScreenState==0)
          {
               // Move the cursor up
               if (code== KeyEvent.VK_W)
               {
                    runner.screen.menuChoice--;
                    if (runner.screen.menuChoice<0)
                         runner.screen.menuChoice=1;
               }
               // Move the cursor down
               if (code== KeyEvent.VK_S)
               {
                    runner.screen.menuChoice++;
                    if (runner.screen.menuChoice>1)
                         runner.screen.menuChoice=0;
               } 
               // Start a NEW GAME or QUIT
               if (code==KeyEvent.VK_ENTER)
               {
                    // Check which choice has been selected
                    switch(runner.screen.menuChoice)
                    {
                         // GO ONTO THE INSTRUCTIONS SCREEN
                         case 0:
                              runner.screen.titleScreenState = 1;
                              break;
                         // QUIT THE GAME
                         case 1:
                              System.exit(0);
                              break;
                    }
               }
          }
          // INSTRUCTIONS SCREEN
          else if (runner.screen.titleScreenState==1)
          {
               // START THE GAME AFTER THAT
               if (code==KeyEvent.VK_ENTER)
               {
                    runner.gameState = runner.playState;
                    runner.playMusic(0);
               }
          }
     }

     // PLAY STATE
     if(runner.gameState == runner.playState)
     {
          // MOVE UP
          if (code== KeyEvent.VK_W)
          {
               upPressed = true;
          }
          // MOVE DOWN
          if (code== KeyEvent.VK_S)
          {
               downPressed = true;
          }
          // MOVE LEFT
          if (code== KeyEvent.VK_A)
          {
               leftPressed = true;
          }
          // MOVE DOWN
          if (code== KeyEvent.VK_D)
          {
               rightPressed = true;
          }
          // GO INTO PAUSE STATE
          if (code== KeyEvent.VK_SPACE)
          { 
               runner.gameState = runner.pauseState;    
          }
          // ENTER KEY HAS BEEN PRESSED - USED FOR CONVERSING WITH THE NPC
          if (code== KeyEvent.VK_ENTER)
          {
               enterPressed = true;
          }
     }

     // PAUSE STATE
     else if(runner.gameState == runner.pauseState)
     {
          // GO BACK TO PLAY STATE
          if (code== KeyEvent.VK_SPACE)
          {
               runner.gameState = runner.playState;
          }
     }

     // DIALOGUE STATE
     else if(runner.gameState == runner.dialogueState)
     {
          // GO INTO PLAY STATE IF ENTER IS PRESSED
          if(code == KeyEvent.VK_ENTER)
          {
               for(int i = 0; i < 88;i++)
               {
               runner.gameState = runner.playState;
               }
          }
     }

     //GAME OVER STATE OR VICTORY STATE
     if ((runner.gameState == runner.gameOverState)||(runner.gameState == runner.victoryState))
     {
          // MOVE CURSOR UP
          if (code== KeyEvent.VK_W)
          {
               runner.screen.menuChoice--;
               if (runner.screen.menuChoice<0)
                    runner.screen.menuChoice=1;
          }

          //MOVE CURSOR DOWN
          if (code== KeyEvent.VK_S)
          {
               runner.screen.menuChoice++;
               if (runner.screen.menuChoice>1)
                    runner.screen.menuChoice=0;
          } 

          // SELECT THE CORRECT MENU CHOICE 
          if (code==KeyEvent.VK_ENTER)
          {
               // WHICH MENU CHOICE
               switch(runner.screen.menuChoice)
               {
                    // RETRY THE GAME
                    case 0:
                         // Call the retry method from Runner class
                         runner.retry();
                         // Go back into play state
                         runner.gameState = runner.playState;
                         // Start playing the music
                         runner.playMusic(0);
                         break;
                    // QUIT THE GAME
                    case 1:
                         System.exit(0);
                         break;
               }
          }
     }
    }

    /**
     * A method that checks if the key has been released or stopped being pressed
     * Precondition: A KeyEvent object must be passed to the method
     * Postcondition: The upPressed, downPressed, leftPressed and rightPressed instance variables are updated
     * @param e - A KeyEvent object that checks for any key input
     */
    @Override
    public void keyReleased(KeyEvent e) 
    {
          //Get the code of the key that has been pressed
          int code = e.getKeyCode();
          //If the code is VK_W, that means the W key has stopped being pressed, and the player should not move up
          if (code== KeyEvent.VK_W)
          {
               upPressed = false;
          }
          //If the code is VK_S, that means the S key has stopped being pressed, and the player should not move down
          if (code== KeyEvent.VK_S)
          {
               downPressed = false;
          }
          //If the code is VK_A, that means the A key has stopped being pressed, and the player should not move left
          if (code== KeyEvent.VK_A)
          {
               leftPressed = false;
          }
          //If the code is VK_D, that means the D key has stopped being pressed, and the player should not move right
          if (code== KeyEvent.VK_D)
          {
               rightPressed = false;
          }
    }
    
}
