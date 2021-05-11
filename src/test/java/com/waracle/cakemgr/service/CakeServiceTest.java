package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.Cake;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CakeServiceTest {

    private CakeService cakeService;

    @Before
    public void setUp() throws Exception {
        cakeService = new CakeServiceImpl();
    }

    @Test
    public void getCakeListTest() {
        List<Cake> cakes = cakeService.getCakes();
        cakes.forEach(System.out::println);
        assertFalse(cakes.isEmpty());
        assertEquals(cakes.get(0).getTitle(), "victoria sponge");
    }

    @Test
    public void addCakeTest() {
        Cake cake = new Cake("Chocolate Cake","Easy Chocolate Cake",
                "https://ichef.bbci.co.uk/food/ic/food_16x9_1600/recipes/easy_chocolate_cake_31070_16x9.jpg");
        cakeService.addCake(cake);
        assertEquals(cakeService.getCakes().size(), 6);
        assertTrue(cakeService.getCakes().contains(cake));
    }

    @Test
    public void getCakeTest() {
       Cake cake = cakeService.getCake("Carrot Cake");
       assertEquals(cake.getDescription(), "Bugs bunnys favourite");
       assertEquals(cake.getImage(), "http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg");
    }
}