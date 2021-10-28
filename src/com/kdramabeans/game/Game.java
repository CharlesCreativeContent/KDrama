package com.kdramabeans.game;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.kdramabeans.game.Gui.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class Game {

    public static Player player = new Player();
    public static Story story;
    public static DataParser item;
    public static BGM music;
    public static BGM soundfx;

    public static Map<String, String> evidenceMap = new HashMap<>() {{
        put("watch", "evidence 1");
        put("business card", "evidence 2");
        put("rose", "evidence 3");
        put("bitcoin", "evidence 4");
        put("voice recorder", "evidence 5");
    }};

    public static String printStatus() {
        String status = "";
        status += (story.printStory() + "\n" + story.printItems() + "\n" + story.printOptions());
        return status;
    }
    public static Icon getJPG(String jpgName){
        //sets up scene image
        Icon foundJPG = null;
        try {
            foundJPG =  new ImageIcon(Game.class.getResource("/"+jpgName+".jpg"));
        }catch(Exception e){
            System.out.println("Failed to load: "+jpgName+".jpg");
            e.printStackTrace();
        }
        return foundJPG;
    }
    public static Icon getGIF(String gifName){
        //sets up scene image
        Icon foundGIF = null;
        try {
            foundGIF =  new ImageIcon(Game.class.getResource("/"+gifName+".gif"));
        }catch(Exception e){
            System.out.println("Failed to load: "+gifName+".gif");
            e.printStackTrace();
        }
        return foundGIF;
    }

    public static void playGame() {
        try {
            String[] input = StringUtils.split(mainTextField.getText().toLowerCase().trim(), " ", 2);
            System.out.println("PLAYER INPUT: " + mainTextField.getText());
            final String[] Result = new String[1];
            Map<String, Runnable> allActions = new HashMap<>();

            ArrayList<Runnable> runners = new ArrayList<>() {{
                add(() -> {
                    if (story.hasItem(input[1]) || player.hasGrabbedItem(input[1])) {
                        Result[0] = item.getItemDescription(input[1]);
                        showMessageDialog(statusArea, "",Result[0],JOptionPane.INFORMATION_MESSAGE,getGIF(input[1].replaceAll(" ","_")));
                    } else {
                        Result[0] = "You cannot examine that.\n";
                        showMessageDialog(statusArea, Result[0]);
                    }
                });
                add(() -> {
                    if (story.hasItem(input[1]) && !player.hasGrabbedItem(input[1])) {
                        if (player.grabItem(input[1])) {
                            story.setOptions(input[1]);
                            Result[0] = "You have grabbed " + input[1];
                            showMessageDialog(statusArea, "",Result[0],JOptionPane.INFORMATION_MESSAGE,getGIF(input[1].replaceAll(" ","_")));
                        } else {
                            Result[0] = "You have too many items! Try dropping one if you really need to grab " + input[1];
                            showMessageDialog(statusArea, Result[0]);
                        }
                    } else {
                        Result[0] = "You cannot grab that.\n";
                    }
                });
                add(() -> {
                    String evidence = evidenceMap.get(input[1]);
                    if (player.hasGrabbedItem(input[1]) && story.hasHidden(evidence)) {
                        player.addEvidence(evidence);
                        player.dropItem(input[1]);
                        Result[0] = "You have used : " + input[1] + ", and you collected : " + evidence;
                    } else {
                        Result[0] = "You don't have this item in your inventory or your item does not work here";
                    }
                });
                add(() -> {
                    Result[0] = player.dropItem(input[1]);
                    showMessageDialog(statusArea, "",Result[0],JOptionPane.INFORMATION_MESSAGE,getGIF(input[1]));
                });
                add(() -> {
                    if (story.getOptions().containsKey(input[1])) {
                        story.setCurrentOption(input[1]);
                        story.nextScene(true);
                        Result[0] = "You chose option: " + input[1];
                    } else {
                        Result[0] = "Use: [choose,move,select] [number]\n";
                    }
                });
            }};
            try {
                InputStream validVerbs = Game.class.getResourceAsStream("/validVerbs.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(validVerbs));
                final int[] counter = {0};
                reader.lines().forEachOrdered(synonyms -> {
                    String[] verbs = synonyms.split(",");
                    for (String verb : verbs) {
                        allActions.put(verb, runners.get(counter[0]));
                    }
                    counter[0]++;
                });
            } catch (Exception except) {
                System.out.println("not a valid verb: or can't find validVerb.csv");
                except.printStackTrace();
            }
            allActions.getOrDefault(input[0], () -> {
                int answer = story.getOptions().values().stream().map(obj -> obj.get("description").toString().toLowerCase()).collect(Collectors.toList()).indexOf(mainTextField.getText().toLowerCase().trim()) + 1;
                if (answer != 0) {
                    if(mainTextField.getText().toLowerCase().trim().equals("go to academy")){
                        player.dropItem("wallet");
                    }
                    if(mainTextField.getText().toLowerCase().trim().equals("work out")){
                        Player.increaseExperience(100);
                    }
                    if(mainTextField.getText().toLowerCase().trim()
                            .substring(0,6).equals("accuse")){
                        Player.increaseExperience(100);
                        player.clearItems();
                    }
                    if(mainTextField.getText().toLowerCase().trim().equals("eat snack")){
                        Player.increaseHappiness(20);
                    }
                    story.setCurrentOption("" + answer);
                    story.nextScene(true);
                    Result[0] = mainTextField.getText();
                    showMessageDialog(statusArea, "",Result[0],JOptionPane.INFORMATION_MESSAGE,getGIF(Result[0].toLowerCase().replaceAll(" ","_")));
                } else {
                    Result[0] = "Not a command\n";
                }

            }).run();
            mainTextArea.setText(printStatus());
            inventoryArea.setText(player.printGrabbedItems() + "\n" + player.printEvidence());
            mainTextField.setText("");

            String sceneName = story.getScene().get("name").asText();
            currentScene.setText(sceneName.toUpperCase());
            sceneLabel.setIcon(getJPG(sceneName.replaceAll(" ","_")));

        } catch (ArrayIndexOutOfBoundsException exception) {
            statusArea.setText("Error: you didn't enter your move correctly");
        }
    }

    static {
        try {
            story = new Story();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}