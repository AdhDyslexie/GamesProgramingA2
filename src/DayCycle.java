public class DayCycle {
    // Setting up the DayCycle like this allows for easy multiplayer implimentation in the future. The Main class gets its own DayCycle,
    // so that the remaining days and actions per day are the same worldwide, but each player stores how many actions left they can take
    // on any given day.
    private int currentDay;
    private int actionsPerDay;

    // Basic Constructor
    DayCycle() {
        currentDay = 0;
        actionsPerDay = 5;
    }

    // Constructor taking starting number of days and actions per day
    DayCycle(int actionsPD) {
        currentDay = 0;
        actionsPerDay = actionsPD;
    }

    // -------------------------------------------------------------------- GET INFO --------------------------------------------------------------------
    public int DaysRemaining(){
        return currentDay;
    }

    public int ActionsPerDay() {
        return actionsPerDay;
    }

    // -------------------------------------------------------------------- SET INFO --------------------------------------------------------------------
    public void setCurrentDay(int newValue) {
        currentDay = newValue;
    }

    public void incrimentDaysRemaining() {
        currentDay++;
    }

    public int NewDay() {
        currentDay++;

        // Returns actions per day so we can set the player's actions to it.
        return actionsPerDay;
    }
}