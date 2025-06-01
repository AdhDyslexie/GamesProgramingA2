import java.util.Random;

public class TileMap {
    // Holds the data for one tile on the map
    public class mapTile {
        Tile tile;
        int x;
        int y;

        // Constructor
        mapTile(int tile_index, int newX, int newY) {
            tile = tileSet.tiles[tile_index];
            x = newX;
            y = newY;
        }
    }

    // Holds all the tiles!
    mapTile[] tileMap;

    // Stores the next free index, so we can easily add tiles to the array.
    private int firstFreeIndex;

    TileSet tileSet;

    // Constructor
    TileMap(int totalNumTiles, final TileSet tiles) {

        tileSet = tiles;

        tileMap = new mapTile[totalNumTiles];
        firstFreeIndex = 0;
    }

    // -------------------------------------------------------------------- GET INFO --------------------------------------------------------------------
    public int TileWidth() {
        return tileSet.tileWidth;
    }

    public int TileHeight() {
        return tileSet.tileHeight;
    }

    // -------------------------------------------------------------------- CHECK STUFF --------------------------------------------------------------------
    // Check if there's enough space left in the mapTile array to add the desired number of tiles - this overload of the function is here so there can be a default message.
    public boolean enoughSpaceInMap(int tilesToAdd) {
        return enoughSpaceInMap(tilesToAdd, "Error adding tiles! Not enough space in map.");
    }

    // The function that actually checks if there's enough space left in the mapTile array to add the desired number of tiles.
    public boolean enoughSpaceInMap(int tilesToAdd, String message) {
        if (firstFreeIndex + tilesToAdd > tileMap.length) {
            System.out.println(message);
            return false;
        }
        return true;
    }


    // -------------------------------------------------------------------- ADD STUFF TO THIS LAYER --------------------------------------------------------------------

    // I made the below function the way I did because it was the quickest and easiest way for me to translate a
    // concept image of the tilemap I wanted into the code that the program understands. Previously, the
    // constructor took an int[][] - an array of tile info arrays.

    // Add a row of tiles to the map
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

    // Add a single tile to the map
    public void addTileToMap(int indx, int x, int y) {
        // If the tilemap is full, print an error message and return.
        if (!enoughSpaceInMap(1, "Error adding a tile! Map is already full.")) {
            return;
        }
        tileMap[firstFreeIndex] = new mapTile(indx, x, y);
        firstFreeIndex++;
    }

    // Add a ton of random tiles to the map.
    public void addFloorToMap(int randomOptions, int OptionsStartIndex, int tilesToAdd, int startX, int startY, int width) {
        Random random = new Random();
        for (int i = 0; i < tilesToAdd; i++) {
            addTileToMap(random.nextInt(randomOptions) + OptionsStartIndex, i % width + startX, i / width + startY);
        }
    }
}