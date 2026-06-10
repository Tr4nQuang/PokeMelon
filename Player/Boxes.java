package Player;

import java.util.ArrayList;

import Engine.SortingMachine;
import Pokemon.Pokemon;

public class Boxes {
    
    // -------------------- FIELDS -------------------- //


    private ArrayList <Pokemon> boxes;                            // for storing pokemon that player catched
    private SortingMachine sortingMachine = new SortingMachine(); // create the SortingMachine object


    // -------------------- CONSTRUCTORS -------------------- //


    // default constructor
    public Boxes ()
    {
        boxes = new ArrayList<Pokemon>();
    }

    // copying constructor
    public Boxes (Boxes inputBoxes)
    {
        this.boxes = new ArrayList<>(inputBoxes.getBoxes());
    }


    // -------------------- GETTERS -------------------- //


    public ArrayList <Pokemon> getBoxes ()
    {
        return this.boxes;
    }

    public int getSize ()
    {
        return this.boxes.size();
    }

    public Pokemon getPokemon (int inputIndex)
    {
        return this.boxes.get(inputIndex);
    }


    // -------------------- OPERATION FEATURES -------------------- //


    // add pokemon into the storage
    public void addPokemon (Pokemon inputPokemon)
    {
        this.boxes.add(inputPokemon);
    }

    // remove pokemon from the storage
    public void removePokemon (Pokemon inputPokemon)
    {
        this.boxes.remove(inputPokemon);
    }

    // sort the list of pokemon
    public void sortList (int inputChoice, boolean isIncrease)
    {

        /*
        inputChoice
        1: Id
        2: Name
        3: Gen
        4: Rarity
        5: Level
        6: Hp
        7: Attack
        8: Defense
        9: Speed
        */

        if (inputChoice == 1) sortingMachine.sortId(this.boxes, isIncrease);
        if (inputChoice == 2) sortingMachine.sortName(this.boxes, isIncrease);
        if (inputChoice == 3) sortingMachine.sortGen(this.boxes, isIncrease);
        if (inputChoice == 4) sortingMachine.sortRarity(this.boxes, isIncrease);
        if (inputChoice == 5) sortingMachine.sortLevel(this.boxes, isIncrease);
        if (inputChoice == 6) sortingMachine.sortHp(this.boxes, isIncrease);
        if (inputChoice == 7) sortingMachine.sortAtk(this.boxes, isIncrease);
        if (inputChoice == 8) sortingMachine.sortDfs(this.boxes, isIncrease);
        if (inputChoice == 9) sortingMachine.sortSpd(this.boxes, isIncrease);

    }

}
