package Engine;

import Pokemon.Pokemon;

public class TypeChart {

    // -------------------- FIELDS -------------------- //
    

    private static final double[][] typeChart = {
        // Row: Attacker Move's type
        // Col: Defender's type
        // [Attacker Move's Type][Defender's Type] = effiency that 
        // Note:
        // ** Col 0 is useful and all values are 1.0 because there are many pokemon doesn't have Type2
        // ** Row 0 is useless and all values are 0.0 because there is no move/pokemon's skill with No-Type type
        //   0    1    2    3    4    5    6    7    8    9   10   11   12   13   14   15   16   17   18
        { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // 0
        { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 0.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 }, // 1
        { 1.0, 2.0, 1.0, 0.5, 0.5, 1.0, 2.0, 0.5, 0.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 2.0, 0.5 }, // 2
        { 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0 }, // 3
        { 1.0, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 0.5, 0.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0 }, // 4
        { 1.0, 1.0, 1.0, 0.0, 2.0, 1.0, 2.0, 0.5, 1.0, 2.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0 }, // 5
        { 1.0, 1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0 }, // 6
        { 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 2.0, 0.5 }, // 7
        { 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 1.0 }, // 8
        { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 0.5, 1.0, 2.0, 1.0, 1.0, 2.0 }, // 9
        { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 2.0, 0.5, 0.5, 2.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0 }, // 10
        { 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0 }, // 11
        { 1.0, 1.0, 1.0, 0.5, 0.5, 2.0, 2.0, 0.5, 1.0, 0.5, 0.5, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0 }, // 12
        { 1.0, 1.0, 1.0, 2.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 0.5, 1.0, 1.0 }, // 13
        { 1.0, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 0.0, 1.0 }, // 14
        { 1.0, 1.0, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 2.0, 1.0, 1.0, 0.5, 2.0, 1.0, 1.0 }, // 15
        { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.0 }, // 16
        { 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5 }, // 17
        { 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0 }  // 18
    };


    // -------------------- METHODS -------------------- //


    // translate Type from String ("ice") to int (15)
    public int translateType (String inputType)
    {
        if (inputType == null) return 0;

        if (inputType.equalsIgnoreCase("normal")) return 1;
        if (inputType.equalsIgnoreCase("fighting")) return 2;
        if (inputType.equalsIgnoreCase("flying")) return 3;
        if (inputType.equalsIgnoreCase("poison")) return 4;
        if (inputType.equalsIgnoreCase("ground")) return 5;
        if (inputType.equalsIgnoreCase("rock")) return 6;
        if (inputType.equalsIgnoreCase("bug")) return 7;
        if (inputType.equalsIgnoreCase("ghost")) return 8;
        if (inputType.equalsIgnoreCase("steel")) return 9;
        if (inputType.equalsIgnoreCase("fire")) return 10;
        if (inputType.equalsIgnoreCase("water")) return 11;
        if (inputType.equalsIgnoreCase("grass")) return 12;
        if (inputType.equalsIgnoreCase("electric")) return 13;
        if (inputType.equalsIgnoreCase("psychic")) return 14;
        if (inputType.equalsIgnoreCase("ice")) return 15;
        if (inputType.equalsIgnoreCase("dragon")) return 16;
        if (inputType.equalsIgnoreCase("dark")) return 17;
        if (inputType.equalsIgnoreCase("fairy")) return 18;

        return 0;

    }

    // calculate the effectiveness of Attacker's move on Defender
    public double calculateEffectiveness (int inputMoveType, Pokemon inputDefender)
    {
        if (inputDefender == null || inputMoveType <= 0 || inputMoveType >= typeChart.length) return 1.0;

        int defenderType1 = this.translateType(inputDefender.getType1());
        int defenderType2 = this.translateType(inputDefender.getType2());

        double effectiveness1 = typeChart[inputMoveType][defenderType1];
        double effectiveness2 = typeChart[inputMoveType][defenderType2];

        return effectiveness1 * effectiveness2;
    }

    // display the message of Effectiveness
    public String getEffectiveness (int inputMoveType, Pokemon inputDefender)
    {
        if (inputDefender == null) return "";

        double effectiveness = calculateEffectiveness(inputMoveType, inputDefender);

        if (effectiveness == 0.0) return "It doesn't affect " + inputDefender.getName();

        else if (0.0 < effectiveness && effectiveness < 1.0) return "It's not very effective...";

        else if (effectiveness == 1.0) return "";

        else if (1.0 < effectiveness && effectiveness < 4.0) return "It's super effective!"; 

        else if (effectiveness == 4.0) return "It's double super effective!!!";

        else return "Error in Effectiveness???";

    }

}
