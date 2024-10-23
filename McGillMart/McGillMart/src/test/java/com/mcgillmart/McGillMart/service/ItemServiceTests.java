// package com.mcgillmart.McGillMart.service;

// import static org.junit.Assert.*;

// import com.services.ItemService;
// import org.junit.Before;
// import org.junit.Test;
// import org.mockito.InjectMocks;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// public class ItemServiceTest {

//     @InjectMocks
//     private ItemService service;

//     @Before
//     public void setUp() {
//         List<Map<String, String>> items = new ArrayList<>();

//         Map<String, String> item1 = new HashMap<>();
//         item1.put("id", "01");
//         item1.put("name", "science hoodie");
//         item1.put("price", "40");
//         item1.put("description", "soft hoodie for science students");
//         item1.put("category", "Clothing");

//         Map<String, String> item2 = new HashMap<>();
//         item2.put("id", "02");
//         item2.put("name", "med hoodie");
//         item2.put("price", "40");
//         item2.put("description", "soft hoodie for med students");
//         item2.put("category", "Clothing");

//         Map<String, String> item3 = new HashMap<>();
//         item3.put("id", "03");
//         item3.put("name", "nurse hoodie");
//         item3.put("price", "40");
//         item3.put("description", "soft hoodie for nursing students");
//         item3.put("category", "Clothing");

//         Map<String, String> item4 = new HashMap<>();
//         item4.put("id", "04");
//         item4.put("name", "signals & networks book");
//         item4.put("price", "250");
//         item4.put("description", "textbook for ecse 316");
//         item4.put("category", "Textbook");

//         Map<String, String> item5 = new HashMap<>();
//         item5.put("id", "05");
//         item5.put("name", "operating systems book");
//         item5.put("price", "250");
//         item5.put("description", "textbook for ecse 427");
//         item5.put("category", "Textbook");

//         items.add(item1);
//         items.add(item2);
//         items.add(item3);
//         items.add(item4);
//         items.add(item5);

//         itemService = new ItemService(items);
//     }

//     @Test
//     public void testFilterItemsByName() {
//         String keyword = "hoodie";

//         List<Map<String, String>> filteredItems = itemService.filterItemsByName(keyword);

//         assertEquals(3, filteredItems.size());

//         for (Map<String, String> item : filteredItems) {
//             String itemName = item.get("name").toLowerCase(); // Convert to lowercase to make it case-insensitive
//             assertTrue(itemName.contains(keyword.toLowerCase()));
//         }

//         Map<String, String> firstItem = filteredItems.get(0);
//         assertEquals("01", firstItem.get("id"));
//         assertEquals("science hoodie", firstItem.get("name"));
//     }

//     @Test
//     public void testFilterItemsByCategoryClothing() {
//         List<Map<String, String>> filteredItems = itemService.filterItemsByCategory("Clothing");

//         assertEquals(3, filteredItems.size());

//         Map<String, String> firstItem = filteredItems.get(0);
//         assertEquals("01", firstItem.get("id"));
//         assertEquals("science hoodie", firstItem.get("name"));

//         Map<String, String> lastItem = filteredItems.get(2);
//         assertEquals("03", lastItem.get("id"));
//         assertEquals("nurse hoodie", lastItem.get("name"));
//     }

//     @Test
//     public void testFilterItemsByCategoryTextbook() {
//         List<Map<String, String>> filteredItems = itemService.filterItemsByCategory("Textbook");

//         assertEquals(2, filteredItems.size());

//         Map<String, String> firstItem = filteredItems.get(0);
//         assertEquals("04", firstItem.get("id"));
//         assertEquals("signals & networks book", firstItem.get("name"));

//         Map<String, String> lastItem = filteredItems.get(1);
//         assertEquals("05", lastItem.get("id"));
//         assertEquals("operating systems book", lastItem.get("name"));
//     }
// }


package com.mcgillmart.McGillMart.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.Item.Category;
import com.mcgillmart.McGillMart.repositories.ItemRepository;
import com.mcgillmart.McGillMart.repositories.McGillMartRepository;
import com.mcgillmart.McGillMart.services.ItemService;
import com.mcgillmart.McGillMart.util.ServiceUtils;

@SpringBootTest
public class ItemServiceTests {

    @Mock
    private ItemRepository itemDao;

    @Mock
    private McGillMartRepository martRepo;

    @InjectMocks
    private ItemService service;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        itemDao.deleteAll();
    }

    public static final List<Item> ITEMS = new ArrayList<>();

    @Test
    public void testFilterItemsByName() {
        Item item1 = new Item();
        String name1 = "aName1";
        String description1 = "aDescription1";
        Integer price1 = 100;
        String category1 = Category.Clothing.toString();
        item1.setName(name1);
        item1.setDescription(description1);
        item1.setPrice(price1);
        item1.setCategory(Item.Category.valueOf(category1));

        Item item2 = new Item();
        String name2 = "aName2";
        String description2 = "aDescription2";
        Integer price2 = 100;
        String category2 = Category.Clothing.toString();
        item2.setName(name2);
        item2.setDescription(description2);
        item2.setPrice(price2);
        item2.setCategory(Item.Category.valueOf(category2));

        Item item3 = new Item();
        String name3 = "aName3";
        String description3 = "aDescription3";
        Integer price3 = 100;
        String category3 = Category.Textbook.toString();
        item3.setName(name3);
        item3.setDescription(description3);
        item3.setPrice(price3);
        item3.setCategory(Item.Category.valueOf(category3));

        ITEMS.add(item1);
        ITEMS.add(item2);
        ITEMS.add(item3);

        when(itemDao.findAll()).thenReturn(ITEMS);

        List<Item> items = service.filterItemsByName(item1.getName());

        assertTrue(items.get(0).getName().equals(item1.getName()));
        assertTrue(items.get(0).getDescription().equals(item1.getDescription()));
        assertTrue(items.get(0).getPrice() == item1.getPrice());
        assertTrue(items.get(0).getCategory().equals(item1.getCategory()));

        
    }

    @Test
    public void testFilterItemsByCategory() {
        Item item1 = new Item();
        String name1 = "aName1";
        String description1 = "aDescription1";
        Integer price1 = 100;
        String category1 = Category.Clothing.toString();
        item1.setName(name1);
        item1.setDescription(description1);
        item1.setPrice(price1);
        item1.setCategory(Item.Category.valueOf(category1));

        Item item2 = new Item();
        String name2 = "aName2";
        String description2 = "aDescription2";
        Integer price2 = 100;
        String category2 = Category.Clothing.toString();
        item2.setName(name2);
        item2.setDescription(description2);
        item2.setPrice(price2);
        item2.setCategory(Item.Category.valueOf(category2));

        Item item3 = new Item();
        String name3 = "aName3";
        String description3 = "aDescription3";
        Integer price3 = 100;
        String category3 = Category.Textbook.toString();
        item3.setName(name3);
        item3.setDescription(description3);
        item3.setPrice(price3);
        item3.setCategory(Item.Category.valueOf(category3));

        ITEMS.add(item1);
        ITEMS.add(item2);
        ITEMS.add(item3);

        when(itemDao.findAll()).thenReturn(ITEMS);

        List<Item> clothing = service.filterItemsByCategory(Item.Category.Clothing.toString());

        for (Item item : clothing) {
            assertEquals(Item.Category.Clothing, item.getCategory());
        }
    }


    


}
