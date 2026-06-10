package Player;

import java.util.ArrayList;

import Inventory.Inventory;
import Map.Area;
import Pokemon.Pokemon;

public class Player extends Trainer {
    
    // -------------------- FIELDS -------------------- //


    private int money;
    private boolean[] badgeCount = new boolean[9];   // 8 badges (not use index 0)
    private Boxes boxes = new Boxes();               // storage for pokemon that are not in the party
    private Inventory inventory = new Inventory();   // inventory for storing items that player has

    private int highestLevel; // highest level of pokemon that player has (both in party and boxes) | this value is used to set level for pokemon that is spawned

    private Area currentArea;     // current area of player
    private int positionX;        // X position of player on the map
    private int positionY;        // Y position of player on the map


    // -------------------- CONSTRUCTORS -------------------- //


    // create new player (new game)
    public Player (String inputName)
    {

        super(inputName);
        this.highestLevel = 1; // default highest level is 1

    }

    // continuing player
    public Player (String inputName, Party inputParty, int inputMoney, boolean[] inputBadgeCount, Boxes inputBoxes, Inventory inpuInventory, Area inputCurrentArea, int inputPositionX, int inputPositionY)
    {

        // general informations
        super(inputName, inputParty);

        // player's properties
        this.money = inputMoney;
        this.badgeCount = inputBadgeCount;
        this.boxes = new Boxes(inputBoxes);
        this.inventory = new Inventory(inpuInventory);

        // set the highestLevel
        determineHighestLevel ();

        // position in the map
        this.currentArea = new Area(inputCurrentArea);
        this.positionX = inputPositionX;
        this.positionY = inputPositionY;

    }


    // -------------------- GETTERS -------------------- //


    public int getMoney ()
    {
        return this.money;
    }

    public boolean[] getBadgeCount ()
    {
        return this.badgeCount;
    }

    public Boxes getBoxes ()
    {
        return this.boxes;
    }

    public Inventory getInventory ()
    {
        return this.inventory;
    }

    public int getHighestLevel ()
    {
        return this.highestLevel;
    }

    public Area getCurrentArea ()
    {
        return this.currentArea;
    }

    public void setCurrentArea (Area inputCurrentArea)
    {
        this.currentArea = inputCurrentArea;
    }

    public void setMoney (int inputMoney)
    {
        this.money = inputMoney;
    }

    public int getPositionX ()
    {
        return this.positionX;
    }

    public int getPositionY ()
    {
        return this.positionY;
    }


    // -------------------- SYSTEM METHODS -------------------- //


    // add money
    public void addMoney (int inputMoney)
    {
        this.money += inputMoney;
    }

    // re-determine the highest level of pokemon in the party and boxes
    public void determineHighestLevel ()
    {

        this.highestLevel = Math.max(this.highestLevel, 1);

        for (Pokemon p : super.getParty().getParty())
        {
            if (p != null && p.getLevel() > this.highestLevel)
            {
                this.highestLevel = p.getLevel();
            }
        }

        for (Pokemon p : this.boxes.getBoxes())
        {
            if (p != null && p.getLevel() > this.highestLevel)
            {
                this.highestLevel = p.getLevel();
            }
        }

    }

}
