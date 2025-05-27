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

    // Map dimensions in pixels
    private double mapHeight;
    private double mapWidth;

    // Holds all the tiles!
    mapTile[] tileMap;

    Map(int[][] tileMapInfo) {
        mapHeight = 500;
        mapWidth = 500;

        tileSet = new TileSet();

        tileMap = new mapTile[tileMapInfo.length];
        for (int i = 0; i < tileMapInfo.length; i++) {
            tileMap[i] = new mapTile(tileMapInfo[i][0], tileMapInfo[i][1], tileMapInfo[i][2]);
        }
    }

    public int getTileWidth() {
        return tileSet.tileWidth;
    }

    public int getTileHeight() {
        return tileSet.tileHeight;
    }
}