package com.citylist.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.citylist.entity.CityList;
import com.citylist.repository.CityListRepository;
import com.citylist.repository.CityListSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityListServiceImpl implements CityListService{
	
	private final CityListRepository cityListRepository;

	@Override
	public Page<CityList> getCityList(int offset, int pageSize, String name, Long cityId) throws Exception {
		
		Pageable pageable = PageRequest.of(offset,pageSize);
		
		Specification<CityList> spec = null;
		
		
		if(Objects.nonNull(name)) {
			spec = Specification.where(CityListSpecification.cityName(name));
		}
		if(Objects.nonNull(cityId)) {
			spec = Specification.where(CityListSpecification.cityId(cityId));
		}
		
		Page<CityList> response = null;
		
		try {
			response = cityListRepository.findAll(spec, pageable);
		} catch(Exception e) {
			log.error("db error {}", e.toString());
			throw new Exception(e.toString());
		}
		return response;
	}

	@Override
	public CityList updateCityList(Long cityId, CityList cityList) throws Exception {
		
		CityList cityListDB = cityListRepository.findById(cityId).orElseThrow(() -> new Exception("City cannot be found"));
		
		if(Objects.nonNull(cityList.getName())) {
			cityListDB.setName(cityList.getName());
		}
		if(Objects.nonNull(cityList.getPhoto())) {
			cityListDB.setPhoto(cityList.getPhoto());
		}
		
		CityList response = null;
		
		try {
			response = cityListRepository.save(cityListDB);
		} catch(Exception e) {
			log.error("db error {}", e.toString());
			throw new Exception(e.toString());
		}
		
		return response;
	}

}
