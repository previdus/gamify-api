package com.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_points")
public class UserPoints {
	
	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	private long userId;
	@Column(name = "lms_points",columnDefinition="int default 0")
	private long lmsPoints = 0;
	
	
	public UserPoints(long userId, long lmsPoints) {
		super();
		this.userId = userId;
		this.lmsPoints = lmsPoints;
	}
	
	public void addLmsPoints(int points){
		this.lmsPoints = this.lmsPoints + points;
	}

	public long getUserId() {
		return userId;
	}

	
	public long getLmsPoints() {
		return lmsPoints;
	}


}
