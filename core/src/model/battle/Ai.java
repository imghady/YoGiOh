package model.battle;

import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.mat.Mat;
import model.user.Deck;
import model.user.MainDeck;
import model.user.User;
import view.TerminalOutput;

import java.util.ArrayList;
import java.util.Random;

public class Ai {
    private Player opponent;
    private Player ai;
    private Deck deck;
    private ArrayList<Card> mainDeck = new ArrayList<>();

    public Ai(Player opponent, Player ai) {
        this.opponent = opponent;
        this.ai = ai;
    }

    public void setDeck() {
        deck = ai.getUser().getActiveDeck();
        ArrayList<Card> cards = deck.getMainDeck().getMainDeckCards();
        for (Card card : cards) {
            if (card.getCardType().equals("Spell")) {
                Card spell = card;
                Spell spell1 = new Spell(spell.getName(), spell.getIcon(), spell.getDescription(), spell.getStatus(), spell.getPrice());
                mainDeck.add(spell1);
            }
            if (card.getCardType().equals("Trap")) {
                Card trap = card;
                Trap trap1 = new Trap(trap.getName(), trap.getIcon(), trap.getDescription(), trap.getStatus(), trap.getPrice());
                mainDeck.add(trap1);
            } else {
                Card monster =card;
                Monster monster1 = new Monster(monster.getName(), monster.getLevel(), monster.getAttribute()
                        , monster.getMonsterType(), monster.getCardType(), monster.getAttack(), monster.getDefence(), monster.getDescription(), monster.getPrice());
                mainDeck.add(monster1);

            }
        }
    }

    public void doTurn() {
        for (int i = 0; i < 5; i++) {
            if (ai.getMat().getMonsterZone(i) == null) {
                for (Card card : mainDeck) {
                    if (card instanceof Monster) {
                        ai.getMat().setMonsterZone(i, (Monster) card);
                        break;
                    }
                }
                break;
            }
        }
        Card card = ai.getMat().getMonsterZone(0);
        for (int i = 0; i < 5; i++) {
            if (ai.getMat().getMonsterZone(i) != null && ai.getMat().getMonsterZone(i).getAttack() > card.getAttack()) {
                card = ai.getMat().getMonsterZone(i);
            }
        }
        boolean doesOpponentHaveMonster = false;
        for (int i = 0; i < 5; i++) {
            if (opponent.getMat().getMonsterZone(i) != null) {
                doesOpponentHaveMonster = true;
            }
        }
        if (!doesOpponentHaveMonster) {
            System.out.println("you opponent receives " + card.getAttack() + " battle damage");
            opponent.setLifePoint(opponent.getLifePoint() - card.getAttack());
        }
    }

}
