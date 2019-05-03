package inf112.roborally.game.objects;

import org.jetbrains.annotations.NotNull;

public class StartPosition implements Comparable {

    public Position position;
    protected Integer startNumber;

    public StartPosition(int x, int y, int startNumber) {
        this.startNumber = startNumber;
        position = new Position(x, y);
    }

    @Override
    public int compareTo(@NotNull Object o) {
        StartPosition other = (StartPosition) o;
        return this.startNumber.compareTo(other.startNumber);
    }

}
