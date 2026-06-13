package Engine;

import java.util.*;

import Inventory.Inventory;
import Inventory.Item;
import Inventory.Itemdex;
import Map.Area;
import Move.Move;
import Player.Player;
import Player.Trainer;
import Player.Boxes;
import Pokemon.Pokemon;


public class DisplayMachine {
    
    // -------------------- SUB/HELPER METHODS -------------------- //


    // clean the screen
    public void clearScreen()
	{
		
        for (int i = 0; i < 50; i++)
        {
            System.out.println ();
        }

	}

    // Helper method to format moves for the UI
    private String formatMoveForMenu(Move move)
    {

        if (move == null) return "None";

        // Formats like: THUNDERBOLT [ELECTRIC] (15/15)
        return String.format("%s [%s] (%d/%d)", 
                move.getName().toUpperCase(), 
                move.getType().toUpperCase(), 
                move.getCurrentPP(), 
                move.getMaxPP());

    }

    private void displayBattleHeader(Battle inpuBattle)
    {
        String border = "=========================================================";
        System.out.println(border);

        Pokemon tmpEnemyPokemon = inpuBattle.getEnemyPokemon();
        if (tmpEnemyPokemon != null)
        {
            if (inpuBattle.getIsWildPokemon()) System.out.printf("%56s\n", "Wild " + tmpEnemyPokemon.getName().toUpperCase() + " (Lv. " + tmpEnemyPokemon.getLevel() + ")");
            else System.out.printf("%56s\n", tmpEnemyPokemon.getName().toUpperCase() + " (Lv. " + tmpEnemyPokemon.getLevel() + ")");
            System.out.printf("%32s", "");
            tmpEnemyPokemon.displayHPBar();
        }
        else
        {
            System.out.printf("%56s\n", "Enemy has no available Pokémon");
            System.out.printf("%32s", "");
            System.out.println("[--------------------]");
        }

        System.out.println("\n");

        Pokemon tmpPlayerPokemon = inpuBattle.getPlayerPokemon();

        if (tmpPlayerPokemon != null)
        {
            System.out.print(" ");
            tmpPlayerPokemon.displayGeneral();
        }

        else
        {
            System.out.println("Your active Pokémon is fainted or unavailable.");
        }

        System.out.println(border);
    }


    // -------------------- DISPLAY METHODS -------------------- //


    public void displayBattleMenu(Battle inpuBattle, boolean showPrompt)
    {

        clearScreen();
        displayBattleHeader(inpuBattle);

        // check if player's pokemon is null or not => To display appropriate message
        if (inpuBattle.getPlayerPokemon() != null) System.out.println("\n  What will " + inpuBattle.getPlayerPokemon().getName().toUpperCase() + " do?\n");
        else System.out.println("\n  What will you do?\n");

        System.out.println("  [1] 🔪FIGHT                      [2] 🎒BAG");
        System.out.println("  [3] 🐾POKEMON                    [4] 🏃RUN\n");
        System.out.println("=========================================================");

        if (showPrompt) System.out.print("> ");
        
    }



