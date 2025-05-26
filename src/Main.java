import java.awt.event.*;

public class Main extends GameEngine{

    public static void main(String[] args) {

        int framerate = 60;
        createGame(new Main(), framerate);
    }
    boolean inventoryIsOpen;

    int tilemapLayers;

    Player player;
    Map map[];
    Npc npc;

    public void init() {
        mWidth = 500;
        mHeight = 500;

        setWindowSize(mWidth, mHeight);

        inventoryIsOpen = false;

        tilemapLayers = 2;

        player = new Player();
        map = new Map[tilemapLayers];
        npc = new Npc();

        map[0] = new Map(new int[][]{{0, 0, 1}, {0, 1, 1}, {0, 2, 1}, {1, 1, 0}, {1, 2, 0}});
        map[1] = new Map(new int[][]{{1, 2, 1}});
    }

    @Override
    public void update(double dt) {
        
    }

    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
		clearBackground(mWidth, mHeight);

        drawPlayer();

        if (inventoryIsOpen) {
            drawInventory();
        }
        drawMap();
    }

    // ########################################### \\
    // ############# Draw methods ################ \\
    // ########################################### \\

    public void drawPlayer() {
        // Draw the player at the current position
        changeColor(red);
        saveCurrentTransform();
        translate(player.x, player.y);
        
        drawRectangle(0, 0, player.width, player.height);
        restoreLastTransform();
    }

    public void drawInventory() {
        saveCurrentTransform();

        changeColor(white);
        translate(100, 150);
        drawSolidRectangle(0, 0, 300, 200);

        restoreLastTransform();
    }

    public void drawMap() {
        // For each layer
        for (int i = 0; i < tilemapLayers; i++) {
            // For each tile in this layer
            for (int j = 0; j < map[i].tileMap.length; j++) {
                // Draw this tile at its tile-based location * the tile width
                drawImage(map[i].tileMap[j].tile.image, map[i].tileMap[j].x * map[i].tileSet.tileWidth, map[i].tileMap[j].y * map[i].tileSet.tileHeight);
            }
        }
    }

    // ########################################### \\
    // ############# Input Handling ############## \\
    // ########################################### \\

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E) {
            if (inventoryIsOpen) {
                inventoryIsOpen = false;
            } else {
                inventoryIsOpen = true;
            }
        }
    }
}
