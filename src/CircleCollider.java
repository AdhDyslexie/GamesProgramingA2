public class CircleCollider {
    private double x;
    private double y;
    private int radius;

    CircleCollider(int newX, int newY, int rad) {
        x = newX;
        y = newY;
        radius = rad;
    }

    // -------------------------------------------------------------------- CHECK FOR COLLISIONS --------------------------------------------------------------------
    // Overloads for checking for collisions with another circle
    public boolean IsColliding(double otherX, double otherY, int otherRadius) {
        if (GameEngine.distance(x, y, otherX, otherY) < radius + otherRadius) {
            return true;
        }
        return false;
    }

    public boolean IsColliding(CircleCollider circle) {
        return IsColliding(circle.X(), circle.Y(), circle.Radius());
    }

    // Overloads for checking for collisions with a box
    public boolean IsColliding(int otherLeftX, int otherRightX, int otherTopY, int otherBottomY) {
        // If not within any bounds
        if ((x < otherLeftX || x > otherRightX) && (y > otherBottomY || y < otherTopY)) {
            return false;
        }

        if (   GameEngine.distance(x, y, otherLeftX, otherTopY) > radius
            && GameEngine.distance(x, y, otherLeftX, otherBottomY) > radius
            && GameEngine.distance(x, y, otherRightX, otherTopY) > radius
            && GameEngine.distance(x, y, otherRightX, otherBottomY) > radius) {
            
            // if within bounds of x but are not within bounds of y
            if ((x > otherLeftX && x < otherRightX) && (y - radius > otherBottomY || y + radius < otherTopY)) {
                return false;
            }

            // if within bounds of y but are not within bounds of x
            if ((y < otherBottomY && y > otherTopY) && (x - radius > otherRightX || x + radius < otherLeftX)) {
                return false;
            }
        }

        return true;
    }

    public boolean IsColliding(BoxCollider box) {
        return IsColliding(box.LeftX(), box.RightX(), box.TopY(), box.BottomY());
    }

    // -------------------------------------------------------------------- GET INFO --------------------------------------------------------------------
    public double X() {
        return x;
    }

    public double Y() {
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

    // -------------------------------------------------------------------- OPERATOR METHODS --------------------------------------------------------------------
    // Taking ints
    public void YMinusEquals(int subtract) {
        y -= subtract;
    }

    public void YPlusEquals(int add) {
        y += add;
    }

    public void XMinusEquals(int subtract) {
        x -= subtract;
    }

    public void XPlusEquals(int add) {
        x += add;
    }

    // Taking doubles
    public void YMinusEquals(double subtract) {
        y -= subtract;
    }

    public void YPlusEquals(double add) {
        y += add;
    }

    public void XMinusEquals(double subtract) {
        x -= subtract;
    }

    public void XPlusEquals(double add) {
        x += add;
    }
}
