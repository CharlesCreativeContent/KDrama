package com.kdramabeans.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class DataParser {

    static String STORY_NODE = "story";
    static String ITEMS_NODE = "items";
    static String RANDOMEVENTS_NODE = "randomEvents";
    static String DESCRIPTION_NODE = "description";
    static String POSITION_NODE = "position";
    static String ITEM_OPTION = "option";
    static String HIDDEN_NODE = "hidden";

    private ObjectMapper mapper;
    private JsonNode root;
    private JsonNode event;

    DataParser() {
        try {
            InputStream gameData = getClass().getResourceAsStream("/data.json");
            mapper = new ObjectMapper();
            root = mapper.readTree(gameData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getItemDescription(String itemName) {
        String itemD = root.path(ITEMS_NODE).path(itemName).path(DESCRIPTION_NODE).asText();
        return StringUtils.capitalize(itemName) + ": " + itemD;
    }

    public String getItemPosition(String itemName) {
        return root.path(ITEMS_NODE).path(itemName).path(POSITION_NODE).asText();
    }

    public List<String> getItemNames() {
        List<String> result = new ArrayList<>();
        root.path(ITEMS_NODE).fieldNames().forEachRemaining(result::add);
        return result;
    }

    public List<String> getSceneItems(JsonNode scene) {
        List<String> result = new ArrayList<>();
        JsonNode arrNode = scene.path(ITEMS_NODE);
        for (JsonNode objNode : arrNode) {
            result.add(objNode.asText());
        }
        return result;
    }

    public List<String> getSceneHidden(JsonNode scene) {
        List<String> result = new ArrayList<>();
        JsonNode arrNode = scene.path(HIDDEN_NODE);
        for (JsonNode objNode : arrNode) {
            result.add(objNode.asText());
        }
        return result;
    }

    public Map getItemOption(String itemName) {
        JsonNode jsonNode = root.path(ITEMS_NODE).path(itemName).path(ITEM_OPTION);
        return mapper.<Map<String, Object>>convertValue(jsonNode, new TypeReference<>() {
        });
    }

    public JsonNode getRandomEvents() {
        int randEventSize = root.path(RANDOMEVENTS_NODE).size();
        Random rand = new Random();
        String eventNumber = "event" + rand.nextInt(randEventSize);
        event = root.path(RANDOMEVENTS_NODE).path(eventNumber);
        return event;
    }

    public JsonNode getStoryIntro() {
        return root.path(STORY_NODE).path("home");
    }

    public JsonNode getStory() {
        return root.path(STORY_NODE);
    }
}