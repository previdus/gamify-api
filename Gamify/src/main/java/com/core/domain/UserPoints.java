package com.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_points")
public class UserPoints implements Serializable{
	
	private static final long serialVersionUID = 4050230454270725820L;
	
	@Id
	@Column(name = "user_id", nullable = false)
	private long userId;
	
	@Column(name = "lms_points",columnDefinition="int default 0")
	private long lmsPoints = 0;
	
	
	public UserPoints() {
	}
	
	public UserPoints(long userId, long lmsPoints) {
		super();
		this.userId = userId;
		this.lmsPoints = lmsPoints;
	}
	
	public void addLmsPoints(int points){
		this.lmsPoints = this.lmsPoints + points;
	}
	
	

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setLmsPoints(long lmsPoints) {
		this.lmsPoints = lmsPoints;
	}

	public long getUserId() {
		return userId;
	}

	
	public long getLmsPoints() {
		return lmsPoints;
	}


}
