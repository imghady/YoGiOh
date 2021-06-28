package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import model.user.User;

public class ShowOneCard implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont text2;
    BitmapFont error;
    BitmapFont type;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Texture backButton;
    Texture show;
    Texture cardName;
    Texture card;
    String name = "";
    User currentLoggedInUser;
    int message = 0;
    boolean isNameCorrect = false;
    String address = null;

    public ShowOneCard(MyGdxGame game, boolean isMute, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text2 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        error = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        type = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        show = new Texture("buttons/show.png");
        cardName = new Texture("buttons/cardName.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(wallpaper, 0, 0, 1600, 960);
        text.getData().setScale(0.3f);
        text2.getData().setScale(0.2f);
        error.getData().setScale(0.2f);
        type.getData().setScale(0.15f);
        text2.setColor(Color.GREEN);
        type.setColor(Color.YELLOW);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "show one card", 150, 850);
        text2.draw(batch, "enter a card name to show:", 150, 500);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(cardName, 50, 300, cardName.getWidth(), cardName.getHeight());
        batch.draw(show, 660, 180, show.getWidth(), show.getHeight());
        type.draw(batch, name, 340, 380);
        if (isNameCorrect && address != null) {
            card = new Texture(address);
            batch.draw(card, 1100, 150, card.getWidth(), card.getHeight());
        }
        if (message == 1) {
            error.setColor(Color.RED);
            error.draw(batch, "enter a name", 150, 280);
        } else if (message == 2) {
            error.setColor(Color.RED);
            error.draw(batch, "incorrect card name!", 150, 280);
        }
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

            if (Gdx.input.getY() > 660 - cardName.getHeight() && Gdx.input.getY() < 660) {
                if (Gdx.input.getX() > 50 && Gdx.input.getX() < 50 + cardName.getWidth()) {
                    Gdx.input.getTextInput(this, "Card name", "", "");
                }
            }

            if (Gdx.input.getY() > 780 - show.getHeight() && Gdx.input.getY() < 780) {
                if (Gdx.input.getX() > 660 && Gdx.input.getX() < 660 + show.getWidth()) {
                    message = 0;
                    if (name.equals("")) {
                        message = 1;
                    } else if (getCardImageFileAddress(name) == null) {
                        message = 2;
                    } else {
                        message = 0;
                        isNameCorrect = true;
                        address = getCardImageFileAddress(name) + ".jpg";
                    }
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
            return "cards/monsters/BattleOx";
        if (input.equals("Axe Raider"))
            return "cards/monsters/AxeRaider";
        if (input.equals("Yomi Ship"))
            return "cards/monsters/YomiShip";
        if (input.equals("Horn Imp"))
            return "cards/monsters/HornImp";
        if (input.equals("Silver Fang"))
            return "cards/monsters/SilverFang";
        if (input.equals("Suijin"))
            return "cards/monsters/Suijin";
        if (input.equals("Fireyarou"))
            return "cards/monsters/Fireyarou";
        if (input.equals("Curtain of the dark ones"))
            return "cards/monsters/CurtainOfTheDarkOnes";
        if (input.equals("Feral Imp"))
            return "cards/monsters/FeralImp";
        if (input.equals("Dark magician"))
            return "cards/monsters/DarkMagician";
        if (input.equals("Wattkid"))
            return "cards/monsters/Wattkid";
        if (input.equals("Baby dragon"))
            return "cards/monsters/BabyDragon";
        if (input.equals("Hero of the east"))
            return "cards/monsters/HeroOfTheEast";
        if (input.equals("Battle warrior"))
            return "cards/monsters/BattleWarrior";
        if (input.equals("Crawling dragon"))
            return "cards/monsters/CrawlingDragon";
        if (input.equals("Flame manipulator"))
            return "cards/monsters/FlameManipulator";
        if (input.equals("Blue-Eyes white dragon"))
            return "cards/monsters/BlueEyesWhiteDragon";
        if (input.equals("Crab Turtle"))
            return "cards/monsters/CrabTurtle";
        if (input.equals("Skull Guardian"))
            return "cards/monsters/SkullGuardian";
        if (input.equals("Slot Machine"))
            return "cards/monsters/SlotMachine";
        if (input.equals("Haniwa"))
            return "cards/monsters/Haniwa";
        if (input.equals("Man-Eater Bug"))
            return "cards/monsters/ManEaterBug";
        if (input.equals("Gate Guardian"))
            return "cards/monsters/GateGuardian";
        if (input.equals("Scanner"))
            return "cards/monsters/Scanner";
        if (input.equals("Bitron"))
            return "cards/monsters/Bitron";
        if (input.equals("Marshmallon"))
            return "cards/monsters/Marshmallon";
        if (input.equals("Beast King Barbaros"))
            return "cards/monsters/BeastKingBarbaros";
        if (input.equals("Texchanger"))
            return "cards/monsters/Texchanger";
        if (input.equals("Leotron ") || input.equals("Leotron"))
            return "cards/monsters/Leotron";
        if (input.equals("The Calculator"))
            return "cards/monsters/TheCalculator";
        if (input.equals("Alexandrite Dragon"))
            return "cards/monsters/AlexandriteDragon";
        if (input.equals("Mirage Dragon"))
            return "cards/monsters/MirageDragon";
        if (input.equals("Herald of Creation"))
            return "cards/monsters/HeraldOfCreation";
        if (input.equals("Exploder Dragon"))
            return "cards/monsters/ExploderDragon";
        if (input.equals("Warrior Dai Grepher"))
            return "cards/monsters/WarriorDaiGrepher";
        if (input.equals("Dark Blade"))
            return "cards/monsters/DarkBlade";
        if (input.equals("Wattaildragon"))
            return "cards/monsters/Wattaildragon";
        if (input.equals("Terratiger# the Empowered Warrior") || input.equals("Terratiger"))
            return "cards/monsters/Terratiger";
        if (input.equals("The Tricky"))
            return "cards/monsters/TheTricky";
        if (input.equals("Spiral Serpent"))
            return "cards/monsters/SpiralSerpent";
        if (input.equals("Command Knight"))
            return "cards/monsters/CommandKnight";
        if (input.equals("Trap Hole"))
            return "cards/traps/TrapHole";
        if (input.equals("Mirror Force"))
            return "cards/traps/MirrorForce";
        if (input.equals("Magic Cylinder"))
            return "cards/traps/MagicCylinder";
        if (input.equals("Mind Crush"))
            return "cards/traps/MindCrush";
        if (input.equals("Torrential Tribute"))
            return "cards/traps/TorrentialTribute";
        if (input.equals("Time Seal"))
            return "cards/traps/TimeSeal";
        if (input.equals("Negate Attack"))
            return "cards/traps/NegateAttack";
        if (input.equals("Solemn Warning"))
            return "cards/traps/SolemnWarning";
        if (input.equals("Magic Jamamer") || input.equals("Magic Jammer"))
            return "cards/traps/Magic Jammer";
        if (input.equals("Call of The Haunted"))
            return "cards/traps/Call of the Hunted";
        if (input.equals("Vanity's Emptiness"))
            return "cards/traps/VanitysEmptiness";
        if (input.equals("Wall of Revealing Light"))
            return "cards/traps/WallOfRevealingLight";
        if (input.equals("Monster Reborn"))
            return "cards/spells/MonsterReborn";
        if (input.equals("Terraforming"))
            return "cards/spells/Terraforming";
        if (input.equals("Pot of Greed"))
            return "cards/spells/PotOfGreed";
        if (input.equals("Raigeki"))
            return "cards/spells/Raigeki";
        if (input.equals("Change of Heart"))
            return "cards/spells/ChangeOfHeart";
        if (input.equals("Swords of Revealing Light"))
            return "cards/spells/SwordOfRevealingLight";
        if (input.equals("Harpie's Feather Duster"))
            return "cards/spells/HarpiesFeatherDuster";
        if (input.equals("Dark Hole"))
            return "cards/spells/DarkHole";
        if (input.equals("Supply Squad"))
            return "cards/spells/SupplySquad";
        if (input.equals("Spell Absorption"))
            return "cards/spells/SpellAbsorption";
        if (input.equals("Messenger of peace"))
            return "cards/spells/MessengerOfPeace";
        if (input.equals("Twin Twisters"))
            return "cards/spells/TwinTwisters";
        if (input.equals("Mystical space typhoon"))
            return "cards/spells/MysticalSpaceTyphoon";
        if (input.equals("Ring of defense"))
            return "cards/spells/RingOfDefense";
        if (input.equals("Yami"))
            return "cards/spells/Yami";
        if (input.equals("Forest"))
            return "cards/spells/Forest";
        if (input.equals("Closed Forest"))
            return "cards/spells/ClosedForest";
        if (input.equals("Umiiruka"))
            return "cards/spells/Umiiruka";
        if (input.equals("Sword of dark destruction"))
            return "cards/spells/SwordOfDarkDestruction";
        if (input.equals("Black Pendant"))
            return "cards/spells/BlackPendant";
        if (input.equals("United We Stand"))
            return "cards/spells/UnitedWeStand";
        if (input.equals("Magnum Shield"))
            return "cards/spells/MagnumShield";
        if (input.equals("Advanced Ritual Art"))
            return "cards/spells/AdvancedRitualArt";
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

    @Override
    public void input(String text) {
        this.name = text;
    }

    @Override
    public void canceled() {

    }
}
