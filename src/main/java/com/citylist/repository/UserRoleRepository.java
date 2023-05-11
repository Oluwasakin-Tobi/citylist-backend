package com.citylist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citylist.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

}
