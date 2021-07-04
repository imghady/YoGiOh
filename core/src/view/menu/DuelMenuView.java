package view.menu;

import controller.DuelMenu;
import view.ScanInput;
import view.TerminalOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenuView {

    private String currentUsername;
    private DuelMenu duelMenu;

    public DuelMenuView(String username) {
        setCurrentUsername(username);

    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void duelMenuRun() {
        String input;
        Matcher matcher;
        while (true) {
            input = ScanInput.getInput();

            if ((matcher = getMatcher(input, "duel (--new|-n) (--second-player|-s) (?<secondPlayer>[\\w]+) (--rounds|-r) (?<rounds>[\\d]+)")).matches())
                newDuel(matcher);
            else if ((matcher = getMatcher(input, "duel (--new|-n) (--rounds|-r) (?<rounds>[\\d]+) (--second-player|-s) (?<secondPlayer>[\\w]+)")).matches())
                newDuel(matcher);
            else if ((matcher = getMatcher(input, "duel (--second-player|-s) (?<secondPlayer>[\\w]+) (--new|-n) (--rounds|-r) (?<rounds>[\\d]+)")).matches())
                newDuel(matcher);
            else if ((matcher = getMatcher(input, "duel (--second-player|-s) (?<secondPlayer>[\\w]+) (--rounds|-r) (?<rounds>[\\d]+) (--new|-n)")).matches())
                newDuel(matcher);
            else if ((matcher = getMatcher(input, "duel (--rounds|-r) (?<rounds>[\\d]+) (--new|-n) (--second-player|-s) (?<secondPlayer>[\\w]+)")).matches())
                newDuel(matcher);
            else if ((matcher = getMatcher(input, "duel (--rounds|-r) (?<rounds>[\\d]+) (--second-player|-s) (?<secondPlayer>[\\w]+) (--new|-n)")).matches())
                newDuel(matcher);

            else if ((matcher = getMatcher(input, "duel (--new|-n) (--ai|-a) (--rounds|-r) (?<rounds>[\\d]+)")).matches())
                newDuelWithAI(matcher);
            else if ((matcher = getMatcher(input, "duel (--new|-n) (--rounds|-r) (?<rounds>[\\d]+) (--ai|-a)")).matches())
                newDuelWithAI(matcher);
            else if ((matcher = getMatcher(input, "duel (--ai|-a) (--new|-n) (--rounds|-r) (?<rounds>[\\d]+)")).matches())
                newDuelWithAI(matcher);
            else if ((matcher = getMatcher(input, "duel (--ai|-a) (--rounds|-r) (?<rounds>[\\d]+) (--new|-n)")).matches())
                newDuelWithAI(matcher);
            else if ((matcher = getMatcher(input, "duel (--rounds|-r) (?<rounds>[\\d]+) (--new|-n) (--ai|-a)")).matches())
                newDuelWithAI(matcher);
            else if ((matcher = getMatcher(input, "duel (--rounds|-r) (?<rounds>[\\d]+) (--ai|-a) (--new|-n)")).matches())
                newDuelWithAI(matcher);

            else if ((matcher = getMatcher(input, "select (--monster|-m) (?<number>[\\d]+)(?<opponent> --opponent| -o|)")).matches())
                selectMonster(matcher);
            else if ((matcher = getMatcher(input, "select (--monster|-m)(?<opponent> --opponent| -o|) (?<number>[\\d]+)")).matches())
                selectMonster(matcher);
            else if ((matcher = getMatcher(input, "select (?<number>[\\d]+) (--monster|-m)(?<opponent> --opponent| -o|)")).matches())
                selectMonster(matcher);
            else if ((matcher = getMatcher(input, "select (?<number>[\\d]+)(?<opponent> --opponent| -o|) (--monster|-m)")).matches())
                selectMonster(matcher);
            else if ((matcher = getMatcher(input, "select(?<opponent> --opponent| -o|) (--monster|-m) (?<number>[\\d]+)")).matches())
                selectMonster(matcher);
            else if ((matcher = getMatcher(input, "select(?<opponent> --opponent| -o|) (?<number>[\\d]+) (--monster|-m)")).matches())
                selectMonster(matcher);

            else if ((matcher = getMatcher(input, "select (--spell|-s) (?<number>[\\d]+)(?<opponent> --opponent| -o|)")).matches())
                selectSpellOrTrap(matcher);
            else if ((matcher = getMatcher(input, "select (--spell|-s)(?<opponent> --opponent| -o|) (?<number>[\\d]+)")).matches())
                selectSpellOrTrap(matcher);
            else if ((matcher = getMatcher(input, "select (?<number>[\\d]+) (--spell|-s)(?<opponent> --opponent| -o|)")).matches())
                selectSpellOrTrap(matcher);
            else if ((matcher = getMatcher(input, "select (?<number>[\\d]+)(?<opponent> --opponent| -o|) (--spell|-s)")).matches())
                selectSpellOrTrap(matcher);
            else if ((matcher = getMatcher(input, "select(?<opponent> --opponent| -o|) (--spell|-s) (?<number>[\\d]+)")).matches())
                selectSpellOrTrap(matcher);
            else if ((matcher = getMatcher(input, "select(?<opponent> --opponent| -o|) (?<number>[\\d]+) (--spell|-s)")).matches())
                selectSpellOrTrap(matcher);

            else if ((matcher = getMatcher(input, "select (--field|-f) (?<number>[\\d]+)(?<opponent> --opponent| -o|)")).matches())
                selectField(matcher);
            else if ((matcher = getMatcher(input, "select (--field|-f)(?<opponent> --opponent| -o|) (?<number>[\\d]+)")).matches())
                selectField(matcher);
            else if ((matcher = getMatcher(input, "select (?<number>[\\d]+) (--field|-f)(?<opponent> --opponent| -o|)")).matches())
                selectField(matcher);
            else if ((matcher = getMatcher(input, "select (?<number>[\\d]+)(?<opponent> --opponent| -o|) (--field|-f)")).matches())
                selectField(matcher);
            else if ((matcher = getMatcher(input, "select(?<opponent> --opponent| -o|) (--field|-f) (?<number>[\\d]+)")).matches())
                selectField(matcher);
            else if ((matcher = getMatcher(input, "select(?<opponent> --opponent| -o|) (?<number>[\\d]+) (--field|-f)")).matches())
                selectField(matcher);

            else if ((matcher = getMatcher(input, "select (--hand|-h) (?<number>[\\d]+)")).matches())
                selectHand(matcher);
            else if ((matcher = getMatcher(input, "select (?<number>[\\d]+) (--hand|-h)")).matches())
                selectHand(matcher);

            else if ((getMatcher(input, "select -d")).matches())
                deSelectCard();

            else if ((getMatcher(input, "next phase")).matches())
                nextPhase();

            else if ((getMatcher(input, "summon")).matches())
                summon();

            else if ((getMatcher(input, "set")).matches())
                set();

            else if ((matcher = getMatcher(input, "set (-- position|--position|-p) (attack|defense)")).matches())
                changeCardPosition(matcher);

            else if ((getMatcher(input, "flip-summon")).matches())
                flipSummon();

            else if ((matcher = getMatcher(input, "attack ([\\d]+)")).matches())
                attack(matcher);

            else if ((getMatcher(input, "attack direct")).matches())
                directAttack();

            else if ((getMatcher(input, "activate effect")).matches())
                activeEffect();

            else if ((getMatcher(input, "show graveyard")).matches())
                showGraveyard();

            else if ((getMatcher(input, "card show (--selected|-s)")).matches())
                selectedCardShow();

            else if ((getMatcher(input, "surrender")).matches())
                surrender();

            else if ((matcher = getMatcher(input, "increase (--money|-m) ([\\d]+)")).matches())
                increaseMoney(matcher);

            else if ((matcher = getMatcher(input, "select (--hand|-h) (?<cardName>[\\w]+) (--force|-f)")).matches())
                selectForcedCard(matcher);
            else if ((matcher = getMatcher(input, "select (--hand|-h) (--force|-f) (?<cardName>[\\w]+)")).matches())
                selectForcedCard(matcher);
            else if ((matcher = getMatcher(input, "select (?<cardName>[\\w]+) (--hand|-h) (--force|-f)")).matches())
                selectForcedCard(matcher);
            else if ((matcher = getMatcher(input, "select (?<cardName>[\\w]+) (--force|-f) (--hand|-h)")).matches())
                selectForcedCard(matcher);
            else if ((matcher = getMatcher(input, "select (--force|-f) (--hand|-h) (?<cardName>[\\w]+)")).matches())
                selectForcedCard(matcher);
            else if ((matcher = getMatcher(input, "select (--force|-f) (?<cardName>[\\w]+) (--hand|-h)")).matches())
                selectForcedCard(matcher);

            else if ((matcher = getMatcher(input, "increase (--LP|-l) ([\\d]+)")).matches())
                increaseLifePoint(matcher);

            else if ((matcher = getMatcher(input, "duel set-winner ([\\w]+)")).matches())
                setWinner(matcher);

            else if ((matcher = getMatcher(input, "card show ([\\w]+)")).matches())
                cardShow(matcher);

            else if ((getMatcher(input, "menu enter [\\w]+")).matches())
                TerminalOutput.output("menu navigation is not possible");

            else if ((getMatcher(input, "menu exit")).matches())
                break;
            else if (input.matches("menu show-current")) {
                TerminalOutput.output("Duel Menu");
            }
            else
                TerminalOutput.output("invalid command");

            if (duelMenu.isDuelIsOn() && hasGameEnded()) {
                TerminalOutput.output(duelMenu.getTerminalOutput());
                break;
            }
            TerminalOutput.output(duelMenu.getTerminalOutput());
            duelMenu.showBoard();
            TerminalOutput.output(duelMenu.getTerminalOutput());

        }
    }


    public void cardShow(Matcher matcher) {
        String cardName = matcher.group(1);
        duelMenu.cardShow(cardName);
    }

    public void newDuel(Matcher matcher) {
        String secondPlayerUsername = matcher.group("secondPlayer");
        int rounds = Integer.parseInt(matcher.group("rounds"));
        duelMenu = new DuelMenu(currentUsername, secondPlayerUsername, rounds, false);
    }

    public void newDuelWithAI(Matcher matcher) {
        int rounds = Integer.parseInt(matcher.group("rounds"));
        duelMenu = new DuelMenu(currentUsername, "AI", rounds, true);
    }

    public void selectMonster(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number"));
        String opponent = matcher.group("opponent");
        boolean isOpponent = opponent.equals(" --opponent") || opponent.equals(" -o");
        duelMenu.selectMonster(number, isOpponent);
    }

    public void selectSpellOrTrap(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number"));
        String opponent = matcher.group("opponent");
        boolean isOpponent = opponent.equals(" --opponent") || opponent.equals(" -o");
        duelMenu.selectSpellOrTrap(number, isOpponent);
    }

    public void selectField(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number"));
        String opponent = matcher.group("opponent");
        boolean isOpponent = opponent.equals(" --opponent") || opponent.equals(" -o");
        duelMenu.selectField(number, isOpponent);
    }

    public void selectHand(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number"));
        duelMenu.selectHand(number);
    }

    public void deSelectCard() {
        duelMenu.deSelectCard();
    }

    public void nextPhase() {
        duelMenu.nextPhase();
    }

    public void summon() {
        duelMenu.summon();
    }

    public void set() {
        duelMenu.set();
    }

    public void changeCardPosition(Matcher matcher) {
        String mode = matcher.group(2);
        boolean isOnAttack = mode.equals("attack");
        duelMenu.changeCardPosition(isOnAttack);
    }

    public void flipSummon() {
        duelMenu.flipSummon();
    }

    public void attack(Matcher matcher) {
        int number = Integer.parseInt(matcher.group(1));
        duelMenu.attack(number);
    }

    public void directAttack() {
        duelMenu.directAttack();
    }

    public void activeEffect() {
        duelMenu.activeEffect();
    }

    public void ritualSummon() {
        //TODO
    }

    public void specialSummon() {
        //TODO
    }

    public void showGraveyard() {
        duelMenu.showGraveyard();
    }

    public void selectedCardShow() {
        duelMenu.selectedCardShow();
    }

    public boolean hasGameEnded() {
        return duelMenu.hasGameEnded();
    }

    public void surrender() {
        duelMenu.surrender();
    }

    public void increaseMoney(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group(2));
        duelMenu.increaseMoney(amount);
    }

    public void selectForcedCard(Matcher matcher) {
        String cardName = matcher.group("cardName");
        duelMenu.selectForcedCard(cardName);
    }

    public void increaseLifePoint(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group(2));
        duelMenu.increaseLifePoint(amount);
    }

    public void setWinner(Matcher matcher) {
        String nickname = matcher.group(1);
        duelMenu.setWinner(nickname);
    }

    private Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}