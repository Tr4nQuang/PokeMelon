package Engine;

import java.util.ArrayList;
import java.util.Random;

import Pokemon.*;
import Map.*;
import Player.*;


public class SpawningMachine {
    
    // -------------------- FIELDS -------------------- //

  
    Pokedex pokedex = new Pokedex(); // create pokedex for spliting pokemons into different arraylist base on their rairty
    TypeChart typeChart = new TypeChart();


    // rate of spawning
    private int total = 1000;       // total rate (100%)
    private int commonRate = 450;   // Common:45%
    private int uncommonRate = 350; // Uncommon: 35%
    private int rareRate = 150;     // Rare: 15%
    private int superRareRate = 45; // Super Rare: 4.5%
    private int legendaryRate = 5;  // Legendary: 0.5%

    /*
    Type of Area && type of pokemon will be spawned there
    1. City
    Types: Normal, Fighting, Poison, Steel
    Type IDs: 1, 2, 4, 9

    2. River
    Types: Water, Bug
    Type IDs: 11, 7

    3. Ocean
    Types: Water, Dragon
    Type IDs: 11, 16

    4. Dark biome
    Types: Dark, Ghost, Psychic
    Type IDs: 17, 8, 14

    5. Rocky biome
    Types: Rock, Ground, Fire
    Type IDs: 6, 5, 10

    6. Electric biome
    Types: Electric, Steel
    Type IDs: 13, 9

    7. Cold biome
    Types: Ice, Water
    Type IDs: 15, 11

    8. Nature biome
    Types: Grass, Bug, Flying, Fairy
    Type IDs: 12, 7, 3, 18
    */

    // 1. City
    ArrayList<Pokemon> type1 = new ArrayList<Pokemon>();
    int lastIndexOfCommonType1 = 0;    // last index of common pokemon in type1 list
    int lastIndexOfUncommonType1 = 0;  // last index of uncommon pokemon in type1 list
    int lastIndexOfRareType1 = 0;      // last index of rare pokemon in type1 list
    int lastIndexOfSuperRareType1 = 0; // last index of super_rare pokemon in type1 list
    int lastIndexOfLegendaryType1 = 0; // last index of legendary pokemon in type1 list

    // 2. River
    ArrayList<Pokemon> type2 = new ArrayList<Pokemon>();
    int lastIndexOfCommonType2 = 0;    // last index of common pokemon in type2 list
    int lastIndexOfUncommonType2 = 0;  // last index of uncommon pokemon in type2 list
    int lastIndexOfRareType2 = 0;      // last index of rare pokemon in type2 list
    int lastIndexOfSuperRareType2 = 0; // last index of super_rare pokemon in type2 list
    int lastIndexOfLegendaryType2 = 0; // last index of legendary pokemon in type2 list

    // 3. Ocean
    ArrayList<Pokemon> type3 = new ArrayList<Pokemon>();
    int lastIndexOfCommonType3 = 0;    // last index of common pokemon in type3 list
    int lastIndexOfUncommonType3 = 0;  // last index of uncommon pokemon in type3 list
    int lastIndexOfRareType3 = 0;      // last index of rare pokemon in type3 list
    int lastIndexOfSuperRareType3 = 0; // last index of super_rare pokemon in type3 list
    int lastIndexOfLegendaryType3 = 0; // last index of legendary pokemon in type3 list

    // 4. Dark biome
    ArrayList<Pokemon> type4 = new ArrayList<Pokemon>();
    int lastIndexOfCommonType4 = 0;    // last index of common pokemon in type4 list
    int lastIndexOfUncommonType4 = 0;  // last index of uncommon pokemon in type4 list
    int lastIndexOfRareType4 = 0;      // last index of rare pokemon in type4 list
    int lastIndexOfSuperRareType4 = 0; // last index of super_rare pokemon in type4 list
    int lastIndexOfLegendaryType4 = 0; // last index of legendary pokemon in type4 list

