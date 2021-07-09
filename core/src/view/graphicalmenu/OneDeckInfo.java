package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;
import com.mygdx.game.Mola;
import model.card.Card;
import model.user.Deck;
import model.user.User;

import java.util.ArrayList;

public class OneDeckInfo implements Screen, Input.TextInputListener {
    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont text2;
    BitmapFont text3;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Deck deck;
    Texture backButton;
    User currentLoggedInUser;
    String deckInfo = "";
    String mainDeckCard = "";
    String sideDeckCard = "";
    String mainDeckCard2 = "";
    String sideDeckCard2 = "";

    public OneDeckInfo(Mola game, boolean isMute, User currentLoggedInUser, Deck deck) {
        this.deck = deck;
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text2 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text3 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
    }

    @Override
    public void input(String text) {

    }

    @Override
    public void canceled() {

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
        text2.getData().setScale(0.17f);
        text3.getData().setScale(0.17f);
        text2.setColor(Color.GREEN);
        text3.setColor(Color.YELLOW);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Deck Info", 150, 900);
        deckInfo = "Name: " + deck.getName() + " , ";
        if (deck.isValid()) {
            deckInfo += "Valid";
        }
        if (!deck.isValid()) {
            deckInfo += "Invalid";
        }
        if (deck.isActiveDeck()) {
            deckInfo += " , activeDeck";
        }
        ArrayList<Card> mainCards = deck.getMainDeck().getMainDeckCards();
        ArrayList<Card> sideCards = deck.getSideDeck().getSideDeckCards();
        mainDeckCard = "MainDeck:\n";
        sideDeckCard = "SideDeck:\n";
        mainDeckCard2 = "";
        sideDeckCard2 = "";
        for (int i = 0; i < 20; i++) {
            if (mainCards.size() == i) {
                break;
            }
            mainDeckCard += mainCards.get(i).getName() + "\n";
        }
        for (int i = 20; i < mainCards.size(); i++) {
            mainDeckCard2 += mainCards.get(i).getName() + "\n";
        }
        for (int i = 0; i < 20; i++) {
            if (sideCards.size() == i) {
                break;
            }
            sideDeckCard += sideCards.get(i).getName() + "\n";
        }
        for (int i = 20; i < sideCards.size(); i++) {
            sideDeckCard2 += sideCards.get(i).getName() + "\n";
        }
        text3.draw(batch, deckInfo, 150, 820);
        text1.draw(batch, mainDeckCard, 550, 900);
        text1.draw(batch, mainDeckCard2, 810, 900);
        text1.draw(batch, sideDeckCard, 1070, 900);
        text1.draw(batch, sideDeckCard2, 1330, 900);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
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

        }

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
