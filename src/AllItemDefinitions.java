import java.awt.Image;

public class AllItemDefinitions {
    private Image tileSet;

    ItemDefinition[] items;

    AllItemDefinitions() {
        items = new ItemDefinition[2];
        tileSet = GameEngine.loadImage("tileset.png");
        items[0] = new ItemDefinition(GameEngine.subImage(tileSet, 0, 0, 32, 32), "Rock", "Ohhhh yeahhhhh what a rock", 5, 10, ItemDefinition.PlantType.LEAFY, ItemDefinition.MainColor.BROWN);
        items[1] = new ItemDefinition(GameEngine.subImage(tileSet, 32, 0, 32, 32), "Plant", "what do you PLAN To do with this", 20, 2, ItemDefinition.PlantType.PLAIN, ItemDefinition.MainColor.DARKGREEN);
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