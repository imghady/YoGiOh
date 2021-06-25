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
import com.mygdx.game.MyGdxGame;
import controller.LoginMenu;
import model.Finisher;
import model.user.User;

import javax.print.attribute.standard.Finishings;
import java.io.IOException;

public class Register implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont title;
    BitmapFont text;
    BitmapFont error;
    Texture register;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Music music;
    Texture backButton;
    String username = "";
    String password = "";
    String nickname = "";
    String holder;
    boolean isHolderUsername = false;
    boolean isHolderPassword = false;
    boolean isHolderNickname = false;
    Texture field;
    int message = 0;

    public Register(MyGdxGame game, boolean isMute) {
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        title = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        error = new BitmapFont(Gdx.files.internal("Agency.fnt"));
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
        title.getData().setScale(0.3f);
        text.getData().setScale(0.21f);
        error.getData().setScale(0.2f);
        text.setColor(Color.YELLOW);
        title.draw(batch, "Register Menu", 150, 850);
        batch.draw(register, 800, 100, register.getWidth(), register.getHeight());
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(field, 100, 300, field.getWidth(), field.getHeight());
        text.draw(batch, username, 370, 635);
        text.draw(batch, password, 370, 505);
        text.draw(batch, nickname, 370, 385);
        batch.end();

        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            backHandle();

            if (Gdx.input.getX() > 100 && Gdx.input.getX() < 100 + field.getWidth()) {
                if (Gdx.input.getY() > 660 - field.getHeight() / 3 && Gdx.input.getY() < 660) {
                    message = 0;
                    isHolderNickname = true;
                    Gdx.input.getTextInput(this, "nickname", "", "");
                } else if (Gdx.input.getY() > 660 - 2 * field.getHeight() / 3 && Gdx.input.getY() < 660 - field.getHeight() / 3) {
                    message = 0;
                    isHolderPassword = true;
                    Gdx.input.getTextInput(this, "password", "", "");
                } else if (Gdx.input.getY() > 660 - field.getHeight() && Gdx.input.getY() < 660 - 2 * field.getHeight() / 3) {
                    isHolderUsername = true;
                    Gdx.input.getTextInput(this, "username", "", "");
                    message = 0;
                }
            }

            if (Gdx.input.getY() > 860 - register.getHeight() && Gdx.input.getY() < 860) {
                if (Gdx.input.getX() > 800 && Gdx.input.getX() < 800 + register.getWidth()) {
                    if (username != null && password != null && nickname != null && !username.equals("") &&
                    !password.equals("") && !nickname.equals("")) {
                        if (User.getUserByUsername(username) == null) {
                            if (User.getUserByNickname(nickname) == null) {
                                message = 4;
                                LoginMenu loginMenu = new LoginMenu();
                                loginMenu.registerNewUser(username, nickname, password);
                                try {
                                    Finisher.finish();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                message = 3;
                            }
                        } else {
                            message = 2;
                        }
                    } else {
                        message = 1;
                    }
                }
            }

        }

        if (message == 1) {
            batch.begin();
            text.draw(batch, "please complete all fields.", 130, 280);
            batch.end();
        } else if (message == 2) {
            batch.begin();
            error.setColor(com.badlogic.gdx.graphics.Color.RED);
            error.draw(batch, "user with username " + username + " already exists", 110, 280);
            batch.end();
        } else if (message == 3) {
            batch.begin();
            error.setColor(Color.RED);
            error.draw(batch, "user with nickname " + nickname + " already exists", 110, 280);
            batch.end();
        } else if (message == 4) {
            batch.begin();
            error.setColor(Color.GREEN);
            error.draw(batch, "user created successfully!", 110, 280);
            batch.end();
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

    private void backHandle() {
        if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                game.setScreen(new Start(game, isMute));
                dispose();
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

        if (isHolderNickname) {
            nickname = holder;
            isHolderNickname = false;
        }
    }

    @Override
    public void canceled() {

    }
}
