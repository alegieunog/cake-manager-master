package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dto.Cake;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CakeControllerTest {

    @Autowired
    @InjectMocks
    CakeController cakeController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void webApplicationContextLoads(){
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("cakeController"));
    }

    @Test
    public void listCakesTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentAsString(), notNullValue());
        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void createCakeTest() throws Exception {
        String payload = "{\"title\": \"Chocolate Cake\",\"description\": \"Easy Chocolate Cake\",\"image\": \n" +
                "    \"https://ichef.bbci.co.uk/food/ic/food_16x9_1600/recipes/easy_chocolate_cake_31070_16x9.jpg\"}";

        MvcResult mvcResult = mockMvc.perform(post("/cake/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8")
                .content(payload))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentAsString(), notNullValue());
        assertThat(response.getStatus(), is(equalTo(302)));
    }

    @Test
    public void getCakeTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/cake")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", "victoria%20sponge"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentAsString(), notNullValue());
        assertThat(response.getStatus(), is(equalTo(200)));
    }
}