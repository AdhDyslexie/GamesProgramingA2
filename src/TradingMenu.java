public class TradingMenu {
    // X and Y to draw the menu at
    double menuX;
    double menuY;

    // Width and Height of the menu
    int menuWidth;
    int menuHeight;
    
    // X and Y to draw the slot at
    int slotX;
    int slotY;

    // Width and height of the slot
    int slotWidth;
    int slotHeight;

    boolean itemInSlot;

    int itemInSlotIndex;

    private GameEngine.AudioClip buttonSound;

    TradingMenu(int tileWidth, int tileHeight, int buffer, int invSizeMultiplier) {
        menuWidth = 300;
        menuHeight = 120;
        menuX = 100;
        menuY = 150;

        slotWidth = (tileWidth + buffer * 2) * invSizeMultiplier;
        slotHeight = (tileHeight + buffer * 2) * invSizeMultiplier;
        slotX = (menuHeight - slotHeight) / 2;
        slotY = (menuHeight - slotHeight) / 2;
        
        itemInSlot = false;
        itemInSlotIndex = -1;
        buttonColor = Color.BLACK;

        buttonSound = GameEngine.loadAudio("ClickSound.wav");
    }

    // -------------------------------------------------------------------- GET INFO --------------------------------------------------------------------
    // Menu
    public int X() {
        return menuX;
    }

    public int Y() {
        return menuY;
    }

    public int Width() {
        return menuWidth;
    }

    public int Height() {
        return menuHeight;
    }

    // Slot
    public int SlotX() {
        return slotX;
    }

    public int SlotY() {
        return slotY;
    }

    public int SlotWidth() {
        return slotWidth;
    }

    public int SlotHeight() {
        return slotHeight;
    }

    // Button
    public int ButtonX() {
        return buttonX;
    }

    public int ButtonY() {
        return buttonY;
    }

    public int ButtonWidth() {
        return buttonWidth;
    }

    public int ButtonHeight() {
        return buttonHeight;
    }

    public Color ButtonColor() {
        return buttonColor;
    }

    // Slot info
    public boolean SlotTaken() {
        return itemInSlot;
    }

    public int SlotTakenItemIndex() {
        return itemInSlotIndex;
    }

    public GameEngine.AudioClip ButtonSound() {
        return buttonSound;
    }


    // -------------------------------------------------------------------- CALCULATE WORLD COORDINATES --------------------------------------------------------------------
    // Calculate world coordinate positions for slot
    public int SlotLeftWorldX() {
        return menuX + slotX;
    }

    public int SlotRightWorldX() {
        return SlotLeftWorldX() + slotWidth;
    }

    public int SlotTopWorldY() {
        return menuY + slotY;
    }

    public int SlotBottomWorldY() {
        return SlotTopWorldY() + slotHeight;
    }

    // Calculate world coordinate positions for button
    public int ButtonLeftWorldX() {
        return menuX + buttonX;
    }

    public int ButtonRightWorldX() {
        return ButtonLeftWorldX() + buttonWidth;
    }

    public int ButtonTopWorldY() {
        return menuY + buttonY;
    }
    
    public int ButtonBottomWorldY() {
        return ButtonTopWorldY() + buttonHeight;
    }

    // -------------------------------------------------------------------- SET INFO --------------------------------------------------------------------
    public void setSlotTaken(boolean newValue) {
        itemInSlot = newValue;
    }

    public void setSlotTakenIndex(int newValue) {
        itemInSlotIndex = newValue;
    }

    public void setButtonColor(Color newColor) {
        buttonColor = newColor;
    }
}
