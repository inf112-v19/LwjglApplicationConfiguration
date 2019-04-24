package inf112.roborally.game.server;

public class Payload <T>  {
    private T value;

    public Payload(T value){
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
