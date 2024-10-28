package com.mcgillmart.McGillMart.features;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/resources/",
    glue = {"com.mcgillmart.McGillMart.features", 
    "com.mcgillmart.McGillMart.features"} )
public class CucumberFeaturesTestRunner {

}
