package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Mola;
import model.user.User;

public class ChooseCharacter implements Screen {
    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Texture backButton;
    Texture char1;
    Texture char2;
    Texture char3;
    Texture char4;
    Texture char5;
    User user;


    public ChooseCharacter(Mola game, boolean isMute, User user) {
        this.user = user;
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
        char1 = new Texture("characters/Char1.png");
        char2 = new Texture("characters/Char2.png");
        char3 = new Texture("characters/Char3.png");
        char4 = new Texture("characters/Char4.png");
        char5 = new Texture("characters/Char5.png");
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
        text.getData().setScale(0.25f);
        text.setColor(Color.GREEN);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "your account created successfully. now choose your character:", 150, 850);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(char1, 50, 200, char1.getWidth(), char1.getHeight());
        batch.draw(char2, 350, 200, char2.getWidth(), char2.getHeight());
        batch.draw(char3, 650, 200, char3.getWidth(), char3.getHeight());
        batch.draw(char4, 950, 200, char4.getWidth(), char4.getHeight());
        batch.draw(char5, 1250, 200, char5.getWidth(), char5.getHeight());
        batch.end();

        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new Start(game, isMute));
                    dispose();
                }
            }

            if (Gdx.input.getY() > 760 - char1.getHeight() && Gdx.input.getY() < 760) {
                if (Gdx.input.getX() > 50 && Gdx.input.getX() < 350) {
                    user.setCharacterFileAddress("characters/Char1.png");
                    game.setScreen(new MainMenu(game, isMute, user));
                    dispose();
                } else if (Gdx.input.getX() > 350 && Gdx.input.getX() < 650) {
                    user.setCharacterFileAddress("characters/Char2.png");
                    game.setScreen(new MainMenu(game, isMute, user));
                    dispose();
                } else if (Gdx.input.getX() > 650 && Gdx.input.getX() < 950) {
                    user.setCharacterFileAddress("characters/Char3.png");
                    game.setScreen(new MainMenu(game, isMute, user));
                    dispose();
                } else if (Gdx.input.getX() > 950 && Gdx.input.getX() < 1250) {
                    user.setCharacterFileAddress("characters/Char4.png");
                    game.setScreen(new MainMenu(game, isMute, user));
                    dispose();
                } else if (Gdx.input.getX() > 1250 && Gdx.input.getX() < 1550) {
                    user.setCharacterFileAddress("characters/Char5.png");
                    game.setScreen(new MainMenu(game, isMute, user));
                    dispose();
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
}
