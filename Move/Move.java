package Move;
public class Move {
    
    // -------------------- FIELDS -------------------- //


    private int id;            // id of skill/move
    private String name;       // name of skill

    private int unlockedLevel; // level to unlock this skill
    private String type;       // type of skill
    
    private int power;         // damage of skill
    private double accuracy;   // percent that skill will hit enemy
    private int maxPP;         // limit usage of skill
    private int currentPP;     // current number of usage


    // -------------------- CONSTRUCTORS -------------------- //


    public Move ()
    {
        this.name = this.type = "undefined";
        this.id = this.unlockedLevel = this.power = this.maxPP = this.currentPP = -1;
        this.accuracy = -1.0;
    }

    // constructor for using in movedex
    public Move (int inputId, String inputName, String inputType, int inputPower, int inputMaxPP, double inputAccuracy)
    {
        this.id = inputId;
        this.name = inputName;

        this.type = inputType;

        this.power = inputPower;
        this.maxPP = inputMaxPP;
        this.currentPP = this.maxPP; // is started with a fully charge
        this.accuracy = inputAccuracy;
    }

    // is used in pokemon move list
    public Move (Move inputMove, int inputLevel)
    {
        // level to unlock the move
        this.unlockedLevel = inputLevel;

        // copy the move
        this.id = inputMove.getId();
        this.name = inputMove.getName();

        this.type = inputMove.getType();

        this.power = inputMove.getPower();
        this.maxPP = inputMove.getMaxPP();
        this.currentPP = this.maxPP; // is started with a fully charge
        this.accuracy = inputMove.getAccuracy();
    }


    // -------------------- GETTERS -------------------- //

    public int getId ()
    {
        return this.id;
    }

    public String getName ()
    {
        return this.name;
    }

    public int getUnlockedLevel ()
    {
        return this.unlockedLevel;
    }

    public String getType ()
    {
        return this.type;
    }

    public int getPower ()
    {
        return this.power;
    }

    public double getAccuracy ()
    {
        return this.accuracy;
    }

    public int getMaxPP ()
    {
        return this.maxPP;
    }

    public int getCurrentPP ()
    {
        return this.currentPP;
    }


    // -------------------- SETTERS -------------------- //


    public void setCurrentPP (int inputPP)
    {
        this.currentPP = inputPP;
    }


    // -------------------- DISPLAY METHODS -------------------- //


    @Override
    public String toString ()
    {
        return String.format("Name: %s | Type: %s | Power: %d | Accuracy: %.0f%% | PP: %d/%d", this.name, this.type, this.power, this.accuracy, this.currentPP, this.maxPP);
    }

}
