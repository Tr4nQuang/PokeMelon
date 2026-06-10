package Inventory;

public class Inventory 
{
    
    // -------------------- FIELDS -------------------- //


    private int[] inventory = new int[new Itemdex().getItemdex().length]; // contains the amount of each item in the inventory (using index as itemId in itemdex)
    private Itemdex itemdex = new Itemdex();                              // dictionary of items


    // -------------------- CONSTRUCTORS -------------------- //


    // default constructor
    public Inventory ()
    {
        return;
    }

    // copying constructor
    public Inventory (Inventory inpuInventory)
    {  
        for (int i = 0; i < this.inventory.length; i++) this.inventory[i] = inpuInventory.getInventory()[i];
    }


    // -------------------- GETTERS -------------------- //


    public int[] getInventory ()
    {
        return this.inventory;
    }

    public int getItemAmount (int inputId)
    {
        return this.inventory[inputId];
    }

    public Item getItem (int inputId)
    {
        return this.itemdex.getItem(inputId);
    }


    // -------------------- OPERATIONS -------------------- //


    public void addItem (int inputId, int amount)
    {
        this.inventory[inputId] += amount;
    }

    public void useItem (int inputId)
    {

        // if player doesn't have any of that item => do nothing
        if (this.inventory[inputId] <= 0)
        {
            //System.out.println("You don't have any of this item");
            return;
        }

        this.inventory[inputId]--; // decrease amount by 1

    }

    public void display ()
    {
        System.out.println("Inventory:");
        for (int i = 0; i < this.inventory.length; i++) if (this.inventory[i] > 0) System.out.println (itemdex.getItem(i) + " | Amount: " + this.inventory[i]);
    }

}
