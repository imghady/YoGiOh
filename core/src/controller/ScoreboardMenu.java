package controller;

import model.user.User;
import view.TerminalOutput;

import java.util.ArrayList;

public class ScoreboardMenu {

    public void sortUsersByScore() {
        ArrayList<User> allUsers = User.getAllUsers();
        User[] allUsersForSort = allUsers.toArray(new User[0]);
        for (int i = 0; i < allUsersForSort.length; i++) {
            int flagForEnd = 0;
            for (int j = 0; j < allUsersForSort.length - 1; j++) {
                if (allUsersForSort[j].getScore() < allUsersForSort[j + 1].getScore()
                        || (allUsersForSort[j].getScore() == allUsersForSort[j + 1].getScore() &&
                        allUsersForSort[j].getNickname().compareTo(allUsersForSort[j + 1].getNickname()) > 0)) {
                    User holderUser = allUsersForSort[j];
                    allUsersForSort[j] = allUsersForSort[j + 1];
                    allUsersForSort[j + 1] = holderUser;
                    flagForEnd = 1;
                }
            }
            if (flagForEnd == 0)
                break;
        }
        printScoreboard(allUsersForSort);
    }

    private void printScoreboard(User[] allUserSorted) {
        int rankCounter = 2;
        int rankHolder = 1;
        TerminalOutput.output("1- "+toStringForOneUser(allUserSorted[0]));
        for (int i = 1; i < allUserSorted.length; i++) {
            if (allUserSorted[i].getScore()==allUserSorted[i-1].getScore())
                TerminalOutput.output(rankHolder+"- "+toStringForOneUser(allUserSorted[i]));
            else {
                TerminalOutput.output(rankCounter + "- " + toStringForOneUser(allUserSorted[i]));
                rankHolder=rankCounter;
            }
            rankCounter+=1;
        }
    }

    private String toStringForOneUser(User user){
        return user.getNickname()+": "+user.getScore();
    }
}
