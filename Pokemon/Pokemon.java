package Pokemon;
import java.util.*;

import Engine.*;
import Move.*;


public class Pokemon {
    
    // -------------------- FIELDS -------------------- //

    // objects
    SortingMachine sortingMachine = new SortingMachine();
    DisplayMachine displayMachine = new DisplayMachine();

    // general information
    private int id;         // e.g, "001" for Bulbasaur
    private String name;    // e.g, "Bulbasaur"
    private int level;
    private int exp;

    // pokemon types
    private int gen;        // e.g, "1" for bulbasaur
    private String rarity;  // e.g, "common", "uncommon", "rare", "super_rare", "legendary"
    private String type1;   // e.g, "grass" for bulbasaur
    private String type2;   // e.g, "poison" for bulbasaur (if no type2 => "null")

    // moves
    private Move[] moveActivated = new Move [4]; // moves that pokemon will use in combat
    private ArrayList<Move> moveList = new ArrayList<Move> (); // possible moves that a pokemon can use/learn

    // stats
    private int maxHp;
    private int hp;
    private int atk;
    private int dfs;
    private int spd;

    // iv (individual value): 0 -> 31
    private final int ivHp;
    private final int ivAtk;
    private final int ivDfs;
    private final int ivSpd;

    // system information
    private double catchRate;
    private double baseExp;
    private GrowthRate growthRate;  // e.g, "slow", "medium_slow", "medium_fast", "fast", "fluctuating", "erratic" (more details in GrowthRate class)

    // evolution information
    private int evolutionLevel;
    private String evolutionName;


    // -------------------- CONSTRUCTORS -------------------- //


    // default constructor
    public Pokemon ()
    {

        this.name = this.rarity = this.type1 = this.type2 = this.evolutionName = "undefined";
        this.id = this.level = this.exp = this.gen = this.maxHp = this.hp = this.atk = this.dfs = this.spd = -1;
        this.ivHp = this.ivAtk = this.ivDfs = this.ivSpd = -1;
        this.catchRate = this.baseExp = this.evolutionLevel = -1;
        this.growthRate = null;

    }

    // constructor for data base pokemon
    public Pokemon (int inputId, String inputName, int inputGen, String inputRarity, String inputType1, String inputType2, int inputHp, int inputAtk, int inputDfs, int inputSpd, double inputCatchRate, double inputBaseExp, String inputGrowthRate, int inputEvolutionLevel, String inputEvolutionName)
    {

        // generate random iv for pokemon
        Random ran = new Random(); // random generator for iv
        this.ivHp = ran.nextInt(32);
        this.ivAtk = ran.nextInt(32);
        this.ivDfs = ran.nextInt(32);
        this.ivSpd = ran.nextInt(32);


        // inputs for pokemon information
        this.id = inputId;
        this.name = inputName;
        this.level = 1;

        this.gen = inputGen;
        this.rarity = inputRarity;

        this.type1 = inputType1;
        this.type2 = inputType2;

        this.maxHp = this.hp = inputHp;
        this.atk = inputAtk;
        this.dfs = inputDfs;
        this.spd = inputSpd;

        this.catchRate = inputCatchRate;
        this.baseExp = inputBaseExp;
        
        this.growthRate = new GrowthRate (inputGrowthRate);
        this.exp = growthRate.nextLevelCalculator(0); // currentLevel set as 0 to calculate exp that a level 1 pokemon will have

        this.evolutionLevel = inputEvolutionLevel;
        this.evolutionName = inputEvolutionName;

    }

