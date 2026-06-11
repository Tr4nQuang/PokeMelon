package Inventory.Item_Types;

import java.util.*;

import Inventory.Item;
import Inventory.Itemdex;
import Pokemon.*;
import Engine.Battle;


public class ExpItem extends Item
{
    
    // -------------------- FIELDS -------------------- //
    

    private final int expAmount; // amount of EXP that will be gained when item is used


    // -------------------- CONSTRUCTORS -------------------- //


    public ExpItem (int inputId, String inputName, String inputDescription, int inputCategory)
    {

        super (inputId, inputName, inputDescription, inputCategory);
        
        if (inputId == 11) this.expAmount = 100;        // Exp. Candy XS
        else if (inputId == 12) this.expAmount = 800;   // Exp. Candy S
        else if (inputId == 13) this.expAmount = 3000;  // Exp. Candy M
        else if (inputId == 14) this.expAmount = 10000; // Exp. Candy L
        else if (inputId == 15) this.expAmount = 30000; // Exp. Candy XL
        else this.expAmount = -1; // error value

    }

    public ExpItem (ExpItem inpuItem)
    {

        super(inpuItem);
        this.expAmount = inpuItem.getExpAmount();

    }


    // -------------------- GETTERS -------------------- //


    public int getExpAmount ()
    {
        return this.expAmount;
    }


    // -------------------- OPERATIONS -------------------- //

    // using item
    public boolean use (Battle battle, Pokemon playerPokemon, Scanner scan)
    {

        // if pokemon is fainted, cannot use exp item
        if (playerPokemon.getHp() == 0)
        {
            System.out.println ("\n⚠️ Cannot use " + super.getName() + " on " + playerPokemon.getName() + " because it is fainted!");
            waitForEnter(scan);
            return false;
        }

        // if pokemon is in battle, cannot use exp item
        if (battle.checkBattleStatus())
        {
            System.out.println ("\n⚠️ Cannot use " + super.getName() + " on " + playerPokemon.getName() + " because it is in battle!");
            waitForEnter(scan);
            return false;
        }

        int expBefore = playerPokemon.getExp();
        playerPokemon.addExp(this.expAmount);
        int expAfter = playerPokemon.getExp();

        System.out.println ("\nUsed " + super.getName() + " on " + playerPokemon.getName() + " and gained " + (expAfter - expBefore) + " EXP!");
        waitForEnter(scan);
        return true;

    }

}
