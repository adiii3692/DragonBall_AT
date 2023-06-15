//Import all necessary libraries
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

//Class that manages all tiles and displays them on the window
public class TileManager {

    //Declare a Runner Class object
    Runner runner; 
    //Create an array of Tiles which stores all different types of tiles - grass, earth, sand, etc
    public Tile[] tile;
    //A 2D array that represents the world map
    public int[][] worldMap;

    /**
     * Constructor that instantiates instance variables
     * Precondition: A Runner class object must be passed to the constructor
     * Postcondition: Instance variables are instantiated
     * @param runner - A Runner class object 
     */
    public TileManager(Runner runner)
    {
        //Instantiate the runner class object
        this.runner = runner;
        //Instantiate the tile array to store 10 differnt types of tiles
        tile = new Tile[10];
        //Instantiate the worldMap array to store maxWorldRow x maxWorldCol elements
        worldMap = new int[runner.maxWorldRow][runner.maxWorldCol];
        //Load all the tile images into the tile array
        loadTiles();
        //Load the map into the worldMap array
        loadMap("./maps/2.txt");
    }

    /**
     * Load all the images of the 10 different tiles into the tile array
     * Precondition: A tile array must be declared
     * Postcondition: All 10 tile images are added to the tile array
     */
    public void loadTiles()
    {
        try
        {
            //Load the grass tile
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("./tiles/grass.png"));

            //Load the wall tile
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("./tiles/wall.png"));
            tile[1].collision = true;
            
            //Load the water tile
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("./tiles/water.png"));
            tile[2].collision = true;

            //Load the earth tile
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("./tiles/earth.png"));

            //Load the tree tile
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("./tiles/tree.png"));
            tile[4].collision = true;

            //Load the sand tile
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("./tiles/sand.png"));

            //Load the plank tile
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("./tiles/plank.png"));
            tile[6].collision = true;

            //Load the roofEdge tile
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("./tiles/roofEdge.png"));
            tile[7].collision = true;

            //Load the roof tile
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("./tiles/roof.png"));
            tile[8].collision = true;

            //Load the tree2 tile
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("./tiles/tree2.png"));
            tile[9].collision = true;

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Load the map into the worldMap array
     * Precondition: A string containing the filepath to the map must be passed as an arguement
     * Postcondition: The map has been loaded into worldMap array
     * @param filePath - The String containing the filepath to the map
     */
    public void loadMap(String filePath)
    {
        try
        {
            //Access the .txt world map file
            InputStream file = getClass().getResourceAsStream(filePath); 
            //Read the .txt world map file
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            //Loop through the rows of the worldMap array
            for (int row=0;row<runner.maxWorldRow;row++)
            {
                //Read a line from the .txt world map file
                String line = reader.readLine();

                //Loop through the columns of the worldMap array
                for (int col=0;col<runner.maxWorldCol;col++)
                {
                    //Convert the map's row we read as a String to an array
                    String mapRow[] = line.split(" ");
                    //Convert each integer in the mapRow array from a String to an integer and add it to the worldMap array
                    int num = Integer.parseInt(mapRow[col]);
                    worldMap[row][col] = num;
                }
            }

            //Close the buffer reader
            reader.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Draw the tiles on the window display
     * Precondition: A Graphics2D object must be passed as an arguement to the method
     * Postcondition: The tiles are drawn on the window
     * @param g2 - A Graphics2D object
     */
    public void draw(Graphics2D g2)
    {
        //Loop through the rows of the world Map
        for (int worldRow=0;worldRow<runner.maxWorldRow;worldRow++)
        {
            //Loop through the columns of the world map
            for (int worldCol=0;worldCol<runner.maxWorldCol;worldCol++)
            {
                //Obtain the number at that particular tile
                int tileNum = worldMap[worldRow][worldCol];
                //The x coordinate position of the tile in pixels on the world map
                int worldX = worldCol*runner.tileSize;
                //The y coordinate position of the tile in pixels on the world map
                int worldY = worldRow*runner.tileSize;
                //The x coordinate position of the tile in pixels on the screen
                int screenX = worldX -  runner.player.worldX + runner.player.screenX;
                //The y coordinate position of the tile in pixels on the screen
                int screenY = worldY - runner.player.worldY + runner.player.screenY;

                //Check if the tile is on the screen and if it is, draw it 
                if ((worldX + runner.tileSize> (runner.player.worldX-runner.player.screenX)) &&
                    (worldX - runner.tileSize< (runner.player.worldX+runner.player.screenX)) &&
                    (worldY + runner.tileSize> (runner.player.worldY-runner.player.screenY)) &&
                    (worldY - runner.tileSize< (runner.player.worldY+runner.player.screenY)))
                {
                    //Draw the tile on the window
                    g2.drawImage(tile[tileNum].image,screenX,screenY,runner.tileSize,runner.tileSize,null);
                }
            }
        }
    }
}