    // normal pokemon (have specific level)
    public Pokemon (Pokedex pokedex, int inputId, int inputLevel)
    {
        
        // generate random iv for pokemon
        Random ran = new Random(); // random generator for iv
        this.ivHp = ran.nextInt(32);
        this.ivAtk = ran.nextInt(32);
        this.ivDfs = ran.nextInt(32);
        this.ivSpd = ran.nextInt(32);


        // inputs for pokemon information
        this.id = inputId;
        this.level = inputLevel;

        Pokemon defaultPokemon = pokedex.getPokedex()[this.id];

        this.name = defaultPokemon.getName();
        this.gen = defaultPokemon.getGen();;
        this.rarity = defaultPokemon.getRarity();;

        this.type1 = defaultPokemon.getType1();;
        this.type2 = defaultPokemon.getType2();;

        this.moveList = new ArrayList<>(pokedex.getPokemon(inputId).getMoveList()); // set the move list
        setActivatedMove();                                                         // set the activated move list
        calculateStats(pokedex);                                                    // calculate stats for pokemon

        this.catchRate = defaultPokemon.getCatchRate();
        this.baseExp = defaultPokemon.getBaseExp();
        
        this.growthRate = defaultPokemon.getGrowthRate();
        this.exp = growthRate.nextLevelCalculator(level - 1); // currentLevel set as (level - 1) to calculate exp that a currentLevel pokemon will have

        this.evolutionLevel = defaultPokemon.getEvolutionLevel();
        this.evolutionName = defaultPokemon.getEvolutionName();

    }

    // restore pokemon with full saved state
    public Pokemon (Pokedex pokedex, int inputId, int inputLevel, int inputExp, int inputHp, int inputIvHp, int inputIvAtk, int inputIvDfs, int inputIvSpd)
    {

        // set IVs from save data
        this.ivHp = inputIvHp;
        this.ivAtk = inputIvAtk;
        this.ivDfs = inputIvDfs;
        this.ivSpd = inputIvSpd;

        // inputs for pokemon information
        this.id = inputId;
        this.level = inputLevel;

        Pokemon defaultPokemon = pokedex.getPokedex()[this.id];

        this.name = defaultPokemon.getName();
        this.gen = defaultPokemon.getGen();
        this.rarity = defaultPokemon.getRarity();

        this.type1 = defaultPokemon.getType1();
        this.type2 = defaultPokemon.getType2();

        this.moveList = new ArrayList<Move>();
        this.moveActivated = new Move[4];

        calculateStats(pokedex);
        this.hp = Math.max(0, Math.min(inputHp, this.maxHp));

        this.catchRate = defaultPokemon.getCatchRate();
        this.baseExp = defaultPokemon.getBaseExp();
        this.growthRate = defaultPokemon.getGrowthRate();
        this.exp = inputExp;

        this.evolutionLevel = defaultPokemon.getEvolutionLevel();
        this.evolutionName = defaultPokemon.getEvolutionName();
        
    }


    // -------------------- GETTERS -------------------- //


    public int getId ()
    {
        return this.id;
    }
    
    public String getName ()
    {
        return this.name;
    }

    public int getLevel ()
    {
        return this.level;
    }

    public int getExp ()
    {
        return this.exp;
    }

    public int getGen ()
    {
        return this.gen;
    }

    public String getRarity ()
    {
        return this.rarity;
    }

    public String getType1 ()
    {
        return this.type1;
    }

    public String getType2 ()
    {
        return this.type2;
    }

    public Move[] getMoveActivated ()
    {
        return this.moveActivated;
    }

    public ArrayList<Move> getMoveList ()
    {
        return this.moveList;
    }

    public int getMaxHp ()
    {
        return this.maxHp;
    }

    public int getHp ()
    {
        return this.hp;
    }

    public int getAtk ()
    {
        return this.atk;
    }

    public int getDfs ()
    {
        return this.dfs;
    }

    public int getSpd ()
    {
        return this.spd;
    }

    public int getIvHp ()
    {
        return this.ivHp;
    }

    public int getIvAtk ()
    {
        return this.ivAtk;
    }

    public int getIvDfs ()
    {
        return this.ivDfs;
    }

    public int getIvSpd ()
    {
        return this.ivSpd;
    }

