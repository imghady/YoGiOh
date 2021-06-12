package view.menu;

import controller.ScoreboardMenu;
import view.CommandMatcher;
import view.ScanInput;
import view.TerminalOutput;

import java.util.regex.Matcher;

public class ScoreboardMenuView {

    public void scoreboardMenuRun() {
        String input;
        while (true) {
            input = ScanInput.getInput();
            if (input.matches("menu enter [\\w]+"))
                TerminalOutput.output("menu navigation is not possible");
            else if (input.matches("menu exit"))
                break;
            else if (input.matches("menu show-current"))
                TerminalOutput.output("Scoreboard Menu");
            else if (input.matches("scoreboard show"))
                showBoard();
            else
                TerminalOutput.output("Invalid Command!\nPlease Try Again");
        }
    }

    public void showBoard() {
        ScoreboardMenu scoreboardMenu = new ScoreboardMenu();
        scoreboardMenu.sortUsersByScore();
    }

}
