package model.card;

import java.util.ArrayList;

public class Monster extends Card{
    protected static ArrayList<Monster> allMonsters = new ArrayList<>();
    protected int level;
    protected String attribute;
    protected String monsterType;

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

    public String getMonsterType() {
        return monsterType;
    }

    public String getAttribute() {
        return attribute;
    }

    public int getLevel(){
        return level;
    }

    public static Monster getMonsterByName(String name) {
        for (Monster monster : allMonsters) {
            if (monster.name.equals(name)){
                return monster;
            }
        }
        return null;
    }

}


