package controller;

import model.card.Card;
import model.user.User;
import view.TerminalOutput;

import java.util.ArrayList;

public class ShopMenu {

    private User currentUser;

    public ShopMenu (String username){
        this.currentUser=User.getUserByUsername(username);
    }

    public ShopMenu (){

    }

    public void buyCard(String cardName){
        if (Card.getCardByName(cardName) == null){
            TerminalOutput.output("There is no card with this name");
            return;
        }
        Card card=Card.getCardByName(cardName);
        if (card.getPrice()>currentUser.getCredit()){
            TerminalOutput.output("not enough money");
            return;
        }
        currentUser.addCard(card);
        TerminalOutput.output("Card Buy Successful.");
    }

    public void showAllCard(){
        ArrayList<Card> allCard=Card.getAllCards();
        Card[] allCardForSort=new Card[allCard.size()];
        for (int i = 0; i < allCard.size(); i++) {
            allCardForSort[i]=allCard.get(i);
        }
        sortCardByName(allCardForSort);
        for (Card card : allCardForSort) {
            TerminalOutput.output(card.getName() + ":" + card.getPrice());
        }
    }

    public void sortCardByName(Card[] allCard){
        for (int i = 0; i < allCard.length; i++) {
            int flagForEnd = 0;
            for (int j = i; j < allCard.length - 1; j++) {
                if (allCard[j].getName().compareTo(allCard[j+1].getName()) > 0) {
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
