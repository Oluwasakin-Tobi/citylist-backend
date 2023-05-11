package com.citylist.service;

import org.springframework.data.domain.Page;

import com.citylist.entity.CityList;

public interface CityListService {

	Page<CityList> getCityList(int offset, int pageSize, String name, Long cityId) throws Exception;

	CityList updateCityList(Long cityId, CityList cityList) throws Exception;

}
