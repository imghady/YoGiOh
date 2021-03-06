package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Mola;
import controller.ScoreboardMenu;
import model.user.User;

public class OnlineUser implements Screen {
    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont title;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont text2;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Texture backButton;
    User currentLoggedInUser;
    String[] users;
    long now;

    public OnlineUser(Mola game, boolean isMute, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        title = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text2 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        users = ScoreboardMenu.getOnlineUser();
        now = System.currentTimeMillis();
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
        text.getData().setScale(0.2f);
        text2.getData().setScale(0.2f);
        text.setColor(Color.YELLOW);
        text2.setColor(Color.GREEN);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        title.draw(batch, "Scoreboard", 150, 850);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            if (i == users.length) {
                break;
            }
            if (users[i].equals(currentLoggedInUser.getNickname())) {
                text2.draw(batch, (i+1) + ". " + users[i], 200, 700 - 60 * counter);
            } else {
                text.draw(batch, (i+1) + ". " + users[i], 200, 700 - 60 * counter);
            }
            counter++;
        }
        counter = 0;
        for (int i = 10; i < users.length; i++) {
            if (users[i].equals(currentLoggedInUser.getNickname())) {
                text2.draw(batch, (i+1) + ". " + users[i], 550, 700 - 60 * counter);
            } else {
                text.draw(batch, (i+1) + ". " + users[i], 550, 700 - 60 * counter);
            }
            counter++;
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

        }

        if (System.currentTimeMillis() - now >= 10000){
            users = ScoreboardMenu.getOnlineUser();
            now = System.currentTimeMillis();
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
}
