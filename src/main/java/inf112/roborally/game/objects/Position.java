package inf112.roborally.game.objects;

/**
 * Keep track of the positions for a game object
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    public void moveX(int x) { this.x = x; }

    public void moveY(int y) { this.y = y; }

}
