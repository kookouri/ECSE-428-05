package com.mcgillmart.McGillMart;

import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mcgillmart.McGillMart.services.ItemService;
import com.mcgillmart.McGillMart.services.McGillMartService;
import com.mcgillmart.McGillMart.services.UserService;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    
    @Autowired
    private McGillMartService mcgillMartService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Override
    public void run(String... args) throws Exception {
        initializeDatabase();
    }

    private void initializeDatabase() {
        // If the unique mcgillMart is not there, create it and initializes
        if (mcgillMartService.getAllMcGillMarts().isEmpty()) {
            System.err.println("McGillMart created");
            mcgillMartService.createMcGillMart();
            itemService.createItem("McGill Classic Hoodie", 45.99, "UNISEX - An instant classic. This unisex pullover hoodie is made from peached fleece, and features a lined hood with drawstring, front pouch pocket, rib cuffs and waistband, and a two-tone embroidered McGill logo up front. 50% cotton / 50% polyester", "Clothing", "https://test.lejames.ca/sites/default/files/catalogue/images/12965847_14485.jpg");
            itemService.createItem("McGill Basic Tee", 14.99, "UNISEX - Even the basics look better with the McGill touch. This unisex crewneck t-shirt is 100% cotton with the University's crest screen printed across the chest. Machine washable for easy wear and care.", "Clothing", "https://test.lejames.ca/sites/default/files/catalogue/images/12983810_14438_revised.jpg");
            itemService.createItem("McGill Plaid Print Pen", 4.99, "Click action ballpoint pen that features the McGill wordmark printed on a rubberized barrel and plaid imprint on the clip.", "Stationery", "https://test.lejames.ca/sites/default/files/catalogue/images/mcgill-plaid-pen-web_0.jpg");
            itemService.createItem("The Future Is Now: Solving the Climate Crisis with Today's Technologies", 28.99, "Publication date: September 27, 2022. 304 pages", "Textbook", "https://test.lejames.ca/sites/default/files/catalogue/images/untitled_design_1.png");
            itemService.createItem("Silk McGill Tie", 49.95, "Represent your alma mater wherever you go with this pure silk McGill tie. Features stripes outlined in silver white and school's crest. The 48\"-long, 3\"-wide tie is available in navy blue or red.", "Clothing", "https://test.lejames.ca/sites/default/files/catalogue/images/10335208_14560.jpg");

            userService.createUser("admin@mail.com", "Admin", "password", "111-111-1111");
        }
        else {
            System.err.println("McGillMart already created");
        }
    }
}