package Inventory.Item_Types;

import Inventory.*;
import Pokemon.*;
import Engine.Battle;


public class RevivingItem extends Item
{
    
    // -------------------- FIELDS -------------------- //


    private final int percentHeal; // percent of HP that revived pokemon will receive


    // -------------------- CONSTRUCTORS -------------------- //


    public RevivingItem (int inputId, String inputName, String inputDescription, int inputCategory)
    {

        super (inputId, inputName, inputDescription, inputCategory);

        if (inputId == 5) percentHeal = 50; // Revive
        else if (inputId == 6) percentHeal = 100; // Max Revive
        else percentHeal = -1; // error value

    }

    public RevivingItem (RevivingItem inpuItem)
    {

        super(inpuItem);
        this.percentHeal = inpuItem.getPercentHeal();

    }


    // -------------------- GETTERS -------------------- //


    public int getPercentHeal ()
    {
        return this.percentHeal;
    }


    // -------------------- OPERATIONS -------------------- //


    // using item
    @Override
    public boolean use (Battle battle, Pokemon playerPokemon)
    {

        // if pokemon have not been fainted yet, cannot use revive item
        if (playerPokemon.getHp() > 0)
        {
            System.out.println ("Cannot use " + super.getName() + " on " + playerPokemon.getName() + " because it is not fainted!");
            return false;
        }

        playerPokemon.addHp (playerPokemon.getMaxHp() * percentHeal / 100);

        System.out.println ("Used " + super.getName() + " on " + playerPokemon.getName() + " to revive it and healed " + playerPokemon.getHp() + " HP!");
        return true;

    }

}
