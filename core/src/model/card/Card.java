package model.card;

import java.util.ArrayList;
import java.util.Comparator;

public class Card {
    private static ArrayList<Card> cards = new ArrayList<>();
    protected String name;
    protected String cardType;
    protected String description;
    protected String id;
    protected String ownerUsername;
    protected int price;
    protected boolean isField = false;
    protected boolean isAttack = false;
    protected boolean isOn;
    protected int attack;
    protected int defence;
    protected boolean isFirstEffectUse = true;
    protected boolean isFirstTimeFlipping = false;

    public boolean isFirstTimeFlipping() {
        return isFirstTimeFlipping;
    }

    public void setFirstTimeFlipping(boolean firstTimeFlipping) {
        isFirstTimeFlipping = firstTimeFlipping;
    }

    public boolean isAttack() {
        return isAttack;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getAttack() {
        return attack;
    }

    public String getCardType() {
        return cardType;
    }

    public boolean isFirstEffectUse() {
        return isFirstEffectUse;
    }

    public void setFirstEffectUse(boolean firstEffectUse) {
        isFirstEffectUse = firstEffectUse;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public boolean isField() {
        return isField;
    }

    public void setField(boolean field) {
        isField = field;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Card> getAllCards() {
        return cards;
    }

    public static Comparator<Card> nameComparator = Comparator.comparing(card -> card.name);

    public String getType() {
        return cardType;
    }

    public void setType(String type) {
        this.cardType = type;
    }

    public static Card getCardByName(String name) {
        for (Card card : cards) {
            if (card.getName().equals(name))
                return card;
        }
        return null;
    }

    public static String showCard(String name) {
        String cardInfo = "Name : ";
        Card card = getCardByName(name);
        assert card != null;
        if (card.cardType.equals("Spell")) {
            Spell spell = Spell.getSpellByName(name);
            cardInfo += spell.getName() + "\n" + "Spell" + "\n";
            cardInfo += "Type : " + spell.icon + "\n";
            cardInfo += "Description : " + spell.getDescription() + "\n";
        } else if (card.cardType.equals("Trap")) {
            Trap trap = Trap.getTrapByName(name);
            cardInfo += trap.getName() + "\n" + "Trap" + "\n";
            cardInfo += "Type : " + trap.icon + "\n";
            cardInfo += "Description : " + trap.getDescription() + "\n";
        } else {
            Monster monster = Monster.getMonsterByName(name);
            cardInfo += monster.getName() + "\n";
            cardInfo += "Level : " + monster.level + "\n";
            cardInfo += "Type : " + monster.monsterType + "\n";
            cardInfo += "ATK : " + monster.attack + "\n";
            cardInfo += "DEF : " + monster.defence + "\n";
            cardInfo += "Description : " + monster.getDescription() + "\n";
        }
        return cardInfo;
    }

    public static void addToCards(Card card) {
        cards.add(card);
    }

}
