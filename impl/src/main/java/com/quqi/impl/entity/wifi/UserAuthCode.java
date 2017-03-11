package com.quqi.impl.entity.wifi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.quqi.impl.entity.PersistentObject;

@Entity
@Table(name = "USER_AUTH_CODE")
@DynamicUpdate
public class UserAuthCode extends PersistentObject {

	private static final long serialVersionUID = -245121419246281307L;

	@Id
	@GeneratedValue(generator = "identity", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(name = "ID", unique = true, nullable = false, precision = 22)
	private Long id;

	@Column(name = "max_address", nullable = false, length = 255)
	private String macAddress;

	@Column(name = "phone_number", nullable = true, length = 64)
	private String phoneNumber;

	@Column(name = "auth_code", nullable = true, length = 64)
	private String authCode;

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

}
