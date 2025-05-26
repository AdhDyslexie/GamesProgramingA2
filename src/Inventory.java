import java.awt.Image;

public class Inventory {

    private int maxSize;
    private Item[] items;

    Inventory(int size) {
        setMaxSize(size);
        items = new Item[maxSize];
    }

    public int maxSize() {
        return maxSize;
    }

    public void setMaxSize(int size) {
        maxSize = size;
    }

    // Temporary methods
    
    public void addItem(Item new_item) {
        for (int i = 0; i < maxSize; i++) {
            if (items[i] == null) {
                items[i] = new_item;
            }
        }
    }

    public void addItem(Image img, String name, String des, int val, int wei) {
        for (int i = 0; i < maxSize; i++) {
            if (items[i] == null) {
                items[i] = new Item(img, name, des, val, wei);
            }
        }
    }

    public Item getItemAtIndex(int index) {
        return items[index];
    }

    public void removeItemAtIndex(int index) {
        items[index] = null;
    }
}