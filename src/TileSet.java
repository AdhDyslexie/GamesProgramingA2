import java.awt.Image;

public class TileSet {
    Image tileSetImage;

    // Tile dimensions in pixels
    int tileHeight;
    int tileWidth;

    // Update this when adding/removing tiles from the tileset
    int rowsInTileSet;
    int columnsInTileSet;

    // Number of tiles in the tileset, automatically calculated
    int numTiles;

    Tile[] tiles;

    TileSet() {
        tileSetImage = GameEngine.loadImage("tilesetv2.png");

        tileWidth = 32;
        tileHeight = 32;

        rowsInTileSet = 4;
        columnsInTileSet = 8;
        numTiles = rowsInTileSet * columnsInTileSet;
        tiles = new Tile[numTiles];
        int x;
        int y;
        for (int i = 0; i < numTiles; i++) {
            x = i % columnsInTileSet;
            y = i / columnsInTileSet;
            tiles[i] = new Tile(GameEngine.subImage(tileSetImage, x * tileWidth, y * tileHeight, tileWidth, tileHeight));
        }
    }
}