package model.battle;

import controller.DuelMenu;
import model.card.Card;
import model.user.Deck;

import java.util.ArrayList;

public class Phase {

    private String currentPhase;
    private String terminalOutput;
    private DuelMenu duelMenu;

    public Phase(DuelMenu duelMenu) {
        currentPhase = "Draw Phase";
        setDuelMenu(duelMenu);
    }

    public void setDuelMenu(DuelMenu duelMenu) {
        this.duelMenu = duelMenu;
    }

    public void nextPhase() {
        if (currentPhase.equals("Draw Phase")) {
            currentPhase = "Standby Phase";
        }
        if (currentPhase.equals("Standby Phase")) {
            currentPhase = "First Main Phase";
        }
        if (currentPhase.equals("First Main Phase")) {
            currentPhase = "Battle Phase";
        }
        if (currentPhase.equals("Battle Phase")) {
            currentPhase = "Second Main Phase";
        }
        if (currentPhase.equals("Second Main Phase")) {
            currentPhase = "End Phase";
        }
        if (currentPhase.equals("End Phase")){
            currentPhase = "Draw Phase";
        }

    }


    public String getCurrentPhase() {
        return currentPhase;
    }

    public String drawPhase(Player player) {
        ArrayList<Card> cards=player.getMainDeckCard();
        if (player.getMat().isHandFull()){
            terminalOutput = "Hand is full";
            return terminalOutput;
        }
        player.getMat().addToHand(cards.get(cards.size()-1));
        terminalOutput = "new card added to hand: "+cards.get(cards.size()-1).getName();
        player.deleteCard();
        return terminalOutput;
    }

    public void standbyPhase(Player player) {

    }

    public void firstMainPhase(Player player) {

    }

    public void battlePhase(Player player) {

    }

    public void secondMainPhase(Player player) {

    }

    public String endPhase(Player player) {
        terminalOutput = "its "+player.getUser().getNickname()+"'s turn";
        duelMenu.changeTurn();
        return terminalOutput;
    }


}
