package com.citylist.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.citylist.controller.CityListController;
import com.citylist.entity.CityList;
import com.citylist.service.CityListService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class CityListControllerTest {
	
	@Mock
	private CityListService cityListService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		CityListController cityListController = new CityListController(cityListService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(cityListController).build();
	}
	
	@Test
	public void testUpdateCityList() throws Exception {
		
		CityList updatedCity = new CityList();
		updatedCity.setName("name2");
		updatedCity.setPhoto("Photo2");
		updatedCity.setId(18L);
		
		when(cityListService.updateCityList(updatedCity.getId(), updatedCity))
		.thenReturn(updatedCity);
		
		mockMvc.perform(MockMvcRequestBuilders.patch("/citylists?cityId=18")
				.content(new ObjectMapper().writeValueAsString(updatedCity))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data.name").value("name2"))
				.andExpect(jsonPath("$.data.photo").value("Photo2"))
				.andReturn();
		
	}
	
	@Test
	public void testGetCityList() throws Exception {
		
		Pageable pageable = PageRequest.of(0,25);
		
		Page<CityList> page = new PageImpl<>(Collections.singletonList(getCity()), pageable, 1);
		
		when(cityListService.getCityList(0, 25, null, null))
		.thenReturn(page);
		
		MvcResult response = mockMvc.perform(MockMvcRequestBuilders
				.get("/citylists?offset=0&pageSize=25")
				.content(new ObjectMapper().writeValueAsString(page))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(print())
				.andReturn();
		
		String json = response.getResponse().getContentAsString();
		
		assertNotNull(json);
		
	}
	
	private CityList getCity() {
		
		CityList cityList = new CityList();
		cityList.setId(1L);
		cityList.setName("name1");
		cityList.setPhoto("Photo1");
		
		return cityList;
		
	}

}
