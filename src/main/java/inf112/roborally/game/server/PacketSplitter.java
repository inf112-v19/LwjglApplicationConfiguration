package inf112.roborally.game.server;

import inf112.roborally.game.player.ProgramCard;

public class PacketSplitter {

    public static String keyWord(String msg) {
        return msg.split(" ")[0];
    }

    public static String[] payload(String msg) {
        try {
            String payload = msg.split(" ", 2)[1];
            return payload.split(" ");
        }
        catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("msg does not contain payload");
        }
    }

    public static ProgramCard programCard(String msg) {
        if (!keyWord(msg).equals("CARD"))
            throw new IllegalArgumentException("msg is not a card message");

        if (payload(msg).length != 4)
            throw new IndexOutOfBoundsException();

        String[] payload = payload(msg);
        return new ProgramCard(payload[1], payload[2], payload[3]);
    }

    public static int nCards(String msg) {
        if (!keyWord(msg).equals("RECEIVE_CARD"))
            throw new IllegalArgumentException();

        return Integer.parseInt(payload(msg)[0]);
    }

    public static String name(String msg) {
        if (!keyWord(msg).equals("RECEIVE_CARD"))
            throw new IllegalArgumentException();

        return payload(msg)[1];
    }
}
