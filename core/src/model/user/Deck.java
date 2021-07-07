package model.user;

import java.util.ArrayList;
import java.util.Objects;

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

    public static ArrayList<Deck> getAllDecks() {
        return allDecks;
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

    public static void addToAllDeck(Deck deck){
        allDecks.add(deck);
    }

    public static Deck getDeckByName(String name, String creatorUsername){
//        for (Deck deck : allDecks){
//            if (deck.getName().equals(name) && deck.getCreatorUsername().equals(creatorUsername))
//                return deck;
//        }
        if (Objects.requireNonNull(User.getUserByUsername(creatorUsername)).getDecks().containsKey(name)) {
            return User.getUserByUsername(creatorUsername).getDecks().get(name);
        }
        return null;
    }
}
