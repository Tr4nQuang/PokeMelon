package Engine;

import java.util.*;

import Inventory.Inventory;
import Inventory.Itemdex;
import Player.*;
import Pokemon.*;
import Move.*;


public class Battle {
    
    // -------------------- FIELDS -------------------- //


    // trainers information
    private Player player;  // player
    private Trainer enemy;  // enemy

    // pokemons information
    private Pokemon playerPokemon;
    private Pokemon enemyPokemon;

    // battle system
    private TypeChart typeChart = new TypeChart();
    private Itemdex itemdex = new Itemdex();
    private DisplayMachine displayMachine = new DisplayMachine();

    private int turn;
    private boolean isPlayerFirst = false; // determine will player move first or not
    private boolean isWildPokemon = false; // determine whether battle is against wild pokemon or trainer

    private int choice = 0; // 1: Fight | 2: Bag (Using bag) | 3: Pokemon (Change pokemon) | 4: Run


    // -------------------- CONSTRUCTORS -------------------- //


    // default constructor
    public Battle ()
    {
        this.player = null;
        this.enemy = null;
        this.playerPokemon = this.enemyPokemon = null;
    }

    // normal constructor
    public Battle (Player inputPlayer, Trainer inputEnemy, boolean isWild)
    {
        // assgin trainers
        this.player = inputPlayer;
        this.enemy = inputEnemy;

        // assign in-combat pokemon of trainers
        this.playerPokemon = this.player.getLeadPokemon();
        this.enemyPokemon = this.enemy.getLeadPokemon();

        // assign battle fields
        this.turn = 1;        
        if (!(this.playerPokemon == null || this.enemyPokemon == null) && this.playerPokemon.getSpd() >= this.enemyPokemon.getSpd()) this.isPlayerFirst = true; // assign who move first
        this.isWildPokemon = isWild;
    }


    // -------------------- GETTERS -------------------- //


    public Player getPlayer ()
    {
        return this.player;
    }

    public Trainer getEnemy ()
    {
        return this.enemy;
    }

    public Pokemon getPlayerPokemon ()
    {
        return this.playerPokemon;
    }

    public Pokemon getEnemyPokemon ()
    {
        return this.enemyPokemon;
    }

    public int getTurn ()
    {
        return this.turn;
    }

    public boolean getIsPlayerFirst ()
    {
        return this.isPlayerFirst;
    }

    public boolean getIsWildPokemon ()
    {
        return this.isWildPokemon;
    }


    // -------------------- SETTERS -------------------- //


    public void setPlayer (Player inputPlayer)
    {
        this.player = inputPlayer;
        this.playerPokemon = this.player.getLeadPokemon();
    }

    public void setEnemy (Trainer inputEnemy)
    {
        this.enemy = inputEnemy;
        this.enemyPokemon = this.enemy.getLeadPokemon();
    }

    public void setPlayerPokemon (Pokemon pokemon)
    {
        this.playerPokemon = pokemon;
    }

    public void setEnemyPokemon (Pokemon pokemon)
    {
        this.enemyPokemon = pokemon;
    }


    // -------------------- BATTLE SYSTEM METHODS -------------------- //

    
    // is used to determine whether battle is happening or not
    public boolean checkBattleStatus ()
    {
        return !(this.playerPokemon == null || this.enemyPokemon == null);
    }

    // next turn of battle
    public void nextTurn ()
    {
        this.turn++;
    }

    // end the battle immediately & display the message
    public String endBattle ()
    {

        // if battle is not end yet => end it and make player win (is used when successfully catch pokemon)
        if (!(this.playerPokemon == null || this.enemyPokemon == null)) this.enemyPokemon = null;

        // enemy run out of available pokemon => win
        if (this.enemyPokemon == null) return ("🥳You won the battle!");
        
        // player run out of available pokemon => lose
        return ("🤕You lost the battle!");

    }

