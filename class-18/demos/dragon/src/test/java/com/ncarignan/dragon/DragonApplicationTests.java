package com.ncarignan.dragon;

import com.ncarignan.dragon.controllers.ApplicationUserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

// Necessary to manually import
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// necessary matchers to manually import
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DragonApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApplicationUserController applicationUserController;

	@Test
	void contextLoads() {
	}

	@Test
	public void homePageShouldRenderWithH1AndForm() throws Exception {
		this.mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("<h1>We are home</h1>")))
				.andExpect(content().string(containsString("<form action=\"/newuser\" method=\"POST\">")));
	}


	@Test
	public void detailPageShouldHaveSnowdropName() throws Exception {
		this.mockMvc.perform(get("/user/snowdrop"))
				.andExpect(redirectedUrl("http://localhost/login"));
//				.andExpect(content().string(containsString("<h1>snowdrop</h1>")));
	}
}
