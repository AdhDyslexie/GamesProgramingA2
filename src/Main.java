// 

// import java.awt.RenderingHints.Key;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Point;

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

    public void init() {
        mWidth = 500;
        mHeight = 500;

        setWindowSize(mWidth, mHeight);

        items = new AllItemDefinitions();

        player = new Player();
        
        Image tempimage = loadImage("tileset.png");
        npc = new Npc(150d, 300d, 30, 50, tempimage, items.getDefinitionAtIndex(0), 0);

        floorItems = new ItemInstance[5];
        dayCycle = new DayCycle();

        player.setActionsRemaining(dayCycle.ActionsPerDay());

        map = new Map();
    }

    @Override
    public void update(double dt) {
        switch (map.mapToRender) {
            case MARKET:
            // Check for collisions with the edges of the map
            if (player.collider.IsColliding(map.marketTopCollider)) {
                player.up = false;
            }
            if (player.collider.IsColliding(map.marketBottomCollider)) {
                player.down = false;
            }
            if (player.collider.IsColliding(map.marketLeftCollider)) {
                player.left = false;
            }
            if (player.collider.IsColliding(map.marketRightCollider)) {
                player.right = false;
            }

            // Check for collisions with the left house
            if (player.collider.IsColliding(map.leftHouseCollider)) {
                // If player is within x bounds of house, they should not be able to move up
                if (player.x + player.collider.Radius() / 2 > map.leftHouseCollider.LeftX() && player.x - player.collider.Radius() / 2 < map.leftHouseCollider.RightX()) {
                    player.up = false;
                } else if (player.x - player.collider.Radius() < map.leftHouseCollider.LeftX()) {
                    player.right = false;
                } else if (player.x + player.collider.Radius() > map.leftHouseCollider.RightX()) {
                    player.left = false;
                }
            }

            // Check for collisions with the right house
            if (player.collider.IsColliding(map.rightHouseCollider)) {
                // If player is within x bounds of house, they should not be able to move up
                if (player.x + player.collider.Radius() / 2 > map.rightHouseCollider.LeftX() && player.x - player.collider.Radius() / 2 < map.rightHouseCollider.RightX()) {
                    player.up = false;
                } else if (player.x - player.collider.Radius() < map.rightHouseCollider.LeftX()) {
                    player.right = false;
                } else if (player.x + player.collider.Radius() > map.rightHouseCollider.RightX()) {
                    player.left = false;
                }
            }
            break;

            case HOME:
            if (player.collider.IsColliding(map.homeTopCollider)) {
                player.up = false;
            }
            if (player.collider.IsColliding(map.homeBottomCollider)) {
                player.down = false;
            }
            if (player.collider.IsColliding(map.homeLeftCollider)) {
                player.left = false;
            }
            if (player.collider.IsColliding(map.homeRightCollider)) {
                player.right = false;
            }
            break;
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
            case BUYBUTTON:
                winbutton();
                break;
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

        drawText(20, 30, "Money: " + player.money, "Arial", 20);
        drawText(20, 60, "Actions remaining: " + player.ActionsRemaining(), "Arial", 20);
        drawText(20, 90, "Days remaining: " + dayCycle.DaysRemaining(), "Arial", 20);

        if (map.mapToRender == Map.MapToRender.HOME) {
            changeColor(100, 100, 100);
            drawText(70, 470, "Press 'N' to go to market, with 5 items in your inventory", "Arial", 15);
        }
        if (player.winmode != 0) {
            changeColor(black);
            drawSolidRectangle(0,0,500,500);
            changeColor(white);
            if (player.winmode == 101) {
                drawText(135, 200, "You Win!");
            }
            if (player.winmode < 101) {
                player.money -= 1;
                player.winmode += 1;
            }
            drawText(130,250,"Money: " + player.money);
        }
    }

    // ########################################### \\
    // ############# Draw methods ################ \\
    // ########################################### \\
    public void drawNpc() {
        if (map.mapToRender == Map.MapToRender.MARKET) {
            saveCurrentTransform();

        translate(npc.X() - npc.Width() / 2, npc.Y() - npc.Height());
        drawImage(npc.npcAnimate(player.animframe, 0/*npc.Type()*/).Image(), 0, 0,30,50);

            restoreLastTransform();
        }
    }

    public void drawPlayer() {
        saveCurrentTransform();
        translate(player.x - player.width / 2, player.y - player.height);
        
        drawImage(player.animate().Image(),0,0,player.width,player.height);

        // restoreLastTransform();
        // translate(player.x, player.y);
        // drawLine(0, 0, 0, 1, 5);

        // restoreLastTransform();
        // if (floorItems[0] != null) {
        //     translate(floorItems[0].xPos(), floorItems[0].yPos());
        //     drawLine(0, 0, 0, 1, 5);
        // }

        // Draw player's collider -- for debugging purposes
        // restoreLastTransform();
        // translate(player.collider.X(), player.collider.Y());
        // drawCircle(0, 0, player.collider.Radius());

        // Draw a box collider -- for debugging purposes
        // restoreLastTransform();
        // translate(map.rightHouseCollider.LeftX(), map.rightHouseCollider.TopY());
        // drawRectangle(0, 0, map.rightHouseCollider.RightX() - map.rightHouseCollider.LeftX(), map.rightHouseCollider.BottomY() - map.rightHouseCollider.TopY());

        restoreLastTransform();
    }

    public void drawFloorItems() {
        if (map.mapToRender == Map.MapToRender.MARKET) {
            saveCurrentTransform();

            for (int i = 0; i < floorItems.length; i++) {
                if (floorItems[i] != null) {
                    translate(floorItems[i].xPos() - 16, floorItems[i].yPos() - 16);
                    drawImage(floorItems[i].Image(), 0, 0);
                    restoreLastTransform();
                }
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
        if (player.menuOpen == Player.MenuOpen.INVENTORY) {
            hover(0);
        } else {
            hover(150);
        }
        restoreLastTransform();
    }
    public void hover (int l) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        double my = b.getY()-l;
        double mx = b.getX() - 210;
        for (int xCheck = 70 + (player.inventory.renderingBufferSize() * player.inventory.SizeMultiplier()), i = 0;
             i < player.inventory.maxSize();
             xCheck += (map.MarketMap()[0].TileWidth() + player.inventory.renderingBufferSize()) * player.inventory.SizeMultiplier(), i++) {
            if(!(player.tradingMenu.SlotTakenItemIndex() == i)) {
                if (mx > xCheck && mx < xCheck + map.MarketMap()[0].TileWidth() * player.inventory.SizeMultiplier()) {
                    // This item was hovered
                    if (my > 390 && my < 450) {
                        if (player.inventory.getItemAtIndex(i) != null) {
                            drawSolidRectangle(mx - 67, my - 482, 200, 100);
                            changeColor(black);
                            drawText(mx - 67, my - 462, "Name: " + player.inventory.getItemAtIndex(i).Name(), "Arial", 14);
                            int lines = 0;
                            if (player.inventory.getItemAtIndex(i).Description().length() < 40) {
                                drawText(mx - 67, my - 442, "Desc: " + player.inventory.getItemAtIndex(i).Description(), "Arial", 10);
                            } else {
                                String[] arrayStr = player.inventory.getItemAtIndex(i).Description().split("]", 3);
                                drawText(mx - 67, my - 442, "Desc: ", "Arial", 10);
                                for (String s : arrayStr) {
                                    drawText(mx - 37, my - 442 + (lines * 15), s, "Arial", 10);
                                    lines++;
                                }
                                lines--;
                            }
                            drawText(mx - 67, my - 422 + (lines * 15), "Value: " + player.inventory.getItemAtIndex(i).Value(), "Arial", 14);
                            changeColor(white);
                            break;
                        }
                    }
                }
            }
        }
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
        for (int i = 0; i < map.currentMapRenderLayers(); i++) {
            // For each tile in this layer
            if (i == player.renderLayer - 1) {
                drawPlayer();
                nonTilemapLayersRendered++;
            } else {
                for (int j = 0; j < map.CurrentMap()[i - nonTilemapLayersRendered].tileMap.length; j++) {
                    // Draw this tile at its tile-based location * the tile width
                    drawImage(  map.CurrentMap()[i - nonTilemapLayersRendered].tileMap[j].tile.image,
                                map.CurrentMap()[i - nonTilemapLayersRendered].tileMap[j].x * map.CurrentMap()[i - nonTilemapLayersRendered].TileWidth(),
                                map.CurrentMap()[i - nonTilemapLayersRendered].tileMap[j].y * map.CurrentMap()[i - nonTilemapLayersRendered].TileHeight());
                }
            }
        }
    }

    public void winbutton() {
        changeColor(white);
        drawSolidRectangle(200,300,200,50);
        changeColor(black);
        drawText(220,330,"Buy store? $100","Arial",20);

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
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (player.menuOpen == Player.MenuOpen.BUYBUTTON) {
                player.menuOpen = Player.MenuOpen.NONE;
            } else if (player.menuOpen == Player.MenuOpen.NONE){
                player.menuOpen = Player.MenuOpen.BUYBUTTON;
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
                        playAudio(player.tradingMenu.ButtonSound(), .05f);
                        player.tradingMenu.setButtonColor(java.awt.Color.GRAY);
                        player.money += player.inventory.getItemAtIndex(player.tradingMenu.SlotTakenItemIndex()).Value() * npc.Multiplier();
                        player.decrimentActionsRemaining();
                        player.inventory.removeItemAtIndex(player.tradingMenu.SlotTakenItemIndex());
                        player.tradingMenu.setSlotTaken(false);
                        npc.updateMultiplier(null);
                        if (player.ActionsRemaining() <= 0) {
                            player.setActionsRemaining(dayCycle.NewDay());
                        }
                    }
                }
            }
        }
        if (player.menuOpen == Player.MenuOpen.BUYBUTTON) {
            if (e.getY() > 300 && e.getY() < 350 && e.getX() > 200 && e.getX() < 400) {
                if (player.money > 99) {        // win mode
                    player.winmode = 1;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (player.tradingMenu.ButtonColor() == java.awt.Color.GRAY) {
            player.tradingMenu.setButtonColor(black);
        }
    }

    public void NewDay() {
        player.setActionsRemaining(dayCycle.NewDay());
        player.x = 300;
        player.y = 300;

        // call daycycle new day
        // move player
        // spawn random items

        for (int i = 0; i < floorItems.length; i++) {
            //floorItems[i] = new ItemInstance(30, (i * 10) + 50, false, rand(1));
        }
    }
}