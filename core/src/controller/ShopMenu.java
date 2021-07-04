package controller;

import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.user.User;
import view.TerminalOutput;

import java.util.ArrayList;

public class ShopMenu {

    private User currentUser;

    public ShopMenu(String username) {
        this.currentUser = User.getUserByUsername(username);
    }

    public ShopMenu() {

    }

    public void buyCard(String cardName) {
        if (Card.getCardByName(cardName) == null) {
            TerminalOutput.output("There is no card with this name");
            return;
        }
        Card card = Card.getCardByName(cardName);
        if (card.getPrice() > currentUser.getCredit()) {
            TerminalOutput.output("not enough money");
            return;
        }
        if (card instanceof Monster) {
            Monster monster = (Monster) card;
            Monster monster1 = new Monster(monster.getName(), monster.getLevel(), monster.getAttribute()
                    , monster.getMonsterType(), monster.getCardType(), monster.getAttack(), monster.getDefence(), monster.getDescription(), monster.getPrice());
            currentUser.addCard(monster1);
            TerminalOutput.output("Card Buy Successful.");
            return;
        }
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            Spell spell1 = new Spell(spell.getName(), spell.getIcon(), spell.getDescription(), spell.getStatus(), spell.getPrice());
            currentUser.addCard(spell1);
            TerminalOutput.output("Card Buy Successful.");
            return;
        }
        if (card instanceof Trap) {
            Trap trap = (Trap) card;
            Trap trap1 = new Trap(trap.getName(), trap.getIcon(), trap.getDescription(), trap.getStatus(), trap.getPrice());
            currentUser.addCard(trap1);
            TerminalOutput.output("Card Buy Successful.");
        }
    }

    public void showAllCard() {
        ArrayList<Card> allCard = Card.getAllCards();
        Card[] allCardForSort = new Card[allCard.size()];
        for (int i = 0; i < allCard.size(); i++) {
            allCardForSort[i] = allCard.get(i);
        }
        sortCardByName(allCardForSort);
        for (Card card : allCardForSort) {
            TerminalOutput.output(card.getName() + ":" + card.getPrice());
        }
    }

    public void sortCardByName(Card[] allCard) {
        for (int i = 0; i < allCard.length; i++) {
            int flagForEnd = 0;
            for (int j = i; j < allCard.length - 1; j++) {
                if (allCard[j].getName().compareTo(allCard[j + 1].getName()) > 0) {
                    Card holderUser = allCard[j];
                    allCard[j] = allCard[j + 1];
                    allCard[j + 1] = holderUser;
                    flagForEnd = 1;
                }
            }
            if (flagForEnd == 0)
                break;
        }
    }

}
