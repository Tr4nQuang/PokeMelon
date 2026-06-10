package Pokemon;


public class GrowthRate {
    

    // -------------------- Fields -------------------- //


    private String rate; // e.g, "slow", "medium_slow", "medium_fast", "fast", "fluctuating", "erratic"


    // -------------------- Constructors -------------------- //


    public GrowthRate ()
    {
        rate = "undefined";
    }

    public GrowthRate (String inputRate)
    {
        rate = inputRate.toLowerCase();
    }


    // -------------------- Getter -------------------- //


    public String getRate ()
    {
        return rate;
    }


    // -------------------- Methods -------------------- //


    public int nextLevelCalculator (int currentLevel)
    {

        // n: next level => we calculate required exp to reach to the next level
        int n = currentLevel + 1;

        if (rate.equals ("erratic"))
        {

            if (n <= 50) return (n * n * n) * (100 - n) / 50;

            if (50 < n && n <= 68) return (n * n * n) * (150 - n) / 100;

            if (68 < n && n <= 98) return (n * n * n) * ((1911 - 10 * n) / 3) / 500;

            if (98 < n && n <= 100) return (n * n * n) * (160 - n) / 100;

        }

        if (rate.equals("fast")) return 4 * (n * n * n) / 5;

        if (rate.equals("medium_fast")) return (n * n * n);

        if (rate.equals("medium_slow")) return (6 * (n * n * n) / 5) - (15 * (n * n)) + (100 * n) - 140;

        if (rate.equals("slow")) return 5 * (n * n * n) / 4;

        if (rate.equals("fluctuating"))
        {

            if (n <= 15) return (n * n * n) * ((((n + 1) / 3) + 24) / 50);

            if (15 < n && n <= 36) return (n * n * n) * ((n + 14) / 50);

            if (36 < n && n <= 100) return (n * n * n) * (((n / 2) + 32) / 50);

        }

        return -1;

    }

}