    public void displayFightingMenu (Battle inpuBattle)
    {
        clearScreen();
        
        String border = "=========================================================";
        System.out.println(border);
        
        // -------------------- 1st Box -------------------- //

		// 1. ENEMY SECTION (Aligned to the right using %35s to push it over)
		Pokemon tmpEnemyPokemon = inpuBattle.getEnemyPokemon();

		if (inpuBattle.getIsWildPokemon()) System.out.printf("%56s\n", "Wild " + tmpEnemyPokemon.getName().toUpperCase() + " (Lv. " + tmpEnemyPokemon.getLevel() + ")");
		else System.out.printf("%56s\n", tmpEnemyPokemon.getName().toUpperCase() + " (Lv. " + tmpEnemyPokemon.getLevel() + ")");
		
		// Print the enemy HP bar slightly pushed to the right as well
		System.out.printf("%32s", ""); // Print 30 empty spaces
		tmpEnemyPokemon.displayHPBar(); // Display the [███---] line
		
		System.out.println("\n"); // Empty line for spacing between Pokemon
		
		// 2. PLAYER SECTION (Aligned to the left)
		Pokemon tmpPlayerPokemon = inpuBattle.getPlayerPokemon();

		System.out.print(" ");
        inpuBattle.getPlayerPokemon().displayGeneral();
		
		System.out.println(border);
		
		// -------------------- 2nd Box -------------------- //

        // 3. CHOOSING MOVES
        System.out.println("\n  Select a move:\n");
        
        // Grab the array of 4 moves from the active Pokemon
        Move[] moves = inpuBattle.getPlayerPokemon().getMoveActivated();
        
        // Use the helper method to cleanly format all 4 slots
        String m1 = formatMoveForMenu(moves[0]);
        String m2 = formatMoveForMenu(moves[1]);
        String m3 = formatMoveForMenu(moves[2]);
        String m4 = formatMoveForMenu(moves[3]);
        
        // Print Row 1 (Moves 1 and 2) - Padded to 32 characters to fit the Type!
        System.out.printf("  [1] %-32s [2] %-32s\n", m1, m2);
        
        // Print Row 2 (Moves 3 and 4)
        System.out.printf("  [3] %-32s [4] %-32s\n", m3, m4);
        
        // The Back Button
        System.out.println("\n  [0] BACK");
        System.out.println(border);
        System.out.print("> ");

    }



    public void displayInventoryMenu (Battle inpuBattle)
    {

        clearScreen();
		
		String border = "=========================================================";
		System.out.println(border);
		
        // -------------------- 1st Box -------------------- //

		// 1. ENEMY SECTION (Aligned to the right using %35s to push it over)
		Pokemon tmpEnemyPokemon = inpuBattle.getEnemyPokemon();

		if (inpuBattle.getIsWildPokemon()) System.out.printf("%56s\n", "Wild " + tmpEnemyPokemon.getName().toUpperCase() + " (Lv. " + tmpEnemyPokemon.getLevel() + ")");
		else System.out.printf("%56s\n", tmpEnemyPokemon.getName().toUpperCase() + " (Lv. " + tmpEnemyPokemon.getLevel() + ")");
		
		// Print the enemy HP bar slightly pushed to the right as well
		System.out.printf("%32s", ""); // Print 30 empty spaces
		tmpEnemyPokemon.displayHPBar(); // Display the [███---] line
		
		System.out.println("\n"); // Empty line for spacing between Pokemon
		
		// 2. PLAYER SECTION (Aligned to the left)
		Pokemon tmpPlayerPokemon = inpuBattle.getPlayerPokemon();

		System.out.print(" ");
        inpuBattle.getPlayerPokemon().displayGeneral();
		
		System.out.println(border);
		
		// -------------------- 2nd Box -------------------- //
    
        // 3. 15 ITEMS
        System.out.println("\n  BAG: Select an item to use:\n");
    
        Inventory tmpInventory = inpuBattle.getPlayer().getInventory();
        int[] items = tmpInventory.getInventory();
        int totalItems = 15;
        
        // Loop through all 15 items, jumping by 2 for the columns
        for (int i = 1; i <= totalItems; i += 2) {
            
            // Left column
            Item leftItem;
            if (i <= totalItems) leftItem = tmpInventory.getItem(i); // take the 1st item
            else leftItem = null; // If i is overbound => Empty slot
            
            String text1;
            if (leftItem != null) text1 = leftItem.getName().toUpperCase() + " (x" + items[i] + ")"; // Build the text: "Potion (x3)"
            else text1 = "None";
            
            // right column
            Item rightItem;
            if (i + 1 <= totalItems) rightItem = tmpInventory.getItem(i + 1); // take the 2nd item
            else rightItem = null; // If i is overbound => Empty slot
            
            String text2;
            if (rightItem != null) text2 = rightItem.getName().toUpperCase() + " (x" + items[i + 1] + ")"; // Build the text: "Potion (x3)"
            else text2 = "None";
            
            // display text1 | text2
            if (i + 1 <= totalItems) System.out.printf("  [%2d] %-30s [%2d] %-30s\n", (i), text1, (i + 1), text2); // Print Row with 2 items
            
            else System.out.printf("  [%2d] %-30s\n", (i), text1); // Print the final 15th item alone on the bottom row

        }
        
        System.out.println("\n  [ 0] BACK");
        System.out.println(border);
        System.out.print("> ");

    }



