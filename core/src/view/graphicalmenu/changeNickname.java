package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import model.Finisher;
import model.user.User;
import view.TerminalOutput;

import java.io.IOException;

public class changeNickname implements Screen, Input.TextInputListener {

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
    boolean isMute = false;
    Texture backButton;
    Texture change;
    Texture agree;
    User currentLoggedInUser;
    String nickname = "";
    int message = 0;

    public changeNickname(MyGdxGame game, boolean isMute, User currentLoggedInUser) {
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
        change = new Texture("buttons/changeNickname.png");
        agree = new Texture("buttons/agree.png");
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
        text.getData().setScale(0.2f);
        text2.getData().setScale(0.3f);
        text3.getData().setScale(0.2f);
        text.setColor(Color.GREEN);
        text3.setColor(Color.RED);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Change nickname", 150, 850);
        text.draw(batch, "enter your new nickname:", 150, 540);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(change, 100, 300, change.getWidth(), change.getHeight());
        text2.draw(batch, nickname, 550, 425);
        batch.draw(agree, 650, 100, agree.getWidth(), agree.getHeight());
        if (message == 1) {
            text3.draw(batch, nickname + " already taken!", 150, 250);
        } else if (message == 2) {
            text.draw(batch, "nickname changed successfully", 140, 250);
        }
        batch.end();

        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new Profile(game, isMute, currentLoggedInUser));
                    dispose();
                }
            }

            if (Gdx.input.getY() > 660 - change.getHeight() && Gdx.input.getY() < 660) {
                if (Gdx.input.getX() > 100 && Gdx.input.getX() < 100 + change.getWidth()) {
                    message = 0;
                    Gdx.input.getTextInput(this, "nickname", "", "");
                }
            }

            if (Gdx.input.getY() > 860 - agree.getHeight() && Gdx.input.getY() < 860) {
                if (Gdx.input.getX() > 650 && Gdx.input.getX() < 650 + agree.getWidth()) {
                    if (!canChangeNickname(nickname)) {
                        message = 1;
                    } else {
                        message = 2;
                        currentLoggedInUser.setNickname(nickname);
                        try {
                            Finisher.finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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

    public boolean canChangeNickname(String nickname) {
        return User.getUserByNickname(nickname) == null;
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
        nickname = text;
    }

    @Override
    public void canceled() {

    }
}
