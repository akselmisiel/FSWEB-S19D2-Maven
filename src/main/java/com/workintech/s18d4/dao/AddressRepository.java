package com.workintech.s18d4.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.workintech.s18d4.entity.Address;
import org.springframework.stereotype.Repository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