    public void displayChangingPokemonMenu (Battle inpuBattle)
    {

        clearScreen();
		
		String border = "=========================================================";
		System.out.println(border);
		
        // -------------------- 1st Box -------------------- //

		// 1. ENEMY SECTION (Aligned to the right using %35s to push it over)
		Pokemon tmpEnemyPokemon = inpuBattle.getEnemyPokemon();

		if (inpuBattle.getIsWildPokemon()) System.out.printf("%56s\n", "Wild " + tmpEnemyPokemon.getName().toUpperCase() + " (Lv. " + tmpEnemyPokemon.getLevel() + ")");
		else System.out.printf("%56s\n", tmpEnemyPokemon.getName().toUpperCase() + " (Lv. " + tmpEnemyPokemon.getLevel() + ")");
		
		// Print the enemy HP bar slightly pushed to the right as well
		System.out.printf("%32s", ""); // Print 30 empty spaces
		tmpEnemyPokemon.displayHPBar(); // Display the [███---] line
		
		System.out.println("\n"); // Empty line for spacing between Pokemon
		
		// 2. PLAYER SECTION (Aligned to the left)
		Pokemon tmpPlayerPokemon = inpuBattle.getPlayerPokemon();

		System.out.print(" ");
        inpuBattle.getPlayerPokemon().displayGeneral();
		
		System.out.println(border);
		
		// -------------------- 2nd Box -------------------- //

        // 3. PARTY OF 6 POKEMONS
        System.out.println("\n  POKEMON: Choose a Pokemon to send out:\n");
    
        Pokemon[] party = inpuBattle.getPlayer().getParty().getParty(); 
        
        for (int i = 0; i < 6; i++) 
        {
            Pokemon p = party[i];
            
            // if pokemon [index i] exists
            if (p != null)     
            {

                String statusTag = "";

                if (p.getHp() == 0) statusTag = "(FNT)";
                else if (p == inpuBattle.getPlayerPokemon()) statusTag = "(ACTIVE)";
                
                // Format: [1] PIKACHU      (Lv.  6) [████████--]  24/ 30 HP (ACTIVE)
                // %-12s pads the name to 12 chars to keep the Level columns aligned
                System.out.printf("  [%d] %-12s (Lv.%3d)", (i + 1), p.getName().toUpperCase(), p.getLevel());
                p.displayMiniHPBar();
                System.out.printf (" %s\n", statusTag);

            }

            // if pokemon [index i] does not exist
            else System.out.printf("  [%d] None\n", (i + 1));

        }
        
        System.out.println("\n  [ 0] BACK");
        System.out.println(border);
        System.out.print("> ");

    }



