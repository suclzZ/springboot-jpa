package com.sucl.jpa.web;

import com.sucl.jpa.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author sucl
 * @date 2019/6/15
 */
//@WebMvcTest
public class UserControllerTest extends AbstractTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void before(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test"));
    }
}
