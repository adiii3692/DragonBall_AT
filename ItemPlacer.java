// setNpc() method done by Taran; rest done by Aditya
//A class to place the items on the world map
public class ItemPlacer {
    
    //A runner class object
    Runner runner;

    /**
     * Constructor to instantiate the runner class object
     * Precondition: A runner class object must be passed to the constructor
     * Postcondition: A runner class object has been instantiated
     * @param runner
     */
    public ItemPlacer(Runner runner)
    {
        //Instnatiate the runner class object 
        this.runner = runner;
    }

    /**
     * Place the item at the right place on the worldMap
     * Precondition: A runner class object must have been instantiated
     * Postcondition: All items have been placed in their appropriate positions
     */
    public void placeItem()
    {
        //Dragon Ball 1
        runner.items[0] = new DragonBall(1);
        runner.items[0].worldX = 50*runner.tileSize;
        runner.items[0].worldY = 16*runner.tileSize;

        //Dragon Ball 2
        runner.items[1] = new DragonBall(2);
        runner.items[1].worldX = 41*runner.tileSize;
        runner.items[1].worldY = 9*runner.tileSize;

        //Dragon Ball 3
        runner.items[2] = new DragonBall(3);
        runner.items[2].worldX = 47*runner.tileSize;
        runner.items[2].worldY = 48*runner.tileSize;
        
        //Dragon Ball 4
        runner.items[3] = new DragonBall(4);
        runner.items[3].worldX = 53*runner.tileSize;
        runner.items[3].worldY = 33*runner.tileSize;

        //Dragon Ball 5
        runner.items[4] = new DragonBall(5);
        runner.items[4].worldX = 16*runner.tileSize;
        runner.items[4].worldY = 36*runner.tileSize;

        //Dragon Ball 6
        runner.items[5] = new DragonBall(6);
        runner.items[5].worldX = 10*runner.tileSize;
        runner.items[5].worldY = 46*runner.tileSize;

        //Dragon Ball 7
        runner.items[6] = new DragonBall(7);
        runner.items[6].worldX = 40*runner.tileSize;
        runner.items[6].worldY = 35*runner.tileSize;

        //Door
        runner.items[7] = new Door();
        runner.items[7].worldX = 18*runner.tileSize;
        runner.items[7].worldY = 14*runner.tileSize;

        //Portal
        runner.items[8] = new Portal();
        runner.items[8].worldX = 18*runner.tileSize;
        runner.items[8].worldY = 11*runner.tileSize;
    }

    /**
     * Place the NPC at the right place on the worldMap
     * Precondition: A runner class object must have been instantiated
     * Postcondition: The NPC has been placed in its appropriate position
     */
    public void setNPC()
    {
        runner.npc[0] = new NPC(runner); 
        runner.npc[0].worldX = runner.tileSize*21;
        runner.npc[0].worldY = runner.tileSize*21;
        
    }

