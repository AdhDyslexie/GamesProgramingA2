public class TradingMenu {
    // X and Y to draw the menu at
    private int menuX;
    private int menuY;

    // Width and Height of the menu
    private int menuWidth;
    private int menuHeight;
    
    // X and Y to draw the slot at, relative to the menu location
    private int slotX;
    private int slotY;

    // Width and height of the slot
    private int slotWidth;
    private int slotHeight;

    // X and Y to draw a Trade button at, relative to the menu location
    private int buttonX;
    private int buttonY;

    // Width and Height of the button
    private int buttonWidth;
    private int buttonHeight;

    private boolean itemInSlot;

    private int itemInSlotIndex;

    TradingMenu(int tileWidth, int tileHeight, int buffer, int invSizeMultiplier) {
        menuWidth = 300;
        menuHeight = 120;
        menuX = 100;
        menuY = 150;

        slotWidth = (tileWidth + buffer * 2) * invSizeMultiplier;
        slotHeight = (tileHeight + buffer * 2) * invSizeMultiplier;
        slotX = (menuHeight - slotHeight) / 2;
        slotY = (menuHeight - slotHeight) / 2;

        // menuWidth - (slotX * 2 + slotWidth)          gives space for the button to be able to be drawn
        // (above - buttonWidth) / 2                    gives button x
        // (menuHeight - buttonHeight) / 2              gives button y
        buttonWidth = 140;
        buttonHeight = 40;
        buttonX = ((menuWidth - (slotX + slotWidth) - buttonWidth) / 2) + (slotX + slotWidth);
        // buttonY = (menuHeight - buttonHeight) / 2; // This will draw the button in the middle of the avaliable space
        buttonY = slotY; // This will draw the button in line with the top edge of the slot
        
        itemInSlot = false;
        itemInSlotIndex = -1;
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

    // Slot info
    public boolean SlotTaken() {
        return itemInSlot;
    }

    public int SlotTakenItemIndex() {
        return itemInSlotIndex;
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
}
