package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import controller.DuelMenu;
import model.battle.Player;
import model.card.Card;
import model.mat.Mat;
import model.user.MainDeck;
import model.user.User;

import java.util.ArrayList;

public class Duel implements Screen {
    SpriteBatch batch;
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    Texture mute;
    Texture unmute;
    boolean isMute;
    boolean isAi;
    int rounds;
    Texture backButton;
    User currentLoggedInUser;
    Texture mat;
    Texture card;
    DuelMenu duelMenu;
    int xGrave = 300;
    int yGrave = 960 - 200;
    int xDeck = 1130;
    int yDeck = 960 - 200;
    int xM1 = 400;
    int xM2 = 570;
    int xM3 = 750;
    int xM4 = 920;
    int xM5 = 1090;
    int yM = 960 - 340;
    int xS1 = 400;
    int xS2 = 570;
    int xS3 = 750;
    int xS4 = 920;
    int xS5 = 1090;
    int yS = 960 - 545;
    float width = 110;
    float height = 160;
    Player showingPlayer;

    public Duel(MyGdxGame game, boolean isMute, User currentLoggedInUser, boolean isAi, String secondUserUsername, int rounds) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        card = new Texture("Cards/Monsters/BabyDragon.jpg");
        this.isAi = isAi;
        this.rounds = rounds;
        mat = new Texture("mat.png");
        String firstUser = currentLoggedInUser.getUsername();
        String secondUser = secondUserUsername;
        duelMenu = new DuelMenu(firstUser, secondUser, rounds, isAi);
        showingPlayer = duelMenu.currentTurnPlayer;
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
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Duel", 150, 900);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(mat, 300, 250);
        batch.end();
        loadMonsters();
        loadSpells();
        loadGraveyard();
        loadDeck();

        if (Gdx.input.justTouched()) {

            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new DuelSelect(game, isMute, currentLoggedInUser));
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

    private void loadDeck() {
        ArrayList<Card> mainDeckCards = showingPlayer.getMainDeckCard();
        if (!mainDeckCards.isEmpty()) {
            Card card = mainDeckCards.get(mainDeckCards.size() - 1);
            String address = getCardImageFileAddressLandscape(card.getName());
            Texture texture = new Texture(Gdx.files.internal(address + ".jpg"));
            batch.draw(texture, xDeck, yDeck, height, width);
        }
    }

    private void loadGraveyard() {
        ArrayList<Card> graveyard = duelMenu.currentTurnPlayer.getMat().getGraveyard();
        if (!graveyard.isEmpty()) {
            Card card = graveyard.get(graveyard.size() - 1);
            String address = getCardImageFileAddressLandscape(card.getName());
            Texture texture = new Texture(Gdx.files.internal(address + ".jpg"));
            batch.draw(texture, xGrave, yGrave, height, width);
        }
    }

    private void loadSpells() {
        batch.begin();
        Mat mat = showingPlayer.getMat();
        for (int i = 0; i < 5; i++) {
            if (mat.getSpellAndTrapZone(i) != null) {
                Card card = mat.getSpellAndTrapZone(i);
                String address = getCardImageFileAddress(card.getName());
                Texture texture = new Texture(Gdx.files.internal(address + ".jpg"));
                if (i == 0)
                    batch.draw(texture, xS1, yS, width, height);
                if (i == 1)
                    batch.draw(texture, xS2, yS, width, height);
                if (i == 2)
                    batch.draw(texture, xS3, yS, width, height);
                if (i == 3)
                    batch.draw(texture, xS4, yS, width, height);
                if (i == 4)
                    batch.draw(texture, xS5, yS, width, height);
            }
        }
        batch.end();
    }

