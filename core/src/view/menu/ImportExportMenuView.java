package view.menu;


import view.ScanInput;

import java.util.regex.Matcher;

public class ImportExportMenuView {

    public void importExportMenuRun() {
        String input;
        while (true) {
            input = ScanInput.getInput();
            if (input.matches("menu exit")) {
                break;
            }
        }
    }

    public void importCard(String input) {
        Matcher matcher;
        String cardName;
    }

    public void exportCard(String input) {
        Matcher matcher;
        String cardName;
    }

}
