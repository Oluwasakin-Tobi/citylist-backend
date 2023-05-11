package com.citylist.repository;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.citylist.entity.CityList;

public class CityListSpecification {

	public static Specification<CityList> cityName(String cityName) {

		return (Objects.isNull(cityName)) ? null
				: (cityList, cq, cb) -> cb.equal(cb.lower(cityList.get("name")), cityName.toLowerCase());

	}

	public static Specification<CityList> cityId(Long cityId) {

		return (Objects.isNull(cityId)) ? null : (cityList, cq, cb) -> cb.equal(cityList.get("id"), cityId);

	}

}
