package model.battle;

import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.mat.Mat;
import model.user.MainDeck;
import model.user.User;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private User user;
    private ArrayList<Card> selectedCards = new ArrayList<>();
    private Card currentSelectedCard;
    private int numberOfMonsterZone;
    private Mat mat;
    private String selectedName;
    private boolean isSummoned = false;
    public boolean isSpell = false;
    public boolean isPermissionForDrawPhase = true;
    private int lifePoint;
    private int handNumber;
    protected ArrayList<Card> mainDeckCard = new ArrayList<>();

    public void setPermissionForDrawPhase(boolean permissionForDrawPhase) {
        isPermissionForDrawPhase = permissionForDrawPhase;
    }

    public void isPermissionForDrawPhase(boolean isPermissionForDrawPhase) {
        this.isPermissionForDrawPhase = isPermissionForDrawPhase;
    }

    public int getSizeOfDeck(){
        return mainDeckCard.size();
    }

    public Player(User user) {
        setUser(user);
        MainDeck mainDeck = user.getActiveDeck().getMainDeck();
        generateMainDeck(mainDeck);
        this.lifePoint = 8000;
        mat = new Mat();
        currentSelectedCard = null;
        Collections.shuffle(mainDeckCard);
        for (int i = 0; i < 5; i++) {
            this.getMat().addToHand(mainDeckCard.get(mainDeckCard.size()-1));
            this.deleteCard();
        }
    }

    public void generateMainDeck(MainDeck mainDeck) {
        ArrayList<Card> cards = mainDeck.getMainDeckCards();
        for (Card card : cards) {
            if (card.getCardType().equals("Spell")) {
                Card spell = card;
                Spell spell1 = new Spell(spell.getName(), spell.getIcon(), spell.getDescription(), spell.getStatus(), spell.getPrice());
                mainDeckCard.add(spell1);
            }
            if (card.getCardType().equals("Trap")) {
                Card trap = card;
                Trap trap1 = new Trap(trap.getName(), trap.getIcon(), trap.getDescription(), trap.getStatus(), trap.getPrice());
                mainDeckCard.add(trap1);
            } else {
                Card monster =card;
                Monster monster1 = new Monster(monster.getName(), monster.getLevel(), monster.getAttribute()
                        , monster.getMonsterType(), monster.getCardType(), monster.getAttack(), monster.getDefence(), monster.getDescription(), monster.getPrice());
                mainDeckCard.add(monster1);

            }
        }
    }

    public void setHandNumber(int handNumber) {
        this.handNumber = handNumber;
    }

    public void setSummoned(boolean summoned) {
        isSummoned = summoned;
    }


    public ArrayList<Card> getMainDeckCard() {
        return mainDeckCard;
    }

    public void deleteCard() {
        mainDeckCard.remove(mainDeckCard.size() - 1);
    }

    public User getUser() {
        return user;
    }

    public void setNumberOfMonsterZone(int numberOfMonsterZone) {
        this.numberOfMonsterZone = numberOfMonsterZone;
    }

    public int getNumberOfMonsterZone() {
        return numberOfMonsterZone;
    }

    public void changeLifePoint(int change) {
        this.lifePoint += change;
    }

    public Mat getMat() {
        return mat;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public int getHandNumber() {
        return handNumber;
    }

    public String getSelectedName() {
        return selectedName;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public void setCurrentSelectedCard(Card currentSelectedCard) {
        this.currentSelectedCard = currentSelectedCard;
        selectedCards.add(currentSelectedCard);
    }

    public void addSelectedCard(Card card) {
        selectedCards.add(card);
    }

    public Card getCurrentSelectedCard() {
        return currentSelectedCard;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public boolean isSummoned() {
        return isSummoned;
    }

    public boolean isEqual(Player player) {
        if (player.getUser().getUsername().equals(this.user.getUsername())) {
            return true;
        }
        return false;
    }


    public void setSpell(boolean spell) {
        this.isSpell = spell;
    }

    public ArrayList<Card> getSelectedCards() {
        return selectedCards;
    }

    public void changeScore() {

    }
}
