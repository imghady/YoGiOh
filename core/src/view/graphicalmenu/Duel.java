package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import controller.DuelMenu;
import model.card.Card;
import model.user.User;

import java.util.ArrayList;

public class Duel implements Screen {
    SpriteBatch batch;
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    Texture mute;
    Texture unmute;
    boolean isMute;
    boolean isAi;
    int rounds;
    Texture backButton;
    User currentLoggedInUser;
    Texture mat;
    Texture card;
    DuelMenu duelMenu;
    int xGrave = 300;
    int yGrave = 960 - 200;
    int xDeck = 1130;
    int yDeck = 960 - 200;
    int xM1 = 400;
    int xM2 = 570;
    int xM3 = 750;
    int xM4 = 920;
    int xM5 = 1090;
    int yM = 960 - 340;
    int xS1 = 400;
    int xS2 = 570;
    int xS3 = 750;
    int xS4 = 920;
    int xS5 = 1090;
    int yS = 960 - 545;
    float width = 110;
    float height = 160;

    public Duel(MyGdxGame game, boolean isMute, User currentLoggedInUser, boolean isAi, String secondUserUsername, int rounds) {
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
        card = new Texture("Cards/Monsters/BabyDragon.jpg");
        this.isAi = isAi;
        this.rounds = rounds;
        mat = new Texture("mat.png");
        String firstUser = currentLoggedInUser.getUsername();
        String secondUser = secondUserUsername;
        //duelMenu = new DuelMenu(firstUser, secondUser, rounds, isAi);
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
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Duel", 150, 900);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(mat, 300, 250);
        batch.draw(card, xGrave, yGrave,  width/2f,  height/2f, width, height, 1, 1, 90, 0, 0, card.getHeight(), card.getWidth(), false, false);
        batch.end();
        loadMonsters();
        loadSpells();
        loadGraveyard();
        loadDeck();

        if (Gdx.input.justTouched()) {

            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new DuelSelect(game, isMute, currentLoggedInUser));
                    dispose();
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

    private void loadDeck() {

    }

    private void loadGraveyard() {
//        ArrayList<Card> graveyard = duelMenu.currentTurnPlayer.getMat().getGraveyard();
//        if (!graveyard.isEmpty()) {
//
//        }
    }

    private void loadSpells() {

    }

    private void loadMonsters() {
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