    private void loadMonsters() {
        batch.begin();
        Mat mat = showingPlayer.getMat();
        for (int i = 0; i < 5; i++) {
            if (mat.getMonsterZone(i) != null) {
                Card card = mat.getMonsterZone(i);
                String address = getCardImageFileAddress(card.getName());
                Texture texture = new Texture(Gdx.files.internal(address + ".jpg"));
                if (i == 0)
                    batch.draw(texture, xM1, yM);
                if (i == 1)
                    batch.draw(texture, xM2, yM);
                if (i == 2)
                    batch.draw(texture, xM3, yM);
                if (i == 3)
                    batch.draw(texture, xM4, yM);
                if (i == 4)
                    batch.draw(texture, xM5, yM);
            }
        }
        batch.end();
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
            return "cards/SpellTrap/TrapHole";
        if (input.equals("Mirror Force"))
            return "cards/SpellTrap/MirrorForce";
        if (input.equals("Magic Cylinder"))
            return "cards/SpellTrap/MagicCylinder";
        if (input.equals("Mind Crush"))
            return "cards/SpellTrap/MindCrush";
        if (input.equals("Torrential Tribute"))
            return "cards/SpellTrap/TorrentialTribute";
        if (input.equals("Time Seal"))
            return "cards/SpellTrap/TimeSeal";
        if (input.equals("Negate Attack"))
            return "cards/SpellTrap/NegateAttack";
        if (input.equals("Solemn Warning"))
            return "cards/SpellTrap/SolemnWarning";
        if (input.equals("Magic Jamamer") || input.equals("Magic Jammer"))
            return "cards/SpellTrap/Magic Jammer";
        if (input.equals("Call of The Haunted"))
            return "cards/SpellTrap/Call of the Hunted";
        if (input.equals("Vanity's Emptiness"))
            return "cards/SpellTrap/VanitysEmptiness";
        if (input.equals("Wall of Revealing Light"))
            return "cards/SpellTrap/WallOfRevealingLight";
        if (input.equals("Monster Reborn"))
            return "cards/SpellTrap/MonsterReborn";
        if (input.equals("Terraforming"))
            return "cards/SpellTrap/Terraforming";
        if (input.equals("Pot of Greed"))
            return "cards/SpellTrap/PotOfGreed";
        if (input.equals("Raigeki"))
            return "cards/SpellTrap/Raigeki";
        if (input.equals("Change of Heart"))
            return "cards/SpellTrap/ChangeOfHeart";
        if (input.equals("Swords of Revealing Light"))
            return "cards/SpellTrap/SwordOfRevealingLight";
        if (input.equals("Harpie's Feather Duster"))
            return "cards/SpellTrap/HarpiesFeatherDuster";
        if (input.equals("Dark Hole"))
            return "cards/SpellTrap/DarkHole";
        if (input.equals("Supply Squad"))
            return "cards/SpellTrap/SupplySquad";
        if (input.equals("Spell Absorption"))
            return "cards/SpellTrap/SpellAbsorption";
        if (input.equals("Messenger of peace"))
            return "cards/SpellTrap/MessengerOfPeace";
        if (input.equals("Twin Twisters"))
            return "cards/SpellTrap/TwinTwisters";
        if (input.equals("Mystical space typhoon"))
            return "cards/SpellTrap/MysticalSpaceTyphoon";
        if (input.equals("Ring of defense"))
            return "cards/SpellTrap/RingOfDefense";
        if (input.equals("Yami"))
            return "cards/SpellTrap/Yami";
        if (input.equals("Forest"))
            return "cards/SpellTrap/Forest";
        if (input.equals("Closed Forest"))
            return "cards/SpellTrap/ClosedForest";
        if (input.equals("Umiiruka"))
            return "cards/SpellTrap/Umiiruka";
        if (input.equals("Sword of dark destruction"))
            return "cards/SpellTrap/SwordOfDarkDestruction";
        if (input.equals("Black Pendant"))
            return "cards/SpellTrap/BlackPendant";
        if (input.equals("United We Stand"))
            return "cards/SpellTrap/UnitedWeStand";
        if (input.equals("Magnum Shield"))
            return "cards/SpellTrap/MagnumShield";
        if (input.equals("Advanced Ritual Art"))
            return "cards/SpellTrap/AdvancedRitualArt";
        return null;
    }

