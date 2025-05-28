public class DayCycle {
    // Setting up the DayCycle like this allows for easy multiplayer implimentation in the future. The Main class gets its own DayCycle,
    // so that the remaining days and actions per day are the same worldwide, but each player stores how many actions left they can take
    // on any given day.
    private int daysRemaining;
    private int startingDays;
    private int actionsPerDay;

    // Basic Constructor
    DayCycle() {
        startingDays = 7;
        daysRemaining = startingDays;
        actionsPerDay = 5;
    }

    // Constructor taking starting number of days and actions per day
    DayCycle(int startDays, int actionsPD) {
        startingDays = startDays;
        daysRemaining = startingDays;
        actionsPerDay = actionsPD;
    }

    // -------------------------------------------------------------------- GET INFO --------------------------------------------------------------------
    public int DaysRemaining(){
        return daysRemaining;
    }

    public int ActionsPerDay() {
        return actionsPerDay;
    }

    // -------------------------------------------------------------------- SET INFO --------------------------------------------------------------------
    public void setDaysRemaining(int newValue) {
        daysRemaining = newValue;
    }

    public void decrimentDaysRemaining() {
        daysRemaining--;
    }

    public int NewDay() {
        daysRemaining--;
        // Returns actions per day so we can set the player's actions to it.
        return actionsPerDay;
    }
}