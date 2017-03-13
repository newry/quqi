package com.quqi.impl.repository.wifi;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.quqi.impl.entity.wifi.UserSMSHistory;

public interface UserSMSHistoryRepository extends CrudRepository<UserSMSHistory, Long> {
	@Query("SELECT d FROM UserSMSHistory d WHERE d.phoneNumber=:phoneNumber and d.dateAdded>:date order by d.dateAdded desc")
	public UserSMSHistory findByPhoneNumer(@Param("phoneNumber") String phoneNumber, @Param("date") Calendar date);

	@Query("SELECT d FROM UserSMSHistory d WHERE d.dateAdded<:date")
	public List<UserSMSHistory> findByDate(@Param("date") Calendar date);
}
