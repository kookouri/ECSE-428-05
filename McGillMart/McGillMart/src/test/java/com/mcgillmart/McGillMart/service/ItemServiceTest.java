package com.mcgillmart.McGillMart.service;

import static org.junit.Assert.*;

import com.services.ItemService;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemServiceTest {

    private ItemService itemService;

    @Before
    public void setUp() {
        List<Map<String, String>> items = new ArrayList<>();

        Map<String, String> item1 = new HashMap<>();
        item1.put("id", "01");
        item1.put("name", "science hoodie");
        item1.put("price", "40");
        item1.put("description", "soft hoodie for science students");
        item1.put("category", "Clothing");

        Map<String, String> item2 = new HashMap<>();
        item2.put("id", "02");
        item2.put("name", "med hoodie");
        item2.put("price", "40");
        item2.put("description", "soft hoodie for med students");
        item2.put("category", "Clothing");

        Map<String, String> item3 = new HashMap<>();
        item3.put("id", "03");
        item3.put("name", "nurse hoodie");
        item3.put("price", "40");
        item3.put("description", "soft hoodie for nursing students");
        item3.put("category", "Clothing");

        Map<String, String> item4 = new HashMap<>();
        item4.put("id", "04");
        item4.put("name", "signals & networks book");
        item4.put("price", "250");
        item4.put("description", "textbook for ecse 316");
        item4.put("category", "Textbook");

        Map<String, String> item5 = new HashMap<>();
        item5.put("id", "05");
        item5.put("name", "operating systems book");
        item5.put("price", "250");
        item5.put("description", "textbook for ecse 427");
        item5.put("category", "Textbook");

        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);

        itemService = new ItemService(items);
    }

    @Test
    public void testFilterItemsByName() {
        String keyword = "hoodie";

        List<Map<String, String>> filteredItems = itemService.filterItemsByName(keyword);

        assertEquals(3, filteredItems.size());

        for (Map<String, String> item : filteredItems) {
            String itemName = item.get("name").toLowerCase(); // Convert to lowercase to make it case-insensitive
            assertTrue(itemName.contains(keyword.toLowerCase()));
        }

        Map<String, String> firstItem = filteredItems.get(0);
        assertEquals("01", firstItem.get("id"));
        assertEquals("science hoodie", firstItem.get("name"));
    }

    @Test
    public void testFilterItemsByCategoryClothing() {
        List<Map<String, String>> filteredItems = itemService.filterItemsByCategory("Clothing");

        assertEquals(3, filteredItems.size());

        Map<String, String> firstItem = filteredItems.get(0);
        assertEquals("01", firstItem.get("id"));
        assertEquals("science hoodie", firstItem.get("name"));

        Map<String, String> lastItem = filteredItems.get(2);
        assertEquals("03", lastItem.get("id"));
        assertEquals("nurse hoodie", lastItem.get("name"));
    }

    @Test
    public void testFilterItemsByCategoryTextbook() {
        List<Map<String, String>> filteredItems = itemService.filterItemsByCategory("Textbook");

        assertEquals(2, filteredItems.size());

        Map<String, String> firstItem = filteredItems.get(0);
        assertEquals("04", firstItem.get("id"));
        assertEquals("signals & networks book", firstItem.get("name"));

        Map<String, String> lastItem = filteredItems.get(1);
        assertEquals("05", lastItem.get("id"));
        assertEquals("operating systems book", lastItem.get("name"));
    }
}
