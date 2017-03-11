package com.quqi.impl.repository.wifi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.quqi.impl.entity.wifi.User;

public interface UserRepository extends CrudRepository<User, Long> {
	@Query("SELECT d FROM User d WHERE d.macAddress=:macAddress and d.status='active'")
	public User findByMacAddress(@Param("macAddress") String macAddress);
}
