package view.menu;

import controller.DeckMenu;
import controller.ShopMenu;
import model.user.Deck;
import model.user.User;
import view.ScanInput;
import view.TerminalOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenuView {

    private DeckMenu deckMenu;
    private String loggedInUser;

    public DeckMenuView(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        deckMenu = new DeckMenu(loggedInUser);
    }

    public void deckMenuRun() {
        String input;
        Matcher matcher;
        while (true) {
            input = ScanInput.getInput();
            if ((matcher = getMatcher(input, "deck create ([\\w]+)")).matches())
                createDeck(matcher);

            else if ((matcher = getMatcher(input, "deck delete ([\\w]+)")).matches())
                deleteDeck(matcher);

            else if ((matcher = getMatcher(input, "deck set-activate ([\\w]+)")).matches())
                setActive(matcher);

            else if ((matcher = getMatcher(input, "deck add-card (--card|-c) (?<card>[\\w ]+) (--deck|-d) (?<deck>[\\w]+)(?<side>( --side| -s|))")).matches())
                addCardToDeck(matcher);
            else if ((matcher = getMatcher(input, "deck add-card (--card|-c) (?<card>[\\w ]+)(?<side>( --side| -s|)) (--deck|-d) (?<deck>[\\w]+)")).matches())
                addCardToDeck(matcher);
            else if ((matcher = getMatcher(input, "deck add-card (--deck|-d) (?<deck>[\\w]+) (--card|-c) (?<card>[\\w ]+)(?<side>( --side| -s|))")).matches())
                addCardToDeck(matcher);
            else if ((matcher = getMatcher(input, "deck add-card (--deck|-d) (?<deck>[\\w]+)(?<side>( --side| -s|)) (--card|-c) (?<card>[\\w ]+)")).matches())
                addCardToDeck(matcher);
            else if ((matcher = getMatcher(input, "deck add-card(?<side>( --side| -s|))(--card|-c) (?<card>[\\w ]+) (--deck|-d) (?<deck>[\\w]+)")).matches())
                addCardToDeck(matcher);
            else if ((matcher = getMatcher(input, "deck add-card(?<side>( --side| -s|)) (--deck|-d) (?<deck>[\\w]+) (--card|-c) (?<card>[\\w ]+)")).matches())
                addCardToDeck(matcher);

            else if ((matcher = getMatcher(input, "deck rm-card (--card|-c) (?<card>[\\w ]+) (--deck|-d) (?<deck>[\\w]+)(?<side>( --side| -s|))")).matches())
                removeCardFromDeck(matcher);
            else if ((matcher = getMatcher(input, "deck rm-card (--card|-c) (?<card>[\\w ]+)(?<side>( --side| -s|)) (--deck|-d) (?<deck>[\\w]+)")).matches())
                removeCardFromDeck(matcher);
            else if ((matcher = getMatcher(input, "deck rm-card (--deck|-d) (?<deck>[\\w]+) (--card|-c) (?<card>[\\w ]+)(?<side>( --side| -s|))")).matches())
                removeCardFromDeck(matcher);
            else if ((matcher = getMatcher(input, "deck rm-card (--deck|-d) (?<deck>[\\w]+)(?<side>( --side| -s|)) (--card|-c) (?<card>[\\w ]+)")).matches())
                removeCardFromDeck(matcher);
            else if ((matcher = getMatcher(input, "deck rm-card(?<side>( --side| -s|)) (--card|-c) (?<card>[\\w ]+) (--deck|-d) (?<deck>[\\w]+)")).matches())
                removeCardFromDeck(matcher);
            else if ((matcher = getMatcher(input, "deck rm-card(?<side>( --side| -s|)) (--deck|-d) (?<deck>[\\w]+) (--card|-c) (?<card>[\\w ]+)")).matches())
                removeCardFromDeck(matcher);

            else if (input.matches("deck show --all") || input.matches("deck show -a"))
                showAllDeck();

            else if ((matcher = getMatcher(input, "deck show --(deck-name|d) (?<deckName>[\\w]+)(?<side> --side| -s|)")).matches())
                showOneDeck(matcher);
            else if ((matcher = getMatcher(input, "deck show(?<side> --side| -s|) --(deck-name|d) (?<deckName>[\\w]+)")).matches())
                showOneDeck(matcher);

            else if (input.matches("deck show --cards") || input.matches("deck show -c"))
                showAllCards();

            else if ((matcher = getMatcher(input, "card show ([\\w]+)")).matches()) {
                cardShow(matcher);
            }

            else if ((getMatcher(input, "menu enter [\\w]+")).matches())
                TerminalOutput.output("menu navigation is not possible");

            else if ((getMatcher(input, "menu show-current")).matches())
                TerminalOutput.output("deck menu");

            else if ((getMatcher(input, "menu exit")).matches()) {
                TerminalOutput.output("exit successfully!");
                break;
            }

            else
                TerminalOutput.output("invalid command");

            TerminalOutput.output(deckMenu.getTerminalOutput());
        }
    }

    public void cardShow(Matcher matcher) {
        String cardName = matcher.group(1);
        deckMenu.cardShow(cardName);
    }

    public void createDeck(Matcher matcher) {
        String deckName = matcher.group(1);
        deckMenu.createDeck(deckName);
    }

    public void deleteDeck(Matcher matcher) {
        String deckName = matcher.group(1);
        deckMenu.deleteDeck(deckName);
    }

    public void setActive(Matcher matcher) {
        String deckName = matcher.group(1);
        deckMenu.setActive(deckName);
    }

    public void addCardToDeck(Matcher matcher) {
        String deckName = matcher.group("deck");
        String cardName = matcher.group("card");
        String sideDeck = matcher.group("side");
        boolean isSideDeck = sideDeck.equals(" --side") || sideDeck.equals(" -s");
        deckMenu.addCardToDeck(deckName, cardName, isSideDeck);
    }

    public void removeCardFromDeck(Matcher matcher) {
        String deckName = matcher.group("deck");
        String cardName = matcher.group("card");
        String sideDeck = matcher.group("side");
        boolean isSideDeck = sideDeck.equals(" --side") || sideDeck.equals(" -s");
        deckMenu.removeCardFromDeck(deckName, cardName, isSideDeck);
    }

    public void showAllDeck() {
        deckMenu.showAllDeck();
    }

    public void showOneDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        String sideDeck = matcher.group("side");
        boolean isSideDeck = sideDeck.equals(" --side") || sideDeck.equals(" -s");
        deckMenu.showOneDeck(deckName, isSideDeck);
    }

    public void showAllCards() {
        deckMenu.showAllCards();
    }

    private Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
