package Engine;

import java.io.*;
import java.util.*;
import Map.Area;
import Map.GameMap;

import Inventory.Inventory;
import Player.Player;
import Player.Party;
import Player.Boxes;
import Pokemon.Pokedex;
import Pokemon.Pokemon;


public class SaveManager {

    // -------------------- FIELDS -------------------- //


    File saveFile = new File("player_save.txt");


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


    // -------------------- OPERATIONS -------------------- //


    // check if the save file exists and has data
    public boolean hasSaveFile()
    {
        return saveFile.exists() && saveFile.length() > 0;
    }


    // save the Player data to the text file
    public void saveGame(Player player)
    {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile)))
        {
            // Write basic stats line by line
            writer.write(player.getName());
            writer.newLine();
            writer.write(player.getMoney());
            writer.newLine();

            // Save current location and position
            if (player.getCurrentArea() != null) writer.write(player.getCurrentArea().getName());
            else writer.write("Kiwi Village");

            writer.newLine();
            writer.write(player.getPositionX() + "," + player.getPositionY());
            writer.newLine();

            // Save badges as a comma-separated list (indexes 1..8)
            boolean[] badges = player.getBadgeCount();
            for (int i = 1; i <= 8; i++)
            {
                writer.write(Boolean.toString(i < badges.length ? badges[i] : false));
                if (i < 8) writer.write(",");
            }

            writer.newLine();

            // Save inventory counts
            int[] inventoryCounts = player.getInventory().getInventory();
            for (int i = 0; i < inventoryCounts.length; i++)
            {
                writer.write(Integer.toString(inventoryCounts[i]));
                if (i < inventoryCounts.length - 1) writer.write(",");
            }

            writer.newLine();

            // Save party
            Pokemon[] partyPokemon = player.getParty().getParty();
            int partySize = 0;
            for (Pokemon p : partyPokemon) if (p != null) partySize++;
            writer.write(Integer.toString(partySize));
            writer.newLine();
            for (Pokemon p : partyPokemon)
            {
                if (p != null)
                {
                    writer.write(p.getId() + "," + p.getLevel() + "," + p.getHp());
                    writer.newLine();
                }
            }

            // Save box list
            ArrayList<Pokemon> boxList = player.getBoxes().getBoxes();
            writer.write(Integer.toString(boxList.size()));
            writer.newLine();
            for (Pokemon p : boxList)
            {
                writer.write(p.getId() + "," + p.getLevel() + "," + p.getHp());
                writer.newLine();
            }

            System.out.println("💾 Game saved successfully!");
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    // load the Player data from the text file using BufferedReader
    public Player loadGame()
    {

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile)))
        {

            // read basic stats
            String name = reader.readLine();
            int money = Integer.parseInt(reader.readLine());
            String areaName = reader.readLine();

            // read position
            String positionLine = reader.readLine();
            int positionX = 0;
            int positionY = 0;
            if (positionLine != null && positionLine.contains(","))
            {
                String[] positionParts = positionLine.split(",");
                positionX = Integer.parseInt(positionParts[0]);
                positionY = Integer.parseInt(positionParts[1]);
            }

            // read badges
            boolean[] badges = new boolean[9];
            String badgeLine = reader.readLine();
            if (badgeLine != null)
            {
                String[] badgeStrings = badgeLine.split(",");
                int savedBadges = Math.min(badgeStrings.length, 8);
                for (int i = 1; i <= savedBadges; i++)
                {
                    badges[i] = Boolean.parseBoolean(badgeStrings[i - 1]);
                }
            }

            // read inventory
            Inventory inventory = new Inventory();
            String inventoryLine = reader.readLine();
            if (inventoryLine != null)
            {
                String[] inventoryStrings = inventoryLine.split(",");
                int[] inventoryCounts = inventory.getInventory();
                int savedItems = Math.min(inventoryStrings.length, inventoryCounts.length);
                for (int i = 0; i < savedItems; i++)
                {
                    inventoryCounts[i] = Integer.parseInt(inventoryStrings[i]);
                }
            }

            // read party
            Pokedex pokedex = new Pokedex();
            int partySize = Integer.parseInt(reader.readLine());
            ArrayList<Pokemon> partyList = new ArrayList<Pokemon>();
            for (int i = 0; i < partySize; i++)
            {
                String[] parts = reader.readLine().split(",");
                int id = Integer.parseInt(parts[0]);
                int level = Integer.parseInt(parts[1]);
                int hp = Integer.parseInt(parts[2]);
                Pokemon pokemon = new Pokemon(pokedex, id, level);
                pokemon.setHp(hp);
                partyList.add(pokemon);
            }

            // read boxes
            int boxSize = Integer.parseInt(reader.readLine());
            Boxes boxes = new Boxes();
            for (int i = 0; i < boxSize; i++)
            {
                String[] parts = reader.readLine().split(",");
                int id = Integer.parseInt(parts[0]);
                int level = Integer.parseInt(parts[1]);
                int hp = Integer.parseInt(parts[2]);
                Pokemon pokemon = new Pokemon(pokedex, id, level);
                pokemon.setHp(hp);
                boxes.addPokemon(pokemon);
            }

            Area currentArea = new Area(areaName, -1);
            Party party = new Party(partyList);
            Player loadedPlayer = new Player(name, party, money, badges, boxes, inventory, currentArea, positionX, positionY);
            return loadedPlayer;
            
        }
        
        catch (IOException | NumberFormatException e)
        {
            System.out.println("❌ Error: Save file is corrupted or missing.");
            return null;
        }
    }

}