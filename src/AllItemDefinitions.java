import java.awt.Image;

public class AllItemDefinitions {
    private Image tileSet;

    ItemDefinition[] items;

    AllItemDefinitions() {
        items = new ItemDefinition[12];
        tileSet = GameEngine.loadImage("tilesetv2.png");
        items[0] = new ItemDefinition(GameEngine.subImage(tileSet, 0, 0, 32, 32), "Open Fern", "This guy knows all about ]healthy communication", 5, ItemDefinition.PlantType.FERN, ItemDefinition.MainColor.DARKGREEN);
        items[1] = new ItemDefinition(GameEngine.subImage(tileSet, 32, 0, 32, 32), "Closed Fern", "Probably needs a hug", 20, ItemDefinition.PlantType.FERN, ItemDefinition.MainColor.LIGHTGREEN);
        items[2] = new ItemDefinition(GameEngine.subImage(tileSet, 64, 0, 32, 32), "Red Mushroom", "The first thing you think of when ]someone says 'mushroom'", 13, ItemDefinition.PlantType.MUSHROOM, ItemDefinition.MainColor.RED);
        items[3] = new ItemDefinition(GameEngine.subImage(tileSet, 96, 0, 32, 32), "Leafy Plant", "What do you PLAN To do with this?", 9, ItemDefinition.PlantType.GREENERY, ItemDefinition.MainColor.LIGHTGREEN);
        items[4] = new ItemDefinition(GameEngine.subImage(tileSet, 0, 32, 32, 32), "Rose", "How ROSEmantic!!!", 19, ItemDefinition.PlantType.BIGFLOWER, ItemDefinition.MainColor.RED);
        items[5] = new ItemDefinition(GameEngine.subImage(tileSet, 32, 32, 32, 32), "Moss", "MOSSt people don't see the value", 3, ItemDefinition.PlantType.GREENERY, ItemDefinition.MainColor.DARKGREEN);
        items[6] = new ItemDefinition(GameEngine.subImage(tileSet, 64, 32, 32, 32), "Stick", "Just one tiny leaf, clinging on", 7, ItemDefinition.PlantType.GREENERY, ItemDefinition.MainColor.BROWN);
        items[7] = new ItemDefinition(GameEngine.subImage(tileSet, 96, 32, 32, 32), "Lily of the Valley", "Definitely not poisonous. Trust me.", 15, ItemDefinition.PlantType.SMALLFLOWERS, ItemDefinition.MainColor.WHITE);
        items[8] = new ItemDefinition(GameEngine.subImage(tileSet, 0, 64, 32, 32), "Purple Flower Vine", "I dunno I ran out of ideas", 12, ItemDefinition.PlantType.SMALLFLOWERS, ItemDefinition.MainColor.PURPLE);
        items[9] = new ItemDefinition(GameEngine.subImage(tileSet, 32, 64, 32, 32), "Brown Mushroom", "Slightly less generic than ]the other mushrooms", 11, ItemDefinition.PlantType.MUSHROOM, ItemDefinition.MainColor.BROWN);
        items[10] = new ItemDefinition(GameEngine.subImage(tileSet, 64, 64, 32, 32), "Lilypad and Flower", "LILYPAD a little flower, little flower] little flower!", 23, ItemDefinition.PlantType.BIGFLOWER, ItemDefinition.MainColor.WHITE);
        items[11] = new ItemDefinition(GameEngine.subImage(tileSet, 96, 64, 32, 32), "Lavender", "So pretty!!!", 18, ItemDefinition.PlantType.BIGFLOWER, ItemDefinition.MainColor.PURPLE);
    }

    public ItemDefinition getDefinitionAtIndex(int i) {
        if (i < items.length) {
            return items[i];
        }
        return null;
    }

    public int numberOfItems() {
        return items.length;
    }
}