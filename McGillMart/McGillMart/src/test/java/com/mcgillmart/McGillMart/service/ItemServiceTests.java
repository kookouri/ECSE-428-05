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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.Optional;

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

        List<Item> item = service.filterItemsByName(item1.getName());

        assertTrue(item.equals(item1.getName()));
        assertTrue(item.getDescription().equals(item1.getDescription()));
        assertTrue(item.getPrice() == item1.getPrice());
        assertTrue(item.getCategory().equals(item1.getCategory()));

        
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

    @Test
    public void testFindItemById() {
        Item item = new Item();
        item.setId(1);
        item.setName("Test Item");
        item.setDescription("A test item description");
        item.setPrice(200);
        item.setCategory(Category.Clothing);
        when(itemDao.findById(1)).thenReturn(Optional.of(item));
        Item foundItem = service.findItemById(1);
        assertNotNull(foundItem);
        assertEquals(item.getId(), foundItem.getId());
        assertEquals(item.getName(), foundItem.getName());
        assertEquals(item.getDescription(), foundItem.getDescription());
        assertEquals(item.getPrice(), foundItem.getPrice());
        assertEquals(item.getCategory(), foundItem.getCategory());
        verify(itemDao, times(1)).findById(1);
    }

    @Test
    public void testFindItemByIdNotFound() {
        when(itemDao.findById(999)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.findItemById(999);
        });
        assertEquals("There is no item with ID 999.", exception.getMessage());
        verify(itemDao, times(1)).findById(999);
    }

    @Test
    public void testFindAllItems() {
        Item item1 = new Item();
        item1.setId(1);
        item1.setName("Item1");
        item1.setDescription("Description1");
        item1.setPrice(100);
        item1.setCategory(Category.Clothing);
        Item item2 = new Item();
        item2.setId(2);
        item2.setName("Item2");
        item2.setDescription("Description2");
        item2.setPrice(150);
        item2.setCategory(Category.Textbook);
        ITEMS.add(item1);
        ITEMS.add(item2);
        when(itemDao.findAll()).thenReturn(ITEMS);
        List<Item> items = service.findAllItems();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
        verify(itemDao, times(1)).findAll();
    }

    @Test
    public void testFindAllItemsEmpty() {
        when(itemDao.findAll()).thenReturn(new ArrayList<>());
        List<Item> items = service.findAllItems();
        assertNotNull(items);
        assertTrue(items.isEmpty());
        verify(itemDao, times(1)).findAll();
    }

}
