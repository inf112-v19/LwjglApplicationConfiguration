package inf112.roborally.game.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Contains assets used by the game.
 * With AssMan you are able to load as well as dispose all of them at the same time.
 * <p>
 * Calling {@link #dispose()} will dispose all assets.
 */
public class AssMan {

    public static final AssetManager manager = new AssetManager();

    //Music
    public static final AssetDescriptor<Sound> SOUND_PLAYER_LASER
            = new AssetDescriptor<>("assets/music/playerLaser.wav", Sound.class);

    public static final AssetDescriptor<Sound> SOUND_PLAYER_REPAIR
            = new AssetDescriptor<>("assets/music/playerRepair.wav", Sound.class);

    public static final AssetDescriptor<Sound> SOUND_PLAYER_WILHELM_SCREAM
            = new AssetDescriptor<>("assets/music/playerWilhelmScream.wav", Sound.class);

    public static final AssetDescriptor<Music> MUSIC_MAIN_THEME
            = new AssetDescriptor<>("assets/music/Zander Noriega - Perpetual Tension.wav", Music.class);

    public static final AssetDescriptor<Sound> SOUND_BUTTON_CLICKED
            = new AssetDescriptor<>("assets/soundeffects/buttons_and_clicks/Clic07.mp3", Sound.class);
    //Hud buttons
    public static final AssetDescriptor<Texture> BUTTON_SUBMIT
            = new AssetDescriptor<>("assets/gui/buttons/buttonSubmit.png", Texture.class);
    public static final AssetDescriptor<Texture> BUTTON_SUBMIT_GREY
            = new AssetDescriptor<>("assets/gui/buttons/buttonSubmitGrey.png", Texture.class);
    public static final AssetDescriptor<Texture> BUTTON_CLEAR
            = new AssetDescriptor<>("assets/gui/buttons/buttonClear.png", Texture.class);
    public static final AssetDescriptor<Texture> BUTTON_SETTINGS
            = new AssetDescriptor<>("assets/buttons/settingsbtn.png", Texture.class);

    //MenuScreen
    public static final AssetDescriptor<Texture> MENU_BACKGROUND
            = new AssetDescriptor<>("assets/img/menuScreen.png", Texture.class);

    //LobbyScreen
    public static final AssetDescriptor<Texture> LOBBY_BACKGROUND
            = new AssetDescriptor<>("assets/img/lobby.png", Texture.class);

    public static final AssetDescriptor<TextureAtlas> LOBBY_ANIMATION
            = new AssetDescriptor<>("assets/img/lobbyAnimation/waitingForPlayersToJoin.atlas", TextureAtlas.class);

    //GameScreen
    public static final AssetDescriptor<Texture> GAME_BACKGROUND
            = new AssetDescriptor<>("assets/img/background.png", Texture.class);
    public static final AssetDescriptor<Texture> GAME_GRID
            = new AssetDescriptor<>("assets/img/grid.png", Texture.class);
    public static final AssetDescriptor<Texture> GAME_CLOUDS
            = new AssetDescriptor<>("assets/img/clouds.png", Texture.class);
    //SetupScreen
    public static final AssetDescriptor<Texture> SETUP_PLACEFLAGS_BACKGROUND
            = new AssetDescriptor<>("assets/img/placeFlags.png", Texture.class);
    //Register display
    public static final AssetDescriptor<Texture> REGISTER_PROGRAM_REGISTER
            = new AssetDescriptor<>("assets/gui/programregisters.png", Texture.class);
    public static final AssetDescriptor<Texture> REGISTER_DAMAGE_TOKEN
            = new AssetDescriptor<>("assets/gui/tokens/damageToken.png", Texture.class);
    public static final AssetDescriptor<Texture> REGISTER_LIFE_TOKEN
            = new AssetDescriptor<>("assets/gui/tokens/lifeToken.png", Texture.class);
    public static final AssetDescriptor<Texture> REGISTER_WIRES
            = new AssetDescriptor<>("assets/gui/wires.png", Texture.class);
    public static final AssetDescriptor<Texture> REGISTER_LOCK_TOKEN
            = new AssetDescriptor<>("assets/gui/tokens/lockToken.png", Texture.class);
    //Backup
    public static final AssetDescriptor<Texture> BACKUP
            = new AssetDescriptor<>("assets/objects/backup.png", Texture.class);
    public static final AssetDescriptor<Texture> REPAIR_ANIMATION
            = new AssetDescriptor<>("assets/animations/wrench.png", Texture.class);
    //TestScreen class
    public static final AssetDescriptor<Texture> TEST_BACKGROUND
            = new AssetDescriptor<>("assets/img/testscreen.png", Texture.class);
    //Flag object
    public static final AssetDescriptor<TextureAtlas> FLAG_ATLAS
            = new AssetDescriptor<>("assets/objects/flags/flags.atlas", TextureAtlas.class);
    // Buttons:
    public static final AssetDescriptor<Texture> POWER_DOWN
            = new AssetDescriptor<>("assets/gui/buttons/powerdown.png", Texture.class);
    public static final AssetDescriptor<Texture> POWER_DOWN_PRESS
            = new AssetDescriptor<>("assets/gui/buttons/powerdown_press.png", Texture.class);
    public static final AssetDescriptor<Texture> POWER_DOWN_PRESSED
            = new AssetDescriptor<>("assets/gui/buttons/powerdown_active.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> PROGRAM_CARD_ATLAS
            = new AssetDescriptor<>("assets/gui/cards/imageButton.atlas", TextureAtlas.class);
    //Laser
    public static final AssetDescriptor<TextureAtlas> LASER_ATLAS
            = new AssetDescriptor<>("assets/objects/animatedlaser.atlas", TextureAtlas.class);
    //Status display for robots
    public static final AssetDescriptor<Texture> STATUS_DISPLAY_ROBOTS =
            new AssetDescriptor<>("assets/gui/robot_status_display.png", Texture.class);
    // Font
    public static final AssetDescriptor<BitmapFont> FONT_GROTESKIA =
            new AssetDescriptor<>("assets/fonts/groteskia.fnt", BitmapFont.class);
    public static final AssetDescriptor<BitmapFont> FONT_GROTESKIA_ITALIC =
            new AssetDescriptor<>("assets/fonts/groteskia_italic.fnt", BitmapFont.class);
    // TextFieldScreen
    public static final AssetDescriptor<Texture> TEXT_FIELD_BG
            = new AssetDescriptor<>("assets/buttons/inputfield/text_field_bg.png", Texture.class);
    public static final AssetDescriptor<Texture> TEXT_FIELD_CURSOR
            = new AssetDescriptor<>("assets/buttons/inputfield/cursor.png", Texture.class);
    static final AssetDescriptor<Texture> RIGHT_ARROW
            = new AssetDescriptor<>("assets/buttons/right_arrow.png", Texture.class);
    static final AssetDescriptor<Texture> RIGHT_ARROW_PRESS
            = new AssetDescriptor<>("assets/buttons/right_arrow_press.png", Texture.class);
    static final AssetDescriptor<Texture> LEFT_ARROW
            = new AssetDescriptor<>("assets/buttons/left_arrow.png", Texture.class);
    static final AssetDescriptor<Texture> LEFT_ARROW_PRESS
            = new AssetDescriptor<>("assets/buttons/left_arrow_press.png", Texture.class);
    static final AssetDescriptor<Texture> BACK
            = new AssetDescriptor<>("assets/buttons/back.png", Texture.class);
    static final AssetDescriptor<Texture> BACK_PRESS
            = new AssetDescriptor<>("assets/buttons/back_press.png", Texture.class);
    static final AssetDescriptor<Texture> TEXT_BUTTON_UP
            = new AssetDescriptor<>("assets/buttons/button.png", Texture.class);
    static final AssetDescriptor<Texture> TEXT_BUTTON_PRESS
            = new AssetDescriptor<>("assets/buttons/button_press.png", Texture.class);
    //Player skins
    private static final AssetDescriptor<Texture> PLAYER_BARTENDER_CLAPTRAP
            = new AssetDescriptor<>("assets/robots/bartenderclaptrap.png", Texture.class);
    private static final AssetDescriptor<Texture> PLAYER_CLAPTRAP_REFINED
            = new AssetDescriptor<>("assets/robots/claptrapRefined.png", Texture.class);
    private static final AssetDescriptor<Texture> PLAYER_BUTLER_REFINED
            = new AssetDescriptor<>("assets/robots/butlerRefined.png", Texture.class);
    private static final AssetDescriptor<Texture> PLAYER_CLAPTRAP_3000
            = new AssetDescriptor<>("assets/robots/claptrap3000.png", Texture.class);
    private static final AssetDescriptor<Texture> PLAYER_NURSE_BOT
            = new AssetDescriptor<>("assets/robots/NUR53_CP.png", Texture.class);
    private static final AssetDescriptor<Texture> PLAYER_CAPTAIN_BOT
            = new AssetDescriptor<>("assets/robots/EMPR_TP.png", Texture.class);
    private static final AssetDescriptor<Texture> PLAYER_WIZARD_BOT
            = new AssetDescriptor<>("assets/robots/B34RD_TP.png", Texture.class);
    private static final AssetDescriptor<Texture> PLAYER_COP_BOT
            = new AssetDescriptor<>("assets/robots/CU5TM-TP.png", Texture.class);
    //Maps
    private static final AssetDescriptor<Texture> MAP_VAULT
            = new AssetDescriptor<>("assets/maps/vault.png", Texture.class);
    private static final AssetDescriptor<Texture> MAP_SPACE_BUG
            = new AssetDescriptor<>("assets/maps/space_bug.png", Texture.class);
    private static final AssetDescriptor<Texture> MAP_SPACE_BUG2
            = new AssetDescriptor<>("assets/maps/space_bug2.png", Texture.class);
    private static final AssetDescriptor<Texture> MAP_AROUND_THE_WORLD
            = new AssetDescriptor<>("assets/maps/around_the_world.png", Texture.class);
    private static final AssetDescriptor<Texture> TEXT_BUTTON_CHECKED
            = new AssetDescriptor<>("assets/buttons/button_checked.png", Texture.class);
    public static TextureRegionDrawable getWaitingForPlayersAtlas;

    @SuppressWarnings("Duplicates")
    public static void load() {
        //Font
        manager.load(FONT_GROTESKIA);
        manager.load(FONT_GROTESKIA_ITALIC);
        //Music
        manager.load(SOUND_PLAYER_LASER);
        manager.load(SOUND_PLAYER_REPAIR);
        manager.load(SOUND_PLAYER_WILHELM_SCREAM);
        manager.load(MUSIC_MAIN_THEME);
        manager.load(SOUND_BUTTON_CLICKED);

        //Player skins
        manager.load(PLAYER_BARTENDER_CLAPTRAP);
        manager.load(PLAYER_CLAPTRAP_REFINED);
        manager.load(PLAYER_BUTLER_REFINED);
        manager.load(PLAYER_CLAPTRAP_3000);
        manager.load(PLAYER_CAPTAIN_BOT);
        manager.load(PLAYER_COP_BOT);
        manager.load(PLAYER_NURSE_BOT);
        manager.load(PLAYER_WIZARD_BOT);

        // Maps (png files)
        manager.load(MAP_VAULT);
        manager.load(MAP_SPACE_BUG);
        manager.load(MAP_SPACE_BUG2);
        manager.load(MAP_AROUND_THE_WORLD);

        //Hud buttons
        manager.load(BUTTON_SUBMIT);
        manager.load(BUTTON_SUBMIT_GREY);
        manager.load(BUTTON_CLEAR);
        manager.load(BUTTON_SETTINGS);

        //MENU
        manager.load(MENU_BACKGROUND);

        //Lobby
        manager.load(LOBBY_BACKGROUND);
        manager.load(LOBBY_ANIMATION);

        //Gamescreen
        manager.load(GAME_BACKGROUND);
        manager.load(GAME_GRID);
        manager.load(GAME_CLOUDS);

        //setup
        manager.load(SETUP_PLACEFLAGS_BACKGROUND);

        //Program register
        manager.load(REGISTER_PROGRAM_REGISTER);
        manager.load(REGISTER_DAMAGE_TOKEN);
        manager.load(REGISTER_LIFE_TOKEN);
        manager.load(REGISTER_WIRES);
        manager.load(REGISTER_LOCK_TOKEN);
        manager.load(POWER_DOWN);
        manager.load(POWER_DOWN_PRESS);
        manager.load(POWER_DOWN_PRESSED);

        //Backup
        manager.load(BACKUP);

        //Repair animation
        manager.load(REPAIR_ANIMATION);

        //Testscreen
        manager.load(TEST_BACKGROUND);

        //Flag
        manager.load(FLAG_ATLAS);

        //Program card
        manager.load(PROGRAM_CARD_ATLAS);

        //Laser
        manager.load(LASER_ATLAS);

        //Status display for robots
        manager.load(STATUS_DISPLAY_ROBOTS);

        // InputFieldScreen
        manager.load(TEXT_FIELD_BG);
        manager.load(TEXT_FIELD_CURSOR);
        manager.load(TEXT_BUTTON_UP);
        manager.load(TEXT_BUTTON_PRESS);
        manager.load(TEXT_BUTTON_CHECKED);

        manager.load(LEFT_ARROW);
        manager.load(LEFT_ARROW_PRESS);
        manager.load(RIGHT_ARROW);
        manager.load(RIGHT_ARROW_PRESS);
        manager.load(BACK);
        manager.load(BACK_PRESS);
    }

    public static Texture[] getMapChoices() {
        Texture[] maps = new Texture[4];
        maps[0] = manager.get(MAP_VAULT);
        maps[1] = manager.get(MAP_SPACE_BUG);
        maps[2] = manager.get(MAP_SPACE_BUG2);
        maps[3] = manager.get(MAP_AROUND_THE_WORLD);

        return maps;
    }

    public static Texture[] getPlayerSkins() {
        Texture[] skins = new Texture[8];
        skins[0] = manager.get(PLAYER_CLAPTRAP_REFINED);
        skins[1] = manager.get(PLAYER_COP_BOT);
        skins[2] = manager.get(PLAYER_CLAPTRAP_3000);
        skins[3] = manager.get(PLAYER_BARTENDER_CLAPTRAP);
        skins[4] = manager.get(PLAYER_NURSE_BOT);
        skins[5] = manager.get(PLAYER_BUTLER_REFINED);
        skins[6] = manager.get(PLAYER_WIZARD_BOT);
        skins[7] = manager.get(PLAYER_CAPTAIN_BOT);

        return skins;
    }

    public static TextureAtlas.AtlasRegion getFlagAtlasRegion(int flagN) {
        return manager.get(FLAG_ATLAS).findRegion(Integer.toString(flagN));
    }

    public static void dispose() {
        System.out.println("Disposing asset manager");
        manager.clear();
    }
}
