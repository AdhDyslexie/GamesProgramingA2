import java.awt.Image;

public class ItemDefinition {
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

    ItemDefinition(Image img, String nam, String des, int val, int wei) {
        icon = img;
        name = nam;
        description = des;
        value = val;
        weight = wei;
    }
}