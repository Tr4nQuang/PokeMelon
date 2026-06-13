
//import javax.swing.*;
//import java.awt.event.*;
import java.io.*;
import java.util.*;

import Map.*;
import Move.Move;
import Move.Movedex;
import Pokemon.*;
import Engine.*;
import Inventory.*;
import Player.*;


class PokeMelon
{
	public static void main(String[] args) {

		// -------------------- FIELDS -------------------- //


		// setting fields & Engines
		Scanner scan = new Scanner(System.in);
		GameMap map = new GameMap ();

		Battle battle = new Battle();
		DisplayMachine displayMachine = new DisplayMachine();
		NewGameEngine newGameEngine = new NewGameEngine();
		FileManager fileManager = new FileManager();
		SortingMachine sortingMachine = new SortingMachine();
		SpawningMachine spawner = new SpawningMachine();

		// dictionaries
		Pokedex pokedex = new Pokedex();
		Movedex movedex = new Movedex();
		Itemdex itemdex = new Itemdex();
		
		// game fields
		Player player = new Player("PokeMelon");




		// -------------------- -------------------- G A M E P L A Y -------------------- -------------------- //




		// if save file does not exist => New Player
		if (!fileManager.checkSaveFile()) player = newGameEngine.startNewJourney(map);

		// if save file exists => load player from save file
		else player = fileManager.loadGame(map);


		// start the game loop
		while (true)
		{

			int gameChoice = -1;

			displayMachine.displayMainMenu(player);
			gameChoice = parseInt(scan.nextLine());

			if (gameChoice == 1)
			{

				// check the eligibility for entering battle
				if (player.getLeadPokemon() == null)
				{
					System.out.println ("  You don't have any Pokémon left!\nHead to the nearest Pokémon Center");
					waitForEnter(scan);
					continue;
				}

				// spawn the wild pokemon
				ArrayList wildPokemon = new ArrayList<>();
				wildPokemon.add (spawner.spawnPokemon(player, true));
				Trainer wildTrainer = new Trainer("Wild", new Party(wildPokemon));
				battle = new Battle(player, wildTrainer, true);

				// in combat with wild pokemon
				fighting(battle, displayMachine, scan);

			}

			if (gameChoice == 2)
			{

				// check the eligibility for entering battle
				if (player.getLeadPokemon() == null)
				{
					System.out.println ("  You don't have any Pokémon left!\nHead to the nearest Pokémon Center");
					waitForEnter(scan);
					continue;
				}

				fightNPCs(player, displayMachine, scan, spawner, pokedex);

			}

			if (gameChoice == 3) enterBuilding (player, displayMachine, scan);

			if (gameChoice == 4) takeBus(player, displayMachine, map, scan);

			if (gameChoice == 5) manageParty(player, displayMachine, scan);

			if (gameChoice == 6) displayInventory(player, displayMachine, scan);

			if (gameChoice == 7) saveGame(fileManager, player, scan);

			if (gameChoice == 8) giftCode(player, displayMachine, scan);

			if (gameChoice == 0) break;

		}

		scan.close();
		
	}


	// -------------------- HELPERS -------------------- //


	public static int parseInt (String inpuString)
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

	private static void waitForEnter(Scanner scan)
	{
		System.out.print("\nPress Enter to continue...");
		scan.nextLine();
	}


	// -------------------- Option 1 (also is used for 2): Fight Wild Pokemon -------------------- //


