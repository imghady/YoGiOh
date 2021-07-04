package model.card;

import java.util.ArrayList;

public class Spell extends Card{

    protected static ArrayList<Spell> allSpells = new ArrayList<>();




    public Spell(String name, String icon, String description, String status, int price) {
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.status = status;
        this.price = price;
        this.cardType = "Spell";
        allSpells.add(this);
        Card.addToCards(this);
    }

    public static Spell getSpellByName(String name) {
        for (Spell spell : allSpells) {
            if (spell.name.equals(name)){
                return spell;
            }
        }
        return null;
    }


}
