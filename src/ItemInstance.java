import java.awt.Image;

public class ItemInstance {
    private ItemDefinition info;

    // Used to tell the program whether to draw the item in an inventory position or at its x and y coords. Useful for if we want to implement drag and drop inventory stuff.
    private boolean IsInInventory;

    // Stores where the item should be drawn if not in the player's inventory.
    private int x;
    private int y;

    ItemInstance(int xpos, int ypos, boolean isInInv, ItemDefinition inf) {
        x = xpos;
        y = ypos;
        IsInInventory = isInInv;
        info = inf;
    }

    // --------------------------------------- GET METHODS ---------------------------------------------

    public Image Image() {
        return info.icon;
    }

    public String Name() {
        return info.name;
    }

    public String Description() {
        return info.description;
    }

    public int Value() {
        return info.value;
    }

    public int Weight() {
        return info.weight;
    }

    public boolean IsInInventory() {
        return IsInInventory;
    }

    public int xPos() {
        return x;
    }

    public int yPos() {
        return y;
    }

    // --------------------------------------- SET METHODS ---------------------------------------------

    public void setIsInInventory(boolean newValue) {
        IsInInventory = newValue;
    }

    public void setXPos(int newValue) {
        x = newValue;
    }

    public void setYPos(int newValue) {
        y = newValue;
    }
}