    public void displayMainMenu(Player player)
    {

        clearScreen();
        
        String border = "=========================================================";
        System.out.println(border);
        
        // -------------------- 1st Box: TRAINER CARD -------------------- //
        
        // 1. Print player's general information
        System.out.printf("  NAME: %-20s MONEY: 🧧%-10d\n", player.getName().toUpperCase(), player.getMoney());
        
        String areaName;
        if (player.getCurrentArea() != null) areaName = player.getCurrentArea().getName().toUpperCase();
        else areaName = "UNKNOWN REGION";
        
        System.out.printf("  AREA: %-40s\n\n", areaName);

        
        // 2. Print the Badge Icons
        String[] badgeIcons = {"🪨", " 💧", "⚡️", "🍃", "☠️", " 🔮", "🔥", "🌍"};
        System.out.print("  BADGES: ");

        for (String icon : badgeIcons) System.out.print(icon + " ");
        System.out.println(); // Next line
        
        // 3. Print the Status Lights aligned under the icons
        System.out.print("          "); // for formating purpose
        boolean[] badges = player.getBadgeCount(); 
        
        for (int i = 0; i < 8; i++) // go through 8 badges
        {
            if (badges != null && i < badges.length && badges[i]) System.out.print("🟢 "); // Obtained
            else System.out.print("🔴 "); // Locked
        }

        System.out.println(); // New line
        
        System.out.println(border);
        
        // -------------------- 2nd Box: MAIN MENU -------------------- //
        
        System.out.println("\n  What would you like to do?\n");
        
        System.out.printf("  [1] 🌾 CATCH WILD POKEMON   [2] 🤺 FIGHT NPCs\n");
        System.out.printf("  [3] 🏢 ENTER BUILDING       [4] 🚎 TAKE BUS\n");
        System.out.printf("  [5] 🐾 MANAGE PARTY         [6] 🎒 INVENTORY\n");
        System.out.printf("  [7] 💾 SAVE GAME            [8] 🎁 GIFT CODE\n");
        
        System.out.println("\n  [0] ❌ EXIT GAME");
        System.out.println(border);
        System.out.print("> ");

    }

    

    public void displayMapMenu(Player player)
    {

        clearScreen();
        
        String border = "=========================================================";
        System.out.println(border);
        
        // -------------------- 1st Box: WORLD MAP -------------------- //
        
        System.out.println("                       🗺️  WORLD MAP");
        System.out.println(border);
        System.out.println();
        
        // Display the map
        System.out.println(" 🏔️ Persimmon Peak    🏡 Kiwi Village");
        System.out.println("         |                  |");
        System.out.println("         |                  |");
        System.out.println(" 🌲 Berry Forest ------ 🏙️ Fruit City ------ ⚡️ Power Plant");
        System.out.println("         |                  |                      |");
        System.out.println("         |                  |                      |");
        System.out.println(" 🕳️ Shadow Cave    ⚓️ Watermelon Harbor     🌋 Guava Volcano");
        System.out.println("                            |");
        System.out.println("                            |");
        System.out.println("                    🌊 Coconut Ocean");
        System.out.println();
        
        System.out.println(border);
        
        // -------------------- 2nd Box: MOVEMENT MENU -------------------- //
        
        // Safely fetch current area name
        String areaName;
        if (player.getCurrentArea() != null) areaName = player.getCurrentArea().getName().toUpperCase();
        else areaName = "UNKNOWN REGION";
        
        System.out.println("\n  Current Area: " + areaName + "\n");
        System.out.println("  Where would you like to go?\n");
        
        System.out.printf("  [1] ⬆️ NORTH\n");
        System.out.printf("  [2] ⬇️ SOUTH\n");
        System.out.printf("  [3] ⬅️ WEST\n");
        System.out.printf("  [4] ➡️ EAST\n");
        
        System.out.println("\n  [0] ❌ BACK");
        System.out.println(border);
        System.out.print("> ");

    }



    public void displayTrainerBattleIntro(Trainer trainer)
    {

        clearScreen();

        String border = "==========================================================";
        System.out.println(border);
        System.out.println("                     ⚔️ TRAINER BATTLE ⚔️");
        System.out.println(border);
        System.out.println("\n  You encountered " + trainer.getName().toUpperCase() + "!");
        System.out.println("  " + trainer.getName() + " has " + trainer.getParty().getSize() + " Pokemon!");
        System.out.println("\n  " + trainer.getName() + "'s Party:");
        trainer.getParty().display();
        System.out.println("\n" + border);
        System.out.print("\nPress Enter to continue...");

    }



    public void displayBuildingMenu (String areaName)
    {

        clearScreen();

		System.out.println("\n=================================================");
		System.out.println("        🏢 BUILDINGS IN " + areaName.toUpperCase());
		System.out.println("=================================================");
        System.out.println();
		System.out.println("  [1] Pokémon Centre");

		// Dynamically show the Gym Tower ONLY if they are in Fruit City
		if (areaName.equalsIgnoreCase("🏙️ Fruit City")) System.out.println("  [2] Gym Tower");

		System.out.println("\n  [0] Back\n");
		System.out.println("=================================================");
		System.out.print("> ");

    }



