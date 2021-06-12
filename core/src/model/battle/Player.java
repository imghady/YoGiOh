package model.battle;

import com.sun.tools.javac.Main;
import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.mat.Mat;
import model.user.MainDeck;
import model.user.User;
import view.TerminalOutput;

import javax.management.monitor.MonitorSettingException;
import java.lang.management.MonitorInfo;
import java.util.ArrayList;

public class Player {
    private User user;
    private ArrayList<Card> selectedCards = new ArrayList<>();
    private Card currentSelectedCard;
    private int numberOfMonsterZone;
    private Mat mat;
    private String selectedName;
    private boolean isSummoned = false;
    private int lifePoint;
    private int handNumber;
    protected ArrayList<Card> mainDeckCard = new ArrayList<>();


    public Player (User user){
        setUser(user);
        MainDeck mainDeck = user.getActiveDeck().getMainDeck();
        generateMainDeck(mainDeck);
        this.lifePoint=8000;
        mat=new Mat();
        currentSelectedCard=null;
    }

    public void generateMainDeck(MainDeck mainDeck){
        ArrayList<Card> cards=mainDeck.getMainDeckCards();
        for (Card card : cards) {
            if (card instanceof Monster){
                Monster monster = (Monster) card;
                Monster monster1 = new Monster(monster.getName(),monster.getLevel(),monster.getAttribute()
                        ,monster.getMonsterType(),monster.getCardType(),monster.getAttack(),monster.getDefence(),monster.getDescription(),monster.getPrice());
                mainDeckCard.add(monster1);
            }
            if (card instanceof Spell){
                Spell spell=(Spell) card;
                Spell spell1=new Spell(spell.getName(),spell.getIcon(),spell.getDescription(),spell.getStatus(),spell.getPrice());
                mainDeckCard.add(spell1);
            }
            if (card instanceof Trap){
                Trap trap=(Trap) card;
                Trap trap1 = new Trap(trap.getName(),trap.getIcon(),trap.getDescription(),trap.getStatus(),trap.getPrice());
                mainDeckCard.add(trap1);
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

    public void deleteCard(){
        mainDeckCard.remove(mainDeckCard.size()-1);
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

    public void changeLifePoint(int change){
        this.lifePoint+=change;
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

    public void addSelectedCard(Card card){
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
        if (player.getUser().getUsername().equals(this.user.getUsername())){
            return true;
        }
        return false;
    }


    public void changeScore(){

    }
}
