// Work done by Aditya - Constructor, tileCollision() method and itemCollision(); Taran's part - checkCharacter() and checkPlayer()
//A class which checks for any collision - Collision with items such as Dragon Balls and tiles such as water/trees

//Import the Arrays Class
import java.util.Arrays;

public class Collision {

    //A Runner class object
    Runner runner;

    /**
     * A constructor which instantiates the Runner class object
     * Precondition: A Runner class object must be passed as an arguement to the method
     * Postcondition: The runner class object has been instantiated
     * @param runner - The Runner class object
     */
    public Collision(Runner runner)
    {
        //Instantiate the runner class object, which is an instance variable
        this.runner = runner;
    }

    /**
     * A method that checks whether the player is colliding with a tile that cannot be walked on
     * Precondition: A character class object must be passed to the method
     * Postcondition: Updates the character class object's instance variable that checks for collisions (colliding)
     * @param character
     */
    public void tileCollision(Character character)
    {
        //The character's left side hitbox's x coordinate on the world map - Obtained by adding the character's world X and the hitbox's x coordinate
        int characterLeftWorldX = character.worldX + character.hitBox.x;
        //The character's right side hitbox's x coordinate on the world map 
        int characterRightWorldX = character.worldX + character.hitBox.x + character.hitBox.width;
        //The character's top side's y coordinate on the world map
        int characterTopWorldY = character.worldY + character.hitBox.y;
        //The character's bottom side's y coordinate on the world map
        int characterBottomWorldY = character.worldY + character.hitBox.y + character.hitBox.height; 

        //The column in which the character's left side is - Obtained by dividing characterLeftWorldX by the tileSize
        int characterLeftCol = characterLeftWorldX/runner.tileSize;
        //The column in which the character's right side is - Obtained by dividing characterRightWorldX by the tileSize
        int characterRightCol = characterRightWorldX/runner.tileSize;
        //The row in which the character's top side is - Obtained by dividing characterTopWorldY by the tile size
        int characterTopRow = characterTopWorldY/runner.tileSize;
        //The row in which the character's bottom side is - Obtained by dividing characterBottomWorldY by the tile size
        int characterBottomRow = characterBottomWorldY/runner.tileSize;

        //Two tile numbers - The two tiles which the character will collide with 
        int tileNum1, tileNum2; 

        //Depending on the character's direction, determine which two tiles the character "will" collide into 
        switch (character.direction)
        {
            //If the character is going up
            case "up":
                //Change the character top row to get the row in which the character will be according to its speed
                characterTopRow = (characterTopWorldY - character.speed)/runner.tileSize;
                //The tile which the left side of the character will collide into 
                tileNum1 = runner.tileM.worldMap[characterTopRow][characterLeftCol];
                //The tile which the right side of the character will collide into 
                tileNum2 = runner.tileM.worldMap[characterTopRow][characterRightCol];
                // If the character is the player and if the player has transformed into blue, let them walk over water
                if ((character.name.equals("player"))&&(runner.player.hasBalls>=2))
                {
                    // Let the player walk over water now
                    if (((tileNum1!=2) || (tileNum1!=2))&&((runner.tileM.tile[tileNum1].collision==true) || (runner.tileM.tile[tileNum2].collision==true)))
                    {
                        character.colliding = true;
                    }
                } 
                //Check if the two tiles can be walked on or not. If we can't walk over the tiles, then change the colliding instance variable of the character
                else if((runner.tileM.tile[tileNum1].collision==true) || (runner.tileM.tile[tileNum2].collision==true))
                    character.colliding = true;
                break;
            //If the character is going down
            case "down":
                //Change the character bottom row to get the row in which the character will be according to its speed
                characterBottomRow = (characterBottomWorldY + character.speed)/runner.tileSize;
                //The tile which the left side of the character will collide into 
                tileNum1 = runner.tileM.worldMap[characterBottomRow][characterLeftCol];
                //The tile which the right side of the character will collide into 
                tileNum2 = runner.tileM.worldMap[characterBottomRow][characterRightCol];
                // If the character is the player and if the player has transformed into blue, let them walk over water
                if ((character.name.equals("player"))&&(runner.player.hasBalls>=2))
                {
                    // Let the player walk over water now
                    if (((tileNum1!=2) || (tileNum1!=2))&&((runner.tileM.tile[tileNum1].collision==true) || (runner.tileM.tile[tileNum2].collision==true)))
                    {
                        character.colliding = true;
                    }
                } 
                //Check if the two tiles can be walked on or not. If we can't walk over the tiles, then change the colliding instance variable of the character
                else if ((runner.tileM.tile[tileNum1].collision==true) || (runner.tileM.tile[tileNum2].collision==true))
                    character.colliding = true;
                break;
            //If the character is going left
            case "left":
                //Change the character left col to get the column in which the character will be according to its speed
                characterLeftCol = (characterLeftWorldX - character.speed)/runner.tileSize;
                //The tile which the top of the character will collide into 
                tileNum1 = runner.tileM.worldMap[characterTopRow][characterLeftCol];
                //The tile which the bottom of the character will collide into
                tileNum2 = runner.tileM.worldMap[characterBottomRow][characterLeftCol];
                // If the character is the player and if the player has transformed into blue, let them walk over water
                if ((character.name.equals("player"))&&(runner.player.hasBalls>=2))
                {
                    // Let the player walk over water now
                    if (((tileNum1!=2) || (tileNum1!=2))&&((runner.tileM.tile[tileNum1].collision==true) || (runner.tileM.tile[tileNum2].collision==true)))
                    {
                        character.colliding = true;
                    }
                } 
                //Check if the two tiles can be walked on or not. If we can't walk over the tiles, then change the colliding instance variable of the character
                else if ((runner.tileM.tile[tileNum1].collision==true) || (runner.tileM.tile[tileNum2].collision==true))
                    character.colliding = true;
                break;
            //If the character is going right
            case "right":
                //Change the character right col to get the column in which the character will be according to its speed
                characterRightCol = (characterRightWorldX + character.speed)/runner.tileSize;
                //The tile which the top of the character will collide into 
                tileNum1 = runner.tileM.worldMap[characterTopRow][characterRightCol];
                //The tile which the bottom of the character will collide into
                tileNum2 = runner.tileM.worldMap[characterBottomRow][characterRightCol];
                // If the character is the player and if the player has transformed into blue, let them walk over water
                if ((character.name.equals("player"))&&(runner.player.hasBalls>=2))
                {
                    // Let the player walk over water now
                    if (((tileNum1!=2) || (tileNum1!=2))&&((runner.tileM.tile[tileNum1].collision==true) || (runner.tileM.tile[tileNum2].collision==true)))
                    {
                        character.colliding = true;
                    }
                } 
                //Check if the two tiles can be walked on or not. If we can't walk over the tiles, then change the colliding instance variable of the character
                else if ((runner.tileM.tile[tileNum1].collision==true) || (runner.tileM.tile[tileNum2].collision==true))
                    character.colliding = true;
                break; 
        }
    }