	public static void fighting (Battle inputBattle, DisplayMachine inputDisplayMachine, Scanner scan)
	{

		int choice = -1;
		String combatMessage = "";

		while (inputBattle.checkBattleStatus())
		{

			inputDisplayMachine.displayBattleMenu(inputBattle, true); // display menu

			/*
			1. Player move first => turn is odd number
			2. Player move after => turn is even number

			A: is player move first? (1: true, 0: false)
			B: is odd number? (1: true, 0: false)

			A   B   A^B   !(A^B)
			0   0    0       1
			0   1    1       0
			1   0    1       0
			1   1    0       1           
			*/

			if (!(inputBattle.getIsPlayerFirst() ^ inputBattle.getTurn() % 2 != 0)) // player's turn
			{

				choice = parseInt(scan.nextLine());

				if (choice == 1) combatMessage = inputBattle.fightOption(scan);
				if (choice == 2) inputBattle.bagOption(scan);
				if (choice == 3) inputBattle.pokemonOption(scan);
				if (choice == 4) combatMessage = inputBattle.runOption();

				inputDisplayMachine.displayBattleMenu(inputBattle, false);
				
				// only display combatMessage when it's not empty & reset the message immediately after displaying
				if (!combatMessage.equals(""))
				{
					System.out.println (combatMessage);
					combatMessage = "";
					waitForEnter(scan);
				}
				
			}

			else // enemy's turn
			{

				combatMessage = inputBattle.enemyAttack(); // enemy do the attack

				inputDisplayMachine.displayBattleMenu(inputBattle, false);
				
				// only display combatMessage when it's not empty & reset the message immediately after displaying
				if (!combatMessage.equals(""))
				{
					System.out.println (combatMessage);
					combatMessage = "";
					waitForEnter(scan);
				}
				
			}

		}

	}


	// -------------------- Option 2: Fight NPCs -------------------- //


	public static void fightNPCs (Player player, DisplayMachine inputDisplayMachine, Scanner scan, SpawningMachine spawner, Pokedex pokedex)
	{

		Random ran = new Random();
		inputDisplayMachine.clearScreen();

		// Create random NPC trainer with 1-6 random Pokemon
		Party npcParty = new Party();
		int partySize = ran.nextInt(6) + 1; // 1 to 6 Pokemon

		for (int i = 0; i < partySize; i++)
		{

			// Use spawner to generate valid Pokemon
			Pokemon npcPokemon = spawner.spawnPokemon(player, false);
			if (npcPokemon != null) npcParty.addPokemon(npcPokemon);

		}

		// create NPC
		String[] npcNames = {"Kevin Apple", "Henry Pineapple", "Ray Tangerine", "Kerry Banana", "Kimberly Strawberry", "Officer Stanley Sugarcane", "Lord Dragon Fruit", "Shinnosuke Coconut", "Doraemon Blueberry"};
		String npcName = npcNames[ran.nextInt(npcNames.length)];
		Trainer npcTrainer = new Trainer(npcName, npcParty);

		// display NPC battle intro
		inputDisplayMachine.displayTrainerBattleIntro(npcTrainer);
		scan.nextLine();

		// create and start the battle
		Battle battle = new Battle(player, npcTrainer, false);
		fighting(battle, inputDisplayMachine, scan);

	}


	// -------------------- Option 3: Enter Building -------------------- //


	public static void enterBuilding(Player player, DisplayMachine inputDisplayMachine, Scanner scan)
	{
		int choice = -1;
		
		// take the name of the city the player is currently standing in
		String currentArea = player.getCurrentArea().getName();

		while (true)
		{
			
			inputDisplayMachine.displayBuildingMenu (currentArea);
			choice = parseInt(scan.nextLine());

			// choice 1: pokemon centre
			if (choice == 1) pokemonCentre(player, inputDisplayMachine, scan);

			// choice 2: gym tower (only available in Fruit City)
			else if (choice == 2 && currentArea.equalsIgnoreCase("🏙️ Fruit City")) 
			{
				// Trigger your Gym Tower logic here!
				// enterElevator(player, gymTower, scan);
				System.out.println("\n  You step into the massive Gym Tower...");
				waitForEnter(scan);
			}

			else if (choice == 0) break;

		}
	}

	// Building 1: Pokemon Centre (Available anywhere)
	public static void pokemonCentre (Player player, DisplayMachine inputDisplayMachine, Scanner scan)
	{

		int choice = -1;

		while (true)
		{

			inputDisplayMachine.displayPokemonCentreMenu();
			choice = parseInt(scan.nextLine());

			if (choice == 1) // heal pokemons
			{

				// Heal all Pokemon in party (HP & move's PP)
				System.out.println("Healing your Pokemon...");

				for (int i = 0; i < player.getParty().getSize(); i++)
				{

					Pokemon p = player.getParty().getPokemon(i);

					if (p != null)
					{

						// reset hp of pokemon
						p.setHp(p.getMaxHp());
						
						// reset all the PP of activated moves
						for (Move m : p.getMoveActivated())
						{
							// only reset PP once m is defined
							if (m != null) m.setCurrentPP(m.getMaxPP());
						}

						// reset all the PP of possible moves
						for (Move m : p.getMoveList()) m.setCurrentPP(m.getMaxPP());

					}

				}

				System.out.println("All your Pokemon have been fully healed!");
				waitForEnter(scan);

			}

			// Open Boxes
			else if (choice == 2) displayBoxesMenu(player, inputDisplayMachine, scan);

			else if (choice == 0) break;

		}

	}

