package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;
import model.user.User;

public class DuelSelect implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont text2;
    Texture mute;
    Texture unmute;
    Texture agree;
    boolean isMute;
    Texture backButton;
    Texture duelButtons;
    User currentLoggedInUser;
    String secondUser = "";
    int rounds;

    public DuelSelect(MyGdxGame game, boolean isMute, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        text2 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        agree = new Texture("buttons/agree.png");
        duelButtons = new Texture("buttons/DuelButtons.png");
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
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.getData().setScale(0.5f);
        text2.getData().setScale(0.3f);
        text2.setColor(Color.CYAN);
        text.draw(batch, "Duel", 150, 850);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(duelButtons, 100, 150);
        if (!secondUser.isEmpty()) {
            text2.draw(batch, "Opponent: " + secondUser, 950, 350);
            batch.draw(agree, 1000, 100);
        }
        batch.end();

        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            int height = duelButtons.getHeight();

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

            if (x > 100 && x < 100 + duelButtons.getWidth() && y < 810 && y > 810 - height / 4) {
                game.setScreen(new Duel(game, isMute, currentLoggedInUser, true, "AI", 3));
            }
            if (x > 100 && x < 100 + duelButtons.getWidth() && y < 810 - height / 4 && y > 810 - 2 * height / 4) {
                game.setScreen(new Duel(game, isMute, currentLoggedInUser, true, "AI",1));
            }
            if (x > 100 && x < 100 + duelButtons.getWidth() && y < 810 - 2 * height / 4 && y > 810 - 3 * height / 4) {
                Gdx.input.getTextInput(this, "opponent username", "Enter opponent username", "");
                rounds = 3;
            }
            if (x > 100 && x < 100 + duelButtons.getWidth() && y < 810 - 3 * height / 4 && y > 810 - 4 * height / 4) {
                Gdx.input.getTextInput(this, "opponent username", "Enter opponent username", "");
                rounds = 1;
            }

            if (x > 1000 && x < 1000 + agree.getWidth() && y > 860 - agree.getHeight() && y < 860) {
                game.setScreen(new Duel(game, isMute, currentLoggedInUser, false, secondUser, rounds));
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
        this.secondUser = text;
    }

    @Override
    public void canceled() {

    }
}
