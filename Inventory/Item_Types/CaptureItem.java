package Inventory.Item_Types;

import java.util.*;

import Engine.Battle;
import Inventory.*;
import Pokemon.*;


public class CaptureItem extends Item
{
    
    // -------------------- FIELDS -------------------- //
    

    private final double captureRate; // catch rate multiplier


    // -------------------- CONSTRUCTORS -------------------- //


    public CaptureItem (int inputId, String inputName, String inputDescription, int inputCategory)
    {

        super(inputId, inputName, inputDescription, inputCategory);
        
        if (inputId == 7) this.captureRate = 1;         // Poke Ball
        else if (inputId == 8) this.captureRate = 1.5;  // Great Ball
        else if (inputId == 9) this.captureRate = 2.0;  // Ultra Ball
        else if (inputId == 10) this.captureRate = 520; // Master Ball
        else this.captureRate = -1; // error value

    }

    public CaptureItem (CaptureItem inpuItem)
    {

        super(inpuItem);
        this.captureRate = inpuItem.getCaptureRate();

    }


    // -------------------- GETTERS -------------------- //


    public double getCaptureRate ()
    {
        return this.captureRate;
    }


    // -------------------- OPERATIONS -------------------- //


    // using item
    public boolean use (Battle battle, Pokemon wildPokemon)
    {

        // if battle is not against wild pokemon, cannot use capture item
        if (!battle.getIsWildPokemon())
        {
            System.out.println ("Cannot use " + super.getName() + " on trainer's pokemon!");
            return false;
        }

        // if player is NOT in battle, cannot use capture item
        if (!battle.checkBattleStatus())
        {
            System.out.println ("Cannot use " + super.getName() + " because there is no target!");
            return false;
        }

        // if pokemon is fainted, cannot use healing item on it
        if (wildPokemon.getHp() == 0)
        {
            System.out.println ("Cannot use " + super.getName() + " on " + wildPokemon.getName() + " because it is fainted!");
            return false;
        }



        // OTHERWISE => work on the catching process
        double alpha = ((3.0 * wildPokemon.getMaxHp() - 2.0 * wildPokemon.getHp()) * wildPokemon.getCatchRate() * this.captureRate) / (3.0 * wildPokemon.getMaxHp());

        if (alpha >= 255) // if alpha >= 255: catch immediately!!
        {

            System.out.println("Gotcha! Pokémon was caught!");
            
            // if player's party is not full yet => add catched pokemon directly into player's party
            if (!battle.getPlayer().getParty().isFull()) battle.getPlayer().getParty().addPokemon(wildPokemon);

            // else => add catched pokemon into player's boxes
            else battle.getPlayer().getBoxes().addPokemon(wildPokemon);

        }

        else // if the catch was not guaranteed => do the 4 shakes
        {

            // needed objects and variables for the next steps
            Random ran = new Random();

            double beta = 65536 * Math.sqrt(Math.sqrt(alpha / 255.0)); // calculate constant beta
            int gamma;

            for (int test = 1; test <= 4; test++)
            {

                gamma = ran.nextInt(65536); // random a number 0 -> 65535

                if (gamma < (int) beta) continue; // if gamma < beta: the test passes and the ball shakes => move to the next shake
                
                else // if gamma >= beta: the test fails and the ball is broken
                {

                    if (test == 1) System.out.println("Oh no! The Pokémon broke free!"); // fail test 1
                    if (test == 2) System.out.println("Aww! It appeared to be caught!"); // fail test 2
                    if (test == 3) System.out.println("Aaargh! Almost had it!");         // fail test 3
                    if (test == 4) System.out.println("Gaaarrhhhh! So close!");          // fail test 4
                    
                    return true;

                }

            }

            // if pass all 4 tests => Successfully catch pokemon => Add pokemon
            System.out.println("Gotcha! Pokémon was caught!");
            
            // if player's party is not full yet => add catched pokemon directly into player's party
            if (!battle.getPlayer().getParty().isFull()) battle.getPlayer().getParty().addPokemon(wildPokemon);

            // else => add catched pokemon into player's boxes
            else battle.getPlayer().getBoxes().addPokemon(wildPokemon);
            
        }

        return true;

    }

}
