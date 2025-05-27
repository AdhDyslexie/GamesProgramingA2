import java.awt.RenderingHints.Key;
import java.awt.event.*;

public class Main extends GameEngine{

    public static void main(String[] args) {

        int framerate = 60;
        createGame(new Main(), framerate);
    }

    int renderingLayers;

    ItemInstance floorItems[];
    AllItemDefinitions items;

    Player player;
    Map map[];
    Npc npc;

    public void init() {
        mWidth = 500;
        mHeight = 500;

        setWindowSize(mWidth, mHeight);

        renderingLayers = 3;
        items = new AllItemDefinitions();

        player = new Player();
        map = new Map[renderingLayers - 1];
        npc = new Npc();

        map[0] = new Map(new int[][]{{0, 0, 1}, {0, 1, 1}, {0, 2, 1}, {1, 1, 0}, {1, 2, 0}});
        map[1] = new Map(new int[][]{{1, 2, 1}});

        floorItems = new ItemInstance[2];
        floorItems[0] = new ItemInstance(20, 30, false, items.getDefinitionAtIndex(0));
        floorItems[1] = new ItemInstance(50, 70, false, items.getDefinitionAtIndex(1));
    }

    @Override
    public void update(double dt) {
        player.update();
    }

    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
		clearBackground(mWidth, mHeight);

        renderLayers();
        drawFloorItems();

        if (player.menuOpen) {
            drawInventory();
        }
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

    public void drawFloorItems() {
        saveCurrentTransform();

        for (int i = 0; i < items.numberOfItems(); i++) {
            if (floorItems[i] != null) {
                translate(floorItems[i].xPos(), floorItems[i].yPos());
                drawImage(floorItems[i].Image(), 0, 0);
            }
        }
        // something in here is wrong and causing items to draw differently before and after picking an item up

        restoreLastTransform();
    }

    public void drawInventory() {
        saveCurrentTransform();

        // Inventory background
        changeColor(white);
        translate(70, 150);
        drawSolidRectangle(0, 0,
                            (map[0].getTileWidth() * player.inventory.maxSize() + player.inventory.renderingBufferSize() * (player.inventory.maxSize() + 1)) * 2,
                            (map[0].getTileHeight() + player.inventory.renderingBufferSize() * 2) * 2);

        for (int i = 0; i < player.inventory.maxSize(); i++) {
            // buffer(4) * i + 1 gets the correct buffer for x, + buffer for y
            if (player.inventory.getItemAtIndex(i) != null) {
                drawImage(  player.inventory.getItemAtIndex(i).Image(),
                            ((map[0].getTileWidth() * i) + (player.inventory.renderingBufferSize() * i + player.inventory.renderingBufferSize())) * 2,
                            (player.inventory.renderingBufferSize()) * 2,
                            map[0].getTileWidth() * 2,
                            map[0].getTileHeight() * 2
                            );
            }
        }

        restoreLastTransform();
    }

    public void renderLayers() {
        int nonTilemapLayersRendered = 0;

        // For each layer
        for (int i = 0; i < renderingLayers; i++) {
            // For each tile in this layer
            if (i == player.renderLayer - 1) {
                drawPlayer();
                nonTilemapLayersRendered++;
            } else {
                for (int j = 0; j < map[i - nonTilemapLayersRendered].tileMap.length; j++) {
                    // Draw this tile at its tile-based location * the tile width
                    drawImage(  map[i - nonTilemapLayersRendered].tileMap[j].tile.image,
                                map[i - nonTilemapLayersRendered].tileMap[j].x * map[i - nonTilemapLayersRendered].getTileWidth(),
                                map[i - nonTilemapLayersRendered].tileMap[j].y * map[i - nonTilemapLayersRendered].getTileHeight());
                }
            }
        }
    }

    // ########################################### \\
    // ############# Input Handling ############## \\
    // ########################################### \\

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E) {
            if (player.menuOpen) {
                player.menuOpen = false;
            } else {
                player.menuOpen = true;
                player.IsMoving = false;
            }
        }
        if (player.menuOpen) {
            // keyPressedInventory(e);
        } else {
            keyPressedWorld(e);
        }

        if (e.getKeyCode() == KeyEvent.VK_F) {
            for (int i = 0; i < floorItems.length; i++) {
                if (floorItems[i] != null) {
                    if (distance(floorItems[i].xPos(), floorItems[i].yPos(), player.x, player.y) < 80) {
                        if (player.inventory.addItem(floorItems[i])) {
                            floorItems[i] = null;
                        }
                        break;
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W && player.direction == Player.Direction.UP) {
            player.IsMoving = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_A && player.direction == Player.Direction.LEFT) {
            player.IsMoving = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_S && player.direction == Player.Direction.DOWN) {
            player.IsMoving = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_D && player.direction == Player.Direction.RIGHT) {
            player.IsMoving = false;
        }
    }

    public void keyPressedWorld(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.direction = Player.Direction.UP;
            player.IsMoving = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.direction = Player.Direction.LEFT;
            player.IsMoving = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) { 
            player.direction = Player.Direction.DOWN;
            player.IsMoving = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            player.direction = Player.Direction.RIGHT;
            player.IsMoving = true;
        }
    }
}