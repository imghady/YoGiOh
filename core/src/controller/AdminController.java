package controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

public class AdminController {
    public JSONParser parser = new JSONParser();

    public static String ban(String cardName){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "adminBan");
        jsonObject.put("cardName",cardName);
        String result = "";
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
            if (result.equals("success")){
                return "success";
            }
            else {
                return "card invalid!";
            }
        } catch (IOException e) {
            return "";
        }
    }

    public static  String add(String cardName,String number){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "adminAdd");
        jsonObject.put("cardName",cardName);
        jsonObject.put("number",number);
        String result = "";
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
            if (result.equals("success")){
                return "success";
            }
            else {
                return "card invalid!";
            }
        } catch (IOException e) {
            return "";
        }
    }
    public static  String remove(String cardName,String number){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "adminAdd");
        jsonObject.put("cardName",cardName);
        jsonObject.put("number",number);
        String result = "";
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
            if (result.equals("success")){
                return "success";
            }
            else {
                return "card invalid!";
            }
        } catch (IOException e) {
            return "";
        }
    }


}
