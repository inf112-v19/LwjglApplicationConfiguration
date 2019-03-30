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

    public static final AssetDescriptor<Music> MUSIC_MAIN_THEME
            = new AssetDescriptor<>( "assets/music/Zander Noriega - Perpetual Tension.wav", Music.class);




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



    //GameScreen
    public static final AssetDescriptor<Texture> GAMESCREEN_BACKGROUND2
            = new AssetDescriptor<>("assets/screens/gamescreen/background2.png", Texture.class);

    public static final AssetDescriptor<Texture> GAMESCREEN_GRID2
            = new AssetDescriptor<>("assets/screens/gamescreen/grid2.png", Texture.class);

    public static final AssetDescriptor<Texture> GAMESCREEN_CLOUDS
            = new AssetDescriptor<>("assets/screens/gamescreen/clouds.png", Texture.class);


    //EndGameScreen
    public static final AssetDescriptor<Texture> ENDGAME_BACKGROUND
            = new AssetDescriptor<>("assets/screens/endgamescreen/endgamebackground.png", Texture.class);


    //Settings
    public static final AssetDescriptor<Texture> SETTINGS_BACKGROUND
            = new AssetDescriptor<>("assets/screens/settingsscreen/settingsbackground.png", Texture.class);



    //SetupScreen
    public static final AssetDescriptor<Texture> SETUP_SETUP_SCREEN
            = new AssetDescriptor<>( "assets/screens/setupscreen/setupscreen.png", Texture.class);

    public static final AssetDescriptor<Texture> SETUP_SELECT_SKIN_TEXT
            = new AssetDescriptor<>( "assets/screens/setupscreen/selectskintext.png", Texture.class);

    public static final AssetDescriptor<Texture> SETUP_SETUP_SCREEN_PLACE_FLAGS
            = new AssetDescriptor<>( "assets/screens/setupscreen/setupscreenPlaceFlags.png", Texture.class);




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
    public static final AssetDescriptor<Texture> REPAIRSITE_BACKGROUND
            = new AssetDescriptor<>("assets/objects/repairsite.png", Texture.class);

    public static final AssetDescriptor<Texture> REPAIRSITE_REPAIR_ANIMATION
            = new AssetDescriptor<>("assets/animations/wrench.png", Texture.class);


    //MenuScreen class
    public static final AssetDescriptor<Texture> MENUSCREEN_TITLESCREEN
            = new AssetDescriptor<>("assets/screens/menuscreen/titlescreen.jpg", Texture.class);

    public static final AssetDescriptor<Texture> MENUSCREEN_PRESS_ENTER_WHITE
            = new AssetDescriptor<>("assets/screens/menuscreen/pressEnterWhite.png", Texture.class);



    //TestScreen class
    public static final AssetDescriptor<Texture> TESTSCREEN_BACKGROUND
            = new AssetDescriptor<>("assets/screens/testscreen/testscreen.png", Texture.class);


    //Flag object
    public static final AssetDescriptor<Texture> FLAG_SKIN
            = new AssetDescriptor<>("assets/objects/flags.png", Texture.class);


    @SuppressWarnings("Duplicates")
    public static void load(){

        //Music
        manager.load(MUSIC_PLAYER_LASER);
        manager.load(MUSIC_PLAYER_REPAIR);
        manager.load(MUSIC_PLAYER_WILHELM_SCREAM);
        manager.load(MUSIC_MAIN_THEME);

        //Player skins
        manager.load(PLAYER_BARTENDER_CLAPTRAP);
        manager.load(PLAYER_CLAPTRAP_REFINED);
        manager.load(PLAYER_BUTLER_REFINED);
        manager.load(PLAYER_CLAPTRAP_3000);
        manager.load(PLAYER_TVBOT);

        //Hud buttons
        manager.load(BUTTON_SUBMIT);
        manager.load(BUTTON_SUBMIT_GREY);
        manager.load(BUTTON_CLEAR);
        manager.load(BUTTON_SETTINGS);

        //Gamescreen
        manager.load(GAMESCREEN_BACKGROUND2);
        manager.load(GAMESCREEN_GRID2);
        manager.load(GAMESCREEN_CLOUDS);

        //Endgame
        manager.load(ENDGAME_BACKGROUND);

        //Settings
        manager.load(SETTINGS_BACKGROUND);

        //Setup
        manager.load(SETUP_SETUP_SCREEN);
        manager.load(SETUP_SELECT_SKIN_TEXT);
        manager.load(SETUP_SETUP_SCREEN_PLACE_FLAGS);

        //Program register
        manager.load(REGISTER_PROGRAM_REGISTER);
        manager.load(REGISTER_DAMAGE_TOKEN);
        manager.load(REGISTER_LIFE_TOKEN);
        manager.load(REGISTER_WIRES);
        manager.load(REGISTER_LOCK_TOKEN);

        //Backup
        manager.load(BACKUP);

        //Repair site
        manager.load(REPAIRSITE_BACKGROUND);
        manager.load(REPAIRSITE_REPAIR_ANIMATION);

        //Menuscreen
        manager.load(MENUSCREEN_TITLESCREEN);
        manager.load(MENUSCREEN_PRESS_ENTER_WHITE);

        //Testscreen
        manager.load(TESTSCREEN_BACKGROUND);

        //Flag
        manager.load(FLAG_SKIN);

    }

    public void dispose(){
        System.out.println("disposing asset manager");
        manager.clear();
    }

}
