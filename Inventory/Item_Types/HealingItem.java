package Inventory.Item_Types;

import Inventory.*;

import java.util.Scanner;

import Engine.Battle;
import Pokemon.*;


public class HealingItem extends Item
{
    
    // -------------------- FIELDS -------------------- //
    

    private final int healAmount; // amount of HP that will be healed when item is used


    // -------------------- CONSTRUCTORS -------------------- //


    // default constructor
    public HealingItem (int inputId, String inputName, String inputDescription, int inputCategory)
    {

        super (inputId, inputName, inputDescription, inputCategory);
        
        if (inputId == 1) this.healAmount = 20; // Potion
        else if (inputId == 2) this.healAmount = 50; // Super Potion
        else if (inputId == 3) this.healAmount = 200; // Hyper Potion
        else if (inputId == 4) this.healAmount = 9999; // Max Potion
        else this.healAmount = -1; // error value

    }

    public HealingItem (HealingItem inpuItem)
    {

        super(inpuItem);
        this.healAmount = inpuItem.getHealAmount();

    }


    // -------------------- GETTERS -------------------- //


    public int getHealAmount ()
    {
        return this.healAmount;
    }


    // -------------------- OPERATIONS -------------------- //


    // using item
    @Override
    public boolean use (Battle battle, Pokemon playerPokemon, Scanner scan)
    {

        // if pokemon is fainted, cannot use healing item on it
        if (playerPokemon.getHp() == 0)
        {
            System.out.println ("\n⚠️ Cannot use " + super.getName() + " on " + playerPokemon.getName() + " because it is fainted!");
            waitForEnter(scan);
            return false;
        }

        // if pokemon's hp is fulled, cannot use healing item on it
        if (playerPokemon.getHp() >= playerPokemon.getMaxHp())
        {
            System.out.println ("\n⚠️ Cannot use " + super.getName() + " on " + playerPokemon.getName() + " because its HP is full!");
            waitForEnter(scan);
            return false;
        }

        int hpBeforeHeal = playerPokemon.getHp();
        playerPokemon.addHp (Math.min(playerPokemon.getHp() + this.healAmount, playerPokemon.getMaxHp()));
        int hpAfterHeal = playerPokemon.getHp();

        System.out.println ("\nUsed " + super.getName() + " on " + playerPokemon.getName() + " and healed " + (hpAfterHeal - hpBeforeHeal) + " HP!");
        waitForEnter(scan);
        return true;

    }

}
