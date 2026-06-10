package Pokemon;
import java.io.*;

import Engine.SortingMachine;
import Move.Move;
import Move.Movedex;


public class Pokedex {
    
    // -------------------- FIELDS -------------------- //


    SortingMachine sortingMachine = new SortingMachine();
    private Pokemon[] pokedex = new Pokemon[2000]; // dictionary of pokemon
    private int size = 0;                          // amount of pokemon inside the dictionary


    // -------------------- CONSTRUCTORS -------------------- //


    public Pokedex ()
    {

        // files
        File pokemonDataBase = new File("Data_Base_files/pokemon_dataBase.txt");
        File pokemonMoveDataBase = new File("Data_Base_files/pokemon_move_dataBase.txt");

        // Movedex for assigning moves for each pokemon
        Movedex movedex = new Movedex();


        // check if file exists or not
        if (!pokemonDataBase.exists())
        {
            System.out.println ("pokemon_dataBase.txt does not exist!");
            return;
        }

        if (!pokemonMoveDataBase.exists())
        {
            System.out.println ("pokemon_move_dataBase.txt does not exist!");
            return;
        } 

        // read informations of pokemon
        try
        {
            
            BufferedReader reader = new BufferedReader(new FileReader(pokemonDataBase));
            String str;

            // pokemon fields
            int inputId;
            String inputName;
            int inputGen;

            String inputType1;
            String inputType2;

            int inputHp;
            int inputAtk;
            int inputDfs;
            int inputSpd;

            double inputCatchRate;
            double inputBaseExp;
            String inputGrowthRate;

            int inputEvolutionLevel;
            String inputEvolutionName;
            String inputRarity;

            while ((str = reader.readLine()) != null)
            {

                // 1. get word and convert word from string to specific type
                // 2. delete the word from the string
                // for getting last word, does not need to remove from the string because the string at that time also is the word

                // id
                inputId = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // name
                inputName = str.substring(0, str.indexOf(" "));
                str = str.substring(str.indexOf(" ") + 1);

                // generation
                inputGen = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // type 1
                inputType1 = str.substring(0, str.indexOf(" "));
                str = str.substring(str.indexOf(" ") + 1);

                // type 2
                inputType2 = str.substring(0, str.indexOf(" "));
                if (inputType2.equals("none")) inputType2 = null; // if type 2 is none => set the string as null
                str = str.substring(str.indexOf(" ") + 1);

                // hp
                inputHp = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // atk
                inputAtk = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // dfs
                inputDfs = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // spd
                inputSpd = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // catch rate
                inputCatchRate = parseDouble(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // base exp
                inputBaseExp = parseDouble(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // growth rate
                inputGrowthRate = str.substring(0, str.indexOf(" "));
                str = str.substring(str.indexOf(" ") + 1);

                // evolution level
                inputEvolutionLevel = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // evolution name
                inputEvolutionName = str.substring(0, str.indexOf(" "));
                str = str.substring(str.indexOf(" ") + 1);

                // rarity
                inputRarity = str.substring(0);
                

                // create the pokemon and put it into the pokedex
                Pokemon poke = new Pokemon(inputId, inputName, inputGen, inputRarity, inputType1, inputType2, inputHp, inputAtk, inputDfs, inputSpd, inputCatchRate, inputBaseExp, inputGrowthRate, inputEvolutionLevel, inputEvolutionName);
                pokedex[inputId] = poke;
                this.size++;

            }
            
            reader.close ();

        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }


        // read moves information of pokemon
        try
        {

            BufferedReader reader = new BufferedReader(new FileReader(pokemonMoveDataBase));
            String str;

            // information from file
            int inputPokemonId;
            int inputMoveId;
            int inputUnlockedLevel;

            while ((str = reader.readLine()) != null)
            {

                // pokemon id
                inputPokemonId = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // move id
                inputMoveId = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // unlocked level
                inputUnlockedLevel = parseInt(str.substring(0));


                // assign the move to indicated pokemon
                pokedex[inputPokemonId].addMoveList(new Move(movedex.getMove (inputMoveId), inputUnlockedLevel));

            }


            reader.close();

        }

        catch (IOException e)
        {
            e.printStackTrace();
        }


        // sort move list of pokemon (ascending base on unlocked level)
        for (int i = 1; i < pokedex.length; i++)
        {
            // if element is not empty => sort the move list of that pokemon (ascending by unlocked level)
            if (pokedex[i] != null) sortingMachine.sortUnlockedLevel (pokedex[i].getMoveList(), true);
        }

    }


    // -------------------- GETTERS -------------------- //


    // get number of pokemon in pokedex
    public int getSize ()
    {
        return this.size;
    }

    // get a specific pokemon (using id)
    public Pokemon getPokemon (int inputId)
    {
        return this.pokedex[inputId];
    }

    // get list of pokemon
    public Pokemon[] getPokedex ()
    {
        return this.pokedex;
    }


    // -------------------- SYSTEM METHODS -------------------- //


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

    public double parseDouble (String inputString)
    {

        double ans = 0;
        int coef = 1;
        int decimal = 1;

        for (int i = 0; i < inputString.length(); ++i)
        {

            char c = inputString.charAt(i);

            if (i == 0 && c == '-') coef *= -1;

            else if ('0' <= c && c <= '9')
            {
                if (decimal == 1) ans = ans * 10 + (c - '0');

                else
                {
                    ans += 1.0 * (c - '0') / decimal;
                    decimal *= 10;
                }
            }

            else if (c == '.') decimal *= 10;

            else
            {
                System.out.println("This string is not a valid double!");
                return -1.0;
            }

        }

        return ans * coef;

    }


    // -------------------- DISPLAY METHODS -------------------- //


    public void display ()
    {

        for (int i = 0; i < this.pokedex.length; i++)
        {

            Pokemon tmpPokemon = this.pokedex[i];

            if (tmpPokemon != null)
            {
                System.out.println("=========================================");
                System.out.printf("      POKÉDEX: #%03d %s      \n", tmpPokemon.getId(), tmpPokemon.getName().toUpperCase());
                System.out.println("=========================================");
                System.out.println("[ GENERAL INFO ]");
                System.out.printf("Generation : %d\n", tmpPokemon.getGen());
                System.out.printf("Rarity     : %s\n", tmpPokemon.getRarity());
                System.out.printf("Type 1     : %s\n", tmpPokemon.getType1());
                System.out.printf("Type 2     : %s\n", tmpPokemon.getType2());
                System.out.println();
                System.out.println("[ BASE STATS ]");
                System.out.printf("HP         :  %3d  [████      ]\n", tmpPokemon.getHp());
                System.out.printf("Attack     :  %3d  [████      ]\n", tmpPokemon.getAtk());
                System.out.printf("Defense    :  %3d  [████      ]\n", tmpPokemon.getDfs());
                System.out.printf("Speed      :  %3d  [████      ]\n", tmpPokemon.getSpd());
                System.out.printf("Base Total : %d\n", (tmpPokemon.getHp() + tmpPokemon.getAtk() + tmpPokemon.getDfs() + tmpPokemon.getSpd()));
                System.out.println();
                System.out.println("[ TRAINING & SYSTEM ]");
                System.out.printf("Base EXP   : %.1f\n", tmpPokemon.getBaseExp());
                System.out.printf("Catch Rate : %.1f\n", tmpPokemon.getCatchRate());
                System.out.printf("Growth Rate: %s\n", tmpPokemon.getGrowthRate().getRate());
                System.out.println();
                System.out.println("[ EVOLUTION ]");
                System.out.printf("Evolves at : Level %d\n", tmpPokemon.getEvolutionLevel());
                System.out.printf("Evolves to : %s\n", tmpPokemon.getEvolutionName().toUpperCase());
                System.out.println("=========================================\n\n");
            }

        }

    }

}