    public double getCatchRate ()
    {
        return this.catchRate;
    }

    public double getBaseExp ()
    {
        return this.baseExp;
    }

    public GrowthRate getGrowthRate ()
    {
        return this.growthRate;
    }

    public int getEvolutionLevel ()
    {
        return this.evolutionLevel;
    }

    public String getEvolutionName ()
    {
        return this.evolutionName;
    }


    // -------------------- SETTERS / ADDERS -------------------- //


    public void setLevel (int inputLevel)
    {
        this.level = inputLevel;
        calculateStats (new Pokedex()); // calculate new stats for pokemon after setting new level for it
    }

    public void addExp (int inputExp, Scanner scan)
    {
        this.exp += inputExp;
        levelUp (new Pokedex(), scan); // after adding exp, check for level up and evolve up
    }

    public void addHp (int inputHp)
    {
        this.hp = Math.min (this.maxHp, this.hp + inputHp);
    }

    public void setHp (int inputHp)
    {
        this.hp = Math.max(0, Math.min(inputHp, this.maxHp));
    }
    
    // automatically assign move into activated move list (is used by system to assign skill to pokemon that the activated move list is empty, not player)
    public void setActivatedMove ()
    {

        int size = this.moveList.size();

        // count number of moves that pokemon at this level can activate
        int count = 0;

        for (int i = 0; i < size; i++)
        {
            if (this.moveList.get(i).getUnlockedLevel () <= this.level) count++;
        }

        // assign the 4 available lastest moves for pokemon
        for (int i = count - 1; i >= Math.max (0, count - 4); i--) addMoveActivated (this.moveList.get(i));

    }


    // -------------------- DISPLAY METHODS -------------------- //


    // display stats
    public void displayStats ()
    {
        System.out.printf("Max HP: %d\n", this.maxHp);
        System.out.printf("Attack:  %d\n", this.atk);
        System.out.printf("Defense: %d\n", this.dfs);
        System.out.printf("Speed:   %d\n", this.spd);
    }

    // display list of activated move
    public void displayMoveActivated ()
    {

        System.out.println("Activated Moves:");

        for (int i = 0; i < 4; i++)
        {
            if (this.moveActivated[i] != null) System.out.printf ("%d. %s\n", (i + 1), this.moveActivated[i]);
            else System.out.printf ("%d. None\n", (i + 1));
        }

    }

    // display moves list
    public void displayMoveList ()
    {

        System.out.println ("Move List:");
        int count = 0;

        for (Move m : this.moveList)
        {
            ++count;
            System.out.printf ("%d. %s | Unlock Level: %d\n", count, m, m.getUnlockedLevel());
        }

    }

