package Map;
import Map.Area;

import java.util.*;
import java.io.*;


public class GameMap
{
    
    
    // -------------------- FIELDS -------------------- //
    
    
    // hashmap is used to access a node Area quickly
    public HashMap <String, Area> map = new HashMap ();
    
    
    // -------------------- CONSTRUCTORS -------------------- //
    

    /*
    public GameMap ()
    {
        this.map.put ("null", null);
    }
    */
    
    // constructor with a single map database file as input
    public GameMap ()
    {

        File file = new File("Data_Base_files/map_dataBase.txt");

        // add the null area into the map (this area is treated as the border for the outer areas)
        this.map.put("null", null);

        if (!file.exists())
        {
            System.out.println("map_dataBase.txt does not exist!");
            return;
        }

        ArrayList<String[]> records = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {

            String line;

            while ((line = reader.readLine()) != null)
            {

                line = line.trim(); // remove all extra space at the front & end of string

                if (line.isEmpty()) continue;

                String[] parts = line.split(","); // split the line into multiple string (using "," to split)

                if (parts.length != 6) // a standard input line should include 6 piece of string
                {
                    System.out.println("Invalid map_dataBase line: " + line);
                    continue;
                }

                String areaName = parts[0].trim();
                int areaType = parseInt(parts[1].trim());
                Area area = new Area(areaName, areaType);
                this.map.put(areaName, area);
                records.add(parts);

            }
        }

        // catch exception
        catch (IOException e)
        {
            e.printStackTrace();
        }


        // do the connection
        for (String[] parts : records)
        {

            Area area = this.map.get(parts[0]);

            if (area == null) continue; // if area is null => skip

            area.north = getArea(parts[2].trim());
            area.south = getArea(parts[3].trim());
            area.west = getArea(parts[4].trim());
            area.east = getArea(parts[5].trim());

        }

    }


    // -------------------- HELPERS -------------------- //


    // is used to get area by entering their name
    public Area getArea (String name)
    {
        // if the text file says there is no exit ("none") => return null
        if (name.equals("none") || name.equals("null")) return null;
        
        // otherwise, just grab the Area directly from the map & return it
        return this.map.get(name);
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
    
}