    // 5. Rocky biome
    ArrayList<Pokemon> type5 = new ArrayList<Pokemon>();
    int lastIndexOfCommonType5 = 0;    // last index of common pokemon in type5 list
    int lastIndexOfUncommonType5 = 0;  // last index of uncommon pokemon in type5 list
    int lastIndexOfRareType5 = 0;      // last index of rare pokemon in type5 list
    int lastIndexOfSuperRareType5 = 0; // last index of super_rare pokemon in type5 list
    int lastIndexOfLegendaryType5 = 0; // last index of legendary pokemon in type5 list

    // 6. Electric biome
    ArrayList<Pokemon> type6 = new ArrayList<Pokemon>();
    int lastIndexOfCommonType6 = 0;    // last index of common pokemon in type6 list
    int lastIndexOfUncommonType6 = 0;  // last index of uncommon pokemon in type6 list
    int lastIndexOfRareType6 = 0;      // last index of rare pokemon in type6 list
    int	lastIndexOfSuperRareType6 = 0; // last index of super_rare pokemon in type6 list
    int	lastIndexOfLegendaryType6 = 0; // last index of legendary pokemon in type6 list

    // 7. Cold biome
    ArrayList<Pokemon> type7 = new ArrayList<Pokemon>();
    int	lastIndexOfCommonType7 = 0;    // last index of common pokemon in type7 list
    int	lastIndexOfUncommonType7 = 0;  // last index of uncommon pokemon in type7 list
    int	lastIndexOfRareType7 = 0;      // last index of rare pokemon in type7 list
    int	lastIndexOfSuperRareType7 = 0; // last index of super_rare pokemon in type7 list
    int	lastIndexOfLegendaryType7 = 0; // last index of legendary pokemon in type7 list

    // 8. Nature biome
    ArrayList<Pokemon> type8 = new ArrayList<Pokemon>();
    int lastIndexOfCommonType8 = 0;    // last index of common pokemon in type8 list
    int lastIndexOfUncommonType8 = 0;  // last index of uncommon pokemon in type8 list
    int lastIndexOfRareType8 = 0;      // last index of rare pokemon in type8 list
    int lastIndexOfSuperRareType8 = 0; // last index of super_rare pokemon in type8 list
    int lastIndexOfLegendaryType8 = 0; // last index of legendary pokemon in type8 list


    // -------------------- CONSTRUCTORS -------------------- //


