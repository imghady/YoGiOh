package model.user;

import model.card.Card;

import java.util.ArrayList;

public class SideDeck {
    private ArrayList<Card> sideDeckCards = new ArrayList<>();

    public ArrayList<Card> getSideDeckCards() {
        return sideDeckCards;
    }

    public int getSideDeckSize(){
        return sideDeckCards.size();
    }

    public void addCard(Card card){
        sideDeckCards.add(card);
    }

    public void removeCard(Card card){
        sideDeckCards.remove(card);
    }

}
