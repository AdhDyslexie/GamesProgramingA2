import java.awt.Image;

public class ItemDefinition {
    // Two enums used to decide how much money the player will get from trades
    enum PlantType {
        BIGFLOWER,      // Rose, lavender, lilypad
        SMALLFLOWERS,   // Vine with flowers, lily of the valley
        GREENERY,       // Moss, leafy plant, stick
        MUSHROOM,       // Red mushroom, brown mushroom
        FERN            // Open fern, closed fern
    };

    enum MainColor {
        RED,            // Rose and red mushroom
        DARKGREEN,      // Moss and open fern
        LIGHTGREEN,     // Curled fern, leafy plant
        PURPLE,         // Vine with flowers, lavender
        BROWN,          // Stick, brown mushrooms
        WHITE           // Lilypad and lily of the valley
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

    ItemDefinition(Image img, String nam, String des, int val, PlantType typ, MainColor col) {
        icon = img;
        name = nam;
        description = des;
        value = val;
        type = typ;
        color = col;
    }
}