    public SpawningMachine ()
    {

        Pokemon pokemon = new Pokemon();
        SortingMachine sortingMachine = new SortingMachine();

        // assign each pokemon into the lists of rarity
        for (int i = 1; i < pokedex.getPokedex().length; i++)
        {

            if (pokedex.getPokemon(i) == null) continue; // if there is no pokemon with this id => skip it

            pokemon = pokedex.getPokemon(i);

            // Type 1: Normal, Fighting, Poison, Steel
            if (typeChart.translateType(pokemon.getType1()) == 1 || typeChart.translateType(pokemon.getType1()) == 2 || typeChart.translateType(pokemon.getType1()) == 4 || typeChart.translateType(pokemon.getType1()) == 9)
            {

                // add the appropriate pokemon into the type list
                type1.add(pokemon);

            }

            // Type 2: Water, Bug
            if (typeChart.translateType(pokemon.getType1()) == 11 || typeChart.translateType(pokemon.getType1()) == 7)
            {

                // add the appropriate pokemon into the type list
                type2.add(pokemon);

            }

            // Type 3: Water, Dragon
            if (typeChart.translateType(pokemon.getType1()) == 11 || typeChart.translateType(pokemon.getType1()) == 16)
            {
                
                // add the appropriate pokemon into the type list
                type3.add(pokemon);

            }

            // Type 4: Dark, Ghost, Psychic
            if (typeChart.translateType(pokemon.getType1()) == 17 || typeChart.translateType(pokemon.getType1()) == 8 || typeChart.translateType(pokemon.getType1()) == 14)
            {

                // add the appropriate pokemon into the type list
                type4.add(pokemon);

            }

            // Type 5: Rock, Ground, Fire
            if (typeChart.translateType(pokemon.getType1()) == 6 || typeChart.translateType(pokemon.getType1()) == 5 || typeChart.translateType(pokemon.getType1()) == 10)
            {
                
                // add the appropriate pokemon into the type list
                type5.add(pokemon);

            }

            // Type 6: Electric, Steel
            if (typeChart.translateType(pokemon.getType1()) == 13 || typeChart.translateType(pokemon.getType1()) == 9)
            {

                // add the appropriate pokemon into the type list
                type6.add(pokemon);

            }

            // Type 7: Ice, Water
            if (typeChart.translateType(pokemon.getType1()) == 15 || typeChart.translateType(pokemon.getType1()) == 11)
            {

                // add the appropriate pokemon into the type list
                type7.add(pokemon);

            }

            // Type 8: Grass, Bug, Flying, Fairy
            if (typeChart.translateType(pokemon.getType1()) == 12 || typeChart.translateType(pokemon.getType1()) == 7 || typeChart.translateType(pokemon.getType1()) == 3 || typeChart.translateType(pokemon.getType1()) == 18)
            {

                // add the appropriate pokemon into the type list
                type8.add(pokemon);

            }

        }


        // sort the arrayList of each biome
        sortingMachine.sortRarity(type1, true);
        sortingMachine.sortRarity(type2, true);
        sortingMachine.sortRarity(type3, true);
        sortingMachine.sortRarity(type4, true);
        sortingMachine.sortRarity(type5, true);
        sortingMachine.sortRarity(type6, true);
        sortingMachine.sortRarity(type7, true);
        sortingMachine.sortRarity(type8, true);


        // assign the last index of each rarity in each type list
        for (int i = 0; i < type1.size(); i++)
        {

            if (type1.get(i).getRarity().equals("common")) lastIndexOfCommonType1 = i;
            else if (type1.get(i).getRarity().equals("uncommon")) lastIndexOfUncommonType1 = i;
            else if (type1.get(i).getRarity().equals("rare")) lastIndexOfRareType1 = i;
            else if (type1.get(i).getRarity().equals("super_rare")) lastIndexOfSuperRareType1 = i;
            else if (type1.get(i).getRarity().equals("legendary")) lastIndexOfLegendaryType1 = i;

        }

        for (int i = 0; i < type2.size(); i++)
        {

            if (type2.get(i).getRarity().equals("common")) lastIndexOfCommonType2 = i;
            else if (type2.get(i).getRarity().equals("uncommon")) lastIndexOfUncommonType2 = i;
            else if (type2.get(i).getRarity().equals("rare")) lastIndexOfRareType2 = i;
            else if (type2.get(i).getRarity().equals("super_rare")) lastIndexOfSuperRareType2 = i;
            else if (type2.get(i).getRarity().equals("legendary")) lastIndexOfLegendaryType2 = i;

        }

        for (int i = 0; i < type3.size(); i++)
        {

            if (type3.get(i).getRarity().equals("common")) lastIndexOfCommonType3 = i;
            else if (type3.get(i).getRarity().equals("uncommon")) lastIndexOfUncommonType3 = i;
            else if (type3.get(i).getRarity().equals("rare")) lastIndexOfRareType3 = i;
            else if (type3.get(i).getRarity().equals("super_rare")) lastIndexOfSuperRareType3 = i;
            else if (type3.get(i).getRarity().equals("legendary")) lastIndexOfLegendaryType3 = i;

        }

        for (int i = 0; i < type4.size(); i++)
        {

            if (type4.get(i).getRarity().equals("common")) lastIndexOfCommonType4 = i;
            else if (type4.get(i).getRarity().equals("uncommon")) lastIndexOfUncommonType4 = i;
            else if (type4.get(i).getRarity().equals("rare")) lastIndexOfRareType4 = i;
            else if (type4.get(i).getRarity().equals("super_rare")) lastIndexOfSuperRareType4 = i;
            else if (type4.get(i).getRarity().equals("legendary")) lastIndexOfLegendaryType4 = i;

        }

        for (int i = 0; i < type5.size(); i++)
        {

            if (type5.get(i).getRarity().equals("common")) lastIndexOfCommonType5 = i;
            else if (type5.get(i).getRarity().equals("uncommon")) lastIndexOfUncommonType5 = i;
            else if (type5.get(i).getRarity().equals("rare")) lastIndexOfRareType5 = i;
            else if (type5.get(i).getRarity().equals("super_rare")) lastIndexOfSuperRareType5 = i;
            else if (type5.get(i).getRarity().equals("legendary")) lastIndexOfLegendaryType5 = i;

        }

        for (int i = 0; i < type6.size(); i++)
        {

            if (type6.get(i).getRarity().equals("common")) lastIndexOfCommonType6 = i;
            else if (type6.get(i).getRarity().equals("uncommon")) lastIndexOfUncommonType6 = i;
            else if (type6.get(i).getRarity().equals("rare")) lastIndexOfRareType6 = i;
            else if (type6.get(i).getRarity().equals("super_rare")) lastIndexOfSuperRareType6 = i;
            else if (type6.get(i).getRarity().equals("legendary")) lastIndexOfLegendaryType6 = i;

        }

        for (int i = 0; i < type7.size(); i++)
        {

            if (type7.get(i).getRarity().equals("common")) lastIndexOfCommonType7 = i;
            else if (type7.get(i).getRarity().equals("uncommon")) lastIndexOfUncommonType7 = i;
            else if (type7.get(i).getRarity().equals("rare")) lastIndexOfRareType7 = i;
            else if (type7.get(i).getRarity().equals("super_rare")) lastIndexOfSuperRareType7 = i;
            else if (type7.get(i).getRarity().equals("legendary")) lastIndexOfLegendaryType7 = i;

        }

        for (int i = 0; i < type8.size(); i++)
        {

            if (type8.get(i).getRarity().equals("common")) lastIndexOfCommonType8 = i;
            else if (type8.get(i).getRarity().equals("uncommon")) lastIndexOfUncommonType8 = i;
            else if (type8.get(i).getRarity().equals("rare")) lastIndexOfRareType8 = i;
            else if (type8.get(i).getRarity().equals("super_rare")) lastIndexOfSuperRareType8 = i;
            else if (type8.get(i).getRarity().equals("legendary")) lastIndexOfLegendaryType8 = i;

        }


    }


