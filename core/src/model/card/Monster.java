package model.card;

import java.util.ArrayList;

public class Monster extends Card{

    public Monster(String name, int level, String attribute, String monsterType, String cardType, int attack, int defence, String description, int price) {
        this.name = name;
        this.level = level;
        this.attribute = attribute;
        this.monsterType = monsterType;
        this.cardType = cardType;
        this.attack = attack;
        this.defence = defence;
        this.description =description;
        this.price = price;
        allMonsters.add(this);
        Card.addToCards(this);
    }

    public boolean isAttack() {
        return isAttack;
    }


}


