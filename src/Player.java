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
    private double speed;
    Boolean IsMoving;
    int money;
    Direction direction;
    double reach;

    Inventory inventory;
    TradingMenu tradingMenu;
    MenuOpen menuOpen;
    int renderLayer;

    CircleCollider collider;

    // -1 if not holding any item
    double itemHolding;

    private int actionsRemaining;

    Player() {
        x = 100;
        y = 200;
        width = 30;
        height = 50;
        speed = 5;
        IsMoving = false;
        direction = Direction.UP;
        money = 0;
        menuOpen = MenuOpen.NONE;
        inventory = new Inventory(5);
        tradingMenu = new TradingMenu(32, 32, inventory.renderingBufferSize(), inventory.SizeMultiplier());
        renderLayer = 4;
        reach = 30;
        itemHolding = -1;

        collider = new CircleCollider((int)x, (int)(y - width / 2), (int)width / 2);
    }

    // Called in Main's update function every frame.
    public void update() {
        if (IsMoving) {
            switch (direction) {
                case UP:
                    y = y - speed;
                    collider.setY((int)(y - width / 2));
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
        if (tradingMenu.SlotTaken()) {
            inventory.getItemAtIndex(tradingMenu.SlotTakenItemIndex()).setIsInInventory(true);
            tradingMenu.setSlotTakenIndex(-1);
            tradingMenu.setSlotTaken(false);
        }
    }

    // -------------------------------------------------------------------- GET PLAYER INFO --------------------------------------------------------------------
    public int ActionsRemaining() {
        return actionsRemaining;
    }

    // -------------------------------------------------------------------- SET PLAYER INFO --------------------------------------------------------------------
    public void setActionsRemaining(int newValue) {
        actionsRemaining = newValue;
    }

    public void decrimentActionsRemaining() {
        actionsRemaining--;
    }
}