	// Building 2: Gym Tower (Only available in Fruit City)

	// method for displaying and managing boxes
	private static void displayBoxesMenu (Player player, DisplayMachine inputDisplayMachine, Scanner scan)
	{

		int choice = -1;

		while (true)
		{

			inputDisplayMachine.displayBoxesMenu(player);
			Boxes boxes = player.getBoxes();
			ArrayList<Pokemon> boxesList = boxes.getBoxes();
			choice = parseInt(scan.nextLine());

			if (choice == 1) // Move Pokemon from Boxes to Party
			{

				// Move from boxes to party
				if (boxesList.size() == 0)
				{
					System.out.println("  No Pokemon in boxes!");
					waitForEnter(scan);
				}

				else if (player.getParty().isFull())
				{
					System.out.println("  Your party is full!");
					waitForEnter(scan);
				}

				else
				{

					System.out.print("  Select Pokemon ID (1-" + boxesList.size() + "): ");
					int pokemonId = parseInt(scan.nextLine());

					if (pokemonId > 0 && pokemonId <= boxesList.size())
					{

						Pokemon p = boxesList.get(pokemonId - 1);
						player.getParty().addPokemon(p);
						boxes.removePokemon(p);
						System.out.println("  " + p.getName() + " has been moved to your party!");
						waitForEnter(scan);

					}

				}

			}

			else if (choice == 2) // Move from party to boxes
			{

				// if party is empty => display message
				if (player.getParty().getSize() == 0)
				{
					System.out.println("  Your party is empty!");
					waitForEnter(scan);
				}

				// else => do the task
				else
				{

					System.out.print("  Select Pokemon (1-" + player.getParty().getSize() + "): ");
					int pokemonId = parseInt(scan.nextLine());

					if (pokemonId > 0 && pokemonId <= player.getParty().getSize())
					{

						Pokemon p = player.getParty().getPokemon(pokemonId - 1);

						if (p != null)
						{

							boxes.addPokemon(p);
							player.getParty().removePokemon(pokemonId - 1);
							System.out.println("  " + p.getName() + " has been stored in your boxes!");
							waitForEnter(scan);

						}

					}

				}

			}

			else if (choice == 0) break;

		}

	}


	// -------------------- Option 4: Changing Area -------------------- //


	public static void takeBus (Player player, DisplayMachine inputDisplayMachine, GameMap inputGameMap, Scanner scan)
	{

		int choice = -1;

		while (true)
		{

			inputDisplayMachine.displayMapMenu(player);
			choice = parseInt(scan.nextLine());
			Area currentArea = player.getCurrentArea();

			if (currentArea == null) // current area is empty
			{
				System.out.println("\n  Your current location is not set.");
				waitForEnter(scan);
				break;
			}


			Area nextArea = null;
			String direction = ""; // for displaying message

			if (choice == 0) break; // go back to main menu

			else if (choice == 1) // go to the North
			{
				nextArea = currentArea.north;
				direction = "north";
			}

			else if (choice == 2) // go to the South
			{
				nextArea = currentArea.south;
				direction = "south";
			}

			else if (choice == 3) // go to the West
			{
				nextArea = currentArea.west;
				direction = "west";
			}

			else if (choice == 4) // go to the East
			{
				nextArea = currentArea.east;
				direction = "east";
			}

			else // invalid choice => ignore it and continue to get input
			{
				continue;
			}

			if (nextArea == null)
			{
				System.out.println("\n  You cannot go " + direction + " from " + currentArea.getName());
				waitForEnter(scan);
				continue;
			}

			player.setCurrentArea(nextArea);
			System.out.println("\n  You take the bus " + direction + " to " + nextArea.getName() + "!");
			waitForEnter(scan);
		
		}

	}

	
	// -------------------- Option 5: Manage Party -------------------- //