    /**
     * Place the enemies at the right place on the worldMap
     * Precondition: A runner class object must have been instantiated
     * Postcondition: All enemies have been placed in their appropriate positions
     */
    public void placeEnemies()
    {
        // Dragon Ball 7 enemy 1
        runner.enemies[0] = new Enemy(runner);
        runner.enemies[0].worldX = runner.tileSize*40;
        runner.enemies[0].worldY = runner.tileSize*32;
        runner.enemies[0].direction = "down";

        // Dragon Ball 7 enemy 2
        runner.enemies[1] = new Enemy(runner);
        runner.enemies[1].worldX = runner.tileSize*40;
        runner.enemies[1].worldY = runner.tileSize*37;
        runner.enemies[1].direction = "up";

        // NPC enemy 1
        runner.enemies[2] = new Enemy(runner);
        runner.enemies[2].worldX = runner.tileSize*21;
        runner.enemies[2].worldY = runner.tileSize*31;
        runner.enemies[2].direction = "up";

        // Dragon Ball 6 enemy
        runner.enemies[3] = new Enemy(runner);
        runner.enemies[3].worldX = runner.tileSize*10;
        runner.enemies[3].worldY = runner.tileSize*44;
        runner.enemies[3].direction = "down";

        // Dragon Ball 6 enemy
        runner.enemies[4] = new Enemy(runner);
        runner.enemies[4].worldX = runner.tileSize*12;
        runner.enemies[4].worldY = runner.tileSize*46;
        runner.enemies[4].direction = "left";

        // Dragon Ball 3 enemy
        runner.enemies[5] = new Enemy(runner);
        runner.enemies[5].worldX = runner.tileSize*43;
        runner.enemies[5].worldY = runner.tileSize*48;
        runner.enemies[5].direction = "right";

        // Dragon Ball 3 enemy
        runner.enemies[6] = new Enemy(runner);
        runner.enemies[6].worldX = runner.tileSize*43;
        runner.enemies[6].worldY = runner.tileSize*46;
        runner.enemies[6].direction = "down";

        // NPC enemy 2
        runner.enemies[7] = new Enemy(runner);
        runner.enemies[7].worldX = runner.tileSize*21;
        runner.enemies[7].worldY = runner.tileSize*10;
        runner.enemies[7].direction = "down";

        // Dragon Ball 1 enemy
        runner.enemies[8] = new Enemy(runner);
        runner.enemies[8].worldX = runner.tileSize*48;
        runner.enemies[8].worldY = runner.tileSize*16;
        runner.enemies[8].direction = "right";

        // Dragon Ball 2 enemy
        runner.enemies[9] = new Enemy(runner);
        runner.enemies[9].worldX = runner.tileSize*43;
        runner.enemies[9].worldY = runner.tileSize*9;
        runner.enemies[9].direction = "left";

        // Dragon Ball 2 alley enemy 1
        runner.enemies[10] = new Enemy(runner);
        runner.enemies[10].worldX = runner.tileSize*41;
        runner.enemies[10].worldY = runner.tileSize*14;
        runner.enemies[10].direction = "up";

        // Dragon Ball 1 alley below enemy
        runner.enemies[11] = new Enemy(runner);
        runner.enemies[11].worldX = runner.tileSize*49;
        runner.enemies[11].worldY = runner.tileSize*20;
        runner.enemies[11].direction = "left";

        // Dragon Ball 1 alley above enemy
        runner.enemies[12] = new Enemy(runner);
        runner.enemies[12].worldX = runner.tileSize*49;
        runner.enemies[12].worldY = runner.tileSize*11;
        runner.enemies[12].direction = "left";

        // Dragon Ball 2 alley enemy 2
        runner.enemies[13] = new Enemy(runner);
        runner.enemies[13].worldX = runner.tileSize*43;
        runner.enemies[13].worldY = runner.tileSize*20;
        runner.enemies[13].direction = "left";

        // Dragon Ball 4 alley enemy 1
        runner.enemies[14] = new Enemy(runner);
        runner.enemies[14].worldX = runner.tileSize*54;
        runner.enemies[14].worldY = runner.tileSize*40;
        runner.enemies[14].direction = "up";

        // Dragon Ball 4 alley enemy 2
        runner.enemies[15] = new Enemy(runner);
        runner.enemies[15].worldX = runner.tileSize*52;
        runner.enemies[15].worldY = runner.tileSize*28;
        runner.enemies[15].direction = "down";

        // Dragon Ball 7 enemy 3
        runner.enemies[16] = new Enemy(runner);
        runner.enemies[16].worldX = runner.tileSize*35;
        runner.enemies[16].worldY = runner.tileSize*31;
        runner.enemies[16].direction = "right";

        // Dragon Ball 7 enemy 4
        runner.enemies[17] = new Enemy(runner);
        runner.enemies[17].worldX = runner.tileSize*43;
        runner.enemies[17].worldY = runner.tileSize*36;
        runner.enemies[17].direction = "up";
    }
}
