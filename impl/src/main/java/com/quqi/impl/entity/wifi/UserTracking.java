package com.quqi.impl.entity.wifi;

import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quqi.impl.entity.PersistentObject;

@Entity
@Table(name = "USER_TRACKING")
@DynamicUpdate
public class UserTracking extends PersistentObject {

	private static final long serialVersionUID = -7890433232718538628L;

	@Id
	@GeneratedValue(generator = "identity", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(name = "ID", unique = true, nullable = false, precision = 22)
	private Long id;

	@ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	@JsonIgnore
	private User user;

	@Column(name = "store", length = 255)
	private String store;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", nullable = false, updatable = false)
	private Calendar startTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME")
	private Calendar endTime;

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Long getId() {
		return id;
	}

}
