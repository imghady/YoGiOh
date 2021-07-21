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

public class RemoveCardAdmin implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont error;
    BitmapFont text1;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Texture backButton;
    Texture field;
    Texture agree;
    User currentLoggedInUser;
    int message = 0;
    String messageString = "";
    boolean isHolderName = false;
    boolean isHolderNumber = false;
    String holder = "";
    String number = "";
    String cardName = "";
    controller.Auction auction = new controller.Auction();

    public RemoveCardAdmin(Mola game, boolean isMute, User currentLoggedInUser) {
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
        agree = new Texture("buttons/agree.png");
        field = new Texture("buttons/adminInput.png");
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
        error.getData().setScale(0.2f);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Admin Remove Card", 150, 850);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(field, 100, 300, field.getWidth(), field.getHeight());
        batch.draw(agree, 450, 200, agree.getWidth(), agree.getHeight());
        text.draw(batch, cardName, 370, 495);
        text.draw(batch, number, 420, 375);
        if (message == 1) {
            batch.begin();
            error.draw(batch, "please complete all fields.", 130, 280);
            batch.end();
        } else if (message == 2) {
            batch.begin();
            error.setColor(com.badlogic.gdx.graphics.Color.RED);
            error.draw(batch, messageString, 130, 600);
            batch.end();
        } else if (message == 3) {
            batch.begin();
            error.setColor(Color.RED);
            error.draw(batch, messageString, 130, 600);
            batch.end();
        }
        batch.end();


        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new Admin(game, isMute, currentLoggedInUser));
                    dispose();
                }
            }

            if (Gdx.input.getX() > 100 && Gdx.input.getX() < 100 + field.getWidth()) {
                if (Gdx.input.getY() > 610 - field.getHeight() / 2 && Gdx.input.getY() < 610) {
                    message = 0;
                    isHolderNumber = true;
                    Gdx.input.getTextInput(this, "Card Number", "", "");
                } else if (Gdx.input.getY() > 610 - field.getHeight() && Gdx.input.getY() < 610 - field.getHeight() / 2) {
                    message = 0;
                    isHolderName = true;
                    Gdx.input.getTextInput(this, "Card Name", "", "");
                }
            }

            if (Gdx.input.getY() > 950 - agree.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + agree.getWidth()) {
                    if (number.equals("") || cardName.equals("")) {
                        message = 1;
                    } else {

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
    public void resize(int i, int i1) {

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
    public void input(String s) {
        this.holder = s;

        if (isHolderName) {
            cardName = holder;
            isHolderName = false;
        } else if (isHolderNumber) {
            number = holder;
            isHolderNumber = false;
        }
    }

    @Override
    public void canceled() {

    }
}