    // -------------------- GETTERS -------------------- //


    public ArrayList<Pokemon> getType1 ()
    {
        return this.type1;
    }

    public ArrayList<Pokemon> getType2 ()
    {
        return this.type2;
    }

    public ArrayList<Pokemon> getType3 ()
    {
        return this.type3;
    }

    public ArrayList<Pokemon> getType4 ()
    {
        return this.type4;
    }

    public ArrayList<Pokemon> getType5 ()
    {
        return this.type5;
    }

    public ArrayList<Pokemon> getType6 ()
    {
        return this.type6;
    }

    public ArrayList<Pokemon> getType7 ()
    {
        return this.type7;
    }

    public ArrayList<Pokemon> getType8 ()
    {
        return this.type8;
    }


    // -------------------- OPERATION METHOD -------------------- //


    // get a random pokemon in the biome typeList (type1, type2, ..., type8) && that pokemon has rarity as system want
    public Pokemon getRandomPokemon (ArrayList<Pokemon> inputTypeList, int inputLastIndex1, int inputLastIndex2)
    {

        // created needed objects
        Random ran = new Random();

        // get a random pokemon in range: inputLastIndex1 + 1 -> inputLastIndex2 (example: lastIndexOfCommon + 1 -> lastIndexOfUncommon)
        Pokemon pokemon = inputTypeList.get(ran.nextInt(inputLastIndex2 - (inputLastIndex1 + 1) + 1) + (inputLastIndex1 + 1));

        return pokemon;

    }

