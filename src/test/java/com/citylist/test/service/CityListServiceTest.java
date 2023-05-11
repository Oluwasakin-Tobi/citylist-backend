package com.citylist.test.service;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.citylist.entity.CityList;
import com.citylist.repository.CityListRepository;
import com.citylist.service.CityListServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CityListServiceTest {
	
	@InjectMocks
	private CityListServiceImpl cityListService;
	
	@Mock
	private CityListRepository cityListRepository;
	
	@Test
	public void testFetchCityList() throws Exception {

		Pageable pageable = PageRequest.of(0,25);
		
		Specification<CityList> spec = null;
		
		Page<CityList> page = new PageImpl<>(Collections.singletonList(getCity()), pageable, 1);
		
		
		when(cityListRepository.findAll(spec, pageable))
		.thenReturn(page);
		
		Page<CityList> response = cityListService.getCityList(0, 25, any(), any());
		
		assertNotNull(response);
		assertEquals(response.getTotalElements(), 1);
		
		
	}
	
	@Test
	public void testUpdateCity_idNotFound() {
		
		assertThrows(Exception.class, () -> cityListService.updateCityList(any(), any()));
	}
	
	@Test
	public void testUpdateCityList() throws Exception {
		
		CityList cityList = getCity();
		
		CityList updatedCity = new CityList();
		updatedCity.setName("name2");
		updatedCity.setPhoto("Photo2");
		
		when(cityListRepository.findById(any()))
		.thenReturn(Optional.of(cityList));
		
		when(cityListRepository.save(any()))
		.thenReturn(updatedCity);
		
		CityList response = cityListService.updateCityList(cityList.getId(), updatedCity);
		
		verify(cityListRepository, times(1)).save(any(CityList.class));
		assertNotNull(response);
		assertEquals("name2", response.getName());
		
		
	}
	
	private CityList getCity() {
		
		CityList cityList = new CityList();
		cityList.setId(1L);
		cityList.setName("name1");
		cityList.setPhoto("Photo1");
		
		return cityList;
		
	}
	
	

}
