// import java.awt.RenderingHints.Key;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.awt.Image;

public class Main extends GameEngine{

    public static void main(String[] args) {

        int framerate = 60;
        createGame(new Main(), framerate);
    }

    ItemInstance floorItems[];
    AllItemDefinitions items;

    Player player;
    Map map;
    Npc npc;
    DayCycle dayCycle;

    AudioClip testClip;

    public void init() {
        // So. I tried really hard to get sound in. I did a lot of debugging. I tried like 5 different methods. I spent hours on this.
        // I unfortunately was not able to get sounds playing consistently from anywhere but init(). Sometimes they would play from update(),
        // but sometimes they would be so quiet you could barely hear them and sometimes they would be a normal volume, with seemingly no
        // reason. I tried to use the function which takes a volume float but that always gave me an error message saying it was unable
        // to play the sound. Sounds refused to play from anywhere but init() and update(), and I have no clue why. - Callum
        testClip = loadAudio("ButtonSound.wav");
        mWidth = 500;
        mHeight = 500;

        setWindowSize(mWidth, mHeight);

        items = new AllItemDefinitions();

        player = new Player();
        
        Image tempimage = loadImage("tileset.png");
        npc = new Npc(150, 150, 30, 50, tempimage, items.getDefinitionAtIndex(0));

        floorItems = new ItemInstance[2];
        floorItems[0] = new ItemInstance(20, 30, false, items.getDefinitionAtIndex(0));
        floorItems[1] = new ItemInstance(50, 70, false, items.getDefinitionAtIndex(1));
        dayCycle = new DayCycle();

        player.setActionsRemaining(dayCycle.ActionsPerDay());

        map = new Map();
    }

    @Override
    public void update(double dt) {
        if (player.collider.IsColliding(map.backCollider.LeftX(), map.backCollider.RightX(), map.backCollider.TopY(), map.backCollider.BottomY())) {
            player.up = false;
            // System.out.println("Colliding with building!");
        }
        player.update();
    }

    @Override
    public void paintComponent() {
        changeBackgroundColor(new java.awt.Color(98, 196, 245));
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
        drawText(50, 110, "Days remaining: " + dayCycle.DaysRemaining());
    }

    // ########################################### \\
    // ############# Draw methods ################ \\
    // ########################################### \\
    public void drawNpc() {
        saveCurrentTransform();

        translate(npc.X() - npc.Width() / 2, npc.Y() - npc.Height());
        drawImage(npc.Sprite(), 0, 0);

        restoreLastTransform();
    }

    public void drawPlayer() {
        // Draw the player at the current position
        switch (player.direction) {
            case LEFT:
                // Draw left idle/animation!
                changeColor(green);
                break;

            case RIGHT:
                // Draw right idle/animation!
                changeColor(blue);
                break;

            case UP:
                // Draw up idle/animation!
                changeColor(red);
                break;

            case DOWN:
                // Draw down idle/animation!
                changeColor(orange);
                break;
        }
        
        saveCurrentTransform();
        translate(player.x - player.width / 2, player.y - player.height);
        
        if (player.IsMoving) {
            drawRectangle(0, 0, player.width, player.height, 5);
        } else {
            drawRectangle(0, 0, player.width, player.height);
        }
        
        restoreLastTransform();

        // Draw collider -- for debugging purposes
        // translate(player.collider.X(), player.collider.Y());
        // drawCircle(0, 0, player.collider.Radius());

        restoreLastTransform();
        translate(map.backCollider.LeftX(), map.backCollider.TopY());
        drawRectangle(0, 0, map.backCollider.RightX() - map.backCollider.LeftX(), map.backCollider.BottomY() - map.backCollider.TopY());

        restoreLastTransform();
    }

