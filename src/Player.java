public class Player {

    double x;
    double y;
    double width;
    double height;
    double speed;
    Boolean bIsMoving;
    int direction;
    int money;

    Inventory inventory;

    Player() {
        init();
    }

    public void init() {
        x = 0;
        y = 0;
        width = 50;
        height = 50;
        speed = 5;
        bIsMoving = false;
        direction = 0; // 0: up, 1: right, 2: down, 3: left
        money = 0;
    }

    
   
}
