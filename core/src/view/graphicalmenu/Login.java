package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Mola;
import controller.LoginMenu;
import model.user.User;

import java.awt.*;
import java.util.Objects;

public class Login implements Screen, Input.TextInputListener {
    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont title;
    BitmapFont text;
    Texture login;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Music music;
    Texture backButton;
    Texture field;
    TextField usernameButton;

    String username = "";
    String password = "";
    String holder = "";
    boolean isHolderUsername = false;
    boolean isHolderPassword = false;

    int message = 0;

    public Login(Mola game, boolean isMute) {
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        title = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        login = new Texture("buttons/login.png");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        backButton = new Texture("buttons/back.png");
        field = new Texture("buttons/loginfields.png");
        usernameButton = new TextField();
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
        title.getData().setScale(0.35f);
        text.getData().setScale(0.21f);
        title.draw(batch, "Login Menu", 150, 850);
        batch.draw(login, 800, 100, login.getWidth(), login.getHeight());
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(field, 100, 350, field.getWidth(), field.getHeight());
        text.draw(batch, username, 370, 560);
        text.draw(batch, password, 370, 430);
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

            if (Gdx.input.getX() > 800 && Gdx.input.getX() < 800 + login.getWidth()
                    && Gdx.input.getY() < 860 && Gdx.input.getY() > 860 - login.getHeight()) {
                if (username != null && password != null && !username.equals("") && !password.equals("")) {
                    LoginMenu loginMenu = new LoginMenu();
                    String result = loginMenu.loginUser(username,password);
                    if (result.equals("success")){
                        Objects.requireNonNull(User.getUserByUsername(username)).setUserLoggedIn(true);
                        message = 4;
                        game.setScreen(new MainMenu(game, isMute, User.getUserByUsername(username)));
                        dispose();
                    }
                    else if (result.equals("pass")){
                        message = 3;
                    }
                    else if (result.equals("user")){
                        message = 2;
                    }
                } else {
                    message = 1;
                }
            }

            if (Gdx.input.getX() > 100 && Gdx.input.getX() < 100 + field.getWidth()) {
                if (Gdx.input.getY() > 610 - field.getHeight() / 2 && Gdx.input.getY() < 610) {
                    message = 0;
                    isHolderPassword = true;
                    Gdx.input.getTextInput(this, "password", "", "");
                } else if (Gdx.input.getY() > 610 - field.getHeight() && Gdx.input.getY() < 610 - field.getHeight() / 2) {
                    message = 0;
                    isHolderUsername = true;
                    Gdx.input.getTextInput(this, "username", "", "");
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


        if (message == 1) {
            batch.begin();
            text.draw(batch, "please complete all fields.", 130, 280);
            batch.end();
        } else if (message == 2) {
            batch.begin();
            text.setColor(com.badlogic.gdx.graphics.Color.RED);
            text.draw(batch, "user already login", 130, 280);
            batch.end();
        } else if (message == 3) {
            batch.begin();
            text.setColor(Color.RED);
            text.draw(batch, "Username and password didnt match!", 130, 280);
            batch.end();
        } else if (message == 4) {
            batch.begin();
            text.setColor(Color.GREEN);
            text.draw(batch, "user logged in successfully!", 130, 280);
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
        } else if (isHolderPassword) {
            password = holder;
            isHolderPassword = false;
        }

    }

    @Override
    public void canceled() {

    }

}
