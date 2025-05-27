public class Inventory {

    private int maxSize;
    private int renderingBufferSize;
    private ItemInstance[] items;
    private AllItemDefinitions definitions;
    private int sizeMultiplier;

    Inventory(int size) {
        setMaxSize(size);
        renderingBufferSize = 4;
        items = new ItemInstance[maxSize];
        sizeMultiplier = 2;
    }

    public int maxSize() {
        return maxSize;
    }

    public void setMaxSize(int size) {
        maxSize = size;
    }

    public int renderingBufferSize() {
        return renderingBufferSize;
    }

    public int SizeMultiplier() {
        return sizeMultiplier;
    }
    
    // Returns a boolean for whether adding the item was sucessful
    public boolean addItem(ItemInstance new_item) {
        for (int i = 0; i < maxSize; i++) {
            if (items[i] == null) {
                items[i] = new_item;
                items[i].setIsInInventory(true);
                return true;
            }
        }
        return false;
    }

    // Returns a boolean for whether adding the item was sucessful
    public boolean addItem(int xPos, int yPos, boolean isInInv, int defIndex) {
        for (int i = 0; i < maxSize; i++) {
            if (items[i] == null) {
                items[i] = new ItemInstance(0, 0, true, definitions.getDefinitionAtIndex(defIndex));
                return true;
            }
        }
        return false;
    }

    public ItemInstance getItemAtIndex(int index) {
        return items[index];
    }

    public void removeItemAtIndex(int index) {
        items[index] = null;
    }
}