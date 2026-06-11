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
    

    public GameMap ()
    {
        this.map.put ("null", null);
    }
    
    // constructor with array of map files as a parameter
    public GameMap (File[] files)
    {
        
        // add the null area into the map (this area is treated as the border for the outter areas)
        this.map.put ("null", null);

        // fulfill the hashmap with new areas first (name & map)
        for (int i = 0; i < files.length; i++)
        {
            
            if (!files[i].exists ())
            {
                System.out.println ("File #" + (i) + " does not exist!!!");
                return;
            }
            
            
            // read all the name and map of files
            try
            {
                
                // create a buffered for reading input file   
                BufferedReader reader = new BufferedReader (new FileReader (files[i]));
                
                // create new area (using name)
                // logic: name of area is written in the 1st line of file, 2nd line is type of area
                // therefore, only read the 1st & 2nd line of each file to create new area
                String areaName = reader.readLine ();
                int areaType = parseInt(reader.readLine());
                Area area = new Area (areaName, areaType);
                
                // get map from the input file (map will be written on the next 30 lines)
                String tmp;

                for (int j = 0; j < 30; j++) // each map has 30 rows / lines
                {
                    
                    tmp = reader.readLine ();
                    
                    for (int k = 0; k < tmp.length (); k++) area.map[j][k] = tmp.charAt (k);
                    
                }
                
                // put the new area into the hashmap
                this.map.put (area.getName(), area);
                
                // close the reader
                reader.close ();
                
            }
            
            // catch if there is any IO Exception
            catch (IOException e)
            {
                e.printStackTrace ();
            }
            
        }
        
        
        // connects areas together
        for (int i = 0; i < files.length; i++)
        {
            
            // do not need to check the existence of files again
            
            // read the connections between areas
            try
            {
                
                // create a buffered for reading input file
                BufferedReader reader = new BufferedReader (new FileReader (files[i]));
                
                // get the area that the current file represents (1st line always contains "Area Name", then use hashmap to get its Node Area)
                Area area = this.map.get (reader.readLine ());
                
                // skip the contents that were already read (From line 2 -> line 31 || 1 -> 30)
                for (int j = 1; j <= 30; j++) reader.readLine ();
                
                // read the connections (North - South - West - East)
                for (int j = 0; j < 4; j++)
                {
                    
                    // then, base on whether i = 0 -> 3 (AKA: North -> East) to assgin the area
                    if (j == 0) area.north = this.map.get (reader.readLine ());
                    if (j == 1) area.south = this.map.get (reader.readLine ());
                    if (j == 2) area.west = this.map.get (reader.readLine ());
                    if (j == 3) area.east = this.map.get (reader.readLine ());
                    
                }
                
                // close the reader
                reader.close ();
                
            }
            
            // catch if there is any IO Exception
            catch (IOException e)
            {
                e.printStackTrace ();
            }
            
        }
        
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
