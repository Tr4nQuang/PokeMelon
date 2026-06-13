package Engine;

import java.io.*;
import java.util.*;
import Map.Area;
import Map.GameMap;

import Inventory.Inventory;
import Move.Move;
import Move.Movedex;
import Player.Player;
import Player.Party;
import Player.Boxes;
import Pokemon.Pokedex;
import Pokemon.Pokemon;


public class FileManager {

    // -------------------- FIELDS -------------------- //


    File saveFile = new File("player_save.txt");


    // -------------------- HELPERS -------------------- //


    private void writePokemon(BufferedWriter writer, Pokemon p) throws IOException
    {

        writer.write(p.getId() + "," + p.getLevel() + "," + p.getExp() + "," + p.getHp() + ","
            + p.getIvHp() + "," + p.getIvAtk() + "," + p.getIvDfs() + "," + p.getIvSpd());

        Move[] activatedMoves = p.getMoveActivated();
        int activatedCount = 0;
        for (Move m : activatedMoves) if (m != null) activatedCount++;

        writer.write("," + activatedCount);

        for (Move m : activatedMoves)
        {
            if (m != null) writer.write("," + m.getId() + "," + m.getUnlockedLevel() + "," + m.getCurrentPP());
        }

        ArrayList<Move> moveList = p.getMoveList();
        writer.write("," + moveList.size());

        for (Move m : moveList) writer.write("," + m.getId() + "," + m.getUnlockedLevel() + "," + m.getCurrentPP());

        writer.newLine();

    }


    private Pokemon readPokemon(String line, Pokedex pokedex, Movedex movedex)
    {

        String[] token = line.split(",");
        int index = 0;

        int id = parseInt(token[index++]);
        int level = parseInt(token[index++]);
        int exp = parseInt(token[index++]);
        int hp = parseInt(token[index++]);
        int ivHp = parseInt(token[index++]);
        int ivAtk = parseInt(token[index++]);
        int ivDfs = parseInt(token[index++]);
        int ivSpd = parseInt(token[index++]);

        Pokemon pokemon = new Pokemon(pokedex, id, level, exp, hp, ivHp, ivAtk, ivDfs, ivSpd);

        int activatedCount = parseInt(token[index++]);

        for (int j = 0; j < activatedCount; j++)
        {
            int moveId = parseInt(token[index++]);
            int unlockedLevel = parseInt(token[index++]);
            int currentPP = parseInt(token[index++]);

            Move move = new Move(movedex.getMove(moveId), unlockedLevel);
            move.setCurrentPP(currentPP);
            pokemon.getMoveActivated()[j] = move;
        }

        int moveListCount = parseInt(token[index++]);

        for (int j = 0; j < moveListCount; j++)
        {
            int moveId = parseInt(token[index++]);
            int unlockedLevel = parseInt(token[index++]);
            int currentPP = parseInt(token[index++]);

            Move move = new Move(movedex.getMove(moveId), unlockedLevel);
            move.setCurrentPP(currentPP);
            pokemon.getMoveList().add(move);
        }

        return pokemon;

    }

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


    // check if the save file exists and has data or not
    public boolean checkSaveFile()
    {
        return saveFile.exists() && saveFile.length() > 0;
    }


