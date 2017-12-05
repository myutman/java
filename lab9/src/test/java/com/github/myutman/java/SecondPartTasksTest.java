package com.github.myutman.java;


import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws IOException {
        List<String> ans = SecondPartTasks.findQuotes(Arrays.asList("src/resources/file1.txt", "src/resources/file2.txt"), "one");
        assertEquals(Arrays.asList("bone", "alone", "bone", "alone", "stone", "gone", "Lonely", "stone", "stone", "stone"), ans);
    }

    @Test
    public void testPiDividedBy4() {
        assertTrue(Math.abs(SecondPartTasks.piDividedBy4() - Math.PI / 4) < 0.01);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("Pink Floyd", Arrays.asList("Shine On You Crazy Diamond (Parts I-V)", "Welcome to the Machine", "Have a Cigar",
                "Wish You Were Here", "Shine On You Crazy Diamond (Parts VI-IX)"));
        map.put("The Doors", Arrays.asList("Break on Through (To the Other Side)", "Soul Kitchen", "The Crystal Ship", "Twentieth Century Fox",
                "Alabama Song", "Light My Fire", "Back Door Man", "End of the Night", "The End"));
        map.put("The Velvet Underground", Arrays.asList("Sunday Morning", "I’m Waiting for the Man", "Femme Fatale", "Venus in Furs",
                "Run Run Run", "All Tomorrow's Parties", "Heroin", "There She Goes Again", "I’ll Be Your Mirror", "The Black Angel’s Death Song", "European Son"));
        map.put("The Beatles", Arrays.asList("Taxman", "Eleanor Rigby", "I’m Only Sleeping", "Love You To", "Here, There and Everywhere",
                "Yellow Submarine", "She Said, She Said", "Good Day Sunshine", "And Your Bird Can Sing", "For No One", "Dr. Robert", "I Want to Tell You",
                "Got to Get You into My Life", "Tomorrow Never Knows"));
        assertEquals("The Beatles", SecondPartTasks.findPrinter(map));
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> store1 = new HashMap<>();
        store1.put("cabbage",  1);
        store1.put("cucumber", 3);
        store1.put("tomato",   2);
        Map<String, Integer> store2 = new HashMap<>();
        store2.put("apple",    4);
        store2.put("cucumber", 3);
        store2.put("orange",   5);
        Map<String, Integer> store3 = new HashMap<>();
        store3.put("tomato",        4);
        store3.put("apple",         3);
        store3.put("passion fruit", 1);
        Map<String, Integer> ans = new HashMap<>();
        ans.put("cabbage",        1);
        ans.put("cucumber",       6);
        ans.put("tomato",         6);
        ans.put("apple",          7);
        ans.put("orange",         5);
        ans.put("passion fruit",  1);
        assertEquals(ans, SecondPartTasks.calculateGlobalOrder(Arrays.asList(store1, store2, store3)));
    }
}