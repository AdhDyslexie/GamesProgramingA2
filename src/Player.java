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

    double oldX;
    double oldY;

    double x;
    double y;
    double width;
    double height;
    private double speed;
    Boolean IsMoving;
    int money;
    Direction direction;
    double reach;

    boolean left;
    boolean right;
    boolean up;
    boolean down;

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
        y = 300;
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
        initanimations();
    }

    // Called in Main's update function every frame.
    public void update() {
        oldX = x;
        oldY = y;

        // If player is moving diagonally, lower the amount their speed in each direction is changed
        if ((up || down) && (left || right)) {
            speed = 3.5;
        }

        // Move player depending on the keys they have pressed
        if (up) {
            y -= speed;
            collider.YMinusEquals(speed);
            animstate = 1;
        }
        if (down) {
            y += speed;
            collider.YPlusEquals(speed);
            animstate = 3;
        }
        if (left) {
            x -= speed;
            collider.XMinusEquals(speed);
            animstate = 2;
        }
        if (right) {
            x += speed;
            collider.XPlusEquals(speed);
            animstate = 4;
        }

        if (y > oldY) {
            direction = Direction.UP;
        }
        if (y < oldY) {
            direction = Direction.DOWN;
        }
        if (x > oldX) {
            direction = Direction.RIGHT;
        }
        if (x < oldX) {
            direction = Direction.LEFT;
        }

        // If the player has changed location, IsMoving is true. else, IsMoving is false.
        if (y != oldY || x != oldX) {
            IsMoving = true;
        } else {
            IsMoving = false;
            animstate = 0;
        }

        speed = 5;
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
            frames[i] = new Tile(GameEngine.subImage(animsheet, (int)(mx * width), (int)(my * height), (int)width, (int)height));
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