    // calculate damage a pokemon will take when it is attacked
    public int calculateDamage (Pokemon attacker, Pokemon defender, Move move)
    {

        // needed objects and variables
        Random ran = new Random (); // for random

        double damage;        // got from calculation
        double motifier;      // extra coeficience for calculating damage

        // motifier's variables
        double random;        // picks a random floating-point scale multiplier between 0.85 and 1.00
        double stab;          // If the elemental type of the chosen move matches one of the user’s inherent types, STAB = 1.5. If the types do not match, STAB = 1.0
        double effectiveness; // effectiveness between types
        int critChance;       // is used to determine whether the attack is critical or not
        double critical;      // is used to calculate damage


        // calculations
        // calculate damage
        damage = (((2.0 * attacker.getLevel() / 5.0) + 2.0) * move.getPower() * (1.0 * attacker.getAtk() / defender.getDfs()) / 50) + 2;

        // calculate motifier's variables
        random = 1.0 * (ran.nextInt (100 - 85 + 1) + 85) / 100;

        if (attacker.getType1().equals(move.getType()) || (attacker.getType2() != null && attacker.getType2().equals(move.getType()))) stab = 1.5;
        else stab = 1.0;

        effectiveness = typeChart.calculateEffectiveness(typeChart.translateType(move.getType()), defender);

        if (move.getName().contains("slash") || move.getName().contains("cutter") || move.getName().contains("claw") || move.getName().contains("blade")) critChance = 15; // if move is a specific type of move (its critical chance is higher than others) => set critChance to 15%
        else critChance = 5; // else => set critChance to 5%

        if (ran.nextInt(100) < critChance) critical = 1.5; // random 0 -> 99: if randomNumber < critChance => critical!! => set critical to 1.5
        else critical = 1.0; // if not critical => critical = 1.0

        // calculate motifier
        motifier = random * stab * effectiveness * critical;

        // calculate & return final damage
        return (int) (damage * motifier);

    }

    // make enemy do attack
    public String enemyAttack ()
    {

        int moveChoice = -1;    // for choosing moves
        int highestDamage = 0;  // for calculate the highest damage possible
        Move move = new Move(); // store the move

        for (int i = 0; i < 4; i++) // go through the activated moves list of enemy
        {

            move = this.enemyPokemon.getMoveActivated()[i];

            // if move at index (i - 1) != empty && move's currentPP > 0 => calculate and compare the move's damage to the highestDamage
            if (move != null && move.getCurrentPP() > 0 && highestDamage < calculateDamage(this.enemyPokemon, this.playerPokemon, move))
            {
                highestDamage = calculateDamage(this.enemyPokemon, this.playerPokemon, move);
                moveChoice = i;
            }

        }


        // if after conisdering all the moves but the highestDamage = 0 => Run out of PP of all moves => Change to other pokemon & end turn
        if (highestDamage == 0)
        {
            String message = String.format ("%s does not have any available moves\n", this.enemyPokemon.getName().toUpperCase());

            this.enemyPokemon.setHp(0); // make the current enemy pokemon faint
            this.enemyPokemon = this.enemy.getLeadPokemon(); // then change to the next pokemon

            return message;
        }

        // otherwise => decrease the currentPP of pokemon's used move by 1 & player's pokemon take damage
        move = this.enemyPokemon.getMoveActivated()[moveChoice];
        move.setCurrentPP(move.getCurrentPP() - 1);

        Pokemon targetPlayer = this.playerPokemon;
        String effectivenessMessage = this.typeChart.getEffectiveness(this.typeChart.translateType(move.getType()), targetPlayer);

        // player's pokemon take damage & check if player's pokemon is fainted or not. If pokemon is fainted => get another pokemon
        if (targetPlayer.takeDamage(highestDamage)) this.playerPokemon = this.player.getLeadPokemon(); // get the next pokemon of player

        // if next pokemon is null => end battle as player run out of pokemon => player lose
        if (this.playerPokemon == null)
        {
            return String.format ("%s uses %s\n%s\n\n%s", this.enemyPokemon.getName().toUpperCase(), move.getName().toUpperCase(), effectivenessMessage, endBattle());
        }

        else
        {
            // if enemy still have pokemon => turn to next turn
            nextTurn();
            
            // return message
            return String.format ("%s uses %s\n%s\n", this.enemyPokemon.getName().toUpperCase(), move.getName().toUpperCase(), effectivenessMessage);
        }

    }


    // -------------------- HELPERS -------------------- //


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


    // -------------------- OPERATIONS -------------------- //


