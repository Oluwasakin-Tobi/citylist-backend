package com.citylist.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="city_list")
@Data
public class CityList {
	
	@Id
	private Long id;
	private String name;
	private String photo;

}
