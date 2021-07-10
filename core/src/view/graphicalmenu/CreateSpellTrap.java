package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.Mola;
import model.user.User;

public class CreateSpellTrap implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    Texture buttons;
    BitmapFont text;
    BitmapFont text1;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Texture backButton;
    User currentLoggedInUser;
    boolean isTrap;
    boolean isHolderName = false;
    boolean isHolderIcon = false;
    boolean isHolderDescription = false;
    boolean isHolderStatus = false;

    String holder = "";
    String name = "";
    String icon = "";
    String status = "";
    String description = "";
    String cardDetail = "";

    int price = 0;
    int message = 0;

    public CreateSpellTrap(Mola game, boolean isMute, User currentLoggedInUser, boolean isTrap) {
        this.isTrap = isTrap;
        this.currentLoggedInUser = currentLoggedInUser;
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
        buttons = new Texture("buttons/spellTrapFields.png");
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
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        if (isTrap) {
            text.draw(batch, "Create Trap Card", 150, 850);
        } else {
            text.draw(batch, "Create Spell Card", 150, 850);
        }
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(buttons, 150, 150, buttons.getWidth(), buttons.getHeight());
        text.draw(batch, cardDetail, 700, 750);
        batch.end();

        if (isTrap) {
            cardDetail = "";
            cardDetail += "Name : " + name + "\n";
            cardDetail += "Trap" + "\n";
            cardDetail += "Icon : " + icon + "\n";
            cardDetail += "Status : " + status + "\n";
            cardDetail += "Description : " + description + "\n";
            cardDetail += "Price : " + price;
        } else {
            cardDetail = "";
            cardDetail += "Name : " + name + "\n";
            cardDetail += "Spell" + "\n";
            cardDetail += "Icon : " + icon + "\n";
            cardDetail += "Status : " + status + "\n";
            cardDetail += "Description : " + description + "\n";
            cardDetail += "Price : " + price;
        }


        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new SelectType(game, isMute, currentLoggedInUser));
                    dispose();
                }
            }

            if (Gdx.input.getX() > 150 && Gdx.input.getX() < 150 + buttons.getWidth()) {
                if (Gdx.input.getY() > 810 - buttons.getHeight() / 4 && Gdx.input.getY() < 810) {
                    isHolderDescription = true;
                    Gdx.input.getTextInput(this, "Description", "", "");
                } else if (Gdx.input.getY() > 810 - 2 * buttons.getHeight() / 4 && Gdx.input.getY() < 810 - buttons.getHeight() / 4) {
                    isHolderStatus = true;
                    Gdx.input.getTextInput(this, "Status", "", "");
                } else if (Gdx.input.getY() > 810 - 3 * buttons.getHeight() / 4 && Gdx.input.getY() < 810 - 2 * buttons.getHeight() / 4) {
                    isHolderIcon = true;
                    Gdx.input.getTextInput(this, "Icon", "", "");
                } else if (Gdx.input.getY() > 810 - buttons.getHeight() && Gdx.input.getY() < 810 - 3 * buttons.getHeight() / 4) {
                    isHolderName = true;
                    Gdx.input.getTextInput(this, "Name", "", "");
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
        this.holder = text;

        if (isHolderName) {
            name = holder;
            isHolderName = false;
        } else if (isHolderIcon) {
            icon = holder;
            isHolderIcon = false;
        } else if (isHolderStatus) {
            status = holder;
            isHolderStatus = false;
        } else if (isHolderDescription) {
            description = holder;
            isHolderDescription = false;
        }
    }

    @Override
    public void canceled() {

    }
}


