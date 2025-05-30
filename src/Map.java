import java.util.Random;

public class Map {
    // Holds the data for one tile on the map
    public class mapTile {
        Tile tile;
        int x;
        int y;

        mapTile(int tile_index, int newX, int newY) {
            tile = tileSet.tiles[tile_index];
            x = newX;
            y = newY;
        }
    }
    private TileSet tileSet;
    private int firstFreeIndex;

    // Map dimensions in pixels
    // private double mapHeight;
    // private double mapWidth;

    // Holds all the tiles!
    mapTile[] tileMap;

    Map(int totalNumTiles) {
        // mapHeight = 500;
        // mapWidth = 500;

        tileSet = new TileSet();

        tileMap = new mapTile[totalNumTiles];
        firstFreeIndex = 0;
    }

    public int getTileWidth() {
        return tileSet.tileWidth;
    }

    public int getTileHeight() {
        return tileSet.tileHeight;
    }

    public boolean enoughSpaceInMap(int tilesToAdd) {
        return enoughSpaceInMap(tilesToAdd, "Error adding tiles! Not enough space in map.");
    }

    public boolean enoughSpaceInMap(int tilesToAdd, String message) {
        if (firstFreeIndex + tilesToAdd > tileMap.length) {
            System.out.println(message);
            return false;
        }
        return true;
    }

    // I made this function like this because it was the quickest and easiest way for me to translate a
    // concept image of the tilemap I wanted into the code that the program understands. Previously, the
    // constructor took an int[][] - an array of tile info arrays.
    public void addRowToMap(int[] tileCodes, int[] xPositions, int row) {
        // In case the arrays were not input correctly, print an error message and return.
        if (tileCodes.length != xPositions.length) {
            System.out.println("Error adding row " + row + "! Array lengths are not the same.");
            return;
        }

        // If there are not enough empty spaces in the map to add this row, print an error message and return.
        if(!enoughSpaceInMap(tileCodes.length, "Error adding row " + row + "! Not enough empty spaces left in the map.")) {
            return;
        }

        for (int i = 0; i < tileCodes.length; i++) {
            tileMap[i + firstFreeIndex] = new mapTile(tileCodes[i], xPositions[i], row);
        }

        firstFreeIndex += tileCodes.length;
    }

    public void addTileToMap(int indx, int x, int y) {
        // If the tilemap is full, print an error message and return.
        if (!enoughSpaceInMap(1, "Error adding a tile! Map is already full.")) {
            return;
        }
        tileMap[firstFreeIndex] = new mapTile(indx, x, y);
        firstFreeIndex++;
    }

    public void addFloorToMap(int randomOptions, int OptionsStartIndex, int tilesToAdd, int startX, int startY) {
        Random random = new Random();
        for (int i = 0; i < tilesToAdd; i++) {
            addTileToMap(random.nextInt(randomOptions) + OptionsStartIndex, i % 16 + startX, i / 16 + startY);
        }
    }
}