    /**
     * Checks if the character is collding with an object and if the character is the player
     * Precondition: A Character class object and a boolean must be passed to the method
     * Postcondition: Returns an index which may be the index of the object the player is colliding with
     * @param character - A Character class object (May be an NPC or the player)
     * @param player - A boolean which checks if it is the player or not
     * @return index - Either 999 or the index of the object which the player is colldiing with
     */
    public int itemCollision(Character character,boolean player)
    {
        //Declare an instantiate the index variable to be 999 by default
        int index = 999;

        //Loop through the items list in the runner class
        for (int i=0;i<runner.items.length;i++)
        {
            //If the item isn't null, check for collision
            if (runner.items[i] != null)
            {
                //Get the character's hitbox's x coordinate
                character.hitBox.x = character.worldX + character.hitBox.x;
                //Get the character's hitbox's y coordinate
                character.hitBox.y = character.worldY + character.hitBox.y;    

                //Get the item's hitbox's x coordinate
                runner.items[i].hitBox.x = runner.items[i].worldX + runner.items[i].hitBox.x;
                //Get the item's hitbox's y coordinate
                runner.items[i].hitBox.y = runner.items[i].worldY + runner.items[i].hitBox.y;

                //Depending on the direction
                switch(character.direction)
                {
                    //If going up, check whether the character "WILL" collide with an item
                    case "up":
                        //Get the character's future position
                        character.hitBox.y -= character.speed;
                        //Check if the character "will" collide with an item
                        if (character.hitBox.intersects(runner.items[i].hitBox))
                        {
                            //Check if the item can be collided with and if it can, change the character's colliding instance variable
                            if (runner.items[i].collision==true)
                            {
                                //Change the character's colliding instance variable
                                character.colliding = true;
                            }
                            //If the character is the player, return the index of the item
                            if (player==true)
                            {
                                index = i;
                            }
                        }
                        break;
                    //If going up, check whether the character "WILL" collide with an item
                    case "down":
                        //Get the character's future position
                        character.hitBox.y += character.speed;
                        //Check if the character "will" collide with an item
                        if (character.hitBox.intersects(runner.items[i].hitBox))
                        {
                            //Check if the item can be collided with and if it can, change the character's colliding instance variable
                            if (runner.items[i].collision==true)
                            { 
                                //Change the character's colliding instance variable
                                character.colliding = true;
                            }
                            //If the character is the player, return the index of the item
                            if (player==true)
                            {
                                index = i;
                            }
                        }
                        break;
                    //If going left, check whether the character "WILL" collide with an item
                    case "left":
                        //Get the character's future position
                        character.hitBox.x -= character.speed;
                        //Check if the character "will" collide with an item
                        if (character.hitBox.intersects(runner.items[i].hitBox))
                        {
                            //Check if the item can be collided with and if it can, change the character's colliding instance variable
                            if (runner.items[i].collision==true)
                            {   
                                //Change the character's colliding instance variable
                                character.colliding = true;
                            }
                            //If the character is the player, return the index of the item
                            if (player==true)
                            {
                                index = i;
                            }
                        }
                        break;
                    //If going left, check whether the character "WILL" collide with an item
                    case "right":
                        //Get the character's future position
                        character.hitBox.x += character.speed;
                        //Check if the character "will" collide with an item
                        if (character.hitBox.intersects(runner.items[i].hitBox))
                        {
                            //Check if the item can be collided with and if it can, change the character's colliding instance variable
                            if (runner.items[i].collision==true)
                            {
                                //Change the character's colliding instance variable
                                character.colliding = true;
                            }
                            //If the character is the player, return the index of the item
                            if (player==true)
                            {
                                index = i;
                            }
                        }
                        break;
                }
                //Change the character's hitbox back to the default values
                character.hitBox.x = character.hitBoxDefaultX;
                character.hitBox.y = character.hitBoxDefaultY;
                //Change the item's hitbox back to the default values
                runner.items[i].hitBox.x = runner.items[i].hitBoxDefaultX;
                runner.items[i].hitBox.y = runner.items[i].hitBoxDefaultY;
            }
        }

        //Return either 999 or the index of the object the player is colliding with
        return index;
    }

