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
import model.user.User;

public class ChatRoom implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final Mola game;
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
    Texture messageField;
    Texture send;
    User currentLoggedInUser;
    String message = "";
    int message1 = 0;

    public ChatRoom(Mola game, boolean isMute, User currentLoggedInUser) {
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
        messageField = new Texture("buttons/messageField.png");
        send = new Texture("buttons/send.png");
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
        text2.getData().setScale(0.1f);
        text3.getData().setScale(0.2f);
        text.setColor(Color.GREEN);
        text3.setColor(Color.RED);

        text.draw(batch, "Chat Room", 150, 850);
        text.draw(batch, "enter your message:", 150, 280);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(messageField, 100, 100, messageField.getWidth(), messageField.getHeight());
        text2.draw(batch, message, 160, 175);
        batch.draw(send, 1200, 10, send.getWidth(), send.getHeight());
        if (message1 == 1) {
            text3.draw(batch, message + " already taken!", 150, 250);
        } else if (message1 == 2) {
            text.draw(batch, "nickname changed successfully", 140, 250);
        } else if (message1 == 3) {
            text3.draw(batch, "enter a nickname!", 140, 250);
        }
        batch.end();

        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new MainMenu(game, isMute, currentLoggedInUser));
                    dispose();
                }
            }

            if (Gdx.input.getY() > 860 - messageField.getHeight() && Gdx.input.getY() < 860) {
                if (Gdx.input.getX() > 100 && Gdx.input.getX() < 100 + messageField.getWidth()) {
                    message1 = 0;
                    Gdx.input.getTextInput(this, "message", "", "");
                }
            }

            if (Gdx.input.getY() > 950 - send.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 1200 && Gdx.input.getX() < 1200 + send.getWidth()) {

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
        message = text;
    }

    @Override
    public void canceled() {

    }
}
