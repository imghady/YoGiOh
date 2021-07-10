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
import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.user.User;

import java.util.ArrayList;

public class CreateSpellTrap implements Screen, Input.TextInputListener {

    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    Texture buttons;
    BitmapFont text;
    BitmapFont error1;
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

    Texture effectMonster;
    Texture create;
    Texture add;

    String holder = "";
    String name = "";
    String icon = "";
    String status = "";
    String description = "";
    String cardDetail = "";
    String cardForEffectName = "";
    boolean isHolderSpellForEffectName = false;
    String createResult = "";

    int price = 0;
    int message1 = 0;

    ArrayList<Card> spellTrapsForEffect = new ArrayList<>();

    public CreateSpellTrap(Mola game, boolean isMute, User currentLoggedInUser, boolean isTrap) {
        this.isTrap = isTrap;
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        error1 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        mute = new Texture("buttons/mute.png");
        create = new Texture("buttons/create.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        effectMonster = new Texture("buttons/monsterEffect.png");
        add = new Texture("buttons/agree.png");
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
        error1.getData().setScale(0.2f);
        text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
        if (isTrap) {
            text.draw(batch, "Create Trap Card", 150, 850);
        } else {
            text.draw(batch, "Create Spell Card", 150, 850);
        }
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(buttons, 150, 150, buttons.getWidth(), buttons.getHeight());
        text.draw(batch, cardDetail, 700, 750);
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

        if (isTrap) {
            cardDetail = "";
            cardDetail += "Name : " + name + "\n";
            cardDetail += "Trap" + "\n";
            cardDetail += "Icon : " + icon + "\n";
            cardDetail += "Status : " + status + "\n";
            cardDetail += "Description : " + description + "\n";
            cardDetail += "Price : " + price+ "\nEffect like : ";
            for (Card card : spellTrapsForEffect) {
                cardDetail += card.getName() + "\n";
            }
        } else {
            cardDetail = "";
            cardDetail += "Name : " + name + "\n";
            cardDetail += "Spell" + "\n";
            cardDetail += "Icon : " + icon + "\n";
            cardDetail += "Status : " + status + "\n";
            cardDetail += "Description : " + description + "\n";
            cardDetail += "Price : " + price+ "\nEffect like : ";
            for (Card card : spellTrapsForEffect) {
                cardDetail += card.getName() + "\n";
            }
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

            if (Gdx.input.getY() > 810 - add.getHeight() && Gdx.input.getY() < 810) {
                if (Gdx.input.getX() > 1250 && Gdx.input.getX() < 1250 + add.getWidth()) {
                    if (Card.getCardByName(cardForEffectName) == null) {
                        message1 = 1;
                    } else {
                        message1 = 2;
                        spellTrapsForEffect.add(Card.getCardByName(cardForEffectName));
                    }
                }
            }

            if (Gdx.input.getY() > 660 - effectMonster.getHeight() && Gdx.input.getY() < 660) {
                if (Gdx.input.getX() > 1200 && Gdx.input.getX() < 1200 + effectMonster.getWidth()) {
                    message1 = 0;
                    isHolderSpellForEffectName = true;
                    Gdx.input.getTextInput(this, "Card Name", "", "");
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
        if (name.isEmpty() || icon.isEmpty() || status.isEmpty() || description.isEmpty())
            return "Fill all fields";
        int prc = 2000 + spellTrapsForEffect.size() * 300;
        if (prc > currentLoggedInUser.getCredit() / 10) {
            return "not enough money";
        }
        currentLoggedInUser.setCredit(currentLoggedInUser.getCredit() - prc / 10);
        Card newCard;
        if (isTrap)
            newCard = new Trap(name, icon, description, status, prc);
        else
            newCard = new Spell(name, icon, description, status, prc);
        for (Card card: spellTrapsForEffect) {
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect1))
                DuelMenu.spellTrapEffect1.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect2))
                DuelMenu.spellTrapEffect2.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect3))
                DuelMenu.spellTrapEffect3.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect4))
                DuelMenu.spellTrapEffect4.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect5))
                DuelMenu.spellTrapEffect5.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect6))
                DuelMenu.spellTrapEffect6.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect7))
                DuelMenu.spellTrapEffect7.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect8))
                DuelMenu.spellTrapEffect8.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect9))
                DuelMenu.spellTrapEffect9.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect10))
                DuelMenu.spellTrapEffect10.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect11))
                DuelMenu.spellTrapEffect11.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect12))
                DuelMenu.spellTrapEffect12.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect13))
                DuelMenu.spellTrapEffect13.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect14))
                DuelMenu.spellTrapEffect14.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect15))
                DuelMenu.spellTrapEffect15.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect16))
                DuelMenu.spellTrapEffect16.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect17))
                DuelMenu.spellTrapEffect17.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect18))
                DuelMenu.spellTrapEffect18.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect19))
                DuelMenu.spellTrapEffect19.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect20))
                DuelMenu.spellTrapEffect20.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect21))
                DuelMenu.spellTrapEffect21.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect22))
                DuelMenu.spellTrapEffect22.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect23))
                DuelMenu.spellTrapEffect23.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect24))
                DuelMenu.spellTrapEffect24.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect25))
                DuelMenu.spellTrapEffect25.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect26))
                DuelMenu.spellTrapEffect26.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect27))
                DuelMenu.spellTrapEffect27.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect28))
                DuelMenu.spellTrapEffect28.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect29))
                DuelMenu.spellTrapEffect29.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect30))
                DuelMenu.spellTrapEffect30.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect31))
                DuelMenu.spellTrapEffect31.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect32))
                DuelMenu.spellTrapEffect32.add(newCard);
            if (DuelMenu.isCardInArray(card, DuelMenu.spellTrapEffect33))
                DuelMenu.spellTrapEffect33.add(newCard);
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
        } else if (isHolderIcon) {
            icon = holder;
            isHolderIcon = false;
        } else if (isHolderStatus) {
            status = holder;
            isHolderStatus = false;
        } else if (isHolderDescription) {
            description = holder;
            isHolderDescription = false;
        } else if (isHolderSpellForEffectName) {
            cardForEffectName = holder;
            isHolderSpellForEffectName = false;
        }
    }

    @Override
    public void canceled() {

    }
}


