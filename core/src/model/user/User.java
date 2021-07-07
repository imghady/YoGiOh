package model.user;

import model.card.Card;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private static ArrayList<User> allUsers = new ArrayList<>();

    private String username;
    private String password;
    private String nickname;
    private int score;
    private Deck activeDeck = null;
    private HashMap<String, Deck> decks;
    private ArrayList<Card> cards;
    private int credit;
    private boolean isUserLoggedIn = false;
    public String characterFileAddress = "characters/Char4.png";

    public User(String username, String nickname, String password) {
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        setScore(0);
        cards = new ArrayList<>();
        decks = new HashMap<>();
        setCredit(100000);
        allUsers.add(this);
        JSONObject newUser = new JSONObject();
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("nickname", nickname);
        JSONObject userDecks = new JSONObject();
        newUser.put("decks", userDecks);
        String fileAddress = "resources/users/" + username + ".json";
        try (FileWriter file = new FileWriter(fileAddress)) {
            file.write(newUser.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUserByNickname(String nickname) {
        for (User user : allUsers) {
            if (user.getNickname().equals(nickname)) {
                return user;
            }
        }
        return null;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void addToAllUsers(User user) {
        allUsers.add(user);
    }


    public void setCredit(int credit) {
        this.credit = credit;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCredit() {
        return credit;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public void addCard(Card card) {
        cards.add(card);
        setCredit(this.credit - card.getPrice());
    }

    public void addDeck(String name, Deck deck) {
        this.decks.put(name, deck);
    }

    public void deleteDeck(String name) {
        this.decks.remove(name);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Deck getActiveDeck() {
//        ArrayList<Deck> decks = Deck.getAllDecks();
//        for (Deck deck : decks) {
//            if (activeDeck.getName().equals(deck.getName()) && username.equals(deck.getCreatorUsername())) {
//                activeDeck = deck;
//                break;
//            }
//        }
        return activeDeck;
    }

    public HashMap<String, Deck> getDecks() {
        return decks;
    }

    public void deleteCard(String name){
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(name)){
                cards.remove(i);
                return;
            }
        }
    }


    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
    }

    public String getCharacterFileAddress() {
        return characterFileAddress;
    }

    public void setCharacterFileAddress(String characterFileAddress) {
        this.characterFileAddress = characterFileAddress;
    }

    public int showNumberOfCard(String cardName) {
        int cnt = 0;
        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                cnt++;
            }
        }
        return cnt;
    }
}
