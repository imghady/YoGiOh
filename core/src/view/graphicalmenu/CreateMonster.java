package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Mola;
import model.card.Monster;
import model.user.User;

import java.util.ArrayList;

public class CreateMonster implements Screen, Input.TextInputListener {

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
    Texture effectMonster;
    Texture add;
    User currentLoggedInUser;
    boolean isHolderName = false;
    boolean isHolderLevel = false;
    boolean isHolderType = false;
    boolean isHolderAttack = false;
    boolean isHolderDefence = false;
    boolean isHolderDescription = false;
    boolean isHolderMonsterForEffectName = false;
    String holder = "";
    String name = "";
    String level = "";
    String type = "";
    String attack = "";
    String defence = "";
    String description = "";
    String cardDetail = "";
    String monsterForEffectName = "";
    int price = 0;
    int message = 0;
    ArrayList<Monster> monstersForEffect = new ArrayList<>();

    public CreateMonster(Mola game, boolean isMute, User currentLoggedInUser) {
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
        buttons = new Texture("buttons/monsterFields.png");
        effectMonster = new Texture("buttons/monsterEffect.png");
        add = new Texture("buttons/agree.png");

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
        text.draw(batch, "Create Monster Card", 150, 850);
        text.draw(batch, cardDetail, 700, 870);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(buttons, 150, 100, buttons.getWidth(), buttons.getHeight());
        batch.draw(effectMonster, 1200, 300, effectMonster.getWidth(), effectMonster.getHeight());
        batch.draw(add, 1250, 150, add.getWidth(), add.getHeight());
        batch.end();

        cardDetail = "";
        cardDetail += "Name : " + name + "\n";
        cardDetail += "Monster" + "\n";
        cardDetail += "Level : " + level + "\n";
        cardDetail += "Type : " + type + "\n";
        cardDetail += "ATK : " + attack + "\n";
        cardDetail += "DEF : " + defence + "\n";
        cardDetail += "Description : " + description + "\n";
        cardDetail += "Price : " + price + "\nEffect like : ";
        for (Monster monster : monstersForEffect) {

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
                if (Gdx.input.getY() > 860 - buttons.getHeight() / 6 && Gdx.input.getY() < 860) {
                    message = 0;
                    isHolderDescription = true;
                    Gdx.input.getTextInput(this, "Description", "", "");
                } else if (Gdx.input.getY() > 860 - 2 * buttons.getHeight() / 6 && Gdx.input.getY() < 860 - buttons.getHeight() / 6) {
                    message = 0;
                    isHolderDefence = true;
                    Gdx.input.getTextInput(this, "Defence", "", "");
                } else if (Gdx.input.getY() > 860 - 3 * buttons.getHeight() / 6 && Gdx.input.getY() < 860 - 2 * buttons.getHeight() / 6) {
                    message = 0;
                    isHolderAttack = true;
                    Gdx.input.getTextInput(this, "Attack", "", "");
                } else if (Gdx.input.getY() > 860 - 4 * buttons.getHeight() / 6 && Gdx.input.getY() < 860 - 3 * buttons.getHeight() / 6) {
                    message = 0;
                    isHolderType = true;
                    Gdx.input.getTextInput(this, "Type", "", "");
                } else if (Gdx.input.getY() > 860 - 5 * buttons.getHeight() / 6 && Gdx.input.getY() < 860 - 4 * buttons.getHeight() / 6) {
                    message = 0;
                    isHolderLevel = true;
                    Gdx.input.getTextInput(this, "Level", "", "");
                } else if (Gdx.input.getY() > 860 - buttons.getHeight() && Gdx.input.getY() < 860 - 5 * buttons.getHeight() / 6) {
                    message = 0;
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
        } else if (isHolderLevel) {
            level = holder;
            isHolderLevel = false;
        } else if (isHolderType) {
            type = holder;
            isHolderType = false;
        } else if (isHolderAttack) {
            attack = holder;
            isHolderAttack = false;
        } else if (isHolderDefence) {
            defence = holder;
            isHolderDefence = false;
        } else if (isHolderDescription) {
            description = holder;
            isHolderDescription = false;
        } else if (isHolderMonsterForEffectName) {
            monsterForEffectName = holder;
            isHolderMonsterForEffectName = false;
        }
    }

    @Override
    public void canceled() {

    }
}

