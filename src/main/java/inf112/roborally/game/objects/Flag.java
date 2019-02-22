package inf112.roborally.game.objects;

/**
 * Class for a flag on the board, which extends AbstractGameObject
 */
public class Flag extends AbstractGameObject {
    private int flagNumber;
    private String filepath = "assets/gameboard/flag.png";

    public Flag(int x, int y, int flagNumber) {
        super(x, y);
        this.flagNumber = flagNumber;
    }

    public int getFlagNumber(){
        return this.flagNumber;
    }

    public String getFilename() {
        return filepath;
    }

}
