package view.graphicalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Mola;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import controller.DuelMenu;
import controller.GifDecoder;
import model.battle.Player;
import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.mat.Mat;
import model.user.User;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class Duel implements Screen, Input.TextInputListener {
    SpriteBatch batch;
    final Mola game;
    OrthographicCamera camera;
    Texture wallpaper;
    BitmapFont text;
    BitmapFont text1;
    BitmapFont text2;
    Texture mute;
    Texture unmute;
    Texture pause;
    Texture play;
    Texture agree;
    boolean isMute;
    boolean isAi;
    int rounds;
    Texture backButton;
    Texture changeMat;
    Texture changePhase;
    User currentLoggedInUser;
    Texture mat;
    Texture posht;
    Texture coin1;
    Texture coin2;
    Texture leftButtonBar;
    Texture rightButtonBar;
    Texture heart;
    Texture healthBar;
    Sound summon;
    Sound set;
    Sound direct;
    Sound gameOverSound;
    Sound changePhaseSound;
    Sound attack;
    DuelMenu duelMenu;
    float width = 110;
    float height = 160;
    float xGrave = 1130;
    float yGrave = 960 - 200 - width;
    float xField = 300;
    float yField = 960 - 200 - width;
    float xM1 = 400;
    float xM2 = 570;
    float xM3 = 750;
    float xM4 = 920;
    float xM5 = 1090;
    float yM = 960 - 340 - height;
    float xS1 = 400;
    float xS2 = 570;
    float xS3 = 750;
    float xS4 = 920;
    float xS5 = 1090;
    float yS = 960 - 545 - height;
    float xH1 = 400;
    float xH2 = 570;
    float xH3 = 750;
    float xH4 = 920;
    float xH5 = 1090;
    float xH6 = 1260;
    float yH = 960 - 750 - height;
    float elapsed;
    int attackInput = -1;
    boolean gif1ShouldPlay = false;
    boolean gif2ShouldPlay = false;
    boolean gif3ShouldPlay = false;
    boolean gif4ShouldPlay = false;
    boolean coinShouldPlay = true;
    boolean isPaused = false;
    boolean showedCoin = false;
    long gif1Time;
    long gif2Time;
    long gif3Time;
    long gif4Time;
    long coinTime;
    Player showingPlayer;
    String message = "";
    String phaseMessage = "";
    String charPic1;
    String charPic2;
    String currentButtonClicked = "";
    Animation<TextureRegion> gif1;
    Animation<TextureRegion> gif2;
    Animation<TextureRegion> gif3;
    Animation<TextureRegion> gif4;
    Animation<TextureRegion> coin;
    Animation<TextureRegion> gameOver;
    Animation<TextureRegion> background1;
    Animation<TextureRegion> background2;
    Animation<TextureRegion> background3;
    Animation<TextureRegion> background4;
    Texture pic1;
    Texture pic2;
    String holder = "";
    String cheatInput = "";
    boolean isHolderAttackInput;
    boolean isHolderCheatInput = false;

    public Duel(Mola game, boolean isMute, User currentLoggedInUser, boolean isAi, String secondUserUsername, int rounds) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        summon = Gdx.audio.newSound(Gdx.files.internal("sound/summon.wav"));
        set = Gdx.audio.newSound(Gdx.files.internal("sound/set.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sound/gameOver.mp3"));
        direct = Gdx.audio.newSound(Gdx.files.internal("sound/direct.wav"));
        changePhaseSound = Gdx.audio.newSound(Gdx.files.internal("sound/changePhase.wav"));
        attack = Gdx.audio.newSound(Gdx.files.internal("sound/attack.wav"));
        text = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text2 = new BitmapFont(Gdx.files.internal("Agency.fnt"));
        text1 = new BitmapFont(Gdx.files.internal("times.fnt"));
        wallpaper = new Texture("wallpaper.jpg");
        coin1 = new Texture("coin1.png");
        coin2 = new Texture("coin2.png");
        posht = new Texture("posht.jpg");
        mute = new Texture("buttons/mute.png");
        unmute = new Texture("buttons/unmute.png");
        backButton = new Texture("buttons/back.png");
        agree = new Texture("buttons/agree.png");
        heart = new Texture("heart.png");
        healthBar = new Texture("healthBar.png");
        changeMat = new Texture("buttons/changeMat.png");
        pause = new Texture("buttons/pause.png");
        play = new Texture("buttons/play.png");
        leftButtonBar = new Texture("buttons/leftButtonBar.png");
        rightButtonBar = new Texture("buttons/rightButtonBar.png");
        changePhase = new Texture("buttons/changePhase.png");
        gif1 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/gif1.gif").read());
        gif2 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/gif2.gif").read());
        gif3 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/gif3.gif").read());
        gif4 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/gif4.gif").read());
        coin = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/coin.gif").read());
        gameOver = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/gameOver.gif").read());
        background1 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/background.gif").read());
        background2 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/gif5.gif").read());
        background3 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/gif6.gif").read());
        background4 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gifs/gif7.gif").read());
        coinTime = System.currentTimeMillis();
        this.isAi = isAi;
        this.rounds = rounds;
        mat = new Texture("mat.png");
        String firstUser = currentLoggedInUser.getUsername();
        String secondUser = secondUserUsername;
        duelMenu = new DuelMenu(firstUser, secondUser, rounds, isAi);
        showingPlayer = duelMenu.currentTurnPlayer;
        charPic1 = currentLoggedInUser.getCharacterFileAddress();
        pic1 = new Texture(charPic1);
        User user = User.getUserByUsername(secondUserUsername);
        charPic2 = user.getCharacterFileAddress();
        pic2 = new Texture(charPic2);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (showedCoin) {
            batch.begin();

            int field = fieldCardType();
            if (field == 0)
                batch.draw(background1.getKeyFrame(elapsed), 0, -200, 1600, 1160);
            if (field == 1)
                batch.draw(background2.getKeyFrame(elapsed), 0, -200, 1600, 1160);
            if (field == 2)
                batch.draw(background3.getKeyFrame(elapsed), 0, -200, 1600, 1160);
            if (field == 3)
                batch.draw(background4.getKeyFrame(elapsed), 0, -200, 1600, 1160);
            text.getData().setScale(0.15f);
            text1.draw(batch, "la nature est l'eglise de satan...", 1200, 30);
            if (duelMenu.currentTurnPlayer.currentSelectedCard != null) {
                text1.draw(batch, "Selected card: " + duelMenu.currentTurnPlayer.currentSelectedCard.getName(), 600, 700);
            }
            text2.getData().setScale(0.2f);
            text2.setColor(Color.YELLOW);
            text2.draw(batch, "Showing player username: " + showingPlayer.getUser().getUsername(), 110, 930);
            text2.draw(batch, "nickname: " + showingPlayer.getUser().getNickname(), 100, 880);
            if (showingPlayer == duelMenu.firstPlayer) {
                batch.draw(pic1, 50, 600, 200, 250);
            } else {
                batch.draw(pic2, 50, 600, 200, 250);
            }
            text2.draw(batch, message, 590, 800);
            batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
            batch.draw(changeMat, 1500, 50);
            batch.draw(leftButtonBar, 50, 250);
            batch.draw(rightButtonBar, 1550 - rightButtonBar.getWidth(), 250);
            batch.draw(agree, 100, 100, 200, 100);
            batch.draw(mat, 300, 250);
            batch.draw(changePhase, 1320, 650, 250, 100);

            batch.draw(healthBar, 650, 830, showingPlayer.getLifePoint() / 8000f * 600, 40);
            batch.draw(heart, 600, 800, 100, 100);
            batch.draw(pause, 10, 100);
            text.draw(batch, phaseMessage, 700, 650);

            batch.end();
            loadMonsters();
            loadSpells();
            loadGraveyard();
            loadField();
            loadHand();

            batch.begin();
            if (gif1ShouldPlay) {
                batch.draw(gif1.getKeyFrame(elapsed), xM1, yH, xM5 - xM1 + width, yM - yH + height);
                if (System.currentTimeMillis() - gif1Time >= gif1.getAnimationDuration() * 1000) {
                    gif1ShouldPlay = false;
                }
            }
            if (gif2ShouldPlay) {
                batch.draw(gif2.getKeyFrame(elapsed), xM1, yH, xM5 - xM1 + width, yM - yH + height);
                if (System.currentTimeMillis() - gif2Time >= gif2.getAnimationDuration() * 1000) {
                    gif2ShouldPlay = false;
                }
            }
            if (gif3ShouldPlay) {
                batch.draw(gif3.getKeyFrame(elapsed), xM1, yH, xM5 - xM1 + width, yM - yH + height);
                if (System.currentTimeMillis() - gif3Time >= gif3.getAnimationDuration() * 1000) {
                    gif3ShouldPlay = false;
                }
            }
            if (gif4ShouldPlay) {
                batch.draw(gif4.getKeyFrame(elapsed), xM1, yH, xM5 - xM1 + width, yM - yH + height);
                if (System.currentTimeMillis() - gif4Time >= gif4.getAnimationDuration() * 1000) {
                    gif4ShouldPlay = false;
                }
            }

            if (isPaused) {
                batch.draw(play, 400, 200, 800, 660);
            }
            batch.end();

            if (Gdx.input.isKeyPressed(Input.Keys.C) && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                isHolderCheatInput = true;
                Gdx.input.getTextInput(this, "enter cheat", "", "");
            }


            if (Gdx.input.justTouched()) {

                float x = Gdx.input.getX();
                float y = Gdx.input.getY();

                if (isPaused) {
                    if (x > 400 && x < 1200 && y > 200 && y < 760) {
                        isPaused = false;
                    }
                } else {
                    handleCardSelection(x, y);

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

                    if (x >= 80 && x <= 280 && y >= 760 && y <= 860)
                        agree();

                    if (x >= 1500 && x <= 1500 + changeMat.getWidth() && y <= 910 && y >= 910 - changeMat.getHeight()) {
                        if (showingPlayer == duelMenu.firstPlayer)
                            showingPlayer = duelMenu.secondPlayer;
                        else
                            showingPlayer = duelMenu.firstPlayer;
                    }

                    if (x > 10 && x < 10 + pause.getWidth() && y < 860 && y > 860 - pause.getHeight()) {
                        isPaused = true;
                    }


                    batch.begin();
                    int leftBarHeight = leftButtonBar.getHeight();
                    if (x >= 50 && x <= 50 + leftButtonBar.getWidth()) {
                        if (y < 710 && y > 710 - leftBarHeight / 4f) {
                            message = duelMenu.phase2DirectAttack();
                            gif1ShouldPlay = true;
                            gif1Time = System.currentTimeMillis();
                            if (!isMute)
                                direct.play();
                        }
                        if (y < 710 - leftBarHeight / 4f && y > 710 - 2f * leftBarHeight / 4) {
                            isHolderAttackInput = true;
                            Gdx.input.getTextInput(this, "Card number", "", "");
                            currentButtonClicked = "attack";
                            if (!isMute)
                                attack.play();

                        }
                        if (y < 710 - 2f * leftBarHeight / 4 && y > 710 - 3f * leftBarHeight / 4) {
                            //SUMMON
                            message = duelMenu.phase2Summon();
                            gif3ShouldPlay = true;
                            gif3Time = System.currentTimeMillis();
                            if (!isMute)
                                summon.play();
                        }
                        if (y < 710 - 3f * leftBarHeight / 4 && y > 710 - 4f * leftBarHeight / 4) {
                            //SET
                            message = duelMenu.phase2Set();
                            if (!isMute)
                                set.play();
                        }
                    }

                    if (x >= 1550 - rightButtonBar.getWidth() && x <= 1550) {
                        if (y < 710 && y > 710 - leftBarHeight / 4f) {
                            //CHANGE POSITION
                        }
                        if (y < 710 - leftBarHeight / 4f && y > 710 - 2f * leftBarHeight / 4) {
                            //SURRENDER
                        }
                        if (y < 710 - 2f * leftBarHeight / 4 && y > 710 - 3f * leftBarHeight / 4) {
                            //FLIP SUMMON
                        }
                        if (y < 710 - 3f * leftBarHeight / 4 && y > 710 - 4f * leftBarHeight / 4) {
                            //ACTIVE EFFECT
                            message = duelMenu.phase2ActiveEffect();
                            gif4ShouldPlay = true;
                            gif4Time = System.currentTimeMillis();
                        }
                    }

                    if (x >= 1320 && x <= 1570 && y >= 210 && y <= 310) {
                        if (!isMute)
                            changePhaseSound.play();
                        changePhase();
                    }
                    batch.end();
                }


            }
            if (!cheatInput.isEmpty()) {
                if (cheatInput.matches("increase (--money|-m) ([\\d]+)")) {
                    Matcher matcher = duelMenu.getMatcher(cheatInput, "increase (--money|-m) ([\\d]+)");
                    matcher.find();
                    duelMenu.increaseMoney(Integer.parseInt(matcher.group(1)));
                }
                else if (cheatInput.matches("increase (--LP|-l) ([\\d]+)")) {
                    Matcher matcher = duelMenu.getMatcher(cheatInput, "increase (--LP|-l) ([\\d]+)");
                    matcher.find();
                    duelMenu.increaseLifePoint(Integer.parseInt(matcher.group(1)));
                }
                else if (cheatInput.matches("duel set-winner ([\\w+])")) {
                    Matcher matcher = duelMenu.getMatcher(cheatInput, "duel set-winner ([\\w+])");
                    matcher.find();
                    duelMenu.setWinner(matcher.group(1));
                }
                else
                    cheatInput = "";
            }
        } else {
            batch.begin();
            batch.draw(wallpaper, 0, 0, 1600, 960);
            if (coinShouldPlay) {
                batch.draw(coin.getKeyFrame(elapsed), 500, 300 , 550, 400);
                if (System.currentTimeMillis() - coinTime > 2000) {
                    coinShouldPlay = false;
                }
            } else {
                text.getData().setScale(0.3f);
                if (duelMenu.currentTurnPlayer == duelMenu.firstPlayer) {
                    batch.draw(coin1, 700, 400, 200, 200);
                    text.draw(batch, "First player starts", 600, 700);
                } else {
                    batch.draw(coin2, 700, 400, 200, 200);
                    text.draw(batch, "Second player starts", 600, 700);
                }
                batch.draw(agree, 700, 200);
                if (Gdx.input.justTouched()) {
                    float x = Gdx.input.getX();
                    float y = Gdx.input.getY();

                    if (x >= 700 && x <= 700 + agree.getWidth() && y <= 760 && y >= 760 - agree.getHeight()) {
                        showedCoin = true;
                    }
                }
            }
            batch.end();
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

    private void changePhase() {
        phaseMessage = duelMenu.phase2NextPhase();
        System.out.println(phaseMessage);
    }

    private void agree() {
        if (currentButtonClicked.equals("attack")) {
            if (attackInput != -1)
                message = duelMenu.phase2Attack(attackInput);
            gif2ShouldPlay = true;
            gif2Time = System.currentTimeMillis();
            attackInput = -1;
            currentButtonClicked = "";
        }
    }

    private void loadHand() {
        batch.begin();
        Mat mat = showingPlayer.getMat();
        for (int i = 0; i < 6; i++) {
            if (mat.getHandCard(i) != null) {
                Card card = mat.getHandCard(i);
                String address = getCardImageFileAddress(card.getName());
                Texture texture = new Texture(Gdx.files.internal(address + ".jpg"));
                if (showingPlayer == duelMenu.currentTurnPlayer) {
                    if (i == 0)
                        batch.draw(texture, xH1, yH, width, height);
                    if (i == 1)
                        batch.draw(texture, xH2, yH, width, height);
                    if (i == 2)
                        batch.draw(texture, xH3, yH, width, height);
                    if (i == 3)
                        batch.draw(texture, xH4, yH, width, height);
                    if (i == 4)
                        batch.draw(texture, xH5, yH, width, height);
                    if (i == 5)
                        batch.draw(texture, xH6, yH, width, height);
                }
            }
        }
        batch.end();
    }

    private void handleCardSelection(float x, float y) {
        if (x > xM1 && x < xM1 + width && y < 960 - yM && y > 960 - yM - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getMonsterZone(0);
            duelMenu.currentTurnPlayer.setSelectedName("Monster");
        }
        if (x > xM2 && x < xM2 + width && y < 960 - yM && y > 960 - yM - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getMonsterZone(1);
            duelMenu.currentTurnPlayer.setSelectedName("Monster");
        }
        if (x > xM3 && x < xM3 + width && y < 960 - yM && y > 960 - yM - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getMonsterZone(2);
            duelMenu.currentTurnPlayer.setSelectedName("Monster");
        }
        if (x > xM4 && x < xM4 + width && y < 960 - yM && y > 960 - yM - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getMonsterZone(3);
            duelMenu.currentTurnPlayer.setSelectedName("Monster");
        }
        if (x > xM5 && x < xM5 + width && y < 960 - yM && y > 960 - yM - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getMonsterZone(4);
            duelMenu.currentTurnPlayer.setSelectedName("Monster");
        }
        if (x > xS1 && x < xS1 + width && y < 960 - yS && y > 960 - yS - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getSpellAndTrapZone(0);
            duelMenu.currentTurnPlayer.setSelectedName("Spell");
        }
        if (x > xS2 && x < xS2 + width && y < 960 - yS && y > 960 - yS - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getSpellAndTrapZone(1);
            duelMenu.currentTurnPlayer.setSelectedName("Spell");
        }
        if (x > xS3 && x < xS3 + width && y < 960 - yS && y > 960 - yS - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getSpellAndTrapZone(2);
            duelMenu.currentTurnPlayer.setSelectedName("Spell");
        }
        if (x > xS4 && x < xS4 + width && y < 960 - yS && y > 960 - yS - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getSpellAndTrapZone(3);
            duelMenu.currentTurnPlayer.setSelectedName("Spell");
        }
        if (x > xS5 && x < xS5 + width && y < 960 - yS && y > 960 - yS - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getSpellAndTrapZone(4);
            duelMenu.currentTurnPlayer.setSelectedName("Spell");
        }
        if (x > xH1 && x < xH1 + width && y < 960 - yH && y > 960 - yH - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getHandCard(0);
            duelMenu.currentTurnPlayer.setHandNumber(1);
            duelMenu.currentTurnPlayer.setSelectedName("Hand");
        }
        if (x > xH2 && x < xH2 + width && y < 960 - yH && y > 960 - yH - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getHandCard(1);
            duelMenu.currentTurnPlayer.setHandNumber(2);
            duelMenu.currentTurnPlayer.setSelectedName("Hand");
        }
        if (x > xH3 && x < xH3 + width && y < 960 - yH && y > 960 - yH - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getHandCard(2);
            duelMenu.currentTurnPlayer.setHandNumber(3);
            duelMenu.currentTurnPlayer.setSelectedName("Hand");
        }
        if (x > xH4 && x < xH4 + width && y < 960 - yH && y > 960 - yH - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getHandCard(3);
            duelMenu.currentTurnPlayer.setHandNumber(4);
            duelMenu.currentTurnPlayer.setSelectedName("Hand");
        }
        if (x > xH5 && x < xH5 + width && y < 960 - yH && y > 960 - yH - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getHandCard(4);
            duelMenu.currentTurnPlayer.setHandNumber(5);
            duelMenu.currentTurnPlayer.setSelectedName("Hand");
        }
        if (x > xH6 && x < xH6 + width && y < 960 - yH && y > 960 - yH - height) {
            duelMenu.currentTurnPlayer.currentSelectedCard = duelMenu.currentTurnPlayer.getMat().getHandCard(5);
            duelMenu.currentTurnPlayer.setHandNumber(6);
            duelMenu.currentTurnPlayer.setSelectedName("Hand");
        }
    }

    private int fieldCardType() {
        Mat mat = showingPlayer.getMat();
        Card card = mat.getFieldZone();
        if (card == null)
            return 0;
        if (card instanceof Monster)
            return 1;
        if (card instanceof Spell)
            return 2;
        if (card instanceof Trap)
            return 3;
        return -1;
    }

    private void loadField() {
        Mat mat = showingPlayer.getMat();
        Card card = mat.getFieldZone();
        if (card != null) {
            String address = getCardImageFileAddressLandscape(card.getName());
            Texture texture = new Texture(Gdx.files.internal(address + ".jpg"));
            batch.begin();
            batch.draw(texture, xField, yField, height, width);
            batch.end();
        }
    }

    private void loadGraveyard() {
        ArrayList<Card> graveyard = duelMenu.currentTurnPlayer.getMat().getGraveyard();
        if (!graveyard.isEmpty()) {
            Card card = graveyard.get(graveyard.size() - 1);
            String address = getCardImageFileAddressLandscape(card.getName());
            Texture texture = new Texture(Gdx.files.internal(address + ".jpg"));
            batch.begin();
            batch.draw(texture, xGrave, yGrave, height, width);
            batch.end();
        }
    }

    private void loadSpells() {
        batch.begin();
        Mat mat = showingPlayer.getMat();
        for (int i = 0; i < 5; i++) {
            if (mat.getSpellAndTrapZone(i) != null) {
                Card card = mat.getSpellAndTrapZone(i);
                String address = getCardImageFileAddress(card.getName());
                Texture texture;
                if (card.isOn())
                    texture = new Texture(Gdx.files.internal(address + ".jpg"));
                else
                    texture = new Texture(Gdx.files.internal("posht.jpg"));
                if (i == 0)
                    batch.draw(texture, xS1, yS, width, height);
                if (i == 1)
                    batch.draw(texture, xS2, yS, width, height);
                if (i == 2)
                    batch.draw(texture, xS3, yS, width, height);
                if (i == 3)
                    batch.draw(texture, xS4, yS, width, height);
                if (i == 4)
                    batch.draw(texture, xS5, yS, width, height);
            }
        }
        batch.end();
    }

    private void loadMonsters() {
        batch.begin();
        Mat mat = showingPlayer.getMat();
        for (int i = 0; i < 5; i++) {
            if (mat.getMonsterZone(i) != null) {
                Card card = mat.getMonsterZone(i);
                String address = getCardImageFileAddress(card.getName());
                Texture texture;
                if (card.isOn())
                    texture = new Texture(Gdx.files.internal(address + ".jpg"));
                else
                    texture = new Texture(Gdx.files.internal("posht.jpg"));
                if (i == 0)
                    batch.draw(texture, xM1, yM, width, height);
                if (i == 1)
                    batch.draw(texture, xM2, yM, width, height);
                if (i == 2)
                    batch.draw(texture, xM3, yM, width, height);
                if (i == 3)
                    batch.draw(texture, xM4, yM, width, height);
                if (i == 4)
                    batch.draw(texture, xM5, yM, width, height);
            }
        }
        batch.end();
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

    public String getCardImageFileAddress(String input) {
        if (input.equals("Battle OX"))
            return "cards/monsters/BattleOx";
        if (input.equals("Axe Raider"))
            return "cards/monsters/AxeRaider";
        if (input.equals("Yomi Ship"))
            return "cards/monsters/YomiShip";
        if (input.equals("Horn Imp"))
            return "cards/monsters/HornImp";
        if (input.equals("Silver Fang"))
            return "cards/monsters/SilverFang";
        if (input.equals("Suijin"))
            return "cards/monsters/Suijin";
        if (input.equals("Fireyarou"))
            return "cards/monsters/Fireyarou";
        if (input.equals("Curtain of the dark ones"))
            return "cards/monsters/CurtainOfTheDarkOnes";
        if (input.equals("Feral Imp"))
            return "cards/monsters/FeralImp";
        if (input.equals("Dark magician"))
            return "cards/monsters/DarkMagician";
        if (input.equals("Wattkid"))
            return "cards/monsters/Wattkid";
        if (input.equals("Baby dragon"))
            return "cards/monsters/BabyDragon";
        if (input.equals("Hero of the east"))
            return "cards/monsters/HeroOfTheEast";
        if (input.equals("Battle warrior"))
            return "cards/monsters/BattleWarrior";
        if (input.equals("Crawling dragon"))
            return "cards/monsters/CrawlingDragon";
        if (input.equals("Flame manipulator"))
            return "cards/monsters/FlameManipulator";
        if (input.equals("Blue-Eyes white dragon"))
            return "cards/monsters/BlueEyesWhiteDragon";
        if (input.equals("Crab Turtle"))
            return "cards/monsters/CrabTurtle";
        if (input.equals("Skull Guardian"))
            return "cards/monsters/SkullGuardian";
        if (input.equals("Slot Machine"))
            return "cards/monsters/SlotMachine";
        if (input.equals("Haniwa"))
            return "cards/monsters/Haniwa";
        if (input.equals("Man-Eater Bug"))
            return "cards/monsters/ManEaterBug";
        if (input.equals("Gate Guardian"))
            return "cards/monsters/GateGuardian";
        if (input.equals("Scanner"))
            return "cards/monsters/Scanner";
        if (input.equals("Bitron"))
            return "cards/monsters/Bitron";
        if (input.equals("Marshmallon"))
            return "cards/monsters/Marshmallon";
        if (input.equals("Beast King Barbaros"))
            return "cards/monsters/BeastKingBarbaros";
        if (input.equals("Texchanger"))
            return "cards/monsters/Texchanger";
        if (input.equals("Leotron ") || input.equals("Leotron"))
            return "cards/monsters/Leotron";
        if (input.equals("The Calculator"))
            return "cards/monsters/TheCalculator";
        if (input.equals("Alexandrite Dragon"))
            return "cards/monsters/AlexandriteDragon";
        if (input.equals("Mirage Dragon"))
            return "cards/monsters/MirageDragon";
        if (input.equals("Herald of Creation"))
            return "cards/monsters/HeraldOfCreation";
        if (input.equals("Exploder Dragon"))
            return "cards/monsters/ExploderDragon";
        if (input.equals("Warrior Dai Grepher"))
            return "cards/monsters/WarriorDaiGrepher";
        if (input.equals("Dark Blade"))
            return "cards/monsters/DarkBlade";
        if (input.equals("Wattaildragon"))
            return "cards/monsters/Wattaildragon";
        if (input.equals("Terratiger# the Empowered Warrior") || input.equals("Terratiger"))
            return "cards/monsters/Terratiger";
        if (input.equals("The Tricky"))
            return "cards/monsters/TheTricky";
        if (input.equals("Spiral Serpent"))
            return "cards/monsters/SpiralSerpent";
        if (input.equals("Command Knight"))
            return "cards/monsters/CommandKnight";
        if (input.equals("Trap Hole"))
            return "cards/SpellTrap/TrapHole";
        if (input.equals("Mirror Force"))
            return "cards/SpellTrap/MirrorForce";
        if (input.equals("Magic Cylinder"))
            return "cards/SpellTrap/MagicCylinder";
        if (input.equals("Mind Crush"))
            return "cards/SpellTrap/MindCrush";
        if (input.equals("Torrential Tribute"))
            return "cards/SpellTrap/TorrentialTribute";
        if (input.equals("Time Seal"))
            return "cards/SpellTrap/TimeSeal";
        if (input.equals("Negate Attack"))
            return "cards/SpellTrap/NegateAttack";
        if (input.equals("Solemn Warning"))
            return "cards/SpellTrap/SolemnWarning";
        if (input.equals("Magic Jamamer") || input.equals("Magic Jammer"))
            return "cards/SpellTrap/Magic Jammer";
        if (input.equals("Call of The Haunted"))
            return "cards/SpellTrap/Call of the Hunted";
        if (input.equals("Vanity's Emptiness"))
            return "cards/SpellTrap/VanitysEmptiness";
        if (input.equals("Wall of Revealing Light"))
            return "cards/SpellTrap/WallOfRevealingLight";
        if (input.equals("Monster Reborn"))
            return "cards/SpellTrap/MonsterReborn";
        if (input.equals("Terraforming"))
            return "cards/SpellTrap/Terraforming";
        if (input.equals("Pot of Greed"))
            return "cards/SpellTrap/PotOfGreed";
        if (input.equals("Raigeki"))
            return "cards/SpellTrap/Raigeki";
        if (input.equals("Change of Heart"))
            return "cards/SpellTrap/ChangeOfHeart";
        if (input.equals("Swords of Revealing Light"))
            return "cards/SpellTrap/SwordOfRevealingLight";
        if (input.equals("Harpie's Feather Duster"))
            return "cards/SpellTrap/HarpiesFeatherDuster";
        if (input.equals("Dark Hole"))
            return "cards/SpellTrap/DarkHole";
        if (input.equals("Supply Squad"))
            return "cards/SpellTrap/SupplySquad";
        if (input.equals("Spell Absorption"))
            return "cards/SpellTrap/SpellAbsorption";
        if (input.equals("Messenger of peace"))
            return "cards/SpellTrap/MessengerOfPeace";
        if (input.equals("Twin Twisters"))
            return "cards/SpellTrap/TwinTwisters";
        if (input.equals("Mystical space typhoon"))
            return "cards/SpellTrap/MysticalSpaceTyphoon";
        if (input.equals("Ring of defense"))
            return "cards/SpellTrap/RingOfDefense";
        if (input.equals("Yami"))
            return "cards/SpellTrap/Yami";
        if (input.equals("Forest"))
            return "cards/SpellTrap/Forest";
        if (input.equals("Closed Forest"))
            return "cards/SpellTrap/ClosedForest";
        if (input.equals("Umiiruka"))
            return "cards/SpellTrap/Umiiruka";
        if (input.equals("Sword of dark destruction"))
            return "cards/SpellTrap/SwordOfDarkDestruction";
        if (input.equals("Black Pendant"))
            return "cards/SpellTrap/BlackPendant";
        if (input.equals("United We Stand"))
            return "cards/SpellTrap/UnitedWeStand";
        if (input.equals("Magnum Shield"))
            return "cards/SpellTrap/MagnumShield";
        if (input.equals("Advanced Ritual Art"))
            return "cards/SpellTrap/AdvancedRitualArt";
        return null;
    }

    public String getCardImageFileAddressLandscape(String input) {
        if (input.equals("Battle OX"))
            return "cards/monsters/landscape/BattleOx";
        if (input.equals("Axe Raider"))
            return "cards/monsters/landscape/AxeRaider";
        if (input.equals("Yomi Ship"))
            return "cards/monsters/landscape/YomiShip";
        if (input.equals("Horn Imp"))
            return "cards/monsters/landscape/HornImp";
        if (input.equals("Silver Fang"))
            return "cards/monsters/landscape/SilverFang";
        if (input.equals("Suijin"))
            return "cards/monsters/landscape/Suijin";
        if (input.equals("Fireyarou"))
            return "cards/monsters/landscape/Fireyarou";
        if (input.equals("Curtain of the dark ones"))
            return "cards/monsters/landscape/CurtainOfTheDarkOnes";
        if (input.equals("Feral Imp"))
            return "cards/monsters/landscape/FeralImp";
        if (input.equals("Dark magician"))
            return "cards/monsters/landscape/DarkMagician";
        if (input.equals("Wattkid"))
            return "cards/monsters/landscape/Wattkid";
        if (input.equals("Baby dragon"))
            return "cards/monsters/landscape/BabyDragon";
        if (input.equals("Hero of the east"))
            return "cards/monsters/landscape/HeroOfTheEast";
        if (input.equals("Battle warrior"))
            return "cards/monsters/landscape/BattleWarrior";
        if (input.equals("Crawling dragon"))
            return "cards/monsters/landscape/CrawlingDragon";
        if (input.equals("Flame manipulator"))
            return "cards/monsters/landscape/FlameManipulator";
        if (input.equals("Blue-Eyes white dragon"))
            return "cards/monsters/landscape/BlueEyesWhiteDragon";
        if (input.equals("Crab Turtle"))
            return "cards/monsters/landscape/CrabTurtle";
        if (input.equals("Skull Guardian"))
            return "cards/monsters/landscape/SkullGuardian";
        if (input.equals("Slot Machine"))
            return "cards/monsters/landscape/SlotMachine";
        if (input.equals("Haniwa"))
            return "cards/monsters/landscape/Haniwa";
        if (input.equals("Man-Eater Bug"))
            return "cards/monsters/landscape/ManEaterBug";
        if (input.equals("Gate Guardian"))
            return "cards/monsters/landscape/GateGuardian";
        if (input.equals("Scanner"))
            return "cards/monsters/landscape/Scanner";
        if (input.equals("Bitron"))
            return "cards/monsters/landscape/Bitron";
        if (input.equals("Marshmallon"))
            return "cards/monsters/landscape/Marshmallon";
        if (input.equals("Beast King Barbaros"))
            return "cards/monsters/landscape/BeastKingBarbaros";
        if (input.equals("Texchanger"))
            return "cards/monsters/landscape/Texchanger";
        if (input.equals("Leotron ") || input.equals("Leotron"))
            return "cards/monsters/landscape/Leotron";
        if (input.equals("The Calculator"))
            return "cards/monsters/landscape/TheCalculator";
        if (input.equals("Alexandrite Dragon"))
            return "cards/monsters/landscape/AlexandriteDragon";
        if (input.equals("Mirage Dragon"))
            return "cards/monsters/landscape/MirageDragon";
        if (input.equals("Herald of Creation"))
            return "cards/monsters/landscape/HeraldOfCreation";
        if (input.equals("Exploder Dragon"))
            return "cards/monsters/landscape/ExploderDragon";
        if (input.equals("Warrior Dai Grepher"))
            return "cards/monsters/landscape/WarriorDaiGrepher";
        if (input.equals("Dark Blade"))
            return "cards/monsters/landscape/DarkBlade";
        if (input.equals("Wattaildragon"))
            return "cards/monsters/landscape/Wattaildragon";
        if (input.equals("Terratiger# the Empowered Warrior") || input.equals("Terratiger"))
            return "cards/monsters/landscape/Terratiger";
        if (input.equals("The Tricky"))
            return "cards/monsters/landscape/TheTricky";
        if (input.equals("Spiral Serpent"))
            return "cards/monsters/landscape/SpiralSerpent";
        if (input.equals("Command Knight"))
            return "cards/monsters/landscape/CommandKnight";
        if (input.equals("Trap Hole"))
            return "cards/SpellTrap/landscape/TrapHole";
        if (input.equals("Mirror Force"))
            return "cards/SpellTrap/landscape/MirrorForce";
        if (input.equals("Magic Cylinder"))
            return "cards/SpellTrap/landscape/MagicCylinder";
        if (input.equals("Mind Crush"))
            return "cards/SpellTrap/landscape/MindCrush";
        if (input.equals("Torrential Tribute"))
            return "cards/SpellTrap/landscape/TorrentialTribute";
        if (input.equals("Time Seal"))
            return "cards/SpellTrap/landscape/TimeSeal";
        if (input.equals("Negate Attack"))
            return "cards/SpellTrap/landscape/NegateAttack";
        if (input.equals("Solemn Warning"))
            return "cards/SpellTrap/landscape/SolemnWarning";
        if (input.equals("Magic Jamamer") || input.equals("Magic Jammer"))
            return "cards/SpellTrap/landscape/Magic Jammer";
        if (input.equals("Call of The Haunted"))
            return "cards/SpellTrap/landscape/Call of the Hunted";
        if (input.equals("Vanity's Emptiness"))
            return "cards/SpellTrap/landscape/VanitysEmptiness";
        if (input.equals("Wall of Revealing Light"))
            return "cards/SpellTrap/landscape/WallOfRevealingLight";
        if (input.equals("Monster Reborn"))
            return "cards/SpellTrap/landscape/MonsterReborn";
        if (input.equals("Terraforming"))
            return "cards/SpellTrap/landscape/Terraforming";
        if (input.equals("Pot of Greed"))
            return "cards/SpellTrap/landscape/PotOfGreed";
        if (input.equals("Raigeki"))
            return "cards/SpellTrap/landscape/Raigeki";
        if (input.equals("Change of Heart"))
            return "cards/SpellTrap/landscape/ChangeOfHeart";
        if (input.equals("Swords of Revealing Light"))
            return "cards/SpellTrap/landscape/SwordOfRevealingLight";
        if (input.equals("Harpie's Feather Duster"))
            return "cards/SpellTrap/landscape/HarpiesFeatherDuster";
        if (input.equals("Dark Hole"))
            return "cards/SpellTrap/landscape/DarkHole";
        if (input.equals("Supply Squad"))
            return "cards/SpellTrap/landscape/SupplySquad";
        if (input.equals("Spell Absorption"))
            return "cards/SpellTrap/landscape/SpellAbsorption";
        if (input.equals("Messenger of peace"))
            return "cards/SpellTrap/landscape/MessengerOfPeace";
        if (input.equals("Twin Twisters"))
            return "cards/SpellTrap/landscape/TwinTwisters";
        if (input.equals("Mystical space typhoon"))
            return "cards/SpellTrap/landscape/MysticalSpaceTyphoon";
        if (input.equals("Ring of defense"))
            return "cards/SpellTrap/landscape/RingOfDefense";
        if (input.equals("Yami"))
            return "cards/SpellTrap/landscape/Yami";
        if (input.equals("Forest"))
            return "cards/SpellTrap/landscape/Forest";
        if (input.equals("Closed Forest"))
            return "cards/SpellTrap/landscape/ClosedForest";
        if (input.equals("Umiiruka"))
            return "cards/SpellTrap/landscape/Umiiruka";
        if (input.equals("Sword of dark destruction"))
            return "cards/SpellTrap/landscape/SwordOfDarkDestruction";
        if (input.equals("Black Pendant"))
            return "cards/SpellTrap/landscape/BlackPendant";
        if (input.equals("United We Stand"))
            return "cards/SpellTrap/landscape/UnitedWeStand";
        if (input.equals("Magnum Shield"))
            return "cards/SpellTrap/landscape/MagnumShield";
        if (input.equals("Advanced Ritual Art"))
            return "cards/SpellTrap/landscape/AdvancedRitualArt";
        return null;
    }

    @Override
    public void input(String text) {
        this.holder = text;

        if (isHolderAttackInput) {
            this.attackInput = Integer.parseInt(holder);
            isHolderAttackInput = false;
        } else if (isHolderCheatInput) {
            cheatInput = holder;
            isHolderCheatInput = false;
        }


    }

    @Override
    public void canceled() {

    }
}
