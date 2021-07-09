package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Mola;
import model.user.User;

public class ShowAllCards14 implements Screen {

    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont text2;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Texture backButton;
    Texture next;
    Texture pre;
    Texture wattkid;
    Texture yomiShip;
    User currentLoggedInUser;
    String backAddress;


    public ShowAllCards14(Mola game, boolean isMute, User currentLoggedInUser, String backAddress) {
        this.backAddress = backAddress;
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text2 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        next = new Texture("buttons/next.png");
        pre = new Texture("buttons/pre.png");
        wattkid = new Texture("Cards/Monsters/Wattkid.jpg");
        yomiShip = new Texture("Cards/Monsters/YomiShip.jpg");
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(wallpaper, 0, 0, 1600,960);
        text.getData().setScale(0.3f);
        text2.getData().setScale(0.2f);
        text2.setColor(Color.YELLOW);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Show all cards 14 - Monsters", 500, 920);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(next, 800, 30, next.getWidth(), next.getHeight());
        batch.draw(pre, 700, 30, pre.getWidth(), pre.getHeight());
        text2.draw(batch, "asset: " + currentLoggedInUser.showNumberOfCard("Hero of the east"), 250, 840);
        text2.draw(batch, "asset: " + currentLoggedInUser.showNumberOfCard("Horn Imp"), 750, 840);
        text2.draw(batch, "asset: " + currentLoggedInUser.showNumberOfCard("Leotron"), 1250, 840);
        batch.draw(wattkid, 100, 150, wattkid.getWidth(), wattkid.getHeight());
        batch.draw(yomiShip, 600, 150, yomiShip.getWidth(), yomiShip.getHeight());
        batch.end();

        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    if (backAddress.equals("shop")) {
                        game.setScreen(new Shop(game, isMute, currentLoggedInUser));
                        dispose();
                    } else if (backAddress.equals("decks")) {
                        game.setScreen(new Decks(game, isMute, currentLoggedInUser));
                        dispose();
                    }
                }
            }

            if (Gdx.input.getY() > 930 - next.getHeight() && Gdx.input.getY() < 930) {
                if (Gdx.input.getX() > 800 && Gdx.input.getX() < 800 + next.getWidth()) {
                    game.setScreen(new ShowAllCards15(game, isMute, currentLoggedInUser, backAddress));
                    dispose();
                }
            }

            if (Gdx.input.getY() > 930 - pre.getHeight() && Gdx.input.getY() < 930) {
                if (Gdx.input.getX() > 700 && Gdx.input.getX() < 700 + pre.getWidth()) {
                    game.setScreen(new ShowAllCards13(game, isMute, currentLoggedInUser, backAddress));
                    dispose();
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
}
