package Engine;

import java.util.*;

import Move.Move;
import Pokemon.Pokemon;

public class SortingMachine {
    
    // -------------------- COMPARATORS FOR SORTING -------------------- //


    // Pokemon: Comparators are defaultly designed for sorting increasingly
    public boolean cmpId (Pokemon inputPokemonA, Pokemon inputPokemonB)
    {
        return inputPokemonA.getId() <= inputPokemonB.getId();
    }

    public boolean cmpName (Pokemon inputPokemonA, Pokemon inputPokemonB)
    {
        return inputPokemonA.getName().compareTo(inputPokemonB.getName()) <= 0;
    }

    public boolean cmpGen(Pokemon inputPokemonA, Pokemon inputPokemonB)
    {
        return inputPokemonA.getGen() <= inputPokemonB.getGen();
    }

    public boolean cmpRarity(Pokemon inputPokemonA, Pokemon inputPokemonB)
    {

        int weightA = 0; // convert pokemon A rarity to weight
        int weightB = 0; // convert pokemon B rarity to weight

        // convert A
        if (inputPokemonA.getRarity().equals("common")) weightA = 1;
        if (inputPokemonA.getRarity().equals("uncommon")) weightA = 2;
        if (inputPokemonA.getRarity().equals("rare")) weightA = 3;
        if (inputPokemonA.getRarity().equals("super_rare")) weightA = 4;
        if (inputPokemonA.getRarity().equals("legendary")) weightA = 5;

        // convert B
        if (inputPokemonB.getRarity().equals("common")) weightB = 1;
        if (inputPokemonB.getRarity().equals("uncommon")) weightB = 2;
        if (inputPokemonB.getRarity().equals("rare")) weightB = 3;
        if (inputPokemonB.getRarity().equals("super_rare")) weightB = 4;
        if (inputPokemonB.getRarity().equals("legendary")) weightB = 5;

        // return the true | false
        return weightA <= weightB;
    }

    public boolean cmpLevel(Pokemon inputPokemonA, Pokemon inputPokemonB)
    {
        return inputPokemonA.getLevel() <= inputPokemonB.getLevel();
    }

    public boolean cmpHp(Pokemon inputPokemonA, Pokemon inputPokemonB)
    {
        return inputPokemonA.getHp() <= inputPokemonB.getHp();
    }

    public boolean cmpAtk(Pokemon inputPokemonA, Pokemon inputPokemonB)
    {
        return inputPokemonA.getAtk() <= inputPokemonB.getAtk();
    }

    public boolean cmpDfs(Pokemon inputPokemonA, Pokemon inputPokemonB)
    {
        return inputPokemonA.getDfs() <= inputPokemonB.getDfs();
    }

    public boolean cmpSpd(Pokemon inputPokemonA, Pokemon inputPokemonB)
    {
        return inputPokemonA.getSpd() <= inputPokemonB.getSpd();
    }

    // Move: Comparators are defaultly designed for sorting increasingly
    public boolean cmpLevel (Move inputMoveA, Move inputMoveB)
    {
        return inputMoveA.getUnlockedLevel() <= inputMoveB.getUnlockedLevel();
    }

    // -------------------- SORTING METHODS -------------------- //


    // Pokemon Sorting
    public void sortId (ArrayList <Pokemon> inputList, boolean isIncrease)
    {
        
        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpId (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpId (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

    public void sortName (ArrayList <Pokemon> inputList, boolean isIncrease)
    {
        
        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpName (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpName (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

    public void sortGen (ArrayList <Pokemon> inputList, boolean isIncrease)
    {
        
        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpGen (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpGen (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

    public void sortRarity (ArrayList <Pokemon> inputList, boolean isIncrease)
    {
        
        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpRarity (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpRarity (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

    public void sortLevel (ArrayList <Pokemon> inputList, boolean isIncrease)
    {
        
        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpLevel (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpLevel (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

    public void sortHp (ArrayList <Pokemon> inputList, boolean isIncrease)
    {
        
        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpHp (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpHp (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

    public void sortAtk (ArrayList <Pokemon> inputList, boolean isIncrease)
    {
        
        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpAtk (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpAtk (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

    public void sortDfs (ArrayList <Pokemon> inputList, boolean isIncrease)
    {
        
        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpDfs (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpDfs (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

    public void sortSpd (ArrayList <Pokemon> inputList, boolean isIncrease)
    {
        
        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpSpd (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpSpd (inputList.get(i), inputList.get(i + 1)))
                    {
                        Pokemon tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

    // Move Sorting
    public void sortUnlockedLevel (ArrayList <Move> inputList, boolean isIncrease)
    {

        // using bubble sort

        // get size of array list
        int size = inputList.size();

        for (int turn = 1; turn < size; turn++)
        {
            for (int i = 0; i < size - turn; i++)
            {
                // if sort increasing => swap
                if (isIncrease)
                {
                    // wrong position
                    if (!cmpLevel (inputList.get(i), inputList.get(i + 1)))
                    {
                        Move tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }

                // if sort decreasing
                else
                {
                    // wrong position => swap
                    if (cmpLevel (inputList.get(i), inputList.get(i + 1)))
                    {
                        Move tmp = inputList.get(i);
                        inputList.set (i, inputList.get(i + 1));
                        inputList.set (i + 1, tmp);
                    }
                }
            }
        }

    }

}
