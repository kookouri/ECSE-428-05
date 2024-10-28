package com.mcgillmart.McGillMart.features;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import com.mcgillmart.McGillMart.McGillMartApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = McGillMartApplication.class)
public class CucumberSpringConfiguration {
}
