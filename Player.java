import java.awt.Image;
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
    TradingMenu tradingMenu;
    MenuOpen menuOpen;
    int renderLayer;

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
    }

    // Called in Main's update function every frame.
    public void update() {
        if (IsMoving) {
            switch (direction) {
                case UP:
                    y = y - speed;
                    animstate = 1;
                    break;
                
                case DOWN:
                    y += speed;
                    animstate = 3;
                    break;

                case LEFT:
                    x = x - speed;
                    animstate = 2;
                    break;

                case RIGHT:
                    x += speed;
                    animstate = 4;
                    break;
            }
        } else { animstate = 0; }
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

    Image animsheet;
    int  rowsInPlayerSheet;
    int columnsInPlayerSheet;
    int numframes;
    Tile[] frames;
    int spriteWidth = 30;
    int spriteHeight = 50;
    int animstate = 0;
    int animframe = 1;
    int delay = 0;
    public void initanimations() {
        animsheet = GameEngine.loadImage("playeranimsheet.png");
        rowsInPlayerSheet = 5;
        columnsInPlayerSheet = 4;
        numframes = rowsInPlayerSheet *  columnsInPlayerSheet;
        int mx;
        int my;
        frames = new Tile[numframes];
        for (int i = 0; i < numframes;i++) {
            mx = i % columnsInPlayerSheet;
            my = i / columnsInPlayerSheet;
            frames[i] = new Tile(GameEngine.subImage(animsheet, mx * spriteWidth, my * spriteHeight, spriteWidth,spriteHeight));
        }
    }
    public Tile animate() {
        delay++;
        if (delay == 4) {
            animframe++;
            delay = 0;
        }
        if (animframe == 5) {animframe = 1;}
        return frames[(animstate*4)+animframe-1];
    }
}

