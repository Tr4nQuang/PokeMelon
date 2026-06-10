package Inventory;

import Pokemon.Pokemon;
import Engine.Battle;


public abstract class Item 
{

    /* ID of items:

    Healing Items:
    1. Potion: Heals 20 HP
    2. Super Potion: Heals 50 HP
    3. Hyper Potion: Heals 200 HP
    4. Max Potion: Restores HP completely

    Revive Items:
    5. Revive: Revives a fainted Pokémon (HP = 0) and restores half of its maximum HP
    6. Max Revive: Revives a fainted Pokémon and restores its HP completely

    Capture Items:
    7. Poke Ball: 1.0x Catch Rate
    8. Great Ball: 1.5x Catch Rate
    9. Ultra Ball: 2.0x Catch Rate
    10. Master Ball: Guaranteed Catch

    Exp Candy:
    11. Exp Candy XS: Gives 100 EXP
    12. Exp Candy S: Gives 800 EXP
    13. Exp Candy M: Gives 3000 EXP
    14. Exp Candy L: Gives 10000 EXP
    15. Exp Candy XL: Gives 30000 EXP
    */
    
    // -------------------- FIELDS -------------------- //


    // general information
    protected int id;             // id of item => Faster to search and get item
    protected String name;        // name of item
    protected String description; // description of item
    protected int category;       // e.g., 1 (Healing Item) | 2 (Revive Item) | 3 (Capture Item) | 4 (Exp Candy)

    
    // -------------------- CONSTRUCTORS -------------------- //


    // default constructor
    public Item (int inputId, String inputName, String inputDescription, int inputCategory)
    {

        this.id = inputId;
        this.name = inputName;
        this.description = inputDescription;
        this.category = inputCategory;
        
    }

    // copying constructor
    public Item (Item inputItem)
    {

        this.id = inputItem.getId();
        this.name = inputItem.getName();
        this.description = inputItem.getDescription();
        this.category = inputItem.getCategory();
        
    }


    // -------------------- GETTERS -------------------- //
    

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getCategory() {
        return this.category;
    }


    // -------------------- OPERATIONS -------------------- //


    public String toString ()
    {
        return String.format("Name: %s | Description: %s", this.name, this.description);
    }

    // return true: item is used | false: item is not used
    public abstract boolean use (Battle battle, Pokemon pokemon);

}