    // display Mini version of HP bar (this version is display in the menu of pokemon)
    public void displayMiniHPBar ()
    {

        int barLength = 10;
        double hpPercent = (double) this.hp / this.maxHp;
        int filledLength = (int) (barLength * hpPercent);
        
        String bar = "[";

        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) bar += "█";
            else bar += "-";
        }

        bar += "]";

        System.out.printf ("%s %3d/%3d HP", bar, this.hp, this.maxHp);

    }

    // display HP bar of pokemon
    public void displayHPBar ()
    {

        int barLength = 20; // How long the bar looks on screen
        
        // Calculate the percentage of HP remaining
        double hpPercent = (double) this.hp / this.maxHp;
        
        // Calculate how many blocks of the bar need to be filled
        int filledLength = (int) (barLength * hpPercent);
        
        // create the bar String
        String bar = "[";
        
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) bar += "█"; // Filled HP
            else bar += "-";                  // Missing HP
        }

        bar += "]";

        System.out.printf ("%s %3d/%3d HP\n", bar, this.hp, this.maxHp);

    }

    // display general information of pokemon 
    public void displayGeneral ()
    {
        // display
        System.out.printf ("%s (Lv.%3d)\n", (this.name.substring(0, 1).toUpperCase() + this.name.substring(1)), this.level);
        displayHPBar();
    }

    private void waitForEnter(Scanner scan)
	{
		System.out.print("\nPress Enter to continue...");
		scan.nextLine();
	}

    @Override
    public String toString ()
    {
        return String.format("%s (Lv. %d) - Rarity: %s\nType: %s / %s\n\nIVs:\t\t\tStats:\nHP: %d\t\t\tMax HP: %d\nAtk: %d\t\t\tAtk: %d\nDfs: %d\t\t\tDfs: %d\nSpd: %d\t\t\tSpd: %d", this.name, this.level, this.rarity, this.type1, this.type2, this.ivHp, this.hp, this.ivAtk, this.atk, this.ivDfs, this.dfs, this.ivSpd, this.spd);
    }


    // -------------------- SYSTEM METHODS -------------------- //


     // is used to pile up the array to the front
    public void pileUp ()
    {

        for (int i = 1; i <= 5; i++)
        {
            for (int j = 0; j < 6 - i; j++)
            {
                if (this.moveActivated[i] == null)
                {
                    this.moveActivated[i] = this.moveActivated[i + 1];
                    this.moveActivated[i + 1] = null; 
                }
            }
        }
            
    }

    // add move into the activated move list
    public void addMoveActivated (Move inputMove)
    {

        // if pokemon's level is not enough => do nothing
        if (this.level < inputMove.getUnlockedLevel ())
        {
            System.out.printf ("Cannot activate %s because %s's level is insufficient!\n", inputMove.getName(), this.name);
            return;
        }

        for (int i = 0; i < this.moveActivated.length; i++)
        {
            // if there is an empty place => add
            if (this.moveActivated[i] == null)
            {
                this.moveActivated[i] = inputMove; // add move into the moveActivated list
                removeMoveList(inputMove);         // remove move from the moveList
                return;
            }
        }

        // if the moveActivated is fulled => display message
        System.out.printf("Cannot activate %s as the Activated Move list is fulled. Need to remove a move first!\n", inputMove.getName());

    }

    // remove move from the activated move list
    public void removeMoveActivated (int inputIndex)
    {

        // if the element is already empty => don't need to do anything
        if (this.moveActivated[inputIndex] == null) return;

        // else => set the element to null
        addMoveList(this.moveActivated[inputIndex]); // add move back to the moveList before delete it from the moveActivated
        sortingMachine.sortUnlockedLevel (this.moveList, true); // sort the moveList again
        this.moveActivated[inputIndex] = null; // delete move from the moveActivated list
        pileUp(); // pile up the moveActivated list to the front

    }

    // add move into the move list of pokemon
    public void addMoveList (Move inputMove)
    {
        // put move into the move list (name of move, move)
        this.moveList.add (inputMove);
    }

    public void removeMoveList (Move inpuMove)
    {
        // remove move from the move list
        this.moveList.remove (inpuMove);
    }


    // -------------------- GAMEPLAY METHODS -------------------- //


    // calculate new stats of pokemon (often uses after pokemon level up or evolve up)
    public void calculateStats (Pokedex pokedex)
    {

        Pokemon databasePokemon = pokedex.getPokemon(this.id);

        // using formula:
        // Max HP (current lv) = (((2 * BaseHP + IV_HP) * Level) / 100) + Level + 10
        // Stat (current lv) = (((2 * BaseStat + IV_Stat) * Level) / 100) + 5

        this.maxHp = this.hp = (((2 * databasePokemon.getHp() + this.ivHp) * this.level) / 100) + this.level + 10; // baseHp is the hp of pokemon in pokedex
        this.atk = (((2 * databasePokemon.getAtk() + this.ivAtk) * this.level) / 100) + 5;
        this.dfs = (((2 * databasePokemon.getDfs() + this.ivDfs) * this.level) / 100) + 5;
        this.spd = (((2 * databasePokemon.getSpd() + this.ivSpd) * this.level) / 100) + 5;

    }

    // decrease pokemon HP when it takes damage (uses in battle)
    public boolean takeDamage (int inputTakenDamage)
    {

        // calculate the current hp
        this.hp = Math.max (0, this.hp - inputTakenDamage);

        // return true if pokemon is dead
        // return false if pokemon is still alive
        return this.hp == 0;

    }

    // earns exp after defeat a pokemon
    public void takeExp (Battle inputBattle, Pokemon inputEnemy, Scanner scan)
    {

        // if level is not max yet => Can earn EXP
        if (this.level < 100)
        {
            int earnedExp = (int) inputEnemy.getBaseExp () * inputEnemy.getLevel () / 7;

            this.displayMachine.displayFightingMenu(inputBattle);
            System.out.printf ("\n%s gained %d EXP\n", this.name.toUpperCase(), earnedExp);
            this.waitForEnter(scan);

            // earn exp
            this.exp += earnedExp;

            // check for level up
            this.levelUp(new Pokedex(), scan);
        }

    }

    // evolve pokemon if possible
    public void evolveUp (Pokedex pokedex, Scanner scan)
    {

        // if pokemon has next evolution & it satisfies the required level => Pokemon will evolute
        if (this.level >= this.evolutionLevel)
        {

            // display messages
            System.out.printf ("What? %s is evolving!\n", this.name.toUpperCase());
            System.out.println ("...");
            this.waitForEnter(scan);

            // -------------------- Operate the Evolution -------------------- //

            // operate the evolution by update all the information of pokemon (except level and exp)
            // 1. update pokemon general informations
            // 2. update base stats
            // 3. update system information
            // 4. update evolution information
            this.id++; // increase id by 1 because all type of 1 pokemon are stored adjacent in the data base file
            Pokemon tmpPokemon = pokedex.getPokemon(this.id); // get the evolution form

            this.name = tmpPokemon.getName();
            this.gen = tmpPokemon.getGen();
            this.rarity = tmpPokemon.getRarity();
            this.type1 = tmpPokemon.getType1();
            this.type2 = tmpPokemon.getType2();
 
            calculateStats(pokedex); // calculate new stats
            
            this.catchRate = tmpPokemon.getCatchRate();
            this.baseExp = tmpPokemon.getBaseExp();
            this.growthRate = tmpPokemon.getGrowthRate();

            this.evolutionLevel = tmpPokemon.getEvolutionLevel();
            this.evolutionName = tmpPokemon.getEvolutionName();

            // -------------------- ---------------------- -------------------- //

            // displat congratulation message
            System.out.printf ("Congratulations! Your %s evolved into %s!\n", pokedex.getPokemon(this.id - 1).getName().toUpperCase(), this.name.toUpperCase());
            this.waitForEnter(scan);

        }

    }

    // check if pokemon is able to level up or not
    public boolean checkLevelUp ()
    {

        // if level < 100 => Not reach to the max level yet => Can level up
        if (this.level < 100) return this.exp > this.growthRate.nextLevelCalculator(this.level + 1);

        // if level >= 100 => Cannot level up anymore
        return false;

    }

    // level up pokemon if possible (include checkLevelUp () && evolveUp (Pokedex pokedex))
    public void levelUp (Pokedex pokedex, Scanner scan)
    {

        // if pokemon can level up
        if (checkLevelUp ())
        {
            // level up until cannot anymore
            while (checkLevelUp ())
            {
                this.level++; // increase level by 1
                calculateStats (pokedex);
            }

            // then, check for evolution and display message
            evolveUp(pokedex, scan); // after level up, check for the evolution
            System.out.printf ("\n%s grew to level %d!\n\n", this.name.toUpperCase(), this.level); // display level up message
            displayStats(); // display new stats
            this.waitForEnter(scan);
        }
        
    }


}
