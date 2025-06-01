public class Map {
    // Holds the info for all of the tilemaps.
    enum MapToRender {
        MARKET,
        HOME
    }

    MapToRender mapToRender;

    // Market Map
    private int marketRenderingLayers;
    private TileMap[] marketMap;
    private TileSet marketTileSet;

    // Player Home Map
    private int homeRenderingLayers;
    private TileMap[] homeMap;
    private TileSet homeTileSet;

    // Colliders for the edges of the map
    BoxCollider topCollider;
    BoxCollider bottomCollider;
    BoxCollider leftCollider;
    BoxCollider rightCollider;

    // Colliders for the houses at the top of the screen
    BoxCollider leftHouseCollider;
    BoxCollider rightHouseCollider;

    Map () {
        mapToRender = MapToRender.HOME;

        marketRenderingLayers = 4;
        // 0 - Background
        // 1 - Floor tiles
        // 2 - Midground
        // 3 - Player (4 in Player)
        marketTileSet = new TileSet("tilesetv2.png", 32, 32, 4, 8);

        marketMap = new TileMap[marketRenderingLayers - 1];

        marketMap[0] = new TileMap(39, marketTileSet);
        marketMap[0].addRowToMap(new int[]{5, 26, 27}, new int[]{1, 7, 8}, 2);
        marketMap[0].addRowToMap(new int[]{4, 31, 15, 4, 13, 14, 15, 4}, new int[]{0, 1, 2, 6, 7, 8, 9, 15}, 3);
        marketMap[0].addRowToMap(new int[]{6, 22, 7, 23, 12, 6, 22, 22, 7, 23, 12, 6}, new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 14, 15}, 4);
        marketMap[0].addRowToMap(new int[]{28, 21, 30, 28, 21, 20, 30, 28}, new int[]{0, 1, 2, 6, 7, 8, 9, 15}, 5);
        marketMap[0].addRowToMap(new int[]{28, 29, 30, 28, 29, 20, 30, 28}, new int[]{0, 1, 2, 6, 7, 8, 9, 15}, 6);

        marketMap[1] = new TileMap(144, marketTileSet);
        marketMap[1].addFloorToMap(2, 24, 144, 0, 7, 16);

        marketMap[2] = new TileMap(38, marketTileSet);
        marketMap[2].addRowToMap(new int[]{5}, new int[]{11}, 2);
        marketMap[2].addRowToMap(new int[]{4, 31, 15}, new int[]{10, 11, 12}, 3);
        marketMap[2].addRowToMap(new int[]{5, 4, 13, 20, 14, 15}, new int[]{4, 9, 10, 11, 12, 13}, 4);
        marketMap[2].addRowToMap(new int[]{4, 31, 15, 12, 6, 22, 22, 22, 7, 23}, new int[]{3, 4, 5, 8, 9, 10, 11, 12, 13, 14}, 5);
        marketMap[2].addRowToMap(new int[]{12, 6, 21, 7, 23, 28, 21, 20, 20, 30}, new int[]{2, 3, 4, 5, 6, 9, 10, 11, 12, 13}, 6);
        marketMap[2].addRowToMap(new int[]{28, 29, 30, 28, 29, 20, 20, 30}, new int[]{3, 4, 5, 9, 10, 11, 12, 13}, 7);


        homeRenderingLayers = 5;
        // 0 - All walls except top, floor
        // 1 - Back decor, top of walls
        // 2 - Front decor
        // Player renders here
        // 3 - Fronter decor
        homeTileSet = new TileSet("indoorTileset.png", 32, 32, 4, 5);

        homeMap = new TileMap[homeRenderingLayers - 1];

        homeMap[0] = new TileMap(43, homeTileSet);
        homeMap[0].addFloorToMap(3, 0, 15, 6, 4, 5);
        homeMap[0].addFloorToMap(2, 5, 20, 6, 7, 5);
        homeMap[0].addRowToMap(new int[]{14, 4}, new int[]{5, 11}, 8);
        homeMap[0].addRowToMap(new int[]{14, 4}, new int[]{5, 11}, 9);
        homeMap[0].addRowToMap(new int[]{14, 4}, new int[]{5, 11}, 10);
        homeMap[0].addRowToMap(new int[]{19, 9}, new int[]{5, 11}, 11);

        homeMap[1] = new TileMap(21, homeTileSet);
        homeMap[1].addRowToMap(new int[]{11, 7, 7, 7, 7, 7, 12}, new int[]{5, 6, 7, 8, 9, 10, 11}, 3);
        homeMap[1].addRowToMap(new int[]{16, 17}, new int[]{5, 11}, 4);
        homeMap[1].addRowToMap(new int[]{16, 17}, new int[]{5, 11}, 5);
        homeMap[1].addRowToMap(new int[]{16, 3, 3, 17}, new int[]{5, 7, 10, 11}, 6);
        homeMap[1].addRowToMap(new int[]{16, 8, 8, 17}, new int[]{5, 7, 10, 11}, 7);
        homeMap[1].addRowToMap(new int[]{18, 13}, new int[]{5, 11}, 8);

        homeMap[2] = new TileMap(5, homeTileSet);
        homeMap[2].addRowToMap(new int[]{15, 10}, new int[]{7, 10}, 6);
        homeMap[2].addRowToMap(new int[]{15}, new int[]{9}, 7);
        homeMap[2].addRowToMap(new int[]{10}, new int[]{10}, 8);
        homeMap[2].addRowToMap(new int[]{15}, new int[]{6}, 9);

        homeMap[3] = new TileMap(1, homeTileSet);
        homeMap[3].addRowToMap(new int[]{10}, new int[]{10}, 10);

        // Market colliders
        topCollider = new BoxCollider(0, 500, 214, 224);
        bottomCollider = new BoxCollider(0, 500, 500, 510);
        leftCollider = new BoxCollider(-10, 0, 214, 510);
        rightCollider = new BoxCollider(500, 510, 214, 510);

        leftHouseCollider = new BoxCollider(98, 188, 224, 256);
        rightHouseCollider = new BoxCollider(290, 446, 224, 256);
    }


    public int MarketRenderLayers() {
        return marketRenderingLayers;
    }

    public TileMap[] MarketMap() {
        return marketMap;
    }

    public int HomeRenderLayers() {
        return homeRenderingLayers;
    }

    public TileMap[] HomeMap() {
        return homeMap;
    }

    public int currentMapRenderLayers() {
        switch (mapToRender) {
            case MARKET:
                return marketRenderingLayers;

            case HOME:
                return homeRenderingLayers;
        }
        return -1;
    }

    public TileMap[] CurrentMap() {
        switch (mapToRender) {
            case MARKET:
                return marketMap;

            case HOME:
                return homeMap;
        }
        return null;
    }
}