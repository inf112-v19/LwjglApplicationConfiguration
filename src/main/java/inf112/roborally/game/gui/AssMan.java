package inf112.roborally.game.gui;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

/**
 * Contains assets used by the game.
 * With AssMan you are able to load as well as dispose all of them at the same time.
 *
 * .atlas files can not go here.
 */
public class AssMan{

    public static final AssetManager manager = new AssetManager();

    //Music
    public static final AssetDescriptor<Music> MUSIC_PLAYER_LASER
            = new AssetDescriptor<>("assets/music/playerLaser.wav", Music.class);

    public static final AssetDescriptor<Music> MUSIC_PLAYER_REPAIR
            = new AssetDescriptor<>("assets/music/playerRepair.wav", Music.class);

    public static final AssetDescriptor<Music> MUSIC_PLAYER_WILHELM_SCREAM
            = new AssetDescriptor<>("assets/music/playerWilhelmScream.wav", Music.class);



    //Player skins
    public static final AssetDescriptor<Texture> PLAYER_BARTENDER_CLAPTRAP
            = new AssetDescriptor<>("assets/robot/bartenderclaptrap.png", Texture.class);

    public static final AssetDescriptor<Texture> PLAYER_CLAPTRAP_REFINED
            = new AssetDescriptor<>("assets/robot/claptrapRefined.png", Texture.class);

    public static final AssetDescriptor<Texture> PLAYER_BUTLER_REFINED
            = new AssetDescriptor<>("assets/robot/butlerRefined.png", Texture.class);

    public static final AssetDescriptor<Texture> PLAYER_CLAPTRAP_3000
            = new AssetDescriptor<>("assets/robot/claptrap3000.png", Texture.class);

    public static final AssetDescriptor<Texture> PLAYER_TVBOT
            = new AssetDescriptor<>("assets/robot/tvBot.png", Texture.class);



    //Hud buttons
    public static final AssetDescriptor<Texture> BUTTON_SUBMIT
            = new AssetDescriptor<>("assets/cards/buttonSubmit.png", Texture.class);

    public static final AssetDescriptor<Texture> BUTTON_SUBMIT_GREY
            = new AssetDescriptor<>("assets/cards/buttonSubmitGrey.png", Texture.class);

    public static final AssetDescriptor<Texture> BUTTON_CLEAR
            = new AssetDescriptor<>("assets/cards/buttonClear.png", Texture.class);

    public static final AssetDescriptor<Texture> BUTTON_SETTINGS
            = new AssetDescriptor<>("assets/img/settingsbtn.png", Texture.class);



    //Background
    public static final AssetDescriptor<Texture> BACKGROUND_BACKGROUND_2
            = new AssetDescriptor<>("assets/img/background2.png", Texture.class);

    public static final AssetDescriptor<Texture> BACKGROUND_GRID_2
            = new AssetDescriptor<>("assets/img/grid2.png", Texture.class);

    public static final AssetDescriptor<Texture> BACKGROUND_CLOUDS
            = new AssetDescriptor<>("assets/img/clouds.png", Texture.class);

    public static final AssetDescriptor<Texture> BACKGROUND_ENDGAME
            = new AssetDescriptor<>("assets/img/endgamebackground.png", Texture.class);

    public static final AssetDescriptor<Texture> BACKGROUND_SETTINGS
            = new AssetDescriptor<>("assets/img/settingsbackground.png", Texture.class);



    //Register display
    public static final AssetDescriptor<Texture> REGISTER_PROGRAM_REGISTER
            = new AssetDescriptor<>("assets/cards/programregisters.png", Texture.class);

    public static final AssetDescriptor<Texture> REGISTER_DAMAGE_TOKEN
            = new AssetDescriptor<>("assets/cards/tokens/damageToken.png", Texture.class);

    public static final AssetDescriptor<Texture> REGISTER_LIFE_TOKEN
            = new AssetDescriptor<>("assets/cards/tokens/lifeToken.png", Texture.class);

    public static final AssetDescriptor<Texture> REGISTER_WIRES
            = new AssetDescriptor<>("assets/cards/wires.png", Texture.class);

    public static final AssetDescriptor<Texture> REGISTER_LOCK_TOKEN
            = new AssetDescriptor<>("assets/cards/tokens/lockToken.png", Texture.class);



    //Backup
    public static final AssetDescriptor<Texture> BACKUP
            = new AssetDescriptor<>("assets/objects/backup.png", Texture.class);



    //Repair
    public static final AssetDescriptor<Texture> REPAIR_SITE
            = new AssetDescriptor<>("assets/objects/repairsite.png", Texture.class);

    public static final AssetDescriptor<Texture> REPAIR_REPAIR_ANIMATION
            = new AssetDescriptor<>("assets/animations/wrench.png", Texture.class);


    //MenuScreen class
    public static final AssetDescriptor<Texture> MENUSCREEN_TITLESCREEN
            = new AssetDescriptor<>("assets/img/titlescreen.jpg", Texture.class);

    public static final AssetDescriptor<Texture> MENUSCREEN_PRESS_ENTER_WHITE
            = new AssetDescriptor<>("assets/img/pressEnterWhite.png", Texture.class);



    //TestScreen class
    public static final AssetDescriptor<Texture> TESTSCREEN
            = new AssetDescriptor<>("assets/img/testscreen.png", Texture.class);


    @SuppressWarnings("Duplicates")
    public static void load(){
        manager.load(MUSIC_PLAYER_LASER);
        manager.load(MUSIC_PLAYER_REPAIR);
        manager.load(MUSIC_PLAYER_WILHELM_SCREAM);

        manager.load(PLAYER_BARTENDER_CLAPTRAP);
        manager.load(PLAYER_CLAPTRAP_REFINED);
        manager.load(PLAYER_BUTLER_REFINED);
        manager.load(PLAYER_CLAPTRAP_3000);
        manager.load(PLAYER_TVBOT);

        manager.load(BUTTON_SUBMIT);
        manager.load(BUTTON_SUBMIT_GREY);
        manager.load(BUTTON_CLEAR);
        manager.load(BUTTON_SETTINGS);

        manager.load(BACKGROUND_BACKGROUND_2);
        manager.load(BACKGROUND_GRID_2);
        manager.load(BACKGROUND_CLOUDS);
        manager.load(BACKGROUND_ENDGAME);
        manager.load(BACKGROUND_SETTINGS);

        manager.load(REGISTER_PROGRAM_REGISTER);
        manager.load(REGISTER_DAMAGE_TOKEN);
        manager.load(REGISTER_LIFE_TOKEN);
        manager.load(REGISTER_WIRES);
        manager.load(REGISTER_LOCK_TOKEN);

        manager.load(BACKUP);

        manager.load(REPAIR_SITE);
        manager.load(REPAIR_REPAIR_ANIMATION);

        manager.load(MENUSCREEN_TITLESCREEN);
        manager.load(MENUSCREEN_PRESS_ENTER_WHITE);

        manager.load(TESTSCREEN);

    }

    public void dispose(){
        System.out.println("disposing asset manager");
        manager.clear();
    }

}