    // save the Player data to the text file
    public void saveGame(Player player)
    {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile)))
        {

            // write basic stats
            writer.write(player.getName());
            writer.newLine();
            writer.write(String.valueOf(player.getMoney()));
            writer.newLine();

            // save current location and position
            if (player.getCurrentArea() != null) writer.write(player.getCurrentArea().getName());
            else writer.write("🏡 Kiwi Village");

            writer.newLine();
            writer.write(player.getPositionX() + "," + player.getPositionY());
            writer.newLine();

            // save badges as a list that is seperated by comma (indexes 1..8)
            boolean[] badges = player.getBadgeCount();
            
            for (int i = 1; i <= 8; i++)
            {
                writer.write(Boolean.toString(badges[i]));
                if (i < 8) writer.write(",");
            }

            writer.newLine();

            // save inventory counts
            int[] inventoryCounts = player.getInventory().getInventory();

            for (int i = 0; i < inventoryCounts.length; i++)
            {
                writer.write(Integer.toString(inventoryCounts[i]));
                if (i < inventoryCounts.length - 1) writer.write(",");
            }

            writer.newLine();

            // save party
            Pokemon[] partyPokemon = player.getParty().getParty();
            int partySize = 0;

            for (Pokemon p : partyPokemon) if (p != null) partySize++;

            writer.write(Integer.toString(partySize));
            writer.newLine();

            for (Pokemon p : partyPokemon)
            {
                if (p != null) writePokemon(writer, p);
            }

            // Save box list
            ArrayList<Pokemon> boxList = player.getBoxes().getBoxes();
            writer.write(Integer.toString(boxList.size()));
            writer.newLine();

            for (Pokemon p : boxList) writePokemon(writer, p);

            System.out.println("💾 Game saved successfully!");
            

        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    // load the Player data from the text file using BufferedReader
    public Player loadGame(GameMap gameMap)
    {

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile)))
        {

            // read basic stats
            String name = reader.readLine();
            int money = parseInt(reader.readLine());
            String areaName = reader.readLine();

            // read position
            String positionLine = reader.readLine();
            int positionX = 0;
            int positionY = 0;

            if (positionLine != null && positionLine.contains(","))
            {
                String[] positionParts = positionLine.split(",");

                positionX = parseInt(positionParts[0]);
                positionY = parseInt(positionParts[1]);
            }

            // read badges
            boolean[] badges = new boolean[9];
            String badgeLine = reader.readLine();

            if (badgeLine != null)
            {
                String[] badgeStrings = badgeLine.split(",");
                int savedBadges = Math.min(badgeStrings.length, 8);

                for (int i = 1; i <= savedBadges; i++) badges[i] = Boolean.parseBoolean(badgeStrings[i - 1]);
            }

            // read inventory
            Inventory inventory = new Inventory();
            String inventoryLine = reader.readLine();

            if (inventoryLine != null)
            {
                String[] inventoryStrings = inventoryLine.split(",");
                int[] inventoryCounts = inventory.getInventory();
                int savedItems = Math.min(inventoryStrings.length, inventoryCounts.length);

                for (int i = 0; i < savedItems; i++) inventoryCounts[i] = Integer.parseInt(inventoryStrings[i]);
            }

            // read party
            Pokedex pokedex = new Pokedex();
            Movedex movedex = new Movedex();
            int partySize = parseInt(reader.readLine());
            ArrayList<Pokemon> partyList = new ArrayList<Pokemon>();

            for (int i = 0; i < partySize; i++)
            {
                String pokemonLine = reader.readLine();
                Pokemon pokemon = readPokemon(pokemonLine, pokedex, movedex);
                partyList.add(pokemon);
            }

            // read boxes
            int boxSize = parseInt(reader.readLine());
            Boxes boxes = new Boxes();

            for (int i = 0; i < boxSize; i++)
            {
                String pokemonLine = reader.readLine();
                Pokemon pokemon = readPokemon(pokemonLine, pokedex, movedex);
                boxes.addPokemon(pokemon);
            }

            Area currentArea = gameMap.getArea(areaName);
            if (currentArea == null) currentArea = new Area(areaName, -1);
            Party party = new Party(partyList);
            Player loadedPlayer = new Player(name, party, money, badges, boxes, inventory, currentArea, positionX, positionY);

            return loadedPlayer;
            
        }
        
        catch (IOException | NumberFormatException e)
        {
            System.out.println("❌ Error: Save file is corrupted or missing!!!");
            e.printStackTrace ();
            return null;
        }

    }

}
