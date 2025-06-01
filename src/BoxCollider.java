public class BoxCollider {
    private int leftX;
    private int rightX;
    private int topY;
    private int bottomY;

    BoxCollider(int lX, int rX, int tY, int bY) {
        leftX = lX;
        topY = tY;

        if (lX > rX) {
            System.out.println("Error creating box collider: Left X is greater than Right X");
            rightX = leftX + 10;
        } else {
            rightX = rX;
        }
        
        if (tY > bY) {
            System.out.println("Error creating box collider: Top Y is greater than Bottom Y");
            bottomY = topY + 10;
        } else {
            bottomY = bY;
        }
    }

    // -------------------------------------------------------------------- CHECK FOR COLLISIONS --------------------------------------------------------------------
    // Overloads for checking for collisions with another box
    public boolean IsColliding(int otherLeftX, int otherRightX, int otherTopY, int otherBottomY) {
        if (   otherLeftX > leftX && otherLeftX < rightX
            || otherRightX > leftX && otherRightX < rightX) {
            
            // They're colliding!
            return true;
        }

        return false;
    }

    public boolean IsColliding(BoxCollider box) {
        return IsColliding(box.LeftX(), box.RightX(), box.TopY(), box.BottomY());
    }

    // Overloads for checking for collisions with a circle
    public boolean IsColliding(double otherX, double otherY, int otherRadius) {
        // If not within any bounds
        if ((otherX < leftX || otherX > rightX) && (otherY > bottomY || otherY < topY)) {
            return false;
        }

        // If the circle is not colliding with any of the corners, it may not be colliding
        if (   GameEngine.distance(leftX, topY, otherX, otherX) > otherRadius
            && GameEngine.distance(leftX, bottomY, otherX, otherY) > otherRadius
            && GameEngine.distance(rightX, topY, otherX, otherY) > otherRadius
            && GameEngine.distance(rightX, bottomY, otherX, otherY) > otherRadius) {
            
            // if within bounds of x but are not within bounds of y
            if ((otherX > leftX && otherX < rightX) && (otherY - otherRadius > bottomY || otherY + otherRadius < topY)) {
                return false;
            }

            // if within bounds of y but are not within bounds of x
            if ((otherY < bottomY && otherY > topY) && (otherX - otherRadius > rightX || otherX + otherRadius < leftX)) {
                return false;
            }
        }

        return true;
    }

    public boolean IsColliding(CircleCollider circle) {
        return IsColliding(circle.X(), circle.Y(), circle.Radius());
    }

    // -------------------------------------------------------------------- GET METHODS --------------------------------------------------------------------
    public int LeftX() {
        return leftX;
    }
    
    public int RightX () {
        return rightX;
    }

    public int TopY() {
        return topY;
    }

    public int BottomY() {
        return bottomY;
    }
}