    /*
    Battle has 4 options:
    1. Fight
    2. Bag (using inventory)
    3. Pokemon (changing in-combat pokemon)
    4. Run
    */
    public String fightOption (Scanner scan)
    {

        // variables
        int choice = -1;
        Move move = new Move();

        while (true) // only finish the method once player picks a valid move
        {

            //  display the Menu & asks player to pick a move
            displayMachine.displayFightingMenu(this);
            choice = parseInt(scan.nextLine());

            // choice == 0 => exit the fighting option
            if (choice == 0) return "";

            // assign the input to Move move (check if move is empty or not && if move's currentPP > 0 or not)
            if (choice == 1 && this.playerPokemon.getMoveActivated()[0] != null && this.playerPokemon.getMoveActivated()[0].getCurrentPP() > 0) move = this.playerPokemon.getMoveActivated()[0];
            if (choice == 2 && this.playerPokemon.getMoveActivated()[1] != null && this.playerPokemon.getMoveActivated()[1].getCurrentPP() > 0) move = this.playerPokemon.getMoveActivated()[1];
            if (choice == 3 && this.playerPokemon.getMoveActivated()[2] != null && this.playerPokemon.getMoveActivated()[2].getCurrentPP() > 0) move = this.playerPokemon.getMoveActivated()[2];
            if (choice == 4 && this.playerPokemon.getMoveActivated()[3] != null && this.playerPokemon.getMoveActivated()[3].getCurrentPP() > 0) move = this.playerPokemon.getMoveActivated()[3];


            // if move is valid (move is defined) => do the attack
            if (!move.getName().equals("undefined"))
            {

                // decrease currentPP of move by 1 (is used)
                move.setCurrentPP(move.getCurrentPP() - 1);

                // display message data before the enemy may change
                String typeOfMove = move.getType();
                Pokemon targetEnemy = this.enemyPokemon;
                String effectivenessMessage = this.typeChart.getEffectiveness(this.typeChart.translateType(typeOfMove), targetEnemy);

                // enemy's pokemon take damage & check if enemy's pokemon is fainted or not. If pokemon is fainted => get another pokemon
                if (targetEnemy.takeDamage(calculateDamage(this.playerPokemon, targetEnemy, move)))
                {
                    this.playerPokemon.takeExp(this, targetEnemy, scan);
                    this.enemyPokemon = this.enemy.getLeadPokemon(); // get the next pokemon of enemy
                }

                // if next pokemon is null => end battle as enemy run out of pokemon
                if (this.enemyPokemon == null)
                {
                    return String.format("%s uses %s\n%s\n\n%s", this.playerPokemon.getName().toUpperCase(), move.getName().toUpperCase(), effectivenessMessage, endBattle());
                }

                else
                {
                    // if enemy still have pokemon => turn to next turn
                    nextTurn();

                    // return message
                    return String.format("%s uses %s\n%s\n", this.playerPokemon.getName().toUpperCase(), move.getName().toUpperCase(), effectivenessMessage);
                }                

            }

        }
        
    }

