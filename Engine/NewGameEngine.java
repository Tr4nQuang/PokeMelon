package Engine;

import java.util.*;

import Map.Area;
import Player.Player;
import Pokemon.*;


public class NewGameEngine 
{

    // -------------------- FIELDS -------------------- //


    private Scanner scan = new Scanner(System.in);
    private DisplayMachine displayMachine = new DisplayMachine();
    private Pokedex pokedex = new Pokedex();


    // -------------------- OPERATIONS -------------------- //


    public Player startNewJourney() {
        displayMachine.clearScreen();
        
        System.out.println("=========================================================\n");
        System.out.println(" 🌳 Hello there! Welcome to the world of POKEMELON!");
        System.out.println(" 👋 My name is 👨🥼🔬 PROFESSOR MELON 🍈");
        System.out.println("\n=========================================================\n");
        
        // 1. Get the Player's Name
        System.out.print(" First, tell me... what shall I call you?\n > ");
        String playerName = scan.nextLine().trim();
        
        if (playerName.isEmpty()) {
            playerName = "Watermelon 🍉"; // Default name
        }
        
        Player newPlayer = new Player(playerName);
        
        System.out.println("\n Ah, right! " + newPlayer.getName().toUpperCase() + "! Your very own legend is about to unfold! 🐉\n");
        System.out.print(" Press ENTER to continue...");
        scan.nextLine(); 
        
        // 2. Choose the Starter Pokemon
        chooseStarterPokemon(newPlayer);
        
        // 3. Return the fully built player!
        return newPlayer;

    }


    private void chooseStarterPokemon(Player player)
    {

        while (true)
        {

            displayMachine.clearScreen();

            System.out.println("=========================================================");
            System.out.println(" 🎁 Choose your first partner Pokémon!");
            System.out.println("=========================================================\n");
            
            System.out.println("  [1] 🍃 BULBASAUR  (Grass)");
            System.out.println("  [2] 🔥 CHARMANDER (Fire)");
            System.out.println("  [3] 💧 SQUIRTLE   (Water)\n");
            System.out.print("> ");
            
            String choice = scan.nextLine().trim();
            Pokemon starter = null;
            
            if (choice.equals("1")) starter = new Pokemon(pokedex, 1, 5); 
            else if (choice.equals("2")) starter = new Pokemon(pokedex, 4, 5);
            else if (choice.equals("3")) starter = new Pokemon(pokedex, 7, 5);
            else continue;
            
            // after picking a starter pokemon successfully => add new pokemon into the party
            player.getParty().addPokemon(starter);
            player.determineHighestLevel();

            // new player's area
            player.setCurrentArea(new Area("Kiwi Village", 8));
            
            System.out.println("\n 🎉 You received " + starter.getName().toUpperCase() + "!");
            System.out.println(" Your epic journey will start in Kiwi Village 🏡\n");
            System.out.print(" Press ENTER to begin your journey...");
            scan.nextLine();
            
            break; // Exit the loop completely

        }

    }

}