    /**
     * Checks character to character collision (npc to enemy, player to npc ...)
     * Precondition: A Character class object and an array of the target characters must be passed
     * Postcondition: The character's 'colliding' instance variable is switched to true
     * @param Character - The Character class object that we check is colliding with the target
     * @param target - The Character class object that the 'Character' collides with
     * @return index - The index of the the target character that is being collided with
     */
    public int checkCharacter(Character Character, Character[] target)
    {
        // A random index - This might change to the index of the target if colliding
        int index = 999;

        // Loop through the targets
        for (int i=0;i<target.length;i++)
        {
            // If the target is not null and the Character is himself not the target
            if ((target[i] != null) && ((Arrays.asList(target).indexOf(Character))!=i))
            {
                //Get Character's solid area position
                Character.hitBox.x = Character.worldX + Character.hitBox.x;
                Character.hitBox.y = Character.worldY + Character.hitBox.y;    

                //Get the target's solid area position
                target[i].hitBox.x = target[i].worldX + target[i].hitBox.x;
                target[i].hitBox.y = target[i].worldY + target[i].hitBox.y;

                // Check the direction of the character
                switch(Character.direction)
                {
                    case "up":
                        // Predict hitbox direction
                        Character.hitBox.y -= Character.speed;
                        // Check for collision
                        if (Character.hitBox.intersects(target[i].hitBox))
                        {
                                // Update the character's colliding instance variable
                                Character.colliding = true;
            
                                index = i;
                            
                        }
                        break;
                    case "down":
                        // Predict hitbox direction
                        Character.hitBox.y += Character.speed;
                        // Check for collision
                        if (Character.hitBox.intersects(target[i].hitBox))
                        {
                                // Update the character's colliding instance variable
                                Character.colliding = true;
                            
                                index = i;
                            
                        }
                        break;
                    case "left":
                        // Predict hitbox direction
                        Character.hitBox.x -= Character.speed;
                        // Check for collision
                        if (Character.hitBox.intersects(target[i].hitBox))
                        {
                                // Update the character's colliding instance variable
                                Character.colliding = true;
                            
                                index = i;
                        
                        }
                        break;
                    case "right":
                        // Predict hitbox direction
                        Character.hitBox.x += Character.speed;
                        // Check for collision
                        if (Character.hitBox.intersects(target[i].hitBox))
                        {
                                // Update the character's colliding instance variable
                                Character.colliding = true;
                            
                                index = i;
                            
                        }
                        break;
                }

                //Rese the character's hitbox
                Character.hitBox.x = Character.hitBoxDefaultX;
                Character.hitBox.y = Character.hitBoxDefaultY;
                // Reset the target's hitbox
                target[i].hitBox.x = target[i].hitBoxDefaultX;
                target[i].hitBox.y = target[i].hitBoxDefaultY;
            }
        }

        // Return the index
        return index;
    }

