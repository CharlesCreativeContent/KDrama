package com.kdramabeans.game;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class DataParserTest {

    DataParser dp;
    JsonNode scene;

    @Before
    public void setUp() throws Exception {
        dp = new DataParser();
        scene = dp.getStoryIntro();
    }

    @Test
    public void getItemDescriptionTest() {
        System.out.println(dp.getItemDescription("passport"));
    }

    @Test
    public void getItemNamesTest() {
        System.out.println(dp.getItemNames());
    }

    @Test
    public void getItemOptionsTest() {
        System.out.println(dp.getItemOption("passport"));
    }

    @Test
    public void getSceneItemsTest() {
        System.out.println(scene);
        System.out.println(dp.getSceneItems(scene));
    }

    @Test
    public void getSceneHiddenTest() {
        System.out.println(dp.getSceneHidden(scene));
    }

    @Test
    public void randomEventsTest() {
        System.out.println(dp.getRandomEvents());
    }

    @Test
    public void getStoryIntroTest() {
        System.out.println(dp.getStoryIntro());
    }
}