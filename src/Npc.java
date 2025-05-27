import java.awt.Image;

public class Npc {
    private double x;
    private double y;

    private double mood;

    private int width;
    private int height;

    private Image sprite;

    Npc() {
        x = 0;
        y = 0;
        mood = 5;
        width = 30;
        height = 50;
        sprite = null;
    }

    Npc(double xNew, double yNew, double moodNew, int wid, int hei, Image spri) {
        x = xNew;
        y = yNew;
        mood = moodNew;
        width = wid;
        height = hei;
        sprite = spri;
    }

    // ------------------------------------------------- GET METHODS -----------------------------------------------------
    public double xPos() {
        return x;
    }

    public double yPos() {
        return y;
    }

    public double Mood() {
        return mood;
    }

    public int Width() {
        return width;
    }

    public int Height() {
        return height;
    }

    public Image Sprite() {
        return sprite;
    }

    // ------------------------------------------------- SET METHODS -----------------------------------------------------
    public void setMood(double newMood) {
        mood = newMood;
    }

    public void setSprite(Image newSprite) {
        sprite = newSprite;
    }
}