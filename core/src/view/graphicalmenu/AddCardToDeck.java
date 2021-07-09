package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Mola;
import controller.DeckMenu;
import model.Finisher;
import model.card.Card;
import model.user.Deck;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AddCardToDeck implements Screen, Input.TextInputListener {
    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont type;
    BitmapFont error;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Texture backButton;
    User currentLoggedInUser;
    String cardNameString = "";
    String deckNameString = "";
    Texture cardName;
    Texture deckName;
    int message = 0;
    Texture add;
    String address = null;
    boolean isNameCorrect = false;
    Texture card;
    Texture mainDeck;
    Texture sideDeck;
    String holder;
    boolean isHolderCardName = false;
    boolean isHolderDeckName = false;
    boolean isMainDeck = true;


    public AddCardToDeck(Mola game, boolean isMute, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        type = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        error = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        cardName = new Texture("buttons/cardName.png");
        add = new Texture("buttons/addCard.png");
        deckName = new Texture("buttons/deckName.png");
        mainDeck = new Texture("buttons/mainDeck.png");
        sideDeck = new Texture("buttons/sideDeck.png");
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
        type.getData().setScale(0.2f);
        error.getData().setScale(0.2f);
        type.setColor(Color.YELLOW);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Add card to deck", 150, 850);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(cardName, 50, 300, cardName.getWidth(), cardName.getHeight());
        batch.draw(deckName, 50, 450, deckName.getWidth(), deckName.getHeight());
        if (isMainDeck) {
            batch.draw(mainDeck, 280, 590, mainDeck.getWidth(), mainDeck.getHeight());
        } else {
            batch.draw(sideDeck, 280, 590, sideDeck.getWidth(), sideDeck.getHeight());
        }
        type.draw(batch, "Add to : ", 150, 650);
        batch.draw(add, 660, 180, add.getWidth(), add.getHeight());
        type.draw(batch, cardNameString, 340, 385);
        type.draw(batch, deckNameString, 360, 525);
        if (isNameCorrect && address != null) {
            card = new Texture(address);
            batch.draw(card, 1100, 150, card.getWidth(), card.getHeight());
        }
        if (message == 1) {
            error.setColor(Color.RED);
            error.draw(batch, "complete fields!", 100, 190);
        } else if (message == 2) {
            error.setColor(Color.RED);
            error.draw(batch, "incorrect card name!", 100, 190);
        } else if (message == 3) {
            error.setColor(Color.RED);
            error.draw(batch, "invalid deck name!", 100, 190);
        }  else if (message == 8) {
            error.setColor(Color.RED);
            error.draw(batch, "card with name " + cardNameString + " does not exist", 100, 190);
        } else if (message == 4) {
            error.setColor(Color.RED);
            error.draw(batch, "main deck is full!", 100, 190);
        } else if (message == 5) {
            error.setColor(Color.RED);
            error.draw(batch, "side deck is full!", 100, 190);
        } else if (message == 6) {
            error.setColor(Color.RED);
            error.draw(batch, "there are already three cards with name " + cardNameString + " in deck " + deckNameString, 100, 190);
        } else if (message == 7) {
            error.setColor(Color.GREEN);
            error.draw(batch, "card successfully added", 100, 190);
        }
        batch.end();

        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new Decks(game, isMute, currentLoggedInUser));
                    dispose();
                }
            }

            if (Gdx.input.getY() > 370 - mainDeck.getHeight() && Gdx.input.getY() < 370) {
                if (Gdx.input.getX() > 280 && Gdx.input.getX() < 280 + mainDeck.getWidth()) {
                    isMainDeck = !isMainDeck;
                }
            }

            if (Gdx.input.getY() > 660 - cardName.getHeight() && Gdx.input.getY() < 660) {
                if (Gdx.input.getX() > 50 && Gdx.input.getX() < 50 + cardName.getWidth()) {
                    message = 0;
                    isHolderCardName = true;
                    Gdx.input.getTextInput(this, "Card name", "", "");
                }
            }

            if (Gdx.input.getY() > 510 - deckName.getHeight() && Gdx.input.getY() < 510) {
                if (Gdx.input.getX() > 50 && Gdx.input.getX() < 50 + deckName.getWidth()) {
                    message = 0;
                    isHolderDeckName = true;
                    Gdx.input.getTextInput(this, "Deck name", "", "");
                }
            }

            if (Gdx.input.getY() > 780 - add.getHeight() && Gdx.input.getY() < 780) {
                if (Gdx.input.getX() > 660 && Gdx.input.getX() < 660 + add.getWidth()) {
                    message = 0;
                    if (cardNameString.equals("") || deckNameString.equals("")) {
                        message = 1;
                    } else if (getCardImageFileAddress(cardNameString) == null) {
                        message = 2;
                    } else if (!currentLoggedInUser.getDecks().containsKey(deckNameString)) {
                        message = 3;
                    } else {
                        boolean doesCardExist = false;
                        Card card = new Card();
                        ArrayList<Card> cards = currentLoggedInUser.getCards();
                        HashMap<String, Deck> decks = currentLoggedInUser.getDecks();
                        for (Card card1 : cards)
                            if (card1.getName().equals(cardNameString)) {
                                card = card1;
                                doesCardExist = true;
                            }
                        if (!doesCardExist) {
                            message = 8;
                        } else {
                            Deck deck = Deck.getDeckByName(deckNameString, currentLoggedInUser.getUsername());
                            if (isMainDeck && deck.getMainDeck().getMainDeckSize() >= 40) {
                                message = 4;
                            } else if (!isMainDeck && deck.getSideDeck().getSideDeckSize() >= 15) {
                                message = 5;
                            } else {
                                int countCardsInDeck = 0;
                                for (Card card1 : deck.getMainDeck().getMainDeckCards()) {
                                    if (card1.getName().equals(cardNameString))
                                        countCardsInDeck++;
                                }
                                for (Card card1 : deck.getSideDeck().getSideDeckCards()) {
                                    if (card1.getName().equals(cardNameString))
                                        countCardsInDeck++;
                                }
                                if (countCardsInDeck >= 3) {
                                    message = 6;
                                } else {
                                    message = 7;
                                    isNameCorrect = true;
                                    DeckMenu deckMenu = new DeckMenu(currentLoggedInUser.getUsername());
                                    System.out.println(isMainDeck);
                                    deckMenu.addCardToDeckFinal(deckNameString, !isMainDeck, Card.getCardByName(cardNameString), Deck.getDeckByName(deckNameString, currentLoggedInUser.getUsername()));
                                    try {
                                        Finisher.finish();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    address = getCardImageFileAddress(cardNameString) + ".jpg";
                                }
                            }
                        }
                    }
                }
            }

        }


        if (isMute) {
            batch.begin();
            batch.draw(mute, 10, 850, mute.getWidth(), mute.getHeight());
            Mola.music.pause();
            batch.end();
        } else {
            batch.begin();
            batch.draw(unmute, 10, 850, unmute.getWidth(), unmute.getHeight());
            Mola.music.play();
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
        this.holder = text;

        if (isHolderCardName) {
            cardNameString = holder;
            isHolderCardName = false;
        } else if (isHolderDeckName) {
            deckNameString = holder;
            isHolderDeckName = false;
        }
    }

    @Override
    public void canceled() {

    }
}
