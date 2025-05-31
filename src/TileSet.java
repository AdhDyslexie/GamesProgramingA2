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

    TileSet(String fileName, int width, int height, int rows, int columns) {
        tileSetImage = GameEngine.loadImage(fileName);

        tileWidth = width;
        tileHeight = height;

        rowsInTileSet = rows;
        columnsInTileSet = columns;
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