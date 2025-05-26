import java.awt.Image;

public class Item {
    
    // Image (pixel art)
    Image icon;
    // Name
    String name;
    // Description
    String description;
    // Type
    // Value
    int value;
    // Weight
    int weight;

    Item(Image i, String n, String d, int v, int w) {
        icon = i;
        name = n;
        description = d;
        value = v;
        weight = w;
    }
}
