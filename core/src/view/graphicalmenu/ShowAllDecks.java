package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import model.user.Deck;
import model.user.User;

import java.util.HashMap;
import java.util.Map;

public class ShowAllDecks implements Screen {
    SpriteBatch batch;
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont text2;
    BitmapFont text3;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Texture backButton;
    User currentLoggedInUser;
    String activeDecks = "";
    String deActiveDecks = "";

    public ShowAllDecks(MyGdxGame game, boolean isMute, User currentLoggedInUser) {
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
        text.draw(batch, "All Decks", 150, 900);
        activeDecks = "Active deck:\n";
        HashMap<String, Deck> decks = currentLoggedInUser.getDecks();
        Deck activeDeck = currentLoggedInUser.getActiveDeck();
        if (activeDeck != null)
            printDeckForAllDeck(activeDeck);
        deActiveDecks = "other decks:\n";
        for (Map.Entry<String, Deck> entry : decks.entrySet()) {
            Deck deck = entry.getValue();
            if (!deck.isActiveDeck())
                printOtherDeckForAllDeck(deck);
        }
        text2.draw(batch, activeDecks, 300, 780);
        text3.draw(batch, deActiveDecks, 300, 730);
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

    private void printDeckForAllDeck(Deck deck) {
        activeDecks += deck.getName() + ": main deck " + deck.getMainDeck().getMainDeckSize() +
                ", side deck " + deck.getSideDeck().getSideDeckSize() + ", ";
        if (deck.isValid())
            activeDecks += "valid\n";
        else
            activeDecks += "invalid\n";
    }

    private void printOtherDeckForAllDeck(Deck deck) {
        deActiveDecks += deck.getName() + ": main deck " + deck.getMainDeck().getMainDeckSize() +
                ", side deck " + deck.getSideDeck().getSideDeckSize() + ", ";
        if (deck.isValid())
            deActiveDecks += "valid\n";
        else
            deActiveDecks += "invalid\n";
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
