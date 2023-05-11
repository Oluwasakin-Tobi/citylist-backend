package com.citylist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.citylist.entity.CityList;

@Repository
public interface CityListRepository extends JpaRepository<CityList, Long>,
JpaSpecificationExecutor<CityList> {

}
