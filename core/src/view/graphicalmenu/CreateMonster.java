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
import controller.DuelMenu;
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
    BitmapFont error1;
    BitmapFont error2;
    BitmapFont text1;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Texture backButton;
    Texture effectMonster;
    Texture create;
    Texture add;
    Texture attributePic;
    User currentLoggedInUser;
    boolean isHolderName = false;
    boolean isHolderLevel = false;
    boolean isHolderType = false;
    boolean isHolderAttack = false;
    boolean isHolderAttribute = false;
    boolean isHolderDefence = false;
    boolean isHolderDescription = false;
    boolean isHolderMonsterForEffectName = false;
    String holder = "";
    String name = "";
    String level = "";
    String monsterType = "";
    String attack = "";
    String defence = "";
    String description = "";
    String cardDetail = "";
    String attribute = "";
    String monsterForEffectName = "";
    String createResult = "";
    int price = 0;
    int message1 = 0;
    int message2 = 0;

    ArrayList<Monster> monstersForEffect = new ArrayList<>();

    public CreateMonster(Mola game, boolean isMute, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        error1 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        error2 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        buttons = new Texture("buttons/monsterFields.png");
        effectMonster = new Texture("buttons/monsterEffect.png");
        add = new Texture("buttons/agree.png");
        create = new Texture("buttons/create.png");
        attributePic = new Texture("buttons/attribute.png");
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
        error1.getData().setScale(0.2f);
        error2.getData().setScale(0.2f);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        text.draw(batch, "Create Monster Card", 150, 900);
        text.draw(batch, cardDetail, 700, 870);
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(buttons, 150, 100, buttons.getWidth(), buttons.getHeight());
        batch.draw(attributePic, 150, 720, attributePic.getWidth(), attributePic.getHeight());
        batch.draw(effectMonster, 1200, 300, effectMonster.getWidth(), effectMonster.getHeight());
        batch.draw(add, 1250, 150, add.getWidth(), add.getHeight());
        if (message1 == 1) {
            error1.setColor(Color.RED);
            error1.draw(batch, "invalid monster name", 1200, 100);
        } else if (message1 == 2) {
            error1.setColor(Color.GREEN);
            error1.draw(batch, "add successfully", 1200, 100);
        }
        batch.draw(create, 1250, 600);
        if (!createResult.isEmpty()) {
            text.draw(batch, createResult, 1100, 800);
        }
        batch.end();

        cardDetail = "";
        cardDetail += "Name : " + name + "\n";
        cardDetail += "Monster" + "\n";
        cardDetail += "Level : " + level + "\n";
        cardDetail += "Type : " + monsterType + "\n";
        cardDetail += "ATK : " + attack + "\n";
        cardDetail += "DEF : " + defence + "\n";
        cardDetail += "Attribute : " + attribute + "\n";
        cardDetail += "Description : " + description + "\n";
        cardDetail += "Price : " + price + "\nEffect like : ";
        for (Monster monster : monstersForEffect) {
            cardDetail += monster.getName() + "\n";
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

            if (Gdx.input.getY() > 240 - attributePic.getHeight() && Gdx.input.getY() < 240) {
                if (Gdx.input.getX() > 150 && Gdx.input.getX() < 150 + attributePic.getWidth()) {
                    message2 = 0;
                    isHolderAttribute = true;
                    Gdx.input.getTextInput(this, "Attribute", "", "");
                }
            }


            if (Gdx.input.getX() > 150 && Gdx.input.getX() < 150 + buttons.getWidth()) {
                if (Gdx.input.getY() > 860 - buttons.getHeight() / 6 && Gdx.input.getY() < 860) {
                    message2 = 0;
                    isHolderDescription = true;
                    Gdx.input.getTextInput(this, "Description", "", "");
                } else if (Gdx.input.getY() > 860 - 2 * buttons.getHeight() / 6 && Gdx.input.getY() < 860 - buttons.getHeight() / 6) {
                    message2 = 0;
                    isHolderDefence = true;
                    Gdx.input.getTextInput(this, "Defence", "", "");
                } else if (Gdx.input.getY() > 860 - 3 * buttons.getHeight() / 6 && Gdx.input.getY() < 860 - 2 * buttons.getHeight() / 6) {
                    message2 = 0;
                    isHolderAttack = true;
                    Gdx.input.getTextInput(this, "Attack", "", "");
                } else if (Gdx.input.getY() > 860 - 4 * buttons.getHeight() / 6 && Gdx.input.getY() < 860 - 3 * buttons.getHeight() / 6) {
                    message2 = 0;
                    isHolderType = true;
                    Gdx.input.getTextInput(this, "Type", "", "");
                } else if (Gdx.input.getY() > 860 - 5 * buttons.getHeight() / 6 && Gdx.input.getY() < 860 - 4 * buttons.getHeight() / 6) {
                    message2 = 0;
                    isHolderLevel = true;
                    Gdx.input.getTextInput(this, "Level", "", "");
                } else if (Gdx.input.getY() > 860 - buttons.getHeight() && Gdx.input.getY() < 860 - 5 * buttons.getHeight() / 6) {
                    message2 = 0;
                    isHolderName = true;
                    Gdx.input.getTextInput(this, "Name", "", "");
                }
            }

            if (Gdx.input.getY() > 810 - add.getHeight() && Gdx.input.getY() < 810) {
                if (Gdx.input.getX() > 1250 && Gdx.input.getX() < 1250 + add.getWidth()) {
                    if (Monster.getMonsterByName(monsterForEffectName) == null) {
                        message1 = 1;
                    } else {
                        message1 = 2;
                        monstersForEffect.add(Monster.getMonsterByName(monsterForEffectName));
                    }
                }
            }

            if (Gdx.input.getY() > 660 - effectMonster.getHeight() && Gdx.input.getY() < 660) {
                if (Gdx.input.getX() > 1200 && Gdx.input.getX() < 1200 + effectMonster.getWidth()) {
                    message1 = 0;
                    isHolderMonsterForEffectName = true;
                    Gdx.input.getTextInput(this, "Monster Name", "", "");
                }
            }

            if (Gdx.input.getX() > 1250 && Gdx.input.getX() < 1250 + create.getWidth() &&
                    Gdx.input.getY() < 360 && Gdx.input.getY() > 360 - create.getHeight()) {
                createResult = createCard();
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

    private String createCard() {
        int lvl = 0;
        int atk = 0;
        int def = 0;
        if (name.isEmpty() || level.isEmpty() || description.isEmpty() || monsterType.isEmpty() || attack.isEmpty() || defence.isEmpty() || attribute.isEmpty() || monsterType.isEmpty())
            return "Fill all fields";
        try {
            lvl = Integer.parseInt(level);
        } catch (Exception e) {
            return "Level should be a number";
        }
        try {
            atk = Integer.parseInt(attack);
        } catch (Exception e) {
            return "Attack should be a number";
        }
        try {
            def = Integer.parseInt(defence);
        } catch (Exception e) {
            return "Defence should be a number";
        }
        int prc = atk + def + (monstersForEffect.size() + lvl) * 300;
        price = prc;
        String cardType = "Normal";
        if (monstersForEffect.size() > 0)
            cardType = "Effect";
        Monster newMonster = new Monster(name, lvl, attribute, monsterType, cardType, atk, def, description, prc);
        if (prc > currentLoggedInUser.getCredit() / 10) {
            return "not enough money";
        }
        currentLoggedInUser.setCredit(currentLoggedInUser.getCredit() - prc / 10);
        for (Monster monster : monstersForEffect) {
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect1))
                DuelMenu.monsterEffect1.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect2))
                DuelMenu.monsterEffect2.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect3))
                DuelMenu.monsterEffect3.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect4))
                DuelMenu.monsterEffect4.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect5))
                DuelMenu.monsterEffect5.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect6))
                DuelMenu.monsterEffect6.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect7))
                DuelMenu.monsterEffect7.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect8))
                DuelMenu.monsterEffect8.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect9))
                DuelMenu.monsterEffect9.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect10))
                DuelMenu.monsterEffect10.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect11))
                DuelMenu.monsterEffect11.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect12))
                DuelMenu.monsterEffect12.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect13))
                DuelMenu.monsterEffect13.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect14))
                DuelMenu.monsterEffect14.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect15))
                DuelMenu.monsterEffect15.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect16))
                DuelMenu.monsterEffect16.add(newMonster);
            if (DuelMenu.isCardInArray(monster, DuelMenu.monsterEffect17))
                DuelMenu.monsterEffect17.add(newMonster);
        }
        return "Success";
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
            monsterType = holder;
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
        } else if (isHolderAttribute) {
            attribute = holder;
            isHolderAttribute = false;
        }
    }

    @Override
    public void canceled() {

    }
}

