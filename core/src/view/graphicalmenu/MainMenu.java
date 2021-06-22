package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import model.user.User;

public class MainMenu implements Screen {

    SpriteBatch batch;
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont title;
    BitmapFont text;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Texture backButton;
    User currentLoggedInUser;
    Texture fields;
    Texture play;

    public MainMenu(MyGdxGame game, boolean isMute, User user) {
        this.currentLoggedInUser = user;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        title = new BitmapFont(Gdx.files.internal("times.fnt"));
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        fields = new Texture("buttons/mainFields.png");
        play = new Texture("buttons/play.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        batch.begin();
        text.getData().setScale(0.2f);
        batch.draw(wallpaper, 0, 0, 1600, 960);
        title.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        text.draw(batch, "hi " + currentLoggedInUser.getUsername(), 910, 910);
        batch.draw(fields, 170, 140, fields.getWidth(), fields.getHeight());
        batch.draw(play, 1210, 170, 300, 300);
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

            if (Gdx.input.getY() > 790 - play.getHeight() && Gdx.input.getY() < 790) {
                if (Gdx.input.getX() > 1210 && Gdx.input.getX() < 1210 + play.getWidth()) {
                    game.setScreen(new Duel(game, isMute, currentLoggedInUser));
                    dispose();
                }
            }

            if (Gdx.input.getX() > 170 && Gdx.input.getX() < 170 + fields.getWidth()) {
                if (Gdx.input.getY() > 665 && Gdx.input.getY() < 820) {
                    game.setScreen(new Shop(game, isMute, currentLoggedInUser));
                    dispose();
                } else if (Gdx.input.getY() > 510 && Gdx.input.getY() < 665) {
                    game.setScreen(new Scoreboard(game, isMute, currentLoggedInUser));
                    dispose();
                } else if (Gdx.input.getY() > 355 && Gdx.input.getY() < 510) {
                    game.setScreen(new Profile(game, isMute, currentLoggedInUser));
                    dispose();
                } else if (Gdx.input.getY() > 200 && Gdx.input.getY() < 335) {
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
