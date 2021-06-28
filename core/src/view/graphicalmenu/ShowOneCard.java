package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import model.user.User;

public class ShowOneCard implements Screen {

    SpriteBatch batch;
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Texture backButton;
    User currentLoggedInUser;

    public ShowOneCard(MyGdxGame game, boolean isMute, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(wallpaper, 0, 0, 1600,960);
        text.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "show one card", 150, 850);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.end();

        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new Shop(game, isMute, currentLoggedInUser));
                    dispose();
                }
            }

        }

        if (isMute) {
            batch.begin();
            batch.draw(mute, 10, 850, mute.getWidth(), mute.getHeight());
            MyGdxGame.music.pause();
            batch.end();
        } else {
            batch.begin();
            batch.draw(unmute, 10, 850, unmute.getWidth(), unmute.getHeight());
            MyGdxGame.music.play();
            batch.end();
        }

    }


    public String getCardImageFileAddress(String input) {
        if (input.equals("Battle OX"))
            return "BattleOx";
        if (input.equals("Axe Raider"))
            return "AxeRaider";
        if (input.equals("Yomi Ship"))
            return "YomiShip";
        if (input.equals("Horn Imp"))
            return "HornImp";
        if (input.equals("Silver Fang"))
            return "SilverFang";
        if (input.equals("Suijin"))
            return "Suijin";
        if (input.equals("Fireyarou"))
            return "Fireyarou";
        if (input.equals("Curtain of the dark ones"))
            return "CurtainOfTheDarkOnes";
        if (input.equals("Feral Imp"))
            return "FeralImp";
        if (input.equals("Dark magician"))
            return "DarkMagician";
        if (input.equals("Wattkid"))
            return "Wattkid";
        if (input.equals("Baby dragon"))
            return "BabyDragon";
        if (input.equals("Hero of the east"))
            return "HeroOfTheEast";
        if (input.equals("Battle warrior"))
            return "BattleWarrior";
        if (input.equals("Crawling dragon"))
            return "CrawlingDragon";
        if (input.equals("Flame manipulator"))
            return "FlameManipulator";
        if (input.equals("Blue-Eyes white dragon"))
            return "BlueEyesWhiteDragon";
        if (input.equals("Crab Turtle"))
            return "CrabTurtle";
        if (input.equals("Skull Guardian"))
            return "SkullGuardian";
        if (input.equals("Slot Machine"))
            return "SlotMachine";
        if (input.equals("Haniwa"))
            return "Haniwa";
        if (input.equals("Man-Eater Bug"))
            return "ManEaterBug";
        if (input.equals("Gate Guardian"))
            return "GateGuardian";
        if (input.equals("Scanner"))
            return "Scanner";
        if (input.equals("Bitron"))
            return "Bitron";
        if (input.equals("Marshmallon"))
            return "Marshmallon";
        if (input.equals("Beast King Barbaros"))
            return "BeastKingBarbaros";
        if (input.equals("Texchanger"))
            return "Texchanger";
        if (input.equals("Leotron ") || input.equals("Leotron"))
            return "Leotron";
        if (input.equals("The Calculator"))
            return "TheCalculator";
        if (input.equals("Alexandrite Dragon"))
            return "AlexandriteDragon";
        if (input.equals("Mirage Dragon"))
            return "MirageDragon";
        if (input.equals("Herald of Creation"))
            return "HeraldOfCreation";
        if (input.equals("Exploder Dragon"))
            return "ExploderDragon";
        if (input.equals("Warrior Dai Grepher"))
            return "WarriorDaiGrepher";
        if (input.equals("Dark Blade"))
            return "DarkBlade";
        if (input.equals("Wattaildragon"))
            return "Wattaildragon";
        if (input.equals("Terratiger# the Empowered Warrior") || input.equals("Terratiger"))
            return "Terratiger";
        if (input.equals("The Tricky"))
            return "TheTricky";
        if (input.equals("Spiral Serpent"))
            return "SpiralSerpent";
        if (input.equals("Command Knight"))
            return "CommandKnight";
        if (input.equals("Trap Hole"))
            return "TrapHole";
        if (input.equals("Mirror Force"))
            return "MirrorForce";
        if (input.equals("Magic Cylinder"))
            return "MagicCylinder";
        if (input.equals("Mind Crush"))
            return "MindCrush";
        if (input.equals("Torrential Tribute"))
            return "TorrentialTribute";
        if (input.equals("Time Seal"))
            return "TimeSeal";
        if (input.equals("Negate Attack"))
            return "NegateAttack";
        if (input.equals("Solemn Warning"))
            return "SolemnWarning";
        if (input.equals("Magic Jamamer") || input.equals("Magic Jammer"))
            return "Magic Jammer";
        if (input.equals("Call of The Haunted"))
            return "Call of the Hunted";
        if (input.equals("Vanity's Emptiness"))
            return "VanitysEmptiness";
        if (input.equals("Wall of Revealing Light"))
            return "WallOfRevealingLight";
        if (input.equals("Monster Reborn"))
            return "MonsterReborn";
        if (input.equals("Terraforming"))
            return "Terraforming";
        if (input.equals("Pot of Greed"))
            return "PotOfGreed";
        if (input.equals("Raigeki"))
            return "Raigeki";
        if (input.equals("Change of Heart"))
            return "ChangeOfHeart";
        if (input.equals("Swords of Revealing Light"))
            return "SwordOfRevealingLight";
        if (input.equals("Harpie's Feather Duster"))
            return "HarpiesFeatherDuster";
        if (input.equals("Dark Hole"))
            return "DarkHole";
        if (input.equals("Supply Squad"))
            return "SupplySquad";
        if (input.equals("Spell Absorption"))
            return "SpellAbsorption";
        if (input.equals("Messenger of peace"))
            return "MessengerOfPeace";
        if (input.equals("Twin Twisters"))
            return "TwinTwisters";
        if (input.equals("Mystical space typhoon"))
            return "MysticalSpaceTyphoon";
        if (input.equals("Ring of defense"))
            return "RingOfDefense";
        if (input.equals("Yami"))
            return "Yami";
        if (input.equals("Forest"))
            return "Forest";
        if (input.equals("Closed Forest"))
            return "ClosedForest";
        if (input.equals("Umiiruka"))
            return "Umiiruka";
        if (input.equals("Sword of dark destruction"))
            return "SwordOfDarkDestruction";
        if (input.equals("Black Pendant"))
            return "BlackPendant";
        if (input.equals("United We Stand"))
            return "UnitedWeStand";
        if (input.equals("Magnum Shield"))
            return "MagnumShield";
        if (input.equals("Advanced Ritual Art"))
            return "AdvancedRitualArt";
        return null;
    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