	public static void manageParty (Player player, DisplayMachine inputDisplayMachine, Scanner scan)
	{

		int choice = -1;

		while (true)
		{

			inputDisplayMachine.displayManagePartyMenu(player);
			choice = parseInt(scan.nextLine());

			if (choice == 1) // Display full detail of pokemon
			{

				// view Pokemon details
				System.out.print("\n  Select Pokemon (1-" + player.getParty().getSize() + "): ");
				int pokemonId = parseInt(scan.nextLine());

				if (pokemonId > 0 && pokemonId <= player.getParty().getSize())
				{

					Pokemon p = player.getParty().getPokemon(pokemonId - 1);
					if (p != null)
					{

						inputDisplayMachine.clearScreen();
						String detailBorder = "==========================================================";
						System.out.println(detailBorder);
						System.out.println("           " + p);  // display pokemon's information
						System.out.println();
						System.out.println(detailBorder);
						System.out.println();
						p.displayMoveActivated();               // display activated moves
						System.out.println();
						System.out.println(detailBorder);
						System.out.println();
						p.displayMoveList();                    // display possible moves
						System.out.println();
						System.out.println(detailBorder);
						waitForEnter(scan);

					}

				}

			}

			else if (choice == 2) // Move pokemon
			{

				// Move Pokemon in party
				System.out.print("\n  Select Pokemon to move (1-" + player.getParty().getSize() + "): ");
				int fromId = parseInt(scan.nextLine());

				if (fromId > 0 && fromId <= player.getParty().getSize()) // only move when fromId is valid
				{

					System.out.print("  Select new position (1-6): ");
					int toId = parseInt(scan.nextLine());

					if (toId > 0 && toId <= 6)
					{

						Pokemon[] party = player.getParty().getParty();
						Pokemon temp = party[fromId - 1];
						party[fromId - 1] = party[toId - 1];
						party[toId - 1] = temp;

						System.out.println("  Pokemon moved successfully!");
						player.getParty().pileUp (); // pile up the party to the front
						waitForEnter(scan);

					}

				}

			}

			else if (choice == 0) break;

		}

	}


	// -------------------- Option 6: Display Inventory -------------------- //


	public static void displayInventory (Player player, DisplayMachine inputDisplayMachine, Scanner scan)
	{

		int choice = -1;

		while (true)
		{

			inputDisplayMachine.displayInventoryMenu(player);
			choice = parseInt(scan.nextLine());

			if (choice > 0 && choice <= 15 && player.getInventory().getItemAmount(choice) > 0)
			{

				System.out.print("\n  Select Pokemon (1-" + player.getParty().getSize() + ") or 0 to cancel: ");
				int pokemonId = parseInt(scan.nextLine());

				if (pokemonId == 0) continue; // Do nothing, go back to item selection

				else if (pokemonId > 0 && pokemonId <= player.getParty().getSize())
				{

					Pokemon p = player.getParty().getPokemon(pokemonId - 1);

					// if pokemon != null && successfully using item
					if (p != null && player.getInventory().getItem(choice).use(new Battle(player, new Trainer(null), false), p, scan))
					{
						player.getInventory().useItem(choice); // decrease amount of item by 1
					}

				}

			}

			else if (choice == 0) break;

		}

	}


	// -------------------- Option 7: Save File -------------------- //


	public static void saveGame (FileManager fileManager, Player player, Scanner scan)
	{
		fileManager.saveGame(player);
		waitForEnter(scan);
	}


	// -------------------- Option 8: Gift Code -------------------- //


	public static void giftCode (Player player, DisplayMachine inputDisplayMachine, Scanner scan)
	{

		/*
		* GOITANTHUVIP: x20 of each items & 100,000 money
		*/

		System.out.print ("Enter giftcode: ");
		String giftcode = scan.nextLine();

		if (giftcode.equals("GOITANTHUVIP"))
		{

			// add items
			for (int i = 1; i <= 15; i++) player.getInventory().addItem(i, 20);

			// add money
			player.addMoney(100000);

			inputDisplayMachine.displayMainMenu(player);
			System.out.println("You've received x20 per each item & 100,000 PokeCoin🪙");
			waitForEnter(scan);

		}

	}

}
