package view.menu;

import controller.ProfileMenu;
import view.CommandMatcher;
import view.ScanInput;
import view.TerminalOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuView {

    private String currentUsername;
    private ProfileMenu profileMenu;
    private boolean isChangePasswordInputCompressed = true;

    public ProfileMenuView(String currentUsername) {
        setCurrentUsername(currentUsername);
        profileMenu = new ProfileMenu(currentUsername);
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void profileMenuRun() {
        String input;
        while (true) {
            input = ScanInput.getInput();
            if (input.matches("menu exit")) {
                TerminalOutput.output("exit successfully!");
                break;
            } else if (input.matches("profile change (--nickname|-n) ([^ ]+)")) {
                profileChangeNickname(input);
            } else if (isChangePasswordInputValid(input)) {
                profileChangePassword(input);
            } else if (input.matches("menu show-current")) {
                TerminalOutput.output("Profile Menu");
            } else if (input.matches("menu enter (.+)")) {
                TerminalOutput.output("menu navigation is not possible");
            } else {
                TerminalOutput.output("invalid command");
            }
        }
    }

    public void profileChangeNickname(String input) {
        Matcher matcher = CommandMatcher.getCommandMatcher(input, "profile change (--nickname|-n) ([^ ]+)");
        String nickname = matcher.group(2);
        profileMenu.profileChangeNickname(nickname);
    }

    public void profileChangePassword(String input) {
        String newPassword;
        String currentPassword;
        if (isChangePasswordInputCompressed) {
            Matcher matcherForNewPassword = CommandMatcher.getCommandMatcher(input, "-n ([^ ]+)");
            newPassword = matcherForNewPassword.group(1);
            Matcher matcherForCurrentPassword = CommandMatcher.getCommandMatcher(input, "-c ([^ ]+)");
            currentPassword = matcherForCurrentPassword.group(1);
        } else {
            Matcher matcherForNewPassword = CommandMatcher.getCommandMatcher(input, "--new ([^ ]+)");
            newPassword = matcherForNewPassword.group(1);
            Matcher matcherForCurrentPassword = CommandMatcher.getCommandMatcher(input, "--current ([^ ]+)");
            currentPassword = matcherForCurrentPassword.group(1);
            setChangePasswordInputCompressed(true);
        }
        profileMenu.profileChangePassword(currentPassword, newPassword);
    }

    public boolean isChangePasswordInputValid(String changePasswordInput) {
        if (changePasswordInput.matches("^profile change (.+)")) {
            if (changePasswordInput.split(" ").length == 7) {
                if (isThisRegexExistInInput(changePasswordInput, "(--current [^ ]+)")) {
                    if (isThisRegexExistInInput(changePasswordInput, "(--password )")) {
                        if (isThisRegexExistInInput(changePasswordInput, "(--new [^ ]+)")) {
                            setChangePasswordInputCompressed(false);
                            return true;
                        }
                    }
                }
                if (isThisRegexExistInInput(changePasswordInput, "(-c [^ ]+)")) {
                    if (isThisRegexExistInInput(changePasswordInput, "(-p [^ ]+)")) {
                        return isThisRegexExistInInput(changePasswordInput, "(-n [^ ]+)");
                    }
                }
            }
        }
        return false;
    }

    private static boolean isThisRegexExistInInput(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }


    public boolean isChangePasswordInputCompressed() {
        return isChangePasswordInputCompressed;
    }

    public void setChangePasswordInputCompressed(boolean changePasswordInputCompressed) {
        isChangePasswordInputCompressed = changePasswordInputCompressed;
    }
}