    // method for spawning a pokemon in the area
    public Pokemon spawnPokemon (Player player, Area area)
    {

        // created needed objects
        Random ran = new Random();
        Pokemon pokemon = new Pokemon();

        /*
        Type of Area
        1. City
        2. River
        3. Ocean
        4. Dark biome
        5. Rocky biome
        6. Electric biome
        7. Cold biome
        8. Nature biome
        */

        /*
        Rate
        1. Common: 40%
        2. Uncommon: 35%
        3. Rare: 15%
        4. Very Rare: 9%
        5. Legendary: 1%
        */

        // validate area type and fallback if it is invalid
        int areaType = area.getType();
        if (areaType < 1 || areaType > 8)
        {
            System.out.printf("Warning: invalid area type %d for %s. Defaulting to City biome.%n", areaType, area.getName());
            areaType = 1;
        }

        // generate a number 0 -> 999
        int rate = ran.nextInt(this.total);

        if (rate < this.commonRate) // Common: 0 -> 399 (40%)
        {

            // Type 1: City
            if (areaType == 1) pokemon = getRandomPokemon(type1, -1, lastIndexOfCommonType1);

            // Type 2: River
            else if (areaType == 2) pokemon = getRandomPokemon(type2, -1, lastIndexOfCommonType2);

            // Type 3: Ocean
            else if (areaType == 3) pokemon = getRandomPokemon(type3, -1, lastIndexOfCommonType3);

            // Type 4: Dark biome
            else if (areaType == 4) pokemon = getRandomPokemon(type4, -1, lastIndexOfCommonType4);

            // Type 5: Rocky biome
            else if (areaType == 5) pokemon = getRandomPokemon(type5, -1, lastIndexOfCommonType5);

            // Type 6: Electric biome
            else if (areaType == 6) pokemon = getRandomPokemon(type6, -1, lastIndexOfCommonType6);

            // Type 7: Cold biome
            else if (areaType == 7) pokemon = getRandomPokemon(type7, -1, lastIndexOfCommonType7);

            // Type 8: Nature biome
            else if (areaType == 8) pokemon = getRandomPokemon(type8, -1, lastIndexOfCommonType8);

        }

        else if (rate < this.commonRate + this.uncommonRate) // Uncommon: 400 -> 749 (35%)
        {

            // Type 1: City
            if (areaType == 1) pokemon = getRandomPokemon(type1, lastIndexOfCommonType1, lastIndexOfUncommonType1);
            
            // Type 2: River
            else if (areaType == 2) pokemon = getRandomPokemon(type2, lastIndexOfCommonType2, lastIndexOfUncommonType2);

            // Type 3: Ocean
            else if (areaType == 3) pokemon = getRandomPokemon(type3, lastIndexOfCommonType3, lastIndexOfUncommonType3);

            // Type 4: Dark biome
            else if (areaType == 4) pokemon = getRandomPokemon(type4, lastIndexOfCommonType4, lastIndexOfUncommonType4);

            // Type 5: Rocky biome
            else if (areaType == 5) pokemon = getRandomPokemon(type5, lastIndexOfCommonType5, lastIndexOfUncommonType5);

            // Type 6: Electric biome
            else if (areaType == 6) pokemon = getRandomPokemon(type6, lastIndexOfCommonType6, lastIndexOfUncommonType6);

            // Type 7: Cold biome
            else if (areaType == 7) pokemon = getRandomPokemon(type7, lastIndexOfCommonType7, lastIndexOfUncommonType7);

            // Type 8: Nature biome
            else if (areaType == 8) pokemon = getRandomPokemon(type8, lastIndexOfCommonType8, lastIndexOfUncommonType8);

        }

        else if (rate < this.commonRate + this.uncommonRate + this.rareRate) // Rare: 750 -> 899 (15%)
        {
            
            // Type 1: City
            if (areaType == 1) pokemon = getRandomPokemon(type1, lastIndexOfUncommonType1, lastIndexOfRareType1);

            // Type 2: River
            else if (areaType == 2) pokemon = getRandomPokemon(type2, lastIndexOfUncommonType2, lastIndexOfRareType2);

            // Type 3: Ocean
            else if (areaType == 3) pokemon = getRandomPokemon(type3, lastIndexOfUncommonType3, lastIndexOfRareType3);

            // Type 4: Dark biome
            else if (areaType == 4) pokemon = getRandomPokemon(type4, lastIndexOfUncommonType4, lastIndexOfRareType4);

            // Type 5: Rocky biome
            else if (areaType == 5) pokemon = getRandomPokemon(type5, lastIndexOfUncommonType5, lastIndexOfRareType5);

            // Type 6: Electric biome
            else if (areaType == 6) pokemon = getRandomPokemon(type6, lastIndexOfUncommonType6, lastIndexOfRareType6);

            // Type 7: Cold biome
            else if (areaType == 7) pokemon = getRandomPokemon(type7, lastIndexOfUncommonType7, lastIndexOfRareType7);

            // Type 8: Nature biome
            else if (areaType == 8) pokemon = getRandomPokemon(type8, lastIndexOfUncommonType8, lastIndexOfRareType8);

        }

        else if (rate < this.commonRate + this.uncommonRate + this.rareRate + this.superRareRate) // Very Rare: 900 -> 989 (9%)
        {
            
            // Type 1: City
            if (areaType == 1) pokemon = getRandomPokemon(type1, lastIndexOfRareType1, lastIndexOfSuperRareType1);

            // Type 2: River
            else if (areaType == 2) pokemon = getRandomPokemon(type2, lastIndexOfRareType2, lastIndexOfSuperRareType2);

            // Type 3: Ocean
            else if (areaType == 3) pokemon = getRandomPokemon(type3, lastIndexOfRareType3, lastIndexOfSuperRareType3);

            // Type 4: Dark biome
            else if (areaType == 4) pokemon = getRandomPokemon(type4, lastIndexOfRareType4, lastIndexOfSuperRareType4);

            // Type 5: Rocky biome
            else if (areaType == 5) pokemon = getRandomPokemon(type5, lastIndexOfRareType5, lastIndexOfSuperRareType5);

            // Type 6: Electric biome
            else if (areaType == 6) pokemon = getRandomPokemon(type6, lastIndexOfRareType6, lastIndexOfSuperRareType6);

            // Type 7: Cold biome
            else if (areaType == 7) pokemon = getRandomPokemon(type7, lastIndexOfRareType7, lastIndexOfSuperRareType7);

            // Type 8: Nature biome
            else if (areaType == 8) pokemon = getRandomPokemon(type8, lastIndexOfRareType8, lastIndexOfSuperRareType8);

        }

        else // Legendary: 990 -> 999 (1%)
        {
            
            // Type 1: City
            if (areaType == 1) pokemon = getRandomPokemon(type1, lastIndexOfSuperRareType1, lastIndexOfLegendaryType1);

            // Type 2: River
            else if (areaType == 2) pokemon = getRandomPokemon(type2, lastIndexOfSuperRareType2, lastIndexOfLegendaryType2);

            // Type 3: Ocean
            else if (areaType == 3) pokemon = getRandomPokemon(type3, lastIndexOfSuperRareType3, lastIndexOfLegendaryType3);

            // Type 4: Dark biome
            else if (areaType == 4) pokemon = getRandomPokemon(type4, lastIndexOfSuperRareType4, lastIndexOfLegendaryType4);

            // Type 5: Rocky biome
            else if (areaType == 5) pokemon = getRandomPokemon(type5, lastIndexOfSuperRareType5, lastIndexOfLegendaryType5);

            // Type 6: Electric biome
            else if (areaType == 6) pokemon = getRandomPokemon(type6, lastIndexOfSuperRareType6, lastIndexOfLegendaryType6);

            // Type 7: Cold biome
            else if (areaType == 7) pokemon = getRandomPokemon(type7, lastIndexOfSuperRareType7, lastIndexOfLegendaryType7);

            // Type 8: Nature biome
            else if (areaType == 8) pokemon = getRandomPokemon(type8, lastIndexOfSuperRareType8, lastIndexOfLegendaryType8);

        }

        // spawn a pokemon
        return new Pokemon(pokedex, pokemon.getId(), ran.nextInt(player.getHighestLevel()) + 1); // random level from 1 -> highest level of player's pokemon (max is 100)

    }

}
