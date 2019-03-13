package inf112.roborally.game.sound;

/**
 * Interface for the music and the sounds in the game
 * Because there is a difference in the LibGdx library between music and sounds,
 * there a little difference in their global variable for music/sound
 * Therefore, they need to be implemented differently
 */
public interface IGameMusic {

    /**
     * Play the music
     */
    public void play();

    /**
     * Mute the music
     */
    public void mute();

    /**
     * Dispose of the music, should be done when closing the game
     */
    public void dispose();

}
