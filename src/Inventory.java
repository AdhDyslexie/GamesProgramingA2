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
    
    public void addItem() {}
    public Item getItemAtIndex(int index) { return null; }
    public void removeItemAtIndex(int index) {}


}