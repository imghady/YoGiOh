
package view.menu;

import model.user.User;
import view.ScanInput;
import view.TerminalOutput;

import java.util.Objects;

public class MainMenuView {

    private String currentUserLoggedInUsername;

    public MainMenuView(String username) {
        this.currentUserLoggedInUsername = username;
    }

    public void mainMenuRun() {
        String input;
        while (true) {
            input = ScanInput.getInput();
            if (input.matches("user logout") || input.matches("menu exit")) {
                userLogout();
                break;
            } else if (input.matches("menu show-current")) {
                TerminalOutput.output("Main Menu");
            } else if (input.matches("menu enter (.+)")) {
                enterMenu(input);
            } else {
                TerminalOutput.output("invalid command");
            }
        }
    }

    public void userLogout() {
        Objects.requireNonNull(User.getUserByUsername(currentUserLoggedInUsername)).setUserLoggedIn(false);
        TerminalOutput.output("user logged out successfully!");
    }

    private void enterMenu(String input) {
        if (input.matches("menu enter main")) {
            TerminalOutput.output("you are in main menu now!");
        } else if (input.matches("menu enter deck")) {
            goDeckMenu();
        } else if (input.matches("menu enter duel")) {
            goDuelMenu();
        } else if (input.matches("menu enter import-export")) {
            goImportExportMenu();
        } else if (input.matches("menu enter profile")) {
            goProfileMenu();
        } else if (input.matches("menu enter scoreboard")) {
            goScoreboardMenu();
        } else if (input.matches("menu enter shop")) {
            goShopMenu();
        } else {
            TerminalOutput.output("invalid command");
        }
    }

    private void goDeckMenu() {
        TerminalOutput.output("enter deck menu successfully!");
        DeckMenuView deckMenuView = new DeckMenuView(currentUserLoggedInUsername);
        deckMenuView.deckMenuRun();
    }

    private void goDuelMenu() {
        TerminalOutput.output("enter duel menu successfully!");
        DuelMenuView duelMenuView = new DuelMenuView(currentUserLoggedInUsername);
        duelMenuView.duelMenuRun();
    }

    private void goImportExportMenu() {
        TerminalOutput.output("enter import-export menu successfully!");
        ImportExportMenuView importExportMenuView  = new ImportExportMenuView();
        importExportMenuView.importExportMenuRun();
    }

    private void goProfileMenu() {
        TerminalOutput.output("enter profile menu successfully!");
        ProfileMenuView  profileMenuView = new ProfileMenuView(currentUserLoggedInUsername);
        profileMenuView.profileMenuRun();
    }

    private void goScoreboardMenu() {
        TerminalOutput.output("enter scoreboard menu successfully!");
        ScoreboardMenuView scoreboardMenuView = new ScoreboardMenuView();
        scoreboardMenuView.scoreboardMenuRun();
    }

    private void goShopMenu() {
        TerminalOutput.output("enter shop menu successfully!");
        ShopMenuView shopMenuView = new ShopMenuView(currentUserLoggedInUsername);
        shopMenuView.shopMenuRun();
    }
}