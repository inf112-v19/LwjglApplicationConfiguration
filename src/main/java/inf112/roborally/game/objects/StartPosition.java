package inf112.roborally.game.objects;

import org.jetbrains.annotations.NotNull;

public class StartPosition extends Position implements Comparable{

    public Integer startNumber;

    public StartPosition(int x, int y, int startNumber){
        super(x,y);
        this.startNumber = startNumber;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        StartPosition other = (StartPosition) o;
        return this.startNumber.compareTo(other.startNumber);
    }

}
