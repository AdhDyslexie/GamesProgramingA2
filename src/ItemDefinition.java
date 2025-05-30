import java.awt.Image;

public class ItemDefinition {
    // Two enums used to decide how much money the player will get from trades
    enum PlantType {
        FLOWER,         // Rose and vine with flowers
        MUSHROOM,       // Red and brown mushrooms
        LEAFY,          // Items with any leaves
        PLAIN           // Basically stuff that doesn't fit in the other categories. Moss, Lilypad
    };

    enum MainColor {
        RED,            // Rose and red mushroom
        DARKGREEN,      // Moss and three leafed plant
        LIGHTGREEN,     // Lilypad and two leafed plant
        PURPLE,         // Vine with flowers
        BROWN           // Stick, brown mushrooms
    };

    // Image (pixel art)
    Image icon;

    // Name
    String name;

    // Description
    String description;

    // Type
    PlantType type;
    MainColor color;

    // Value
    int value;
    
    // Weight
    int weight;

    ItemDefinition(Image img, String nam, String des, int val, int wei, PlantType typ, MainColor col) {
        icon = img;
        name = nam;
        description = des;
        value = val;
        weight = wei;
        type = typ;
        color = col;
    }
}