    public void drawFloorItems() {
        saveCurrentTransform();

        for (int i = 0; i < floorItems.length; i++) {
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
                            (map.MarketMap()[0].TileWidth() * player.inventory.maxSize() + player.inventory.renderingBufferSize() * (player.inventory.maxSize() + 1)) * 2,
                            (map.MarketMap()[0].TileHeight() + (player.inventory.renderingBufferSize() * 2)) * 2);

        // Draw each inventory item in their correct spot
        for (int i = 0; i < player.inventory.maxSize(); i++) {
            // If there's an item in this slot, draw it
            if (player.inventory.getItemAtIndex(i) != null) {
                if (player.inventory.getItemAtIndex(i).IsInInventory()) {
                    drawImage(  player.inventory.getItemAtIndex(i).Image(),
                            ((map.MarketMap()[0].TileWidth() * i) + (player.inventory.renderingBufferSize() * i + player.inventory.renderingBufferSize())) * player.inventory.SizeMultiplier(),
                            (player.inventory.renderingBufferSize()) * player.inventory.SizeMultiplier(),
                            map.MarketMap()[0].TileWidth() * player.inventory.SizeMultiplier(),
                            map.MarketMap()[0].TileHeight() * player.inventory.SizeMultiplier()
                            );
                } else {
                    drawImage(player.inventory.getItemAtIndex(i).Image(),
                            player.inventory.getItemAtIndex(i).xPos() - x,
                            player.inventory.getItemAtIndex(i).yPos() - y,
                            map.MarketMap()[0].TileWidth() * player.inventory.SizeMultiplier(),
                            map.MarketMap()[0].TileHeight() * player.inventory.SizeMultiplier());
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
        
        // Draw item value text, if theres something in the slot
        if (player.tradingMenu.SlotTaken()) {
            changeColor(black);
            translate(-30, -32);
            translate(2, 70);
            drawText(0, 0, (int)(player.inventory.getItemAtIndex(player.tradingMenu.SlotTakenItemIndex()).Value() * npc.Multiplier()) + " Coins", "Arial", 30);
        }

        // Draw multiplier text, if not normal (1)
        if (npc.Multiplier() != 1) {
            changeColor(orange);
            translate(0, 22);
            BigDecimal bd = new BigDecimal(npc.Multiplier());
            bd = bd.round(new MathContext(3));
            double rounded = bd.doubleValue();
            drawText(0, 0, "x " + rounded + "!", "Arial", 20);
        }

        restoreLastTransform();
    }

    public void renderLayers() {
        int nonTilemapLayersRendered = 0;

        // For each layer
        for (int i = 0; i < map.MarketRenderLayers(); i++) {
            // For each tile in this layer
            if (i == player.renderLayer - 1) {
                drawPlayer();
                nonTilemapLayersRendered++;
            } else {
                for (int j = 0; j < map.MarketMap()[i - nonTilemapLayersRendered].tileMap.length; j++) {
                    // Draw this tile at its tile-based location * the tile width
                    drawImage(  map.MarketMap()[i - nonTilemapLayersRendered].tileMap[j].tile.image,
                                map.MarketMap()[i - nonTilemapLayersRendered].tileMap[j].x * map.MarketMap()[i - nonTilemapLayersRendered].TileWidth(),
                                map.MarketMap()[i - nonTilemapLayersRendered].tileMap[j].y * map.MarketMap()[i - nonTilemapLayersRendered].TileHeight());
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
                    if (distance(npc.X(), npc.Y(), player.x, player.y) < player.reach) {
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
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        }
    }

    public void keyPressedWorld(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        }
    }

    // ------------------------------------------------------------- MOUSE --------------------------------------------------------------
    public void mousePressed(MouseEvent e) {
        // Check for clicks while the trading menu is open - at the trading menu button or slot, or in the inventory
        // Most of this could be done in TradingMenu in their own functions. I'll change it to do that if I have time. - Callum
        if (player.menuOpen == Player.MenuOpen.TRADING) {
            // Check whether to check for clicks in the inventory or in the trading menu
            if (player.tradingMenu.SlotTaken() == false) {
                // check whether the mouse was clicked in an item's area
                if (e.getY() > 308 && e.getY() < 372) {
                    // 70 + (buffer * slotwe'reon + buffer) * mult
                    for (   int xCheck = 70 + (player.inventory.renderingBufferSize() * player.inventory.SizeMultiplier()), i = 0;
                            i < player.inventory.maxSize();
                            xCheck += (map.MarketMap()[0].TileWidth() + player.inventory.renderingBufferSize()) * player.inventory.SizeMultiplier(), i++) {
                        if (e.getX() > xCheck && e.getX() < xCheck + map.MarketMap()[0].TileWidth() * player.inventory.SizeMultiplier()) {
                            // This item was clicked! put it in the trading menu slot
                            if (player.inventory.getItemAtIndex(i) != null) {
                                player.inventory.getItemAtIndex(i).setIsInInventory(false);
                                player.inventory.getItemAtIndex(i).setXPos(player.tradingMenu.SlotLeftWorldX() + (player.inventory.renderingBufferSize() * player.inventory.SizeMultiplier()));
                                player.inventory.getItemAtIndex(i).setYPos(player.tradingMenu.SlotTopWorldY() + (player.inventory.renderingBufferSize() * player.inventory.SizeMultiplier()));
                                player.tradingMenu.setSlotTaken(true);
                                player.tradingMenu.setSlotTakenIndex(i);
                                npc.updateMultiplier(player.inventory.getItemAtIndex(i).Info());
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
                        // Something is in the slot! return it
                        player.ReturnItemFromTradingMenu();
                        npc.updateMultiplier(null);
                    }
                } else if (e.getX() > player.tradingMenu.ButtonLeftWorldX() && e.getX() < player.tradingMenu.ButtonRightWorldX()) {
                    if (e.getY() > player.tradingMenu.ButtonTopWorldY() && e.getY() < player.tradingMenu.ButtonBottomWorldY()) {
                        // Trading something! Increase money, remove item from inventory, decrease actions, set slot to empty
                        player.tradingMenu.setButtonColor(java.awt.Color.GRAY);
                        player.money += player.inventory.getItemAtIndex(player.tradingMenu.SlotTakenItemIndex()).Value() * npc.Multiplier();
                        player.decrimentActionsRemaining();
                        player.inventory.removeItemAtIndex(player.tradingMenu.SlotTakenItemIndex());
                        player.tradingMenu.setSlotTaken(false);
                        npc.updateMultiplier(null);
                        // playAudio(testClip); // Never plays... but this is where this code would be if it were working.
                        if (player.ActionsRemaining() <= 0) {
                            player.setActionsRemaining(dayCycle.NewDay());
                        }
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