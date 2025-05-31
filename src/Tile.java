import java.awt.Image;

public class Tile {
    private int height;
    private int width;

    Image image;

    Tile(Image i) {
        image = i;
        height = 1;
        width = 1;
    }

    Tile(Image i, int h, int w) {
        image = i;
        height = h;
        width = w;
    }

    public int Height() {
        return height;
    }

    public int Width() {
        return width;
    }

    public Image Image() {
        return image;
    }
}