    /**
     * Checks player to character collision 
     * Precondition: A Character class object must be passed
     * Postcondition: The character's 'colliding' instance variable is switched to true
     * @param Character - The Character class object that we check is colliding with the target
     * @return touchingPlayer - A boolean that checks whether the player is colliding with the character
     */
    public boolean checkPlayer(Character Character)
    {
        // A boolean that checks whether the player is colliding with the character
        boolean touchingPlayer = false;

        //Get Character's solid area position
        Character.hitBox.x = Character.worldX + Character.hitBox.x;
        Character.hitBox.y = Character.worldY + Character.hitBox.y;    

        //Get the object's solid area position
        runner.player.hitBox.x = runner.player.worldX + runner.player.hitBox.x;
        runner.player.hitBox.y = runner.player.worldY + runner.player.hitBox.y;

        // Check the character's direction
        switch(Character.direction)
        {
            case "up":
                // Predict hitbox direction
                Character.hitBox.y -= Character.speed;
                // Check for collision
                if (Character.hitBox.intersects(runner.player.hitBox))
                {
                    // Update the character's colliding instance variable
                    Character.colliding = true;
                    // Update touchingPlayer to true
                    touchingPlayer = true;
                }
                break;
            case "down":
                // Predict hitbox direction
                Character.hitBox.y += Character.speed;
                // Check for collision
                if (Character.hitBox.intersects(runner.player.hitBox))
                {        
                    // Update the character's colliding instance variable
                    Character.colliding = true;
                    // Update touchingPlayer to true
                    touchingPlayer = true;
                }
                break;
            case "left":
                // Predict hitbox direction
                Character.hitBox.x -= Character.speed;
                // Check for collision
                if (Character.hitBox.intersects(runner.player.hitBox))
                {
                    // Update the character's colliding instance variable
                    Character.colliding = true;
                    // Update touchingPlayer to true
                    touchingPlayer = true;
                }
                break;
            case "right":
                // Predict hitbox direction
                Character.hitBox.x += Character.speed;
                // Check for collision
                if (Character.hitBox.intersects(runner.player.hitBox))
                {                 
                    // Update the character's colliding instance variable   
                    Character.colliding = true;
                    // Update touchingPlayer to true
                    touchingPlayer = true;
                }
                break;
        }

        // Reset the character's hitbox
        Character.hitBox.x = Character.hitBoxDefaultX;
        Character.hitBox.y = Character.hitBoxDefaultY;
        // Reset the player's hitbox
        runner.player.hitBox.x = runner.player.hitBoxDefaultX;
        runner.player.hitBox.y = runner.player.hitBoxDefaultY;

        // Return touchingPlayer
        return touchingPlayer;
    }
}
