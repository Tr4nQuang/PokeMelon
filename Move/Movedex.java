package Move;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Pokemon.Pokemon;

public class Movedex
{

    // -------------------- FIELDS -------------------- //

    private Move[] movedex = new Move [1000];   // dictionary of all moves in the game (using index as move id)
    private int size = 0;                       // amount of move in the movedex


    // -------------------- CONSTRUCTORS -------------------- //


    public Movedex ()
    {

        File moveDataBase = new File("Data_Base_files/move_dataBase.txt");

        // check if file exists or not
        if (!moveDataBase.exists())
        {
            System.out.println ("move_dataBase.txt does not exist!");
            return;
        }

        // read informations of moves
        try
        {
            
            BufferedReader reader = new BufferedReader(new FileReader(moveDataBase));
            String str;

            // move fields
            int inputId;
            String inputName;
            
            String inputType; // type of skill
            
            int inputPower; // damage of skill
            int inputMaxPP; // limit usage of skill
            double inputAccuracy; // percent that skill will hit enemy

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

                // type
                inputType = str.substring(0, str.indexOf(" "));
                str = str.substring(str.indexOf(" ") + 1);

                // power
                inputPower = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // maxPP
                inputMaxPP = parseInt(str.substring(0, str.indexOf(" ")));
                str = str.substring(str.indexOf(" ") + 1);

                // accuracy
                inputAccuracy = parseDouble(str.substring(0));
                

                // create the pokemon and put it into the pokedex
                Move move = new Move(inputId, inputName, inputType, inputPower, inputMaxPP, inputAccuracy);
                movedex[inputId] = move;
                this.size++;

            }
            
            reader.close ();

        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    // -------------------- GETTERS -------------------- //


    // get number of move in movedex
    public int getSize ()
    {
        return this.size;
    }

    // get a specific move (using id)
    public Move getMove (int inputId)
    {
        return this.movedex[inputId];
    }

    // get list of moves
    public Move[] getMovedex ()
    {
        return this.movedex;
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

}