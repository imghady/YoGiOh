package view.menu;

import controller.ShopMenu;
import model.card.Card;
import view.CommandMatcher;
import view.ScanInput;
import view.TerminalOutput;

import java.util.regex.Matcher;

public class ShopMenuView {

    private String currentUserLoggedInUsername;

    public ShopMenuView(String currentUserLoggedInUsername) {
        setCurrentUserLoggedInUsername(currentUserLoggedInUsername);
    }

    public void setCurrentUserLoggedInUsername(String currentUserLoggedInUsername) {
        this.currentUserLoggedInUsername = currentUserLoggedInUsername;
    }

    public void shopMenuRun() {
        String input;
        while (true) {
            input = ScanInput.getInput();

            if (input.matches("menu exit")) {
                TerminalOutput.output("exit successfully!");
                break;
            } else if (input.matches("shop buy [\\w ]+")) {
                buyCard(input);
            } else if (input.matches("shop show --all") || input.matches("shop show -a"))
                showAll();
            else if (input.matches("card show [a-zA-Z\\s]+")) {
                cardShow(input);
            } else {
                TerminalOutput.output("Invalid Command!\nPlease Try Again");
            }
        }
    }

    public void showAll() {
        ShopMenu shopMenu = new ShopMenu();
        shopMenu.showAllCard();
    }

    public void cardShow(String input) {
        Matcher matcher = CommandMatcher.getCommandMatcher(input, "card show ([a-zA-Z\\s]+)");
        assert matcher != null;
        String cardName = matcher.group(1);
        TerminalOutput.output(Card.showCard(cardName));
    }

    public void buyCard(String input) {
        Matcher matcher = CommandMatcher.getCommandMatcher(input, "shop buy ([\\w ]+)");
        String cardName = matcher.group(1);
        ShopMenu shopMenu = new ShopMenu(currentUserLoggedInUsername);
        shopMenu.buyCard(cardName);

    }

}