    public void displayPokemonCentreMenu()
    {

        clearScreen();

        String border = "=========================================================";
        System.out.println(border);
        System.out.println("\t\t  🏥 POKÉMON CENTRE 🏥");
        System.out.println(border);
        System.out.println("\n  Welcome to the Pokemon Centre!");
        System.out.println("  How can we help you today?\n");
        System.out.println("  [1] 💊 Heal My Pokemon");
        System.out.println("  [2] 📦 Open Boxes\n");
        System.out.println("  [0] ❌ Leave\n");
        System.out.println(border);
        System.out.print("> ");

    }



    public void displayBoxesMenu(Player player)
    {

        clearScreen();

        String border = "=========================================================";
        System.out.println(border);
        System.out.println("\t\t\t📦 BOXES 📦");
        System.out.println(border);
        System.out.println("\n  Your Boxes:\n");

        Boxes boxes = player.getBoxes();
        ArrayList<Pokemon> boxesList = boxes.getBoxes();

        if (boxesList.size() == 0)
        {
            System.out.println("  Your boxes are empty!");
        }
        else
        {
            for (int i = 0; i < boxesList.size(); i++)
            {
                System.out.print("  " + (i + 1) + ". ");
                boxesList.get(i).displayGeneral();
            }
        }

        System.out.println("\n  [1] Move Pokemon to Party");
        System.out.println("  [2] Store Pokemon in Boxes\n");
        System.out.println("  [0] Back\n");
        System.out.println(border);
        System.out.print("> ");

    }



    public void displayManagePartyMenu(Player player)
    {

        clearScreen();

        String border = "=========================================================";
        System.out.println(border);
        System.out.println("\t\t  🐾 MANAGE PARTY 🐾");
        System.out.println(border);
        System.out.println("\n  Your Party:\n");
        player.getParty().display();
        System.out.println("\n  [1] View Pokemon Details");
        System.out.println("  [2] Move Pokemon\n");
        System.out.println("  [0] Back\n");
        System.out.println(border);
        System.out.print("> ");

    }



    public void displayInventoryMenu(Player player)
    {

        clearScreen();

        String border = "=========================================================";
        System.out.println(border);
        System.out.println("\n  Your Active Pokemon:\n");

        for (int i = 0; i < player.getParty().getSize(); i++) // display all the pokemon in player's party
        {
            Pokemon activePokemon = player.getParty().getPokemon(i);

            if (activePokemon != null) // if pokemon != null => display it
            {
                System.out.print((i + 1) + ". ");
                activePokemon.displayGeneral();
                System.out.println();
            }
        }

        System.out.println(border);
        System.out.println("\n  BAG: Select an item to use:\n");

        Inventory tmpInventory = player.getInventory();
        int[] items = tmpInventory.getInventory();
        int totalItems = 15;

        for (int i = 1; i <= totalItems; i += 2)
        {
            Item leftItem;
            if (i <= totalItems) leftItem = tmpInventory.getItem(i);
            else leftItem = null;

            String text1;
            if (leftItem != null) text1 = leftItem.getName().toUpperCase() + " (x" + items[i] + ")";
            else text1 = "None";

            Item rightItem;
            if (i + 1 <= totalItems) rightItem = tmpInventory.getItem(i + 1);
            else rightItem = null;

            String text2;
            if (rightItem != null) text2 = rightItem.getName().toUpperCase() + " (x" + items[i + 1] + ")";
            else text2 = "None";

            if (i + 1 <= totalItems)
                System.out.printf("  [%2d] %-30s [%2d] %-30s\n", (i), text1, (i + 1), text2);
            else
                System.out.printf("  [%2d] %-30s\n", (i), text1);
        }

        System.out.println("\n  [ 0] BACK");
        System.out.println(border);
        System.out.print("> ");

    }

}
