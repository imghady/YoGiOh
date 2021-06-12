package model;

import com.google.gson.Gson;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Initializer {

    public static void initialize() throws IOException, ParseException {
        initializeMonster();
        initializeSpellsAndTraps();
        addUsers();
        //showAllUsers();
    }

    public static void initializeMonster() {
        List<List<String>> monsterTexts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Monster.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                monsterTexts.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mineMonstersData(monsterTexts);
    }

    public static void initializeSpellsAndTraps() {
        List<List<String>> spellAndTrapTexts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("SpellTrap.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                spellAndTrapTexts.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mineSpellsAndTrapsData(spellAndTrapTexts);
    }

    private static void mineMonstersData(List<List<String>> monsterTexts) {
        for (int i = 1; i < monsterTexts.size(); i++) {
            List<String> monsterText = monsterTexts.get(i);
            for (int j = 0; j < monsterText.size(); j++) {
                monsterText.set(j, monsterText.get(j).replaceAll("#", ","));
            }
            new Monster(monsterText.get(0), Integer.parseInt(monsterText.get(1)), monsterText.get(2),
                    monsterText.get(3), monsterText.get(4), Integer.parseInt(monsterText.get(5)),
                    Integer.parseInt(monsterText.get(6)), monsterText.get(7), Integer.parseInt(monsterText.get(8)));
        }
    }

    private static void mineSpellsAndTrapsData(List<List<String>> spellAndTrapTexts) {
        for (int i = 1; i < spellAndTrapTexts.size(); i++) {
            List<String> spellAndTrapText = spellAndTrapTexts.get(i);
            for (int j = 0; j < spellAndTrapText.size(); j++) {
                spellAndTrapText.set(j, spellAndTrapText.get(j).replaceAll("#", ","));
            }
            if (spellAndTrapText.get(1).equals("Trap")) {
                new Trap(spellAndTrapText.get(0), spellAndTrapText.get(2), spellAndTrapText.get(3), spellAndTrapText.get(4), Integer.parseInt(spellAndTrapText.get(5)));
            } else {
                new Spell(spellAndTrapText.get(0), spellAndTrapText.get(2), spellAndTrapText.get(3), spellAndTrapText.get(4), Integer.parseInt(spellAndTrapText.get(5)));
            }
        }
    }

    private static void writeMonstersOnJSON(List<List<String>> monsterTexts) {
        for (int i = 1; i < monsterTexts.size(); i++) {
            List<String> monsterText = monsterTexts.get(i);
            JSONObject monster = new JSONObject();
            String fileAddress = "src/main/resources/cards/monsters/" + monsterText.get(0) + ".json";
            monster.put("name", monsterText.get(0));
            monster.put("level", monsterText.get(1));
            monster.put("attribute", monsterText.get(2));
            monster.put("monsterType", monsterText.get(3));
            monster.put("cardType", monsterText.get(4));
            monster.put("attack", monsterText.get(5));
            monster.put("defence", monsterText.get(6));
            monster.put("description", monsterText.get(7));
            monster.put("price", monsterText.get(8));
            try (FileWriter file = new FileWriter(fileAddress)) {
                file.write(monster.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeSpellsAndTrapsOnJSON(List<List<String>> spellAndTrapTexts) {
        for (int i = 1; i < spellAndTrapTexts.size(); i++) {
            List<String> spellAndTrapText = spellAndTrapTexts.get(i);

            if (spellAndTrapText.get(1).equals("Trap")) {
                String fileAddress = "src/main/resources/cards/traps/" + spellAndTrapText.get(0) + ".json";
                JSONObject trap = new JSONObject();
                trap.put("name", spellAndTrapText.get(0));
                trap.put("type", spellAndTrapText.get(1));
                trap.put("icon", spellAndTrapText.get(2));
                trap.put("description", spellAndTrapText.get(3));
                trap.put("status", spellAndTrapText.get(4));
                trap.put("price", spellAndTrapText.get(5));
                try (FileWriter file = new FileWriter(fileAddress)) {
                    file.write(trap.toJSONString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String fileAddress = "src/main/resources/cards/spells/" + spellAndTrapText.get(0) + ".json";
                JSONObject spell = new JSONObject();
                spell.put("name", spellAndTrapText.get(0));
                spell.put("type", spellAndTrapText.get(1));
                spell.put("icon", spellAndTrapText.get(2));
                spell.put("description", spellAndTrapText.get(3));
                spell.put("status", spellAndTrapText.get(4));
                spell.put("price", spellAndTrapText.get(5));
                try (FileWriter file = new FileWriter(fileAddress)) {
                    file.write(spell.toJSONString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    private static void addUsers() throws IOException {
        File directoryPath = new File("resources/users");
        File[] filesList = directoryPath.listFiles();
        assert filesList != null;
        for (File file : filesList) {

            Gson gson = new Gson();
            Reader reader = new FileReader(file);
            User user = gson.fromJson(reader, User.class);
            User.addToAllUsers(user);

        }
    }

}
