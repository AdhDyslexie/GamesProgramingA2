public class Main extends GameEngine{
    public static void main(String[] args) {

        int framerate = 60;
        createGame(new Main(), framerate);
    }

    Player player;
    Map map;
    Npc npc;

    public void init(){
        mWidth = 500;
        mHeight = 500;

        setWindowSize(mWidth, mHeight);

        player = new Player();
        map = new Map();
        npc = new Npc();

        
    }

    @Override
    public void update(double dt) {
        
    }

    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
		clearBackground(mWidth, mHeight);

        drawPlayer();
    }

    // ########################################### \\
    // ############# Draw methods ################ \\
    // ########################################### \\

    public void drawPlayer() {
        // Draw the player at the current position
        changeColor(red);
        saveCurrentTransform();
        translate(player.x, player.y);
        
        drawRectangle(player.x, player.y, player.width, player.height);
        restoreLastTransform();
    }

   
}
