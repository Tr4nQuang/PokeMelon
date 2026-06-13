package Map;


public class Area
{
    
    
    // -------------------- FIELDS -------------------- //
    
    
    private String name; // name of Area

    /*
    Type of Area
    1. City
    2. Harbor
    3. Ocean
    4. Dark biome
    5. Rocky & Hot biome
    6. Electric biome
    7. Rocky & Cold biome
    8. Nature biome
    */
    private int type;
   
    public char[][] map = new char[30][30]; // map of Area
    
    // area connections
    public Area north, south, west, east;
    
    
    // -------------------- CONSTRUCTORS -------------------- //
    
    
    public Area ()
    {
        this.name = "undefined";
        this.map = null;
        this.type = -1;
        north = south = west = east = null;
    }
      
    // TESTING constructor
    public Area (String inputName, int inputType)
    {
        this.name = inputName;
        this.type = inputType;
        north = south = west = east = null;
    }

    // copying constructor
    public Area (Area inputArea)
    {

        // copying informations
        this.name = inputArea.getName();
        this.type = inputArea.getType();

        // copying map
        for (int i = 0; i < 30; i++)
        {
            for (int j = 0; j < 30; j++)
            {
                map[i][j] = inputArea.map[i][j];
            }
        }

        // copying node connections
        this.north = inputArea.north;
        this.south = inputArea.south;
        this.west = inputArea.west;
        this.east = inputArea.east;

    }


    // -------------------- GETTERS -------------------- //


    public String getName ()
    {
        return this.name;
    }

    public int getType ()
    {
        return this.type;
    }

}
