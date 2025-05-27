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
    }
}
