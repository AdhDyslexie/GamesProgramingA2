public class Player {
    enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    };

    double x;
    double y;
    double width;
    double height;
    double speed;
    Boolean IsMoving;
    int money;
    Direction direction;

    Inventory inventory;
    boolean menuOpen;
    int renderLayer;

    Player() {
        x = 0;
        y = 0;
        width = 30;
        height = 50;
        speed = 5;
        IsMoving = false;
        direction = Direction.UP;
        money = 0;
        menuOpen = false;
        inventory = new Inventory(5);
        renderLayer = 2;
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
}
