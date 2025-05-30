public class Map {
    // Holds the info for all of the tilemaps.

    // Market Map
    private int marketRenderingLayers;
    private TileMap[] marketMap;

    // Player Home Map
    // private int homeRenderingLayers;
    // private TileMap[] homeMap;

    Map () {
        marketRenderingLayers = 4;
        // 0 - Background
        // 1 - Floor tiles
        // 2 - Midground
        // 3 - Player

        marketMap = new TileMap[marketRenderingLayers - 1];

        marketMap[0] = new TileMap(39);
        marketMap[0].addRowToMap(new int[]{5, 26, 27}, new int[]{1, 7, 8}, 2);
        marketMap[0].addRowToMap(new int[]{4, 31, 15, 4, 13, 14, 15, 4}, new int[]{0, 1, 2, 6, 7, 8, 9, 15}, 3);
        marketMap[0].addRowToMap(new int[]{6, 22, 7, 23, 12, 6, 22, 22, 7, 23, 12, 6}, new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 14, 15}, 4);
        marketMap[0].addRowToMap(new int[]{28, 21, 30, 28, 21, 20, 30, 28}, new int[]{0, 1, 2, 6, 7, 8, 9, 15}, 5);
        marketMap[0].addRowToMap(new int[]{28, 29, 30, 28, 29, 20, 30, 28}, new int[]{0, 1, 2, 6, 7, 8, 9, 15}, 6);

        marketMap[1] = new TileMap(144);
        marketMap[1].addFloorToMap(2, 24, 144, 0, 7);

        marketMap[2] = new TileMap(38);
        marketMap[2].addRowToMap(new int[]{5}, new int[]{11}, 2);
        marketMap[2].addRowToMap(new int[]{4, 31, 15}, new int[]{10, 11, 12}, 3);
        marketMap[2].addRowToMap(new int[]{5, 4, 13, 20, 14, 15}, new int[]{4, 9, 10, 11, 12, 13}, 4);
        marketMap[2].addRowToMap(new int[]{4, 31, 15, 12, 6, 22, 22, 22, 7, 23}, new int[]{3, 4, 5, 8, 9, 10, 11, 12, 13, 14}, 5);
        marketMap[2].addRowToMap(new int[]{12, 6, 21, 7, 23, 28, 21, 20, 20, 30}, new int[]{2, 3, 4, 5, 6, 9, 10, 11, 12, 13}, 6);
        marketMap[2].addRowToMap(new int[]{28, 29, 30, 28, 29, 20, 20, 30}, new int[]{3, 4, 5, 9, 10, 11, 12, 13}, 7);
    }


    public int MarketRenderLayers() {
        return marketRenderingLayers;
    }

    public TileMap[] MarketMap() {
        return marketMap;
    }
}
