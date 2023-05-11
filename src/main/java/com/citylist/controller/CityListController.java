package com.citylist.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citylist.entity.CityList;
import com.citylist.enums.ResponseStatus;
import com.citylist.models.Response;
import com.citylist.service.CityListService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"CityList"})
public class CityListController {
	
	private final CityListService cityListService;
	
	@GetMapping(value = "/citylists")
	public ResponseEntity<?> fetchCityList(
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "pageSize", defaultValue = "25") int pageSize,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "cityId", required = false) Long cityId) throws Exception {
		
		Page<CityList> response = cityListService.getCityList(offset, pageSize, name, cityId);
		
		return ResponseEntity.ok(new Response(response, ResponseStatus.SUCCESSFUL.getCode(), 
				ResponseStatus.SUCCESSFUL.getMessage(), ResponseStatus.SUCCESSFUL.getMessage()));
	}
	
	@PatchMapping(value = "/citylists")
	@PreAuthorize("hasRole('ROLE_ALLOW_EDIT')")
	public ResponseEntity<?> updateCityList(@RequestBody CityList cityList,
			@RequestParam(value = "cityId", required = true) Long cityId) throws Exception {
		
		CityList response = cityListService.updateCityList(cityId, cityList);
		
		return ResponseEntity.ok(new Response(response, ResponseStatus.SUCCESSFUL.getCode(), 
				ResponseStatus.SUCCESSFUL.getMessage(), ResponseStatus.SUCCESSFUL.getMessage()));
	}

}
