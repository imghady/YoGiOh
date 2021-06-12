package model.user;

import java.util.ArrayList;

public class Deck {
    private static ArrayList<Deck> allDecks = new ArrayList<>();
    private String name;
    private String creatorUsername;
    private MainDeck mainDeck = new MainDeck();
    private SideDeck sideDeck = new SideDeck();
    private boolean activeDeck;
    private boolean isValid = false;

    public Deck(String name, String creatorUsername){
        this.name = name;
        this.creatorUsername = creatorUsername;
        allDecks.add(this);
    }

    public String getName() {
        return name;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public MainDeck getMainDeck() {
        return mainDeck;
    }

    public SideDeck getSideDeck() {
        return sideDeck;
    }

    public void setActiveDeck(boolean activeDeck) {
        this.activeDeck = activeDeck;
    }

    public int getSideDeckSize(){
        return sideDeck.getSideDeckSize();
    }

    public int getMainDeckSize(){
        return mainDeck.getMainDeckSize();
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isActiveDeck(){
        return activeDeck;
    }


    public static Deck getDeckByName(String name, String creatorUsername){
        for (Deck deck : allDecks){
            if (deck.getName().equals(name) && deck.getCreatorUsername().equals(creatorUsername))
                return deck;
        }
        return null;
    }
}
