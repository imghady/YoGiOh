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
import model.user.User;
import view.TerminalOutput;

import java.io.IOException;

public class ChangePassword implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    Texture agree;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont error;
    Texture mute;
    Texture unmute;
    Texture change;
    boolean isMute;
    Texture backButton;
    User currentLoggedInUser;
    String newPassword = "";
    String currentPassword = "";
    String holder;
    boolean isCurrent;
    boolean isNew;
    int message;

    public ChangePassword(Mola game, boolean isMute, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        error = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        change = new Texture("buttons/changePass.png");
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
        text.getData().setScale(0.3f);
        error.getData().setScale(0.2f);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Change password", 150, 850);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(agree, 650, 100, agree.getWidth(), agree.getHeight());
        batch.draw(change, 100, 300, change.getWidth(), change.getHeight());
        text.draw(batch, currentPassword, 500, 610);
        text.draw(batch, newPassword, 450, 420);
        if (message == 1) {
            error.setColor(Color.RED);
            error.draw(batch, "wrong password!", 120, 250);
        } else if (message == 2) {
            error.setColor(Color.RED);
            error.draw(batch, "repetitious password!", 120, 250);
        } else if (message == 3) {
            error.setColor(Color.GREEN);
            error.draw(batch, "password changed successfully", 120, 250);
        }  else if (message == 4) {
            error.setColor(Color.YELLOW);
            error.draw(batch, "complete all fields", 120, 250);
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

            if (Gdx.input.getX() > 100 && Gdx.input.getX() < 100 + change.getWidth()) {
                if (Gdx.input.getY() > 660 - change.getHeight() / 2 && Gdx.input.getY() < 660) {
                    message = 0;
                    isNew = true;
                    Gdx.input.getTextInput(this, "new password", "", "");
                } else if (Gdx.input.getY() > 660 - change.getHeight() && Gdx.input.getY() < 660 - change.getHeight() / 2) {
                    message = 0;
                    isCurrent = true;
                    Gdx.input.getTextInput(this, "current password", "", "");
                }
            }

            if (Gdx.input.getY() > 860 - agree.getHeight() && Gdx.input.getY() < 860) {
                if (Gdx.input.getX() > 650 && Gdx.input.getX() < 650 + agree.getWidth()) {
                    if (newPassword.equals("") || currentPassword.equals("")) {
                        message = 4;
                    } else {
                        if (isPasswordWrong(currentPassword)) {
                            message = 1;
                        } else {
                            if (currentPassword.equals(newPassword)) {
                                message = 2;
                                TerminalOutput.output("please enter new password");
                            } else {
                                message = 3;
                                currentLoggedInUser.setPassword(newPassword);
                                try {
                                    Finisher.finish();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
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

    public boolean isPasswordWrong(String password) {
        return !currentLoggedInUser.getPassword().equals(password);
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

        if (isCurrent) {
            currentPassword = holder;
            isCurrent = false;
        }

        if (isNew) {
            newPassword = holder;
            isNew = false;
        }
    }

    @Override
    public void canceled() {

    }
}
