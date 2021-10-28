package com.kdramabeans.game;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<String> grabbedItems = new ArrayList<>(); //player's inventory
    private List<String> evidenceList = new ArrayList<>();

    private static int MAX_INVENTORY = 3; //    default is 3
    private static int happiness = 50; // default is 50
    private static int experience = 10; // default is 10

    /*
     CTOR
     */
    public Player(){

    }

    public Player(int happiness, int experience) {
        this();
        Player.setHappiness(happiness);
        Player.setExperience(experience);
    }

    /*
      METHODS/FUNCTIONS
     */

    //adds an item to the Player's inventory only if they have not reached max capacity
    public boolean grabItem(String item) {
        boolean canGrab = false;
        if (grabbedItems.size() < MAX_INVENTORY) {
            System.out.println("You have grabbed: " + item + "\n");
            grabbedItems.add(item);
            canGrab = true;
        } else {
            System.out.println("You have too many items! Try dropping one if you really need to grab " + item);
        }
        return canGrab;
    }

    public void addEvidence(String evidence) {
        evidenceList.add(evidence);
    }

    //deletes an item from user's inventory, if it's there
    public String dropItem(String item) {
        if (hasGrabbedItem(item)) {
            grabbedItems.remove(item);
            return "You have dropped: " + item;
        } else {
            return "You don't have one to drop.";
        }
    }

    // dynamically prints items in your inventory when player chooses to grab from the scene
    public String printGrabbedItems() {
        return "Items in your inventory: " + grabbedItems;
    }

    public String printEvidence() {
        return "You have collected: " + evidenceList;
    }

    //check if inventory contains the grabbed item
    public boolean hasGrabbedItem(String item) {
        return grabbedItems.contains(item);
    }

    //clears items in the inventory
    public void clearItems() {
        grabbedItems.clear();
    }

    public static int getHappiness() {
        return happiness;
    }

    public static void setHappiness(int happiness) {
        Player.happiness = happiness;
    }

    public static void increaseHappiness(int experience) {
        Player.experience += experience;
    }

    public static int getExperience() {
        return experience;
    }

    public static void setExperience(int experience) {
        Player.experience = experience;
    }

    public static void increaseExperience(int experience) {
        Player.experience += experience;
    }
    public static boolean hasExperienceGreaterThan(int exp){
        return getExperience()>exp;
    }
    public static boolean hasHappinessGreaterThan(int happiness){
        return getHappiness()>happiness;
    }
}