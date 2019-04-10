package inf112.roborally.game.server;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientInput {

    private BufferedReader input;

    public ClientInput(BufferedReader inn){
        this.input = inn;
    }

    public String readLine(){
        try {
            return input.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
