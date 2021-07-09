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
import model.Finisher;
import model.user.Deck;
import model.user.User;

import java.io.IOException;

public class DeckSetting implements Screen, Input.TextInputListener {
    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont text2;
    BitmapFont error;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Texture backButton;
    Texture field;
    Texture active;
    Texture delete;
    User currentLoggedInUser;
    String deckName = "";
    int message = 0;

    public DeckSetting(Mola game, boolean isMute, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        error = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text2 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        field = new Texture("buttons/deckName.png");
        active = new Texture("buttons/active.png");
        delete = new Texture("buttons/delete.png");
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
        error.getData().setScale(0.18f);
        text2.setColor(Color.GREEN);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Active deck", 150, 850);
        text2.draw(batch, "enter deck name to Active:", 150, 400);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(field, 100, 300, field.getWidth(), field.getHeight());
        text2.draw(batch, deckName, 430, 375);
        batch.draw(active, 650, 180, active.getWidth(), active.getHeight());
        batch.draw(delete, 450, 180, delete.getWidth(), delete.getHeight());
        if (message == 2) {
            error.setColor(Color.RED);
            error.draw(batch, "deck with name " + deckName + " dose not exists", 100, 265);
        } else if (message == 1) {
            error.setColor(Color.GREEN);
            error.draw(batch, "deck Activated successfully", 100, 265);
        } else if (message == 3) {
            error.setColor(Color.RED);
            error.draw(batch, "enter a deck name!", 100, 265);
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

            if (Gdx.input.getY() > 660 - field.getHeight() && Gdx.input.getY() < 660) {
                if (Gdx.input.getX() > 100 && Gdx.input.getX() < 100 + field.getWidth()) {
                    message = 0;
                    Gdx.input.getTextInput(this, "deck name", "", "");
                }
            }

            if (Gdx.input.getY() > 780 - active.getHeight() && Gdx.input.getY() < 780) {
                if (Gdx.input.getX() > 650 && Gdx.input.getX() < 650 + active.getWidth()) {
                    if (deckName.equals("")) {
                        message = 3;
                    } else if (currentLoggedInUser.getDecks().containsKey(deckName)) {
                        message = 1;
                        Deck deck = Deck.getDeckByName(deckName, currentLoggedInUser.getUsername());
                        currentLoggedInUser.setActiveDeck(deck);
                        try {
                            Finisher.finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        message = 2;
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
        this.deckName = text;
    }

    @Override
    public void canceled() {

    }
}
