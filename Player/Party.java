package Player;

import java.util.*;

import Pokemon.*;


public class Party {
    
    // -------------------- FIELDS -------------------- //


    private Pokemon[] party;
    private int size;


    // -------------------- CONSTRUCTORS -------------------- //


    public Party ()
    {
        this.party = new Pokemon [6];
        this.size = 0;
    }

    // default constructor
    public Party (ArrayList<Pokemon> inputList)
    {

        // initialize its fields
        this.party = new Pokemon [6];
        this.size = 0;

        // assign pokemon to the party && increase the size by 1
        for (Pokemon p : inputList) party[size++] = p;

    }

    // constructor for copying
    public Party (Party inpuParty)
    {

        this.party = inpuParty.getParty();
        this.size = inpuParty.getSize();

    }


    // -------------------- GETTERS -------------------- //


    public Pokemon[] getParty ()
    {
        return this.party;
    }

    public Pokemon getPokemon (int inputIndex)
    {
        return this.party[inputIndex];
    }

    public int getSize ()
    {
        return this.size;
    }

    public boolean isFull ()
    {
        return this.size >= 6;
    }


    // -------------------- DISPLAY METHODS -------------------- //


    // display the entire party (general form)
    public void display ()
    {

        for (int i = 0; i < 6; i++)
        {
            if (party[i] != null) // if slot [i] is not empty => display it
            {
                System.out.print (i + 1 + ". ");
                party[i].displayGeneral();
            }
        }

    }


    // -------------------- OPERATIONS -------------------- //


    // is used to pile up the array to the front
    public void pileUp ()
    {

        for (int i = 1; i <= 5; i++)
        {
            for (int j = 0; j < 6 - i; j++)
            {
                if (party[i] == null)
                {
                    party[i] = party[i + 1];
                    party[i + 1] = null; 
                }
            }
        }
            
    }

    // is used to add pokemon to the party
    public void addPokemon (Pokemon inputPokemon)
    {

        // if the party is not full yet => add pokemon into it
        if (!isFull()) this.party[size++] = inputPokemon;

    }

    // is used to remove pokemon from the party
    public void removePokemon (int inputIndex)
    {

        // invalid index (index < 0 || index >= current size)
        if (0 < inputIndex || inputIndex >= size) return;

        this.party[inputIndex] = null; // remove pokemon
        --this.size;
        pileUp(); // pile up the array to the front

    }

}