    public void bagOption (Scanner scan)
    {

        // variables
        int choice = -1;
        Inventory tmpInventory = this.player.getInventory();


        while (true) // only finish the method once player picks a valid item
        {    

            // display the bag & asks player to pick an item
            displayMachine.displayInventoryMenu(this);
            choice = parseInt(scan.nextLine());


            // if choice == 0 => Exit the bag option
            if (choice == 0) return;

            if (choice < 0 || choice > 15) continue;

            // category = 1 => Healing item && Only use when amount > 0
            if (tmpInventory.getItem(choice).getCategory() == 1 && tmpInventory.getItemAmount(choice) > 0)
            {

                Pokemon tmp = null; // pick a pokemon from the party

                while (true) // loop until player enter a valid input
                {

                    this.player.getParty().display(); // display the party of player
                    System.out.print ("\nChoose a pokemon to use item on (Enter 0 to Exit): ");
                    int choosePokemon = parseInt(scan.nextLine());

                    // press 0 to go back (exit the while loop) & go back to the choosing item menu
                    if (choosePokemon == 0) break;

                    // if choosePokemon is valid (1->6) && pokemon at index [choosePokemon] != null && the item can be used successfully => decrease amount of item by 1 & go to next turn & end the method
                    else if (1 <= choosePokemon && choosePokemon <= 6 && (tmp = this.player.getParty().getPokemon(choosePokemon - 1)) != null && tmpInventory.getItem(choice).use(this, tmp, scan))
                    {
                        this.player.getInventory().useItem(choice);
                        nextTurn();
                        return;
                    }

                    displayMachine.displayInventoryMenu(this);

                }

            }

            // category = 2 => Reviving item && Only use when amount > 0
            if (tmpInventory.getItem(choice).getCategory() == 2 && tmpInventory.getItemAmount(choice) > 0)
            {

                Pokemon tmp = null; // pick a pokemon from the party

                while (true) // loop until player enter a valid input
                {

                    this.player.getParty().display(); // display the party of player
                    System.out.print ("\nChoose a pokemon to use item on (Enter 0 to Exit): ");
                    int choosePokemon = parseInt(scan.nextLine());

                    // press 0 to go back (exit the while loop) & go back to the choosing item menu
                    if (choosePokemon == 0) break;

                    // if choosePokemon is valid (1->6) && pokemon at index [choosePokemon] != null && the item can be used successfully => decrease amount of item by 1 & go to next turn & end the method
                    else if (1 <= choosePokemon && choosePokemon <= 6 && (tmp = this.player.getParty().getPokemon(choosePokemon - 1)) != null && tmpInventory.getItem(choice).use(this, tmp, scan))
                    {
                        this.player.getInventory().useItem(choice);
                        nextTurn();
                        return;
                    }

                    displayMachine.displayInventoryMenu(this);

                }

            }
            
            // category = 3 => Capturing item && Only use when amount > 0
            if (tmpInventory.getItem(choice).getCategory() == 3 && tmpInventory.getItemAmount(choice) > 0)
            {

                // if pokemon at index [choosePokemon] != null && the item can be used successfully => decrease amount of item by 1 & go to next turn & end the method
                if (tmpInventory.getItem(choice).use(this, this.enemyPokemon, scan))
                {
                    this.player.getInventory().useItem(choice);
                    nextTurn();
                    return;
                }

            }

            // category = 4 => Exp item && Only use when amount > 0
            if (tmpInventory.getItem(choice).getCategory() == 4 && tmpInventory.getItemAmount(choice) > 0)
            {

                Pokemon tmp = null; // pick a pokemon from the party

                while (true) // loop until player enter a valid input
                {

                    this.player.getParty().display(); // display the party of player
                    System.out.print ("\nChoose a pokemon to use item on (Enter 0 to Exit): ");
                    int choosePokemon = parseInt(scan.nextLine());

                    // press 0 to go back (exit the while loop) & go back to the choosing item menu
                    if (choosePokemon == 0) break;

                    // if choosePokemon is valid (1->6) && pokemon at index [choosePokemon] != null && the item can be used successfully => decrease amount of item by 1 & go to next turn & end the method
                    else if (1 <= choosePokemon && choosePokemon <= 6 && (tmp = this.player.getParty().getPokemon(choosePokemon - 1)) != null && tmpInventory.getItem(choice).use(this, tmp, scan))
                    {
                        this.player.getInventory().useItem(choice);
                        nextTurn();
                        return;
                    }

                    displayMachine.displayInventoryMenu(this);

                }

            }

        }

    }

    public void pokemonOption (Scanner scan)
    {

        Pokemon tmp = null; // pick a pokemon from the party

        while (true) // loop until player input a valid pokemon
        {

            displayMachine.displayChangingPokemonMenu(this);
            int choosePokemon = parseInt(scan.nextLine());

            // press 0 to go back (exit the while loop) & go back to the choosing item menu
            if (choosePokemon == 0) return;

            // if choosePokemon is valid (1->6) && pokemon at index [choosePokemon] != null && pokemon's hp > 0 => change playerPokemon to tmp & skip turn
            else if (1 <= choosePokemon && choosePokemon <= 6 && (tmp = this.player.getParty().getPokemon(choosePokemon - 1)) != null && tmp.getHp() > 0)
            {
                this.playerPokemon = tmp;

                System.out.println("I choose you, " + this.playerPokemon.getName().toUpperCase());

                nextTurn();
                return;
            }

        }

    }

    public String runOption ()
    {

        // if player's pokemon is faster than or equal to enemy's pokemon => run successfully (Loss the battle)
        if (this.playerPokemon.getSpd() >= this.enemyPokemon.getSpd())
        {
            this.playerPokemon = null; // make player lose
            endBattle();
            return ("Ran away safely!");
        }

        // otherwise => player cannot escape the battle and also loss their turn
        else
        {
            nextTurn();
            return ("Can't escape!");
        }

    }

}
