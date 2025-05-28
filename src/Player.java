public class Player {
    enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    };

    enum MenuOpen {
        INVENTORY,
        TRADING,
        NONE
    }

    double x;
    double y;
    double width;
    double height;
    double speed;
    Boolean IsMoving;
    int money;
    Direction direction;
    double reach;

    Inventory inventory;
    private TradingMenu tradingMenu;
    MenuOpen menuOpen;
    int renderLayer;

    // -1 if not holding any item
    double itemHolding;

    Player() {
        x = 0;
        y = 0;
        width = 30;
        height = 50;
        speed = 5;
        IsMoving = false;
        direction = Direction.UP;
        money = 0;
        menuOpen = MenuOpen.NONE;
        inventory = new Inventory(5);
        tradingMenu = new TradingMenu(32, 32, inventory.renderingBufferSize(), inventory.SizeMultiplier());
        renderLayer = 2;
        reach = 50;
        itemHolding = -1;
    }

    // Called in Main's update function every frame.
    public void update() {
        if (IsMoving) {
            switch (direction) {
                case UP:
                    y = y - speed;
                    break;
                
                case DOWN:
                    y += speed;
                    break;

                case LEFT:
                    x = x - speed;
                    break;

                case RIGHT:
                    x += speed;
                    break;
            }
        }
    }

    public void ReturnItemFromTradingMenu() {
        // Check that there's an item to return
        if (TradingMenuSlotTaken()) {
            inventory.getItemAtIndex(TradingMenuSlotIndex()).setIsInInventory(true);
            setTradingMenuSlotIndex(-1);
            setTradingMenuSlotFull(false);
        }
    }

    // -------------------------------------------------------------------- GET TRADING MENU INFO --------------------------------------------------------------------
    public double TradingMenuX() {
        return tradingMenu.menuX;
    }

    public double TradingMenuY() {
        return tradingMenu.menuY;
    }

    public int TradingMenuWidth() {
        return tradingMenu.menuWidth;
    }

    public int TradingMenuHeight() {
        return tradingMenu.menuHeight;
    }

    public int TradingMenuSlotWidth() {
        return tradingMenu.slotWidth;
    }

    public int TradingMenuSlotHeight() {
        return tradingMenu.slotHeight;
    }

    public int TradingMenuSlotX() {
        return tradingMenu.slotX;
    }

    public int TradingMenuSlotY() {
        return tradingMenu.slotY;
    }

    public boolean TradingMenuSlotTaken() {
        return tradingMenu.itemInSlot;
    }

    public int TradingMenuSlotIndex() {
        return tradingMenu.itemInSlotIndex;
    }

    // -------------------------------------------------------------------- SET TRADING MENU INFO --------------------------------------------------------------------
    public void setTradingMenuSlotFull(boolean newValue) {
        tradingMenu.itemInSlot = newValue;
    }

    public void setTradingMenuSlotIndex(int newValue) {
        tradingMenu.itemInSlotIndex = newValue;
    }
}
