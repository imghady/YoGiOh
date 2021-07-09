package view.graphicalmenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Mola;

public class Start extends Game implements Screen,  Input.TextInputListener  {

    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    Texture login;
    Texture register;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Texture title;
    Texture about;



    public Start(Mola game, boolean isMute) {
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        login = new Texture("buttons/login.png");
        register = new Texture("buttons/register.png");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        title = new Texture("buttons/title.png");
        about = new Texture("buttons/about.png");
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
        batch.draw(login, 110, 300, login.getWidth(),login.getHeight());
        batch.draw(title, 380, 520, title.getWidth(),title.getHeight());
        batch.draw(register, 110, 100, register.getWidth(),register.getHeight());
        batch.draw(about, 1150, 800, about.getWidth(),about.getHeight());
        batch.end();

        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getX() > 110 && Gdx.input.getX() < 110 + login.getWidth()
                    && Gdx.input.getY() < 660 && Gdx.input.getY() > 660 - login.getHeight()) {
                game.setScreen(new Login(game, isMute));
                dispose();
            }

            if (Gdx.input.getX() > 110 && Gdx.input.getX() < 110 + register.getWidth()
                    && Gdx.input.getY() < 860 && Gdx.input.getY() > 860 - register.getHeight()) {
                game.setScreen(new Register(game, isMute));
                dispose();
            }

            if (Gdx.input.getX() > 1150 && Gdx.input.getX() < 1150 + about.getWidth()
                    && Gdx.input.getY() < 160 && Gdx.input.getY() > 160 - about.getHeight()) {
                game.setScreen(new About(game, isMute));
                dispose();
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
    public void create() {

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

    }

    @Override
    public void canceled() {

    }
}
