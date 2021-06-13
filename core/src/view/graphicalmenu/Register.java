package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

public class Register implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    Texture register;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Music music;
    Texture backButton;
    String username;
    String password;
    String nickname;
    String holder;
    boolean isHolderUsername = false;
    boolean isHolderPassword = false;
    boolean isHolderNickname = false;
    Texture field;

    public Register(MyGdxGame game, boolean isMute) {
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        register = new Texture("buttons/register.png");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        backButton = new Texture("buttons/back.png");
        field = new Texture("buttons/registerfields.png");
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
        text.draw(batch, "Register Menu", 150, 850);
        batch.draw(register, 800, 100, register.getWidth(), register.getHeight());
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(field, 100, 300, field.getWidth(), field.getHeight());
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

    @Override
    public void input(String text) {
        this.holder = text;

        if (isHolderUsername) {
            username = holder;
            isHolderUsername = false;
        }

        if (isHolderPassword) {
            password = holder;
            isHolderPassword = false;
        }
    }

    @Override
    public void canceled() {

    }
}
