// import java.awt.RenderingHints.Key;
import java.awt.event.*;
import java.awt.Image;

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
    DayCycle dayCycle;

    public void init() {
        mWidth = 500;
        mHeight = 500;

        setWindowSize(mWidth, mHeight);

        renderingLayers = 3;
        items = new AllItemDefinitions();

        player = new Player();
        map = new Map[renderingLayers - 1];
        Image tempimage = loadImage("tileset.png");
        npc = new Npc(150, 150, 5, 30, 50, tempimage);

        map[0] = new Map(new int[][]{{0, 0, 1}, {0, 1, 1}, {0, 2, 1}, {1, 1, 0}, {1, 2, 0}});
        map[1] = new Map(new int[][]{{1, 2, 1}});

        floorItems = new ItemInstance[2];
        floorItems[0] = new ItemInstance(20, 30, false, items.getDefinitionAtIndex(0));
        floorItems[1] = new ItemInstance(50, 70, false, items.getDefinitionAtIndex(1));
        dayCycle = new DayCycle();

        player.setActionsRemaining(dayCycle.ActionsPerDay());
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
        drawNpc();

        switch (player.menuOpen) {
            case INVENTORY:
                drawInventory(70, 150);
                break;

            case TRADING:
                drawTradingMenu();
                drawInventory(70, 300);
                break;

            case NONE:
                break;
        }

        drawText(50, 50, "Money: " + player.money);
        drawText(50, 80, "Actions remaining: " + player.ActionsRemaining());
    }

    // ########################################### \\
    // ############# Draw methods ################ \\
    // ########################################### \\
    public void drawNpc() {
        saveCurrentTransform();

        translate(npc.xPos(), npc.yPos());
        drawImage(npc.Sprite(), 0, 0);

        restoreLastTransform();
    }

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
                restoreLastTransform();
            }
        }
    }

    public void drawInventory(int x, int y) {
        saveCurrentTransform();

        // Inventory background
        changeColor(white);
        translate(x, y);
        drawSolidRectangle(0, 0,
                            (map[0].getTileWidth() * player.inventory.maxSize() + player.inventory.renderingBufferSize() * (player.inventory.maxSize() + 1)) * 2,
                            (map[0].getTileHeight() + (player.inventory.renderingBufferSize() * 2)) * 2);

        // Draw each inventory item in their correct spot
        for (int i = 0; i < player.inventory.maxSize(); i++) {
            // If there's an item in this slot, draw it
            if (player.inventory.getItemAtIndex(i) != null) {
                if (player.inventory.getItemAtIndex(i).IsInInventory()) {
                    drawImage(  player.inventory.getItemAtIndex(i).Image(),
                            ((map[0].getTileWidth() * i) + (player.inventory.renderingBufferSize() * i + player.inventory.renderingBufferSize())) * player.inventory.SizeMultiplier(),
                            (player.inventory.renderingBufferSize()) * player.inventory.SizeMultiplier(),
                            map[0].getTileWidth() * player.inventory.SizeMultiplier(),
                            map[0].getTileHeight() * player.inventory.SizeMultiplier()
                            );
                } else {
                    drawImage(player.inventory.getItemAtIndex(i).Image(),
                            player.inventory.getItemAtIndex(i).xPos() - x,
                            player.inventory.getItemAtIndex(i).yPos() - y,
                            map[0].getTileWidth() * player.inventory.SizeMultiplier(),
                            map[0].getTileHeight() * player.inventory.SizeMultiplier());
                }
            }
        }

        restoreLastTransform();
    }

    public void drawTradingMenu() {
        saveCurrentTransform();

        // Draw menu background
        changeColor(white);
        translate(player.tradingMenu.X(), player.tradingMenu.Y());
        drawSolidRectangle(0, 0, player.tradingMenu.Width(), player.tradingMenu.Height());

        // Draw slot
        changeColor(black);
        translate(player.tradingMenu.SlotX(), player.tradingMenu.SlotY());
        drawSolidRectangle(0, 0, player.tradingMenu.SlotWidth(), player.tradingMenu.SlotHeight());

        // Draw button
        // (restore last transform, translate to button world coords, draw button at 0,0)
        restoreLastTransform();
        changeColor(player.tradingMenu.ButtonColor());
        translate(player.tradingMenu.ButtonLeftWorldX(), player.tradingMenu.ButtonTopWorldY());
        drawSolidRectangle(0, 0, player.tradingMenu.ButtonWidth(), player.tradingMenu.ButtonHeight());

        // Draw trading button text
        changeColor(white);
        translate(30, 32);
        drawText(0, 0, "Trade", "Arial", 30);
        
        // Draw item value text
        if (player.tradingMenu.SlotTaken()) {
            changeColor(black);
            translate(-30, -32);
            translate(2, 70);
            drawText(0, 0, player.inventory.getItemAtIndex(player.tradingMenu.SlotTakenItemIndex()).Value() + " Coins", "Arial", 30);
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


    // ------------------------------------------------------------- KEYBOARD ------------------------------------------------------------
    public void keyPressed(KeyEvent e) {
        // Toggle whether the inventory is open
        if (e.getKeyCode() == KeyEvent.VK_E) {
            if (player.menuOpen == Player.MenuOpen.INVENTORY) {
                player.menuOpen = Player.MenuOpen.NONE;
            } else if (player.menuOpen == Player.MenuOpen.NONE){
                player.menuOpen = Player.MenuOpen.INVENTORY;
                player.IsMoving = false;
            }
        }

        // Do world stuff
        if (player.menuOpen == Player.MenuOpen.NONE) {
            keyPressedWorld(e);
        }

        // Interact with things
        if (e.getKeyCode() == KeyEvent.VK_F) {
            if (player.menuOpen == Player.MenuOpen.NONE) {
                for (int i = 0; i < floorItems.length; i++) {
                    if (floorItems[i] != null) {
                        if (distance(floorItems[i].xPos(), floorItems[i].yPos(), player.x, player.y) < player.reach) {
                            if (player.inventory.addItem(floorItems[i])) {
                                floorItems[i] = null;
                            }
                            break;
                        }
                    }
                }
                for (int i = 0; i < 1; i++) {
                    if (distance(npc.xPos(), npc.yPos(), player.x, player.y) < player.reach) {
                        player.menuOpen = Player.MenuOpen.TRADING;
                    }
                }
            } else if (player.menuOpen == Player.MenuOpen.TRADING) {
                player.menuOpen = Player.MenuOpen.NONE;
                player.ReturnItemFromTradingMenu();
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

    // ------------------------------------------------------------- MOUSE --------------------------------------------------------------
    public void mousePressed(MouseEvent e) {
        // Check for clicks while the trading menu is open - at the trading menu button or slot, or in the inventory
        if (player.menuOpen == Player.MenuOpen.TRADING) {
            // Check whether to check for clicks in the inventory or in the trading menu
            if (player.tradingMenu.SlotTaken() == false) {
                // check whether the mouse was clicked in an item's area
                if (e.getY() > 308 && e.getY() < 372) {
                    // 70 + (buffer * slotwe'reon + buffer) * mult
                    for (   int xCheck = 70 + (player.inventory.renderingBufferSize() * player.inventory.SizeMultiplier()), i = 0;
                            i < player.inventory.maxSize();
                            xCheck += (map[0].getTileWidth() + player.inventory.renderingBufferSize()) * player.inventory.SizeMultiplier(), i++) {
                        if (e.getX() > xCheck && e.getX() < xCheck + map[0].getTileWidth() * player.inventory.SizeMultiplier()) {
                            if (player.inventory.getItemAtIndex(i) != null) {
                                player.inventory.getItemAtIndex(i).setIsInInventory(false);
                                player.inventory.getItemAtIndex(i).setXPos(player.tradingMenu.SlotLeftWorldX() + (player.inventory.renderingBufferSize() * player.inventory.SizeMultiplier()));
                                player.inventory.getItemAtIndex(i).setYPos(player.tradingMenu.SlotTopWorldY() + (player.inventory.renderingBufferSize() * player.inventory.SizeMultiplier()));
                                player.tradingMenu.setSlotTaken(true);
                                player.tradingMenu.setSlotTakenIndex(i);
                                break;
                            }
                        }
                    }
                }
            // If there's something in the slot, we should check for clicks in the slot or trade button
            } else if (player.tradingMenu.SlotTaken()) {
                // check for clicks in the trading slot, to put items back in inventory
                if (e.getX() > player.tradingMenu.SlotLeftWorldX() && e.getX() < player.tradingMenu.SlotRightWorldX()) {
                    if (e.getY() > player.tradingMenu.SlotTopWorldY() && e.getY() < player.tradingMenu.SlotBottomWorldY()) {
                        player.ReturnItemFromTradingMenu();
                    }
                } else if (e.getX() > player.tradingMenu.ButtonLeftWorldX() && e.getX() < player.tradingMenu.ButtonRightWorldX()) {
                    if (e.getY() > player.tradingMenu.ButtonTopWorldY() && e.getY() < player.tradingMenu.ButtonBottomWorldY()) {
                        // Trading something! Increase money, remove item from inventory, decrease actions, set slot to empty
                        player.tradingMenu.setButtonColor(java.awt.Color.GRAY);
                        player.money += player.inventory.getItemAtIndex(player.tradingMenu.SlotTakenItemIndex()).Value();
                        player.decrimentActionsRemaining();
                        player.inventory.removeItemAtIndex(player.tradingMenu.SlotTakenItemIndex());
                        player.tradingMenu.setSlotTaken(false);
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (player.tradingMenu.ButtonColor() == java.awt.Color.GRAY) {
            player.tradingMenu.setButtonColor(black);
        }
    }
}