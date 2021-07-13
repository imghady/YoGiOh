package controller;

import com.google.gson.Gson;
import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import view.TerminalOutput;

import java.io.IOException;
import java.util.ArrayList;

public class ShopMenu {

    private User currentUser;
    private JSONParser parser = new JSONParser();

    public ShopMenu(String username) {
        this.currentUser = User.getUserByUsername(username);
    }

    public ShopMenu() {

    }

    public String buyCard(String cardName) {
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("command","shop");
        jsonObject.put("cardName",cardName);
        jsonObject.put("token",AppClient.getToken());
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            String result = AppClient.dataInputStream.readUTF();
            String[] data = result.split("#####");
            jsonObject = (JSONObject) parser.parse(data[0]);
            if (jsonObject.get("type").equals("Error")){
                return jsonObject.get("message").toString();
            }
            else if (jsonObject.get("type").equals("monster")){
                System.out.println("kir1");
                Gson gson = new Gson();
                currentUser.addCard(gson.fromJson(data[1],Monster.class));
                return "success";
            }else if (jsonObject.get("type").equals("trap")){
                System.out.println("kir2");
                Gson gson = new Gson();
                currentUser.addCard(gson.fromJson(data[1],Trap.class));
                return "success";
            }else if (jsonObject.get("type").equals("spell")){
                System.out.println("kir3");
                Gson gson = new Gson();
                currentUser.addCard(gson.fromJson(data[1],Spell.class));
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void showAllCard() {
        ArrayList<Card> allCard = Card.getAllCards();
        Card[] allCardForSort = new Card[allCard.size()];
        for (int i = 0; i < allCard.size(); i++) {
            allCardForSort[i] = allCard.get(i);
        }
        sortCardByName(allCardForSort);
        for (Card card : allCardForSort) {
            TerminalOutput.output(card.getName() + ":" + card.getPrice());
        }
    }

    public void sortCardByName(Card[] allCard) {
        for (int i = 0; i < allCard.length; i++) {
            int flagForEnd = 0;
            for (int j = i; j < allCard.length - 1; j++) {
                if (allCard[j].getName().compareTo(allCard[j + 1].getName()) > 0) {
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
