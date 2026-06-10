package Player;
import Pokemon.Pokemon;


public class Trainer {
    
    // -------------------- FIELDS -------------------- //


    private String name;   // Name of the trainer | e.g, "Satoshi"
    private Party party;   // Pokemon team of the trainer (max 6 pokemons)


    // -------------------- CONSTRUCTORS -------------------- //


    // this constructor is used in for Player.java class only
    public Trainer (String inputName)
    {
        this.name = inputName;
        this.party = new Party();
    }

    // this constructor is used for NPC
    public Trainer (String inputName, Party inputParty)
    {
        this.name = inputName;
        this.party = new Party(inputParty);
    }


    // -------------------- GETTERS -------------------- //


    // get trainer's name
    public String getName ()
    {
        return this.name;
    }

    // get trainer's party OBJECT
    public Party getParty ()
    {
        return this.party;
    }

    // get the 1st alive pokemon in trainer team
    public Pokemon getLeadPokemon ()
    {

        for (Pokemon p : party.getParty())
        {
            if (p != null && p.getHp() > 0) return p;
        }

        return null;

    }

}
