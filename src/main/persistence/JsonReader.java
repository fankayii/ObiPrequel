package persistence;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import model.items.Bacta;
import org.json.*;

/**
 * Represents a reader that reads some fields of game from JSON data stored in file
 * Based on JsonReader from JsonSerializationDemo
 */
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads level from file and returns it;
    // throws IOException if an error occurs reading data from file
    public int readLevel() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLevel(jsonObject);
    }

    // EFFECTS: reads player's health from file and returns it;
    // throws IOException if an error occurs reading data from file
    public int readHealth() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHealth(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses level from JSON object and returns it
    private int parseLevel(JSONObject jsonObject) {
        return jsonObject.getInt("Level");

    }

    // EFFECTS: reads player's position from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Point readPos() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePos(jsonObject);

    }

    // EFFECTS: reads the list of bactas from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Bacta> readBactas() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBactas(jsonObject);


    }

    // EFFECTS: parses Position from JSON object and returns as a Point object
    private Point parsePos(JSONObject jsonObject) {
        JSONObject pos = jsonObject.getJSONObject("Position");
        int xpos = pos.getInt("xpos");
        int ypos = pos.getInt("ypos");
        return new Point(xpos,ypos);

    }

    // EFFECTS: parses the list of bactas from JSON object and returns it
    private ArrayList<Bacta> parseBactas(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Bactas");
        ArrayList<Bacta> bactaList = new ArrayList<>();
        for (Object json: jsonArray) {
            JSONObject nextBacta = (JSONObject) json;
            bactaList.add(new Bacta(nextBacta.getInt("xpos"),nextBacta.getInt("ypos")));
        }
        return bactaList;

    }

    // EFFECTS: parses player's health from JSON object and returns it
    private int parseHealth(JSONObject jsonObject) {
        return jsonObject.getInt("Health");
    }


}
