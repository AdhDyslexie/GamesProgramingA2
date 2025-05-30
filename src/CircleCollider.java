public class CircleCollider {
    private int x;
    private int y;
    private int radius;

    CircleCollider(int newX, int newY, int rad) {
        x = newX;
        x = newX;
        radius = rad;
    }

    // -------------------------------------------------------------------- CHECK FOR COLLISIONS --------------------------------------------------------------------
    // Overload for checking for collisions with another circle
    public boolean IsColliding(int otherX, int otherY, int otherRadius) {
        if (GameEngine.distance(x, y, otherX, otherY) < radius + otherRadius) {
            return true;
        }
        return false;
    }

    // Overload for checking for collisions with a box
    public boolean IsColliding(int otherLeftX, int otherRightX, int otherTopY, int otherBottomY) {
        if (   GameEngine.distance(x, y, otherLeftX, otherTopY) > radius
            && GameEngine.distance(x, y, otherLeftX, otherBottomY) > radius
            && GameEngine.distance(x, y, otherRightX, otherTopY) > radius
            && GameEngine.distance(x, y, otherRightX, otherBottomY) > radius) {
            
            // if within bounds of x but are not within bounds of y
            if ((x > otherLeftX && x < otherRightX) && (y - radius > otherBottomY | y + radius < otherTopY)) {
                return false;
            }

            // if within bounds of y but are not within bounds of x
            if ((y > otherBottomY && y < otherTopY) && (x - radius > otherLeftX | x + radius < otherRightX)) {
                return false;
            }
        }

        return true;
    }

    // -------------------------------------------------------------------- GET INFO --------------------------------------------------------------------
    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    public int Radius() {
        return radius;
    }

    // -------------------------------------------------------------------- SET INFO --------------------------------------------------------------------
    public void setX(int newValue) {
        x = newValue;
    }

    public void setY(int newValue) {
        y = newValue;
    }

    public void setRadius(int newValue) {
        radius = newValue;
    }
}
