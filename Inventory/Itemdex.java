package Inventory;

import java.io.*;

import Inventory.*;
import Inventory.Item_Types.CaptureItem;
import Inventory.Item_Types.ExpItem;
import Inventory.Item_Types.HealingItem;
import Inventory.Item_Types.RevivingItem;
import Pokemon.Pokemon;


public class Itemdex 
{
    
    // -------------------- FIELDS -------------------- //


    private Item[] itemdex = new Item[16]; // dictionary of all items in the game (index = id of item)
    private int size = 0;                  // number of real items in the itemdex


    // -------------------- CONSTRUCTORS -------------------- //


    public Itemdex ()
    {

        // get file of items data base
        File file = new File("Data_Base_files/item_dataBase.txt");

        // check if file exists or not
        if (!file.exists())
        {
            System.out.println ("item_dataBase.txt does not exist!");
            return;
        }

        // open data base file and create the dictionary of items
        try
        {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str;

            // item fields
            int inputId;
            String inputName;
            String inputDescription;
            int inputCategory;

            while ((str = reader.readLine()) != null)
            {

                // 1. get word and convert word from string to specific type
                // 2. delete the word from the string
                // for getting last word, does not need to remove from the string because the string at that time also is the word

                // id
                inputId = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // name
                inputName = str.substring(0, str.indexOf("'"));
                str = str.substring(str.indexOf("'") + 2);

                // description
                inputDescription = str.substring(0, str.indexOf("'"));
                str = str.substring(str.indexOf("'") + 2);

                // category
                inputCategory = parseInt(str);


                // create item (using its category to identify which class should be used) and add to itemdex
                // 1: HealingItem
                // 2: RevivingItem
                // 3: CaptureItem
                // 4: ExpItem
                if (inputCategory == 1)
                {
                    HealingItem item = new HealingItem (inputId, inputName, inputDescription, inputCategory);
                    itemdex[inputId] = item;
                    size++;
                }

                if (inputCategory == 2)
                {
                    RevivingItem item = new RevivingItem (inputId, inputName, inputDescription, inputCategory);
                    itemdex[inputId] = item;
                    size++;
                }

                if (inputCategory == 3)
                {
                    CaptureItem item = new CaptureItem (inputId, inputName, inputDescription, inputCategory);
                    itemdex[inputId] = item;
                    size++;
                }

                if (inputCategory == 4)
                {
                    ExpItem item = new ExpItem (inputId, inputName, inputDescription, inputCategory);
                    itemdex[inputId] = item;
                    size++;
                }

            }

            reader.close();

        }

        // catch any IO errors
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    // -------------------- GETTERS -------------------- //


    // get number of item in itemdex
    public int getSize ()
    {
        return this.size;
    }

    // get a specific item (using id)
    public Item getItem (int inputId)
    {
        return this.itemdex[inputId];
    }

    // get the list of item
    public Item[] getItemdex ()
    {
        return this.itemdex;
    } 


    // -------------------- METHODS -------------------- //


    public int parseInt (String inpuString)
    {

        int ans = 0;
        int coef = 1;

        for (int i = 0; i < inpuString.length(); i++)
        {

            char c = inpuString.charAt(i);

            if (i == 0 && c == '-') coef *= -1;

            else if ('0' <= c && c <= '9') ans = ans * 10 + (c - '0');

            else
            {
                System.out.println ("parseInt Error: this string does not contain a valid int");
                return -1;
            }

        }

        return ans * coef;

    }

}