    public String getCardImageFileAddressLandscape(String input) {
        if (input.equals("Battle OX"))
            return "cards/monsters/landscape/BattleOx";
        if (input.equals("Axe Raider"))
            return "cards/monsters/landscape/AxeRaider";
        if (input.equals("Yomi Ship"))
            return "cards/monsters/landscape/YomiShip";
        if (input.equals("Horn Imp"))
            return "cards/monsters/landscape/HornImp";
        if (input.equals("Silver Fang"))
            return "cards/monsters/landscape/SilverFang";
        if (input.equals("Suijin"))
            return "cards/monsters/landscape/Suijin";
        if (input.equals("Fireyarou"))
            return "cards/monsters/landscape/Fireyarou";
        if (input.equals("Curtain of the dark ones"))
            return "cards/monsters/landscape/CurtainOfTheDarkOnes";
        if (input.equals("Feral Imp"))
            return "cards/monsters/landscape/FeralImp";
        if (input.equals("Dark magician"))
            return "cards/monsters/landscape/DarkMagician";
        if (input.equals("Wattkid"))
            return "cards/monsters/landscape/Wattkid";
        if (input.equals("Baby dragon"))
            return "cards/monsters/landscape/BabyDragon";
        if (input.equals("Hero of the east"))
            return "cards/monsters/landscape/HeroOfTheEast";
        if (input.equals("Battle warrior"))
            return "cards/monsters/landscape/BattleWarrior";
        if (input.equals("Crawling dragon"))
            return "cards/monsters/landscape/CrawlingDragon";
        if (input.equals("Flame manipulator"))
            return "cards/monsters/landscape/FlameManipulator";
        if (input.equals("Blue-Eyes white dragon"))
            return "cards/monsters/landscape/BlueEyesWhiteDragon";
        if (input.equals("Crab Turtle"))
            return "cards/monsters/landscape/CrabTurtle";
        if (input.equals("Skull Guardian"))
            return "cards/monsters/landscape/SkullGuardian";
        if (input.equals("Slot Machine"))
            return "cards/monsters/landscape/SlotMachine";
        if (input.equals("Haniwa"))
            return "cards/monsters/landscape/Haniwa";
        if (input.equals("Man-Eater Bug"))
            return "cards/monsters/landscape/ManEaterBug";
        if (input.equals("Gate Guardian"))
            return "cards/monsters/landscape/GateGuardian";
        if (input.equals("Scanner"))
            return "cards/monsters/landscape/Scanner";
        if (input.equals("Bitron"))
            return "cards/monsters/landscape/Bitron";
        if (input.equals("Marshmallon"))
            return "cards/monsters/landscape/Marshmallon";
        if (input.equals("Beast King Barbaros"))
            return "cards/monsters/landscape/BeastKingBarbaros";
        if (input.equals("Texchanger"))
            return "cards/monsters/landscape/Texchanger";
        if (input.equals("Leotron ") || input.equals("Leotron"))
            return "cards/monsters/landscape/Leotron";
        if (input.equals("The Calculator"))
            return "cards/monsters/landscape/TheCalculator";
        if (input.equals("Alexandrite Dragon"))
            return "cards/monsters/landscape/AlexandriteDragon";
        if (input.equals("Mirage Dragon"))
            return "cards/monsters/landscape/MirageDragon";
        if (input.equals("Herald of Creation"))
            return "cards/monsters/landscape/HeraldOfCreation";
        if (input.equals("Exploder Dragon"))
            return "cards/monsters/landscape/ExploderDragon";
        if (input.equals("Warrior Dai Grepher"))
            return "cards/monsters/landscape/WarriorDaiGrepher";
        if (input.equals("Dark Blade"))
            return "cards/monsters/landscape/DarkBlade";
        if (input.equals("Wattaildragon"))
            return "cards/monsters/landscape/Wattaildragon";
        if (input.equals("Terratiger# the Empowered Warrior") || input.equals("Terratiger"))
            return "cards/monsters/landscape/Terratiger";
        if (input.equals("The Tricky"))
            return "cards/monsters/landscape/TheTricky";
        if (input.equals("Spiral Serpent"))
            return "cards/monsters/landscape/SpiralSerpent";
        if (input.equals("Command Knight"))
            return "cards/monsters/landscape/CommandKnight";
        if (input.equals("Trap Hole"))
            return "cards/SpellTrap/landscape/TrapHole";
        if (input.equals("Mirror Force"))
            return "cards/SpellTrap/landscape/MirrorForce";
        if (input.equals("Magic Cylinder"))
            return "cards/SpellTrap/landscape/MagicCylinder";
        if (input.equals("Mind Crush"))
            return "cards/SpellTrap/landscape/MindCrush";
        if (input.equals("Torrential Tribute"))
            return "cards/SpellTrap/landscape/TorrentialTribute";
        if (input.equals("Time Seal"))
            return "cards/SpellTrap/landscape/TimeSeal";
        if (input.equals("Negate Attack"))
            return "cards/SpellTrap/landscape/NegateAttack";
        if (input.equals("Solemn Warning"))
            return "cards/SpellTrap/landscape/SolemnWarning";
        if (input.equals("Magic Jamamer") || input.equals("Magic Jammer"))
            return "cards/SpellTrap/landscape/Magic Jammer";
        if (input.equals("Call of The Haunted"))
            return "cards/SpellTrap/landscape/Call of the Hunted";
        if (input.equals("Vanity's Emptiness"))
            return "cards/SpellTrap/landscape/VanitysEmptiness";
        if (input.equals("Wall of Revealing Light"))
            return "cards/SpellTrap/landscape/WallOfRevealingLight";
        if (input.equals("Monster Reborn"))
            return "cards/SpellTrap/landscape/MonsterReborn";
        if (input.equals("Terraforming"))
            return "cards/SpellTrap/landscape/Terraforming";
        if (input.equals("Pot of Greed"))
            return "cards/SpellTrap/landscape/PotOfGreed";
        if (input.equals("Raigeki"))
            return "cards/SpellTrap/landscape/Raigeki";
        if (input.equals("Change of Heart"))
            return "cards/SpellTrap/landscape/ChangeOfHeart";
        if (input.equals("Swords of Revealing Light"))
            return "cards/SpellTrap/landscape/SwordOfRevealingLight";
        if (input.equals("Harpie's Feather Duster"))
            return "cards/SpellTrap/landscape/HarpiesFeatherDuster";
        if (input.equals("Dark Hole"))
            return "cards/SpellTrap/landscape/DarkHole";
        if (input.equals("Supply Squad"))
            return "cards/SpellTrap/landscape/SupplySquad";
        if (input.equals("Spell Absorption"))
            return "cards/SpellTrap/landscape/SpellAbsorption";
        if (input.equals("Messenger of peace"))
            return "cards/SpellTrap/landscape/MessengerOfPeace";
        if (input.equals("Twin Twisters"))
            return "cards/SpellTrap/landscape/TwinTwisters";
        if (input.equals("Mystical space typhoon"))
            return "cards/SpellTrap/landscape/MysticalSpaceTyphoon";
        if (input.equals("Ring of defense"))
            return "cards/SpellTrap/landscape/RingOfDefense";
        if (input.equals("Yami"))
            return "cards/SpellTrap/landscape/Yami";
        if (input.equals("Forest"))
            return "cards/SpellTrap/landscape/Forest";
        if (input.equals("Closed Forest"))
            return "cards/SpellTrap/landscape/ClosedForest";
        if (input.equals("Umiiruka"))
            return "cards/SpellTrap/landscape/Umiiruka";
        if (input.equals("Sword of dark destruction"))
            return "cards/SpellTrap/landscape/SwordOfDarkDestruction";
        if (input.equals("Black Pendant"))
            return "cards/SpellTrap/landscape/BlackPendant";
        if (input.equals("United We Stand"))
            return "cards/SpellTrap/landscape/UnitedWeStand";
        if (input.equals("Magnum Shield"))
            return "cards/SpellTrap/landscape/MagnumShield";
        if (input.equals("Advanced Ritual Art"))
            return "cards/SpellTrap/landscape/AdvancedRitualArt";
        return null;
    }
}
