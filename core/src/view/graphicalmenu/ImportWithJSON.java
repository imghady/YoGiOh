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
import model.card.Monster;
import model.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class ImportWithJSON implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    Texture buttons;
    BitmapFont text;
    BitmapFont error;
    BitmapFont text1;
    BitmapFont text2;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Texture backButton;
    Texture input;
    Texture agree;
    User currentLoggedInUser;
    String toJSON = "";
    int message = 0;
    String cardDetail = "";



    public ImportWithJSON(Mola game, boolean isMute, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        error = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text2 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        buttons = new Texture("buttons/importButtons.png");
        input = new Texture("buttons/JSONEnter.png");
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
        error.getData().setScale(0.2f);
        text2.getData().setScale(0.2f);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Import With JSON", 150, 850);
        text2.draw(batch, cardDetail, 700, 700);

        if (message == 1) {
            error.setColor(Color.RED);
            error.draw(batch, "enter JSON String", 100, 280);
        } else if (message == 2) {
            error.setColor(Color.RED);
            error.draw(batch, "incorrect JSON format", 100, 280);
        } else if (message == 3) {
            error.setColor(Color.GREEN);
            error.draw(batch, "import successfully", 100, 280);
        }

        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(input, 100, 500, input.getWidth(), input.getHeight());
        batch.draw(agree, 150, 350, agree.getWidth(), agree.getHeight());
        batch.end();

        if (Gdx.input.justTouched()) {

            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 110 && Gdx.input.getY() > 110 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getY() > 950 - backButton.getHeight() && Gdx.input.getY() < 950) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new ImportExport(game, isMute, currentLoggedInUser));
                    dispose();
                }
            }

            if (Gdx.input.getY() > 460 - input.getHeight() && Gdx.input.getY() < 460) {
                if (Gdx.input.getX() > 100 && Gdx.input.getX() < 100 + input.getWidth()) {
                    Gdx.input.getTextInput(this, "JSON String", "", "");
                }
            }

            if (Gdx.input.getY() > 610 - agree.getHeight() && Gdx.input.getY() < 610) {
                if (Gdx.input.getX() > 150 && Gdx.input.getX() < 150 + agree.getWidth()) {
                    message = 0;
                    cardDetail = "";
                    if (toJSON.equals("")) {
                        message = 1;
                    } else {
                        JSONObject card = new JSONObject();
                        JSONParser parser = new JSONParser();
                        try {
                            card = (JSONObject) parser.parse(toJSON);
                            if (card.containsKey("monsterType")) {
                                if (card.containsKey("level") && card.containsKey("defence") && card.containsKey("attack")
                                        && card.containsKey("price") && card.containsKey("name") && card.containsKey("cardType")
                                        && card.containsKey("description") && card.containsKey("attribute")) {

                                    message = 3;

                                    cardDetail += card.get("name") + "\n";
                                    cardDetail += "Monster" + "\n";
                                    cardDetail += "Level : " + card.get("level") + "\n";
                                    cardDetail += "Type : " + card.get("cardType") + "\n";
                                    cardDetail += "ATK : " + card.get("attack") + "\n";
                                    cardDetail += "DEF : " + card.get("defence") + "\n";
                                    cardDetail += "Description : " + card.get("description") + "\n";
                                    cardDetail += "Price : " + card.get("price");

                                } else {
                                    message = 2;
                                }
                            } else {
                                if (card.containsKey("type")) {
                                    if (card.get("type").equals("Spell")) {
                                        if (card.containsKey("price") && card.containsKey("icon") && card.containsKey("status") && card.containsKey("name")
                                                && card.containsKey("description")) {

                                            message = 3;

                                            cardDetail += card.get("name") + "\n";
                                            cardDetail += "Spell" + "\n";
                                            cardDetail += "Status : " + card.get("status") + "\n";
                                            cardDetail += "Description : " + card.get("description") + "\n";
                                            cardDetail += "Price : " + card.get("price");

                                        } else {
                                            message = 2;
                                        }
                                    } else if (card.get("type").equals("Trap")) {
                                        if (card.containsKey("price") && card.containsKey("icon") && card.containsKey("status") && card.containsKey("name")
                                                && card.containsKey("description")) {

                                            message = 3;

                                            cardDetail += card.get("name") + "\n";
                                            cardDetail += "Trap" + "\n";
                                            cardDetail += "Status : " + card.get("status") + "\n";
                                            cardDetail += "Description : " + card.get("description") + "\n";
                                            cardDetail += "Price : " + card.get("price");
                                        } else {
                                            message = 2;
                                        }
                                    }
                                }
                            }
                        } catch (ParseException e) {
                            message = 2;
                            e.printStackTrace();
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
        this.toJSON = text;
    }

    @Override
    public void canceled() {

    }
}

