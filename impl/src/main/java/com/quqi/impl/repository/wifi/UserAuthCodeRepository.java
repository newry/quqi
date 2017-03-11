package com.quqi.impl.repository.wifi;

import java.util.Calendar;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.quqi.impl.entity.wifi.UserAuthCode;

public interface UserAuthCodeRepository extends CrudRepository<UserAuthCode, Long> {
	@Query("SELECT d FROM UserAuthCode d WHERE d.macAddress=:macAddress and d.phoneNumber=:phoneNumber and d.dateAdded>:date order by d.dateAdded desc")
	public UserAuthCode findByMacAddress(@Param("macAddress") String macAddress,
			@Param("phoneNumber") String phoneNumber, @Param("date") Calendar date);
}
