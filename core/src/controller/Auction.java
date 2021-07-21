package controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Auction {
    public JSONParser parser = new JSONParser();

    public String addAuction(String cardName,String startOffer){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "addAuction");
        jsonObject.put("startOffer",startOffer);
        jsonObject.put("cardName",cardName);
        jsonObject.put("token",AppClient.getToken());
        String result = "";
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject1 = (JSONObject) parser.parse(result);
            if (jsonObject1.get("type").equals("Successful")){
                return "success";
            }
            else {
                return jsonObject1.get("message").toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getActive(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "getActiveAuction");
        jsonObject.put("token",AppClient.getToken());
        String result = "";
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
            return result;
        } catch (IOException e) {
            return "";
        }
    }

}
